package com.example.mytest.youtubtest;

import com.example.mytest.youtubtest.spec.YoutubeProvider;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class YouTubeService implements YoutubeProvider {

    private static  final HttpTransport HTTP_TRANSPORT =  new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 5;
    private static YouTube youtube;

    private static void prettyPrint(Iterator<Video> iteratorsSearchResults, YoutubeDTO youtubeDTO) {

        System.out.println("\n========================================");
        System.out.println("===========================================\n");

        if(!iteratorsSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorsSearchResults.hasNext()) {

            Video singleVideo = iteratorsSearchResults.next();

            //Double checks the kind is video
            Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

            System.out.println(" Video Id" + singleVideo.getId());
            System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
            System.out.println(" contentDetails Duration: " + singleVideo.getContentDetails().getDuration());
            System.out.println(" Thumbnail: " + thumbnail.getUrl());
            System.out.println("\n-------------------------------------------------------------\n");
            youtubeDTO.setTitle(singleVideo.getSnippet().getTitle());
            youtubeDTO.setVideoId(singleVideo.getId());
        }
    }

    public YoutubeDTO get() {
        YoutubeDTO youtubeDTO = new YoutubeDTO();

        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-video-duration-get").build();

            //내가 원하는 정보 지정 가능. 공식 문서 참조
            YouTube.Videos.List videos = youtube.videos().list("id, snippet,contentDetails");

            videos.setKey("AIzaSyD7YsZK0jsDkN-E2gku_BKOFTaGEU3e8O0");
            videos.setId("iNmSro36dhI");
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            List<Video> videoList = videos.execute().getItems();

            if (videoList != null) {
                prettyPrint((Iterator<Video>) ((List<?>) videoList).iterator(), youtubeDTO);
            }

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return youtubeDTO;

    }

    public String test() {
        System.out.println("???");
        return null;
    }


}


