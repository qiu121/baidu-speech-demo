package com.github.qiu121.controller;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/11/24
 */
@RestController
@RequestMapping("/audio")
public class AudioController {

    public static final String APP_ID = "你的 App ID";
    public static final String API_KEY = "你的 Api Key";
    public static final String SECRET_KEY = "你的 Secret Key";

    @GetMapping("/test/{name}")
    public ResponseEntity<Resource> test(@PathVariable String name) {
        ClassPathResource resource = new ClassPathResource(name + ".mp3");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg3"))
                .body(resource);
    }

    /**
     * @param name
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/{name}")
    public ResponseEntity<byte[]> getAudio(@PathVariable String name) throws InterruptedException {

        /*
            在线合成REST-API-Java-SDK文档
            https://cloud.baidu.com/doc/SPEECH/s/Zlbxhfedg
        */
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 设置可选参数
        HashMap<String, Object> options = new HashMap<>();
        options.put("spd", "6");// 语速，取值0-9,默认为5
        options.put("pit", "5");// 音调，取值0-9,默认为5
        options.put("per", "1");// 普通发音人选择：度小美=0(默认)，度小宇=1，，度逍遥（基础）=3，度丫丫=4
        options.put("vol", "5");// 音量，取值0-9,默认为5

        TtsResponse response = client.synthesis(name, "zh", 1, options);
        byte[] data = response.getData();
        JSONObject result = response.getResult();

        // long currentTimeMillis = System.currentTimeMillis();
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.parseMediaType("audio/mpeg3"));
        // headers.setContentLength(data.length);
        // headers.setContentDispositionFormData("attachment", currentTimeMillis + ".mp3");
        // headers.setContentDispositionFormData("inline", currentTimeMillis + ".mp3");

        if (result != null) {
            System.out.println(result.toString(2));
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg3"))
                .body(data);
    }
}
