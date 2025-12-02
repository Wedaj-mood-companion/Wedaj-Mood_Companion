package com.wedaj.core;

public class Memory {

    private String userName = "";
    private String favoriteColor = "";

    public void captureFacts(String message) {
        message = message.toLowerCase();

        if (message.contains("my name is")) {
            userName = message.replace("my name is", "").trim();
        }

        if (message.contains("my favorite color is")) {
            favoriteColor = message.replace("my favorite color is", "").trim();
        }
    }

    public String recall() {
        StringBuilder sb = new StringBuilder();

        if (!userName.isEmpty()) {
            sb.append("By the way, I remember your name is ").append(userName).append(". 😊");
        }

        if (!favoriteColor.isEmpty()) {
            sb.append("\nI also remember your favorite color is ").append(favoriteColor).append("!");
        }

        return sb.toString();
    }
}

