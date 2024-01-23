package user;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private boolean isPurchasedBefore;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.isPurchasedBefore = false;
    }

    //Getters and Setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPurchasedBefore() {
        return isPurchasedBefore;
    }

    public void setPurchasedBefore(boolean purchasedBefore) {
        isPurchasedBefore = purchasedBefore;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                "Purchased before'" + isPurchasedBefore + '\'' +
                '}';
    }
}

