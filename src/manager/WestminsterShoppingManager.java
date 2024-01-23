package manager;

import system.Cloth;
import system.Electronic;
import system.Product;
import system.ShoppingManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> productsInTheSystem;
    private List<Product> productsToBeAdded;
    private final Scanner scanner = new Scanner(System.in);
    private String fileName = "productDetails.txt";
    private int maximumProductLimit = 50;

    /**
     * Constructor of the westminster shopping manager class.
     */
    public WestminsterShoppingManager() {
        this.productsInTheSystem = new ArrayList<>(50);
        this.productsToBeAdded = new ArrayList<>();
    }

    /**
     * This method is overridden from the interface called shopping manager and
     * this method is responsible for add new products to the system.
     */
    @Override
    public void addNewProduct() {
        Scanner scanner1 = new Scanner(System.in);
        productsInTheSystem.clear(); //when I call load from file method. this is going to be duplicate. to avoid that I clear it.
        loadFromTheSystem();
        System.out.print("""
                What do you want to add to the system
                1) Electronic
                2) Cloth
                >>>>>>>\s""");
        try {
            Product product = add(scanner1.nextInt()).getProduct();
            System.out.println(product);
            if (checkForAvailabilityOfTheProduct(product) & productsInTheSystem.size() < maximumProductLimit) {
                System.out.println("\n" + product.getProductName() + " is now on the product list to be added.");
                productsToBeAdded.add(product);
            } else {
                if (productsInTheSystem.size() >= maximumProductLimit) {
                    System.out.println("\nReached 50 products");
                } else {
                    System.out.printf("\n%s with %s product id is already in the system\n", product.getProductName(),
                            product.getProductId());
                }
            }
            System.out.println("---------------------");
        }catch (IllegalArgumentException e){
            System.out.println("Enter valid inputs");
        }
    }

    /**
     * This method responsible for check which type of object needs to be added to the system.
     * @param option -> user option
     * @return -> relevant object according to the users input (option).
     */
    private Product add(int option){
        return switch (option){
            case 1 -> new Electronic();
            case 2 -> new Cloth();
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * This method responsible for check whether product can be added to the system.
     * if it is this will return true otherwise false.
     * @param product -> product needs to added to the system.
     * @return -> true if can be added otherwise false.
     */
    private boolean checkForAvailabilityOfTheProduct(Product product){
        for(Product system : productsInTheSystem){
            if(system.getProductId().equals(product.getProductId())) return false;
        }
        return true;
    }

    /**
     * This method responsible for delete particular product from the system.
     * it will ask productID and quantity.
     */
    @Override
    public void deleteAProduct() {
        productsInTheSystem.clear();
        loadFromTheSystem();
        System.out.print("Enter product Id: ");
        scanner.nextLine();
        String idToBeDelete = scanner.nextLine();
        Product product = null;
        Iterator<Product> iterator = productsInTheSystem.iterator();
        while (iterator.hasNext()) {
            product = iterator.next();
            if(product.getProductId().equals(idToBeDelete)) {
                System.out.printf("Current there are %d items left in the system," +
                        "How many items do you want to remove: ",product.getNumberOfAvailableItems());
                int removeAmount = scanner.nextInt();
                if(removeAmount > product.getNumberOfAvailableItems()){
                    System.out.println("Invalid amount.");
                }else if(removeAmount == product.getNumberOfAvailableItems()) {
                    System.out.println(product.getProductId() + " is removed");
                    iterator.remove();
                    break;
                }else{
                    int newAmount = product.getNumberOfAvailableItems() - removeAmount;
                    product.setNumberOfAvailableItems(newAmount);
                    break;
                }
            }
        }
        update(productsInTheSystem, false);
    }

    /**
     * This method comes from shopping manager interface and responsible for
     * print the main menu of the application.
     */
    @Override
    public void printMainMenu(){
        System.out.println("-".repeat(50));
        System.out.println("\s\s" +"*".repeat(2) + " Welcome to Westminster Shopping Manager " +
                "*".repeat(2));
        System.out.println("-".repeat(50));
        System.out.print("""
                1) Add a new product
                2) Delete a product
                3) Print list of the products
                4) Save in a file
                5) Quit
                >>>>>>>>>>>>>\s""");
    }

    /**
     * This method comes from interface called shopping manager and responsibel
     * for save data to a txt format file.
     */
    @Override
    public void saveInFile(){
        update(productsToBeAdded, true);
    }

    /**
     * This method is responsible for update file/
     * @param list -> product list need to be added to the system
     * @param isAppend - > whether append these data or not.
     */
    private void update(List<Product> list, boolean isAppend){
        try {
            FileOutputStream fs = new FileOutputStream(fileName, isAppend);
            ObjectOutputStream os;
            if (fs.getChannel().position() == 0) {
                os = new ObjectOutputStream(fs);
            } else {
                os = new MyObjectOutputStream(fs);
            }
            Product product = null;
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                product = iterator.next();
                os.writeObject(product);
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
     * This method comes from shopping manager interface and responsible
     * for print items that are already in the system.
     */
    @Override
    public void printTheListOfItems(){
        Scanner scanner2 = new Scanner(System.in);
        System.out.print("""
                What do you wants to print
                1) Items that already in the system
                2) Items to be added to the system
                >>>>>>>>>>\s""");
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> printFromSystem();
                case 2 -> printFromToBeAdded();
                default -> {
                    System.out.println("Enter valid inputs.");
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println("Enter valid inputs");
            scanner.nextLine();
        }
    }

    /**
     * This method responsible for print items that already saved and in the system.
     */
    private void printFromSystem(){
        System.out.println("Reading.....");
        try {
            FileInputStream fi = new FileInputStream(fileName);
            ObjectInputStream oi = new ObjectInputStream(fi);
            while (true){
                try {
                    Object obj = oi.readObject();
                    if(obj instanceof Cloth){
                        System.out.println(formatObjectOutput((Cloth)obj));
                    }else if(obj instanceof Electronic){
                        System.out.println(formatObjectOutput((Electronic)obj));
                    }
                } catch (EOFException e){
                    break;
                }
            }
            oi.close();
            fi.close();
        }catch (IOException | ClassNotFoundException e){
            System.out.println();
        }
    }

    /**
     * This method is responsible for print items that are in the ToBe added list.
     * this is a temporary list.
     */
    private void printFromToBeAdded(){
        for(Product p : productsToBeAdded){
            System.out.println(formatObjectOutput(p));
        }
    }

    /**
     * This method is responsible for load data from the file and save in an
     * arraylist called productsInTheSystem. This will help to check validations
     */
    private void loadFromTheSystem(){
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
     * This method is responsible for format data before print in the console.
     * @param product -> product that need to be formatted.
     * @return -> String representation of the product attributes.
     */
    private String formatObjectOutput(Product product){
        String string = "";
        if(product instanceof Electronic e){
            string = String.format("Electronic [Id:- %s, Name:- %s, Price:- Rs.%.2f, NumOfItems:- %d, " +
                    "Brand:- %s, Warranty:- %s]",e.getProductId(),e.getProductName(),
                    e.getPrice(), e.getNumberOfAvailableItems(),e.getBrand(),e.getWarrantyPeriod());
        }else if(product instanceof Cloth c){
            string = String.format("Cloth [Id:- %s, Name:- %s, Price:- Rs.%.2f, NumOfItems:- %d, " +
                            "Size:- %s, Color:- %s]",c.getProductId(),c.getProductName(),
                    c.getPrice(), c.getNumberOfAvailableItems(),c.getSize(),c.getColor());
        }
        return string;
    }

    public List<Product> getProductsToBeAdded() {
        return productsToBeAdded;
    }
}