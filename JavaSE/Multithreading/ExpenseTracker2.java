import java.util.Scanner;

class Expense3 {
    String description;
    double amount;

    public Expense3(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public void displayDetails() {
        System.out.println(description + ": Rs." + amount);
    }
}

class GeneralExpense extends Expense3 {
    public GeneralExpense(String description, double amount) {
        super(description, amount);
    }
}

class SubscriptionExpense extends Expense3 {
    int totalMonths;

    public SubscriptionExpense(String description, double amount, int totalMonths) {
        super(description, amount);
        this.totalMonths = totalMonths;
    }

    @Override
    public void displayDetails() {
        System.out.println(description + ": Rs." + amount + " per month for " + totalMonths + " months");
    }
}

// Expense Tracker Class
class ExpenseTracker3 {
    private Expense3[] expenses;
    private int count;
    private double totalSpent;
    private double remainingIncome;
    private double annual;
    private static final String USERNAME = "sridhar";
    private static final String PASSWORD = "12345";

    public ExpenseTracker3(int size, double annual) {
        expenses = new Expense3[size];
        count = 0;
        this.annual = annual;
        this.remainingIncome = annual;
    }

    void addExpense(String description, double amount) {
        if (count < expenses.length) {
            expenses[count] = new GeneralExpense(description, amount);
            count++;
            totalSpent += amount;
            remainingIncome -= amount;
        } else {
            System.out.println("Expense list is full!");
        }
    }

    void addSubscriptionExpense(String description, double amount, int totalMonths) {
        if (count < expenses.length) {
            expenses[count] = new SubscriptionExpense(description, amount, totalMonths);
            count++;
            totalSpent += amount * totalMonths;
            remainingIncome -= amount * totalMonths;
        } else {
            System.out.println("Expense list is full!");
        }
    }

    public void displayExpenses() {
        System.out.println("\nAll Expenses:");
        for (int i = 0; i < count; i++) {
            expenses[i].displayDetails();
        }
        System.out.println("Total Spent: Rs. " + totalSpent);
        System.out.println("Remaining Income Left: Rs. " + remainingIncome);
        System.out.println();
    }

    public synchronized double getRemainingIncome() {
        return remainingIncome;
    }
}

// Monitor Class
class ExpenseMonitor extends Thread {
    private final ExpenseTracker3 tracker;
    private boolean running = true;

    public ExpenseMonitor(ExpenseTracker3 tracker) {
        this.tracker = tracker;
    }

    public void stopMonitoring() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("[Monitor] Remaining Income: Rs. " + tracker.getRemainingIncome());
            try {
                Thread.sleep(5000); 
            } catch (InterruptedException e) {
                System.out.println("Monitor interrupted");
            }
        }
    }
}

public class ExpenseTrackerMultithreading {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInputName, userPassword;

        do {
            System.out.print("Enter Username: ");
            userInputName = scanner.nextLine();
            System.out.print("Enter Password: ");
            userPassword = scanner.nextLine();

            if (!userInputName.equals("sridhar") || !userPassword.equals("12345")) {
                System.out.println("Invalid Credentials! Try Again");
            }
        } while (!userInputName.equals("sridhar") || !userPassword.equals("12345"));

        System.out.print("Enter your Annual Income: ");
        double annualIncome = scanner.nextDouble();
        System.out.print("Enter the Size of Expense List: ");
        int size = scanner.nextInt();
        scanner.nextLine();

        ExpenseTracker3 tracker = new ExpenseTracker3(size, annualIncome);
        ExpenseMonitor monitor = new ExpenseMonitor(tracker);
        monitor.start();

        double amount;
        
        while (true) {
            System.out.println("\n1. Add Expense");
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
                    do {
                        System.out.print("Enter amount: ");
                        amount = scanner.nextDouble();
                        if(amount <=0 ) {
                            System.out.println("Input Cannot be Negative");
                        }
                    } while (amount <= 0);
                    scanner.nextLine();
                    tracker.addExpense(description, amount);
                    break;
                case 2:
                    System.out.print("Enter the Subscription Name: ");
                    description = scanner.nextLine();
                    do { 
                        System.out.print("Enter the Subscription Price: ");
                        amount = scanner.nextDouble();
                        if(amount <=0 ) {
                            System.out.println("Input Cannot be Negative");
                        }
                    } while (amount <= 0);
                    System.out.print("Enter the Subscription Period in Months: ");
                    int totalMonths = scanner.nextInt();
                    scanner.nextLine();
                    tracker.addSubscriptionExpense(description, amount, totalMonths);
                    break;
                case 3:
                    tracker.displayExpenses();
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
