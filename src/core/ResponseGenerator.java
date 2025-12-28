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
                response = "I’m glad to hear that! Keep smiling.";
                break;
            case Sad:
                response = "I’m sorry you’re feeling down. Want to talk about it?";
                break;
            case Angry:
                response = "I understand you’re upset. Let’s take a deep breath together.";
                break;
            case Excited:
                response = "That’s awesome! What’s got you so thrilled?";
                break;
            case Anxious:
                response = "It sounds like you’re worried. Remember to pause and breathe.";
                break;
            case Tired:
                response = "You seem exhausted. Maybe a short rest would help.";
                break;
            case Neutral:
                response = "Got it. Tell me more.";
                break;
            case Unknown:
            default:
                response = "I’m not sure how to interpret that. Could you rephrase?";
                break;
        }

        return new MoodResponse(response);
    }
}
