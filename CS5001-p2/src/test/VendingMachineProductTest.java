package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import impl.VendingMachineProduct;
import interfaces.IVendingMachineProduct;

public class VendingMachineProductTest {

    @Test
    void VendingMachineProduct_StoresValues() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        assertEquals("A1", p.getLaneCode());
        assertEquals("ChocoBoom", p.getDescription());
    }

    @Test
    void VendingMachineProduct_ReturnValues() {
        IVendingMachineProduct p = new VendingMachineProduct("B2", "McVities");
        assertAll(
            () -> assertEquals("B2", p.getLaneCode()),
            () -> assertEquals("McVities", p.getDescription())
        );
    }

    @Test
    void VendingMachineProduct_DifferentInstancesIndependent() {
        IVendingMachineProduct p1 = new VendingMachineProduct("C1", "Crisps");
        IVendingMachineProduct p2 = new VendingMachineProduct("D1", "Chocolate");
        assertNotEquals(p1.getLaneCode(), p2.getLaneCode());
        assertNotEquals(p1.getDescription(), p2.getDescription());
    }

    @Test
    void VendingMachineProduct_ConstructorDoesNotThrow() {
        assertDoesNotThrow(() -> new VendingMachineProduct("Z9", "Juice"));
    }

    @Test
    void VendingMachineProduct_NotAllowNonNumericLaneCodes() {
        assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("A#", "Snack"));
    }

    @Test
    void VendingMachineProduct_NotAllowEmptyLaneCode() {
        assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("", "Crisps"));
    }

    @Test
    void VendingMachineProduct_NotAllowEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("B1", ""));
    }

    @Test
    void VendingMachineProduct_NotAllowNullValues() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct(null, "Snack")),
            () -> assertThrows(IllegalArgumentException.class, () -> new VendingMachineProduct("A1", null))
        );
    }
}
