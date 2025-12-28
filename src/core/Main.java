package core;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private final ChatBot chatBot = new ChatBot();
    private final SignupManager signupManager = new SignupManager();

    private final Map<String, ObservableList<HBox>> chats = new HashMap<>();
    private ObservableList<HBox> currentChat;
    private int chatCounter = 1;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Wedaj - Your Mood Companion");

        // --- Signup Page ---
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");

        Button btnSignup = new Button("Sign Up");
        Button btnLogin = new Button("Login");
        Label lblAuthStatus = new Label();

        VBox signupLayout = new VBox(10,
                new Label("Welcome to Wedaj 😊"),
                txtEmail, txtPassword,
                new HBox(10, btnSignup, btnLogin),
                lblAuthStatus
        );
        signupLayout.setPadding(new Insets(20));
        signupLayout.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));

        Scene signupScene = new Scene(signupLayout, 400, 300);

        // --- Chat Page ---
        ListView<String> chatHistory = new ListView<>();
        chatHistory.setPrefWidth(150);
        chatHistory.setVisible(false);

        ListView<HBox> lstChat = new ListView<>();
        currentChat = FXCollections.observableArrayList();
        lstChat.setItems(currentChat);

        TextField txtInput = new TextField();
        txtInput.setPromptText("How are you feeling today?");
        txtInput.setMaxWidth(400);

        Button btnSend = new Button("Send");
        Label lblMood = new Label("Mood: —");

        // --- Icons for buttons ---
        ImageView historyIcon = new ImageView(new Image("file:src/icons/history.png"));
        historyIcon.setFitWidth(24);
        historyIcon.setFitHeight(24);
        Button btnToggleHistory = new Button();
        btnToggleHistory.setGraphic(historyIcon);
        btnToggleHistory.setTooltip(new Tooltip("Show/Hide Chat History"));

        Button btnNewChat = new Button("New Chat");

        ImageView themeIcon = new ImageView(new Image("file:src/icons/theme.png"));
        themeIcon.setFitWidth(24);
        themeIcon.setFitHeight(24);
        Button btnThemeToggle = new Button();
        btnThemeToggle.setGraphic(themeIcon);
        Tooltip themeTooltip = new Tooltip("Light Mode");
        btnThemeToggle.setTooltip(themeTooltip);

        ImageView logoutIcon = new ImageView(new Image("file:src/icons/logout.png"));
        logoutIcon.setFitWidth(24);
        logoutIcon.setFitHeight(24);
        Button btnLogout = new Button();
        btnLogout.setGraphic(logoutIcon);
        btnLogout.setTooltip(new Tooltip("Logout"));

        VBox leftPanel = new VBox(10, btnToggleHistory, btnNewChat, chatHistory);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(160);

        HBox topBar = new HBox(lblMood, new Region(), btnThemeToggle, btnLogout);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);

        HBox inputBar = new HBox(8, txtInput, btnSend);
        inputBar.setAlignment(Pos.CENTER);
        inputBar.setPadding(new Insets(10));

        BorderPane chatLayout = new BorderPane();
        chatLayout.setTop(topBar);
        chatLayout.setCenter(lstChat);
        chatLayout.setBottom(inputBar);
        chatLayout.setLeft(leftPanel);

        Scene chatScene = new Scene(chatLayout, 900, 520);

        // --- Helper to add messages ---
        final var addMessage = new Object() {
            void add(String text, boolean isUser) {
                Label lbl = new Label(text);
                lbl.setWrapText(true);
                lbl.setStyle("-fx-background-color: " + (isUser ? "#00aaff" : "#444") +
                        "; -fx-text-fill: white; -fx-padding: 8; -fx-background-radius: 10;");

                // Edit/Copy icons
                ImageView editIcon = new ImageView(new Image("file:src/icons/edit.png"));
                editIcon.setFitWidth(16);
                editIcon.setFitHeight(16);
                Button btnEdit = new Button();
                btnEdit.setGraphic(editIcon);

                ImageView copyIcon = new ImageView(new Image("file:src/icons/copy.png"));
                copyIcon.setFitWidth(16);
                copyIcon.setFitHeight(16);
                Button btnCopy = new Button();
                btnCopy.setGraphic(copyIcon);

                HBox actions = new HBox(5, btnEdit, btnCopy);
                actions.setVisible(false);

                VBox messageBox = new VBox(lbl, actions);
                messageBox.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
                messageBox.setPadding(new Insets(5));
                messageBox.setMaxWidth(600);

                messageBox.setOnMouseEntered(e -> actions.setVisible(true));
                messageBox.setOnMouseExited(e -> actions.setVisible(false));

                btnEdit.setOnAction(e -> {
                    TextInputDialog dialog = new TextInputDialog(lbl.getText());
                    dialog.setHeaderText("Edit message");
                    dialog.setContentText("Update:");
                    dialog.showAndWait().ifPresent(newText -> lbl.setText(newText));
                });

                btnCopy.setOnAction(e -> {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(lbl.getText());
                    clipboard.setContent(content);
                });

                HBox container = new HBox(messageBox);
                container.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
                currentChat.add(container);

                // Scroll to latest message
                lstChat.scrollTo(currentChat.size() - 1);
            }
        };

        // --- Actions ---
        btnSignup.setOnAction(e -> {
            var status = signupManager.signup(txtEmail.getText(), txtPassword.getText());
            if (status == SignupManager.Status.SUCCESS) {
                lblAuthStatus.setText("Signup successful! Logged in.");
                stage.setScene(chatScene);
                startNewChat(chatHistory, lstChat);
            } else {
                lblAuthStatus.setText("Signup failed.");
            }
        });

        btnLogin.setOnAction(e -> {
            var status = signupManager.login(txtEmail.getText(), txtPassword.getText());
            if (status == SignupManager.Status.SUCCESS) {
                lblAuthStatus.setText("Login successful!");
                stage.setScene(chatScene);
                if (chats.isEmpty()) startNewChat(chatHistory, lstChat);
            } else {
                lblAuthStatus.setText("Login failed.");
            }
        });

        btnSend.setOnAction(e -> {
            String userText = txtInput.getText();
            if (userText == null || userText.isBlank()) return;

            String currentUser = signupManager.currentUser();
            if (currentUser == null) return; // safety

            addMessage.add(userText, true);
            ChatBot.Result result = chatBot.reply(userText, currentUser);
            addMessage.add(result.text, false);
            lblMood.setText("Mood: " + result.mood);
            txtInput.clear();
        });


        txtInput.setOnAction(e -> btnSend.fire());

        btnNewChat.setOnAction(e -> startNewChat(chatHistory, lstChat));

        btnToggleHistory.setOnAction(e -> chatHistory.setVisible(!chatHistory.isVisible()));

        // Chat history selection
        chatHistory.getSelectionModel().selectedItemProperty().addListener((obs, oldChat, newChat) -> {
            if (newChat != null && chats.containsKey(newChat)) {
                currentChat = chats.get(newChat);
                lstChat.setItems(currentChat);
            }
        });

        btnThemeToggle.setOnAction(e -> {
            if (themeTooltip.getText().equals("Light Mode")) {
                chatLayout.setBackground(new Background(new BackgroundFill(Color.web("#1e1e1e"), null, null)));
                lstChat.setStyle("-fx-control-inner-background: #1e1e1e; -fx-text-fill: white;");
                txtInput.setStyle("-fx-background-color: transparent; -fx-border-color: #00aaff; -fx-text-fill: white;");
                themeTooltip.setText("Dark Mode");
            } else {
                chatLayout.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));
                lstChat.setStyle("-fx-control-inner-background: #ffffff; -fx-text-fill: black;");
                txtInput.setStyle("-fx-background-color: transparent; -fx-border-color: #0066cc; -fx-text-fill: black;");
                themeTooltip.setText("Light Mode");
            }
        });

        btnLogout.setOnAction(e -> {
            signupManager.logout();
            currentChat.clear();
            chatHistory.getItems().clear();
            stage.setScene(signupScene);
        });

        // Start with signup scene
        stage.setScene(signupScene);
        stage.show();
    }

    private void startNewChat(ListView<String> chatHistory, ListView<HBox> lstChat) {
        String chatName = "Chat " + chatCounter++;
        ObservableList<HBox> newChat = FXCollections.observableArrayList();
        chats.put(chatName, newChat);
        chatHistory.getItems().add(chatName);
        currentChat = newChat;
        lstChat.setItems(currentChat);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
