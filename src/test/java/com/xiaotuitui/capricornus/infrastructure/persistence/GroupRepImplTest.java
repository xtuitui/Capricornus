package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupRepImplTest {
	
	@Autowired
	private GroupRep groupRep;
	
	@Test
	public void testQueryGroupByName(){
		Group group = groupRep.queryGroupByName("capricornus-user");
		System.out.println(group);
	}
	
	@Test
	public void testQueryAllGroup(){
		List<Group> groupList = groupRep.queryAllGroup();
		System.out.println(groupList);
	}

}