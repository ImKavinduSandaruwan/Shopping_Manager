package user;

import manager.MyObjectOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Register extends JFrame implements ActionListener {

    private JTextField userNameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JButton backToMain;
    private JButton register;

    public static void main(String[] args) {
        Register register = new Register();
    }

    /**
     * This method is responsible for perform certain tasks when user clicks buttons.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backToMain) this.dispose();

        if(e.getSource() == register){
            User user = new User(userNameField.getText(),
                    new String(passwordField.getPassword()));
            saveUserData(user);
            JOptionPane.showMessageDialog(null, "Completed");
            reset();
        }
    }

    /**
     * This method is responsible for save user data to the system
     * @param user -> object that need to be saved
     */
    private void saveUserData(User user){
        try {
            FileOutputStream fs = new FileOutputStream("user-data.txt", true);
            ObjectOutputStream os;
            if (fs.getChannel().position() == 0) {
                os = new ObjectOutputStream(fs);
            } else {
                os = new MyObjectOutputStream(fs);
            }
            os.writeObject(user);
            os.close();
            fs.close();
            System.out.println("Successfully Completed\n");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for rest input fields.
     */
    private void reset(){
        nameField.setText("");
        userNameField.setText("");
        passwordField.setText("");
    }

    public Register(){
        setLayout(null);
        setTitle("Register 20221150");
        setSize(500,500);
        setBackground(Color.white);
        setLocationRelativeTo(null);

        JLabel registerText = new JLabel("Register to the system");
        registerText.setBounds(130,60,500,100);
        registerText.setFont(new Font("Serif", Font.PLAIN, 25));
        add(registerText);

        JLabel name = new JLabel("Enter your First Name: ");
        name.setBounds(40,150,200,100);
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        add(name);

        JLabel userName = new JLabel("Enter your User name: ");
        userName.setBounds(40,210,200,100);
        userName.setFont(new Font("Serif", Font.PLAIN, 20));
        add(userName);

        JLabel password = new JLabel("Enter your password: ");
        password.setBounds(40,270,200,100);
        password.setFont(new Font("Serif", Font.PLAIN, 20));
        add(password);

        nameField = new JTextField();
        nameField.setBounds(240,190,200,30);
        add(nameField);

        userNameField = new JTextField();
        userNameField.setBounds(240,250,200,30);
        add(userNameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(240,310,200,30);
        add(passwordField);

        backToMain = new JButton("Back to Main Menu");
        backToMain.setBounds(70,400,150,30);
        backToMain.setBackground(Color.white);
        backToMain.setFocusPainted(false);
        add(backToMain);
        backToMain.addActionListener(this);

        register = new JButton("Register");
        register.setBounds(260,400,150,30);
        register.setBackground(Color.white);
        register.setFocusPainted(false);
        add(register);
        register.addActionListener(this);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
