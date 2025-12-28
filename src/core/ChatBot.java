package core;

public class ChatBot {
    private final MoodAnalyzer analyzer = new MoodAnalyzer();
    private final Memory memory = new Memory();
    private final ResponseGenerator generator = new ResponseGenerator();

    public static class Result {
        public final String text;
        public final Mood mood;

        public Result(String text, Mood mood) {
            this.text = text;
            this.mood = mood;
        }
    }

    public Result reply(String userMessage) {
        if ("crash".equals(userMessage)) throw new RuntimeException("Forced crash for testing");
        try {
            if (userMessage == null || userMessage.isBlank()) {
                return new Result("Message was empty.", Mood.Neutral);
            }

            memory.captureFacts(userMessage);
            Mood mood = analyzer.detectMood(userMessage);
            ResponseGenerator.MoodResponse responder = generator.getResponse(mood, userMessage);

            String reply = responder.reply();
            String memoryInfo = memory.recall();
            String fullReply = reply + (memoryInfo.isEmpty() ? "" : "\n" + memoryInfo);

            // Debug output
            System.out.println("[DBG] user='" + userMessage + "'");
            System.out.println("[DBG] mood=" + mood);
            System.out.println("[DBG] reply=" + reply);

            return new Result(fullReply, mood);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("Error processing message.", Mood.Unknown);
        }
    }
}

