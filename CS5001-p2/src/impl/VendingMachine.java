package impl;

import java.util.HashMap;
import java.util.Map;

import exceptions.LaneCodeAlreadyInUseException;
import exceptions.LaneCodeNotRegisteredException;
import exceptions.ProductUnavailableException;
import interfaces.IVendingMachineProduct;
import interfaces.IVendingMachine;
import interfaces.IProductRecord;

/**
 * This class represents a simple vending machine which can stock and sell products.
 *
 */
public class VendingMachine implements IVendingMachine {
    private final Map<String, IProductRecord> records;
    private final int maxItemsPerProduct;
    /**
     * Maximum amount of items per products.
     */
    public static final int DEFAULT_MAX_ITEMS = 10;

    /**
     * Constructs an empty vending machine.
     * @param maxItemsPerProduct Maximum amount of items permitted per product (physical restriction of the Vending Machine)
     */
    public VendingMachine(int maxItemsPerProduct) {
        this.records = new HashMap<>();
        this.maxItemsPerProduct = maxItemsPerProduct;
    }
    /**
     * Constructs an empty vending machine.
     */
    public VendingMachine() {
        this.records = new HashMap<>();
        this.maxItemsPerProduct = DEFAULT_MAX_ITEMS;
    }

    @Override
    public void registerProduct(IVendingMachineProduct vendingMachineProduct) throws LaneCodeAlreadyInUseException {
        // TODO Auto-generated method stub
        String laneCode = vendingMachineProduct.getLaneCode();

        if (records.containsKey(laneCode)) {
            throw new LaneCodeAlreadyInUseException();
        }

        records.put(laneCode, new ProductRecord(vendingMachineProduct));
        // System.out.println("Registered product at lane " + laneCode);

    }

    @Override
    public void unregisterProduct(IVendingMachineProduct vendingMachineProduct) throws LaneCodeNotRegisteredException {
        // TODO Auto-generated method stub
        String laneCode = vendingMachineProduct.getLaneCode();
        if (!records.containsKey(laneCode)) {
            throw new LaneCodeNotRegisteredException();
        }
        records.remove(laneCode);

    }

    @Override
    public void addItem(String laneCode) throws LaneCodeNotRegisteredException {
        // TODO Auto-generated method stub
        IProductRecord record = records.get(laneCode);
        if (record == null) {
            throw new LaneCodeNotRegisteredException();
        }

        if (record.getNumberAvailable() >= this.maxItemsPerProduct) {
            throw new IllegalStateException(
                "Cannot add more items: reached maximum of " + this.maxItemsPerProduct + " for lane " + laneCode
            );
        }

        record.addItem();

    }

    @Override
    public void buyItem(String laneCode) throws ProductUnavailableException, LaneCodeNotRegisteredException {
        // TODO Auto-generated method stub
        IProductRecord record = records.get(laneCode);

        if (record == null) {
            throw new LaneCodeNotRegisteredException();
        }

        record.buyItem();
    }

    @Override
    public int getNumberOfProducts() {
        // TODO Auto-generated method stub
        // return 0;
        return records.size();
    }

    @Override
    public int getTotalNumberOfItems() {
        // TODO Auto-generated method stub
        // return 0;
        int total = 0;
        for (IProductRecord record : records.values()) {
            total += record.getNumberAvailable();
        }
        return total;
    }

    @Override
    public int getNumberOfItems(String laneCode) throws LaneCodeNotRegisteredException {
        // TODO Auto-generated method stub
        // return 0;
        IProductRecord record = records.get(laneCode);

        if (record == null) {
            throw new LaneCodeNotRegisteredException();
        }

        return record.getNumberAvailable();
    }

    @Override
    public int getNumberOfSales(String laneCode) throws LaneCodeNotRegisteredException {
        // TODO Auto-generated method stub
        // return 0;
        IProductRecord record = records.get(laneCode);

        if (record == null) {
            throw new LaneCodeNotRegisteredException();
        }

        return record.getNumberOfSales();
    }

    @Override
    public IVendingMachineProduct getMostPopular() throws LaneCodeNotRegisteredException {
        // TODO Auto-generated method stub
        // return null;
        if (records.isEmpty()) {
            throw new LaneCodeNotRegisteredException();
        }

        IProductRecord mostPopular = null;
        for (IProductRecord record : records.values()) {
            if (mostPopular == null || record.getNumberOfSales() > mostPopular.getNumberOfSales()) {
                mostPopular = record;
            }
        }

        return mostPopular.getProduct();
    }
}
