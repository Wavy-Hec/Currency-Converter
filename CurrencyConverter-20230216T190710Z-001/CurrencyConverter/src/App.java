import java.awt.*;
import java.awt.event.*;
import java.beans.Statement;
import java.lang.management.PlatformLoggingMXBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.swing.*;

import org.w3c.dom.css.Counter;



public class App {
    public static JFrame f;
    public static void main(String[] args) throws Exception {
        

        String jdbc = "jdbc:sqlite:database.db"; // used to connect to the SQLite database file
        
            Connection conn = DriverManager.getConnection(jdbc);

            java.sql.Statement st = conn.createStatement();

            ResultSet rs = ((java.sql.Statement) st).executeQuery("SELECT * FROM data"); // used to get all the records in the table

            int counter = 0;
            while (rs.next())
            {
                System.out.println(counter);
                counter++;
                
            }

            String [] c = new String [counter];
            int counter2 = 0;

            rs = ((java.sql.Statement) st).executeQuery("SELECT * FROM data");
            while (rs.next())
            {
                System.out.println(rs.getString("CurrencyName"));
                c[counter2] = (rs.getString("CurrencyName"));
                System.out.println(c[counter2]);
                counter2++;
                
            }


    f = new JFrame("Currency Converter");
    
    f.setLayout(new GridBagLayout());
    
    GridBagConstraints gbc = new GridBagConstraints();

    //USD Dollar Label
    JLabel usdDollar = new JLabel("USD Dollars");
    usdDollar.setFont(new Font("Arial", Font.PLAIN, 36));

    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.insets = new Insets(6,6,6,6);

    f.add( usdDollar, gbc);
    //USD Dollar input for text
    JTextArea usdAmount = new JTextArea("");
    usdAmount.setFont(new Font("Arial", Font.PLAIN, 36));

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    gbc.insets = new Insets(6,6,6,6);

    f.add(usdAmount, gbc);
    
    //Wanted Currency Text
    JLabel wantedAmount = new JLabel(" ");
    wantedAmount.setFont(new Font("Arial", Font.PLAIN, 36));

    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 1;
    gbc.insets = new Insets(6,6,6,6);

    f.add(wantedAmount, gbc);

   
    //Drop Down menu
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.insets = new Insets(6,6,6,6);
    
    JPanel panel = new JPanel();
    
    JLabel lbl = new JLabel("Wanted Currency");
    
    lbl.setFont(new Font("Arial", Font.PLAIN, 36));
    lbl.setVisible(true);
    
    panel.add(lbl, gbc);

    
    final JComboBox<String> cb = new JComboBox<String>(c);

    cb.setFont(new Font("Arial", Font.PLAIN, 36));

    cb.setVisible(true);
    panel.add(cb, gbc);

    f.add(panel, gbc);


    //Process Button
    JButton processButton = new JButton("Process");

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 0;
    gbc.gridwidth=2;
    gbc.insets = new Insets(6,6,6,6);

    cb.setFont(new Font("Arial", Font.PLAIN, 36));

    
    cb.setVisible(true);
   

    f.add(processButton, gbc);

    
    processButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            try 
            (Connection conn = DriverManager.getConnection(jdbc)) {
                java.sql.Statement st = conn.createStatement();
                ResultSet rs = ((java.sql.Statement) st).executeQuery("SELECT * FROM data");
                while (rs.next())
                {   
                    if(rs.getString("CurrencyName").equals(cb.getSelectedItem())){
                        // lbl.setText(rs.getString("CurrencyName"));
                        String x = usdAmount.getText();
                        double ans = Double.valueOf(x);
                        ans = ans * rs.getDouble("Rate");
                        if(Double.valueOf(rs.getString("Rate")) == 0){
                            wantedAmount.setText("Currency is no longer available for trade or does not exist");
                        }
                        else{
                            wantedAmount.setText("Iso: " + rs.getString("Iso") + "  Amount: " +  String.valueOf(ans));
                        }
                        break;
                       
                    }
                    else{
                        wantedAmount.setText("Insert a number please");
                    }
                }
                


            } catch (NumberFormatException | SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });


    f.setSize(800 , 600);

    f.setVisible(true);

    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
