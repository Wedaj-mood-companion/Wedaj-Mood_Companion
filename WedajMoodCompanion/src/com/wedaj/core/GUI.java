package com.wedaj.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private ChatBot chatbot;
    private JTextArea chatArea;
    private JTextField inputField;
    private JLabel moodLabel;

    public GUI() {
        chatbot = new ChatBot();

        setTitle("Wedaj - Mood Companion");
        setSize(500, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Chat History
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Bottom panel for input
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        inputField = new JTextField();
        bottomPanel.add(inputField, BorderLayout.CENTER);

        JButton sendBtn = new JButton("Send");
        bottomPanel.add(sendBtn, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Mood indicator
        moodLabel = new JLabel("Mood: Neutral");
        moodLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(moodLabel, BorderLayout.NORTH);

        // Event handling
        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    private void sendMessage() {
        String userMsg = inputField.getText().trim();
        if (userMsg.isEmpty()) return;

        chatArea.append("You: " + userMsg + "\n");

        String reply = chatbot.processMessage(userMsg);
        chatArea.append("Wedaj: " + reply + "\n\n");

        moodLabel.setText("Mood: " + chatbot.getCurrentMood());

        inputField.setText("");
    }

    public static void main(String[] args) {
        new GUI();
    }
}
