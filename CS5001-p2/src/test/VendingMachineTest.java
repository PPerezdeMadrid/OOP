package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void VendingMachine_RegisterProductSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        assertEquals(1, vm.getNumberOfProducts());
    }

    @Test
    void VendingMachine_RegisterDuplicateThrows() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        assertThrows(LaneCodeAlreadyInUseException.class, () -> vm.registerProduct(p));
    }

    @Test
    void VendingMachine_UnregisterProductSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.unregisterProduct(p);
        assertEquals(0, vm.getNumberOfProducts());
    }

    @Test
    void VendingMachine_UnregisterUnknownThrows() {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        assertThrows(LaneCodeNotRegisteredException.class, () -> vm.unregisterProduct(p));
    }

    @Test
    void VendingMachine_AddItemSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        assertEquals(1, vm.getNumberOfItems("A1"));
    }

    @Test
    void VendingMachine_AddItemToUnknownLaneThrows() {
        IVendingMachine vm = new VendingMachine();
        assertThrows(LaneCodeNotRegisteredException.class, () -> vm.addItem("A1"));
    }

    @Test
    void VendingMachine_AddMultipleItemsSameLane() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        vm.addItem("A1");
        vm.addItem("A1");
        assertEquals(3, vm.getNumberOfItems("A1"));
    }

    @Test
    void VendingMachine_BuyItemSuccess() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
    }

    @Test
    void VendingMachine_BuyFromUnknownLaneThrows() {
        IVendingMachine vm = new VendingMachine();
        assertThrows(LaneCodeNotRegisteredException.class, () -> vm.buyItem("A1"));
    }

    @Test
    void VendingMachine_BuyWithoutStockThrows() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        assertThrows(ProductUnavailableException.class, () -> vm.buyItem("A1"));
    }

    @Test
    void VendingMachine_GetNumberOfProductsCorrect() throws Exception {
        IVendingMachine vm = new VendingMachine();
        vm.registerProduct(new VendingMachineProduct("A1", "ChocoBoom"));
        vm.registerProduct(new VendingMachineProduct("B1", "McVities"));
        assertEquals(2, vm.getNumberOfProducts());
    }

    @Test
    void VendingMachine_GetTotalNumberOfItemsCorrect() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p1 = new VendingMachineProduct("A1", "ChocoBoom");
        IVendingMachineProduct p2 = new VendingMachineProduct("B1", "McVities");
        vm.registerProduct(p1);
        vm.registerProduct(p2);
        vm.addItem("A1");
        vm.addItem("A1");
        vm.addItem("B1");
        assertEquals(3, vm.getTotalNumberOfItems());
    }

    @Test
    void VendingMachine_GetNumberOfItemsForLane() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        assertEquals(1, vm.getNumberOfItems("A1"));
    }

    @Test
    void VendingMachine_GetNumberOfSalesForLane() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
    }

    @Test
    void VendingMachine_SalesIndependentPerProduct() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p1 = new VendingMachineProduct("A1", "ChocoBoom");
        IVendingMachineProduct p2 = new VendingMachineProduct("B1", "McVities");
        vm.registerProduct(p1);
        vm.registerProduct(p2);
        vm.addItem("A1"); vm.addItem("B1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
        assertEquals(0, vm.getNumberOfSales("B1"));
    }

    @Test
    void VendingMachine_GetMostPopularReturnsCorrectProduct() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct ChocoBoom = new VendingMachineProduct("A1", "ChocoBoom");
        IVendingMachineProduct McVities = new VendingMachineProduct("B1", "McVities");
        vm.registerProduct(ChocoBoom);
        vm.registerProduct(McVities);
        vm.addItem("A1"); vm.addItem("B1");
        vm.buyItem("A1");
        assertEquals(ChocoBoom, vm.getMostPopular());
    }

    @Test
    void VendingMachine_GetMostPopularThrowsWhenEmpty() {
        IVendingMachine vm = new VendingMachine();
        assertThrows(LaneCodeNotRegisteredException.class, vm::getMostPopular);
    }

    @Test
    void VendingMachine_ReRegisterAfterUnregisterAllowed() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.unregisterProduct(p);
        vm.registerProduct(p);
        assertEquals(1, vm.getNumberOfProducts());
    }

    @Test
    void VendingMachine_TotalsConsistentAfterMixedOps() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        vm.registerProduct(p);
        vm.addItem("A1"); vm.addItem("A1");
        vm.buyItem("A1");
        assertEquals(1, vm.getTotalNumberOfItems());
    }

    @Test
    void VendingMachine_HandlesMultipleLanesIndependently() throws Exception {
        IVendingMachine vm = new VendingMachine();
        IVendingMachineProduct p1 = new VendingMachineProduct("A1", "ChocoBoom");
        IVendingMachineProduct p2 = new VendingMachineProduct("B1", "McVities");
        vm.registerProduct(p1);
        vm.registerProduct(p2);
        vm.addItem("A1"); vm.addItem("A1"); vm.addItem("B1");
        vm.buyItem("A1");
        assertEquals(1, vm.getNumberOfSales("A1"));
        assertEquals(0, vm.getNumberOfSales("B1"));
        assertEquals(2, vm.getNumberOfProducts());
    }
}
