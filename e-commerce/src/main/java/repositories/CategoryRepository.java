package repositories;

import java.util.List;

import Entity.ProductCategory;
import IRepositories.ICategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

public class CategoryRepository implements ICategoryRepository {
	private final EntityManager em;
	
	public CategoryRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public ProductCategory getProductCategory(int id) {
		ProductCategory category = null;
		try {
			category = em.find(ProductCategory.class, id);
			if (category != null) {
				System.out.println("Product Category details: \n" + category);
			} else {
				System.err.println("No product category found for the given id!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public List<ProductCategory> getAllProductCategories() {
		List<ProductCategory> categories = null;
		try {
			categories = em.createQuery("from ProductCategory p").getResultList();

			if (!categories.isEmpty()) {
				System.out.println("All product categories: ");
				for (ProductCategory category : categories) {
					System.out.println(category);
					System.out.println("-------------------------------------------");
				}
			} else {
				System.err.println("No product categories found!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addCategory(ProductCategory category) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(category);
			System.out.println("Product Category added successfully!!!");
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void updateCategory(int id, ProductCategory category) {
		EntityTransaction et = em.getTransaction();
		try {
			ProductCategory existing_category = em.find(ProductCategory.class, id);

			if (existing_category != null) {
				existing_category.setName(category.getName());

				et.begin();
				em.merge(existing_category);
				System.out.println("Product Category with id: " + id + " is updated successfully!");
				et.commit();
			} else {
				System.err.println("No product category found for given id!");
			}
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}

	}

	@Override
	public void deleteCategory(int id) {
		EntityTransaction et = em.getTransaction();
		try {
			ProductCategory existing_category = em.find(ProductCategory.class, id);

			if (existing_category != null) {

				et.begin();
				em.remove(existing_category);
				System.out.println("Product Category with id: " + id + " is deleted successfully!");
				et.commit();
			} else {
				System.err.println("No product category found for given id!");
			}
		} catch (Exception e) {
			if (et.isActive())
				et.rollback();
			e.printStackTrace();
		}

	}

}
