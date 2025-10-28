package Driver;

import java.util.Scanner;

import DAO.AccountDAO;
import DAO.BankDAO;
import DAO.BranchDAO;
import DAO.CustomerDAO;
import DAO.LoanDAO;
import DTO.Account;
import DTO.Bank;
import DTO.Branch;
import DTO.Customer;
import DTO.Loan;
import Service.AccountService;
import Service.BankService;
import Service.BranchService;
import Service.CustomerService;
import Service.LoanService;

public class BMSDriver {
	static int choice;
	static Scanner scanner = new Scanner(System.in);

	// ******************** Setter Caller Methods ********************

	static Bank bankSetter() {
		Bank bank = new Bank();
		System.out.println("Enter the bank name: ");
		bank.setName(scanner.nextLine());
		System.out.println("Enter the bank code: ");
		bank.setCode(scanner.nextLine());
		System.out.println("Enter the bank type: ");
		bank.setType(scanner.nextLine());
		return bank;
	}

	static Branch branchSetter() {
		Branch branch = new Branch();
		System.out.println("Enter the branch name: ");
		branch.setName(scanner.nextLine());
		System.out.println("Enter the ifsc code: ");
		branch.setIfsc(scanner.nextLine());
		System.out.println("Enter the branch address: ");
		branch.setAddress(scanner.nextLine());
		return branch;
	}

	static Customer customerSetter() {
		Customer customer = new Customer();
		System.out.println("Enter the customer name: ");
		customer.setName(scanner.nextLine());
		System.out.println("Enter the customer contact number(10 digits): ");
		customer.setPhone(scanner.nextLong());
		scanner.nextLine();
		System.out.println("Enter the customer address: ");
		customer.setAddress(scanner.nextLine());
		return customer;
	}

	static Account accountSetter(Customer customer) {
		Account account = new Account();
		account.setHolderName(customer.getName());
		System.out.println("Enter the account type: ");
		account.setType(scanner.nextLine());

		return account;
	}

	static Loan loanSetter() {
		Loan loan = new Loan();
		System.out.println("Enter the loan type: ");
		loan.setType(scanner.nextLine());
		System.out.println("Enter the loan amount: ");
		loan.setAmount(scanner.nextDouble());
		scanner.nextLine();
		return loan;
	}

//********************Service Caller Methods ********************

	static void manageBanks() {
		BankDAO bankDAO = new BankService();

		System.out.println("Manage Banks 🏦");
		System.out.println("Select one of the following: ");
		System.out.println("1. Add new Bank\n2. Update existing bank\n3. View Bank\n4. Delete Bank\n5. Exit");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			Bank bank = bankSetter();
			bankDAO.addBank(bank);
			break;
		}
		case 2: {
			System.out.println("Enter the bank id : ");
			int id = scanner.nextInt();
			scanner.nextLine();
			Bank bank = bankSetter();

			bankDAO.updateBank(id, bank);
			break;
		}
		case 3: {
			System.out.println("---------------------------------------");
			System.out.println("How do you want to view a bank? :\n");
			System.out.println("1. By bank id\n2. By Branch\n3. All banks");
			int c = scanner.nextInt();
			scanner.nextLine();
			switch (c) {
			case 1: {
				System.out.println("Enter the bank id: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				bankDAO.getBankById(id);
				break;
			}
			case 2: {
				System.out.println("Enter the branch id: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				bankDAO.getBankByBranch(id);
				break;
			}
			case 3: {
				bankDAO.getAllBanks();
				break;
			}
			default: {
				System.err.println("Invalid choice, please choose among the given choices!\n");
				manageBanks();
				break;
			}
			}
			break;
		}
		case 4: {
			System.out.println("Enter the bank id : ");
			int id = scanner.nextInt();
			scanner.nextLine();

			bankDAO.deleteBank(id);
			break;
		}
		case 5: {
			bmsMenu();
			break;
		}
		default: {
			System.err.println("Invalid choice, please choose among the given choices!\n");
			manageBanks();
			break;
		}
		}
	}

