import java.util.InputMismatchException;
import java.util.Scanner;

// Base Class
class Expense4 {
    String description;
    double amount;

    public Expense4(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public void displayDetails() {
        System.out.println(description + ": ₹" + amount);
    }
}

// Subclass
class GeneralExpense extends Expense4 {
    public GeneralExpense(String description, double amount) {
        super(description, amount);
    }
}

// Subclass
class SubscriptionExpense extends Expense4 {
    int totalMonths;

    public SubscriptionExpense(String description, double amount, int totalMonths) {
        super(description, amount);
        this.totalMonths = totalMonths;
    }

    @Override
    public void displayDetails() {
        System.out.println(description + ": ₹" + amount + " for " + totalMonths + " months");
    }
}

// Expense Tracker Class
public class ExpenseTracker4 {
    private Expense4[] expenses;
    private int count;
    private double totalSpent;
    private static double remainingIncome, annual;
    private static final String Username = "sridhar";
    private static final String Password = "12345";

    public ExpenseTracker4(int size, double annual) {
        expenses = new Expense4[size];
        count = 0;
        remainingIncome = annual;
        this.annual = annual;
    }

    // Add General Expense
    void addExpense(String description, double amount) {
        if (count < expenses.length) {
            expenses[count] = new GeneralExpense(description, amount);
            count++;
            System.out.println("Expense added: " + description + " - ₹" + amount);
            totalSpent += amount;
        } else {
            System.out.println("Expense list is full!");
        }
    }

    // Add Subscription Expense
    void addSubscriptionExpense(String description, double amount, int totalMonths) {
        if (count < expenses.length) {
            expenses[count] = new SubscriptionExpense(description, amount, totalMonths);
            count++;
            System.out.println("Expense added: " + description + " - ₹" + amount + " for " + totalMonths + " months");
            totalSpent += amount * totalMonths;
        } else {
            System.out.println("Expense list is full!");
        }
    }

    // Display Expenses
    public void displayExpenses() {
        System.out.println("\nAll Expenses: ");
        for (int i = 0; i < count; i++) {
            expenses[i].displayDetails();
        }
        System.out.println("Total Spent: ₹" + totalSpent);
        remainingIncome = annual - totalSpent;
        System.out.println("Remaining Income Left: ₹" + remainingIncome + "\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInputName, userPassword;

        // Login System
        do {
            System.out.print("Enter Username: ");
            userInputName = scanner.nextLine();
            System.out.print("Enter Password: ");
            userPassword = scanner.nextLine();

            if (!userInputName.equals(Username) || !userPassword.equals(Password)) {
                System.out.println("Invalid Credentials! Try Again");
            }
        } while (!userInputName.equals(Username) || !userPassword.equals(Password));

        double annualIncome = 0;
        int size = 0;
        
        try {
            System.out.print("Enter your Annual Income: ");
            annualIncome = scanner.nextDouble();
            System.out.print("Enter the Size of Expense List: ");
            size = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numeric values.");
            return;
        }

        ExpenseTracker4 tracker = new ExpenseTracker4(size, annualIncome);

        while (true) {
            try {
                System.out.println("1. Add Expense");
                System.out.println("2. Add Subscription Expense");
                System.out.println("3. Display Expenses");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter description: ");
                        String description = scanner.nextLine();
                        double amount;
                        do {
                            System.out.print("Enter amount: ");
                            amount = scanner.nextDouble();
                            if (amount < 0) {
                                System.out.println("Enter a Valid Amount!");
                            }
                        } while (amount < 0);
                        tracker.addExpense(description, amount);
                        break;
                    case 2:
                        System.out.print("Enter the Subscription Name: ");
                        description = scanner.nextLine();
                        do {
                            System.out.print("Enter the Subscription Price: ");
                            amount = scanner.nextDouble();
                            if (amount < 0) {
                                System.out.println("Enter a Valid Amount!");
                            }
                        } while (amount < 0);
                        System.out.print("Enter the Subscription Period in Months: ");
                        int totalMonths = scanner.nextInt();
                        tracker.addSubscriptionExpense(description, amount, totalMonths);
                        break;
                    case 3:
                        tracker.displayExpenses();
                        break;
                    case 4:
                        System.out.println("Exiting System.....");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter valid data.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}