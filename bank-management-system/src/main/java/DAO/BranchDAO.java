package DAO;

import DTO.Branch;

public interface BranchDAO {
	void addBranch(int bank_id,Branch branch);

	void updateBranch(int branch_id,Branch branch);

	void deleteBranch(int branch_id);

	void getBranchById(int branch_id);

	void getAllBranches();

	void getAllBranchesByBank(int bank_id);
	
}
