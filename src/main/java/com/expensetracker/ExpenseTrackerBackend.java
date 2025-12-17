package com.expensetracker;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExpenseTrackerBackend {
    private final List<Expense> expenses = new ArrayList<>();
    private final String[] categories = {"🥘 Food", "🚌 Travel", "🏠 Rent", "📱 Utilities", "🎉 Entertainment", "🛒 Shopping", "💊 Health"};

    public static class Expense {
        String date;
        double amount;
        String category;

        public Expense(String date, double amount, String category) {
            this.date = date;
            this.amount = amount;
            this.category = category;
        }

        @Override
        public String toString() {
            return date + ",₹" + amount + "," + category;
        }
    }

    public void addExpense(double amount, String category) {
        if (!Arrays.asList(categories).contains(category)) {
            throw new IllegalArgumentException("Invalid category");
        }
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        expenses.add(new Expense(date, amount, category));
    }

    public double calculateTotal() {
        double total = 0;
        for (Expense exp : expenses) {
            total += exp.amount;
        }
        return total;
    }

    public void saveExpensesToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Date,Amount,Category");
            for (Expense exp : expenses) {
                writer.println(exp.date + ",₹" + exp.amount + "," + exp.category);
            }
        }
    }

    public String generateMonthlyReport() {
        Map<String, Integer> countMap = new HashMap<>();
        Map<String, Double> totalMap = new HashMap<>();

        for (String cat : categories) {
            countMap.put(cat, 0);
            totalMap.put(cat, 0.0);
        }

        for (Expense exp : expenses) {
            countMap.put(exp.category, countMap.get(exp.category) + 1);
            totalMap.put(exp.category, totalMap.get(exp.category) + exp.amount);
        }

        StringBuilder report = new StringBuilder("Monthly Report:\n");
        for (String cat : categories) {
            int count = countMap.get(cat);
            double total = totalMap.get(cat);
            if (count > 0) {
                report.append(cat)
                      .append(": ₹").append(total)
                      .append(" (").append(count).append(" entries)\n");
            }
        }
        return report.toString();
    }

    public void addSampleData() {
        expenses.add(new Expense("01-07-2025", 200, "🥘 Food"));
        expenses.add(new Expense("02-07-2025", 150, "🚌 Travel"));
        expenses.add(new Expense("03-07-2025", 5000, "🏠 Rent"));
        expenses.add(new Expense("04-07-2025", 320, "📱 Utilities"));
        expenses.add(new Expense("05-07-2025", 600, "🎉 Entertainment"));
        expenses.add(new Expense("06-07-2025", 700, "🛒 Shopping"));
        expenses.add(new Expense("07-07-2025", 120, "💊 Health"));
    }

    public static void main(String[] args) throws IOException {
        ExpenseTrackerBackend tracker = new ExpenseTrackerBackend();
        tracker.addSampleData();

        tracker.addExpense(350, "🥘 Food");
        System.out.println("Total Expenses: ₹" + tracker.calculateTotal());

        tracker.saveExpensesToFile("expenses.csv");
        System.out.println(tracker.generateMonthlyReport());
    }
}
