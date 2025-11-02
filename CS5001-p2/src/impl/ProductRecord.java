package impl;

import exceptions.ProductUnavailableException;
import interfaces.IVendingMachineProduct;
import interfaces.IProductRecord;

/**
 * This class represents a ProductRecord, recording information relating to a product sold in a vending machine.
 *
 */
public class ProductRecord implements IProductRecord {
    @Override
        public IVendingMachineProduct getProduct() {
            return this.vendingMachineProduct;
    }
    private IVendingMachineProduct vendingMachineProduct;
    private int numberAvailable;
    private int numberOfSales;

    /* 
    * Constructs a ProductRecord for the given vending machine product.
    */
    public ProductRecord(IVendingMachineProduct vendingMachineProduct) {
        this.vendingMachineProduct = vendingMachineProduct;
        this.numberAvailable = 0;
        this.numberOfSales = 0;

    }

    /*
    * Getter for the product this record represents.
    */



    @Override
    public int getNumberOfSales() {
        // TODO Auto-generated method stub
        // return 0;
        return this.numberOfSales;
    }

    @Override
    public int getNumberAvailable() {
        // TODO Auto-generated method stub
        // return 0;
        return this.numberAvailable;
    }

    @Override
    public void addItem() {
        this.numberAvailable++;
    }


    @Override
    public void buyItem() throws ProductUnavailableException {
        // TODO Auto-generated method stub
        if (numberAvailable == 0) {
            throw new ProductUnavailableException(); // Revisar si esto se puede hacer
        }
        this.numberAvailable--;
        this.numberOfSales++;
    }

}
