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
        return "Expense { " + "description= '" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' + '}';
    }
}

public class ExpenseTracker5 {
    private List<Expense> expenses = new ArrayList<>();
    private Map<String, List<Expense>> categoryMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    double amount;
    
    public void addExpense() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        do {
            System.out.print("Enter amount: ");
            amount = scanner.nextDouble();
            if(amount <=0 ) {
                System.out.println("Input cannot be Negative");
            }
        } while(amount <= 0);

        scanner.nextLine();
        
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        
        Expense newExpense = new Expense(description, amount, category);
        expenses.add(newExpense);
        
        categoryMap.putIfAbsent(category, new ArrayList<>());
        categoryMap.get(category).add(newExpense);
        
        System.out.println("Expense added successfully!");
    }

    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    public void displayByCategory() {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        List<Expense> categoryExpenses = categoryMap.getOrDefault(category, new ArrayList<>());
        
        if (categoryExpenses.isEmpty()) {
            System.out.println("No expenses found in this category.");
        } else {
            for (Expense expense : categoryExpenses) {
                System.out.println(expense);
            }
        }
    }

    public void sortExpensesByAmount() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        expenses.sort(Comparator.comparingDouble(exp -> exp.amount));
        System.out.println("Expenses sorted by amount.");
        displayExpenses();
    }

    public void menu() {
        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. Display All Expenses");
            System.out.println("3. Display Expenses by Category");
            System.out.println("4. Sort Expenses by Amount");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    displayExpenses();
                    break;
                case 3:
                    displayByCategory();
                    break;
                case 4:
                    sortExpensesByAmount();
                    break;
                case 5:
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ExpenseTracker5 tracker = new ExpenseTracker5();
        tracker.menu();
    }
}
