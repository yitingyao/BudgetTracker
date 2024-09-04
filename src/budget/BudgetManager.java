package budget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BudgetManager {
	private List<Item> itemsList;
	private double balance;

	public BudgetManager() {
		this.itemsList = new ArrayList<>();
		this.balance = 0.00;
	}

	public void addToBalance(double income) {// Adds income to the balace. 
		this.balance += income;
	}

	public void addItem(String item, double price, int type) {// Adds an item with a price and category to the itemsList.
		this.itemsList.add(new Item(item, price, type));
	}

	public void showPurchasesByCategory(String category) {//The list of items displayed is not sorted. Items are displayed in the order they were added to the itemsList.
		double categoryTotal = 0.00;
		System.out.println();
		System.out.println(category + ":");
		ArrayList<Item> categoryList = new ArrayList<>();
		for (Item item : itemsList) {
			if (item.getCategory() == PurchaseCategory.valueOf(category.toUpperCase())) {// Filters items belonging to the specified category

				categoryList.add(item);
			}
		}

		if (categoryList.isEmpty()) {
			System.out.println("Purchase list is empty\n");
		} else {
			for (Item item : categoryList) {// Iterates over and displays filtered items, calculates total for category.

				System.out.printf("%s: $%.2f\n", item.getName(), item.getPrice());
				categoryTotal += item.getPrice();
			}
		}
		System.out.printf("Total for %s: $%.2f\n\n", category, categoryTotal);
	}

	public void showAllPurchases() {// Displays all purchases without sorting, items appear in order of purchase. 
		System.out.println();
		if (this.itemsList.size() > 0) {
			System.out.println("ALL:");
			for (Item item : itemsList) {
				System.out.printf("%s: $%.2f\n", item.getName(), item.getPrice());
			}
		} else {
			System.out.println("Purchase list is empty!\n");
		}
	}

	public void sortAllPurchases() {// Sorts and displays all purchases by category, and within each category by price. 
		if (this.itemsList.isEmpty()) {
			System.out.println("Purchase list is empty!");
			return;
		}

		itemsList.sort(Comparator.comparing(Item::getCategory).thenComparing(Item::getPrice));

		StringBuilder output = new StringBuilder();
		String[] categories = { "FOOD", "CLOTHES", "ENTERTAINMENT", "OTHER" };

		for (String category : categories) {
			double categoryTotal = 0.00;
			boolean hasItems = false;
			output.append(category).append(":\n");
			for (Item item : itemsList) {
				if (item.getCategory().toString().equalsIgnoreCase(category)) {
					output.append(String.format("%s: $%.2f\n", item.getName(), item.getPrice()));
					categoryTotal += item.getPrice();
					hasItems = true;
				}
			}
			if (!hasItems) {
				output.append("Total for ").append(category).append(": $0.00\n\n");
			} else {
				output.append(String.format("Total for %s: $%.2f\n\n", category, categoryTotal));
			}
		}

		System.out.println(output.toString());
	}

	public void sortAllTypes() {//Sorts categories by price in descending order.
		if (this.itemsList.isEmpty()) {
			System.out.println("Purchase list is empty!");
			return;
		}

		// Sort the itemsList by category and then by price in descending order
		itemsList.sort(Comparator.comparing(Item::getCategory).thenComparing(Item::getPrice).reversed());

		// Calculate and print totals by category
		double sum = 0.00;
		String[] categories = { "FOOD", "CLOTHES", "ENTERTAINMENT", "OTHER" };

		for (String category : categories) {
			double categoryTotal = 0.00;
			for (Item item : itemsList) {// Checks if the item's category matches the current category in the outer loop.
				// If the item belongs to the current category, it adds its price to categoryTotal.
				if (item.getCategory().toString().equalsIgnoreCase(category)) {
					categoryTotal += item.getPrice();
				}
			}
			System.out.printf("%s: $%.2f\n", category, categoryTotal);
			sum += categoryTotal;
		}

		System.out.printf("Total across all categores: $%.2f\n\n", sum);
	}

	public void sortType(int type) {// Sorts and displays items by a specific category type.
		if (this.itemsList.isEmpty()) {
			System.out.println("Purchase list is empty!");
			return;
		}

		String category = convertCategory(type);
		double sum = 0.00;
		ArrayList<Item> categoryList = new ArrayList<>();// Creates a list for the items of the specifcied category.
		for (Item item : itemsList) {
			if (item.getCategory() == PurchaseCategory.valueOf(category.toUpperCase())) {
				categoryList.add(item);
			}
		}

		if (!categoryList.isEmpty()) {
			for (Item item : categoryList) {
				System.out.printf("%s: $%.2f\n", item.getName(), item.getPrice());
				sum += item.getPrice();// Accumulates total price of the items in the category.
			}
		} else {
			System.out.println("Purchase list is empty!\n");
		}

		System.out.printf("Total for %s: $%.2f\n", category, sum);
	}

	private String convertCategory(int i) {// Converts an integer to a category string. 
		switch (i) {
		case 1:
			return "FOOD";
		case 2:
			return "CLOTHES";
		case 3:
			return "ENTERTAINMENT";
		case 4:
			return "OTHER";
		}
		return "";
	}

	public double getBalance() {
		return balance;
	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String generateFormattedOutput() {// Generates a formatted output of the purchases by category. 
		// Used in the saveFile method to create a structured text summary of a purchases shown in Purchases.txt
		StringBuilder output = new StringBuilder();
		String[] categories = { "FOOD", "CLOTHES", "ENTERTAINMENT", "OTHER" };
		double totalSpent = 0.00;

		for (String category : categories) {// Iterates through each category. 
			output.append(category).append(":\n");
			double catTotal = 0.00;
			for (Item item : itemsList) {// Adds each item in the category to the output.
				if (item.getCategory().toString().equalsIgnoreCase(category)) {
					output.append(String.format("%s: $%.2f\n", item.getName(), item.getPrice()));
					catTotal += item.getPrice();// Calculates total spent in the category.
				}
			}
			output.append(String.format("Total for %s: $%.2f\n\n", category, catTotal));
			totalSpent += catTotal;
		}

		output.append(String.format("Total Spent: $%.2f\n", totalSpent));
		output.append(String.format("Current Balance: $%.2f\n", balance - totalSpent));
		return output.toString();
	}
}
