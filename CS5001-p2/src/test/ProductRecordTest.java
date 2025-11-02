package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import impl.ProductRecord;
import impl.VendingMachineProduct;
import interfaces.IProductRecord;
import interfaces.IVendingMachineProduct;
import exceptions.ProductUnavailableException;

public class ProductRecordTest {

    @Test
    void ProductRecord_InitialValuesAreZero() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        IProductRecord record = new ProductRecord(p);

        assertEquals(0, record.getNumberAvailable());
        assertEquals(0, record.getNumberOfSales());
    }

    @Test
    void ProductRecord_AddItemIncreasesAvailable() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        ProductRecord record = new ProductRecord(p);
        record.addItem();

        assertEquals(1, record.getNumberAvailable());
        assertEquals(0, record.getNumberOfSales());
    }

    @Test
    void ProductRecord_AddItemMultipleTimesIncreasesAvailable() {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem();
        r.addItem();
        r.addItem();
        assertEquals(3, r.getNumberAvailable());
    }

    @Test
    void ProductRecord_BuyItemReducesStockAndIncreasesSales() throws Exception {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        ProductRecord record = new ProductRecord(p);
        record.addItem();
        record.buyItem();

        assertEquals(0, record.getNumberAvailable());
        assertEquals(1, record.getNumberOfSales());
    }

    @Test
    void ProductRecord_BuyItemWithoutStockThrows() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        ProductRecord record = new ProductRecord(p);

        assertThrows(ProductUnavailableException.class, record::buyItem);
    }

    @Test
    void ProductRecord_MultipleBuysUpdateCountsCorrectly() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        for (int i = 0; i < 5; i++) r.addItem();
        for (int i = 0; i < 3; i++) r.buyItem();
        assertEquals(2, r.getNumberAvailable());
        assertEquals(3, r.getNumberOfSales());
    }

    @Test
    void ProductRecord_GetProductReturnsSameInstance() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "ChocoBoom");
        IProductRecord record = new ProductRecord(p);

        assertSame(p, record.getProduct());
    }

    @Test
    void ProductRecord_NoNegativeStockAfterBuys() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem();
        r.buyItem();
        assertEquals(0, r.getNumberAvailable());
    }

    @Test
    void ProductRecord_SalesCountAccurateAfterAddBuyCycles() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        for (int i = 0; i < 3; i++) {
            r.addItem();
            r.buyItem();
        }
        assertEquals(3, r.getNumberOfSales());
        assertEquals(0, r.getNumberAvailable());
    }

    @Test
    void ProductRecord_StockCountCorrectAfterMixedOps() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem(); r.addItem(); r.buyItem(); r.addItem();
        assertEquals(2, r.getNumberAvailable());
    }

    @Test
    void ProductRecord_IndependentRecordsWorkSeparately() throws Exception {
        IProductRecord r1 = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        IProductRecord r2 = new ProductRecord(new VendingMachineProduct("B1", "Juice"));
        r1.addItem(); r1.buyItem();
        assertEquals(1, r1.getNumberOfSales());
        assertEquals(0, r2.getNumberOfSales());
    }

    @Test
    void ProductRecord_AddAfterSalesWorks() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem(); r.buyItem(); r.addItem();
        assertEquals(1, r.getNumberAvailable());
    }

    @Test
    void ProductRecord_ProductReferenceUnchanged() {
        IVendingMachineProduct p = new VendingMachineProduct("A1", "Cola");
        IProductRecord r = new ProductRecord(p);
        assertSame(p, r.getProduct());
    }

    @Test
    void ProductRecord_BuyAffectsOnlyThisRecord() throws Exception {
        IProductRecord r1 = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        IProductRecord r2 = new ProductRecord(new VendingMachineProduct("B1", "Water"));
        r1.addItem(); r1.buyItem();
        assertEquals(1, r1.getNumberOfSales());
        assertEquals(0, r2.getNumberOfSales());
    }

    @Test
    void ProductRecord_AddBuyRestoresStockBalance() throws Exception {
        IProductRecord r = new ProductRecord(new VendingMachineProduct("A1", "Cola"));
        r.addItem();
        r.buyItem();
        r.addItem();
        assertEquals(1, r.getNumberAvailable());
    }
    
}
