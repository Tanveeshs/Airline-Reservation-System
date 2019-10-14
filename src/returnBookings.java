import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class returnBookings {
    ResultSet result,flightsData;
    private String[] depttime = new String[100];
    private String[] arrtime = new String[100];
    String[] fare = new String[100];
    String[] flight_no = new String[100];
    JFrame frame;
    JButton submit;
    JLayeredPane panel;
    String bookindgID;
    JRadioButton f[] = new JRadioButton[20];
    ButtonGroup b1 = new ButtonGroup();
    JLabel src,dest,dest_place,date;
    String total;
    private int choosen=-1;
    int i=0;
    Font a = new Font("Times New Roman",Font.BOLD,22);
    returnBookings(String source, String destination,String onDay,String onDate,String reDay,String reDate,String pax,String userID,String fare1,String fnoO) throws SQLException {
        //Booking ID Generator
        {
            Integer id = (int)((Math.random() * 9000000)+1000000);
            bookindgID = new String(id.toString());

        }
        System.out.println(bookindgID);
        //frame!
        {
            frame = new JFrame("Search Flights");
            frame.setLayout(null);
            panel = frame.getLayeredPane();
            frame.setSize(800, 700);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        //Source label
        {
            src = new JLabel("Source:");
            src.setFont(a);
            src.setBounds(20, 80, src.getPreferredSize().width, src.getPreferredSize().height);
            panel.add(src);
        }
        //Source place label
        {
            JLabel src_place = new JLabel(destination);
            src_place.setFont(a);
            src_place.setBounds(25 + src.getPreferredSize().width, 80, src_place.getPreferredSize().width, src_place.getPreferredSize().height);
            panel.add(src_place);

        }
        //Destination label
        {
            dest = new JLabel("Destination:");
            dest.setFont(a);
            dest.setBounds(290, 80, dest.getPreferredSize().width, dest.getPreferredSize().height);
            panel.add(dest);
        }
        //Destination label for place
        {
            dest_place = new JLabel(source);
            dest_place.setFont(a);
            dest_place.setBounds(300 + dest.getPreferredSize().width, 80, dest_place.getPreferredSize().width, dest_place.getPreferredSize().height);
            panel.add(dest_place);
        }
        //Date Label
        {
            date = new JLabel("Date");
            date.setFont(a);
            date.setBounds(600,80,date.getPreferredSize().width,date.getPreferredSize().height);
            panel.add(date);
        }
        //Date Value Label
        {
            JLabel dateval = new JLabel(reDate);
            dateval.setFont(a);
            dateval.setBounds(610+date.getPreferredSize().width,80,dateval.getPreferredSize().width,dateval.getPreferredSize().height);
            panel.add(dateval);
        }
        //Labels for Flight Search
        {
            JLabel fno1 = new JLabel("Flight No");
            fno1.setFont(new Font("Arial",Font.BOLD,20));
            fno1.setBounds(38,160,fno1.getPreferredSize().width,fno1.getPreferredSize().height);
            panel.add(fno1);
            JLabel fno2 = new JLabel("Departure");
            fno2.setFont(new Font("Arial",Font.BOLD,20));
            fno2.setBounds(158,160,fno2.getPreferredSize().width,fno2.getPreferredSize().height);
            panel.add(fno2);
            JLabel fno3 = new JLabel("Arrival");
            fno3.setFont(new Font("Arial",Font.BOLD,20));
            fno3.setBounds(278,160,fno3.getPreferredSize().width,fno3.getPreferredSize().height);
            panel.add(fno3);
            JLabel fno4 = new JLabel("Fare");
            fno4.setFont(new Font("Arial",Font.BOLD,20));
            fno4.setBounds(398,160,fno1.getPreferredSize().width,fno1.getPreferredSize().height);
            panel.add(fno4);

        }
        //Displaying FLIGHT DATA
        {
            String sql = "select detime,arrtime,fare,flight_no from flights where onday like '" + reDay + "' AND src like '" + destination + "' AND dest like '" + source + "'";
            System.out.println(sql);
            flightsData = excecuteSQL(sql);

            while (flightsData.next()) {
                depttime[i] = flightsData.getString(1);
                arrtime[i] = flightsData.getString(2);
                fare[i] = flightsData.getString(3);
                flight_no[i] = flightsData.getString(4);
                i++;
            }
            for (int x=0;x<i;x++)
            {
                JLabel fno = new JLabel(flight_no[x]);
                fno.setFont(new Font("Arial",Font.PLAIN,20));
                fno.setBounds(40,200+40*x,fno.getPreferredSize().width,fno.getPreferredSize().height);
                panel.add(fno);
                JLabel deptT = new JLabel(depttime[x]);
                deptT.setFont(new Font("Arial",Font.PLAIN,20));
                deptT.setBounds(160,200+40*x,deptT.getPreferredSize().width,deptT.getPreferredSize().height);
                panel.add(deptT);
                JLabel arrT = new JLabel(arrtime[x]);
                arrT.setFont(new Font("Arial",Font.PLAIN,20));
                arrT.setBounds(280,200+40*x,arrT.getPreferredSize().width,arrT.getPreferredSize().height);
                panel.add(arrT);
                JLabel fareL = new JLabel(fare[x]);
                fareL.setFont(new Font("Arial",Font.PLAIN,20));
                fareL.setBounds(400,200+40*x,fareL.getPreferredSize().width,fareL.getPreferredSize().height);
                panel.add(fareL);
                f[x]=new JRadioButton();
                b1.add(f[x]);
                f[x].setBounds(620,200+40*x,f[x].getPreferredSize().width,f[x].getPreferredSize().height);
                panel.add(f[x]);
            }
            try {
                f[0].setSelected(true);
                checkChosen();
                for (int j = 0; j <i ; j++) {
                    f[j].addActionListener(e -> checkChosen());
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        }
        //Submit Button
        {
            submit = new JButton("Proceed to Passenger Details");
            submit.setFont(new Font("Arial", Font.PLAIN, 15));
            submit.setBounds(400, 600, submit.getPreferredSize().width, submit.getPreferredSize().height);
            panel.add(submit);
            submit.addActionListener(e -> {
                    total = String.valueOf( Long.valueOf(fare1) +Long.valueOf(fare[choosen]));
                new pass_details(source,destination,pax,bookindgID,onDate,reDate,userID,total,fnoO,flight_no[choosen]);
                frame.dispose();
            });
        }

        ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\findFlights.jpg");
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
    public static void main(String[] args) throws SQLException {
        new returnBookings("Mumbai,India","New Delhi,India","Mon","23.10.19",null,"25.10.19","1","Tanveeshs","1500","JK125");
    }
    private void checkChosen() {
        for (int j = 0; j <i ; j++) {
            if(f[j].isSelected())
            {
                choosen = j;
                System.out.println(choosen);
                break;
            }
        }
    }

}
