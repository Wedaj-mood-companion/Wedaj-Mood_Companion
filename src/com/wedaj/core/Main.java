package com.wedaj.core;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Main extends Application {
    
    private ChatBot chatbot;
    private VBox chatHistory;
    private TextField inputField;
    private Label moodIndicator;
    
    @Override
    public void start(Stage primaryStage) {
        chatbot = new ChatBot();
        
        // Create main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Title
        Label title = new Label("Wedaj - Mood Companion");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        root.setTop(titleBox);
        
        // Chat history area
        chatHistory = new VBox(10);
        chatHistory.setPadding(new Insets(10));
        chatHistory.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-radius: 5;");
        
        ScrollPane scrollPane = new ScrollPane(chatHistory);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(scrollPane);
        
        // Mood indicator
        moodIndicator = new Label("Mood: Neutral 😊");
        moodIndicator.setFont(Font.font("Arial", 14));
        moodIndicator.setStyle("-fx-text-fill: #3498db;");
        
        // Input area
        inputField = new TextField();
        inputField.setPromptText("Type your message here...");
        inputField.setFont(Font.font("Arial", 14));
        inputField.setOnAction(e -> sendMessage());
        
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        sendButton.setOnAction(e -> sendMessage());
        
        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setPadding(new Insets(10, 0, 0, 0));
        
        // Bottom panel with mood indicator and input
        VBox bottomPanel = new VBox(10, moodIndicator, inputBox);
        bottomPanel.setPadding(new Insets(10, 0, 0, 0));
        root.setBottom(bottomPanel);
        
        // Add welcome message
        addMessage("Wedaj", "Hello! I'm Wedaj, your mood companion. How are you feeling today? 😊", true);
        
        // Create scene
        Scene scene = new Scene(root, 500, 600);
        
        // Optional: Load external CSS
        // scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setTitle("Wedaj - Mood Companion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void sendMessage() {
        String userInput = inputField.getText().trim();
        
        if (!userInput.isEmpty()) {
            // Add user message
            addMessage("You", userInput, false);
            
            // Get bot response
            String botResponse = chatbot.processMessage(userInput);
            
            // Update mood indicator based on response
            updateMoodIndicator(botResponse);
            
            // Add bot response
            addMessage("Wedaj", botResponse, true);
            
            // Clear input field
            inputField.clear();
            
            // Scroll to bottom
            chatHistory.requestLayout();
        }
    }
    
    private void updateMoodIndicator(String response) {
        if (response.contains("😊") || response.contains("🌟") || response.contains("🎉")) {
            moodIndicator.setText("Mood: Happy 😄");
            moodIndicator.setStyle("-fx-text-fill: #27ae60;");
        } else if (response.contains("💔") || response.contains("🌧️") || response.contains("🤗")) {
            moodIndicator.setText("Mood: Sad 😔");
            moodIndicator.setStyle("-fx-text-fill: #e74c3c;");
        } else {
            moodIndicator.setText("Mood: Neutral 😊");
            moodIndicator.setStyle("-fx-text-fill: #3498db;");
        }
    }
    
    private void addMessage(String sender, String message, boolean isBot) {
        Label senderLabel = new Label(sender + ":");
        senderLabel.setFont(Font.font("Arial", 12));
        senderLabel.setStyle(isBot ? "-fx-text-fill: #3498db;" : "-fx-text-fill: #27ae60;");
        
        TextArea messageArea = new TextArea(message);
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.setPrefRowCount(2);
        messageArea.setMaxWidth(400);
        messageArea.setStyle(isBot ? 
            "-fx-background-color: #e3f2fd; -fx-border-color: #bbdefb;" : 
            "-fx-background-color: #e8f5e9; -fx-border-color: #c8e6c9;");
        messageArea.setFont(Font.font("Arial", 14));
        
        VBox messageBox = new VBox(5, senderLabel, messageArea);
        messageBox.setAlignment(isBot ? Pos.TOP_LEFT : Pos.TOP_RIGHT);
        
        chatHistory.getChildren().add(messageBox);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}