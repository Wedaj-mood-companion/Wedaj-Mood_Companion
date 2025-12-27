package core;

import java.util.Map;

public class ResponseGenerator {
    private final Map<Mood, String> emoji = Map.of(
            Mood.Happy, "😀",
            Mood.Sad, "😢",
            Mood.Angry, "😡",
            Mood.Tired, "😴",
            Mood.Excited, "🤩",
            Mood.Anxious, "😬",
            Mood.Neutral, "🙂"
    );

    public MoodResponse getResponse(Mood mood, String userMessage) {
        return new MoodResponse(mood, emoji.get(mood), userMessage);
    }

    public static class MoodResponse {
        private final Mood mood;
        private final String emoji;
        private final String userMessage;

        public MoodResponse(Mood mood, String emoji, String userMessage) {
            this.mood = mood;
            this.emoji = emoji;
            this.userMessage = userMessage;
        }

        public String reply() {
            switch (mood) {
                case Happy:
                    return "I’m glad to hear that! " + emoji;
                case Sad:
                    return "I’m sorry you’re feeling low. " + emoji;
                case Angry:
                    return "That sounds frustrating. " + emoji;
                case Tired:
                    return "Rest matters. " + emoji;
                case Excited:
                    return "Love that energy! " + emoji;
                case Anxious:
                    return "That sounds stressful. " + emoji;
                case Neutral:
                case Unknown:
                    return "Hmm, I couldn’t quite understand that. 🤔";
                case Greeting:
                        return "Hello there! 👋";
                default:
                    return "I didn’t understand, can you rephrase? " + emoji;
            }
        }
    }
}
