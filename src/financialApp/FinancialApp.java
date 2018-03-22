package financialApp;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * CLASS FinancialApp
 * A small gui application to calculate what the debt accumulated by having a mortgage, credit cards, and income as
 * a bank account.
 *
 * @author Ricky Mendez, Issac Olsen, Tosh Roberts, Zachary Tucker
 */
public class FinancialApp extends JFrame {

    private JPanel contentPane;
    private JFileChooser fileChooser;
    private JMenuBar menu;
    private JMenu fileMenu;
    private JMenuItem addItem, calculateItem;
    private JTextArea fileDisplay = new JTextArea();
    private Charset charset = Charset.forName("UTF-8");
    public static Mortgage mortgage;
    public static SavingsAccount savingsAccount;

    /**
     * MAIN - Starts the application
     * @param args
     */
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FinancialApp frame = new FinancialApp();
                    frame.setVisible(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * METHOD FinancialApp - builds the window
     */
    public FinancialApp(){

        // Window defaults to terminate on exit and size of window when launched
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100,100,600,600);
        setTitle("Financial Application");

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new AccountFileFilter());
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        // Create the main content panel
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        menuBar();
        add(fileDisplay, BorderLayout.CENTER);
    }

    private void menuBar() {
        menu = new JMenuBar();
        setJMenuBar(menu);

        // Add item menu items
        addItem = new JMenuItem("Add/Edit");
        addItem.addActionListener(menuListener(new UserPanel()));

        calculateItem = new JMenuItem("Calculate");
        calculateItem.addActionListener(menuListener(new CalculatePanel()));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(createFileMenu());
        menu.add(addItem);
        menu.add(calculateItem);
        menu.add(exit);
    }

    private JMenu createFileMenu() {
        fileMenu = new JMenu("File");

        JMenuItem importFile = new JMenuItem("Import...");
        JMenuItem exportFile = new JMenuItem("Export...");

        importFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(FinancialApp.this) == JFileChooser.APPROVE_OPTION){
                    try(BufferedReader br = Files.newBufferedReader(fileChooser.getSelectedFile().toPath())) {
                        CreditCard cc;
                        String line;
                        int counter = UserPanel.getCreditCards().size();
                        while ((line = br.readLine())!=null) {
                            if (line.endsWith(",")) {
                                switch (line){
                                    case "CreditCard,":
                                        fileDisplay.append("adding credit cards\n");
                                        String lndr = null;
                                        double bal = 0;
                                        double min = 0;
                                        double rate = 0;
                                        String columns[];

                                        do {
                                            columns = br.readLine().split(",");
                                            fileDisplay.append(columns[0] + " " + columns[1] + "\n");
                                            if (columns[0].contains("lender")) {
                                                lndr = columns[1];
                                            }
                                            if (columns[0].contains("balance")) {
                                                bal = Double.parseDouble(columns[1]);
                                            }
                                            if (columns[0].contains("minPay")) {
                                                min = Double.parseDouble(columns[1]);
                                            }
                                            if (columns[0].contains("rate")) {
                                                rate = Double.parseDouble(columns[1]);
                                            }
                                        } while (!columns[0].contains("rate"));

                                        if (lndr != null && min != 0 && bal != 0 && rate != 0) {
                                            counter++;

                                            cc = new CreditCard(counter, lndr, bal, min, rate);

                                            UserPanel.getCreditCards().add(cc);
                                        }
                                        break;
                                    case "Mortgage,":
                                        fileDisplay.append("adding mortgage\n");
                                        double mBal = 0;
                                        int mTerm = 0;
                                        double mRate = 0;
                                        String mColumns[];
                                        do {
                                            mColumns = br.readLine().split(",");

                                            if (mColumns[0].contains("loan")){
                                                mBal = Double.parseDouble(mColumns[1]);
                                            }

                                            if (mColumns[0].contains("term")){
                                                mTerm = Integer.parseInt(mColumns[1]);
                                            }

                                            if (mColumns[0].contains("rate")){
                                                mRate = Double.parseDouble(mColumns[1]);
                                            }
                                        } while (!mColumns[0].contains("rate"));

                                        if (mBal != 0 && mTerm != 0 && mRate !=0) {
                                            mortgage = new Mortgage(mTerm, mRate, mBal);
                                            UserPanel.mortgage = mortgage;
                                        }

                                        break;
                                    case "Savings,":
                                        fileDisplay.append("adding savings account\n");
                                        int sAccNum = 0;
                                        double aBal = 0;
                                        String sColumns[];

                                        do {
                                            sColumns = br.readLine().split(",");

                                            if (sColumns[0].contains("account")){
                                                sAccNum = Integer.parseInt(sColumns[1]);
                                            }

                                            if (sColumns[0].contains("balance")){
                                                aBal = Double.parseDouble(sColumns[1]);
                                            }
                                        } while (!sColumns[0].contains("balance"));

                                        if (sAccNum != 0 && aBal != 0){
                                            savingsAccount = new SavingsAccount(sAccNum,aBal);
                                            UserPanel.savings = savingsAccount;
                                        }
                                        break;
                                    default:
                                        fileDisplay.append("No Financial items found");
                                        break;
                                }
                            }
                        }

                        br.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        exportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(FinancialApp.this) == JFileChooser.APPROVE_OPTION){
                    Path saved = fileChooser.getSelectedFile().toPath();
                    try (BufferedWriter bw = Files.newBufferedWriter(saved, charset)){
                        if (!UserPanel.getCreditCards().isEmpty()){
                            for (CreditCard cc :
                                    UserPanel.getCreditCards()) {
                                bw.write(cc.toString());
                            }
                        }

                        if (UserPanel.mortgage != null){
                            bw.write(UserPanel.mortgage.toString());
                        }

                        if (UserPanel.savings != null){
                            bw.write(UserPanel.savings.toString());
                        }

                        bw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        fileMenu.add(importFile);
        fileMenu.add(exportFile);

        return fileMenu;
    }

    private ActionListener menuListener(JPanel panel){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.removeAll();
                contentPane.add(panel, BorderLayout.CENTER);

                revalidate();
                repaint();
            }
        };
    }
}