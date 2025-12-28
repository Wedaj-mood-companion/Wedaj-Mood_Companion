package core;

public class ResponseGenerator {

    // Inner class for consistency with your ChatBot usage
    public static class MoodResponse {
        private final String reply;

        public MoodResponse(String reply) {
            this.reply = reply;
        }

        public String reply() {
            return reply;
        }
    }

    public MoodResponse getResponse(Mood mood, String userMessage) {
        String response;

        switch (mood) {
            case Greeting:
                response = "Hello! How are you feeling today?";
                break;
            case Happy:
                response = "I’m glad to hear that! Keep smiling and enjoy the moment.";
                break;
            case Sad:
                response = "I’m sorry you’re feeling down. It’s okay to share what’s on your mind.";
                break;
            case Angry:
                response = "I understand you’re upset. Let’s pause and take a deep breath together.";
                break;
            case Excited:
                response = "That’s awesome! What’s got you so thrilled right now?";
                break;
            case Anxious:
                response = "It sounds like you’re worried. Remember to pause, breathe, and take things one step at a time.";
                break;
            case Tired:
                response = "You seem exhausted. Maybe a short rest or break would help recharge you.";
                break;
            case Conflicted:
                response = "I hear mixed feelings — part of you feels positive, but something upsetting happened too. Want to talk more about it?";
                break;
            case Neutral:
                response = "Got it. Tell me more about how you’re feeling.";
                break;
            case Unknown:
            default:
                response = "I’m not sure how to interpret that. Could you rephrase or explain a bit more?";
                break;
        }

        return new MoodResponse(response);
    }
}
