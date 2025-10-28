package DAO;

import DTO.Account;
import DTO.Customer;

public interface CustomerDAO {
	void addNewCustomer(int bank_id,Customer customer, Account account);

	void updateCustomer(int customer_id, Customer customer);

	void deleteCustomer(int customer_id);

	void getCustomerById(int customer_id);

	void getCustomerByAccountNumber(long account_no);

	void getAllCustomers();
	
	void applyForLoan(int loan_id,int customer_id);
}
