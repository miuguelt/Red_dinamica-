/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controllers.util.JsfUtil;
import java.util.Date;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author DANYAL
 */
@ManagedBean
@SessionScoped
public class Mail {

    private String to = "miuguel_1989@hotmail.com";
    private String from;
    private String message;
    private String subject = "try";
    private String smtpServ;
    private String compte;
    private String motpasse;

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getMotpasse() {
        return motpasse;
    }

    public void setMotpasse(String motpasse) {
        this.motpasse = motpasse;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSmtpServ() {
        return smtpServ;
    }

    public void setSmtpServ(String smtpServ) {
        this.smtpServ = smtpServ;
    }

    public void sendMail() {
        try {
            Properties props = System.getProperties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.starttls.enable", true);

            this.setFrom("Java.Mail.CA@gmail.com");
            this.setSmtpServ("smtp.gmail.com");
            props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

            props.put("mail.smtp.host", smtpServ);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", true);

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
////             -- Create a new message --
            Message msg = new MimeMessage(session);
//            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));
            // -- We could include CC recipients too --
            // if (cc != null)
            // msg.setRecipients(Message.RecipientType.CC
            // ,InternetAddress.parse(cc, false));
            // -- Set the subject and body text --
            msg.setSubject(subject);
            msg.setText(message);
//            // -- Set some other header information --
            msg.setHeader("Red Dinamica", "Comunidad Colombiana");
            msg.setSentDate(new Date());
//            // -- Send the message --
            Transport.send(msg);
            JsfUtil.addSuccessMessage("Correo de notificación enviada");

        } catch (Exception ex) {
            JsfUtil.addSuccessMessage("error" + ex.getMessage());
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "red.dinamica.uis@gmail.com";
            String password = "reddinamicauis";
            return new PasswordAuthentication(username, password);
//            return new PasswordAuthentication(getCompte(), getMotpasse());
        }
    }
}