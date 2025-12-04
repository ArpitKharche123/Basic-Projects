package utils;

import java.util.Scanner;

import Driver.e_comDriver;
import Entity.Product;
import Entity.ProductCategory;
import IRepositories.ICategoryRepository;
import IRepositories.IOrderRepository;
import IRepositories.IPaymentRepository;
import IRepositories.IProductRepository;
import IRepositories.IUserRepository;
import jakarta.persistence.EntityManager;
import repositories.CategoryRepository;
import repositories.OrderRepository;
import repositories.PaymentRepository;
import repositories.ProductRepository;
import repositories.UserRepository;

public class AdminUtil {
	static Scanner scanner = new Scanner(System.in);
	static int choice;
	private static EntityManager em=e_comDriver.em;

	static IUserRepository iUserRepository = new UserRepository(em);
	static IProductRepository iProductRepository = new ProductRepository(em);
	static IOrderRepository iOrderRepository = new OrderRepository(em);
	static IPaymentRepository iPaymentRepository = new PaymentRepository(em);
	static ICategoryRepository icategoryRepository = new CategoryRepository(em);

	public static void adminMenu() {
		System.out.println("*****Welcome to Admin Menu***** \n Select one of the actions below: ");
		System.out.println(
				"\n1.Manage Users \n2. Manage Products \n3. Manage Orders \n4.Manage Payments \n5.Manage Product Category \n6.Logout");

		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			manageUsers();
			adminMenu();
			break;
		}
		case 2: {
			manageProducts();
			adminMenu();
			break;
		}
		case 3: {
			manageOrders();
			adminMenu();
			break;
		}
		case 4: {
			managePayments();
			adminMenu();
			break;
		}
		case 5: {
			manageProductCategory();
			adminMenu();
		}
		case 6: {
			e_comDriver.start();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			adminMenu();
			break;
		}
	}

	static void manageUsers() {
		/*
		 * 1.getUserById User 2. getAllUsers List
		 */
		System.out.println("\nManage Users 👨‍🦰 \nSelect one of the following action: ");
		System.out.println("\n1. Display User\n2.Display all users\n3.Go back to Menu");

		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the user id:");
			int id = scanner.nextInt();
			scanner.nextLine();
			iUserRepository.getUserById(id);
			break;
		}
		case 2: {
			iUserRepository.getAllUsers();
			break;
		}
		case 3: {
			adminMenu();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			manageUsers();
			break;
		}
	}

	static void manageProducts() {
		System.out.println("\nManage Products ‍📦 \nSelect one of the following action: ");
		System.out.println("\n1. Display Product\n2. Display all products\n3. Add product \n4.Update Product"
				+ "\n5. set category to product \n6. Set product out of stock \n7. Remove Product \n8.View all Products by category \n9.Go back to Menu");

		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the product id:");
			int id = scanner.nextInt();
			scanner.nextLine();
			iProductRepository.getProductById(id);
			break;
		}
		case 2: {
			iProductRepository.getAllProducts();
			break;
		}
		case 3: {
			Product product = EComUtil.productSetter();
			iProductRepository.addProduct(product);
			break;
		}
		case 4: {
			System.out.println("Enter the product id to be updated:");
			int id = scanner.nextInt();
			Product product = EComUtil.productSetter();
			iProductRepository.updateProduct(id, product);
			break;
		}
		case 5: {
			System.out.println("Enter the product id:");
			int id = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter the category id:");
			int c_id = scanner.nextInt();
			scanner.nextLine();

			iProductRepository.setCategoryToProduct(id, c_id);
			break;
		}
		case 6: {
			System.out.println("Enter the product id:");
			int id = scanner.nextInt();
			scanner.nextLine();

			iProductRepository.setOutOfStock(id);
			break;
		}
		case 7: {
			System.out.println("Enter the product id to be deleted:");
			int id = scanner.nextInt();
			scanner.nextLine();

			iProductRepository.deleteProduct(id);
			break;
		}
		case 8: {
			System.out.println("Enter the category id: ");
			int id = scanner.nextInt();
			scanner.nextLine();

			iProductRepository.getAllProductsByCategory(id);
			break;
		}
		case 9: {
			adminMenu();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			manageProducts();
			break;
		}
	}

	static void manageOrders() {
		/*
		 * 1. getAllOrder List 2. getOrderByOrderId Order 3. getOrdersByUserId List 4.
		 * getOrdertracking(int order id) Hard Delete Order if status = cancelled
		 */
		System.out.println("\nManage Orders ‍ \nSelect one of the following action: ");
		System.out.println(
				"\n1. Display Order\n2. Display all orders\n3. Get User Orders \n4.View Order Tracking \n5.Delete Order \n6.Go back to menu");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the order number to be fetched: ");
			int num = scanner.nextInt();
			scanner.nextLine();
			iOrderRepository.getById(num);
			break;
		}
		case 2: {
			iOrderRepository.getAllOrder();
			break;
		}
		case 3: {
			System.out.println("Enter the user id whose orders to be fetched: ");
			int num = scanner.nextInt();
			scanner.nextLine();
			iOrderRepository.getOrdersByUserId(num);
			break;
		}
		case 4: {
			System.out.println("Enter the order number to be tracked: ");
			int num = scanner.nextInt();
			scanner.nextLine();
			iOrderRepository.getTrackingDetails(num);
			break;
		}
		case 5: {
			System.out.println("Enter the order number to be deleted: ");
			int num = scanner.nextInt();
			scanner.nextLine();
			iOrderRepository.deleteOrder(num);
			break;
		}
		case 6: {
			adminMenu();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			manageOrders();
			break;
		}
	}

	static void managePayments() {
		System.out.println("\nManage Payments ‍ \nSelect one of the following action: ");
		System.out.println(
				"\n1. Display Payment\n2. Display User Payment\n3. Get all Payments \n4.Delete Payment \n5.Go back to menu");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the payment id to be fetched: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			iPaymentRepository.getById(id);
			break;
		}
		case 2: {
			System.out.println("Enter the user id to get his payments: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			iPaymentRepository.getPaymentsByUserId(id);
			break;
		}
		case 3: {
			iPaymentRepository.getAllPayments();
			break;
		}
		case 4: {
			System.out.println("Enter the payment id to be deleted: ");
			int id = scanner.nextInt();
			scanner.nextLine();

			iPaymentRepository.deletePayment(id);
			break;
		}
		case 5: {
			adminMenu();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			managePayments();
			break;
		}
	}

	static void manageProductCategory() {
		System.out.println("\nManage Prodcut Category ‍ \nSelect one of the following action: ");
		System.out.println(
				"\n1. Display Category\n2. Display All Categories\n3. Add new Category \n4.Update Category \n5.Delete Category \n6. Go back to menu");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the category id to be fetched: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			icategoryRepository.getProductCategory(id);
			break;
		}
		case 2: {
			icategoryRepository.getAllProductCategories();
			break;
		}
		case 3: {
			ProductCategory category = EComUtil.categorySetter();
			icategoryRepository.addCategory(category);
			break;
		}
		case 4: {
			System.out.println("Enter the category id to be updated: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			ProductCategory category = EComUtil.categorySetter();
			icategoryRepository.updateCategory(id, category);
			break;
		}
		case 5: {
			System.out.println("Enter the category id to be deleted: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			icategoryRepository.deleteCategory(id);
		}
		case 6: {
			adminMenu();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			manageProductCategory();
			break;
		}
	}

}
