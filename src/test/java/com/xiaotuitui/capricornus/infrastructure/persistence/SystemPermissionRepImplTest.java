package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.io.IOException;
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
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaotuitui.capricornus.domain.model.SystemPermission;
import com.xiaotuitui.capricornus.domain.repository.SystemPermissionRep;
import com.xiaotuitui.testframework.DBUnitUtil;
import com.xiaotuitui.testframework.EntityUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemPermissionRepImplTest {
	
	@Autowired
	private SystemPermissionRep systemPermissionRep;
	
	private static IDataSet systemPermissionDataSet;
	
	private static IDataSet systemPermissionGroupDataSet;
	
	private static String systemPermissionTableName;
	
	private static Column[] additionalColumnInfo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@BeforeClass
    public static void setUpBeforeClass() throws DatabaseUnitException, IOException, SQLException, ClassNotFoundException{
		DBUnitUtil.registerDBUnitConnection();
		systemPermissionDataSet = DBUnitUtil.getDbunitDataSetByXmlFileName("dbunit/system_permission.xml");
		systemPermissionGroupDataSet = DBUnitUtil.getDbunitDataSetByXmlFileName("dbunit/system_permission_group.xml");
		systemPermissionTableName = systemPermissionDataSet.getTableNames()[0];
		additionalColumnInfo = buildAdditionalColumnInfo();
    }
    
    @AfterClass
    public static void tesrDownAfterClass(){
    	DBUnitUtil.closeConnection();
    }
	
	@Test
	public void testQueryAllSystemPermission() throws Exception{
		initSystemPermissionDataSetData();
		List<SystemPermission> systemPermissionList = systemPermissionRep.queryAllSystemPermission();
		ITable expectedTable = systemPermissionDataSet.getTable(systemPermissionTableName);
		ITable actualTable = DBUnitUtil.getDbunitDataSetByEntityList(systemPermissionList, systemPermissionTableName).getTable(systemPermissionTableName);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	@Test
	public void testLoadSystemPermission() throws DatabaseUnitException, SQLException{
		initSystemPermissionDataSetData();
		SystemPermission systemPermission = (SystemPermission) EntityUtil.createEntity(SystemPermission.class, 1);
		Integer id = ((SystemPermission) entityManager.createQuery("from SystemPermission sp where sp.permissionKey = :permissionKey")
				.setParameter("permissionKey", systemPermission.getPermissionKey()).getSingleResult()).getId();
		SystemPermission actualSystemPermission = systemPermissionRep.loadSystemPermission(id);
		Assert.assertEquals(systemPermission.getPermissionKey(), actualSystemPermission.getPermissionKey());
		Assert.assertEquals(systemPermission.getName(), actualSystemPermission.getName());
		Assert.assertEquals(systemPermission.getDescription(), actualSystemPermission.getDescription());
	}
	
	private static ITable includeColumn(ITable table) throws DataSetException{
		return DBUnitUtil.includeColumnTable(table, additionalColumnInfo);
	}
	
	private static Column[] buildAdditionalColumnInfo(){
		Column permissionKey = new Column("permission_key", DataType.VARCHAR);
		Column name = new Column("name", DataType.NVARCHAR);
		Column description = new Column("description", DataType.NVARCHAR);
		return new Column[]{permissionKey, name, description};
	}
	
	private void initSystemPermissionDataSetData() throws DatabaseUnitException, SQLException{
		DBUnitUtil.deleteAll(systemPermissionGroupDataSet);
		DBUnitUtil.cleanInsert(systemPermissionDataSet);
    }
	
}