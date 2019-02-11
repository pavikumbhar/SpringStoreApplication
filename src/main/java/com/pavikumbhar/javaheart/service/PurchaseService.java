package com.pavikumbhar.javaheart.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.pavikumbhar.javaheart.dao.PersistanceDao;
import com.pavikumbhar.javaheart.model.Customer;
import com.pavikumbhar.javaheart.model.Product;
import com.pavikumbhar.javaheart.model.Purchase;
import com.pavikumbhar.javaheart.model.PurchaseItem;

import lombok.AllArgsConstructor;

/**
 * 
 * @author pavikumbhar
 *
 */
@AllArgsConstructor
@Service
public class PurchaseService {

	private PersistanceDao persistanceDao;
	
	public long countAllPurchases() {
		 return persistanceDao.getSingleColumnValue("SELECT COUNT(o) FROM Purchase o", Long.class);
    }

	public void deletePurchase(Purchase purchase) {
		persistanceDao.delete(purchase);
    }

	public Purchase findPurchase(Long id) {
        return  persistanceDao.read(Purchase.class,id);
    }

	public List<Purchase> findAllPurchases() {
        return persistanceDao.getEntityList("SELECT o FROM Purchase o", Purchase.class);
    }

	public List<Purchase> findPurchaseEntries(int firstResult, int maxResults) {
		//TODO
        return persistanceDao.getEntityList("SELECT o FROM Purchase o", Purchase.class );
    }

	public void savePurchase(Purchase purchase) {
		persistanceDao.save(purchase);
    }

	public Purchase updatePurchase(Purchase purchase) {
        return persistanceDao.update(purchase);
    }

	public void savePurchase(Map<Product, Integer> cartContents, Customer purchasedBy) {
		
		Purchase purchase = new Purchase();
		Calendar now = Calendar.getInstance();
		for (Entry<Product, Integer> entry : cartContents.entrySet()) {
			PurchaseItem purchaseItem = new PurchaseItem();
			purchaseItem.setProduct(entry.getKey());
			purchaseItem.setQuantity(entry.getValue());
			purchaseItem.setOrderDate(now);
			
			purchaseItem.setPurchase(purchase);
			purchase.getPurchaseItems().add(purchaseItem);
		}
		purchase.setOrderDate(now);
		purchase.setOrderedBy(purchasedBy);
		
		this.savePurchase(purchase);
	}
}
