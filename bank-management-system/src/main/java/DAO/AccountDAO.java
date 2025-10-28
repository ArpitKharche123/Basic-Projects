package DAO;

import DTO.Account;

public interface AccountDAO {
	void addAccount(int customer_id,Account account);

	void updateAccount(long account_no,Account account);

	void deleteAccount(long account_no);

	void getAccount(long account_no);

//	void getAllAccountsByBranch(int branch_id);
	
	void getAllAccountsByCustomer(int customer_id);

}
