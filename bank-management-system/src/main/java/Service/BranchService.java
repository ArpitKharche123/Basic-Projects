package Service;

import java.util.List;

import DAO.BranchDAO;
import DTO.Bank;
import DTO.Branch;
import DTO.Customer;
import DTO.Loan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class BranchService implements BranchDAO {
	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bms");

	@Override
	public void addBranch(int bank_id, Branch branch) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Bank bank = em.find(Bank.class, bank_id);
			if (bank != null) {

				// Connecting bank to branch
				branch.setBank(bank);

				em.persist(branch);// saving branch first

				// Connecting branch to bank
				bank.getBranches().add(branch);

				et.commit();
				System.out.println("Branch is added successfully!!");
				System.out.println("Branch Details: ");
				System.out.println("Branch Id: " + branch.getId() + "\nName: " + branch.getName() + "\nIFSC: "
						+ branch.getIfsc() + " \nAddress: " + branch.getAddress());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("No bank found for given bank id!!!");
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
	public void updateBranch(int branch_id, Branch branch) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Branch new_branch = em.find(Branch.class, branch_id);

		new_branch.setName(branch.getName());
		new_branch.setIfsc(branch.getIfsc());
		new_branch.setAddress(branch.getAddress());

		try {
			et.begin();
			em.merge(new_branch);
			et.commit();
			System.out.println("Branch is updated successfully!!");
			System.out.println("Branch Details: ");
			System.out.println("Branch Id: " + branch.getId() + "\nName: " + branch.getName() + "\nIFSC: "
					+ branch.getIfsc() + " \nAddress: " + branch.getAddress());
			System.out.println("---------------------------------------------------");

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
	public void deleteBranch(int branch_id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Branch branch = em.find(Branch.class, branch_id);
			if (branch != null) {
			
				// to break relationships between each Loan and Customer
				//Removing branch then loans, ignoring customer
				for (Loan loan : branch.getLoan_offers()) {
					for (Customer customer : loan.getCustomers()) {
						customer.getLoans().remove(loan); // remove loan reference from customer
					}
					loan.getCustomers().clear(); // clear customer list (removes join table entries)
				}

				// Now safely delete the branch (will cascade delete loans)
				em.remove(branch);// removes branch and loans a well
				
				et.commit();
				System.out.println(branch_id + " no. branch is deleted successfully!!!");
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("Branch does'nt exists!!!");
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
	public void getBranchById(int branch_id) {
		EntityManager em = emf.createEntityManager();
		try {
			Branch branch = em.find(Branch.class, branch_id);
			System.out.println("Branch is fetched successfully!!");
			System.out.println("Branch Details: ");
			System.out.println("Branch Id: " + branch.getId() + "\nName: " + branch.getName() + "\nIFSC: "
					+ branch.getIfsc() + " \nAddress: " + branch.getAddress());
			System.out.println("---------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getAllBranches() {
		EntityManager em = emf.createEntityManager();
		try {
			String jpql = "Select b from Branch b";
			Query query = em.createQuery(jpql);

			List<Branch> branches = query.getResultList();
			if (branches.size() > 0) {
				System.out.println("All Branches Details: ");
				for (Branch branch : branches) {
					System.out.println("Branch Id: " + branch.getId() + "\nName: " + branch.getName() + "\nIFSC: "
							+ branch.getIfsc() + " \nAddress: " + branch.getAddress());
					System.out.println("---------------------------------------------------");
				}
			} else {
				System.err.println("No Branches found!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getAllBranchesByBank(int bank_id) {
		EntityManager em = emf.createEntityManager();
		try {
			Bank bank = em.find(Bank.class, bank_id);
			if (bank != null) {
				List<Branch> branches = bank.getBranches();
				if (branches != null) {
					System.out.println("Branch Details: ");
					for (Branch branch : branches) {
						System.out.println("Branch Id: " + branch.getId() + "\nName: " + branch.getName() + "\nIFSC: "
								+ branch.getIfsc() + " \nAddress: " + branch.getAddress());
						System.out.println("---------------------------------------------------");
					}
				} else {
					System.err.println("No branches found for the given bank id!!");
				}
			} else {
				System.err.println("No Bank found for the given id: " + bank_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

}
