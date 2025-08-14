package com.example.social.realtime;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class StreamController {
    private final StreamService stream;
    public StreamController(StreamService stream){ this.stream = stream; }

    @GetMapping("/stream")
    public SseEmitter stream(){ return stream.subscribe(); }
}
