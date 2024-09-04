package budget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

	private static final String FILE_NAME = "Purchases.txt";

	public void saveFile(BudgetManager budgetManager) {// Persists data to Purchases.txt, creates file if none exists. 
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			writer.write(budgetManager.generateFormattedOutput());//Calls generateFormattedOutput() on the budgetManager object to retrieve a properly formatted string of all data.
			System.out.println("Data saved to Purchases.txt.");
		} catch (IOException e) {
			System.out.println("An error occurred while saving to Purchases.txt.");
			e.printStackTrace();
		}
	}

	public void loadFile(BudgetManager budgetManager) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			budgetManager.getItemsList().clear();// Clear the current items list to ensure fresh loading of current data in file.

			double balance = 0.00;
			PurchaseCategory currentCategory = null;// Placeholder to store the current category.

			while ((line = reader.readLine()) != null) {// Reads and processes each line of the file, adding items or setting balance based on the content.
				if (line.trim().isEmpty()) {
					continue; // Skip empty lines in the file to avoid error. 
				} else if (line.endsWith(":")) {
					// It's a category line
					currentCategory = PurchaseCategory.valueOf(line.replace(":", "").trim().toUpperCase());
				} else if (line.contains(": $")) {
					// It's an item or balance line
					String[] parts = line.split(": \\$"); // Split the line into two parts: name and price. 
					if (parts.length == 2) {
						String name = parts[0].trim();
						double price = Double.parseDouble(parts[1].trim());
						if (!name.startsWith("Total for") && !name.startsWith("Total Spent")
								&& !name.startsWith("Current Balance")) {
							budgetManager.addItem(name, price, currentCategory.ordinal() + 1);// Add theitem to the BudgetManager's itemList
							// Uses budgetManager object to access its addItem method.
						} else if (name.equals("Current Balance")) {//Set the balance if the line contains "Current Balance"
							balance = price;
						}
					}
				}
			}

			budgetManager.setBalance(balance);// Set the updated balance in BudgetManager based on the data from the file

			System.out.println("Data loaded from Purchases.txt.");

		} catch (FileNotFoundException e) {
			System.out.println("No existing data file found. Please save your purchases first.");
		} catch (IOException e) {
			System.out.println("An error occurred while loading from Purchases.txt.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("An unexpected error occurred.");
			e.printStackTrace();
		}
	}

}
