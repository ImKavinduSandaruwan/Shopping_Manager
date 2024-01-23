package user;

import system.Cloth;
import system.Electronic;
import system.Product;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class UserGUI extends JFrame implements ActionListener {
    private JComboBox<String> jComboBox;
    private JLabel selectProduct;
    private JButton shoppingCartButton;
    private JButton addToCartButton;
    private ProductTable tableModel;
    private JTable table;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JTextArea bottomDetails = new JTextArea();
    public static ArrayList<Product> productsInTheSystem = new ArrayList<>();
    private static String fileName = "productDetails.txt";
    private String details = "";
    private Product p;
    private JPanel buttonPanel;
    private ShoppingCart cart;
    private User currentUser;

    public UserGUI(User currentUser){
        loadFromTheSystem();
        setSize(700,700);
        setBackground(Color.white);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.currentUser = currentUser;
        setTitle("Westminster Shopping Center"  + "  Username:- " + currentUser.getUserName());
        setLayout(new BorderLayout());
        cart = new ShoppingCart();

        //Setup 3 panels called top, center and bottom to hold other components.
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,90,30));
        topPanel.setBackground(Color.white);
        centerPanel = new JPanel(new FlowLayout());
        centerPanel.setBackground(Color.white);
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        bottomPanel.setBackground(Color.white);

        //Adding components to the Top panel
        //label
        selectProduct = new JLabel("Select Product Category");
        selectProduct.setFont(new Font("Times new Roman",Font.BOLD,20));
        topPanel.add(selectProduct);
        //combobox
        String[] products = {"All", "Electronic", "Clothing"};
        jComboBox = new JComboBox<>(products);
        jComboBox.setSelectedIndex(0);
        jComboBox.setBackground(Color.white);
        topPanel.add(jComboBox);
        jComboBox.addActionListener(this);
        //shopping cart button
        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.setSize(20,20);
        shoppingCartButton.setBackground(Color.white);
        topPanel.add(shoppingCartButton);
        //Shopping cart button operations
        shoppingCartOperations();

        //Adding components to the Center panel
        //Product Table
        tableModel = new ProductTable(productsInTheSystem, new String[]{"Id", "Name", "Category", "Price", "Info"});
        table = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int numberOfItems = ((ProductTable) dataModel).getProductAt(row).getNumberOfAvailableItems();
                if (numberOfItems < 3) {
                    component.setBackground(Color.RED);
                } else {
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        };
        table.setPreferredScrollableViewportSize(new Dimension(650, 230));
        table.setRowHeight(30);
        JScrollPane pane = new JScrollPane(table);
        table.setGridColor(Color.black);
        centerPanel.add(pane);
        tableOperations();

        //Adding components to the Bottom panel
        //Product Details TextArea
        bottomDetails.setFont(new Font("Times new Roman", Font.PLAIN, 20));
        details = """
                
                    Selected Product - Details
                    Product ID:
                    Category:
                    Name:
                    Price:
                    Brand:
                    Warranty:
                    Items Available:
               
                """;
        bottomDetails.setText(details);
        bottomPanel.add(bottomDetails, BorderLayout.WEST);
        //Add to cart button
        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.setPreferredSize(new Dimension(180, 35));
        addToCartButton.setBackground(Color.white);
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addToCartButton);
        buttonPanel.setBackground(Color.white);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        //add to cart operations
        addToCartOperations();

        //Adding Top,Center and Bottom panels to the JFrame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * This method is responsible for load product data from the file.
     */
    private static void loadFromTheSystem(){
        try {
            FileInputStream fi = new FileInputStream(fileName);
            ObjectInputStream oi = new ObjectInputStream(fi);
            while (true){
                try {
                    Object object = oi.readObject();
                    if(object instanceof Electronic){
                        productsInTheSystem.add((Electronic) object);
                    }else if(object instanceof Cloth){
                        productsInTheSystem.add((Cloth) object);
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
     * This method is responsible for show all the products
     * @return -> arraylist that contains all the products
     */
    private ArrayList<Product> showAllProducts(){
        return productsInTheSystem;
    }

    /**
     *This method is responsible for show all the electronic products
     *@return -> arraylist that contains all the electronic products
     */
    private ArrayList<Product> showElectronicProducts(){
        ArrayList<Product> electronicList = new ArrayList<>();
        for(Product p : productsInTheSystem){
            if(p instanceof Electronic){
                electronicList.add(p);
            }
        }
        return electronicList;
    }

    /**
     *This method is responsible for show all the cloth products
     *@return -> arraylist that contains all the cloth products
     */
    private ArrayList<Product>showClothingProducts(){
        ArrayList<Product> clothList = new ArrayList<>();
        for(Product p : productsInTheSystem){
            if(p instanceof Cloth){
                clothList.add(p);
            }
        }
        return clothList;
    }

    /**
     * This method is responsible for perform certain tasks when user clicks buttons.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jComboBox){
            if (jComboBox.getSelectedIndex() == 0) {
                updateTableWhenClickComboBox(showAllProducts());
            }else if (jComboBox.getSelectedIndex() == 1) {
                updateTableWhenClickComboBox(showElectronicProducts());
            }else if (jComboBox.getSelectedIndex() == 2) {
                updateTableWhenClickComboBox(showClothingProducts());
            }
        }
    }

    /**
     * Update product table when user clicks combo box
     * @param list -> list need to be displayed
     */
    private void updateTableWhenClickComboBox(ArrayList<Product> list){
        tableModel = new ProductTable(list,new String[]{"Id", "Name", "Category", "Price", "Info"});
        table.setModel(tableModel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    /**
     * This method is responsible for show details of the selected item.
     * @param category -> category of the product (electronic or cloth)
     * @param p -> which product
     * @param additionalDetails -> addition details of the product. (if it is a cloth
     * additional attributes like size and color)
     */
    private void showSelectedItemDetails(String category, Product p, String additionalDetails){
        details = String.format("""
                                                        
                                   Selected Product - Details
                                   Product ID: %s
                                   Category: %s
                                   Name: %s
                                   Price: %.2f
                                   %s
                                   Items Available: %d
                                 
                                """,
                p.getProductId(), category, p.getProductName(),
                p.getPrice(), additionalDetails, p.getNumberOfAvailableItems());

    }

    /**
     * When user clicks table this method handle all the operations.
     */
    private void tableOperations(){
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                p = tableModel.getProductAt(table.getSelectedRow());
                bottomDetails.setFont(new Font("Times new Roman", Font.PLAIN, 20));
                String category = "";
                String additionalDetails = "";
                if(p instanceof Electronic el){
                    category = "Electronic";
                    additionalDetails = String.format("Brand: %s\n   Warranty: %s", el.getBrand(), el.getWarrantyPeriod());
                } else if(p instanceof Cloth cl){
                    category = "Clothing";
                    additionalDetails = String.format("Size: %s\n   Color: %s", cl.getSize(), cl.getColor());
                }
                showSelectedItemDetails(category,p, additionalDetails);
                bottomDetails.setText(details);
                bottomPanel.add(bottomDetails);
                bottomPanel.revalidate();
                bottomPanel.repaint();
            }
        });
    }

    /**
     * When user clicks add to cart button this is added that selected product to the
     * cart
     */
    private void addToCartOperations(){
        addToCartButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Check if a row is actually selected
                Product selectedProduct = tableModel.getProductAt(selectedRow);
                cart.addToCart(selectedProduct);
                JOptionPane.showMessageDialog(null, String.format("%s added to the cart"
                        ,selectedProduct.getProductName()));
            }
        });
    }

    /**
     * When user clicks shopping cart button new window will open
     */
    private void shoppingCartOperations(){
        shoppingCartButton.addActionListener(e -> {
            new ShoppingCartGUI(cart, currentUser);
        });
    }
}