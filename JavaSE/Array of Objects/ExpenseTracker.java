package bca;

import java.util.Scanner;

class Expense {
    String description;
    double amount;
    int totalMonth;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
    public Expense(String SubscriptionName, double amount,int totalMonth) {
    	this.amount = amount;
    	this.totalMonth = totalMonth;
    	description = SubscriptionName;
    }
}

public class ExpenseTracker {
    private Expense[] expenses;
    private int count;
    private double totalSpent;
	private static double remainingIncome;
    private static String Username = "sridhar";
    private static String Password = "12345";

    public ExpenseTracker(int size, double annual) {
        expenses = new Expense[size];
        count = 0;
        remainingIncome = annual;
    }

    void addExpense(String description, double amount) {
        if (count < expenses.length) {
            expenses[count] = new Expense(description, amount);
            count++;
            System.out.println("Expense added: " + description + " - ₹" + amount);
            totalSpent += amount;
        } else {
            System.out.println("Expense list is full!");
        }
    }
    
    void addExpense(String SubscriptionName, int totalMonth, double amount) {
    	if(count < expenses.length) {
    		expenses[count] = new Expense(SubscriptionName, amount, totalMonth);
    		count++;
    		System.out.println("Expense added: " + SubscriptionName + " - ₹" + amount + " for " + totalMonth + " Months");
            totalSpent += amount * totalMonth;
    	}
    }

    public void displayExpenses() {
        System.out.println("All Expenses: ");
        for (int i = 0; i < count; i++) {
            System.out.println(expenses[i].description + ": ₹" + expenses[i].amount);
        }
        System.out.println("Total Spent: ₹" +  totalSpent);
        remainingIncome = remainingIncome - totalSpent;
        System.out.println("Remaining Income Left: ₹" +  remainingIncome);
    	System.out.println();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String UserInputName, UserPassword;
        String description, subscriptionName;
        double amount;
        int totalMonth;
        
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
        
        System.out.println("Enter your Annual Income: ");
        double annualIncome = scanner.nextDouble();
        System.out.println("Enter the Size of Expense List: ");
        int size = scanner.nextInt();
        
        ExpenseTracker tracker = new ExpenseTracker(size,annualIncome);
        
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
                    System.out.print("Enter amount: ");
                    amount = scanner.nextDouble();
                    tracker.addExpense(description, amount);
                    break;
                case 2:
                	System.out.print("Enter the Subscription Name: ");
                    subscriptionName = scanner.nextLine();
                    System.out.print("Enter the Subscription Price: ");
                    amount = scanner.nextDouble();
                    System.out.print("Enter the Subscription Periods in Month: ");
                    totalMonth = scanner.nextInt();
                    tracker.addExpense(subscriptionName, totalMonth, amount);
                	break;
                case 3:
                    tracker.displayExpenses();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}