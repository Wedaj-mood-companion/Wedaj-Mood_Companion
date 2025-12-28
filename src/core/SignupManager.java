package core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupManager {
    private final Map<String, String> users = new HashMap<>();
    private final Map<String, Memory> userMemories = new HashMap<>();
    private String currentUser = null;

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    public enum Status {
        SUCCESS,
        INVALID_INPUT,
        ALREADY_EXISTS,
        WRONG_CREDENTIALS,
        NOT_LOGGED_IN
    }

    // Sign up new account
    public Status signup(String email, String password) {
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            return Status.INVALID_INPUT;
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            return Status.INVALID_INPUT;
        }
        if (users.containsKey(email)) {
            return Status.ALREADY_EXISTS;
        }

        users.put(email, password);
        userMemories.put(email, new Memory()); // create memory for new user
        currentUser = email; // auto-login
        return Status.SUCCESS;
    }

    // Login existing account
    public Status login(String email, String password) {
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            return Status.INVALID_INPUT;
        }
        if (!users.containsKey(email) || !users.get(email).equals(password)) {
            return Status.WRONG_CREDENTIALS;
        }
        if (!userMemories.containsKey(email)) {
            userMemories.put(email, new Memory()); // create memory if missing
        }
        currentUser = email;
        return Status.SUCCESS;
    }

    // Logout
    public Status logout() {
        if (currentUser == null) return Status.NOT_LOGGED_IN;
        currentUser = null;
        return Status.SUCCESS;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String currentUser() {
        return currentUser;
    }

    public Memory getCurrentUserMemory() {
        return currentUser != null ? userMemories.get(currentUser) : null;
    }
}
