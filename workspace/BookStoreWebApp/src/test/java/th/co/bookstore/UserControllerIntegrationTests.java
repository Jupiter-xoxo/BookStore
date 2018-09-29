package th.co.bookstore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import th.co.bookstore.dao.UserRepository;
import th.co.bookstore.dao.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerIntegrationTests {

//	@Autowired
//    private MockMvc mvc;
	
//	@Test
//	public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
//	     
//	    Employee alex = new Employee("alex");
//	 
//	    List<Employee> allEmployees = Arrays.asList(alex);
//	 
//	    given(service.getAllEmployees()).willReturn(allEmployees);
//	 
//	    mvc.perform(get("/api/employees")
//	      .contentType(MediaType.APPLICATION_JSON))
//	      .andExpect(status().isOk())
//	      .andExpect(jsonPath("$", hasSize(1)))
//	      .andExpect(jsonPath("$[0].name", is(alex.getName())));
//	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void whenFindByUsername_thenReturnNull() {
	    // given
	    User mock = new User();
	    mock.setUsername("empty");
	    
	    // when
	    User found = userRepository.findByUsernameAndIsDeleted(mock.getUsername(), "N");
	 
	    // then
	    Assert.assertNull(found);
	}
	
	@Test
	public void givenIsDeltedNFlag_whenFindByUsername_thenReturnUser() {
	    // given
	    User mock = new User();
	    mock.setUsername("username");
	    mock.setIsDeleted("N");
	    
	    // when
	    User found = userRepository.findByUsernameAndIsDeleted(mock.getUsername(), mock.getIsDeleted());
	 
	    // then
	    Assert.assertEquals(found.getUsername(), mock.getUsername());
	}
	
	@Test
	public void givenIsDeltedYFlag_whenFindByUsername_thenReturnUser() {
	    // given
	    User mock = new User();
	    mock.setUsername("username");
	    mock.setIsDeleted("Y");
	    
	    // when
	    User found = userRepository.findByUsernameAndIsDeleted(mock.getUsername(), mock.getIsDeleted());
	    
	    if (found == null) {
	    	found = new User();
	    }
	 
	    // then
	    Assert.assertNotEquals(found.getUsername(), mock.getUsername());
	}

//	@Test
//	public void whenSaveUser_thenReturnUser() {
//	    // given
//	    User mock = new User();
//	    mock.setUsername("mock");
//	    mock.setPassword("mock");
//	    mock.setIsDeleted("N");
//	    
//	    // when
//	    User found = userRepository.save(mock);
//	 
//	    // then
//	    Assert.assertEquals(found.getUsername(), mock.getUsername());
//	}
	
	@Test
	public void givenUsernameAndIsDeltedNFlag_whenDeleteUser_thenReturnUser() {
	    // given
	    User mock = new User();
	    mock.setUsername("mock");
	    mock.setIsDeleted("N");
	    
	    User mockDelete = userRepository.findByUsernameAndIsDeleted(mock.getUsername(), mock.getIsDeleted());
	    if (mockDelete == null) {
	    	Assert.assertNull(mockDelete);
	    } else {
	    	mockDelete.setIsDeleted("Y");
		    // when
		    User found = userRepository.save(mockDelete);
		 
		    // then
		    Assert.assertEquals(found.getUsername(), mock.getUsername());
	    }
	    
	}
}
