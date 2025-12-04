package repositories;

import java.util.List;

import Entity.Payment;
import Entity.User;
import IRepositories.IPaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

public class PaymentRepository implements IPaymentRepository {
	private final EntityManager em;
	

	public PaymentRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Payment getById(int id) {
		Payment payment = null;
		try {
			payment = em.find(Payment.class, id);
			if (payment != null) {
				System.out.println("Payment Details: ");
				System.out.println(payment);
			} else {
				System.err.println("Payment info not available!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payment;
	}

	@Override
	public List<Payment> getPaymentsByUserId(int user_id) {
		List<Payment> payments = null;
		try {
			User user = em.find(User.class, user_id);
			if (user != null) {
				payments = user.getPayments();
				if (!payments.isEmpty()) {
					System.out.println("Payments done by " + user.getName());
					for (Payment payment : payments) {
						System.out.println(payment);
						System.out.println("-----------------------------------------");
					}
				} else {
					System.err.println("No payments done by the given user!!!");
				}
			} else {
				System.err.println("User not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payments;
	}

	@Override
	public List<Payment> getAllPayments() {
		List<Payment> payments = null;
		try {
			payments = em.createQuery("from Payment p").getResultList();
			if (!payments.isEmpty()) {
				System.out.println("All Payments Details: ");
				for (Payment payment : payments) {
					System.out.println(payment);
					System.out.println("-----------------------------------------");
				}
			} else {
				System.err.println("No payments found!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return payments;
	}

	@Override
	public void deletePayment(int id) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Payment payment = em.find(Payment.class, id);
			if (payment != null && payment.getStatus().equalsIgnoreCase("refunded")) {
				em.remove(payment);
				System.out.println("Payment Record removed successfully!!");
			} else {
				System.err.println("Payment not found!!!");
			}
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}

	}

}
