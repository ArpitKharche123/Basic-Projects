package repositories;

import java.util.List;

import Entity.Product;
import Entity.ProductCategory;
import IRepositories.IProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import utils.JPAUtil;

public class ProductRepository implements IProductRepository {
	private final EntityManager em;
	

	public ProductRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Product getProductById(int id) {
		Product product = null;
		try {
			product = em.find(Product.class, id);

			if (product != null) {
				System.out.println("Product Details: \n" + product);
			} else {
				System.err.println("No product found for the given product id!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = null;
		try {
			products = em.createQuery("from Product p").getResultList();

			if (!products.isEmpty()) {
				System.out.println("All Products: ");
				for (Product product : products) {
					System.out.println(product);
					System.out.println("----------------------------------");
				}
			} else {
				System.err.println("No products found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public List<Product> getAllProductsByCategory(int c_id) {
		List<Product> products = null;
		try {
			Query query = em.createQuery("from Product p where p.category.c_id = :category_id");

			query.setParameter("category_id", c_id);

			products = query.getResultList();

			ProductCategory category = em.find(ProductCategory.class, c_id);
			if (category != null) {
				if (!products.isEmpty()) {
					System.out.println("Product Category :" + category.getName());
					System.out.println("Products : ");
					for (Product product : products) {
						System.out.println(product);
						System.out.println("----------------------------------");
					}
				} else {
					System.err.println("No products found!");
				}
			} else {
				System.err.println("Category does'nt exists for the given id!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void addProduct(Product product) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(product);
			System.out.println("Product added successfully!!");
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(int id, Product product) {
		EntityTransaction et = em.getTransaction();
		try {
			Product existing_product = em.find(Product.class, id);

			if (existing_product != null) {
				existing_product.setName(product.getName());
				existing_product.setPrice(product.getPrice());
				existing_product.setDescription(product.getDescription());

				et.begin();
				em.merge(existing_product);
				System.out.println("Product with id: " + id + " is updated successfully!");
				et.commit();
			} else {
				System.err.println("No product found for given id!");
			}
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void setCategoryToProduct(int id, int c_id) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			ProductCategory category = em.find(ProductCategory.class, c_id);
			if (category != null) {
				Product product = em.find(Product.class, id);
				if (product != null) {
					if (!category.getProducts().contains(product)) {
						product.setCategory(category);
						category.getProducts().add(product);
					}
					em.merge(product);
					System.out.println("\nProduct is added to category!!\n");
					et.commit();
				} else {
					System.err.println("Product not found!!!");
				}
			} else {
				System.err.println("Category not found!!");
			}
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void setOutOfStock(int product_id) {
		EntityTransaction et = em.getTransaction();
		try {
			Product existing_product = em.find(Product.class, product_id);

			if (existing_product != null) {
				existing_product.setAvailable(false);
				existing_product.setTotalQuantity(0);
				et.begin();
				em.merge(existing_product);
				System.out.println("Product with id: " + product_id + " is set to out of stock!");
				et.commit();
			} else {
				System.err.println("No product found for given id!");
			}
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(int id) {
		EntityTransaction et = em.getTransaction();
		try {
			Product existing_product = em.find(Product.class, id);

			if (existing_product != null) {

				et.begin();
				em.remove(existing_product);
				System.out.println("Product with id: " + id + " is deleted successfully!");
				et.commit();
			} else {
				System.err.println("No product found for given id!");
			}
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}
	}

}
