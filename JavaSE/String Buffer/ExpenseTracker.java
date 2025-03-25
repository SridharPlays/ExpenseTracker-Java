import java.util.Scanner;

// Base Class
class Expense3 {
    String description;
    double amount;

    public Expense3(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public void displayDetails() {
        System.out.println(description + ": Rs. " + amount);
    }
}

// Subclass
class GeneralExpense extends Expense3 {
    public GeneralExpense(String description, double amount) {
        super(description, amount);
    }
}

// Subclass
class SubscriptionExpense extends Expense3 {
    int totalMonths;

    public SubscriptionExpense(String description, double amount, int totalMonths) {
        super(description, amount);
        this.totalMonths = totalMonths;
    }

    @Override
    public void displayDetails() {
        System.out.println(description + ": Rs. " + amount + " for " + totalMonths + " months");
    }
}

// Expense Tracker Class
public class ExpenseTracker3 {
    private Expense3[] expenses;
    private int count;
    private double totalSpent;
    private static double remainingIncome, annual;
    private static String Username = "sridhar";
    private static String Password = "12345";

    public ExpenseTracker3(int size, double annual) {
        expenses = new Expense3[size];
        count = 0;
        remainingIncome = annual;
        this.annual = annual;
    }

    // Add General Expense
    void addExpense(String description, double amount) {
        if (count < expenses.length) {
            expenses[count] = new GeneralExpense(description, amount);
            count++;
            System.out.println("Expense added: " + description + " - Rs. " + amount);
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
            System.out.println("Expense added: " + description + " - Rs. " + amount + " for " + totalMonths + " months");
            totalSpent += amount * totalMonths;
        } else {
            System.out.println("Expense list is full!");
        }
    }

    // Display Expenses
    public void displayExpenses() {
        System.out.println("");
        System.out.println("All Expenses: ");
        
        StringBuffer expenseSummary = new StringBuffer();
        for (int i = 0; i < count; i++) {
            expenseSummary.append(expenses[i].description).append(": Rs. ").append(expenses[i].amount);
            if (expenses[i] instanceof SubscriptionExpense) {
                expenseSummary.append(" for ").append(((SubscriptionExpense) expenses[i]).totalMonths).append(" months - " + (((SubscriptionExpense) expenses[i]).totalMonths * expenses[i].amount));
            }
            expenseSummary.append("\n");
        }

        System.out.print(expenseSummary.toString());
        System.out.println("Total Spent: Rs. " + totalSpent);

        remainingIncome = annual - totalSpent;
        System.out.println("Remaining Income Left: Rs. " + remainingIncome);
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String UserInputName, UserPassword;
        String description;
        double amount;
        int totalMonths;

        // Login System
        do {
            System.out.println("Enter Username: ");
            UserInputName = scanner.nextLine();
            System.out.println("Enter Password: ");
            UserPassword = scanner.nextLine();

            if (!UserInputName.equals(Username) || !UserPassword.equals(Password)) {
                System.out.println("Invalid Credentials! Try Again");
            }
        } while (!UserInputName.equals(Username) || !UserPassword.equals(Password));

        // Input Income and Expense List Size
        System.out.println("Enter your Annual Income: ");
        double annualIncome = scanner.nextDouble();
        System.out.println("Enter the Size of Expense List: ");
        int size = scanner.nextInt();
        scanner.nextLine();

        ExpenseTracker3 tracker = new ExpenseTracker3(size, annualIncome);

        while (true) {
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
                    description = scanner.nextLine();
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
                    totalMonths = scanner.nextInt();
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
        }
    }
}
