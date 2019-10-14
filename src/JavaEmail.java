import java.sql.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaEmail {

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;
    ResultSet rs;
    String src,dest;
    void sendMail(String email,String bkid) throws AddressException,
            MessagingException, SQLException {

        JavaEmail javaEmail = new JavaEmail();

        javaEmail.setMailServerProperties();
        javaEmail.createEmailMessage(email,bkid);
        javaEmail.sendEmail();
    }

    public void setMailServerProperties() {

        String emailPort = "587";//gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

    }

    public void createEmailMessage(String to,String bkid) throws AddressException,
            MessagingException, SQLException {
        String[] toEmails = { to };
        String emailSubject = "Booking Confirmed";
        String s = "SELECT SOURCE,DEST FROM BOOKING WHERE BOOKINGID LIKE'"+bkid+"'";
        rs = excecuteSQL(s);
        while (rs.next())
        {
            src = rs.getString(1);
            dest = rs.getString(2);
        }
        String emailBody = "<h1>Congratulations! Your booking has been confimed with booking ID:"+bkid+"</h1><br> We hope to see you aboard<br><br>"+
                "From:"+src+"<br>To:"+dest+
                "<br><br> Copyright Ariline Reservations 2019";

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");//for a html email

    }

    public void sendEmail() throws AddressException, MessagingException {

        String emailHost = "smtp.gmail.com";
        String fromUser = "airlineres121";//just the id alone without @gmail.com
        String fromUserEmailPassword = "nivingnnvslyhswv";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
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