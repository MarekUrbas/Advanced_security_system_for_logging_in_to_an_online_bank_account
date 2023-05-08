import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// User class represents a user of the bank account
class User {
    private final String username;
    private final String passwordHash;

    public User(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();

        for (byte hashByte : hashBytes) {
            stringBuilder.append(String.format("%02x", hashByte));
        }

        return stringBuilder.toString();
    }

    public boolean authenticate(String password) throws NoSuchAlgorithmException {
        String enteredPasswordHash = hashPassword(password);
        return enteredPasswordHash.equals(passwordHash);
    }
}

// BankAccount class represents an online bank account
class BankAccount {
    private final Map<String, User> users;

    public BankAccount() {
        this.users = new HashMap<>();
    }

    public void registerUser(String username, String password) throws NoSuchAlgorithmException {
        User newUser = new User(username, password);
        users.put(username, newUser);
        System.out.println("User registration successful.");
    }

    public void loginUser(String username, String password) throws NoSuchAlgorithmException {
        User user = users.get(username);

        if (user != null && user.authenticate(password)) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        BankAccount bankAccount = new BankAccount();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        bankAccount.registerUser("John", "pass123");

        bankAccount.loginUser(username, password);
    }
}
