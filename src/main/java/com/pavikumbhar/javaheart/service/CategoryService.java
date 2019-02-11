package com.pavikumbhar.javaheart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavikumbhar.javaheart.dao.PersistanceDao;
import com.pavikumbhar.javaheart.model.Category;

import lombok.AllArgsConstructor;

/**
 * 
 * @author pavikumbhar
 *
 */
@AllArgsConstructor
@Service
public class CategoryService {

	private PersistanceDao persistanceDao;

	public List<Category> getAllCategories() {
		return persistanceDao.getEntityList("SELECT o FROM Category o", Category.class);
	}

	public void deleteCategory(Category category) {
		persistanceDao.delete(category);
	}

	public Category findCategory(Long id) {
		return persistanceDao.read(Category.class, id);
	}

	public Category findCategoryEagerly(Long id) {
		return persistanceDao
				.getEntityList("SELECT o FROM Category o LEFT OUTER JOIN FETCH o.products WHERE o.id = '%s'",
						Category.class, id)
				.stream().findFirst().orElse(null);
	}

	public List<Category> findCategoryEntries(int firstResult, int maxResults) {
		// TODO
		return persistanceDao.getEntityList("SELECT o FROM Category o", Category.class);
	}

	public void saveCategory(Category category) {
		persistanceDao.save(category);
	}

	public Category updateCategory(Category category) {
		return persistanceDao.update(category);
	}

}
