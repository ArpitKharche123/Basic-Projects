package IRepositories;

import java.util.List;

import Entity.Payment;

public interface IPaymentRepository {
	Payment getById(int id);

	List<Payment>  getPaymentsByUserId(int user_id);

	List<Payment> getAllPayments();

	void deletePayment(int id);
}
