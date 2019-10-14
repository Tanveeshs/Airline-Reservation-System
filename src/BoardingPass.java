import javax.swing.*;
import java.awt.*;
import java.sql.*;

class BoardingPass  /*implements ActionListener*/ {
    JLabel fname,fname1,src, dest, gate, seat, fno, date, time, al_name;
    //JTextField fa,la,sr,de,ga,se,fn,da,ti,aln;
    JFrame frame;
    JLayeredPane panel;
    JButton next;
    int n;
    String[] a = new String[n];
    String[] b = new String[n];
    int i = -1;
    String sno;
    String p_fname[];
    ResultSet rs,rs1;
    int k = 0;
    int j=0;
    String sql1,sql2;
    BoardingPass(int bid,String userID) throws SQLException {
        Font f = new Font("Courier New", Font.PLAIN, 16);
        Font f1 = new Font("Courier New", Font.PLAIN, 12);

        String src1="";
        String dest1="";
        String fno1 = "";
        String date1="";
        String time1="";
        p_fname = new String[10];
        sql1="SELECT FNAME,LNAME FROM PASSENGER WHERE BID LIKE'"+bid+"'";
        System.out.println(sql1);
        rs = excecuteSQL(sql1);
        while (rs.next())
        {
            String first = rs.getString(1);
            String last = rs.getString(2);
            String name = first+" "+last;
            p_fname[j] = name;
            j++;
        }

        sql1 = "SELECT SOURCE,DEST,FL_NUM_ON,ONDATE FROM BOOKING WHERE BOOKINGID LIKE '"+bid+"'";
        System.out.println(sql1);
        rs = excecuteSQL(sql1);
        while (rs.next())
        {
            sql2="SELECT ANAME FROM AIRPORTS WHERE ACODE LIKE '"+rs.getString(1)+"'";
            rs1 = excecuteSQL(sql2);
            rs1.next();
            src1 = rs1.getString(1);
            sql2="SELECT ANAME FROM AIRPORTS WHERE ACODE LIKE '"+rs.getString(2)+"'";
            rs1 = excecuteSQL(sql2);
            rs1.next();
            dest1 = rs1.getString(1);
            fno1 = rs.getString(3);
            date1 = rs.getString(4);
        }

        sql1 = "SELECT DETIME FROM FLIGHTS WHERE FLIGHT_NO LIKE '"+fno1+"'";
        rs = excecuteSQL(sql1);
        rs.next();
        time1 = rs.getString(1);

        frame = new JFrame("Boarding Pass");
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        panel = new JLayeredPane();
        panel = frame.getLayeredPane();

        fname = new JLabel(p_fname[k]);
        fname.setBounds(110, 280, 300, 30);
        fname.setFont(f);
        panel.add(fname);



        src = new JLabel(src1);
        src.setFont(f);
        src.setBounds(110, 325, src.getPreferredSize().width, src.getPreferredSize().height);
        panel.add(src);



        dest = new JLabel(dest1);
        dest.setFont(f);
        dest.setBounds(110, 385, dest.getPreferredSize().width, dest.getPreferredSize().height);
        panel.add(dest);



        fno = new JLabel(fno1);
        fno.setBounds(280, 325, 150, 30);
        fno.setFont(f);
        panel.add(fno);



        date = new JLabel(date1);
        date.setBounds(340, 325, 100, 30);
        date.setFont(f1);
        panel.add(date);



        time = new JLabel(time1);
        time.setBounds(420, 325, 100, 30);
        time.setFont(f);
        panel.add(time);



        double d = Math.random() * 30 + 1;
        int gno = (int) Math.round(d);
        gate = new JLabel(Integer.toString(gno));
        gate.setBounds(110, 430, 60, 30);
        gate.setFont(f);
        panel.add(gate);

        double d1 = Math.random() * 32 + 1;
        int s = (int)Math.round(d1);
        sno = Integer.toString(s)+(char)65;
        System.out.println(sno);
        seat = new JLabel(sno);
        seat.setBounds(350, 430, 110, 30);
        seat.setFont(f);
        panel.add(seat);

        fname1 = new JLabel(p_fname[k]);
        fname1.setBounds(525, 275, 300, 30);
        fname1.setFont(f1);
        panel.add(fname1);

        JLabel src2 = new JLabel(src1);
        src2.setFont(f1);
        src2.setBounds(525, 325, src2.getPreferredSize().width, src2.getPreferredSize().height);
        panel.add(src2);

        JLabel dest2 = new JLabel(dest1);
        dest2.setFont(f1);
        dest2.setBounds(525, 355, dest2.getPreferredSize().width, dest2.getPreferredSize().height);
        panel.add(dest2);

        JLabel fno2 = new JLabel(fno1);
        fno2.setBounds(525, 390, 150, 30);
        fno2.setFont(f1);
        panel.add(fno2);

        JLabel date2 = new JLabel(date1);
        date2.setBounds(580, 390, 100, 30);
        date2.setFont(f1);
        panel.add(date2);

        JLabel gate2 = new JLabel(Integer.toString(gno));
        gate2.setBounds(525, 430, 60, 30);
        gate2.setFont(f1);
        panel.add(gate2);

        JLabel time2 = new JLabel(time1);
        time2.setBounds(660, 390, 100, 30);
        time2.setFont(f1);
        panel.add(time2);

        JLabel seat1 = new JLabel(sno);
        seat1.setBounds(660, 430, 110, 30);
        seat1.setFont(f1);
        panel.add(seat1);

        JButton next = new JButton("Next");
        next.setBounds(500, 550, 100, 25);
        next.setFont(f);
        next.addActionListener(e->{
            if(k<j-1) {
                k++;
                fname.setText(p_fname[k]);
                sno = sno.substring(0,sno.length()-1);
                sno = sno+(char)(65+k);
                seat.setText(sno);
                seat1.setText(sno);
            }
            else
            {   next.setText("Back to HomePage");
                next.addActionListener(e1 -> {
                    new Home(userID);
                });
                next.setBounds(500,550,next.getPreferredSize().width,25);
            }
        });
        panel.add(next);

        ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\BoardingPass.jpg");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(600, 300, Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(100, 200, 600, 300);
        panel.add(back);

        ImageIcon background1 = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\bpass.jpg");
        Image img1 = background1.getImage();
        Image temp1 = img1.getScaledInstance(800, 700, Image.SCALE_DEFAULT);
        background1 = new ImageIcon(temp1);
        JLabel back1 = new JLabel(background1);
        back1.setLayout(null);
        back1.setBounds(0, 0, 800, 700);
        panel.add(back1);

    }

    public static void main(String args[]) throws SQLException {
            new BoardingPass(6383950,"Tanveeshs");
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
