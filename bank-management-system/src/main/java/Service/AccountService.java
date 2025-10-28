package Service;

import java.util.List;

import DAO.AccountDAO;
import DTO.Account;
import DTO.Branch;
import DTO.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class AccountService implements AccountDAO {
	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bms");

	@Override
	public void addAccount(int customer_id, Account account) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();

			Customer customer = em.find(Customer.class, customer_id);
			if (customer != null) {

				account.setHolderName(customer.getName());
				account.setCustomer(customer);

				em.persist(account);

				customer.getAccounts().add(account);

				et.commit();
				System.out.println("Account is added successfully!!");
				System.out.println("Account Details: ");
				System.out.println("Account No.: " + account.getAccNo() + "\nHolder Name: " + account.getHolderName()
						+ "\nType: " + account.getType() + " \nBalance: " + account.getBalance());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("Customer is not present for given customer id");
			}
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
	}

	@Override
	public void updateAccount(long account_no, Account account) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Account new_account = em.find(Account.class, account_no);

			if (new_account != null) {
				new_account.setHolderName(account.getHolderName());
				new_account.setType(account.getType());
				new_account.setBalance(account.getBalance());
				em.merge(new_account);
				et.commit();
				System.out.println("Account is updated successfully!!");
				System.out.println("Account Details: ");
				System.out.println("Account No.: " + account.getAccNo() + "\nHolder Name: " + account.getHolderName()
						+ "\nType: " + account.getType() + " \nBalance: " + account.getBalance());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("No account found for the given account number!");
			}

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteAccount(long account_no) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Account account = em.find(Account.class, account_no);
			if (account != null) {
				em.remove(account);
				et.commit();
				System.out.println(account_no + " no. account is deleted successfully!!!");
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("Account doesnt exists!!");
			}

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
	}

	@Override
	public void getAccount(long account_no) {
		EntityManager em = emf.createEntityManager();
		try {
			Account account = em.find(Account.class, account_no);
			if(account!=null) {
				System.out.println("Account Details: ");
				System.out.println("Account No.: " + account.getAccNo() + "\nHolder Name: " + account.getHolderName()
						+ "\nType: " + account.getType() + " \nBalance: " + account.getBalance());
				System.out.println("---------------------------------------------------");
			}
			else {
				System.err.println("Account doesnt exists!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getAllAccountsByCustomer(int customer_id) {
		EntityManager em = emf.createEntityManager();
		try {
			Customer customer = em.find(Customer.class, customer_id);
			if (customer != null) {
				List<Account> accounts = customer.getAccounts();
				if (accounts.size() > 0) {
					System.out.println("Accounts Details: ");
					for (Account account : accounts) {
						System.out.println(
								"Account No.: " + account.getAccNo() + "\nHolder Name: " + account.getHolderName()
										+ "\nType: " + account.getType() + " \nBalance: " + account.getBalance());
						System.out.println("---------------------------------------------------");
					}
				} else {
					System.err.println("No accounts found for the given customer!!");
				}
			} else {
				System.err.println("No customer found for the given id: " + customer_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
}
