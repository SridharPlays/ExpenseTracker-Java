import java.util.*;

class Expense {
    String description;
    double amount;
    String category;

    public Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public String toString() {
        return description + " - $" + amount;
    }
}

class SubscriptionExpense extends Expense {
    int months;
    double amount;
    public SubscriptionExpense(String description, double amount, int months) {
        this.amount = amount * months;
        super(description, this.amount, "Subscription");
        this.months = months;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + months + " months";
    }
}

class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();
    private final double annualIncome;

    public ExpenseManager(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public void addExpense(String description, double amount) {
        expenses.add(new Expense(description, amount, "General"));
        System.out.println("Expense added successfully!");
    }

    public void addSubscriptionExpense(String description, double amount, int months) {
        expenses.add(new SubscriptionExpense(description, amount, months));
        System.out.println("Subscription expense added successfully!");
    }

    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\nAll Expenses:");
        expenses.forEach(System.out::println);
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(exp -> exp.amount).sum();
    }

    public double getAnnualIncome() {
        return annualIncome;
    }
}

class ExpenseMonitor extends Thread {
    private final ExpenseManager expenseManager;
    private boolean running = true;

    public ExpenseMonitor(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    @Override
    public void run() {
        while (running) {
            double totalExpenses = expenseManager.getTotalExpenses();
            if (totalExpenses > expenseManager.getAnnualIncome() / 2) {
                System.out.println("\n Warning: You've spent more than half of your annual income!");
                System.out.println("You Spent - " + totalExpenses);
            }
            try {
                Thread.sleep(5000);
                System.out.println("\nYou Spent - " + totalExpenses);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopMonitoring() {
        running = false;
    }
}

public class ExpenseTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User Authentication
        String username, password;
        do {
            System.out.print("Enter Username: ");
            username = scanner.nextLine();
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (!username.equals("sridhar") || !password.equals("12345")) {
                System.out.println("Invalid Credentials! Try Again.");
            }
        } while (!username.equals("sridhar") || !password.equals("12345"));

        System.out.print("Enter your Annual Income: ");
        double annualIncome = scanner.nextDouble();
        scanner.nextLine();

        ExpenseManager expenseManager = new ExpenseManager(annualIncome);
        ExpenseMonitor monitor = new ExpenseMonitor(expenseManager);
        monitor.start();

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
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
                    String desc = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amt = scanner.nextDouble();
                    scanner.nextLine();
                    expenseManager.addExpense(desc, amt);
                    break;
                case 2:
                    System.out.print("Enter Subscription Name: ");
                    String subName = scanner.nextLine();
                    System.out.print("Enter Subscription Price: ");
                    double subPrice = scanner.nextDouble();
                    System.out.print("Enter Subscription Period in Months: ");
                    int months = scanner.nextInt();
                    scanner.nextLine();
                    expenseManager.addSubscriptionExpense(subName, subPrice, months);
                    break;
                case 3:
                    expenseManager.displayExpenses();
                    break;
                case 4:
                    System.out.println("Exiting System...");
                    monitor.stopMonitoring();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
