package DAO;

import java.util.List;

import DTO.Bank;
import DTO.Branch;

public interface BankDAO {
	void addBank(Bank bank);

	void updateBank(int bank_id, Bank bank);

	void deleteBank(int bankk_id);

	void getBankById(int bank_id);

	void getBankByBranch(int branch_id);

	void getAllBanks();

}
