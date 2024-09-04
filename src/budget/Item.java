package budget;

public class Item {// Represents a purchase with a name, price, and category.

	private String name;
	private double price;
	private PurchaseCategory category;

	public Item(String name, double price, int type) {//Initializes an Item object with a name, price, and category type. 
		this.name = name;
		this.price = price;
		this.category = PurchaseCategory.values()[type - 1];/// Converts the integer 'type' to the corresponding enum value.
		// PurchaseCategory.values() gets all enum values, and 'type - 1' indexes the correct one.
		this.category = PurchaseCategory.values()[type - 1];
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public PurchaseCategory getCategory() {
		return category;
	}
}
