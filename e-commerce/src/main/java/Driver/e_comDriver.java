package Driver;

import java.util.Scanner;

import Entity.User;
import IRepositories.IUserRepository;
import jakarta.persistence.EntityManager;
import repositories.UserRepository;
import utils.AdminUtil;
import utils.EComUtil;
import utils.JPAUtil;
import utils.UserUtil;

public class e_comDriver {

	static Scanner scanner = new Scanner(System.in);
	static int choice;

	public static EntityManager em;
	static IUserRepository iUserRepository;

	public static void initializeAdmin() {
		try {
			User user = iUserRepository.getAdmin();
			if (user == null) {
				System.out.println("Add admin for e-commerce: \n");
				User user2 = EComUtil.userSetter();
				user2.setRole("admin");
				iUserRepository.addUser(user2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void login() {
		iUserRepository = new UserRepository(em);
		User user = EComUtil.userSetter();
		if (iUserRepository.authentication(user)) {
			System.out.println("Login Successful!");
			User loggedInUser = iUserRepository.loggedInUser();
			if (loggedInUser.getRole().equals("user"))
				UserUtil.userMenu(loggedInUser);
			else {
				AdminUtil.adminMenu();
			}
		} else {
			System.err.println("Login failed! Invalid Credentials!");
			start();
		}
	}

	public static void register(User user) {
		iUserRepository.addUser(user);
		System.out.println("User added successfully!");
		UserUtil.userAction();
	}

	public static void start() {
		System.out.println("**********Welcome To E-Commerce!**********\nSelect the roll:");
		System.out.println("1. Admin \n2. User");

		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			login();
			break;
		}
		case 2: {
			UserUtil.userAction();
			break;
		}
		default:
			System.err.println("Invalid choice!");
			start();
			break;
		}
	}

	public static void main(String[] args) {
		// initializeAdmin();
		// start();

		// to Catch JPA exceptions:
		System.out.println("Program started.");
		try {
			System.out.println("Creating EntityManager...");
			em = JPAUtil.getEntityManager();
			System.out.println("EntityManager created: " + em);
			iUserRepository = new UserRepository(em);

			initializeAdmin();
			start();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
