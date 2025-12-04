package IRepositories;

import java.util.List;

import Entity.Address;
import Entity.User;

public interface IAddressRepository {
	
	Address getAddressById(int a_id);

	Address getUserAddress(User user, int a_id);

	List<Address> getUserAddresses(User user);

	void addUserAddress(Address address, User user);

	void updateUserAddress(int a_id,Address address, User user);

	void deleteUserAddress(int a_id,User user);

}
