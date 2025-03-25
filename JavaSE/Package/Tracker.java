package expense;
import expense.ExpenseTracker;
import java.util.Scanner;

public class Tracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        String description, subscriptionName;
        double amount;
        int totalMonth;
    
        System.out.println("Enter your Annual Income: ");
        double annualIncome = scanner.nextDouble();
        System.out.println("Enter the Size of Expense List: ");
        int size = scanner.nextInt();
    
        ExpenseTracker tracker = new ExpenseTracker(size, annualIncome);
        scanner.nextLine(); // Consume newline
    
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. Add Subscription Expense");
            System.out.println("3. Display Expenses");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    description = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    amount = scanner.nextDouble();
                    tracker.addExpense(description, amount);
                    scanner.nextLine(); // Consume newline
                    break;
                case 2:
                    System.out.print("Enter the Subscription Name: ");
                    subscriptionName = scanner.nextLine();
                    System.out.print("Enter the Subscription Price: ");
                    amount = scanner.nextDouble();
                    System.out.print("Enter the Subscription Periods in Months: ");
                    totalMonth = scanner.nextInt();
                    tracker.addExpense(subscriptionName, totalMonth, amount);
                    scanner.nextLine(); // Consume newline
                    break;
                case 3:
                    tracker.displayExpenses();
                    break;
                case 4:
                    System.out.println("Exiting System...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
