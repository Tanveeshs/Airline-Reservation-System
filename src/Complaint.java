import oracle.sql.NUMBER;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Random;

public class Complaint {
    JLabel complaint_label;
    JLabel comp;
    JLabel type_label;
    JTextArea complaint;
    JTextField name;
    JLabel name_label;
    JLabel contact_label;
    JTextField contact;
    JFrame frame;
    JLayeredPane panel;
    JTextField username;
    JLabel username_label;
    JComboBox query;
    JButton submit,cancel;
    Connection conn;
    PreparedStatement psd;
    ResultSet rs;
    boolean exception = true;
    Font labels = new Font("Arial",Font.BOLD,20);
    Font f1 = new Font("Times New Roman",Font.BOLD,40);
    Font f = new Font("Serif", Font.PLAIN, 20);
    Complaint(String userid)
    {
        frame = new JFrame("Complaints");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = frame.getLayeredPane();
        panel.setLayout(null);
        frame.setVisible(true);
        frame.setSize(800,700);


        {
            username_label = new JLabel("Username:");
            panel.add(username_label);
            username_label.setFont(f);
            username_label.setBounds(200,100,username_label.getPreferredSize().width,username_label.getPreferredSize().height);
        }
        {
            comp = new JLabel("Complaints");
            comp.setFont(f1);
            comp.setBounds(250, 40, comp.getPreferredSize().width, comp.getPreferredSize().height);
            panel.add(comp);
        }
            //Username Field

        //Name label
        {
            name_label = new JLabel("Full Name:");
            panel.add(name_label);
            name_label.setFont(f);
            name_label.setBounds(200,140,name_label.getPreferredSize().width,name_label.getPreferredSize().height);
        }
        //Name TextField
        {
            name = new JTextField(15);
            panel.add(name);
            name.setBounds(350,145,name.getPreferredSize().width,name.getPreferredSize().height);
        }
        {
            username = new JTextField(20);
            username.setText(userid);
            username.setEnabled(false);
            panel.add(username,JLayeredPane.POPUP_LAYER);
            username.setBounds(350, 105, name.getPreferredSize().width, username.getPreferredSize().height);
        }
        //Contact Number
        {
            contact_label = new JLabel("Contact Number:");
            panel.add(contact_label);
            contact_label.setFont(f);
            contact_label.setBounds(200,180,contact_label.getPreferredSize().width,contact_label.getPreferredSize().height);
        }
        //Contact Number field
        {
            contact = new JTextField(15);
            panel.add(contact);
            contact.setBounds(350,185, contact.getPreferredSize().width,contact.getPreferredSize().height);
        }

        //Query About
        {
            type_label = new JLabel("Complaint type:");
            panel.add(type_label);
            type_label.setFont(f);
            type_label.setBounds(200,220,type_label.getPreferredSize().width,type_label.getPreferredSize().height);
        }
        //JComboBox
        {
            String queries[] = {"Bookings","Feedback","Service","Payment"};
            query = new JComboBox(queries);
            panel.add(query);
            query.setBounds(350,225,name.getPreferredSize().width,query.getPreferredSize().height);

        }
        //Complaint_label
        {
            complaint_label = new JLabel("Complaint:");
            panel.add(complaint_label);
            complaint_label.setFont(f);
            complaint_label.setBounds(200, 260, complaint_label.getPreferredSize().width, complaint_label.getPreferredSize().height);
        }
        //Complaint TextBox
        {
            complaint = new JTextArea(5,20);
            panel.add(complaint);
            complaint.setBounds(350,270,complaint.getPreferredSize().width,complaint.getPreferredSize().height);
        }
        //Button-Submit complaint
        {
            submit= new JButton("Submit");
            panel.add(submit);
            submit.setBounds(200,370,submit.getPreferredSize().width,submit.getPreferredSize().height);
            submit.addActionListener(e->submitfunc());
        }
        //cancel-Submit complaint
        {
            cancel= new JButton("Return to Home Page");
            panel.add(cancel);
            cancel.setBounds(320,370,cancel.getPreferredSize().width,cancel.getPreferredSize().height);
            cancel.addActionListener(e->{
                new Home(userid);
                frame.dispose();
            });
        }
        //Background
        {
            ImageIcon background = new ImageIcon("C:\\Users\\Tanveesh\\IdeaProjects\\AirlineReservationSystem\\src\\complaints.jpg");
            Image img = background.getImage();
            Image temp = img.getScaledInstance(800, 700, Image.SCALE_SMOOTH);
            background = new ImageIcon(temp);
            JLabel back = new JLabel(background);
            back.setLayout(null);
            back.setBounds(0, 0, 800, 700);
            panel.add(back);
        }

    }
    void submitfunc()
    {
        if(name.getText().equals("")){
            JDialog d2 = new JDialog(frame, "Error");
            JLabel l = new JLabel("Name Can't be empty");
            l.setFont(labels);
            d2.setLocation(200, 200);
            d2.add(l);
            d2.setSize(500, 200);
            d2.setVisible(true);

        }
        else if(complaint.getText().equals(""))
        {
            JDialog d2 = new JDialog(frame, "Error");
            JLabel l = new JLabel("Complaint Can't be empty");
            l.setFont(labels);
            d2.setLocation(200, 200);
            d2.add(l);
            d2.setSize(500, 200);
            d2.setVisible(true);

        }
        else
        {
            Random rand = new Random();
            int complaintID = rand.nextInt(90000)+1000000;

            try
            {
                exception = true;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "AIRLINERESERVATION", "Tanveesh21");
                String sql = "insert into Complaint values ('"+username.getText()+"','"+name.getText()+"',"+contact.getText()+",'"+query.getSelectedItem()+"','"+complaint.getText()+"','"+complaintID+"')";
                System.out.println(sql);
                psd=conn.prepareStatement(sql);
                rs = psd.executeQuery();
            }catch (Exception e)
            {
                exception = false;
            }
            finally {
                if(exception){
                    JDialog d2 = new JDialog(frame, "Complaint Recieved");
                    JLabel l = new JLabel("Complaint id:"+complaintID);
                    JButton b = new JButton("Back to home page");
                    b.setBounds(230,110,b.getPreferredSize().width,b.getPreferredSize().height);
                    d2.setLayout(null);
                    l.setFont(labels);
                    l.setBounds(20,40,l.getPreferredSize().width,l.getPreferredSize().height);
                    d2.setLocation(200, 200);
                    d2.add(l);
                    d2.add(b);
                    b.addActionListener(e -> {
                        frame.dispose();
                        new Home(username.getText());
                    });
                    d2.setSize(500, 200);
                    d2.setVisible(true);
                }
                else
                {
                    JDialog d2 = new JDialog(frame, "Complaint Error");
                    JLabel l = new JLabel("Complaint could not be registered");
                    l.setFont(labels);
                    d2.setLocation(200, 200);
                    d2.add(l);
                    d2.setSize(500, 200);
                    d2.setVisible(true);

                }
            }
        }
    }

    public static void main(String args[])
    {
        new Complaint("Tanveeshs");
    }
}
