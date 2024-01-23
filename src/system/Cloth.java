package system;

import java.io.Serializable;
import java.util.Scanner;

public class Cloth extends Product implements Serializable {
    private String size;
    private String color;

    public Cloth() {
        super();
    }

    public Cloth(String productId, String productName, double price, int numberOfAvailableItems,
                 String size, String color) {
        super(productId, productName, price, numberOfAvailableItems);
        this.size = size;
        this.color = color;
    }

    //Getters and Setters
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                "} " + super.toString();
    }

    /**
     * This method is responsible for add clothing items to the system.
     */
    @Override
    public void addProductToTheSystem() {
        super.addProductToTheSystem();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product Size: ");
        this.size = scanner.nextLine();
        System.out.print("Enter product Color: ");
        this.color = scanner.nextLine();
    }

    /**
     * This will return new cloth object.
     * @return -> cloth object
     */
    @Override
    public Product getProduct() {
        this.addProductToTheSystem();
        return new Cloth(super.getProductId(), super.getProductName(),
                super.getPrice(), super.getNumberOfAvailableItems(), size, color);

    }
}