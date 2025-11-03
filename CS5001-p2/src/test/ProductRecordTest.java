package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

import impl.ProductRecord;
import impl.VendingMachineProduct;
import interfaces.IProductRecord;
import interfaces.IVendingMachineProduct;
import exceptions.ProductUnavailableException;

public class ProductRecordTest {
    private static final int NUMBER_FIVE = 5;
    private static final int NUMBER_THREE = 3;

    @Test
    void productRecordInitialValuesAreZero() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        IProductRecord record = new ProductRecord(p);

        assertEquals(0, record.getNumberAvailable());
        assertEquals(0, record.getNumberOfSales());
    }

    @Test
    void productRecordAddItemIncreasesAvailable() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        ProductRecord record = new ProductRecord(p);
        record.addItem();

        assertEquals(1, record.getNumberAvailable());
        assertEquals(0, record.getNumberOfSales());
    }

    @Test
    void productRecordAddItemMultipleTimesIncreasesAvailable() {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem();
        r.addItem();
        r.addItem();
        assertEquals(NUMBER_THREE, r.getNumberAvailable());
    }

    @Test
    void productRecordBuyItemReducesStockAndIncreasesSales() throws Exception {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        ProductRecord record = new ProductRecord(p);
        record.addItem();
        record.buyItem();

        assertEquals(0, record.getNumberAvailable());
        assertEquals(1, record.getNumberOfSales());
    }

    @Test
    void productRecordBuyItemWithoutStockThrows() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        ProductRecord record = new ProductRecord(p);

        assertThrows(ProductUnavailableException.class, record::buyItem);
    }

    @Test
    void productRecordMultipleBuysUpdateCountsCorrectly() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        for (int i = 0; i < NUMBER_FIVE; i++) {
            r.addItem();
        }
        for (int i = 0; i < NUMBER_THREE; i++) {
            r.buyItem();
        }

        assertEquals(2, r.getNumberAvailable());
        assertEquals(NUMBER_THREE, r.getNumberOfSales());
    }

    @Test
    void productRecordGetProductReturnsSameInstance() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        IProductRecord record = new ProductRecord(p);

        assertSame(p, record.getProduct());
    }

    @Test
    void productRecordNoNegativeStockAfterBuys() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem();
        r.buyItem();
        assertEquals(0, r.getNumberAvailable());
    }

    @Test
    void productRecordSalesCountAccurateAfterAddBuyCycles() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        for (int i = 0; i < NUMBER_THREE; i++) {
            r.addItem();
            r.buyItem();
        }
        assertEquals(NUMBER_THREE, r.getNumberOfSales());
        assertEquals(0, r.getNumberAvailable());
    }

    @Test
    void productRecordStockCountCorrectAfterMixedOps() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem(); r.addItem(); r.buyItem(); r.addItem();
        assertEquals(2, r.getNumberAvailable());
    }

    @Test
    void productRecordIndependentRecordsWorkSeparately() throws Exception {
        IProductRecord r1 = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        IProductRecord r2 = new ProductRecord(new VendingMachineProduct("B1", "Juice"));
        r1.addItem(); r1.buyItem();
        assertEquals(1, r1.getNumberOfSales());
        assertEquals(0, r2.getNumberOfSales());
    }

    @Test
    void productRecordAddAfterSalesWorks() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem(); r.buyItem(); r.addItem();
        assertEquals(1, r.getNumberAvailable());
    }

    @Test
    void productRecordProductReferenceUnchanged() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "Cola");
        IProductRecord r = new ProductRecord(p);
        assertSame(p, r.getProduct());
    }

    @Test
    void productRecordBuyAffectsOnlyThisRecord() throws Exception {
        IProductRecord r1 = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        IProductRecord r2 = new ProductRecord(new VendingMachineProduct("B1", "Water"));
        r1.addItem(); r1.buyItem();
        assertEquals(1, r1.getNumberOfSales());
        assertEquals(0, r2.getNumberOfSales());
    }

    @Test
    void productRecordAddBuyRestoresStockBalance() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem();
        r.buyItem();
        r.addItem();
        assertEquals(1, r.getNumberAvailable());
    }
}
