package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mockit.Deencapsulation;
import mockit.Expectations;

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

import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.UserRep;
import com.xiaotuitui.capricornus.util.dto.UserDto;
import com.xiaotuitui.framework.domain.model.PageObject;
import com.xiaotuitui.framework.domain.model.SqlParameters;
import com.xiaotuitui.testframework.DBUnitUtil;
import com.xiaotuitui.testframework.EntityUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepImplTest{
	
	@Autowired
	private UserRep userRep;
	
	private static IDataSet userDataSet;
	
	private static IDataSet userGroupDataSet;
	
	private static String userTableName;
	
	private static Column[] additionalColumnInfo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@BeforeClass
    public static void setUpBeforeClass() throws DatabaseUnitException, IOException, SQLException, ClassNotFoundException{
		DBUnitUtil.registerDBUnitConnection();
		userDataSet = DBUnitUtil.getDbunitDataSetByXmlFileName("dbunit/user.xml");
		userGroupDataSet = DBUnitUtil.getDbunitDataSetByXmlFileName("dbunit/user_group.xml");
		userTableName = userDataSet.getTableNames()[0];
		additionalColumnInfo = buildUserAdditionalColumnInfo();
    }
    
    @AfterClass
    public static void tesrDownAfterClass(){
    	DBUnitUtil.closeConnection();
    }
    
	@Test
	public void testQueryAllUser() throws Exception{
		initUserData();
		List<User> userList = userRep.queryAllUser();
		ITable expectedTable = userDataSet.getTable(userTableName);
		ITable actualTable = DBUnitUtil.getDbunitDataSetByEntityList(userList, userTableName).getTable(userTableName);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	@Test
	public void testQueryUserByUsername() throws DatabaseUnitException, SQLException{
		initUserData();
		User expectedUser = (User) EntityUtil.createEntity(User.class, 1);
		User user = userRep.queryUserByUsername(expectedUser.getUsername());
		Assert.assertEquals(expectedUser.getUsername(), user.getUsername());
		Assert.assertEquals(expectedUser.getNickname(), user.getNickname());
		Assert.assertEquals(expectedUser.getPassword(), user.getPassword());
		Assert.assertEquals(expectedUser.getEmail(), user.getEmail());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryUserByNickname() throws Exception{
		initUserData();
		List<User> expectedUserList = EntityUtil.createEntityList(User.class, 4);
		List<User> userList = userRep.queryUserByNickname("nickname");
		ITable expectedTable = DBUnitUtil.getDbunitDataSetByEntityList(expectedUserList, userTableName).getTable(userTableName);
		ITable actualTable = DBUnitUtil.getDbunitDataSetByEntityList(userList, userTableName).getTable(userTableName);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testCreateUser() throws Exception{
		deleteUserData();
		User user = (User) EntityUtil.createEntity(User.class, 100);
		user.setId(null);
		userRep.createUser(user);
		ITable expectedTable = DBUnitUtil.getDbunitDataSetByEntity(user, userTableName).getTable(userTableName);
		ITable actualTable = DBUnitUtil.getDBUnitConnection(getUnwarpConnection()).createDataSet().getTable(userTableName);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	@Test
	public void testLoadUser() throws DatabaseUnitException, SQLException{
		initUserData();
		User expectedUser = (User) EntityUtil.createEntity(User.class, 1);
		Integer userId = ((User) entityManager.createQuery("from User u where u.username = :username").setParameter("username", expectedUser.getUsername()).getSingleResult()).getId();
		User actualUser = userRep.loadUser(userId);
		Assert.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
		Assert.assertEquals(expectedUser.getNickname(), actualUser.getNickname());
		Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
	}
	
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testRemoveUser() throws Exception{
		initUserData();
		User user = ((User) entityManager.createQuery("from User u where u.username = :username").setParameter("username", "username4").getSingleResult());
		userRep.removeUser(user);
		entityManager.flush();
		ITable expectedTable = DBUnitUtil.getDbunitDataSetByEntityList(EntityUtil.createEntityList(User.class, 3), userTableName).getTable(userTableName);
		ITable actualTable = DBUnitUtil.getDBUnitConnection(getUnwarpConnection()).createDataSet().getTable(userTableName);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	@Test
	public void testBuildSqlParametersEmptyUserDto() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> cls = userRep.getClass();
		Method method = cls.getDeclaredMethod("buildSqlParameters", UserDto.class);
		method.setAccessible(true);
		SqlParameters sqlParameters = (SqlParameters) method.invoke(userRep, new UserDto());
		Assert.assertEquals("select distinct u from User u where 1=1".trim(), sqlParameters.getSqlStringBuilder().toString().trim());
		Assert.assertEquals(new HashMap<String, Object>(), sqlParameters.getParameters());
	}
	
	@Test
	public void testBuildSqlParametersUsername() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> cls = userRep.getClass();
		Method method = cls.getDeclaredMethod("buildSqlParameters", UserDto.class);
		method.setAccessible(true);
		SqlParameters sqlParameters = (SqlParameters) method.invoke(userRep, createUserDtoWithUsername());
		Assert.assertEquals("select distinct u from User u where 1=1 and u.username like :username".trim(), sqlParameters.getSqlStringBuilder().toString().trim());
		Assert.assertEquals(createHashMapWithUsername(), sqlParameters.getParameters());
	}
	
	@Test
	public void testBuildSqlParametersNickname() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> cls = userRep.getClass();
		Method method = cls.getDeclaredMethod("buildSqlParameters", UserDto.class);
		method.setAccessible(true);
		SqlParameters sqlParameters = (SqlParameters) method.invoke(userRep, createUserDtoWithNickname());
		Assert.assertEquals("select distinct u from User u where 1=1 and u.nickname like :nickname".trim(), sqlParameters.getSqlStringBuilder().toString().trim());
		Assert.assertEquals(createHashMapWithNickname(), sqlParameters.getParameters());
	}
	
	@Test
	public void testBuildSqlParametersEmail() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> cls = userRep.getClass();
		Method method = cls.getDeclaredMethod("buildSqlParameters", UserDto.class);
		method.setAccessible(true);
		SqlParameters sqlParameters = (SqlParameters) method.invoke(userRep, createUserDtoWithEmail());
		Assert.assertEquals("select distinct u from User u where 1=1 and u.email like :email".trim(), sqlParameters.getSqlStringBuilder().toString().trim());
		Assert.assertEquals(createHashMapWithEmail(), sqlParameters.getParameters());
	}
	
	@Test
	public void testBuildSqlParametersGroup() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> cls = userRep.getClass();
		Method method = cls.getDeclaredMethod("buildSqlParameters", UserDto.class);
		method.setAccessible(true);
		SqlParameters sqlParameters = (SqlParameters) method.invoke(userRep, createUserDtoWithGroup());
		Assert.assertEquals("select distinct u from User u join u.groups g where 1=1 and g.id = :groupId".trim(), sqlParameters.getSqlStringBuilder().toString().trim());
		Assert.assertEquals(createHashMapWithGroup(), sqlParameters.getParameters());
	}
	
	@Test
	public void testQueryUserByPage() throws Exception{
		initUserData();
		final UserDto userDto = new UserDto();
		new Expectations(userRep) {
			{
				Deencapsulation.invoke(userRep, "buildSqlParameters", userDto);
                result = new SqlParameters("select distinct u from User u where 1=1", new HashMap<String, Object>());
			}
		};
		PageObject pageObject = new PageObject();
		pageObject.setCurrentPage(1l);
		List<User> userList = userRep.queryUserByPage(userDto, pageObject);
		ITable expectedTable = userDataSet.getTable(userTableName);
		ITable actualTable = DBUnitUtil.getDbunitDataSetByEntityList(userList, userTableName).getTable(userTableName);
		Assertion.assertEquals(includeColumn(expectedTable), includeColumn(actualTable));
	}
	
	private Map<String, Object> createHashMapWithGroup() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", 1);
		return map;
	}

	private Map<String, Object> createHashMapWithEmail() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", "%email%");
		return map;
	}

	private Map<String, Object> createHashMapWithNickname() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", "%nickname%");
		return map;
	}

	private Map<String, Object> createHashMapWithUsername() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", "%username%");
		return map;
	}

	private UserDto createUserDtoWithGroup() {
		UserDto userDto = new UserDto();
		userDto.setGroupId(1);
		return userDto;
	}
	
	private UserDto createUserDtoWithEmail() {
		UserDto userDto = new UserDto();
		userDto.setEmail("email");
		return userDto;
	}
	
	private UserDto createUserDtoWithNickname() {
		UserDto userDto = new UserDto();
		userDto.setNickname("nickname");
		return userDto;
	}

	private UserDto createUserDtoWithUsername(){
		UserDto userDto = new UserDto();
		userDto.setUsername("username");
		return userDto;
	}
	
	
	
	
	private Connection getUnwarpConnection(){
		return ((SessionImplementor) entityManager.unwrap(SessionImplementor.class)).connection();
	}
	
	private static ITable includeColumn(ITable table) throws DataSetException{
		return DBUnitUtil.includeColumnTable(table, additionalColumnInfo);
	}
	
	private static Column[] buildUserAdditionalColumnInfo(){
		Column username = new Column("username", DataType.VARCHAR);
		Column nickname = new Column("nickname", DataType.NVARCHAR);
		Column password = new Column("password", DataType.VARCHAR);
		Column email = new Column("email", DataType.VARCHAR);
		return new Column[]{username, nickname, password, email};
	}
	
	private void deleteUserData() throws DatabaseUnitException, SQLException{
		DBUnitUtil.deleteAll(userGroupDataSet);
		DBUnitUtil.deleteAll(userDataSet);
    }
	
	private void initUserData() throws DatabaseUnitException, SQLException{
		DBUnitUtil.deleteAll(userGroupDataSet);
		DBUnitUtil.cleanInsert(userDataSet);
    }

}