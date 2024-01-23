package user;

import manager.MyObjectOutputStream;
import system.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import static user.UserGUI.productsInTheSystem;

public class ShoppingCartGUI extends JFrame implements ActionListener {
    private ArrayList<Product> productList = new ArrayList<>();
    private ShoppingCart shoppingCart;
    private JTextArea bottomDetails = new JTextArea();
    private String details;
    private double total = 0;
    private User currentUser;
    private JTable table;
    private JPanel bottomPanel;
    private JButton buy;
    private ArrayList<User> usersList;

    public ShoppingCartGUI(ShoppingCart cart, User user) {
        shoppingCart = cart;
        currentUser = user;
        lordUserProfile(user);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(700,700);
        setTitle("ShoppingCart 20221150" + " Username: " + currentUser.getUserName());
        setLayout(new GridLayout(2,1));
        setLocationRelativeTo(null);
        setResizable(false);

        //Create tabel
        String[] columnNames = {"Product", "Quantity", "Price"};
        HashMap<ProductKey, Integer> products = new HashMap<>();

        for (Product product : shoppingCart.getShoppingCart()) {
            ProductKey key = new ProductKey(product.getProductName(), product.getPrice());
            products.put(key, products.getOrDefault(key, 0) + 1);
        }

        Object[][] data = new Object[products.size()][3];
        int index = 0;

        for (Map.Entry<ProductKey, Integer> entry : products.entrySet()) {
            data[index][0] = entry.getKey().getProductName();
            data[index][1] = entry.getValue();
            data[index][2] = entry.getKey().getPrice();
            index++;
        }

        this.table = new JTable(data, columnNames);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        table.setFont(font);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(650, 150));
        table.setRowHeight(60);

        //Setup bottom details panel
        bottomDetails.setFont(new Font("Times new Roman", Font.PLAIN, 20));
        details = String.format("\n\n%" + 78 + "s", "Total - \t" + shoppingCart.getTotalCost())
                + "\n\n" + String.format("%" + 60 + "s", "First Purchase Discount(10%) - \t" + checkFirstPurchaseDiscount())
                + "\n\n" + String.format("%" + 54 + "s", "Three items in the same category(20%) - \t" + checkThreeItemDiscount())
                + "\n\n" + String.format("%" + 75 + "s", "Final Total - \t" + getFinalCost());

        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.white);
        bottomPanel.setLayout(null);
        bottomDetails.setText(details);
        bottomPanel.add(bottomDetails);
        bottomDetails.setBounds(60,10,800,250);

        this.buy = new JButton("Buy");
        buy.addActionListener(this);
        buy.setBackground(Color.white);
        bottomPanel.add(buy);
        buy.setBounds(280,290,110,30);

        add(scrollPane);
        add(bottomPanel);
        setVisible(true);
    }

    /**
     *This method is responsible for perform certain tasks when user clicks buttons.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buy){
            JOptionPane.showMessageDialog(null, "Successfully Purchased");
            updateUserProfile();
            updateProductList();
            shoppingCart.getShoppingCart().clear();
        }
    }

    /**
     * This method responsible for check whether user purchased before or not
     * @return -> return 0 if purchased before otherwise return discount
     */
    private double checkFirstPurchaseDiscount(){
        if(currentUser.isPurchasedBefore()) {
            return 0;
        }
        return shoppingCart.getTotalCost() * 0.1;
    }

    /**
     * This method is responsible for check use have selected 3 items in the same category.
     * @return -> return 0 if not otherwise return discount
     */
    private double checkThreeItemDiscount(){
        for (int i = 0; i < table.getRowCount(); i++) {
            Object data = table.getValueAt(i, 1);
            if (data instanceof Integer && (Integer)data >= 3) {
                return shoppingCart.getTotalCost() * 0.2;
            }
        }
        return 0.0;
    }

    /**
     * This method is responsible for calculate final cost including discounts.
     * @return -> final cost
     */
    private double getFinalCost(){
        return shoppingCart.getTotalCost() - (checkFirstPurchaseDiscount()
            + checkThreeItemDiscount());
    }

    /**
     * This method is responsible for load users data from the file.
     * @param user -> current user
     */
    private void lordUserProfile(User user){
        this.usersList = new ArrayList<>();
        try {
            FileInputStream fi = new FileInputStream("user-data.txt");
            ObjectInputStream oi = new ObjectInputStream(fi);
            while (true){
                try {
                    User object = (User)oi.readObject();
                    this.usersList.add(object);
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
        for(User u : usersList){
            if(u.getUserName().equals(user.getUserName())){
                currentUser = u;
            }
        }
    }

    /**
     * This method is responsible for change users purchased or not value and
     * update the file
     */
    private void updateUserProfile(){
        for(User user : usersList){
            if(user.getUserName().equals(currentUser.getUserName())){
                user.setPurchasedBefore(true);
                break;
            }
        }
        try {
            FileOutputStream fs = new FileOutputStream("user-data.txt");
            ObjectOutputStream os;
            if (fs.getChannel().position() == 0) {
                os = new ObjectOutputStream(fs);
            } else {
                os = new MyObjectOutputStream(fs);
            }
            User user = null;
            Iterator<User> iterator = usersList.iterator();
            while (iterator.hasNext()) {
                user = iterator.next();
                os.writeObject(user);
                iterator.remove();
            }
            os.close();
            fs.close();
            System.out.println("Successfully Completed\n");
        } catch (IOException e){
            System.out.println("Cannot write to the file.");
        }
    }

    /**
     * When user buy products this method will reduce that amount from the system.
     */
    private void updateProductList(){
        for(Product productInCart : shoppingCart.getShoppingCart()){
            for(Product productInTheSystem : productsInTheSystem){
                if(productInCart.getProductId().equals(productInTheSystem.getProductId())){
                    productInTheSystem.setNumberOfAvailableItems(productInTheSystem.getNumberOfAvailableItems()-1);
                }
            }
        }
    }
    private static class ProductKey {
        private final String productName;
        private final double price;

        public ProductKey(String productName, double price) {
            this.productName = productName;
            this.price = price;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductKey that = (ProductKey) o;
            return Double.compare(that.price, price) == 0 && Objects.equals(productName, that.productName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productName, price);
        }
    }
}