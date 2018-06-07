package UIProtection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class protection extends JFrame {

    private static protection frame;
    private static login loginFrame;
    private static register registerFrame;
    private static menu menuFrame;
    private User user;
    private int option=0;
    final int LOGIN =1;
    final int REGISTER = 0;

    //MAIN MAIN MAIN MAIN MAIN
    public static void main(String[] args) {
        //create a login / register
        frame = new protection();
        frame.setLayout(null);
        frame.setTitle("Welcome");
        frame.setSize(330,100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //INITIANLIZER FOR THE PROGRAM
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


    //USER OBJECT
    static class User{
        private  String id;
        private String password;
        File file = new File("users.txt");
        private type typeUser ;

        //LOGIN
        public User(String username, String pw) throws IOException{
            if(file.exists() ==false){
                JOptionPane.showMessageDialog(null,"[ERROR]\n\n users.txt " +
                        "does not exist, please register");
                System.exit(0);
            }

            Scanner read = new Scanner(file);
            String temp;

            while (read.hasNext()){
                temp=read.nextLine();
                //Substring find pos of username "{'username'}  [pw]  category"
                if (temp.substring(0, temp.indexOf(" ")).toUpperCase().equals(username.toUpperCase())) {
                    //check password (case sensitive)
                    if (!temp.substring(temp.indexOf("[") + 1, temp.indexOf("]")).equals(pw))
                    {
                        JOptionPane.showMessageDialog(null,"[ERROR]\n\n Password incorrect please enter correct password");
                        System.exit(1);
                    }
                    setCategory(temp);
                    this.id = username;
                    this.password = pw;
                    break;
                }
            }
        }

        //REGISTER
        public User(String username,String pw,type t)throws  IOException {
            if (file.exists()==false){
                //CREATE NEW FILE
                file.createNewFile();
            }else{
                //DOES THE USER EXIST

                if (userExist(username)){
                   JOptionPane.showMessageDialog(null,"[ERROR USER EXIST]\n\nPlease use a new username " +
                           " or Login");
                   System.exit(1);
                }
            }

            //WRITE TO THE END OF THE FILE
            BufferedWriter bw= new BufferedWriter(new FileWriter(file,true));
            //error
            bw.write(username+ " ["+pw+"] "+ t.toString());
            bw.newLine();
            bw.close();

            this.id = username;
            this.password = pw;
            this.typeUser=t;
        }

        //SET THE TYPE OF CATEGORY OF THE USER
        private void setCategory(String user ) {
            this.typeUser=determineType(user.substring(user.indexOf("]")+2,user.length()));
        }

        //DOES THE USER EXIST IN THE FILE
        private boolean userExist (String username) throws FileNotFoundException {
            Scanner read = new Scanner(file);
            String temp;

            while (read.hasNext()) {
                temp = read.nextLine();

                //Substring find pos of username "{'username'}  [pw]  category"
                temp = temp.substring(0, temp.indexOf(" "));
                if (temp.toUpperCase().equals(username.toUpperCase())) {
                    return true;
                }
            }//end while
            return false;
        }

        //CAN THE USER PLAY GAMES
        private boolean canPlay(){
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("HH");
            String time = format.format(date);
            int currentTime = Integer.parseInt(time);

            //JOptionPane.showMessageDialog(null,currentTime);
            switch (typeUser){
                case STUDENT: if (currentTime>=22 || currentTime<=6){
                    return true;
                }
                    break;
                case FACULTY_MEMBER:if (currentTime>=17 || currentTime<=8){
                    return true;
                }
                    break;
                case STAFF: return true;
            }
            return  false;
        }
    }

    //CLICK TO GO TO LOGIN FRAME
    class actionLogin implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            option=1;
            //call the login method
            loginFrame = new login();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setSize(200,150);
            loginFrame.setTitle("Login");
            loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            loginFrame.setResizable(false);
            loginFrame.setVisible(true);
        }
    }

    //CLICK TO GO TO REGISTER FRAME
    class actionRegister implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            option=0;
            // call the register method
            registerFrame = new register();
            registerFrame.setLocationRelativeTo(null);
            registerFrame.setTitle("Register");
            registerFrame.setSize(200,220);
            registerFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            registerFrame.setResizable(false);
            registerFrame.setVisible(true);
        }
    }

    //LOGIN FRAME
    class login extends  JFrame{
        JPasswordField pw;
        JTextField username;
        login(){
            JLabel jlblUsername = new JLabel("Username:");
            JLabel jlblPw = new JLabel("Password:");
            username = new JTextField();
            pw = new JPasswordField();
            JButton jbSignIn = new JButton("Sign in");
            JPanel panel = new JPanel();
            jbSignIn.addActionListener(new signin());

            username.setPreferredSize(new Dimension(110,20));
            pw.setPreferredSize(new Dimension(110,20));
            panel.add(jlblUsername);
            panel.add(username);
            panel.add(jlblPw);
            panel.add(pw);
            panel.add(jbSignIn);
            add(panel);
        }

        class signin implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                //create a login
                String password= pw.getText();
                try {
                    //JOptionPane.showMessageDialog(null,password);
                    user = new User(username.getText().toString(),password.toString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                menuMethod();
            }
        }
    }
    //REGISTER FRAME
    class register extends  JFrame{
        JTextField jtUsername;
        JTextField jtPw;
        JTextField jtVerifypw;
        JComboBox select;

        register(){
            JLabel jlblUsername = new JLabel("Username:");
            JLabel jlblpw = new JLabel("Password:");
            JLabel jlblVerifyPW = new JLabel("Password Verify:");
            jtUsername = new JTextField();
            jtPw = new JTextField();
            jtVerifypw = new JTextField();
            JPanel panel = new JPanel();
            JButton jSubmit= new JButton("Submit");
            String[] types = { "Student", "Faculty Member","Staff" };
            select = new JComboBox(types);
            select.setSelectedIndex(1);
            select.addActionListener( new selection());

            jSubmit.addActionListener(new registerNewUser());

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
            panel.add(select);
            panel.add(jSubmit);
            add(panel);
        }

        class selection implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
            }
        }

        class registerNewUser implements  ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtPw.getText().equals(jtVerifypw.getText())){
                    JOptionPane.showMessageDialog(null,"[ERROR] password does not match");
                    System.exit(1);
                }
                //JOptionPane.showMessageDialog(null,select.getSelectedItem().toString());
                try {
                    user = new User(jtUsername.getText(),jtPw.getText(),determineType(select.getSelectedItem().toString()));
                    JOptionPane.showMessageDialog(null,"[SUCCESS]\n\n" +
                            "Account succesfully created");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                menuMethod();
            }
        }
    }

    //CALL THE MENU METHOD
    public void menuMethod(){
        if (option==LOGIN){
            loginFrame.dispose();
        }else if (option==REGISTER) {
            registerFrame.dispose();
        }
        menuFrame = new menu();
        menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuFrame.setSize(200,250);
        menuFrame.setTitle("Menu");
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }

    //MENU FRAME
    class menu extends  JFrame{
        //
        menu(){
            JButton jbtPlay = new JButton("Play Game");
            JButton jbtbrowser = new JButton("Open Browser");
            JButton jbtLogout = new JButton("Logout");
            JPanel panel = new JPanel();
            panel.add(jbtPlay);
            panel.add(jbtbrowser);
            panel.add(jbtLogout);
            add(panel);
            jbtPlay.addActionListener(new play());
            jbtbrowser.addActionListener(new browser());
            jbtLogout.addActionListener(new logout());
        }

        //button actions
        class play implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.canPlay()){
                    JOptionPane.showMessageDialog(null,"[ACCESS]\n\nThe user can play games");
                }else{
                    JOptionPane.showMessageDialog(null,"[DENIED]\n\nThe user can not play games");
                }
            }
        }

        class logout implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
            int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
            int dialogRes = JOptionPane.showConfirmDialog(null,"Are you sure" +
                    " you want to logout?","Warning",dialogButton);

            if (dialogRes==JOptionPane.YES_OPTION){
                menuFrame.dispose();
                System.exit(1);
            }
        }
    }

        class browser implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();

                try {
                    desktop.browse(new URI("http://baidu.com"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static type determineType(String s){
        if(s.equals("STUDENT")){
            return type.STUDENT;
        }else  if (s.equals("FACULTY_MEMBERS")){
            return type.FACULTY_MEMBER;
        }else {
            return type.STAFF;
        }
    }
}

enum type{

    STUDENT,
    FACULTY_MEMBER,
    STAFF;
}
