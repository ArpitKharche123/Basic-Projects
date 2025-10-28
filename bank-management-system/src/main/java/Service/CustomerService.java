package Service;

import java.util.List;

import DAO.CustomerDAO;
import DTO.Account;
import DTO.Bank;
import DTO.Customer;
import DTO.Loan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class CustomerService implements CustomerDAO {
	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bms");

	@Override
	public void addNewCustomer(int bank_id, Customer customer, Account account) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();

			Bank bank = em.find(Bank.class, bank_id);
			if (bank != null) {
				customer.setBank(bank);

				bank.getCustomers().add(customer);

				customer.getAccounts().add(account);
				em.persist(customer);

				account.setCustomer(customer);
				em.persist(account);

				et.commit();
				System.out.println("Customer is added successfully!!");
				System.out.println("Customer Details: ");
				System.out.println("Customer Id: " + customer.getId() + "\nName: " + customer.getName() + "\nPhone: "
						+ customer.getPhone() + " \nAddress: " + customer.getAddress());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("Bank does'nt exists!!!");
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
	public void updateCustomer(int customer_id, Customer customer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();

			Customer new_customer = em.find(Customer.class, customer_id);

			if (new_customer != null) {
				new_customer.setName(customer.getName());
				new_customer.setPhone(customer.getPhone());
				new_customer.setAddress(customer.getAddress());
				em.merge(new_customer);
				et.commit();
				System.out.println("Customer is updated successfully!!");
				System.out.println("Customer Details: ");
				System.out.println("Customer Id: " + customer.getId() + "\nName: " + customer.getName() + "\nPhone: "
						+ customer.getPhone() + " \nAddress: " + customer.getAddress());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("No Customer found for the given customer id!!");
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
	public void deleteCustomer(int customer_id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();

			Customer customer = em.find(Customer.class, customer_id);
			if (customer != null) {
				em.remove(customer);
				et.commit();
				System.out.println("Customer with id: " + customer_id + " is deleted successfully!!!");
				System.out.println("---------------------------------------------------");

			} else {
				System.err.println("No Customer found for the given customer id!!");
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
	public void getCustomerById(int customer_id) {
		EntityManager em = emf.createEntityManager();
		try {
			Customer customer = em.find(Customer.class, customer_id);
			if (customer != null) {
				System.out.println("Customer is fetched successfully!!");
				System.out.println("Customer Details: ");
				System.out.println("Customer Id: " + customer.getId() + "\nName: " + customer.getName() + "\nPhone: "
						+ customer.getPhone() + " \nAddress: " + customer.getAddress());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("No Customer found for the given customer id!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getCustomerByAccountNumber(long account_no) {
		EntityManager em = emf.createEntityManager();
		try {
			Account account = em.find(Account.class, account_no);
			if (account != null) {
				Customer customer = account.getCustomer();
				if (customer != null) {
					System.out.println("Customer Details: ");
					System.out.println("Customer Id: " + customer.getId() + "\nName: " + customer.getName()
							+ "\nPhone: " + customer.getPhone() + " \nAddress: " + customer.getAddress());
					System.out.println("---------------------------------------------------");

				} else {
					System.err.println("No customer found for the given account no. !!");
				}
			} else {
				System.err.println("No account found for the given account no. : " + account_no);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getAllCustomers() {
		EntityManager em = emf.createEntityManager();
		try {
			String jpql = "Select c from Customer c";
			Query query = em.createQuery(jpql);

			List<Customer> customers = query.getResultList();
			if (customers.size() > 0) {
				System.out.println("All Customer Details: ");
				for (Customer customer : customers) {
					System.out.println("Customer Id: " + customer.getId() + "\nName: " + customer.getName()
							+ "\nPhone: " + customer.getPhone() + " \nAddress: " + customer.getAddress());
					System.out.println("---------------------------------------------------");
				}
			} else {
				System.err.println("No customers found!!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void applyForLoan(int loan_id, int customer_id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			Loan loan = em.find(Loan.class, loan_id);
			if (loan != null) {
				Customer customer = em.find(Customer.class, customer_id);
				if (customer != null) {
					et.begin();
					loan.getCustomers().add(customer);
					customer.getLoans().add(loan);

					em.merge(loan);
					em.merge(customer);

					et.commit();
					System.out.println(customer.getName() + " successfully applied for a loan!!");
				} else {
					System.err.println("Customer not found!!");
				}
			} else {
				System.err.println("Loan does'nt exists!!");
			}
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

}
