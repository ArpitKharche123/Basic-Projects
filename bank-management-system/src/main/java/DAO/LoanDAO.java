package DAO;

import DTO.Loan;

public interface LoanDAO {
	void addLoan(int branch_id,Loan loan);

	void updateLoan(int loan_id,Loan loan);

	void deleteLoan(int loan_id);

	void getLoanById(int loan_id);

	void getLoanByBranch(int branch_id);

	void getAllLoans();
}
