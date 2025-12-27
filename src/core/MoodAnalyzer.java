package core;

public class MoodAnalyzer {

    public Mood detectMood(String message) {
        if (message == null || message.isBlank()) return Mood.Unknown;
        String t = message.toLowerCase();
        if (message == null || message.isBlank()) return Mood.Neutral;
        if (containsAny(t, new String[]{"hi","hello","hey","greetings","good morning","good afternoon", "good evening","what’s up","yo","sup"})) return Mood.Greeting;
        if (containsAny(t, new String[]{"happy","glad","joy","good","great", "joyful", "cheerful", "delighted", "content", "awesome", "fantastic", "wonderful", "smiling", "positive"})) return Mood.Happy;
        if (containsAny(t, new String[]{"sad","down","blue","unhappy", "cry", "depressed", "lonely", "miserable", "crying", "upset", "bad", "hopeless", "low", "heartbroken"})) return Mood.Sad;
        if (containsAny(t, new String[]{"angry","mad","furious","annoyed", "irritated", "frustrated", "rage", "pissed", "resentful", "hate", "cross", "agitated"})) return Mood.Angry;
        if (containsAny(t, new String[]{"tired","exhausted","sleepy","fatigued", "weary", "drained", "worn out", "drowsy", "sluggish", "burnt out", "low energy", "yawning"})) return Mood.Tired;
        if (containsAny(t, new String[]{"excited","hyped","thrilled","pumped","ecstatic","elated","overjoyed", "buzzing","energetic","can’t wait","looking forward","stoked"})) return Mood.Excited;
        if (containsAny(t, new String[]{"anxious","worried","stressed","nervous", "confuse","uneasy","overthinking", "panicked","concerned","restless","afraid","fearful","tense"})) return Mood.Anxious;
        if (containsAny(t, new String[]{"bored"})) return Mood.Neutral;
        // If input is mostly symbols or gibberish:
        if (t.matches("[^a-zA-Z0-9 ]{5,}")) return Mood.Unknown;
        return Mood.Neutral;
    }

    private boolean containsAny(String text, String[] keys) {
        for (String k : keys) {
            if (text.contains(k)) return true;
        }
        return false;
    }
}
