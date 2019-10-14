import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Pattern;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class new_user{
    private JFrame frame;
    private JTextField text1, text2, text3, text6, text7, text8;
    private JPasswordField text4;
    private int r = 0;
    UtilDateModel model = new UtilDateModel();
    Properties p =new Properties();
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    new_user() {
        Font f = new Font("Serif", Font.PLAIN, 20);

        {
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            datePanel = new JDatePanelImpl(model, p);
            datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        }

        //frame
        frame = new JFrame();//creates a frame
        frame.setSize(800, 700); // setting the size of the window
        frame.setLayout(null);
        frame.setVisible(true); // wanting the text to be visible on the window
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setBackground(Color.CYAN);
        //Heading
        JLabel head = new JLabel(" Sign Up");
        Font f2 = new Font("Consolas", Font.BOLD, 40);
        head.setFont(f2);
        head.setBounds(300, 30, 300, head.getPreferredSize().height);
        head.setBackground(new Color(65 - 105 - 225));

        // Label1
        JLabel head1 = new JLabel();
        head1.setText("First Name:");
        head1.setFont(f);
        head1.setBounds(200, 100, head1.getPreferredSize().width, head1.getPreferredSize().height);
        text1 = new JTextField(20);
        text1.setBounds(320, 105, text1.getPreferredSize().width, text1.getPreferredSize().height);

        // Label2
        JLabel head2 = new JLabel();
        head2.setText("Last Name:");
        head2.setFont(f);
        head2.setBounds(200, 140, head2.getPreferredSize().width, head2.getPreferredSize().height);
        text2 = new JTextField(20);
        text2.setBounds(320, 145, text1.getPreferredSize().width, text1.getPreferredSize().height);

        // Label3
        JLabel head3 = new JLabel();
        head3.setText("Username:");
        head3.setFont(f);
        head3.setBounds(200, 180, head3.getPreferredSize().width, head3.getPreferredSize().height);
        text3 = new JTextField(20);
        text3.setBounds(320, 185, text1.getPreferredSize().width, text1.getPreferredSize().height);

        // Label4
        JLabel head4 = new JLabel();
        head4.setText("Password: ");
        head4.setFont(f);
        head4.setBounds(200, 220, head4.getPreferredSize().width, head4.getPreferredSize().height);
        text4 = new JPasswordField(20);
        text4.setBounds(320, 225, text1.getPreferredSize().width, text1.getPreferredSize().height);

        // Label5
        JLabel head5 = new JLabel();
        head5.setText("Date Of Birth:");
        head5.setFont(f);
        head5.setBounds(200, 260, head5.getPreferredSize().width, head5.getPreferredSize().height);
        datePicker.setBounds(320, 265, text1.getPreferredSize().width,text1.getPreferredSize().height);
       //Label6
        JLabel head6 = new JLabel();
        head6.setText("Passport No:");
        head6.setFont(f);
        head6.setBounds(200, 300, head6.getPreferredSize().width, head6.getPreferredSize().height);
        text6 = new JTextField(20);
        text6.setBounds(320, 305, text1.getPreferredSize().width, text1.getPreferredSize().height);

        //Label7
        JLabel head7 = new JLabel();
        head7.setText("Email ID:");
        head7.setFont(f);
        head7.setBounds(200, 340, head7.getPreferredSize().width, head7.getPreferredSize().height);
        text7 = new JTextField(20);
        text7.setBounds(320, 345, text1.getPreferredSize().width, text1.getPreferredSize().height);

        //Label8
        JLabel head8 = new JLabel();
        head8.setText("Mobile No:");
        head8.setFont(f);
        head8.setBounds(200, 380, head8.getPreferredSize().width, head8.getPreferredSize().height);
        text8 = new JTextField(20);
        text8.setBounds(320, 385, text1.getPreferredSize().width, text1.getPreferredSize().height);

        //Button
        JButton SUBMIT = new JButton("Submit");
        SUBMIT.setFont(f);
        SUBMIT.setBounds(200, 430, SUBMIT.getPreferredSize().width, SUBMIT.getPreferredSize().height);
        SUBMIT.addActionListener(e -> submitfunc());

        JButton home = new JButton("Back to Login page");
        home.setFont(f);
        home.setBounds(320, 430, home.getPreferredSize().width, home.getPreferredSize().height);
        home.addActionListener((e -> {
            new Login();
            frame.dispose();
        }));

            ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\new-user.jpg");
            Image img = background.getImage();
            Image temp = img.getScaledInstance(800, 700, Image.SCALE_SMOOTH);
            background = new ImageIcon(temp);
            JLabel back = new JLabel(background);
            back.setLayout(null);
            back.setBounds(0, 0, 800, 700);


        //panel
        // declares a workspace panel wherein inputs are taken
        JLayeredPane panel = frame.getLayeredPane();
        panel.setBackground(Color.PINK);
        panel.add(head);
        panel.add(head5);
        panel.add(head1);
        panel.add(text1);
        panel.add(head2);
        panel.add(text2);
        panel.add(head3);
        panel.add(text3);
        panel.add(head4);
        panel.add(text4);
        panel.add(head6);
        panel.add(text6);
        panel.add(head7);
        panel.add(text7);
        panel.add(head8);
        panel.add(text8);
        panel.add(SUBMIT);
        panel.add(datePicker);
        panel.add(home);
        panel.add(back);

    }

    private void submitfunc() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -6571);

        Date selectedDate = (Date) datePicker.getModel().getValue();
        System.out.println(selectedDate);

        int a = 0;
        r = 0;
        try {
            long h = Long.parseLong(text8.getText());
            if(h > Long.parseLong("9999999999") || h<  1000000000)
            {
                dialog("Please enter a 10 digit number");
                r=1;
            }
        }catch (Exception e)
        {
            dialog("Please enter a valid Mobile Number");
            r=1;
        }


        LinkedList<String> uname = new LinkedList<>();
        ResultSet r1 = excecuteSQL("SELECT USERNAME FROM CUSTOMER");
        try {
            while (r1.next())
            {
                uname.add(r1.getString(1));
            }
            if(uname.contains(text3.getText()))
            {
                dialog("Please Enter a unique Username");
            }
        }catch (Exception ignored)
        {
        }

        if(selectedDate !=null) {
            a = selectedDate.compareTo(cal.getTime());
        }
        else
        {
            dialog("Please enter the date");
            r=1;
        }

        if (text1.getText().equals("") && r != 1) {
            dialog("Fill in the first Name");
            r = 1;
        } else if (text2.getText().equals("") && r != 1) {
            dialog("Fill in the Last Name");
            r = 1;

        } else if (text3.getText().equals("") && r != 1) {
            dialog("Fill in the Username");
            r = 1;
        } else if (new String(text4.getPassword()).equals("") && r != 1) {
            dialog("Enter the password");
            r = 1;
        } else if (text6.getText().equals("") && r != 1) {
            dialog("Enter Passport No.");
            r = 1;
        } else if (text7.getText().equals("") && r != 1) {
            dialog("Enter Email Id");
            r = 1;
        } else if (text8.getText().equals("") && r != 1) {
            dialog("Enter Mobile Number");
            r = 1;
        } else if ((a > 0 || a == 0) && r != 1) {
            dialog("Minimum age required is 18 years");
            r = 1;
        } else if (!(isValidEmail(text7.getText()))&& r!=1)
        {
            dialog("Email Entered is not valid");
        }
        if (r == 0) {
            String pass = new String(text4.getPassword());
            Date d = (Date) datePicker.getModel().getValue();
            DateFormat d1 = new SimpleDateFormat("dd-MM-yyyy");
            String d2 = d1.format(d);
            String sql = "INSERT INTO CUSTOMER VALUES('" + text1.getText() + "','" + text2.getText() + "','" + text3.getText() + "','" + getMD5(pass) + "','" + d2+ "','" + text6.getText()  + "','" + text7.getText() + "','" + text8.getText() + "')";
            excecuteSQL(sql);
            frame.dispose();
            new Login();
        }

    }


    @SuppressWarnings({"DuplicatedCode", "UnusedReturnValue"})
    private ResultSet excecuteSQL(String sql) {
        ResultSet rs = null;
        try {
            Connection conn;
            PreparedStatement psd;

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "AIRLINERESERVATION", "Tanveesh21");
            psd = conn.prepareStatement(sql);
            rs = psd.executeQuery();
        } catch (Exception ignored) {

        }
        return rs;
    }

    private void dialog(String s) {
        JDialog d = new JDialog(frame, "Error");
        JLabel l = new JLabel(s);
        l.setFont(new Font("Consolas",Font.PLAIN,18));
        d.setLocation(200, 200);
        d.add(l);
        d.setSize(400, 200);
        d.setVisible(true);
        r = 1;
    }

    public static void main(String[] args) {
        new new_user();
    }

    private static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(number.toString(16));
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) hashtext.insert(0, "0");
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}