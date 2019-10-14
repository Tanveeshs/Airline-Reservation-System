import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class pass_details {
    JFrame frame;
    JLayeredPane panel;
    JLabel pass, b_id, lb1, Lsrc, Ldest;
    String bid;
    JRadioButton y, n;
    JTextField fname[] = new JTextField[6];
    JTextField lname[] = new JTextField[6];
    JButton b;
    int c;
    JTextField age[] = new JTextField[6];
    Font f = new Font("Calibri", Font.PLAIN, 18);
    Font f1 = new Font("Calibri", Font.BOLD, 20);
    Connection conn;
    PreparedStatement psd;
    ResultSet rs;
    String src1,dest1,p1 ,onDate1,reDate1,userID1,fare1,fnoO,fnoR;

    pass_details(String src, String dest, String p, String bkid,String onDate,String reDate,String userID,String fare,String fnoOnward,String fnoReturn) {
            bid = bkid;
            userID1 = userID;
            fare1 = fare;
       src1 = src;
       dest1 = dest;
       p1 = p;
       onDate1 = onDate;
       reDate1= reDate;
       fnoO = fnoOnward;
        fnoR = fnoReturn;
        //frame
        frame = new JFrame("Passenger Details");
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        //panel
        panel = frame.getLayeredPane();
        panel.setBackground(Color.darkGray);


        //
        JLabel p2 = new JLabel("Passenger Details");
        p2.setFont(new Font("Consolas",Font.BOLD,40));
        p2.setBounds(200,20,p2.getPreferredSize().width,p2.getPreferredSize().height);
        panel.add(p2);

        //inputs
        b_id = new JLabel("Booking ID:");
        b_id.setFont(f1);
        b_id.setBounds(500, 100, b_id.getPreferredSize().width, b_id.getPreferredSize().height);
        panel.add(b_id);

        JLabel bookID = new JLabel(bkid);
        bookID.setFont(f1);
        bookID.setBounds(500 + b_id.getPreferredSize().width, 100, bookID.getPreferredSize().width, bookID.getPreferredSize().height);
        panel.add(bookID);


        c = Integer.parseInt(p);
        pass = new JLabel("Passengers: " + c);
        pass.setFont(f1);
        pass.setBounds(500, 130, pass.getPreferredSize().width, pass.getPreferredSize().height);
        panel.add(pass);

        Lsrc = new JLabel("Source: " + src);
        Lsrc.setFont(f1);
        Lsrc.setBounds(50, 100, Lsrc.getPreferredSize().width, Lsrc.getPreferredSize().height);
        panel.add(Lsrc);

        Ldest = new JLabel("Destination: " + dest);
        Ldest.setFont(f1);
        Ldest.setBounds(210, 100, Ldest.getPreferredSize().width, Ldest.getPreferredSize().height);
        panel.add(Ldest);

        //usernames
        b = new JButton("Proceed to Payment");
        b.setFont(f1);
        b.setBounds(100, 600, b.getPreferredSize().width, b.getPreferredSize().height);
        b.addActionListener(e -> {
            checkFunc(c);
        });
        panel.add(b);

        for (int i = 0; i < c; i++) {
            JLabel name = new JLabel("First Name:");
            name.setFont(f1);
            name.setBounds(50, 170 + 80 * i, name.getPreferredSize().width, name.getPreferredSize().height);
            panel.add(name);
            fname[i] = new JTextField(15);
            fname[i].setBounds(60 + name.getPreferredSize().width, 170 + 80 * i, fname[0].getPreferredSize().width, fname[0].getPreferredSize().height);
            panel.add(fname[i]);
            JLabel lnamea = new JLabel("Last Name:");
            lnamea.setFont(f1);
            lnamea.setBounds(400, 170 + 80 * i, lnamea.getPreferredSize().width, lnamea.getPreferredSize().height);
            panel.add(lnamea);
            lname[i] = new JTextField(15);
            lname[i].setBounds(410 + lnamea.getPreferredSize().width, 170 + 80 * i, fname[0].getPreferredSize().width, fname[0].getPreferredSize().height);
            panel.add(lname[i]);
            JLabel dob = new JLabel("Age(In years):");
            dob.setFont(f1);
            dob.setBounds(400, 200 + 80 * i, dob.getPreferredSize().width, dob.getPreferredSize().height);
            age[i] = new JTextField();
            age[i].setColumns(4);
            age[i].setBounds(410 + dob.getPreferredSize().width, 200 + 80 * i, age[0].getPreferredSize().width, age[0].getPreferredSize().height);
            panel.add(dob);
            panel.add(age[i]);
        }

        ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\passDetails.jpg");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(800, 700, Image.SCALE_DEFAULT);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(0, 0, 800, 700);
        panel.add(back);


    }
    public void checkFunc(int p) {
        int r = 0;
        int age2 = 0;
        for (int i = 0; i < p; i++) {
            if (fname[i].getText().equals("") && r == 0) {
                JDialog d2 = new JDialog(frame, "Error");
                JLabel l = new JLabel(" First Name Can't be empty for Passenger " + (i + 1));
                l.setFont(f1);
                d2.setLocation(200, 200);
                d2.add(l);
                d2.setSize(500, 200);
                d2.setVisible(true);
                r = 1;
            }
            if (lname[i].getText().equals("") && r == 0) {
                JDialog d2 = new JDialog(frame, "Error");
                JLabel l = new JLabel(" Last Name Can't be empty for Passenger " + (i + 1));
                l.setFont(f1);
                d2.setLocation(200, 200);
                d2.add(l);
                d2.setSize(500, 200);
                d2.setVisible(true);
                r = 1;
            }
            String age1 = age[i].getText();
            try {
                age2 = Integer.parseInt(age1);
            } catch (Exception e) {
                if (r == 0) {
                    JDialog d2 = new JDialog(frame, "Error");
                    JLabel l = new JLabel(" Input The Age in Numbers Only for Passenger " + (i + 1));
                    l.setFont(f1);
                    d2.setLocation(200, 200);
                    d2.add(l);
                    d2.setSize(500, 200);
                    d2.setVisible(true);
                    r = 1;
                }
            }

            if ((age2 <= 0 || age2 >= 100) && r == 0) {
                JDialog d2 = new JDialog(frame, "Error");
                JLabel l = new JLabel(" Please Check the age Entered for Passenger " + (i + 1));
                l.setFont(f1);
                d2.setLocation(200, 200);
                d2.add(l);
                d2.setSize(500, 200);
                d2.setVisible(true);
                r = 1;
            }
        }
        if(r==0)
        {
            addToDatabase(p);
        }
    }
    public void addToDatabase(int r)
    {
        for (int i = 0; i < r; i++)
        {

            String s = "INSERT INTO PASSENGER values('"+fname[i].getText()+"','"+lname[i].getText()+"','"+bid+"',"+age[i].getText()+");";
            try
            {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "AIRLINERESERVATION", "Tanveesh21");
                String sql = "INSERT INTO PASSENGER values('"+fname[i].getText()+"','"+lname[i].getText()+"','"+bid+"',"+age[i].getText()+")";
                System.out.println(sql);
                psd=conn.prepareStatement(sql);
                rs = psd.executeQuery();
            }catch (Exception e)
            {
                System.out.println(e);
            }


        }
        new payments(src1,dest1,bid,onDate1,reDate1,p1,userID1,fare1,fnoO,fnoR);
        frame.dispose();

    }

    public static void main(String[] args) {
        new pass_details("BOM", "DEL", "6", "13125252512","23/10/2019","28/10/2019","Tanveeshs","7400","JK123","SS123");
    }

}