package Service;

import java.util.List;

import DAO.BankDAO;
import DTO.Bank;
import DTO.Branch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class BankService implements BankDAO {

	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bms");

	@Override
	public void addBank(Bank bank) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.persist(bank);
			et.commit();
			System.out.println("Bank is added successfully!!");
			System.out.println("Bank Details: ");
			System.out.println("Id: " + bank.getId() + "\nName: " + bank.getName() + "\nCode: " + bank.getCode()
					+ " \nType: " + bank.getType());
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
	public void updateBank(int bank_id, Bank bank) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Bank new_bank = em.find(Bank.class, bank_id);

		new_bank.setName(bank.getName());
		new_bank.setCode(bank.getCode());
		new_bank.setType(bank.getType());

		try {
			et.begin();
			em.merge(new_bank);
			et.commit();
			System.out.println("Bank is updated successfully!!");
			System.out.println("Bank Details: ");
			System.out.println("Id: " + bank_id + "\nName: " + bank.getName() + "\nCode: " + bank.getCode()
					+ " \nType: " + bank.getType());
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
	public void deleteBank(int bankk_id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Bank bank = em.find(Bank.class, bankk_id);

		try {
			et.begin();
			em.remove(bank);
			et.commit();
			System.out.println(bank.getName() + " bank is deleted successfully!!!");
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
	public void getBankById(int bank_id) {
		EntityManager em = emf.createEntityManager();
		try {
			Bank bank = em.find(Bank.class, bank_id);
			System.out.println("Bank Details: ");
			System.out.println("Id: " + bank.getId() + "\nName: " + bank.getName() + "\nCode: " + bank.getCode()
					+ " \nType: " + bank.getType());
			System.out.println("---------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

	@Override
	public void getBankByBranch(int branch_id) {
		EntityManager em = emf.createEntityManager();
		try {
			Branch branch = em.find(Branch.class, branch_id);
			if (branch != null) {
				Bank bank = branch.getBank();
				if (bank != null) {
					System.out.println("Bank Details: ");
					System.out.println("Id: " + bank.getId() + "\nName: " + bank.getName() + "\nCode: " + bank.getCode()
							+ " \nType: " + bank.getType());
					System.out.println("---------------------------------------------------");
				} else {
					System.err.println("No bank found for the given branch!!");
				}
			} else {
				System.err.println("No branch found for the given id: " + branch_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void getAllBanks() {
		EntityManager em = emf.createEntityManager();
		try {
			String jpql = "Select b from Bank b";
			Query query = em.createQuery(jpql);

			List<Bank> banks = query.getResultList();
			System.out.println("All Banks Details: ");
			for (Bank bank : banks) {
				System.out.println("\nId: " + bank.getId() + "\nName: " + bank.getName() + "\nCode: " + bank.getCode()
						+ " \nType: " + bank.getType());
				System.out.println("---------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

}
