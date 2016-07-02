package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.DataType;
import org.hibernate.engine.spi.SessionImplementor;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.domain.repository.CategoryRep;
import com.xiaotuitui.testframework.DBUnitUtil;
import com.xiaotuitui.testframework.EntityUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryRepImplTest {
	
	@Autowired
	private CategoryRep categoryRep;
	
	private static IDataSet dataSet;
	
	private static String table;
	
	private static Column[] additionalColumnInfo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@BeforeClass
    public static void setUpBeforeClass() throws DatabaseUnitException, IOException, SQLException, ClassNotFoundException{
		DBUnitUtil.registerDBUnitConnection();
		dataSet = DBUnitUtil.getDbunitDataSetByXmlFileName("dbunit/category.xml");
		table = dataSet.getTableNames()[0];
		additionalColumnInfo = buildAdditionalColumnInfo();
    }
    
    @AfterClass
    public static void tesrDownAfterClass(){
    	DBUnitUtil.closeConnection();
    }
    
	@Test
	public void testQueryAllCategory() throws Exception{
		initData();
		List<Category> categories = categoryRep.queryAllCategory();
		ITable expectedTable = dataSet.getTable(table);
		ITable actualTable = DBUnitUtil.getDbunitDataSetByEntityList(categories, table).getTable(table);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	@Test
	public void testQueryCategoryByName() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, DatabaseUnitException, SQLException{
		initData();
		Category category = categoryRep.queryCategoryByName("name1");
		Category expectedCategory = (Category) EntityUtil.createEntity(Category.class, 1);
		Assert.assertEquals(expectedCategory.getName(), category.getName());
		Assert.assertEquals(expectedCategory.getDescription(), category.getDescription());
	}
	
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testCreateCategory() throws Exception{
		deleteData();
		Category category = (Category) EntityUtil.createEntity(Category.class, 1);
		category.setId(null);
		categoryRep.createCategory(category);
		ITable expectedTable = DBUnitUtil.getDbunitDataSetByEntity(category, table).getTable(table);
		ITable actualTable = DBUnitUtil.getDBUnitConnection(getUnwarpConnection()).createDataSet().getTable(table);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	@Test
	public void testLoadCategory() throws DatabaseUnitException, SQLException{
		initData();
		Category expectedCategory = (Category) EntityUtil.createEntity(Category.class, 1);
		Integer categoryId = ((Category) entityManager.createQuery("from Category c where c.name = :name").setParameter("name", expectedCategory.getName()).getSingleResult()).getId();
		Category actualCategory = categoryRep.loadCategory(categoryId);
		Assert.assertEquals(expectedCategory.getName(), actualCategory.getName());
		Assert.assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
	}
	
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testRemoveCategory() throws Exception{
		initData();
		Category category = (Category) entityManager.createQuery("from Category c where c.name = :name").setParameter("name", "name4").getSingleResult();
		categoryRep.removeCategory(category);
		entityManager.flush();
		ITable expectedTable = DBUnitUtil.getDbunitDataSetByEntityList(EntityUtil.createEntityList(Category.class, 3), table).getTable(table);
		ITable actualTable = DBUnitUtil.getDBUnitConnection(getUnwarpConnection()).createDataSet().getTable(table);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	private static ITable includeColumn(ITable table) throws DataSetException{
		return DBUnitUtil.includeColumnTable(table, additionalColumnInfo);
	}
	
	private static Column[] buildAdditionalColumnInfo(){
		Column name = new Column("name", DataType.NVARCHAR);
		Column description = new Column("description", DataType.NVARCHAR);
		return new Column[]{name, description};
	}
	
	private Connection getUnwarpConnection(){
		return ((SessionImplementor) entityManager.unwrap(SessionImplementor.class)).connection();
	}
	
	private void deleteData(){
        DBUnitUtil.deleteAll(dataSet);
    }
	
	private void initData() throws DatabaseUnitException, SQLException{
		DBUnitUtil.cleanInsert(dataSet);
    }

}