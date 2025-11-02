package impl;

import interfaces.IFactory;
import interfaces.IVendingMachineProduct;
import interfaces.IVendingMachine;
import interfaces.IProductRecord;


/**
 * This class implements a singleton factory.
 *
 */
public final class Factory implements IFactory {

    private static IFactory factoryInstance = null;

    private Factory() {

    }

    /**
     * Method which returns an instance of the singleton Factory class.
     * @return the instance of the Factory
     */
    public static IFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new Factory();
        }
        return factoryInstance;
    }

    /**
     * Creates a new vending machine product with the given lane code and description.
     * @param laneCode the unique code identifying the product lane (e.g. "A1")
     * @param description the description or name of the product
     * @return a new instance of {@link IVendingMachineProduct}
     */
    @Override
    public IVendingMachineProduct makeVendingMachineProduct(String laneCode, String description) {
        // TODO Auto-generated method stub
        // return null;
        return new VendingMachineProduct(laneCode, description);
    }

     /**
     * Creates a new product record associated with the specified vending machine product.
     * @param vendingMachineProduct the product this record represents
     * @return a new instance of {@link IProductRecord}
     */
    @Override
    public IProductRecord makeProductRecord(IVendingMachineProduct vendingMachineProduct) {
        // TODO Auto-generated method stub
        // return null;
        return new ProductRecord(vendingMachineProduct);
    }

    /**
     * Creates a new empty vending machine instance ready to register products.
     * @return a new instance of {@link IVendingMachine}
     */
    @Override
    public IVendingMachine makeVendingMachine() {
        // TODO Auto-generated method stub
        // return null;
        return new VendingMachine();
    }

}
