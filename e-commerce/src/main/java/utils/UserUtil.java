package utils;

import java.util.Scanner;

import Driver.e_comDriver;
import Entity.Address;
import Entity.Order;
import Entity.User;
import IRepositories.IAddressRepository;
import IRepositories.ICartRepository;
import IRepositories.IOrderRepository;
import IRepositories.IUserRepository;
import jakarta.persistence.EntityManager;
import repositories.AddressRepository;
import repositories.CartRepository;
import repositories.OrderRepository;
import repositories.UserRepository;

public class UserUtil {
	static Scanner scanner = new Scanner(System.in);
	static int choice;
	private static EntityManager em=e_comDriver.em;

	static IUserRepository userRepository = new UserRepository(em);
	static ICartRepository cartRepository = new CartRepository(em);
	static IOrderRepository orderRepository = new OrderRepository(em);
	static IAddressRepository addressRepository = new AddressRepository(em);

	public static void userAction() {
		System.out.println("\nSelect one of the below:");
		System.out.println("1. Login \n2. Register \n3.Exit");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			e_comDriver.login();
			break;
		}
		case 2: {
			e_comDriver.register(EComUtil.userSetter());
			break;
		}
		case 3: {
			e_comDriver.start();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			userAction();
			break;
		}
	}

	public static void userMenu(User user) {
		System.out.println("\nWelcome User 😸\nSelect one of the below options: ");
		System.out.println("\n1.Manage Profile \n2. Manage Cart \n3. Manage Orders \n4. Logout");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			manageProfile(user);
			userMenu(user);
			break;
		}
		case 2: {
			manageCart(user);
			userMenu(user);
			break;
		}
		case 3: {
			manageOrders(user);
			userMenu(user);
			break;
		}
		case 4: {
			userAction();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			userMenu(user);
			break;
		}
	}

	static void manageProfile(User user) {
		/*
		 * 1. view Profile 2. update Profile(add/update addresses included) 3. delete
		 * account
		 */
		System.out.println("\nManage Profile 👨‍🦰‍ \nSelect one of the following action: ");
		System.out.println(
				"\n1. View Profile\n2. Update Profile\n3. Manage Addresses\n4. Delete Account \n5.Go back to menu");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			userRepository.getUserById(user.getU_id());
			break;
		}
		case 2: {
			User new_user = EComUtil.userSetter();
			userRepository.updateUser(user, new_user);
			break;
		}
		case 3: {
			manageAddresses(user);
			break;
		}
		case 4: {
			userRepository.deleteUser(user);
			break;
		}
		case 5: {
			userMenu(user);
			break;
		}

		default:
			System.err.println("Invalid choice!");
			manageProfile(user);
			break;
		}
	}

	// used in manageProfile()
	static void manageAddresses(User user) {
		System.out
				.println("\n1.View User Addresses \n2.Add new address \n3.Update address \n4.Delete Address \n5. Exit");
		choice = scanner.nextInt();
		switch (choice) {
		case 1: {
			addressRepository.getUserAddresses(user);
			break;
		}
		case 2: {
			Address address = EComUtil.addressSetter();
			addressRepository.addUserAddress(address, user);
			break;
		}
		case 3: {
			System.out.println("Enter the address id to be updated: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			Address address = EComUtil.addressSetter();
			addressRepository.updateUserAddress(id, address, user);
			break;
		}
		case 4: {
			System.out.println("Enter the address id to be deleted: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			addressRepository.deleteUserAddress(id, user);
			break;
		}
		case 5: {
			manageProfile(user);
			break;
		}
		default:
			System.err.println("Invalid choice!!");
			manageAddresses(user);
			break;
		}
	}

	static void manageCart(User user) {
		System.out.println("\nManage Cart 🛒‍ \nSelect one of the following action: ");
		System.out.println("\n1. Add product to Cart\n2. View cart\n3. Remove Product \n4.Go back to menu");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the product id: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter the product quantity to add: ");
			int quantity = scanner.nextInt();
			scanner.nextLine();

			cartRepository.addProduct(id, quantity, user);
			break;
		}
		case 2: {
			cartRepository.viewCart(user);
			break;
		}
		case 3: {
			System.out.println("Enter the product id: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter the product quantity to remove: ");
			int quantity = scanner.nextInt();
			scanner.nextLine();
			cartRepository.removeProduct(id, quantity, user);
			break;
		}
		case 4: {
			userMenu(user);
			break;
		}

		default:
			System.err.println("Invalid choice!");
			manageCart(user);
			break;
		}

	}

	static void manageOrders(User user) {
		System.out.println("\nManage Orders 📦‍ \nSelect one of the following action: ");
		System.out.println("\n1. Place an Order\n2. View Orders\n3. Cancel Order\n4.Track order \n5.Go back to menu");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			Order order = new Order();
			System.out.println("Enter the address id: ");
			int address_id = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter the payment method: ");
			String method = scanner.nextLine();

			orderRepository.placeOrder(address_id, method, order, user);// todo: add id and method to method signature
			break;
		}
		case 2: {
			orderRepository.getOrdersByUserId(user.getU_id());
			break;
		}
		case 3: {
			System.out.println("Enter the order id to cancel: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			orderRepository.cancelOrder(id, user);
			break;
		}
		case 4: {
			System.out.println("Enter the order id to track: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			orderRepository.getTrackingDetails(id, user);
			break;
		}
		case 5: {
			userMenu(user);
			break;
		}
		default:
			System.err.println("Invalid choice!");
			manageOrders(user);
			break;
		}
	}

}
