package repositories;

import java.util.List;

import Entity.Address;
import Entity.Order;
import Entity.OrderTracking;
import Entity.Payment;
import Entity.Product;
import Entity.User;
import Entity.joinentities.CartItem;
import Entity.joinentities.OrderItem;
import IRepositories.IOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class OrderRepository implements IOrderRepository {
	private final EntityManager em;

	public OrderRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public void placeOrder(int a_id, String payment_method, Order order, User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();

			user = em.merge(user);// re-attaching user to em

			Address address = new AddressRepository(em).getUserAddress(user, a_id);

			if (user.getAddresses().contains(address)) {

				order.setUser(user);
				double orderAmount = 0;
				List<CartItem> items = user.getCart().getItems();
				if (!items.isEmpty()) {
					for (CartItem cartItem : items) {

						Product product = cartItem.getProduct();

						OrderItem orderItem = new OrderItem();
						orderItem.setOrder(order);
						orderItem.setProduct(product);
						orderItem.setOrdered_quantity(cartItem.getQuantity());

						order.getOrderItems().add(orderItem);

						orderAmount += product.getPrice() * cartItem.getQuantity();
						product.setTotalQuantity(product.getTotalQuantity() - cartItem.getQuantity());

						em.merge(product);
					}
					// Order and Order Tracking
					order.setOrder_amount(orderAmount);
					order.setStatus("ordered");

					OrderTracking tracking = new OrderTracking();
					tracking.setOrder(order);
					tracking.setStatus(order.getStatus());
					order.setOrderTracking(tracking);

					em.persist(tracking);

					user.getOrders().add(order);

					// Payment
					Payment payment = new Payment();
					payment.setAmount(orderAmount);
					payment.setMethod(payment_method);
					payment.setStatus("success");
					payment.setUser(user);
					payment.setOrder(order);

					user.getPayments().add(payment);

					em.persist(payment);

					System.out.println("Order placed successfully! \nDetails: \n" + order + "Payment info: \n" + payment
							+ "\nShipping Address: " + address);

					em.persist(order);
					// em.merge(user); //cascade used in Order and Payment

					user.getCart().getItems().clear();// emptying the cart
					et.commit();

				} else {
					System.err.println("User cart is empty, add products to place order!!");
				}
			} else {
				System.err.println("Invalid Address, enter the correct address to place order");
			}

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public Order getById(int order_num) {
		Order order = null;
		try {
			order = em.find(Order.class, order_num);
			if (order != null)
				System.out.println("Order details: \n" + order);
			else {
				System.err.println("Order not found!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> getAllOrder() {
		List<Order> orders = null;
		try {
			orders = em.createQuery("from Order o").getResultList();
			if (!orders.isEmpty()) {
				System.out.println("All Orders Details: ");
				for (Order order : orders) {
					System.out.println(order);
					System.out.println("----------------------------");
				}
			} else {
				System.err.println("No orders found!!");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return orders;
	}

	@Override
	public List<Order> getOrdersByUserId(int user_id) {
		List<Order> orders = null;
		try {
			Query query = em.createQuery("from Order o where o.user.u_id  =:user_id");
			query.setParameter("user_id", user_id);
			orders = query.getResultList();
			if (!orders.isEmpty()) {
				System.out.println("All orders placed by User");
				for (Order order : orders) {
					System.out.println(order);
					System.out.println("----------------------------");
				}
			} else {
				System.err.println("No orders found!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public OrderTracking getTrackingDetails(int order_num) {
		OrderTracking orderTracking = null;
		try {
			Order order = em.find(Order.class, order_num);
			if (order != null) {
				orderTracking = order.getOrderTracking();
				if (orderTracking != null) {
					System.out.println("Order tracking Details: ");
					System.out.println(orderTracking);
				} else {
					System.err.println("Tracking details does'nt exists!!");
				}
			} else {
				System.err.println("Order does'nt exists!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderTracking;
	}

	@Override
	public OrderTracking getTrackingDetails(int order_num, User user) {
		OrderTracking orderTracking = null;
		try {
			Order order = em.find(Order.class, order_num);
			if (order != null) {
				if (user.getOrders().contains(order)) {
					orderTracking = order.getOrderTracking();
					if (orderTracking != null) {
						System.out.println("Order tracking Details: ");
						System.out.println(orderTracking);
					} else {
						System.err.println("Tracking details does'nt exists!!");
					}
				} else {
					System.err.println("Order does not belongs to logged in user!!");
				}

			} else {
				System.err.println("Order does'nt exists!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderTracking;
	}

	@Override
	public void cancelOrder(int order_num, User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Order order = em.find(Order.class, order_num);
			if (order != null) {
				if (user.getOrders().contains(order)) {
					if (order.getStatus().equals("cancelled")) {
						System.err.println("Order is already cancelled!!!");
					} else {
						order.setStatus("cancelled");
						OrderTracking orderTracking = order.getOrderTracking();
						orderTracking.setStatus(order.getStatus());

						// increase total product quantity by ordered quantity!!!!
						List<OrderItem> orderItems = order.getOrderItems();
						for (OrderItem item : orderItems) {
							Product product = item.getProduct();
							product.setTotalQuantity(product.getTotalQuantity() + item.getOrdered_quantity());
							em.merge(product);
						}

						em.merge(order);
						System.out.println("*****Order cancelled successfully!!*****");

						// Payment refunding

						List<Payment> userPayments = user.getPayments();
						Query query = em.createQuery(
								"Select p from Payment p where p.user.u_id=:user_id and p.order.order_num=:order_num");
						query.setParameter("user_id", user.getU_id());
						query.setParameter("order_num", order_num);

						Payment payment = (Payment) query.getSingleResult();
						if (payment != null) {
							if (user.getPayments().contains(payment)) {
								payment.setStatus("refunded");
								em.merge(payment);
								System.out.println("*****Payment refunded!*****");
							} else {
								System.err.println("Payment record does not belongs to logged in user!!");
							}
						} else {
							System.err.println("Payment record does not exists for the given order!!");
						}
					}
				} else {
					System.err.println("Order does not belongs to logged in user");
				}
			} else {
				System.err.println("Order not found!!!");
			}

			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.commit();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrder(int order_num) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Order order = em.find(Order.class, order_num);
			if (order != null) {
				if (order.getStatus().equals("cancelled")) {

					OrderTracking orderTracking = order.getOrderTracking();

					em.remove(orderTracking);
					em.remove(order);

					System.out.println("Order is removed successfully!!");
				} else {
					System.out.println("Order cannot be removed as status is not cancelled!!");
				}
			} else {
				System.err.println("Order does'nt exists!!");
			}
			et.commit();
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}
	}

}
