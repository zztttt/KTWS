package com.ktws;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.test.context.junit4.SpringRunner;

import com.ktws.KtwsApplication;
import com.ktws.Dao.UserDao;
import com.ktws.Entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KtwsApplication.class)
public class UserDaoTest {

	@Autowired
	protected UserDao userdao;
	
	@Test
	public void testFindAll() {
		List<User> lu = userdao.findAll();
		assertFalse(lu.size() == 0);
	}
	
	@Test
	public void testInsert() {
		User u = new User();
		u.setPassword("888");
		u.setRole("ROLE_USER");
		u.setUsername("test");
		userdao.save(u);
		User newu = userdao.findByName("test");
		assertFalse(newu == null);
	}
	
	@Test
	public void testDelete() {
		User newu = userdao.findByName("test");
		assertFalse(newu == null);
		
		userdao.delete(newu);
		User newu2 = userdao.findByName("test");
		assertFalse(newu2 != null);
	}
/*
	@Test
	public void testFindCountId() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByUserId() {
		fail("Not yet implemented");
	}
*/
}
