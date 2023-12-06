import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

public class ATM {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;

    public static void main(String[] args) {
        initializeUsers();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (authenticateUser(userId, pin)) {
            System.out.println("Authentication successful. Welcome, " + currentUser.getUserId() + "!");
            showMenu(scanner);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private static void initializeUsers() {
        users.put("madhu", new User("madhu","2552", 1000.0));
        users.put("mohith", new User("mohith","2808", 1500.0));
	users.put("rupa", new User("rupa","4444",2500.0));
	users.put("charan", new User("charan","1234",3650.0));
	users.put("akash", new User("akash","2006",1679.2));
        // Add more users as needed
    }

    private static boolean authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    private static void showMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void checkBalance() {
        System.out.println("Current Balance: $" + currentUser.getBalance());
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();
        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            System.out.println("Withdrawal successful. Remaining balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount or insufficient funds. Please try again.");
        }
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            currentUser.setBalance(currentUser.getBalance() + amount);
            System.out.println("Deposit successful. New balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount. Please try again.");
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.print("Enter the user ID to transfer to: ");
        String targetUserId = scanner.nextLine();

        User targetUser = users.get(targetUserId);

        if (targetUser != null) {
            System.out.print("Enter the amount to transfer: $");
            double amount = scanner.nextDouble();

            if (amount > 0 && amount <= currentUser.getBalance()) {
                currentUser.setBalance(currentUser.getBalance() - amount);
                targetUser.setBalance(targetUser.getBalance() + amount);
                System.out.println("Transfer successful. Remaining balance: $" + currentUser.getBalance());
            } else {
                System.out.println("Invalid amount or insufficient funds. Please try again.");
            }
        } else {
            System.out.println("User not found. Please enter a valid user ID.");
        }
    }
}
