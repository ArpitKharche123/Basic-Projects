package IRepositories;

import java.util.List;

import Entity.ProductCategory;

public interface ICategoryRepository {
	ProductCategory getProductCategory(int id);

	List<ProductCategory> getAllProductCategories();

	void addCategory(ProductCategory category);

	void updateCategory(int id, ProductCategory category);

	void deleteCategory(int id);

}
