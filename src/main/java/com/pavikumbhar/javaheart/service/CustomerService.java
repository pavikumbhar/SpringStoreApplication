package com.pavikumbhar.javaheart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavikumbhar.javaheart.dao.PersistanceDao;
import com.pavikumbhar.javaheart.model.Customer;

import lombok.AllArgsConstructor;

/**
 * 
 * @author pavikumbhar
 *
 */
@AllArgsConstructor
@Service
public class CustomerService {
	
	private PersistanceDao persistanceDao;
	
	public long countAllCustomers() {
		 return persistanceDao.getSingleColumnValue("SELECT COUNT(o) FROM Customer o", Long.class);
    }

	public void deleteCustomer(Customer customer) {
		persistanceDao.delete(customer);
    }

	public Customer findCustomer(Long id) {
        return persistanceDao.read(Customer.class, id);
    }
	
	public Customer findCustomer(String name) {
        return persistanceDao.getSingleColumnValue("SELECT o FROM Customer AS o WHERE o.name = '%s'", Customer.class, name);
    }
	
	public List<Customer> findAllCustomers() {
        return persistanceDao.getEntityList("SELECT o FROM Customer o", Customer.class );
    }

	public List<Customer> findCustomerEntries(int firstResult, int maxResults) {
		//TODO
        return persistanceDao.getEntityList("SELECT o FROM Customer o", Customer.class );
    }

	public void saveCustomer(Customer customer) {
		persistanceDao.save(customer);
    }

	public Customer updateCustomer(Customer customer) {
        return persistanceDao.update(customer);
    }

	public Customer loginCustomer(String userId, String password) {
		Customer customer = this.findCustomer(userId);
		if (customer != null && customer.getPassword().equals(password)) {
			return customer;
		}
		return null;
	}
}
