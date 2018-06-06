package UIProtection;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class protection extends JFrame {

    private static protection frame;
    private User user;

    public static void main(String[] args) {
        //create a login / register
        frame = new protection();
        frame.setLayout(null);
        frame.setSize(300,100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public protection() {
        JFrame frame = new JFrame("Welcome");
        JPanel panel = new JPanel();
        JButton jLogin = new JButton("Login");
        JButton jRegister = new JButton("Register");
        panel.setSize(300,300);
        panel.add(jLogin);
        panel.add(jRegister);
        add(panel);

        //add action listeners
        jLogin.addActionListener(new actionLogin());
        jRegister.addActionListener(new actionRegister());
    }

    class actionLogin implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            //call the login method
            login loginFrame = new login();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setSize(200,150);
            loginFrame.setResizable(false);
            loginFrame.setVisible(true);

        }
    }

    class actionRegister implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            // call the register method
            register  registerFrame = new register();
            registerFrame.setLocationRelativeTo(null);
            registerFrame.setSize(200,200);
            registerFrame.setResizable(false);
            registerFrame.setVisible(true);
        }
    }


    static class User{


    }

    class login extends  JFrame{
        login(){
            JLabel jlblUsername = new JLabel("Username:");
            JLabel jlblPw = new JLabel("Password:");
            JTextField username = new JTextField();
            JPasswordField pw = new JPasswordField();
            JButton jbSignIn = new JButton("Sign in");
            JPanel panel = new JPanel();

            username.setPreferredSize(new Dimension(110,20));
            pw.setPreferredSize(new Dimension(110,20));
            panel.add(jlblUsername);
            panel.add(username);
            panel.add(jlblPw);
            panel.add(pw);
            panel.add(jbSignIn);
            add(panel);
        }
    }

    class register extends  JFrame{
        register(){
            JLabel jlblUsername = new JLabel("Username:");
            JLabel jlblpw = new JLabel("Password:");
            JLabel jlblVerifyPW = new JLabel("Password Verify:");
            JTextField jtUsername = new JTextField();
            JTextField jtPw = new JTextField();
            JTextField jtVerifypw = new JTextField();
            JPanel panel = new JPanel();
            JButton jSubmit= new JButton("Submit");

            //set sized for textfields
            jtPw.setPreferredSize(new Dimension(110,20));
            jtUsername.setPreferredSize(new Dimension(110,20));
            jtVerifypw.setPreferredSize(new Dimension(110,20));
            jSubmit.setPreferredSize(new Dimension(150,50));
            panel.add(jlblUsername);
            panel.add(jtUsername);
            panel.add(jlblpw);
            panel.add(jtPw);
            panel.add(jlblVerifyPW);
            panel.add(jtVerifypw);
            panel.add(jSubmit);
            add(panel);
        }
    }

    public void menu(){

    }

    class menu extends  JFrame{
        menu(){

        }
    }
}
