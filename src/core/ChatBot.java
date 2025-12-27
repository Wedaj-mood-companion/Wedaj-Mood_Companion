package core;

public class ChatBot {
    private final MoodAnalyzer analyzer = new MoodAnalyzer();
    private final Memory memory = new Memory();
    private final ResponseGenerator generator = new ResponseGenerator();

    public static class Result {
        public final String text;
        public final String mood;

        public Result(String text, String mood) {
            this.text = text;
            this.mood = mood;
        }
    }

    public Result reply(String userMessage) {
        if (userMessage.equals("crash")) throw new RuntimeException("Forced crash for testing");
        try{
            if(userMessage == null){
                return new Result("Message was empty.", "Neutral");
            }
        memory.captureFacts(userMessage);
        Mood mood = analyzer.detectMood(userMessage);
        ResponseGenerator.MoodResponse responder = generator.getResponse(mood, userMessage);
        String reply = responder.reply();
        String memoryInfo = memory.recall();
        String fullReply = reply + (memoryInfo.isEmpty() ? "" : "\n" + memoryInfo);
        return new Result(fullReply, mood.name());
        } catch (Exception e) {
            return new Result("Error processing message.", "Neutral");
        }
    }
}
