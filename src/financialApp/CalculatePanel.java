package financialApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculatePanel extends JPanel {
    private JPanel calculationPanel, savingsDisplay, btnPanel, mortgageBtnDisplay;
    private ArrayList<CreditCard> creditCards;
    private SavingsAccount savings;
    private Mortgage mortgage;
    private JTextArea display;
    private JLabel accountLabel,depOrWith,yearLabel;
    private JTextField accountNum = new JTextField();
    private JTextField years = new JTextField();
    private JTextField transaction = new JTextField();

    public CalculatePanel(){
        setLayout(new BorderLayout());

        display = new JTextArea();

        btnPanel = createDisplay();

        add(btnPanel, BorderLayout.NORTH);
        add(display, BorderLayout.CENTER);
    }

    private JPanel createDisplay() {
        calculationPanel = new JPanel();

        JButton simMortgagePayment = new JButton("Simulate Mortgage payments");
        simMortgagePayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                makePayment();
                add(btnPanel, BorderLayout.NORTH);
                add(display, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        JButton calcBalance = new JButton("Balances");
        calcBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                calculateBalances();
                add(btnPanel, BorderLayout.NORTH);
                add(display, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        JButton savingsTransactions = new JButton("Simulate basic transactions");
        savingsTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                transactionSimulation();
                add(savingsDisplay, BorderLayout.WEST);
                add(btnPanel, BorderLayout.NORTH);
                add(display, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        calculationPanel.add(calcBalance);
        calculationPanel.add(simMortgagePayment);
        calculationPanel.add(savingsTransactions);

        return calculationPanel;
    }

    private void transactionSimulation() {
        savingsDisplay = new JPanel();
        savingsDisplay.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        accountLabel = new JLabel("Account Number: ");
        depOrWith = new JLabel("Transaction amount: ");

        Dimension dim = new Dimension(100,20);

        accountNum.setPreferredSize(dim);

        transaction.setPreferredSize(dim);

        JButton deposit = new JButton("Deposit");
        deposit.addActionListener(transactionListener(deposit));

        JButton withdraw = new JButton("Withdraw");
        withdraw.addActionListener(transactionListener(withdraw));

        display.setText("");

        // first row
        gbc.gridx = 0;
        gbc.gridy = 0;
        savingsDisplay.add(accountLabel, gbc);
        gbc.gridy++;
        savingsDisplay.add(depOrWith, gbc);
        gbc.gridy++;
        savingsDisplay.add(deposit, gbc);

        // second row
        gbc.gridx = 1;
        gbc.gridy = 0;
        savingsDisplay.add(accountNum, gbc);
        gbc.gridy++;
        savingsDisplay.add(transaction, gbc);
        gbc.gridy++;
        savingsDisplay.add(withdraw, gbc);
    }

    private ActionListener transactionListener(JButton btn) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FinancialApp.savingsAccount != null){
                    savings = FinancialApp.savingsAccount;
                } else {
                    savings = UserPanel.getSavings();
                }

                if (savings == null) {
                    JOptionPane.showMessageDialog(null, "No savings accounts available");
                } else if (accountNum.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Account number is required");
                } else if (!accountNum.getText().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null,
                            "Account number can only contain whole numbers");
                } else if (transaction.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Transaction amount is required");
                } else if (!transaction.getText().matches("\\d*.?\\d+?")){
                    JOptionPane.showMessageDialog(null, "Transaction must be numeric");
                }
                else {
                    if (Integer.parseInt(accountNum.getText()) != savings.getAccountNumber())
                        JOptionPane.showMessageDialog(null, "Invalid Account");
                    else {

                        switch (btn.getText()) {
                            case "Deposit":
                                savings.deposit(Double.parseDouble(transaction.getText()));
                                display.setText("Deposit successful\n" +
                                        "-------------\n");
                                display.append("New " + savings.printBalance());
                                break;
                            case "Withdraw":
                                double withdraw = Double.parseDouble(transaction.getText());

                                if (savings.getBalance() - withdraw < 0) {
                                    JOptionPane.showMessageDialog(null, "You don't have enough to withdraw");
                                } else {
                                    savings.withdraw(withdraw);
                                    display.setText("Withdraw successful\n" +
                                            "------------\n");
                                    display.append("New " + savings.printBalance());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        };
    }

    private void makePayment() {
        mortgageBtnDisplay = new JPanel();
        mortgageBtnDisplay.setLayout(new GridBagLayout());
        display.setText("");

        yearLabel = new JLabel("Years:");

        years.setPreferredSize(new Dimension(100,20));

        JButton calcButton = new JButton("Calculate");
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FinancialApp.mortgage != null){
                    mortgage = FinancialApp.mortgage;
                } else {
                    mortgage = UserPanel.getMortgage();
                }

                if (mortgage == null){
                    JOptionPane.showMessageDialog(null,
                            "Mortgage needs to added before calculation");
                } else {
                    int calcYears;

                    if (years.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please enter number of years");
                    } else if (!years.getText().matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Years must be an Integer");
                    } else {
                        calcYears = Integer.parseInt(years.getText());

                        if (calcYears <= 0 || calcYears > mortgage.getYears()) {
                            JOptionPane.showMessageDialog(null, "Invalid entry\n" +
                                    "Enter years from 1-"+mortgage.getYears());
                        } else {
                            display.append(String.format("New Mortgage Balance after %s year%s: %.2f\n",
                                    years.getText(),
                                    calcYears > 1 ? "'s" : "",
                                    mortgage.balance(calcYears)));
                        }
                    }
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        mortgageBtnDisplay.add(yearLabel, gbc);
        gbc.gridx++;
        mortgageBtnDisplay.add(years,gbc);
        gbc.gridy++;
        mortgageBtnDisplay.add(calcButton, gbc);

        add(mortgageBtnDisplay, BorderLayout.WEST);
    }

    private void calculateBalances(){
        if (FinancialApp.mortgage != null){
            mortgage = FinancialApp.mortgage;
        } else {
            mortgage = UserPanel.getMortgage();
        }

        if (FinancialApp.savingsAccount != null){
            savings = FinancialApp.savingsAccount;
        } else {
            savings = UserPanel.getSavings();
        }

        double total = 0;
        creditCards = UserPanel.getCreditCards();
        display.setText("");

        for (CreditCard cc:
                creditCards) {
            display.append(cc.getLender() + ": ");
            display.append(Double.toString(cc.getBalance()) + "\n");
            total += cc.getBalance();
        }

        if (mortgage != null) {
            display.append(String.format("Mortgage: %.2f\n", mortgage.getBalance()));
            total += mortgage.getBalance();
        }

        if (savings != null) {
            display.append(String.format("Savings: %.2f\n", savings.getBalance()));
            total += savings.getBalance();
        }

        display.append(String.format("Total balances: %.2f\n", total));
    }
}