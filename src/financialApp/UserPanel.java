package financialApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * CLASS UserPanel - Allows for adding new financial items (Bank Accounts, Credit Cards, and Mortgages)
 * @author Ricky Mendez, Issac Olsen, Tosh Roberts, Zachary Tucker
 */
public class UserPanel extends JPanel {

    // fields for the User Panel
    private JRadioButton creditCardBtn, mortgageBtn, bankAccountBtn;
    private ButtonGroup btnGroup = new ButtonGroup();
    private JButton submitBtn, editBtn;
    private JTextArea display = new JTextArea();
    private JPanel btnPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private JTextField accountNumber = new JTextField(),
            accBalance = new JTextField(),
            creditBalance = new JTextField(),
            lender = new JTextField(),
            minPayment = new JTextField(),
            accId = new JTextField(),
            mortgageLoan = new JTextField(),
            mortgageYear = new JTextField(),
            mortgageRate = new JTextField(),
            interestRate = new JTextField();
    private JLabel accountLabel,
            accBalanceLabel,
            creditBalanceLabel,
            accIdLabel,
            lenderLabel,
            minPaymentLabel,
            interestRateLabel,
            mortgageLoanLabel,
            mortgageYearLabel;
    private String pattern = "\\d*.?\\d+?";

    private static ArrayList<CreditCard> creditCards = new ArrayList<>();
    public static SavingsAccount savings;
    public static Mortgage mortgage;


    /**
     * CONSTRUCTOR Builds the button and display panels
     */
    public UserPanel() {
        // Set the layout for the main UserPanel
        setLayout(new BorderLayout());

        // Set the layout for the display panel. Using GridBagLayout for better sizing and placement of components
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridBagLayout());

