import manager.WestminsterShoppingManager;
import system.ShoppingManager;
import user.UserGUI;
import user.WelcomeScreen;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ShoppingManager shoppingManager = new WestminsterShoppingManager();
    private static boolean shouldContinue = true;

    /**
     * Main method. This is the starting point of the application.
     * @param args ->
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                \n\s\s** Welcome to the Westminster Shopping Center **
                -----------------------------------------------------
                1) Manager
                2) User
                0) Exit
                Enter your option:-\s""");
        switch (scanner.nextLine()) {
            case "1" -> manager();
            case "2" -> WelcomeScreen.main(new String[]{"arg1", "arg2"});
            case "0" -> System.out.println("Thank you have a nice day!!");
            default -> {
                System.out.println("Please enter valid inputs.");
                main(new String[]{"arg1", "arg2"});
            }
        }
        scanner.close();

    }

    /**
     * This method is responsible for execute relevant method according to the
     * users out put.
     */
    private static void manager(){
        Scanner scanner = new Scanner(System.in);
        while (shouldContinue){
            shoppingManager.printMainMenu();
            try {
                switch (scanner.nextInt()) {
                    case 1 -> shoppingManager.addNewProduct();
                    case 2 -> shoppingManager.deleteAProduct();
                    case 3 -> shoppingManager.printTheListOfItems();
                    case 4 -> shoppingManager.saveInFile();
                    case 5 -> {
                        shouldContinue = false;
                        System.out.println("Thank you have a nice day!!");
                    }
                    default -> System.out.println("Invalid option\n");
                }
            } catch (InputMismatchException ime){
                System.out.println("Please Enter valid inputs\n");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}