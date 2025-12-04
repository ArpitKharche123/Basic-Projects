package IRepositories;

import java.util.List;

import Entity.Order;
import Entity.OrderTracking;
import Entity.User;

public interface IOrderRepository {
	void placeOrder(int a_id,String payment_method,Order order, User user);

	Order getById(int order_num);

	List<Order> getAllOrder();

	List<Order> getOrdersByUserId(int user_id);

	OrderTracking getTrackingDetails(int order_num);

	OrderTracking getTrackingDetails(int order_num, User user);

	void cancelOrder(int order_num, User user);

	void deleteOrder(int order_num);

}
