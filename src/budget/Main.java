package budget;

import java.util.Scanner;

public class Main {

	private static BudgetManager budgetManager = new BudgetManager();
	private static FileManager fileManager = new FileManager();
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
			mainMenu();
		}
	}

	private static void mainMenu() {
		System.out.println("Choose your action:");
		System.out.println("1) Add income");
		System.out.println("2) Add purchase");
		System.out.println("3) Show list of purchases");
		System.out.println("4) Balance");
		System.out.println("5) Save");
		System.out.println("6) Load");
		System.out.println("7) Analyze (Sort)");
		System.out.println("0) Exit");

		int choice = Integer.parseInt(scanner.nextLine());

		switch (choice) {
		case 1:
			addIncome();
			break;
		case 2:
			addPurchaseMenu();
			break;
		case 3:
			showPurchasesMenu();
			break;
		case 4:
			showBalance();
			break;
		case 5:
			save();
			break;
		case 6:
			load();
			break;
		case 7:
			sortMenu();
			break;
		case 0:
			System.out.println("Bye!");
			System.exit(0);
		default:
			System.out.println("Invalid choice. Please try again.");
		}
	}

	private static void sortMenu() {
		while (true) { // Use a while true loop to stay in sorting menu unless chosen otherwise.
			System.out.println("How do you want to sort?");
			System.out.println("1) Sort all purchases");
			System.out.println("2) Sort by type");
			System.out.println("3) Sort certain type");
			System.out.println("4) Back");

			int method = Integer.parseInt(scanner.nextLine());

			switch (method) {
			case 1:
				budgetManager.sortAllPurchases();
				break;
			case 2:
				budgetManager.sortAllTypes();
				break;
			case 3:
				System.out.println("Choose the type of purchase to sort:");
				System.out.println("1) Food");
				System.out.println("2) Clothes");
				System.out.println("3) Entertainment");
				System.out.println("4) Other");
				int type = Integer.parseInt(scanner.nextLine());
				budgetManager.sortType(type);
				break;
			case 4:
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void addIncome() {
		System.out.println("Enter income:");
		try {
			double income = Double.parseDouble(scanner.nextLine());
			budgetManager.addToBalance(income);
			System.out.println("Income was added!");
		} catch (NumberFormatException e) {
			System.out.println("Invalid choice. Please enter a valid number.");
		}
	}

	private static void addPurchaseMenu() {
		System.out.println("Choose the type of purchase:");
		System.out.println("1) Food");
		System.out.println("2) Clothes");
		System.out.println("3) Entertainment");
		System.out.println("4) Other");
		int category = Integer.parseInt(scanner.nextLine());

		System.out.println("Enter purchase name:");
		String name = scanner.nextLine();
		System.out.println("Enter purchase price:");
		double price = Double.parseDouble(scanner.nextLine());

		budgetManager.addItem(name, price, category);
		System.out.println("Purchase was added!");
	}

	private static void showPurchasesMenu() {
		System.out.println("Choose the type of purchases to show:");
		System.out.println("1) Food");
		System.out.println("2) Clothes");
		System.out.println("3) Entertainment");
		System.out.println("4) Other");
		System.out.println("5) All");
		int choice = Integer.parseInt(scanner.nextLine());

		switch (choice) {
		case 1:
			budgetManager.showPurchasesByCategory("FOOD");
			break;
		case 2:
			budgetManager.showPurchasesByCategory("CLOTHES");
			break;
		case 3:
			budgetManager.showPurchasesByCategory("ENTERTAINMENT");
			break;
		case 4:
			budgetManager.showPurchasesByCategory("OTHER");
			break;
		case 5:
			budgetManager.showAllPurchases(); // Calls showAllPurchases() in BudgetManager class.
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
		}
	}

	private static void showBalance() {
		System.out.printf("Balance: $%.2f%n", budgetManager.getBalance());
	}

	private static void save() {
		fileManager.saveFile(budgetManager);
	}

	private static void load() {
		fileManager.loadFile(budgetManager);
	}
}
