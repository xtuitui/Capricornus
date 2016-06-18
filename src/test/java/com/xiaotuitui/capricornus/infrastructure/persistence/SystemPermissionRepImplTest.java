package com.xiaotuitui.capricornus.infrastructure.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaotuitui.capricornus.domain.model.SystemPermission;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemPermissionRepImplTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void testQueryAllSystemPermission(){
		System.out.println(entityManager.createNamedQuery("SystemPermission.queryAllSystemPermission").getResultList());
	}
	
	@Test
	public void testQueryGroup(){
		SystemPermission systemPermission = entityManager.find(SystemPermission.class, 9);
		System.out.println(systemPermission.getGroups());
	}
	
}