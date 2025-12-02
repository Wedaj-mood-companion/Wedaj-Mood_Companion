package com.wedaj.core;
public class ChatBot {

    private Memory memory;
    private ResponseGenerator responseGenerator;
    private String currentMood = "neutral";

    public ChatBot() {
        this.memory = new Memory();
        this.responseGenerator = new ResponseGenerator();
    }

    public String processMessage(String userMessage) {
        // 1. Update memory (stores name, favorites, etc.)
        memory.captureFacts(userMessage);

        // 2. Detect mood using simple keyword rules
        currentMood = detectMood(userMessage);

        // 3. Generate mood-based response
        String baseResponse = responseGenerator.getResponse(currentMood);

        // 4. Add memory-based responses if relevant
        String memoryInfo = memory.recall();

        return baseResponse + (memoryInfo.isEmpty() ? "" : "\n" + memoryInfo);
    }

    private String detectMood(String msg) {
        msg = msg.toLowerCase();

        String[] happyWords = {"happy", "excited", "great", "good", "nice"};
        String[] sadWords = {"sad", "down", "tired", "unhappy"};
        String[] angryWords = {"angry", "mad", "annoyed", "furious"};

        for (String h : happyWords)
            if (msg.contains(h)) return "happy";

        for (String s : sadWords)
            if (msg.contains(s)) return "sad";

        for (String a : angryWords)
            if (msg.contains(a)) return "angry";

        return "neutral";
    }

    public String getCurrentMood() {
        return currentMood;
    }
}
