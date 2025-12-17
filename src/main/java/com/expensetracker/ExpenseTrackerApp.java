package com.expensetracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ExpenseTrackerApp extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField amountField;
    private JComboBox<String> categoryBox;
    private JLabel totalLabel;
    private boolean darkMode = false;

    private final String[] categories = {
            "🍔 Food", "🚌 Travel", "🏠 Rent",
            "📱 Utilities", "🎉 Entertainment",
            "🛒 Shopping", "💊 Health"
    };

    public ExpenseTrackerApp() {
        setTitle("💰 Personal Expense Tracker");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel root = new BackgroundPanel();
        root.setLayout(new BorderLayout(15, 15));
        setContentPane(root);

        /* ================= TOP ================= */

        JPanel top = glassPanel();
        amountField = new JTextField(10);
        categoryBox = new JComboBox<>(categories);

        JButton addBtn = styledButton("➕ Add");
        JButton saveBtn = styledButton("💾 Save CSV");
        JButton reportBtn = styledButton("📊 Final Report");
        JButton modeBtn = styledButton("🌓 Dark / Light");

        top.add(label("Amount ₹"));
        top.add(amountField);
        top.add(label("Category"));
        top.add(categoryBox);
        top.add(addBtn);
        top.add(saveBtn);
        top.add(reportBtn);
        top.add(modeBtn);

        /* ================= TABLE ================= */

        model = new DefaultTableModel(
                new String[]{"📅 Date", "💵 Amount", "📌 Category"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setOpaque(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        /* ================= BOTTOM ================= */

        JPanel bottom = glassPanel();
        totalLabel = new JLabel("Total: ₹0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        bottom.add(totalLabel);

        root.add(top, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        /* ================= ACTIONS ================= */

        addBtn.addActionListener(e -> addExpense());
        saveBtn.addActionListener(e -> saveCSV());
        reportBtn.addActionListener(e -> showFinalReport());
        modeBtn.addActionListener(e -> toggleMode());

        loadCSV();
        applyTheme();
        setVisible(true);
    }

    /* ================= UI HELPERS ================= */

    private JPanel glassPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 80)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return p;
    }

    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(Color.WHITE);
        return l;
    }

    /* ================= LOGIC ================= */

    private void addExpense() {
        try {
            double amt = Double.parseDouble(amountField.getText());
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            model.addRow(new Object[]{date, "₹" + amt, categoryBox.getSelectedItem()});
            amountField.setText("");
            updateTotal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid amount");
        }
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += Double.parseDouble(
                    model.getValueAt(i, 1).toString().replace("₹", "")
            );
        }
        totalLabel.setText("Total: ₹" + total);
    }

    /* ================= FINAL REPORT ================= */

    private void showFinalReport() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No data available");
            return;
        }

        double[] totals = new double[categories.length];
        int[] counts = new int[categories.length];
        double grandTotal = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            String cat = model.getValueAt(i, 2).toString();
            double amt = Double.parseDouble(
                    model.getValueAt(i, 1).toString().replace("₹", "")
            );
            grandTotal += amt;

            for (int j = 0; j < categories.length; j++) {
                if (cat.equals(categories[j])) {
                    totals[j] += amt;
                    counts[j]++;
                }
            }
        }

        StringBuilder report = new StringBuilder("FINAL EXPENSE REPORT\n\n");
        for (int i = 0; i < categories.length; i++) {
            if (counts[i] > 0) {
                report.append(categories[i])
                        .append(" : ₹").append(totals[i])
                        .append(" (").append(counts[i]).append(" entries)\n");
            }
        }
        report.append("\n-------------------------\nTOTAL SPENT : ₹").append(grandTotal);

        JOptionPane.showMessageDialog(this, report.toString());
    }

    /* ================= CSV ================= */

    private void saveCSV() {
        try (PrintWriter pw = new PrintWriter("expenses.csv")) {
            pw.println("Date,Amount,Category");
            for (int i = 0; i < model.getRowCount(); i++) {
                pw.println(
                        model.getValueAt(i, 0) + "," +
                        model.getValueAt(i, 1) + "," +
                        model.getValueAt(i, 2)
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving CSV");
        }
    }

    private void loadCSV() {
        File f = new File("expenses.csv");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                model.addRow(line.split(","));
            }
            updateTotal();
        } catch (Exception ignored) {}
    }

    /* ================= THEME ================= */

    private void toggleMode() {
        darkMode = !darkMode;
        applyTheme();
    }

    private void applyTheme() {
        if (darkMode) {
            table.setForeground(Color.WHITE);
            table.getTableHeader().setBackground(Color.BLACK);
            table.getTableHeader().setForeground(Color.WHITE);
            totalLabel.setForeground(Color.WHITE);
        } else {
            table.setForeground(Color.BLACK);
            table.getTableHeader().setBackground(new Color(230,230,230));
            table.getTableHeader().setForeground(Color.BLACK);
            totalLabel.setForeground(Color.WHITE);
        }
        repaint();
    }

    /* ================= BACKGROUND PANEL ================= */

    static class BackgroundPanel extends JPanel {
        private final Image bgImage;

        public BackgroundPanel() {
            bgImage = new ImageIcon(
                    Objects.requireNonNull(
                            getClass().getResource("/images/bg.jpg")
                    )
            ).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTrackerApp::new);
    }
}
