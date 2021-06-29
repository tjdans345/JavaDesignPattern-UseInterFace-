package com.example.mytest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnsDTO {
    private Integer statusCode;
    private String statusMessage;
    private Integer id;
    private String name;
    private String email;
    private String birthday;
    private String gender;
    private String accessToken;
    private String state;

}
