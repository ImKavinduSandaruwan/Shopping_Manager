package user;

import system.Cloth;
import system.Electronic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Login extends JFrame implements ActionListener {

    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton backToMain;
    private JButton login;
    private List<User> userDetailsInTheSystem;

    /**
     * This method is responsible for perform certain tasks when user clicks buttons.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backToMain) this.dispose();

        if(e.getSource() == login){
            userDetailsInTheSystem.clear();
            loadFromTheSystem();
            if(validateUser(userNameField.getText(),new String(passwordField.getPassword()))){
                new UserGUI(new User(userNameField.getText(),passwordField.getName()));
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Incorrect details", "Error", JOptionPane.ERROR_MESSAGE);
                userNameField.setText("");
                passwordField.setText("");
            }
        }
    }

    /**
     * This method is responsible for load users data from the file and save it
     * in an arraylist called userDetailsInTheSystem.
     */
    private void loadFromTheSystem(){
        try {
            FileInputStream fi = new FileInputStream("user-data.txt");
            ObjectInputStream oi = new ObjectInputStream(fi);
            while (true){
                try {
                    Object object = oi.readObject();
                    if (object instanceof User user) {
                        userDetailsInTheSystem.add(user);
                    }
                } catch (EOFException e){
                    System.out.println();
                    break;
                }
            }
            oi.close();
            fi.close();
        }catch (IOException e){
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.out.println("Error in class conversion...");
        }

    }

    /**
     * This method is responsible for validate data that user entered.
     * @param userName -> username that user entered
     * @param password -> password that user entered
     * @return -> return true if validation completed.
     */
    private boolean validateUser(String userName, String password){
        return isUserNameCorrect(userName) & isPasswordCorrect(password);
    }

    /**
     * This method is responsible for check whether username is correct
     * @param userName -> username needs to check
     * @return -> true if correct
     */
    private boolean isUserNameCorrect(String userName){
        for(User user : userDetailsInTheSystem){
            if(user.getUserName().equalsIgnoreCase(userName)) return true;
        }
        return false;
    }

    /**
     * This method is responsible for check password is correct or not.
     * @param password -> password needs to be checked
     * @return -> true if password is correct.
     */
    private boolean isPasswordCorrect(String password){
        for(User user : userDetailsInTheSystem){
            if(user.getPassword().equalsIgnoreCase(password)) return true;
        }
        return false;
    }

    /**
     * Create basic JFrame with all the relevant components.
     */
    public Login(){
        setLayout(null);
        setTitle("Login 20221150");
        setSize(500,500);
        setBackground(Color.white);
        setLocationRelativeTo(null);
        userDetailsInTheSystem = new ArrayList<>();
        loadFromTheSystem();

        //Login label
        JLabel loginText = new JLabel("Login to the system");
        loginText.setBounds(150,60,500,100);
        loginText.setFont(new Font("Serif", Font.PLAIN, 25));
        add(loginText);

        //Username label
        JLabel userName = new JLabel("Enter your User name: ");
        userName.setBounds(40,150,200,100);
        userName.setFont(new Font("Serif", Font.PLAIN, 20));
        add(userName);

        //Password label
        JLabel password = new JLabel("Enter your password: ");
        password.setBounds(40,210,200,100);
        password.setFont(new Font("Serif", Font.PLAIN, 20));
        add(password);

        //Username field
        userNameField = new JTextField();
        userNameField.setBounds(240,190,200,30);
        add(userNameField);

        //Password field
        passwordField = new JPasswordField();
        passwordField.setBounds(240,250,200,30);
        add(passwordField);

        //Back to main menu button
        backToMain = new JButton("Back to Main Menu");
        backToMain.setBounds(70,400,150,30);
        backToMain.setBackground(Color.white);
        backToMain.setFocusPainted(false);
        add(backToMain);
        backToMain.addActionListener(this);

        //Login button
        login = new JButton("Login");
        login.setBounds(260,400,150,30);
        login.setBackground(Color.white);
        login.setFocusPainted(false);
        add(login);
        login.addActionListener(this);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
