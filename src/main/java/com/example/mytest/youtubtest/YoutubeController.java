package com.example.mytest.youtubtest;

import com.example.mytest.youtubtest.spec.YoutubeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeController {
    private YoutubeProvider youtubeProvider;

    @Autowired
    public YoutubeController(final YoutubeProvider youtubeProvider) {
        this.youtubeProvider = youtubeProvider;
    }

    @GetMapping("youtube")
    public YoutubeDTO Index() {
        return youtubeProvider.get();
    }

    @GetMapping("test57")
    public String Indexz() {
        return youtubeProvider.test();
    }

}

