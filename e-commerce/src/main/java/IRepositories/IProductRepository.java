package IRepositories;

import java.util.List;

import Entity.Product;
import Entity.ProductCategory;

public interface IProductRepository {
	Product getProductById(int id);
	List<Product> getAllProducts();
	List<Product> getAllProductsByCategory(int c_id);
	void addProduct(Product product);
	void updateProduct(int id,Product product);
	void setCategoryToProduct(int id,int c_id);
	void setOutOfStock(int product_id);
	void deleteProduct(int id);
	
}
