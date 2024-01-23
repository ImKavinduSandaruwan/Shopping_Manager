package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends JFrame implements ActionListener{

    private JButton registerButton;
    private JButton loginButton;

    public static void main(String[] args) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.setSize(500,500);
        welcomeScreen.setBackground(Color.white);
        welcomeScreen.setLocationRelativeTo(null);
        welcomeScreen.setVisible(true);
        welcomeScreen.setResizable(false);
        welcomeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton){
            new Register();
        }
        if(e.getSource() == loginButton){
            new Login();
        }
    }

    public WelcomeScreen(){
        setLayout(null);
        setTitle("Welcome to the Westminster Shopping Center 20221150");
        JLabel welcomeText = new JLabel("Welcome to the Westminster Shopping Center");
        welcomeText.setBounds(60,60,500,100);
        welcomeText.setFont(new Font("Serif", Font.PLAIN, 20));
        add(welcomeText);

        registerButton = new JButton("Register");
        registerButton.setBounds(145,170,200,50);
        registerButton.setBackground(Color.white);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(this);
        add(registerButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(145,250,200,50);
        loginButton.setBackground(Color.white);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);
        add(loginButton);
    }
}
