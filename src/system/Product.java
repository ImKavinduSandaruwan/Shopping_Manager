package system;

import java.io.Serializable;
import java.util.Scanner;

public abstract class Product implements Serializable {
    private String productId;
    private String productName;
    private int numberOfAvailableItems;
    private double price;

    public Product() {}

    public Product(String productId, String productName, double price, int numberOfAvailableItems) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.numberOfAvailableItems = numberOfAvailableItems;
    }
    public void addProductToTheSystem(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product Id: ");
        this.productId = scanner.nextLine();
        System.out.print("Enter product name: ");
        this.productName = scanner.nextLine();
        System.out.print("Enter product price: ");
        this.price = scanner.nextDouble();
        System.out.print("Enter Number of items: ");
        this.numberOfAvailableItems = scanner.nextInt();
        scanner.nextLine();
    }
    public abstract Product getProduct();

    //Getters and Setters
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfAvailableItems() {
        return numberOfAvailableItems;
    }

    public void setNumberOfAvailableItems(int numberOfAvailableItems) {
        this.numberOfAvailableItems = numberOfAvailableItems;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", numberOfAvailableItems=" + numberOfAvailableItems +
                ", price=" + price +
                '}';
    }
}