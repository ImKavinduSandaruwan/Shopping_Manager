package system;

import java.io.Serializable;
import java.util.Scanner;

public class Electronic extends Product implements Serializable {
    private String brand;
    private String warrantyPeriod;

    public Electronic() {
        super();
    }

    public Electronic(String productId, String productName, double price, int numberOfAvailableItems,
                      String brand, String warrantyPeriod) {
        super(productId, productName, price, numberOfAvailableItems);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //Getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronic{" +
                "brand='" + brand + '\'' +
                ", warrantyPeriod='" + warrantyPeriod + '\'' +
                "} " + super.toString();
    }

    /**
     * This method is responsible for add clothing items to the system.
     */
    @Override
    public void addProductToTheSystem() {
        super.addProductToTheSystem();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product brand: ");
        this.brand = scanner.nextLine();
        System.out.print("Enter product warranty: ");
        this.warrantyPeriod = scanner.nextLine();
    }

    /**
     * This will return new electronic object.
     * @return -> electronic object
     */
    @Override
    public Product getProduct() {
        this.addProductToTheSystem();
        return new Electronic(super.getProductId(), super.getProductName(),
                super.getPrice(), super.getNumberOfAvailableItems(),
                brand, warrantyPeriod);

    }
}
