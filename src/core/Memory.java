package core;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private final Map<String, String> store = new HashMap<>();
    private String lastReply = "";

    public void captureFacts(String message) {
        if (message == null || message.isBlank()) return;

        String t = message.toLowerCase().trim();
        lastReply = ""; // Reset for every new check

        // --- NAME EXTRACTION ---
        if (containsAny(t, new String[]{"my name is ", "i am ", "call me "})) {
            String prefix = findPrefix(t, new String[]{"my name is ", "i am ", "call me "});
            // Ensure we aren't accidentally catching "i am tired" as a name
            if (prefix.equals("i am ") && (t.contains("years old") || t.length() > 25)) {
                // Skip to age check or general conversation
            } else {
                String name = extractValue(message, prefix);
                store.put("name", name);
                lastReply = "Nice to meet you, " + name + " 🙂";
                return;
            }
        }

        // --- AGE EXTRACTION ---
        if (t.contains("years old") && t.contains("i am")) {
            try {
                int start = t.indexOf("i am ") + 5;
                int end = t.indexOf("years old");
                String age = message.substring(start, end).trim();
                store.put("age", age);
                lastReply = "Got it! You are " + age + " years old 🎂";
                return;
            } catch (Exception e) { /* ignore malformed age */ }
        }

        // --- LIKES / HOBBIES ---
        if (containsAny(t, new String[]{"i like to ", "my hobby is ", "i love "})) {
            String prefix = findPrefix(t, new String[]{"i like to ", "my hobby is ", "i love "});
            String hobby = extractValue(message, prefix);
            store.put("hobby", hobby);
            lastReply = "Cool! I’ll remember that you enjoy " + hobby + " 🎯";
            return;
        }

        if (containsAny(t, new String[]{"i like ", "favorite is "})) {
            String prefix = findPrefix(t, new String[]{"i like ", "favorite is "});
            String preference = extractValue(message, prefix);
            store.put("like", preference);
            lastReply = "Got it! I’ve noted that you like " + preference + " 📝";
            return;
        }

        // --- RECALL LOGIC ---
        if (t.contains("what") || t.contains("how")) {
            if (t.contains("my name")) {
                String n = store.get("name");
                lastReply = (n != null) ? "You told me your name is " + n + " 🙂" : "I don't know your name yet!";
            } else if (t.contains("hobby") || t.contains("like to do")) {
                String h = store.get("hobby");
                lastReply = (h != null) ? "Your hobby is " + h + " 🎯" : "I don't know your hobbies yet.";
            } else if (t.contains("old am i") || t.contains("my age")) {
                String a = store.get("age");
                lastReply = (a != null) ? "You are " + a + " years old 🎂" : "I haven't recorded your age.";
            } else if (t.contains("i like") || t.contains("my favorite")) {
                String l = store.get("like");
                lastReply = (l != null) ? "You mentioned you like " + l + " 🌟" : "I don't know what you like yet.";
            }
        }

        // --- RESET ---
        if (t.equals("reset memory") || t.equals("forget everything")) {
            store.clear();
            lastReply = "Memory reset 🧹";
        }
    }

    // Helper to extract text after a prefix regardless of length
    private String extractValue(String original, String prefix) {
        int start = original.toLowerCase().indexOf(prefix) + prefix.length();
        return original.substring(start).replaceAll("[.!?]", "").trim();
    }

    private boolean containsAny(String text, String[] keys) {
        for (String k : keys) if (text.contains(k)) return true;
        return false;
    }

    private String findPrefix(String text, String[] keys) {
        for (String k : keys) if (text.contains(k)) return k;
        return "";
    }

    public String recall() {
        return lastReply;
    }
}