	static void manageBranches() {
		BranchDAO branchDAO = new BranchService();

		System.out.println("Manages branches 🪹");
		System.out.println("Select one of the following: ");
		System.out.println("1. Add new Branch\n2. Update existing branch\n3. View Branch\n4. Delete Branch\n5. Exit");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the bank id for which you have to add new branch:");
			int bank_id = scanner.nextInt();
			scanner.nextLine();
			Branch branch = branchSetter();
			branchDAO.addBranch(bank_id, branch);
			break;
		}
		case 2: {
			System.out.println("Enter the branch id : ");
			int id = scanner.nextInt();
			scanner.nextLine();

			Branch branch = branchSetter();

			branchDAO.updateBranch(id, branch);
			break;
		}
		case 3: {
			System.out.println("------------------------------------------");
			System.out.println("How do you want to view a branch? :\n");
			System.out.println("1. By  id\n2. By Bank\n3. All branches ");
			int c = scanner.nextInt();
			scanner.nextLine();
			switch (c) {
			case 1: {
				System.out.println("Enter the branch id: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				branchDAO.getBranchById(id);
				break;
			}
			case 2: {
				System.out.println("Enter the bank id: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				branchDAO.getAllBranchesByBank(id);
				break;
			}
			case 3: {
				branchDAO.getAllBranches();
				break;
			}
			default: {
				System.err.println("Invalid choice, please choose among the given choices!\n");
				manageBranches();
				break;
			}
			}
			break;
		}
		case 4: {
			System.out.println("Enter the branch id : ");
			int id = scanner.nextInt();
			scanner.nextLine();

			branchDAO.deleteBranch(id);
			break;
		}
		case 5: {
			bmsMenu();
			break;
		}
		default: {
			System.err.println("Invalid choice, please choose among the given choices!\n");
			manageBranches();
			break;
		}
		}
	}

