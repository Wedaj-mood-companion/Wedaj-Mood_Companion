import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    // FIELD DECLARATIONS
    private StackPane rootContainer;
    private VBox loginView;
    private BorderPane chatView;
    
    private VBox chatBox; 
    private TextField txtInput;
    private Label lblTitle;
    private Label lblMood;

    //THEME CONSTANTS
    private final String BLUE_BLACK_BG = "#0d1117"; 
    private final String THEME_COLOR = "#34495e"; 
    private final String ACCENT_COLOR = "#ecf0f1";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Wedaj - Your Mood Companion");

        //SIGNUP/LOGIN PAGE
        Label lblLoginTitle = new Label("Wedaj Mood Companion 😊");
        lblLoginTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("email");
        txtEmail.setMaxWidth(300);
        txtEmail.setPrefHeight(40);
        txtEmail.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #888888; " +
                          "-fx-border-color: #444444; -fx-border-width: 1; -fx-border-radius: 5;");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("password");
        txtPass.setMaxWidth(300);
        txtPass.setPrefHeight(40);
        txtPass.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #888888; " +
                         "-fx-border-color: #444444; -fx-border-width: 1; -fx-border-radius: 5;");

        Button btnLogin = new Button("Sign Up/Login");
        btnLogin.setStyle("-fx-background-color: " + THEME_COLOR + "; -fx-text-fill: " + ACCENT_COLOR + "; " +
                          "-fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
        btnLogin.setPrefWidth(300);
        btnLogin.setPrefHeight(40);

        loginView = new VBox(20, lblLoginTitle, txtEmail, txtPass, btnLogin);
        loginView.setAlignment(Pos.CENTER);
        loginView.setStyle("-fx-background-color: " + BLUE_BLACK_BG + ";"); 

        
        //FULL CHAT INTERFACE
        chatBox = new VBox(15);
        chatBox.setPadding(new Insets(15));
        
        ScrollPane scrollPane = new ScrollPane(chatBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        lblTitle = new Label("Wedaj AI");
        lblTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        lblMood = new Label("Mood: —");
        Button btnNewChat = new Button("+ New Chat");
        
        HBox topContent = new HBox(15, btnNewChat, lblTitle, lblMood);
        topContent.setPadding(new Insets(15));
        topContent.setStyle("-fx-background-color: white; -fx-border-color: #EEE; -fx-border-width: 0 0 1 0;");

        txtInput = new TextField();
        txtInput.setPromptText("How are you feeling today?");
        txtInput.setPrefHeight(45);
        txtInput.setStyle("-fx-background-color: transparent; -fx-border-color: " + THEME_COLOR + "; " +
                          "-fx-border-width: 1.5; -fx-border-radius: 25; -fx-padding: 0 15 0 15;");

        Button btnSend = new Button("Send");
        btnSend.setPrefSize(80, 45);
        btnSend.setStyle("-fx-background-color: " + THEME_COLOR + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25;");

        HBox inputBar = new HBox(10, txtInput, btnSend);
        inputBar.setPadding(new Insets(20));
        HBox.setHgrow(txtInput, Priority.ALWAYS);

        chatView = new BorderPane();
        chatView.setTop(topContent);
        chatView.setCenter(scrollPane);
        chatView.setBottom(inputBar);
        chatView.setStyle("-fx-background-color: #ffffff;");
        chatView.setVisible(false);

        //NAVIGATION LOGIC
        btnLogin.setOnAction(e -> {
            loginView.setVisible(false);
            chatView.setVisible(true);
        });

        //FINAL ASSEMBLY
        rootContainer = new StackPane(chatView, loginView);
        Scene scene = new Scene(rootContainer, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}