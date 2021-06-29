package com.example.mytest.controller;

import com.example.mytest.KakaoData;
import com.example.mytest.SnsDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TempController {








    @GetMapping("/test2")
    public String test() {
        System.out.println("ㅎㅇ");
        return "yotubeJsTest";
    }


    //		//카카오 테스트
    // 콜백 API
    @RequestMapping(value = "/test", produces = "apllication/json", method = RequestMethod.GET)
    public String snsTest(@RequestParam("code") String code, @RequestParam("state") String state, RedirectAttributes ra, HttpSession session,
                          HttpServletResponse response, Model model
    ) {

        System.out.println("왔업");
        System.out.println(code);
        System.out.println(state);
        model.addAttribute("code", code);
        SnsDTO snsDTO = null;

//        Map<String, Object> testp = new HashMap<String, Object>();
//        testp.put("result", testPost(code));
        Map<String, Object> accessMap = getToken(code);

        if (accessMap == null) {
            model.addAttribute("result", resultMethod(400, "널값 에러"));
        } else if("code".equals(accessMap.get("error"))) {
            model.addAttribute("result", resultMethod(402, "파라미터값 에러"));
        }
        else {
            String accessToken = (String) accessMap.get("access_token");
            String refreshToken = (String) accessMap.get("refresh_token");
            System.out.println(accessMap);
            System.out.println("access_token" + accessToken);
            System.out.println("refresh_token" + refreshToken);

            snsDTO = requestGetUserInfo(accessToken, refreshToken);
            snsDTO.setState(state);
            Gson gson = new Gson();
            System.out.println("제이슨 타입 변환 전 " + snsDTO);
            model.addAttribute("result", gson.toJson(snsDTO));

        }



        System.out.println("---------------------------------------------------");
        System.out.println("최종 결과값 확인해보자 : " + model.getAttribute("result"));
        return "test5";
    }

    private String resultMethod(int statusCode, String statusMessage) {
        SnsDTO snsDTO = null;
        Gson gson = new Gson();
        snsDTO = SnsDTO.builder().statusCode(statusCode).statusMessage(statusMessage).build();
        
        return gson.toJson(snsDTO);

    }


    //카카오 Post 테스트2
    private Map<String, Object> getToken(String code) {

        //post 요청 ㄱㄱ해야함
        String accessToken = "";
        String refreshToken = "";
        String targetUrl = "https://kauth.kakao.com/oauth/token";

        Map<String, Object> resultObj = new HashMap<String, Object>();

        try {

            URL url = new URL(targetUrl);

            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();


            con.setRequestMethod("POST");
            con.setDoOutput(true);
            // POST 파라미터 전달을 위한 설정
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            //append -> 쿼리스트링 느낌
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=79f79d24d2f1f339724c007a9913ecba");

            sb.append("&redirect_uri=http://192.168.0.30:8080/test/test&response_type=code");
            sb.append("&code= ").append(code);
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            System.out.println("response body : " + result);


            Gson gson = new Gson();
            Type typeOfHashMap = new TypeToken<Map<String, Object>>() {
            }.getType();
            resultObj = gson.fromJson(result.toString(), typeOfHashMap);
            br.close();
            bw.close();
        } catch (Exception e) {

            resultObj.put("error", "code");
           return resultObj;
        }
        return resultObj;

    }

    private SnsDTO requestGetUserInfo(String accessToken, String refreshToken) {

        String targetUrl = "https://kapi.kakao.com/v2/user/me";

        SnsDTO snsDTO = null;
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            URL url = new URL(targetUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);
            con.setDoOutput(true);
            con.getOutputStream().flush();
            int responseCode = con.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            Gson gson = new Gson();
            KakaoData kakaoData = gson.fromJson(result.toString(), KakaoData.class);

            snsDTO = SnsDTO.builder()
                    .statusCode(responseCode)
                    .statusMessage("성공")
                    .id(kakaoData.getId())
                    .name(kakaoData.getProperties().getNickname())
                    .email(kakaoData.getKakaoAccount().getEmail())
                    .birthday(kakaoData.getKakaoAccount().getBirthday())
                    .gender(kakaoData.getKakaoAccount().getGender())
                    .accessToken(accessToken)
                    .build();


//            System.out.println(".class가 무엇일까 ? " + KakaoData.class);
//
//            dataMap.put("kakaoData", kakaoData);
        } catch (Exception e) {
            snsDTO = SnsDTO.builder()
                    .statusCode(401)
                    .statusMessage("토큰값이 잘못되었습니다.")
                    .build();
        }
        return snsDTO;
    }
}