	static void manageCustomers() {
		CustomerDAO customerDAO = new CustomerService();
		System.out.println("Manages customers 👨‍👩‍👧‍👦");
		System.out.println("Select one of the following: ");
		System.out.println(
				"1. Add new Customer\n2. Update existing Customer\n3. View Customer\n4. Delete Customer\n5.Apply for loan\n6. Exit");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1: {
			Customer customer = customerSetter();
			Account account = accountSetter(customer);
			System.out.println("Enter the bank id for which you want to add new customer:");
			int bank_id = scanner.nextInt();
			scanner.nextLine();
			customerDAO.addNewCustomer(bank_id, customer, account);
			break;
		}
		case 2: {
			System.out.println("Enter the customer id : ");
			int id = scanner.nextInt();
			scanner.nextLine();
			Customer customer = customerSetter();

			customerDAO.updateCustomer(id, customer);
			break;
		}
		case 3: {
			System.out.println("------------------------------------------------");
			System.out.println("How do you want to view a customer? :\n");
			System.out.println("1. By  id\n2. By Account Number\n3. All customers ");
			int c = scanner.nextInt();
			switch (c) {
			case 1: {
				System.out.println("Enter the customer id: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				customerDAO.getCustomerById(id);
				break;
			}
			case 2: {
				System.out.println("Enter the account number: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				customerDAO.getCustomerByAccountNumber(id);
				break;
			}
			case 3: {
				customerDAO.getAllCustomers();
				break;
			}
			default: {
				System.err.println("Invalid choice, please choose among the given choices!\n");
				manageCustomers();
				break;
			}
			}
			break;
		}
		case 4: {
			System.out.println("Enter the customer id : ");
			int id = scanner.nextInt();
			scanner.nextLine();

			customerDAO.deleteCustomer(id);
			break;
		}
		case 5: {
			System.out.println("Enter your customer id: ");
			int customer_id = scanner.nextInt();
			System.out.println("Enter the loan id: ");
			int loan_id = scanner.nextInt();
			customerDAO.applyForLoan(loan_id, customer_id);
			break;
		}
		case 6: {
			bmsMenu();
			break;
		}
		default: {
			System.err.println("Invalid choice, please choose among the given choices!\n");
			manageCustomers();
			break;
		}
		}

	}

	static void manageAccounts() {
		AccountDAO accountDAO = new AccountService();
		System.out.println("Manage accounts 🧾");
		System.out.println("Select one of the following: ");
		System.out.println(
				"1. Add new Account\n2. Update existing Account\n3. View Accounts\n4. Delete Account\n5. Exit");
		choice = scanner.nextInt();

		switch (choice) {
		case 1: {
			System.out.println("Enter the customer id for which you want to add new account: ");
			int customer_id = scanner.nextInt();
			scanner.nextLine();

			Account account = new Account();
			System.out.println("Enter the account type: ");
			account.setType(scanner.nextLine());

			accountDAO.addAccount(customer_id, account);
			break;
		}
		case 2: {
			System.out.println("Enter the account number : ");
			long accno = scanner.nextLong();
			scanner.nextLine();

			Account account = new Account();
			System.out.println("Enter the type: ");
			account.setType(scanner.nextLine());

			accountDAO.updateAccount(accno, account);
			break;
		}
		case 3: {
			System.out.println("------------------------------------------");
			System.out.println("How do you want to view an account? :\n");
			System.out.println("1. By Account Number\n2. By customer ");
			int c = scanner.nextInt();
			switch (c) {
			case 1: {
				System.out.println("Enter the account number: ");
				long accno = scanner.nextLong();
				scanner.nextLine();

				accountDAO.getAccount(accno);
				break;
			}
			case 2: {
				System.out.println("Enter the customer id: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				accountDAO.getAllAccountsByCustomer(id);
				break;
			}
			default: {
				System.err.println("Invalid choice, please choose among the given choices!\n");
				manageAccounts();
				break;
			}
			}
			break;
		}
		case 4: {
			System.out.println("Enter the account number: ");
			long accno = scanner.nextLong();
			scanner.nextLine();

			accountDAO.deleteAccount(accno);
			break;
		}
		case 5: {
			bmsMenu();
			break;
		}
		default: {
			System.err.println("Invalid choice, please choose among the given choices!\n");
			manageAccounts();
			break;
		}
		}
	}

	static void manageLoans() {
		LoanDAO loanDAO = new LoanService();
		System.out.println("Manages Loans/Offers 🫴💰");
		System.out.println("Select one of the following: ");
		System.out.println("1. Add new Loan\n2. Update existing Loan\n3. View Loan\n4. Delete Loan\n5. Exit");
		choice = scanner.nextInt();
		switch (choice) {
		case 1: {
			System.out.println("Enter branch id for which you have to add the loan: ");
			int branch_id = scanner.nextInt();
			scanner.nextLine();
			Loan loan = loanSetter();
			loanDAO.addLoan(branch_id, loan);
			break;
		}
		case 2: {
			System.out.println("Enter the loan id : ");
			int id = scanner.nextInt();
			scanner.nextLine();

			Loan loan = loanSetter();

			loanDAO.updateLoan(id, loan);
			break;
		}
		case 3: {
			System.out.println("----------------------------------------");
			System.out.println("How do you want to view a loan? :\n");
			System.out.println("1. By  id\n2. By branch\n3. All loans ");
			int c = scanner.nextInt();
			switch (c) {
			case 1: {
				System.out.println("Enter the loan id: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				loanDAO.getLoanById(id);
				break;
			}
			case 2: {
				System.out.println("Enter the branch id: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				loanDAO.getLoanByBranch(id);
				break;
			}
			case 3: {
				loanDAO.getAllLoans();
				break;
			}
			default: {
				System.err.println("Invalid choice, please choose among the given choices!\n");
				manageLoans();
				break;
			}
			}
			break;
		}
		case 4: {
			System.out.println("Enter the loan id : ");
			int id = scanner.nextInt();
			scanner.nextLine();

			loanDAO.deleteLoan(id);
			break;
		}
		case 5: {
			bmsMenu();
			break;
		}
		default: {
			System.err.println("Invalid choice, please choose among the given choices!\n");
			manageLoans();
			break;
		}
		}
	}

//******************** UI Methods ********************

	static void bmsMenu() {
		System.out.println("*****Welcome to Bank Management System*****");
		System.out.println("Select one of the following: ");
		System.out.println(
				"1. Manage Banks\n2. Manage Branches\n3. Manage Customers\n4. Manage Accounts\n5. Manage Loans\n6. Exit");
		choice = scanner.nextInt();
		switch (choice) {
		case 1: {
			manageBanks();
			break;
		}
		case 2: {
			manageBranches();
			break;
		}
		case 3: {
			manageCustomers();
			break;
		}
		case 4: {
			manageAccounts();
			break;
		}
		case 5: {
			manageLoans();
			break;
		}
		case 6: {
			System.out.println("Thank you 😸, Visit again!!!");
			scanner.close();
			System.exit(0);
		}
		default: {
			System.err.println("Invalid choice, please choose among the given choices!\n");
			bmsMenu();
			break;
		}
		}
	}

	public static void main(String[] args) {
		bmsMenu();
	}
}
