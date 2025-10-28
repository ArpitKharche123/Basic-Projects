package Service;

import java.util.List;

import DAO.LoanDAO;
import DTO.Account;
import DTO.Branch;
import DTO.Customer;
import DTO.Loan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class LoanService implements LoanDAO {
	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bms");

	@Override
	public void addLoan(int branch_id, Loan loan) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();

			Branch branch = em.find(Branch.class, branch_id);
			if (branch != null) {

				loan.setBranch(branch);

				em.persist(loan);

				branch.getLoan_offers().add(loan);

				et.commit();
				System.out.println("Loan is added successfully!!");
				System.out.println("Loan Details: ");
				System.out.println(
						"loan Id: " + loan.getId() + "\nType: " + loan.getType() + "\nAmount: " + loan.getAmount());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("Branch not found for given branch id!");
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
	public void updateLoan(int loan_id, Loan loan) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();

			Loan new_loan = em.find(Loan.class, loan_id);

			if (new_loan != null) {
				new_loan.setType(loan.getType());
				new_loan.setAmount(loan.getAmount());

				em.merge(new_loan);
				et.commit();
				System.out.println("Loan is updated successfully!!");
				System.out.println("Loan Details: ");
				System.out.println(
						"loan Id: " + loan.getId() + "\nType: " + loan.getType() + "\nAmount: " + loan.getAmount());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("No loan found for the given loan id!!");
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
	public void deleteLoan(int loan_id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Loan loan = em.find(Loan.class, loan_id);
			if (loan != null) {
				em.remove(loan);
				et.commit();
				System.out.println("Loan with id: " + loan_id + " is deleted successfully!!");
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("No loan found for the given loan id!!");
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
	public void getLoanById(int loan_id) {
		EntityManager em = emf.createEntityManager();

		try {
			Loan loan = em.find(Loan.class, loan_id);
			if (loan != null) {
				System.out.println("Loan is fetched successfully!!");
				System.out.println("Loan Details: ");
				System.out.println(
						"loan Id: " + loan.getId() + "\nType: " + loan.getType() + "\nAmount: " + loan.getAmount());
				System.out.println("---------------------------------------------------");
			} else {
				System.err.println("No loan found for the given loan id!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getLoanByBranch(int branch_id) {
		EntityManager em = emf.createEntityManager();
		try {
			Branch branch = em.find(Branch.class, branch_id);
			if (branch != null) {
				List<Loan> loans = branch.getLoan_offers();
				if (loans.size() > 0) {
					for (Loan loan : loans) {
						System.out.println("Loan Details: ");
						System.out.println("loan Id: " + loan.getId() + "\nType: " + loan.getType() + "\nAmount: "
								+ loan.getAmount());
						System.out.println("---------------------------------------------------");
					}
				} else {
					System.err.println("No loans found for the given branch id !!");
				}
			} else {
				System.err.println("No branch found for the given branch id : " + branch_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getAllLoans() {
		EntityManager em = emf.createEntityManager();
		try {
			String jpql = "Select l from Loan l";
			Query query = em.createQuery(jpql);

			List<Loan> loans = query.getResultList();
			if (loans.size() > 0) {
				System.out.println("All Loan Details: ");
				for (Loan loan : loans) {
					System.out.println(
							"loan Id: " + loan.getId() + "\nType: " + loan.getType() + "\nAmount: " + loan.getAmount());
					System.out.println("---------------------------------------------------");
				}
			} else {
				System.err.println("No loans found!!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
}
