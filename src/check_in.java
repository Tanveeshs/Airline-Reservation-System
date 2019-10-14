import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



//ERRORS NOT DISPLAYING
//LINK TO BOARDINGPASS
//USERNAME TO BOOKING ID TO SHOW WHEN MAIN
public class check_in {

    JFrame frame;
    String onDate;
    JLayeredPane panel;
    JTextField bkid1;
    JButton submit;
    int g=0;
    Font f2 = new Font("Arial",Font.BOLD,20);
    private JXDatePicker picker = new JXDatePicker();
    check_in(String userId)
    {
        frame = new JFrame("Check In");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = frame.getLayeredPane();
        panel.setLayout(null);
        frame.setVisible(true);
        frame.setSize(800,700);

        JLabel cin = new JLabel("Check In");
        Font f1 = new Font("Consolas",Font.BOLD,50);
        cin.setFont(f1);
        cin.setBounds(280,60,cin.getPreferredSize().width,cin.getPreferredSize().height);
        panel.add(cin);

        JLabel bkid = new JLabel("Booking ID:");
        bkid.setFont(f2);
        bkid.setBounds(220,250,bkid.getPreferredSize().width,bkid.getPreferredSize().height);
        panel.add(bkid);

        JLabel onD = new JLabel("Onward Date:");
        onD.setFont(f2);
        onD.setBounds(220,300,onD.getPreferredSize().width,onD.getPreferredSize().height);
        panel.add(onD);


        picker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        picker.setBounds(360, 300, picker.getPreferredSize().width, picker.getPreferredSize().height);
        panel.add(picker);
        Calendar today = Calendar.getInstance();
        picker.setDate(today.getTime());
        picker.addActionListener(e -> {
            Date oDate = picker.getDate();
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            onDate = oDateFormat.format(oDate);
        });
        bkid1 = new JTextField(20);
        bkid1.setBounds(360,255,bkid1.getPreferredSize().width,bkid1.getPreferredSize().height);
        panel.add(bkid1);

        submit = new JButton("Search Booking");
        submit.setFont(f2);
        submit.setBounds(220,350,submit.getPreferredSize().width,submit.getPreferredSize().height);
        panel.add(submit);
        submit.addActionListener(e->{
            String sql = "SELECT ONDATE FROM BOOKING WHERE BOOKINGID like'"+bkid1.getText()+"'";
            System.out.println(sql);
            try {
                ResultSet sp = excecuteSQL(sql);
                while(sp.next()) {
                    System.out.println(sp.getString(1));
                    System.out.println(onDate);
                    if(sp.getString(1).equals(onDate)) {
                        System.out.println("Access Granted");
                        new BoardingPass(Integer.parseInt(bkid1.getText()),userId);
                        frame.dispose();
                        g=1;
                    }
                }
            } catch (SQLException ex) {

            }
           if(g==0)
           {

               dialog();

           }
        });

        ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\checkin.jpg");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(800, 700, Image.SCALE_DEFAULT);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(0, 0, 800, 700);
        panel.add(back);

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
    private void dialog() {
        JDialog d = new JDialog(frame, "Error");
        JLabel l = new JLabel("Error in Credentials");
        l.setFont(f2);
        d.setLocation(200, 200);
        d.add(l);
        d.setSize(400, 200);
        d.setVisible(true);
    }
    public static void main(String args[])
    {
        new check_in("Tanveeshs");
    }
}