        // Add the other panels and components to the main panel
        add(createBtnPanel(), BorderLayout.NORTH);
        add(displayPanel, BorderLayout.WEST);
        add(display, BorderLayout.CENTER);

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
    }

    /**
     * METHOD createBtnPanel - Creates the buttons that selects each item to add
     * @return JPanel
     */
    private JPanel createBtnPanel() {

        // Using the grid layout to have the buttons displayed in a single row
        btnPanel.setLayout(new GridLayout(1,2));

        // Create the labels for the credit card display to put next to the corresponding text field
        accountLabel = new JLabel("Account:");
        accBalanceLabel = new JLabel("Initial Balance:");
        creditBalanceLabel = new JLabel("Balance:");
        lenderLabel = new JLabel("Lender:");
        minPaymentLabel = new JLabel("Minimum Payment:");
        interestRateLabel = new JLabel("Interest Rate:");
        accIdLabel = new JLabel("(Edit only) Id:");

        // Create the labels to display in the mortgage display. Reuses interestRateLabel
        mortgageYearLabel = new JLabel("Years:");
        mortgageLoanLabel = new JLabel("Loan Amount:");

        // Set the size of the text fields using a single instance of Dimension
        Dimension dim = new Dimension(100,20);
        lender.setPreferredSize(dim);
        creditBalance.setPreferredSize(dim);
        minPayment.setPreferredSize(dim);
        interestRate.setPreferredSize(dim);
        accId.setPreferredSize(dim);

        // Mortgage text fields
        mortgageLoan.setPreferredSize(dim);
        mortgageYear.setPreferredSize(dim);
        mortgageRate.setPreferredSize(dim);

        // Savings Account text fields
        accountNumber.setPreferredSize(dim);
        accBalance.setPreferredSize(dim);

        // Create the GridBagConstraints to place each component on the display panel
        GridBagConstraints gbc = new GridBagConstraints();

        // Credit Card Button setup
        creditCardBtn = new JRadioButton("Add Credit Card");
        creditCardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.removeAll();

                // first column
                gbc.gridx = 0;
                gbc.gridy = 0;
                displayPanel.add(accIdLabel,gbc);
                gbc.gridy++;
                displayPanel.add(lenderLabel, gbc);
                gbc.gridy++;
                displayPanel.add(creditBalanceLabel, gbc);
                gbc.gridy++;
                displayPanel.add(minPaymentLabel, gbc);
                gbc.gridy++;
                displayPanel.add(interestRateLabel, gbc);
                gbc.gridy++;
                displayPanel.add(createEditButton(),gbc);

                // second column
                gbc.gridx = 1;
                gbc.gridy = 0;
                displayPanel.add(accId, gbc);
                gbc.gridy++;
                displayPanel.add(lender, gbc);
                gbc.gridy++;
                displayPanel.add(creditBalance,gbc);
                gbc.gridy++;
                displayPanel.add(minPayment,gbc);
                gbc.gridy++;
                displayPanel.add(interestRate,gbc);
                gbc.gridy++;
                displayPanel.add(createSubmitBtn(), gbc);

                revalidate();
                repaint();
            }
        });

        // Create the Mortgage button
        mortgageBtn = new JRadioButton("Add Mortgage");
        mortgageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.removeAll();

                gbc.gridx = 0;
                gbc.gridy = 0;

                // first column
                displayPanel.add(mortgageYearLabel,gbc);
                gbc.gridy++;
                displayPanel.add(mortgageLoanLabel, gbc);
                gbc.gridy++;
                displayPanel.add(interestRateLabel, gbc);
                gbc.gridy++;
                displayPanel.add(createEditButton(),gbc);

                // second column
                gbc.gridx = 1;
                gbc.gridy = 0;
                displayPanel.add(mortgageYear, gbc);
                gbc.gridy++;
                displayPanel.add(mortgageLoan, gbc);
                gbc.gridy++;
                displayPanel.add(mortgageRate,gbc);
                gbc.gridy++;
                displayPanel.add(createSubmitBtn(), gbc);

                revalidate();
                repaint();
            }
        });

        // Create the Savings Account button
        bankAccountBtn = new JRadioButton("Savings Account");
        bankAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.removeAll();

                gbc.gridx = 0;
                gbc.gridy = 0;

                // first column
                displayPanel.add(accountLabel,gbc);
                gbc.gridy++;
                displayPanel.add(accBalanceLabel, gbc);
                gbc.gridy++;

                // second column
                gbc.gridx = 1;
                gbc.gridy = 0;
                displayPanel.add(accountNumber, gbc);
                gbc.gridy++;
                displayPanel.add(accBalance, gbc);
                gbc.gridy++;
                displayPanel.add(createSubmitBtn(), gbc);

                revalidate();
                repaint();
            }
        });

        /*
        Because we are using radio button to switch items and only one should be active at a time they are added to
        a button group
        */
        btnGroup.add(creditCardBtn);
        btnGroup.add(mortgageBtn);
        btnGroup.add(bankAccountBtn);

        // add the buttons to the button panel and return the button panel
        btnPanel.add(creditCardBtn, 0);
        btnPanel.add(mortgageBtn, 1);
        btnPanel.add(bankAccountBtn, 2);
        return btnPanel;
    }

    private JButton createEditButton() {
        editBtn = new JButton("Edit");
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (creditCardBtn.isSelected()) {
                    if (accId.getText().isEmpty() || accId == null){
                        JOptionPane.showMessageDialog(null, "Id is required");
                    } else {
                        int id = Integer.parseInt(accId.getText());
                        String balance = creditBalance.getText();
                        String min = minPayment.getText();
                        String interest = interestRate.getText();
                        String lndr = lender.getText();

                        for (CreditCard c :
                                creditCards) {
                            if (c.getId() == id) {
                                if (!interest.isEmpty())
                                    c.setInterestRate(Double.parseDouble(interest));

                                if (!balance.isEmpty())
                                    c.setBalance(Double.parseDouble(balance));

                                if (!min.isEmpty())
                                    c.setMinimumPay(Double.parseDouble(min));

                                if (!lndr.isEmpty())
                                    c.setLender(lndr);

                                display.setText("Updated: \n");
                                display.append(c.toString());
                            }
                        }
                    }
                }

                if (mortgageBtn.isSelected()){
                    String year = mortgageYear.getText();
                    String loan = mortgageLoan.getText();
                    String rate = mortgageRate.getText();

                    if (mortgage != null){
                        if (!year.isEmpty())
                            mortgage.setYears(Integer.parseInt(year));

                        if (!loan.isEmpty())
                            mortgage.setLoan(Double.parseDouble(loan));

                        if (!rate.isEmpty())
                            mortgage.setRate(Double.parseDouble(rate));

                        display.setText("Updated: \n");
                        display.append(mortgage.toString());
                    }
                }
            }
        });

        return editBtn;
    }

    /**
     * METHOD createSubmitBtn - There is only one button that controls the adding function of each item.
     * This selects the correct option based on what radio button is selected.
     * @return JButton
     */
    public JButton createSubmitBtn (){
        submitBtn = new JButton("Add");
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // call the corresponding class add method
                if (creditCardBtn.isSelected()) {
                    // add credit card
                    CreditCard cc = null;
                    String balance = creditBalance.getText();
                    String min = minPayment.getText();
                    String interest = interestRate.getText();
                    String lndr = lender.getText();
                    int counter = creditCards.size()+1;

                    if (lndr.isEmpty() || lndr == ""){
                        JOptionPane.showMessageDialog(null,"Lender is required");
                    } else if (balance.isEmpty()) {
                        cc = new CreditCard(counter, lndr);
                    } else if (min.isEmpty() && interest.isEmpty()){
                        if (balance.matches(pattern)) {
                            cc = new CreditCard(counter, lndr, Double.parseDouble(balance));
                        } else {
                            JOptionPane.showMessageDialog(null, "Balance must be numeric");
                        }
                    } else if (interest.isEmpty()) {
                        if (balance.matches(pattern) && min.matches(pattern)) {
                            cc = new CreditCard(counter, lndr, Double.parseDouble(balance), Double.parseDouble(min));
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Balance and Minimum Payment must be numeric");
                        }
                    } else if (min.isEmpty() && !interest.isEmpty()){
                        if (balance.matches(pattern) && interest.matches(pattern)) {
                            cc = new CreditCard(counter,
                                    lndr,
                                    Double.parseDouble(balance),
                                    0,
                                    Double.parseDouble(interest));
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Balance and Interest Rate must be numeric");
                        }
                    } else {
                        if (balance.matches(pattern)
                                && min.matches(pattern)
                                && interest.matches(pattern)) {
                            cc = new CreditCard(counter,
                                    lndr,
                                    Double.parseDouble(balance),
                                    Double.parseDouble(min),
                                    Double.parseDouble(interest));
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Balance, Minimum Payment, and Interest Rate must be numeric");
                        }
                    }

                    if (cc == null){
                        JOptionPane.showMessageDialog(null, "Could not add Credit Card");
                    } else {
                        display.append(cc.toString());
                        creditCards.add(cc);
                    }
                }

                // Create the mortgage
                if (mortgageBtn.isSelected()){

                    if (FinancialApp.mortgage != null) {
                        mortgage = FinancialApp.mortgage;
                    }

                    if (mortgage != null){
                        display.setText(mortgage.toString());
                        JOptionPane.showMessageDialog(null, "Mortgage already exists");
                    } else {
                        String year = mortgageYear.getText();
                        String loan = mortgageLoan.getText();
                        String rate = mortgageRate.getText();

                        if (year.isEmpty() || loan.isEmpty() || rate.isEmpty()){
                            JOptionPane.showMessageDialog(null, "All Fields are required");
                        } else {
                            if (!year.matches("\\d{2}")){
                                JOptionPane.showMessageDialog(null,
                                        "Invalid Year Term ex:15 or 30");
                            } else if (!rate.matches(pattern) || !loan.matches(pattern)){
                                JOptionPane.showMessageDialog(null,
                                        "Rate and Loan must be numeric");
                            } else {
                                mortgage = new Mortgage(
                                        Integer.parseInt(year),
                                        Double.parseDouble(rate),
                                        Double.parseDouble(loan)
                                );
                                display.append(mortgage.toString());
                            }
                        }
                    }
                }

                // Create the Savings Accounts
                if (bankAccountBtn.isSelected()){
                    if (FinancialApp.savingsAccount != null) {
                        savings = FinancialApp.savingsAccount;
                    }

                    if (savings != null){
                        display.append(savings.toString());
                        JOptionPane.showMessageDialog(null,
                                "Savings account was already created");
                    } else {
                        String accNum = accountNumber.getText();
                        String bal = accBalance.getText();

                        if (accNum.isEmpty() || bal.isEmpty()){
                            JOptionPane.showMessageDialog(null,"All fields are required");
                        } else if (!accNum.matches("\\d+")){
                            JOptionPane.showMessageDialog(null,
                                    "Account number can only contain whole numbers");
                        }
                        else {
                            if (bal.matches(pattern)) {
                                savings = new SavingsAccount(Integer.parseInt(accNum), Double.parseDouble(bal));
                                display.append(savings.toString());
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Balance must be numeric");
                            }
                        }
                    }
                }
            }
        });

        return submitBtn;
    }

    public static ArrayList<CreditCard> getCreditCards(){
        return creditCards;
    }

    public static Mortgage getMortgage(){
        return mortgage;
    }

    public static SavingsAccount getSavings(){
        return savings;
    }
}