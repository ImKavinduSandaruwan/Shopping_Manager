package user;

import system.Product;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> shoppingCart;
    private double totalPrice;

    public ShoppingCart() {
        this.shoppingCart = new ArrayList<>();
    }

    /**
     * This method is responsible for add products to the shopping cart
     * @param product -> Product needs to be added to the shopping cart
     */
    public void addToCart(Product product){
        shoppingCart.add(product);
        System.out.println(product.getProductName() + " is added to the cart.");
    }

    /**
     * This method is responsible for remove items from the shopping cart
     * @param product -> Product needs to be removed from the shopping cart
     */
    public void removeFromCart(Product product){
        System.out.println(product.getProductName() + " is removed from the cart.");
        shoppingCart.remove(product);
    }

    /**
     * This method responsible for get total cost of the products in the cart
     * @return -> total cost
     */
    public double getTotalCost(){
        totalPrice = 0;
        for(Product product : shoppingCart){
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCart=" + shoppingCart +
                '}';
    }

    /**
     * @return -> shopping cart array
     */
    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }
}
