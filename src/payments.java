import org.jdesktop.swingx.JXDatePicker;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class payments implements ActionListener {
    JFrame f;
    private JLayeredPane p;
    private JLabel l_src;
    private JLabel l_dest;
    private JRadioButton cc;
    private JRadioButton db;
    private JLabel fdate, rdate;
    JPasswordField cvv;
    private JLabel c_exp;
    JTextField namef, cardn;
    private JLabel lb1, amt;
    private JXDatePicker picker = new JXDatePicker();
    private JButton btn;
    private String date;

    private Long amount;
    String bid,src1,dest1,onDate1,reDate1,pax1,userID1,fno1,fno2;
    private JavaEmail ob2 = new JavaEmail();

    payments(String src, String dest,String bookID,String onDate,String reDate,String pax,String userID,String fare1,String fnoO,String fnoR) {
        //font
        Font f1 = new Font("Courier", Font.BOLD, 16);
        Font f2 = new Font("Consolas", Font.BOLD, 20);
        Font f3 = new Font("Consolas", Font.BOLD, 25);
        bid = bookID;
        src1 = src;
        dest1 = dest;
        onDate1 = onDate;
        reDate1 = reDate;
        pax1 = pax;
        userID1 = userID;
        fno1 = fnoO;
        fno2 = fnoR;
        //frame
        f = new JFrame("Payments");
        f.setSize(800, 700);
        f.setVisible(true);
        f.setLayout(null);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

        //panel
        p = f.getLayeredPane();

        //inputs
        l_src = new JLabel("Source: " + src);
        l_src.setBounds(100, 50, 200, 30);
        l_src.setFont(f3);
        p.add(l_src);


        l_dest = new JLabel("Destination: " + dest);
        l_dest.setBounds(400, 50, 400, 30);
        l_dest.setFont(f3);

        p.add(l_dest);

        JLabel cty = new JLabel("Card Type:");
        cty.setFont(f1);
        cty.setBounds(100, 180, cty.getPreferredSize().width, cty.getPreferredSize().height);
        p.add(cty);


        JLabel lb = new JLabel("CVV:");
        lb.setBounds(380, 320, 100, 30);
        lb.setFont(f1);
        p.add(lb);

        cvv = new JPasswordField(5);
        cvv.setBounds(450, 320, cvv.getPreferredSize().width, 30);
        cvv.setFont(f1);
        p.add(cvv);

        fdate = new JLabel("\t\t");
        fdate.setBounds(100, 150, fdate.getPreferredSize().width, fdate.getPreferredSize().height);
        fdate.setFont(f1);
        p.add(fdate);

        rdate = new JLabel("\t\t");
        rdate.setBounds(190, 150, fdate.getPreferredSize().width, fdate.getPreferredSize().height);
        rdate.setFont(f1);
        p.add(rdate);


        JLabel name = new JLabel("CardHolder Name:");
        name.setBounds(100, 280, 170, 30);
        name.setFont(f1);
        p.add(name);

        namef = new JTextField(15);
        namef.setBounds(110+ name.getPreferredSize().width, 280, 120, 25);
        namef.setFont(f1);
        p.add(namef);

        c_exp = new JLabel("Card Expiry:");
        c_exp.setBounds(100, 320, 120, 30);
        c_exp.setFont(f1);
        p.add(c_exp);

        picker.setFormats(new SimpleDateFormat("MM/yyyy"));
        picker.setBounds(110+ name.getPreferredSize().width, 320, 120, 25);
        p.add(picker);
        picker.addActionListener(e -> {
            DateFormat formatDate = new SimpleDateFormat("MM-yyyy");
            date = formatDate.format(picker.getDate());
        });


        JLabel fare = new JLabel("Fare: ₹" + fare1);
        fare.setBounds(600, 400, 200, 30);
        fare.setFont(f1);
        p.add(fare);

        JLabel np = new JLabel("Seats: " + pax);
        np.setBounds(600, 440, 100, 30);
        np.setFont(f1);
        p.add(np);

        amount = Long.parseLong(pax)*Long.parseLong(fare1);
        amt = new JLabel("Total:₹"+amount);
        amt.setFont(f2);
        amt.setBounds(600, 480, amt.getPreferredSize().width, 50);
        p.add(amt);

        lb1 = new JLabel("Card No:");
        lb1.setBounds(380, 280, 80, 30);
        lb1.setFont(f1);
        p.add(lb1);

        cardn = new JTextField(20);
        cardn.setBounds(450, 280, 150, 25);
        p.add(cardn);


        //buttons
        ButtonGroup pay_ch = new ButtonGroup();
        cc = new JRadioButton("Credit Card");
        cc.setBounds(100, 200, cc.getPreferredSize().width, cc.getPreferredSize().height);
        //cc.setLayout(null);
        db = new JRadioButton("Debit Card");
        db.setBounds(220, 200, db.getPreferredSize().width, db.getPreferredSize().height);

        pay_ch.add(cc);
        pay_ch.add(db);
        p.add(cc);
        p.add(db);
        cc.setSelected(true);

        btn = new JButton();
        btn.setText("Next Page");
        btn.setBounds(500, 580, btn.getPreferredSize().width, btn.getPreferredSize().height);
        btn.addActionListener(this);
        p.add(btn);

        ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\img_pay.jpg");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(800, 700, Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(0, 0, 800, 700);
        p.add(back);

    }

    public static void main(String[] args) {
        new payments("BOM", "DEL","131651325","23/10/2019",null,"3","Tanveeshs","2560","JK121",null);
    }

    public void actionPerformed(ActionEvent e) {
        String opt = null;
        if(cc.isSelected())
        {
            opt="cc";
        }
        if(db.isSelected())
        {
            opt="db";
        }

        String s3;
        String s1 = cardn.getText();
        String s2 = new String(cvv.getPassword());
        s3 = s1.trim();
        if (s1.equals("") && s2.equals("")) {
            JOptionPane.showMessageDialog(btn, "Field cannot be empty");

        } else if (s2.length() != 3)
            JOptionPane.showMessageDialog(btn, "CVV has only 3 digits");

        //noinspection LoopStatementThatDoesntLoop
        for (int i = 0; i < s3.length(); //noinspection UnusedAssignment
             i++) {
            if (s3.charAt(i) >= 48 && s3.charAt(i) <= 57) {

                if (s3.length() == 16) {
                    ResultSet rs1;
                    if(reDate1== null)
                    {
                        String s = "INSERT INTO BOOKING(bookingid,source,dest,ondate,pax,userid,fl_num_on) VALUES('"+bid+"','"+src1+"','"+dest1+"','"+onDate1+"','"+pax1+"','"+userID1+"','"+fno1+"')";
                        System.out.println(s);
                        rs1 = excecuteSQL(s);
                        String r = "INSERT INTO PAYMENTS VALUES('"+bid+"','"+cardn.getText()+"','"+date+"','"+amount+"','"+namef.getText()+"','"+opt+"')";
                        System.out.println(r);
                        rs1 = excecuteSQL(r);
                        JDialog d2 = new JDialog();
                        d2.setVisible(true);
                        d2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        d2.setLayout(null);
                        d2.setSize(410,300);
                        d2.setLocation(300,250);
                        JLabel bD = new JLabel("Your booking is Confirmed! Booking ID is:"+bid);
                        bD.setFont(new Font("Arial", Font.BOLD,14));
                        bD.setBounds(10,100,bD.getPreferredSize().width,bD.getPreferredSize().height);
                        d2.add(bD);
                        JButton bb = new JButton("Go to Home Page!");
                        bb.setBounds(195,200,bb.getPreferredSize().width,bb.getPreferredSize().height);
                        d2.add(bb);

                        String emailid = null;
                        String g="SELECT EMAIL FROM CUSTOMER WHERE USERNAME like'"+userID1+"'";
                        ResultSet rs = excecuteSQL(g);
                        try {
                            while (rs.next())
                            {
                                emailid=rs.getString(1);
                            }

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            ob2.sendMail(emailid,bid);
                        } catch (MessagingException | SQLException ex) {
                            ex.printStackTrace();
                        }


                        bb.addActionListener(e1 -> {
                            new Home(userID1);
                            f.dispose();
                        });

                        break;
                    }
                    else
                    {
                        String s = "INSERT INTO BOOKING VALUES('"+bid+"','"+src1+"','"+dest1+"','"+onDate1+"','"+reDate1+"','"+pax1+"','"+userID1+"','"+fno1+"','"+fno2+"')";
                        System.out.println(s);
                        rs1 = excecuteSQL(s);
                        String r = "INSERT INTO PAYMENTS VALUES('"+bid+"','"+cardn.getText()+"','"+date+"','"+amount+"','"+namef.getText()+"','"+opt+"')";
                        System.out.println(r);
                        rs1 = excecuteSQL(r);
                        JDialog d2 = new JDialog();
                        d2.setVisible(true);
                        d2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        d2.setLayout(null);
                        d2.setSize(410,300);
                        d2.setLocation(300,250);
                        JLabel bD = new JLabel("Your booking is Confirmed! Booking ID is:"+bid);
                        bD.setFont(new Font("Arial", Font.BOLD,14));
                        bD.setBounds(10,100,bD.getPreferredSize().width,bD.getPreferredSize().height);
                        d2.add(bD);
                        JButton bb = new JButton("Go to Home Page!");
                        bb.setBounds(195,200,bb.getPreferredSize().width,bb.getPreferredSize().height);
                        d2.add(bb);


                        String emailid = null;
                        String g="SELECT EMAIL FROM CUSTOMER WHERE USERNAME like'"+userID1+"'";
                        ResultSet rs = excecuteSQL(g);
                        try {
                            while (rs.next())
                            {
                                emailid=rs.getString(1);
                            }

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            ob2.sendMail(emailid,bid);
                        } catch (MessagingException | SQLException ex) {
                            ex.printStackTrace();
                        }

                        bb.addActionListener(e1 -> {
                            new Home(userID1);
                            f.dispose();
                            d2.dispose();
                        });
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(btn, "Card Number must have exactly 16 digits");
                    break;
                }
            } else {
                JOptionPane.showMessageDialog(btn, "Card Number must have only digits");
                break;
            }
        }
    }

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
}

