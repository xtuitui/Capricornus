package com.xiaotuitui.capricornus.infrastructure.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemPermissionRepImplTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void testQueryAllSystemPermission(){
		System.out.println(entityManager.createNamedQuery("SystemPermission.queryAllSystemPermission").getResultList());
	}
	
}