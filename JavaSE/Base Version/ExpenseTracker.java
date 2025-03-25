import java.util.Scanner;

class Expense {
    String description;
    double amount;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
}

public class ExpenseTracker {
    private Expense[] expenses;
    private int count;
    private double totalSpent;
    private static String Username = "sridhar";
    private static String Password = "12345";

    public ExpenseTracker(int size) {
        expenses = new Expense[size];
        count = 0;
    }

    public void addExpense(String description, double amount) {
        if (count < expenses.length) {
            expenses[count] = new Expense(description, amount);
            count++;
            System.out.println("Expense added: " + description + " - ₹" + amount);
            totalSpent += amount;
            
        } else {
            System.out.println("Expense list is full!");
        }
    }

    public void displayExpenses() {
        System.out.println("All Expenses:");
        for (int i = 0; i < count; i++) {
            System.out.println(expenses[i].description + ": ₹" + expenses[i].amount);
        }
        System.out.println("Total Spent: ₹" +  totalSpent);
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker(5);
        Scanner scanner = new Scanner(System.in);
        
        String UserInputName, UserPassword;
        
        do {
        	System.out.println("Enter Username: ");
        	UserInputName = scanner.nextLine();
        	System.out.println("Enter Password: ");
        	UserPassword = scanner.nextLine();
        	
        	if (!UserInputName.equals(Username) || !UserPassword.equals(Password)) 
        	{
        		System.out.println("Invalid Credentials! Try Again");
        	}
        } while(!UserInputName.equals(Username) || !UserPassword.equals(Password));
        
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. Display Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    tracker.addExpense(description, amount);
                    break;
                case 2:
                    tracker.displayExpenses();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}