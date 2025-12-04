package repositories;

import java.util.List;

import Entity.Cart;
import Entity.Product;
import Entity.User;
import Entity.joinentities.CartItem;
import IRepositories.ICartRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import utils.JPAUtil;

public class CartRepository implements ICartRepository {
	private final EntityManager em;
	

	public CartRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public void addProduct(int product_id, int quantity, User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Cart cart = user.getCart();

			CartItem item = new CartItem();

			Product product = em.find(Product.class, product_id);

			if (quantity <= product.getTotalQuantity()) {
				item.setQuantity(item.getQuantity() + quantity);
			} else {
				System.err.println(
						"Cannot add " + quantity + " items \n Available quantity: " + product.getTotalQuantity());
			}
			item.setCart(cart);
			item.setProduct(product);

			em.persist(item);

			cart.getItems().add(item);

			System.out.println(quantity + " products added successfully!!");

			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void viewCart(User user) {
		try {
			Cart cart = user.getCart();
			List<CartItem> items = cart.getItems();
			if (!items.isEmpty()) {
				System.out.println(user.getName() + "'s Products List: ");
				for (CartItem item : items) {
					System.out.println(item);
					System.out.println("-----------------------------------------");
				}
			} else {
				System.err.println("Your cart is empty!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeProduct(int product_id, int quantity, User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Cart cart = user.getCart();

			Query query = em.createQuery("Select i from CartItem i where i.product.p_id=:product_id");
			query.setParameter("product_id", product_id);
			CartItem item = (CartItem) query.getSingleResult();

			Product product = item.getProduct();

			if (quantity <= item.getQuantity()) {
				item.setQuantity(item.getQuantity() - quantity);
				product.setTotalQuantity(product.getTotalQuantity() + quantity);
			} else {
				System.err.println("Enter the valid quantity to remove!!!");
			}

			if (item.getQuantity() == 0) {
				cart.getItems().remove(item);
				em.remove(item);
			} else {
				em.merge(item);
			}
			em.merge(product);
			em.merge(cart);

			if (item.getQuantity() == 0) {
				System.out.println(product.getName() + " is removed from your cart!!");
			} else {
				System.out.println(
						item.getQuantity() + " units of " + product.getName() + " are removed from your cart!!!");
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
