import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static java.util.Objects.*;

public class Home {
    JButton complaints;
    JFrame frame;
    private JLayeredPane panel;
    JLabel return_label;
    JLabel Heading;
    JLabel source_label;
    private JLabel jtype;
    private JLabel user_input;
    JLabel pax_label;
    private JComboBox<Integer> pax;
    JComboBox<String> source;
    private JComboBox<String> destination;
    private JRadioButton oneway, returnj;
    private ButtonGroup G1;
    private JButton search;
    private JButton logout;


    private Font labels, labels2;
    Connection conn = null;
    PreparedStatement psd = null;
    String[] airports = null;
    String sql = null;
    private JXDatePicker picker1 = new JXDatePicker();
    private JXDatePicker picker = new JXDatePicker();
    boolean x =true;
    String onDay,onDate;
    String reDate,reDay;
    String userID1;
    Home(String username) {
        labels = new Font("Arial", Font.BOLD, 20);
        labels2 = new Font("Arial", Font.BOLD, 15);
        Font f1 = new Font("Arial",Font.BOLD,30);
        userID1 = username;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = frame.getLayeredPane();
        panel.setLayout(null);
        frame.setVisible(true);
        frame.setSize(800, 700);

        //Flight Ticket Booking
        {
            Heading = new JLabel("Flight Ticket Booking");
            Heading.setFont(f1);
            panel.add(Heading, JLayeredPane.POPUP_LAYER);
            Heading.setBounds(250, 70, Heading.getPreferredSize().width, Heading.getPreferredSize().height);
        }

        //Welcome User
        {
            user_input = new JLabel();
            user_input.setText("Welcome, " + username + "!");
            user_input.setFont(labels2);
            user_input.setBounds(600, 30, user_input.getPreferredSize().width, user_input.getPreferredSize().height);
            panel.add(user_input);
        }

        //Source Label
        {
            source_label = new JLabel("Source:");
            source_label.setFont(labels2);
            source_label.setBounds(20, 170, source_label.getPreferredSize().width, source_label.getPreferredSize().height);
            panel.add(source_label);
        }
        //Destination Label
        JLabel destination_label;
        {
            destination_label = new JLabel("Destination:");
            destination_label.setFont(labels2);
            panel.add(destination_label);
            destination_label.setBounds(20, 210, destination_label.getPreferredSize().width, destination_label.getPreferredSize().height);

        }
        //Source Drop Down list
        {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "AIRLINERESERVATION", "Tanveesh21");
                sql = "select ANAME from AIRPORTS";
                psd = conn.prepareStatement(sql);
                ResultSet rs = psd.executeQuery();
                LinkedList<String> airport = new LinkedList<>();
                while (rs.next()) {
                    airport.add(rs.getString(1));
                }
                airports = airport.toArray(new String[0]);
            } catch (Exception ignored) {}
            source = new JComboBox<>(requireNonNull(airports));
            panel.add(source);
            source.setBounds(120, 170, source.getPreferredSize().width, source.getPreferredSize().height);
        }
        //Destination Drop Down list
        {
            destination = new JComboBox<>(airports);
            panel.add(destination);
            destination.setBounds(120, 210, destination.getPreferredSize().width, destination.getPreferredSize().height);
        }
        //Journey Type Label
        {
            jtype = new JLabel("Journey Type:");
            jtype.setFont(labels2);
            panel.add(jtype);
            jtype.setBounds(20, 250, jtype.getPreferredSize().width, jtype.getPreferredSize().height);

        }
        //Check Box
        {
            oneway = new JRadioButton("One Way");
            returnj = new JRadioButton("Return", true);
            G1 = new ButtonGroup();
            oneway.setText("One Way");
            returnj.setText("Return");
            returnj.addActionListener(e -> {
                picker1.setVisible(true);
                return_label.setVisible(true);
            });
            oneway.addActionListener(e -> {
                picker1.setVisible(false);
                return_label.setVisible(false);
            });
            oneway.setBounds(120, 250, oneway.getPreferredSize().width, oneway.getPreferredSize().height);
            returnj.setBounds(120 + oneway.getPreferredSize().width + 20, 250, returnj.getPreferredSize().width, returnj.getPreferredSize().height);
            G1.add(oneway);
            G1.add(returnj);
            panel.add(oneway);
            panel.add(returnj);

        }


        //Number of Passenger Label
        {
            pax_label = new JLabel("Number of passngers:");
            pax_label.setFont(labels2);
            panel.add(pax_label);
            pax_label.setBounds(320,250,pax_label.getPreferredSize().width,pax_label.getPreferredSize().height);
        }
        //Number of Passenger ComboBox
        {
            Integer[] pax1 = new Integer[]{1, 2, 3, 4, 5, 6};
            pax = new JComboBox<>(pax1);
            panel.add(pax,JLayeredPane.POPUP_LAYER);
            pax.setBounds(330+pax_label.getPreferredSize().width,250,pax.getPreferredSize().width,pax.getPreferredSize().height);

        }

        //One Way Date Label
        {
            source_label = new JLabel("Onward Date:");
            source_label.setFont(labels2);
            source_label.setBounds(20, 290, source_label.getPreferredSize().width, source_label.getPreferredSize().height);
            panel.add(source_label);
        }

        //One way Date
        {

            picker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
            panel.add(picker);
            Calendar today = Calendar.getInstance();
            picker.setDate(today.getTime());
            picker.setBounds(120, 285, picker.getPreferredSize().width, picker.getPreferredSize().height);
            picker.addActionListener(e -> {
                Date oDate = picker.getDate();
                DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat Day = new SimpleDateFormat("EEE");
                onDate = oDateFormat.format(oDate);
                onDay = Day.format(oDate);
                System.out.println(onDay);
            });
        }
        //Return date label
        {
            return_label = new JLabel("Return Date:");
            return_label.setFont(labels2);
            return_label.setBounds(320, 290, return_label.getPreferredSize().width, return_label.getPreferredSize().height);
            panel.add(return_label);
        }

        //Return Date
        {
            picker1.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
            panel.add(picker1);
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DATE,1);
            picker1.setDate(tomorrow.getTime());

            picker1.setBounds(410, 285, picker1.getPreferredSize().width, picker1.getPreferredSize().height);
            picker1.addActionListener(e -> {
                Date oDate = picker1.getDate();
                DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat Day = new SimpleDateFormat("EEE");
                reDate = oDateFormat.format(oDate);
                reDay = Day.format(oDate);
            });
        }

        //Search button
        {
            search = new JButton("Search Flights");
            search.addActionListener(e -> {
                try {
                    checkFunc();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            panel.add(search);
            search.setBounds(200,330,search.getPreferredSize().width,search.getPreferredSize().height);
        }
        //Logout Button
        {
            logout = new JButton("Logout");
            logout.setFont(labels2);
            logout.setBounds(20,600,logout.getPreferredSize().width,logout.getPreferredSize().height);
            panel.add(logout);
            logout.addActionListener((e ->
            {
                frame.dispose();
                new Login();
            }));
        }
        //Complaints button
        {
            complaints = new JButton("Complaints");
            complaints.setFont(labels2);
            complaints.addActionListener(e -> {
                new Complaint(username);
                frame.dispose();
            });
            panel.add(complaints);
            complaints.setBounds(600,600,complaints.getPreferredSize().width,complaints.getPreferredSize().height);
        }
        {
            JButton web = new JButton("Web Checkin");
            web.setFont(labels2);
            web.setBounds(300,600,web.getPreferredSize().width,web.getPreferredSize().height);
            web.addActionListener(e -> {
                new check_in(userID1);
                frame.dispose();
            });
            panel.add(web);
        }

        {
            ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\homepage.jpg");
            Image img = background.getImage();
            Image temp = img.getScaledInstance(800, 700, Image.SCALE_DEFAULT);
            background = new ImageIcon(temp);
            JLabel back = new JLabel(background);
            back.setLayout(null);
            back.setBounds(0, 0, 800, 700);
            panel.add(back);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home("Tanveeshs"));

    }

    private void checkFunc() throws SQLException {
        int r=0;
            //To check if Source and Destination ain't the same
            if (source.getSelectedItem() == destination.getSelectedItem()) {
                JDialog d = new JDialog(frame, "Error");
                JLabel l = new JLabel("Source and Destination can't be same");
                l.setFont(labels);
                d.setLocation(200, 200);
                d.add(l);
                d.setSize(400, 200);
                d.setVisible(true);
                r=1;
            }


            //To check is return date and onward date are not null
            else if(picker.getDate()==null)
            {
                JDialog d = new JDialog(frame, "Error");
                JLabel l = new JLabel("Fill in the onward date");
                l.setFont(labels);
                d.setLocation(200, 200);
                d.add(l);
                d.setSize(400, 200);
                d.setVisible(true);r=1;
            }
            else if(returnj.isSelected())
            {
                if(picker1.getDate()==null)
                {
                    JDialog d = new JDialog(frame, "Error");
                    JLabel l = new JLabel("Fill in the return date");
                    l.setFont(labels);
                    d.setLocation(200, 200);
                    d.add(l);
                    d.setSize(400, 200);
                    d.setVisible(true);
                    r=1;
                }
            }

        //To check if onward date is greater than today

            if(r==0) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                int a = picker.getDate().compareTo(cal.getTime());
                boolean z = true;
                if (a > 0) {
                    z = false;
                }
                if (a == 0) {
                    z = false;
                }
                if (z) {
                    JDialog d2 = new JDialog(frame, "Error");
                    JLabel l = new JLabel("Onward date should be greater than today");
                    l.setFont(labels);
                    d2.setLocation(200, 200);
                    d2.add(l);
                    d2.setSize(500, 200);
                    d2.setVisible(true);
                    r = 1;
                }
            }


            //To check if Return date is greater than onward date

            if(r==0) {
                if (returnj.isSelected()) {
                    int a = picker1.getDate().compareTo(picker.getDate());
                    boolean y = true;
                    if (a > 0) {
                        y = false;
                    }
                    if (a == 0) {
                        y = false;
                    }
                    if (y) {
                        JDialog d1 = new JDialog(frame, "Error");
                        JLabel l = new JLabel("Return Date can't be before onward date");
                        l.setFont(labels);
                        d1.setLocation(200, 200);
                        d1.add(l);
                        d1.setSize(500, 200);
                        d1.setVisible(true);
                        r = 1;
                    }
                }
            }
            if(r==0) {

                if (returnj.isSelected()) {
                    new FindFlights(String.valueOf(source.getSelectedItem()),String.valueOf(destination.getSelectedItem()),onDay,onDate,reDay,reDate,String.valueOf(pax.getSelectedItem()),userID1);
                }
                else
                {
                    new FindFlights(String.valueOf(source.getSelectedItem()),String.valueOf(destination.getSelectedItem()),onDay,onDate,null,null,String.valueOf(pax.getSelectedItem()),userID1);
                }
                frame.dispose();
            }

    }
}