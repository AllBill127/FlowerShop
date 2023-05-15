package PSK.FlowerShop.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Console;
import java.util.Properties;

@Service
public class EmailService {
    final String username = "kingjustuxs@gmail.com";
    final String password = "rylgxcezlnivlmxy";

    public String makeCreateOrderText (String orderID){
        return "Hello, we would like to inform you that your order has been created." +
                "\nYou can watch it here -> http://localhost:3000/order/"+orderID;
    }

    @Async
    public void sendMail(String email_to, String subject,  String text) {


        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email_to)
            );
            message.setSubject(subject);
            message.setText(text);
            System.out.println("Sending email to "+email_to);

            Transport.send(message);
            System.out.println("Email has been sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}