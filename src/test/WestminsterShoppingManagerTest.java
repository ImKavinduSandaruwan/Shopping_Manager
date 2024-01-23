package test;

import manager.WestminsterShoppingManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import system.Cloth;
import system.Electronic;
import system.Product;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    private static WestminsterShoppingManager manager;

    @BeforeAll
    static void setUp(){
        manager = new WestminsterShoppingManager();
    }

    @Test
    void testAdd() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        Method method = WestminsterShoppingManager.class.getDeclaredMethod("add", int.class);
        method.setAccessible(true);
        Product electronicProduct = (Product) method.invoke(manager, 1);
        assertTrue(electronicProduct instanceof Electronic);
        Product clothProduct = (Product) method.invoke(manager, 2);
        assertTrue(clothProduct instanceof Cloth);
    }

    @Test
    void checkForAvailabilityOfTheProductTest() throws Exception {
        Method method = WestminsterShoppingManager.class.getDeclaredMethod("checkForAvailabilityOfTheProduct", Product.class);
        method.setAccessible(true);
        Electronic electronic = new Electronic("E001", "Laptop", 999.99, 5, "Dell", "2 years");
        boolean result = (boolean) method.invoke(manager, electronic);
        assertTrue(result);
    }
}