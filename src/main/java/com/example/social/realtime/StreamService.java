package com.example.social.realtime;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class StreamService {
    private final Set<SseEmitter> emitters = new CopyOnWriteArraySet<>();

    public SseEmitter subscribe(){
        SseEmitter em = new SseEmitter(0L); // no timeout
        emitters.add(em);
        em.onCompletion(() -> emitters.remove(em));
        em.onTimeout(() -> emitters.remove(em));
        return em;
    }

    public void broadcast(String event, Object data){
        emitters.forEach(em -> {
            try {
                em.send(SseEmitter.event().name(event).data(data));
            } catch (IOException e) {
                em.complete();
                emitters.remove(em);
            }
        });
    }
}
