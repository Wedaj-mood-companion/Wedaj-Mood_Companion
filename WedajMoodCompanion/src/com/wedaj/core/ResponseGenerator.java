package com.wedaj.core;

import java.util.HashMap;

public class ResponseGenerator {

    private HashMap<String, String> responses;

    public ResponseGenerator() {
        responses = new HashMap<>();

        responses.put("happy", "Yay! I'm happy you're feeling good! 😄✨");
        responses.put("sad", "I'm sorry you're feeling down. I'm here for you. 💛");
        responses.put("angry", "It’s okay to feel angry sometimes. Want to talk about it? 😤👉🙂");
        responses.put("neutral", "Tell me more! I'm listening. 😊");
    }

    public String getResponse(String mood) {
        return responses.getOrDefault(mood, responses.get("neutral"));
    }
}
