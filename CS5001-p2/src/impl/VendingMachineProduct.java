package impl;

import interfaces.IVendingMachineProduct;

/**
 * This class represents products that can be stocked and sold in a vending machine in a specific lane.
 *
 */
public class VendingMachineProduct implements IVendingMachineProduct {
    /*
    * Constructs a vending machine product with the given lane code and description.
    */
    public VendingMachineProduct(String laneCode, String description) {
        // Si laneCode es Letra + Número entonces lo aceptamos
        if (laneCode == null || !laneCode.matches("^[A-Z][0-9]$")) {
            throw new IllegalArgumentException("Invalid lane code format. It should be a letter followed by a number (e.g., A1).");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        this.laneCode = laneCode;
        this.description = description;
    }

    private String laneCode;
    private String description;

    @Override
    public String getLaneCode() {
        // TODO Auto-generated method stub
        // return null;
        return laneCode;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        // return null;
        return description;
    }

}
