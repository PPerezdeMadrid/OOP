package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertAll;

import impl.VendingMachineProduct;
import interfaces.IVendingMachineProduct;

public class VendingMachineProductTest {

    @Test
    void vendingMachineProductStoresValues() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        assertEquals("A1", p.getLaneCode());
        assertEquals("ChocoBoom", p.getDescription());
    }

    @Test
    void vendingMachineProductReturnValues() {
        IVendingMachineProduct p = new VendingMachineProduct("B2", "McVities");
        assertAll(
            () -> assertEquals("B2", p.getLaneCode()),
            () -> assertEquals("McVities", p.getDescription())
        );
    }

    @Test
    void vendingMachineProductDifferentInstancesIndependent() {
        IVendingMachineProduct p1 = new VendingMachineProduct("C1", "Crisps");
        IVendingMachineProduct p2 = new VendingMachineProduct("D1", "Chocolate");
        assertNotEquals(p1.getLaneCode(), p2.getLaneCode());
        assertNotEquals(p1.getDescription(), p2.getDescription());
    }

    @Test
    void vendingMachineProductConstructorDoesNotThrow() {
        assertDoesNotThrow(() -> new VendingMachineProduct("Z9", "Juice"));
    }

    @Test
    void vendingMachineProductNotAllowNonNumericLaneCodes() {
        assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("A#", "Snack"));
    }

    @Test
    void vendingMachineProductNotAllowEmptyLaneCode() {
        assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("", "Crisps"));
    }

    @Test
    void vendingMachineProductNotAllowEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("B1", ""));
    }

    @Test
    void vendingMachineProductNotAllowNullValues() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct(null, "Snack")),
            () -> assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("A1", null))
        );
    }
}
