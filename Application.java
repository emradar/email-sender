// Emir Adar
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

public class Application extends JFrame {

    private static final String host = "smtp.gmail.com";
    private static final int port = 587;
    private static final JTextArea chatArea = new JTextArea();
    private static JTextField toField;
    private static JTextField subjectField;
    private static JTextField hostField;
    private static JTextField portField;
    private static JTextField userNameField;
    private static JPasswordField passwordField;
    private JButton sendBtn;

    public Application() throws IOException {
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(700, 500);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        toField = new JTextField();
        subjectField = new JTextField();
        hostField = new JTextField();
        hostField.setText(host);
        hostField.setEditable(false);
        portField = new JTextField();
        portField.setText(String.valueOf(port));
        portField.setEditable(false);
        userNameField = new JTextField();
        passwordField = new JPasswordField();
        sendBtn = new JButton("Send");
        sendBtn.addActionListener(new Send());

        panel.add(new JLabel("To:"));
        panel.add(toField);
        panel.add(new JLabel("Subject:"));
        panel.add(subjectField);
        panel.add(new JLabel("Host:"));
        panel.add(hostField);
        panel.add(new JLabel("Port:"));
        panel.add(portField);
        panel.add(new JLabel("Username:"));
        panel.add(userNameField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        panel.add(new JLabel("Send"));
        panel.add(sendBtn);

        this.add(panel, BorderLayout.NORTH);
        chatArea.setAutoscrolls(true);
        this.add(new JScrollPane(chatArea));
        this.setVisible(true);
    }

    static class Send implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent ae) {
            String to = toField.getText();
            String from = userNameField.getText();
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userNameField.getText(), new String(passwordField.getPassword()));
                }
            });

            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subjectField.getText());
                message.setText(chatArea.getText());
                Transport.send(message);
            } catch (MessagingException e) {
                System.out.println("could not send the email" + e.getMessage());
            }
        }
    }
}