package expense;

public class ExpenseTracker {
    private static class Expense {
        String description;
        double amount;
        int totalMonth;

        public Expense(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }

        public Expense(String subscriptionName, double amount, int totalMonth) {
            this.description = subscriptionName;
            this.amount = amount;
            this.totalMonth = totalMonth;
        }
    }

    private Expense[] expenses;
    private int count;
    private double totalSpent;
    private double remainingIncome;
    private static final String USERNAME = "sridhar";
    private static final String PASSWORD = "12345";

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

    void addExpense(String subscriptionName, int totalMonth, double amount) {
        if (count < expenses.length) {
            expenses[count] = new Expense(subscriptionName, amount, totalMonth);
            count++;
            System.out.println("Expense added: " + subscriptionName + " - ₹" + amount + " for " + totalMonth + " Months");
            totalSpent += amount * totalMonth;
        } else {
            System.out.println("Expense list is full!");
        }
    }

    public void displayExpenses() {
        System.out.println("All Expenses:");
        for (int i = 0; i < count; i++) {
            if (expenses[i].totalMonth > 0) {
                System.out.println(expenses[i].description + ": ₹" + expenses[i].amount + " for " + expenses[i].totalMonth + " months");
            } else {
                System.out.println(expenses[i].description + ": ₹" + expenses[i].amount);
            }
        }
        System.out.println("Total Spent: ₹" + totalSpent);
        remainingIncome -= totalSpent;
        System.out.println("Remaining Income Left: ₹" + remainingIncome);
        System.out.println();
    }
}
