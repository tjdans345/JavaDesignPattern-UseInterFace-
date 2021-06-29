package com.example.mytest.youtubtest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YoutubeDTO {

    private String title;
    private String thumbnail;
    private String videoId;

}
