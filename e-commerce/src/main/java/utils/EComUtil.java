package utils;

import java.util.Scanner;

import Entity.Address;
import Entity.Product;
import Entity.ProductCategory;
import Entity.User;

public class EComUtil {

	static Scanner scanner = new Scanner(System.in);

	public static Product productSetter() {
		Product product = new Product();

		System.out.println("Enter the product name: ");
		product.setName(scanner.nextLine());

		System.out.println("Enter the price: ");
		product.setPrice(scanner.nextDouble());
		scanner.nextLine();

		System.out.println("Enter the product description: ");
		product.setDescription(scanner.nextLine());

		return product;
	}

	public static ProductCategory categorySetter() {
		ProductCategory category = new ProductCategory();

		System.out.println("Enter the category name: ");
		category.setName(scanner.nextLine());

		return category;
	}

	public static User userSetter() {
		User user = new User();

		System.out.println("Enter the user name: ");
		user.setName(scanner.nextLine());

		System.out.println("Enter the user email: ");
		user.setEmail(scanner.nextLine());

		return user;
	}

	public static Address addressSetter() {
		Address address = new Address();

		System.out.println("Enter the house nummber:");
		address.setHouse_number(scanner.nextInt());
		scanner.nextLine();

		System.out.println("Enter the area: ");
		address.setArea(scanner.nextLine());

		System.out.println("Enter the city: ");
		address.setCity(scanner.nextLine());

		System.out.println("Enter the pincode: ");
		address.setPincode(scanner.nextInt());
		scanner.nextLine();

		System.out.println("Enter the state: ");
		address.setState(scanner.nextLine());

		System.out.println("Enter the country: ");
		address.setCountry(scanner.nextLine());

		return address;
	}
}
