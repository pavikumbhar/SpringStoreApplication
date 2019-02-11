package com.pavikumbhar.javaheart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavikumbhar.javaheart.dao.PersistanceDao;
import com.pavikumbhar.javaheart.model.Product;

import lombok.AllArgsConstructor;

/**
 * 
 * @author pavikumbhar
 *
 */
@AllArgsConstructor
@Service
public class ProductService {

	private PersistanceDao persistanceDao;

	public void saveProduct(Product product) {
		persistanceDao.save(product);
	}

	public Product updateProduct(Product product) {
		return persistanceDao.update(product);
	}

	public void deleteProduct(Product product) {
		persistanceDao.delete(product);
	}

	public List<Product> getFeaturedProducts() {
		return persistanceDao.getEntityList("SELECT o FROM Product AS o WHERE o.featured =%s", Product.class, true);
	}

	public List<Product> findAllProducts() {
		return persistanceDao.getEntityList("SELECT o FROM Product o", Product.class);

	}

	public Product findProduct(Long id) {
		if (id == null)
			return null;
		return persistanceDao.read(Product.class, id);
	}

	public List<Product> findProductEntries(Product product) {
		if (product.getCategory() != null && product.getCategory().getId() != 0) {
			return persistanceDao.getEntityList("SELECT o FROM Product o WHERE o.name like '%s' and o.category = '%s'",
					Product.class, product.getName(), product.getCategory());
		} else {
			return persistanceDao.getEntityList("SELECT o FROM Product o WHERE o.name like '%s'", Product.class,
					product.getName());
		}

	}

	public List<Product> findProducts(Product product) {
		return findProductEntries(product);
	}

}
