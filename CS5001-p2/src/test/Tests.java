package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import impl.Factory;
import impl.VendingMachineProduct;
import impl.VendingMachine;
import impl.ProductRecord;
import interfaces.IVendingMachineProduct;
import interfaces.IVendingMachine;
import interfaces.IProductRecord;
import interfaces.IFactory;

/**
 * This is a JUnit test class for the Vending Machine.
 */
public class Tests {

     // ==== Factory TESTS ====

    @Test
    void factorySameInstance() {
        IFactory f1 = Factory.getInstance();
        IFactory f2 = Factory.getInstance();

        assertNotNull(f1, "Factory instance should not be null");
        assertSame(f1, f2, "Factory should return the same instance every time");
    }
    /**
     * This checks that the factory was able to call a sensible constructor to get a non-null instance of IVendingMachineProduct.
     */
   @Test
    public void factoryVendingMachineProductNotNull() {
        IVendingMachineProduct vendingMachineProduct = Factory.getInstance().makeVendingMachineProduct("A1", "Haggis Crisps");
        assertNotNull(vendingMachineProduct);
    }

    @Test
    void factoryCreatesNonNullProductRecord() {
        IFactory factory = Factory.getInstance();
        IVendingMachineProduct product = factory.makeVendingMachineProduct("A1", "ChocoBoom");
        IProductRecord record = factory.makeProductRecord(product);
        assertNotNull(record);
    }

    @Test
    void factoryCreatesNonNullVendingMachine() {
        IFactory factory = Factory.getInstance();
        IVendingMachine vm = factory.makeVendingMachine();
        assertNotNull(vm);
    }

    @Test
    void factoryCreatesCorrectTypeVendingMachineProduct() {
        IFactory factory = Factory.getInstance();
        IVendingMachineProduct product = factory.makeVendingMachineProduct("A1", "ChocoBoom");
        assertTrue(product instanceof VendingMachineProduct);
    }

    @Test
    void factoryCreatesCorrectTypeProductRecord() {
        IFactory factory = Factory.getInstance();
        IVendingMachineProduct product = factory.makeVendingMachineProduct("A1", "ChocoBoom");
        IProductRecord record = factory.makeProductRecord(product);
        assertTrue(record instanceof ProductRecord);
    }

    @Test
    void factoryCreatesCorrectTypeVendingMachine() {
        IFactory factory = Factory.getInstance();
        IVendingMachine vm = factory.makeVendingMachine();
        assertTrue(vm instanceof VendingMachine);
    }

    @Test
    void factoryReturnsDifferentVendingMachineProductInstances() {
        IFactory factory = Factory.getInstance();
        IVendingMachineProduct p1 = factory.makeVendingMachineProduct("A1", "ChocoBoom");
        IVendingMachineProduct p2 = factory.makeVendingMachineProduct("B1", "Water");
        assertNotSame(p1, p2);
    }

    @Test
    void factoryReturnsDifferentProductRecordInstances() {
        IFactory factory = Factory.getInstance();
        IVendingMachineProduct p = factory.makeVendingMachineProduct("A1", "ChocoBoom");
        IProductRecord r1 = factory.makeProductRecord(p);
        IProductRecord r2 = factory.makeProductRecord(p);
        assertNotSame(r1, r2);
    }

    @Test
    void factoryReturnsDifferentVendingMachineInstances() {
        IFactory factory = Factory.getInstance();
        IVendingMachine vm1 = factory.makeVendingMachine();
        IVendingMachine vm2 = factory.makeVendingMachine();
        assertNotSame(vm1, vm2);
    }
}
