import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedList;

public class Login {
    public static JFrame frame;
    private JLabel password;
    private JLabel username;
    private JButton Login;
    private JButton newuser;
    private JLabel output;
    private JTextField username_field;
    private JPasswordField passwordField;
    String sql,pass;
    Connection conn = null;
    PreparedStatement psd = null;
    ResultSet rs = null;
    private JLabel Heading;
    protected Login()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane panel = frame.getLayeredPane();
        panel.setLayout(null);

        //Background
        {
            ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\Background.jpg");
            Image img = background.getImage();
            Image temp = img.getScaledInstance(800, 700, Image.SCALE_SMOOTH);
            background = new ImageIcon(temp);
            JLabel back = new JLabel(background);
            back.setLayout(null);
            back.setBounds(0, 0, 800, 700);
            panel.add(back);
        }
        //Header Label
        {
            Heading = new JLabel("Airline Reservation System");
            Heading.setFont(new Font("Times New Roman", Font.BOLD, 60));
            Heading.setForeground(Color.white);
            panel.add(Heading, JLayeredPane.POPUP_LAYER);
            Heading.setBounds(48, 20, Heading.getPreferredSize().width, Heading.getPreferredSize().height);
        }
        //Username Label
        {
            username = new JLabel("Username:");
            username.setFont(new Font("Sans Serif", Font.BOLD, 20));
            username.setForeground(Color.white);
            panel.add(username,JLayeredPane.POPUP_LAYER);
            Dimension size = username.getPreferredSize();
            username.setBounds(250, 320, size.width, size.height);
        }
        //Password Label
        {

            password = new JLabel("Password:");
            password.setFont(new Font("Sans Serif", Font.BOLD, 20));
            password.setForeground(Color.white);
            panel.add(password,JLayeredPane.POPUP_LAYER);
            Dimension size1 = password.getPreferredSize();
            password.setBounds(250, 360, size1.width, size1.height);
        }
        //output label
        {
            output = new JLabel("                                                                   ");
            output.setFont(new Font("Sans Serif", Font.BOLD, 20));
            output.setForeground(Color.white);
            panel.add(output,JLayeredPane.POPUP_LAYER);
            output.setBounds(250, 415, output.getPreferredSize().width, output.getPreferredSize().height);

        }
        //login button
        {
            Login = new JButton("Login");
            Login.addActionListener((e)->loginButton());
            panel.add(Login,JLayeredPane.POPUP_LAYER);
            Login.setBounds(250, 390, Login.getPreferredSize().width, Login.getPreferredSize().height);
        }
        //new user button
        {
            newuser = new JButton("New User");
            newuser.addActionListener(new ActionListener()
                                      {
                                          @Override
                                          public void actionPerformed(ActionEvent e) {
                                              new new_user();
                                              frame.dispose();
                                          }
                                      });
                    panel.add(newuser, JLayeredPane.POPUP_LAYER);
            newuser.setBounds(250 + Login.getPreferredSize().width + 20, 390, newuser.getPreferredSize().width, newuser.getPreferredSize().height);
        }
        //username text field
        {
            username_field = new JTextField(20);
            panel.add(username_field,JLayeredPane.POPUP_LAYER);
            username_field.setBounds(353, 325, username_field.getPreferredSize().width, username_field.getPreferredSize().height);
        }
        //password field
        {
            passwordField = new JPasswordField(20);
            panel.add(passwordField,JLayeredPane.POPUP_LAYER);
            passwordField.setBounds(251 + password.getPreferredSize().width, 365, passwordField.getPreferredSize().width, passwordField.getPreferredSize().height);
        }

        frame.setSize(800, 700);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new Login();

    }

    private static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(number.toString(16));
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void loginButton() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "AIRLINERESERVATION", "Tanveesh21");

            //Retrieve list of user names
            sql = "select USERNAME from CUSTOMER";

            psd = conn.prepareStatement(sql);
            rs = psd.executeQuery();
            //Linked List for username
            LinkedList<String> users = new LinkedList<>();

            while (rs.next()) {
                users.add(rs.getString(1));
            }
            //Check if username exists
            int x = users.indexOf(username_field.getText());


            if (x == -1) {
                output.setText("User not found");

            }
            else
                {
                    output.setText(" ");
                     sql = "select PASSWORD from CUSTOMER where USERNAME like '"+username_field.getText()+"'";
                    psd = conn.prepareStatement(sql);
                    rs = psd.executeQuery();
                    while (rs.next())
                        pass = rs.getString(1);
                String passText = new String(passwordField.getPassword());
                if(pass.equals(getMD5(passText)))
                {
                    frame.dispose();
                    new Home(username_field.getText());

                }
                else
                {
                    output.setText("Wrong password");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}