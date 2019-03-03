package com.pavikumbhar.javaheart;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pavikumbhar.javaheart.dao.NativeResultConstructor;
import com.pavikumbhar.javaheart.dao.PersistanceDao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author pavikumbhar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringStoreApplication.class)
@Slf4j
public class JpaResultMapperTest {

	@Autowired
	private PersistanceDao persistanceDao;

	@Test
	public void testJpaResultMapper() throws FileNotFoundException, SQLException {

		List<CategoryDTO> categoryList = persistanceDao.getNativeResultListUsingMapper("SELECT ID, NAME FROM Category WHERE ID = 1 ", 1, CategoryDTO.class);

		for (CategoryDTO category : categoryList) {
			log.debug("id : {} ", category.getId());
			log.debug("name : {} ", category.getName());
			log.debug("desc : {} ", category.getDescription());
		}

		log.debug("#########################################################");

		List<CategoryDTO> descriptionList = persistanceDao.getNativeResultListUsingMapper("SELECT  description,ID  FROM Category WHERE ID = 1 ", 2, CategoryDTO.class);

		for (CategoryDTO category : descriptionList) {
			log.debug("id : {} ", category.getId());
			log.debug("name : {} ", category.getName());
			log.debug("desc : {} ", category.getDescription());
		}
		
		List<BigDecimal> BigDecimal = persistanceDao.getNativeSingleColumnValueList("SELECT count(*)  FROM Category  ",  BigDecimal.class );

		
			log.debug("count : {} ", BigDecimal);
			
			List<Long> digDecimal = persistanceDao.getSingleColumnValueList("SELECT count(*)  FROM Category  ",  Long.class );

			
			log.debug("digDecimal : {} ", digDecimal);
		
				
			
	}

	@Getter
	@Setter
	public static class CategoryDTO {
		private BigDecimal id;
		private String name;
		private String description;

		@NativeResultConstructor(index = 1)
		public CategoryDTO(BigDecimal id, String name) {
			this.id = id;
			this.name = name;
		}

		@NativeResultConstructor(index = 2)
		public CategoryDTO(String description, BigDecimal id) {
			this.id = id;
			this.description = description;
		}

		public CategoryDTO(BigDecimal id) {
			this.id = id;
		}

		public CategoryDTO(String name) {
			this.name = name;
		}
	}

}
