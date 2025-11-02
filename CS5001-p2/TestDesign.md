# TestDesign
The project was developed following an incremental and test-driven approach.
I started by writing a few basic tests for each class to check the essential behaviour of the system.
Then, I implemented the simplest possible version of the code just to make those tests run — and fail.
From there, I gradually improved the implementation until all the basic tests passed.
Once the main functionality was working, I created additional tests to explore edge cases and unusual situations, going a bit further than the basic requirements to make sure the program was solid and behaved correctly under all conditions.


## VendingMachineProduct.java
The class represents a single product type in a vending machine, identified by a lane code (e.g. "A1").
Validation ensures that each product has a valid lane code (`[Letter][Number]`) and a non-empty description.
This prevents invalid product creation and enforces data consistency.

1. Checks that constructor stores lane code and description correctly.
2. Ensures getters return consistent values.
3. Verifies independent state between different instances.
4. Confirms valid input does not throw exceptions.
5. Throws exception for invalid lane codes (non letter+number).
6. Throws exception for empty lane code.
7. Throws exception for empty description.
8. Throws exception for null lane code or description.

## ProductRecord.java
The class records stock and sales information for a single product line.
Each `ProductRecord` keeps its own independent counts and throws a `ProductUnavailableException` when a product is unavailable.
This separation allows the vending machine to manage multiple products easily.

1. Initializes with zero stock and sales.
2. Increases available count when adding one item.
3. Increases available count correctly after multiple additions.
4. Reduces stock and increases sales after a purchase.
5. Throws `ProductUnavailableException` when buying with no stock.
6. Updates stock and sales correctly after multiple purchases.
7. Returns the same product instance as in constructor.
8. Prevents negative stock after purchases.
9. Keeps accurate sales count after multiple add–buy cycles.
10. Maintains correct stock after mixed add and buy operations.
11. Works independently for different records.
12. Allows adding stock again after sales.
13. Keeps original product reference unchanged.
14. Purchase affects only this record, not others.
15. Add and buy sequence restores stock balance.


## VendingMachine.java
The vending machine uses a `HashMap<String, IProductRecord>` to store and manage products. Each lane code (like “A1” or “B2”) is used as a unique key linked to a product record that tracks stock and sales. This structure allows very fast access to products by lane code, easy registration and removal, and dynamic resizing without worrying about fixed limits. It also matches perfectly with the method parameters in the interface, which use `String laneCode` in most operations.

Using arrays or matrices would make the implementation much more complex. Arrays have a fixed size and would require manual searching to find products by their lane code. Matrices would not fit the logic of the program, since the machine is not accessed by physical coordinates but by lane codes in string format. Both approaches would increase the amount of code, reduce readability, and make the program harder to maintain.

1. Registers a new product successfully.
2. Throws `LaneCodeAlreadyInUseException` when registering the same product twice.
3. Unregisters a registered product correctly.
4. Throws `LaneCodeNotRegisteredException` when unregistering an unregistered product.
5. Adds item to a registered lane successfully.
6. Throws `LaneCodeNotRegisteredException` when adding to an unknown lane.
7. Allows multiple items to be added to the same lane.
8. Buys an item successfully and updates counts.
9. Throws `LaneCodeNotRegisteredException` when buying from an unknown lane.
10. Throws `ProductUnavailableException` when buying with no stock.
11. Returns correct number of registered products.
12. Returns correct total number of items in the machine.
13. Returns correct number of items for a given lane.
14. Returns correct number of sales for a given lane.
15. Tracks sales independently for each product.
16. Returns the most popular product (highest sales).
17. Throws `LaneCodeNotRegisteredException` when calling `getMostPopular()` with no products.
18. Allows re-registering a product after it has been unregistered.
19. Maintains consistent totals after mixed add and buy operations.
20. Handles multiple lanes independently without interference.

## Factory.java
The factory class follows the Singleton pattern, ensuring that only one instance of `Factory` exists.
It centralizes object creation for `VendingMachineProduct`, `ProductRecord`, and `VendingMachine`, promoting modularity and separation of concerns.
Each call to a `make*()` method returns a new independent instance.

1. Returns the same instance every time (singleton).
2. Creates non-null `VendingMachineProduct` objects.
3. Creates non-null `ProductRecord` objects.
4. Creates non-null `VendingMachine` objects.
5. Produces `VendingMachineProduct` instances of correct type.
6. Produces `ProductRecord` instances of correct type.
7. Produces `VendingMachine` instances of correct type.
8. Returns different `VendingMachineProduct` instances on separate calls.
9. Returns different `ProductRecord` instances on separate calls.
10. Returns different `VendingMachine` instances on separate calls.

