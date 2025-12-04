package repositories;

import java.util.List;

import Entity.Address;
import Entity.User;
import IRepositories.IAddressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import utils.JPAUtil;

public class AddressRepository implements IAddressRepository {

	private final EntityManager em;

	public AddressRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Address getAddressById(int a_id) {
		return null;
	}

	@Override
	public Address getUserAddress(User user, int a_id) {
		Address address = null;
		try {
			address = (Address) em.createQuery("from Address a where a.a_id=:a_id and a.user.u_id=:user_id")
					.setParameter("a_id", a_id).setParameter("user_id", user.getU_id()).getSingleResult();
			if (address != null) {
				System.out.println("User Address Details:");
				System.out.println(address);
			} else {
				System.err.println("Address not found for the given user");
			}
		} catch (NoResultException e) {
			System.err.println(" Address not found for the given user and address ID!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public List<Address> getUserAddresses(User user) {
		List<Address> addresses = null;
		try {
			user = em.merge(user);
			addresses = user.getAddresses();
			if (!addresses.isEmpty()) {
				System.out.println(user.getName() + " 's addresses: ");
				for (Address address : addresses) {
					System.out.println(address);
					System.out.println("---------------------------------------");
				}
			} else {
				System.err.println("No addresses found for the logged in user!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addresses;
	}

	@Override
	public void addUserAddress(Address address, User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();

			user = em.merge(user); // attach user

			boolean alreadyExists = user.getAddresses().stream()
					.anyMatch(a -> a.getHouse_number() == address.getHouse_number()
							&& a.getArea().equalsIgnoreCase(address.getArea())
							&& a.getCity().equalsIgnoreCase(address.getCity()) && a.getPincode() == address.getPincode()
							&& a.getState().equalsIgnoreCase(address.getState())
							&& a.getCountry().equalsIgnoreCase(address.getCountry()));

			if (!alreadyExists) {
				address.setUser(user);
				user.getAddresses().add(address);
				em.persist(address);
				System.out.println("Address is added successfully for user: " + user.getName());
			} else {
				System.err.println("Address already exists!!");
			}

			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserAddress(int a_id, Address address, User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Address existing_address = em.find(Address.class, a_id);
			if (existing_address != null) {
				user = em.merge(user);
				if (user.getAddresses().contains(existing_address)) {
					existing_address.setHouse_number(address.getHouse_number());
					existing_address.setArea(address.getArea());
					existing_address.setCity(address.getCity());
					existing_address.setPincode(address.getPincode());
					existing_address.setState(address.getState());
					existing_address.setCountry(address.getCountry());

					// em.merge(existing_address); //it is managed by em(due to em.find(), so it
					// will automatically get updated!!)
					System.out.println("Address with id: " + a_id + " is updated successfully!!");
				} else {
					System.err.println("Address does not belongs to logged in user!!!");
				}
			} else {
				System.err.println("Address does not exists!!!");
			}

			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUserAddress(int a_id, User user) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Address existing_address = em.find(Address.class, a_id);
			if (existing_address != null) {
				if (user.getAddresses().contains(existing_address)) {

					user.getAddresses().remove(existing_address);
					em.remove(existing_address);

					System.out.println("Address with id: " + a_id + " is deleted successfully!!");
				} else {
					System.err.println("Address does not belongs to logged in user!!!");
				}
			} else {
				System.err.println("Address does not exists!!");
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
