package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import impl.ProductRecord;
import impl.VendingMachineProduct;
import impl.VendingMachine;
import interfaces.IProductRecord;
import interfaces.IVendingMachineProduct;
import interfaces.IVendingMachine;
import exceptions.LaneCodeAlreadyInUseException;
import exceptions.LaneCodeNotRegisteredException;
import exceptions.ProductUnavailableException;


public class VendingMachineTest {

    private static final int CUSTOM_LIMIT = 5;
    private static final int DEFAULT_LIMIT = VendingMachine.DEFAULT_MAX_ITEMS;
    @Test
    void vendingMachineRegisterProductSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        assertEquals(1, vm.getNumberOfProducts());
    }

    @Test
    void vendingMachineRegisterDuplicateThrows() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        assertThrows(LaneCodeAlreadyInUseException.class, () -> vm.registerProduct(p));
    }

    @Test
    void vendingMachineUnregisterProductSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.unregisterProduct(p);
        assertEquals(0, vm.getNumberOfProducts());
    }

    @Test
    void vendingMachineUnregisterUnknownThrows() {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        assertThrows(LaneCodeNotRegisteredException.class, () -> vm.unregisterProduct(p));
    }

    @Test
    void vendingMachineAddItemSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        assertEquals(1, vm.getNumberOfItems("A1"));
    }

    @Test
    void vendingMachineAddItemToUnknownLaneThrows() {
        IVendingMachine vm = new VendingMachine();
        assertThrows(LaneCodeNotRegisteredException.class, () -> vm.addItem("A1"));
    }

    @Test
    void vendingMachineAddMultipleItemsSameLane() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        vm.addItem("A1");
        vm.addItem("A1");
        assertEquals(NUMBER_THREE, vm.getNumberOfItems("A1"));
    }

    @Test
    void vendingMachineBuyItemSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
    }

    @Test
    void vendingMachineBuyFromUnknownLaneThrows() {
        IVendingMachine vm = new VendingMachine();
        assertThrows(LaneCodeNotRegisteredException.class, () -> vm.buyItem("A1"));
    }

    @Test
    void vendingMachineBuyWithoutStockThrows() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        assertThrows(ProductUnavailableException.class, () -> vm.buyItem("A1"));
    }

    @Test
    void vendingMachineGetNumberOfProductsCorrect() throws Exception {
        IVendingMachine vm = new VendingMachine();
        vm.registerProduct(new VendingMachineProduct("A1", "chocoBoom"));
        vm.registerProduct(new VendingMachineProduct("B1", "mcVities"));
        assertEquals(2, vm.getNumberOfProducts());
    }

    @Test
    void vendingMachineGetTotalNumberOfItemsCorrect() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p1 = new VendingMachineProduct("A1", "chocoBoom");
        IVendingMachineProduct p2 = new VendingMachineProduct("B1", "mcVities");
        vm.registerProduct(p1);
        vm.registerProduct(p2);
        vm.addItem("A1");
        vm.addItem("A1");
        vm.addItem("B1");
        assertEquals(NUMBER_THREE, vm.getTotalNumberOfItems());
    }

    @Test
    void vendingMachineGetNumberOfItemsForLane() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        assertEquals(1, vm.getNumberOfItems("A1"));
    }

    @Test
    void vendingMachineGetNumberOfSalesForLane() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
    }

    @Test
    void vendingMachineSalesIndependentPerProduct() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p1 = new VendingMachineProduct("A1", "chocoBoom");
        IVendingMachineProduct p2 = new VendingMachineProduct("B1", "mcVities");
        vm.registerProduct(p1);
        vm.registerProduct(p2);
        vm.addItem("A1"); vm.addItem("B1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
        assertEquals(0, vm.getNumberOfSales("B1"));
    }

    @Test
    void vendingMachineGetMostPopularReturnsCorrectProduct() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct chocoBoom = new VendingMachineProduct("A1", "chocoBoom");
        IVendingMachineProduct mcVities = new VendingMachineProduct("B1", "mcVities");
        vm.registerProduct(chocoBoom);
        vm.registerProduct(mcVities);
        vm.addItem("A1"); vm.addItem("B1");
        vm.buyItem("A1");
        assertEquals(chocoBoom, vm.getMostPopular());
    }

    @Test
    void vendingMachineGetMostPopularThrowsWhenEmpty() {
        IVendingMachine vm = new VendingMachine();
        assertThrows(LaneCodeNotRegisteredException.class, vm::getMostPopular);
    }

    @Test
    void vendingMachineReRegisterAfterUnregisterAllowed() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.unregisterProduct(p);
        vm.registerProduct(p);
        assertEquals(1, vm.getNumberOfProducts());
    }

    @Test
    void vendingMachineTotalsConsistentAfterMixedOps() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "chocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1"); vm.addItem("A1");
        vm.buyItem("A1");
        assertEquals(1, vm.getTotalNumberOfItems());
    }

    @Test
    void vendingMachineHandlesMultipleLanesIndependently() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p1 = new VendingMachineProduct("A1", "chocoBoom");
        IVendingMachineProduct p2 = new VendingMachineProduct("B1", "mcVities");
        vm.registerProduct(p1);
        vm.registerProduct(p2);
        vm.addItem("A1"); vm.addItem("A1"); vm.addItem("B1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
        assertEquals(0, vm.getNumberOfSales("B1"));
        assertEquals(2, vm.getNumberOfProducts());
    }

    @Test
    void vendingMachineAddItemStopsAtLimit() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "Nuts");
        vm.registerProduct(p);

        for (int i = 0; i < CUSTOM_LIMIT; i++) {
            vm.addItem("A1");
        }

        assertEquals(CUSTOM_LIMIT, vm.getNumberOfItems("A1"));
    }

    @Test
    void vendingMachineAddItemBeyondLimitThrowsException() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "Nuts");
        vm.registerProduct(p);

        for (int i = 0; i < DEFAULT_LIMIT; i++) {
            vm.addItem("A1");
        }

        assertThrows(IllegalStateException.class, () -> vm.addItem("A1"));
    }

    void vendingMachineCustomLimitWorksCorrectly() throws Exception {
    VendingMachine vm = new VendingMachine(CUSTOM_LIMIT);
    IVendingMachineProduct p = new VendingMachineProduct("A1", "Nuts");
    vm.registerProduct(p);

    for (int i = 0; i < CUSTOM_LIMIT; i++) {
        vm.addItem("A1");
    }

    assertEquals(CUSTOM_LIMIT, vm.getNumberOfItems("A1"));

    assertThrows(IllegalStateException.class, () -> vm.addItem("A1"));
}

}
