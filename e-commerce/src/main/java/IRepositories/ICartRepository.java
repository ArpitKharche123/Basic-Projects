package IRepositories;

import Entity.User;

public interface ICartRepository {
	// CartItem addCartItem(Cart cart,int product_id);
	void addProduct(int product_id, int quantity, User user);

	void viewCart(User user);

	void removeProduct(int product_id,int quantity,User user);

}
