package repositories;

import java.util.List;

import Entity.Cart;
import Entity.User;
import IRepositories.IUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import utils.JPAUtil;
import utils.UserUtil;

public class UserRepository implements IUserRepository {
	private final EntityManager em;
	User user;// logged in user

	public UserRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public boolean authentication(User user) {
		try {

			Query query = em.createQuery("Select u from User u where u.name=:name and u.email=:email");
			query.setParameter("name", user.getName());
			query.setParameter("email", user.getEmail());
			User existing_user = (User) query.getSingleResult();

			this.user = existing_user;

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User loggedInUser() {
		return this.user;
	}

	@Override
	public User getUserById(int id) {
		try {
			User user = em.find(User.class, id);

			if (user != null) {
				System.out.println("User Details: ");
				System.out.println(user);
			}
			return user;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = null;
		try {
			Query query = em.createQuery("Select u from User u");
			users = query.getResultList();

			if (!users.isEmpty()) {
				System.out.println("All Users details: ");
				for (User user : users) {
					System.out.println(user);
					System.out.println("---------------------------------");
				}
			} else {
				System.err.println("No Active Users!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void addUser(User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();

			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);

			em.persist(cart);
			em.persist(user);

			if (user.getRole().equals("user"))
				System.out.println("User is registered successfully!");
			else
				System.out.println("Admin is added successfully!");

			et.commit();
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}

	}

	@Override
	public void updateUser(User user, User new_user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();

			user.setName(new_user.getName());
			user.setEmail(new_user.getEmail());

			em.merge(user);
			System.out.println("User updated successfully!!!");

			et.commit();
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}

	}

	@Override
	public void deleteUser(User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			user = em.merge(user);// re attaching user to entity manage
			em.remove(user);

			System.out.println("Account is deleted successfully");
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}
		UserUtil.userAction();
	}

	@Override
	public User getAdmin() {
		try {
			Query query = em.createQuery("Select u from User u where u.role='admin'");
			User user = (User) query.getSingleResult();

			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
