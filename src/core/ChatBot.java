package core;

import java.util.HashMap;
import java.util.Map;

public class ChatBot {
    private final MoodAnalyzer moodAnalyzer = new MoodAnalyzer();
    private final ResponseGenerator responseGenerator = new ResponseGenerator();

    // Memory per user (keyed by username/email)
    private final Map<String, Memory> userMemory = new HashMap<>();

    // Get memory for current user (creates if missing)
    public Memory getMemoryForUser(String username) {
        return userMemory.computeIfAbsent(username, k -> new Memory());
    }

    public static class Result {
        public final String text;
        public final Mood mood;

        public Result(String text, Mood mood) {
            this.text = text;
            this.mood = mood;
        }
    }

    public Result reply(String userMessage, String username) {
        Mood mood = moodAnalyzer.detectMood(userMessage);

        // Capture memory for this user
        Memory memory = getMemoryForUser(username);
        memory.captureFacts(userMessage);

        ResponseGenerator.MoodResponse response = responseGenerator.getResponse(mood, userMessage);
        return new Result(response.reply() +
                (memory.recall().isEmpty() ? "" : "\n" + memory.recall()), mood);
    }
}
