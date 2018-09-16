package com.todolist.integration;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.todolist.TodoListApplication;
import com.todolist.dto.CommentDto;
import com.todolist.dto.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodoListApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoItemControllerIT {
	
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Before
	public void before() {
		headers.add("Authorization", getHttpAuthentication("Ahmad", "ahmad"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testAGetAllItems() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items/all"),
				HttpMethod.GET, entity, String.class);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("[").append("{");
		expectedResult.append("\"task\"").append(":").append("\"Incomplete Todo Item\"").append(",");
		expectedResult.append("\"status\"").append(":").append("\"Pending\"").append(",");
		expectedResult.append("\"comments\"").append(":").append("[").append("]");
		expectedResult.append("}").append(",").append("{");
		expectedResult.append("\"task\"").append(":").append("\"Complete Todo Item\"").append(",");
		expectedResult.append("\"status\"").append(":").append("\"Completed\"").append(",");
		expectedResult.append("\"comments\"").append(":").append("[").append("]");
		expectedResult.append("}").append("]");
		JSONAssert.assertEquals(expectedResult.toString(), response.getBody(), false);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 200", 200, status);
	    
	    String content = response.getBody();
	    Assert.assertTrue("failure - expected HTTP response body to have a value",content.trim().length() > 0);
	}
	
	@Test
	public void testBGetRecentPendingItems() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items"),
				HttpMethod.GET, entity, String.class);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("[").append("{");
		expectedResult.append("\"task\"").append(":").append("\"Incomplete Todo Item\"").append(",");
		expectedResult.append("\"status\"").append(":").append("\"Pending\"").append(",");
		expectedResult.append("\"comments\"").append(":").append("[").append("]");
		expectedResult.append("}").append("]");
		JSONAssert.assertEquals(expectedResult.toString(), response.getBody(), false);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 200", 200, status);
	    
	    String content = response.getBody();
	    Assert.assertTrue("failure - expected HTTP response body to have a value",content.trim().length() > 0);
	}
	
	@Test
	public void testCGetTodoItemByItemIDFound() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items/1002"),
				HttpMethod.GET, entity, String.class);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("{");
		expectedResult.append("\"task\"").append(":").append("\"Complete Todo Item\"").append(",");
		expectedResult.append("\"status\"").append(":").append("\"Completed\"").append(",");
		expectedResult.append("\"comments\"").append(":").append("[").append("]");
		expectedResult.append("}");
		JSONAssert.assertEquals(expectedResult.toString(), response.getBody(), false);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 200", 200, status);
	    
	    String content = response.getBody();
	    Assert.assertTrue("failure - expected HTTP response body to have a value",content.trim().length() > 0);
	}
	
	@Test
	public void testDGetTodoItemByItemIDNotFound() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items/1003"),
				HttpMethod.GET, entity, String.class);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 500", 500, status);
	}
	
	@Test
	public void testECreateTodoItem() throws JSONException {

		CommentDto comment = new CommentDto("first comment!","17/09/2018");
		List<CommentDto> comments = new ArrayList<CommentDto>();
		comments.add(comment);
		ItemDto item = new ItemDto("Add Todo Item", "Pending",comments);

		HttpEntity<ItemDto> entity = new HttpEntity<ItemDto>(item, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items"),
				HttpMethod.POST, entity, String.class);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 201", 201, status); // HttpStatus.CREATED
	}
	
	@Test
	public void testFDeleteTodoItemSucessfully() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items/1002"),
				HttpMethod.DELETE, entity, String.class);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 204", 204, status); // HttpStatus.NO_CONTENT
	    
	    // verifing item deleted
	    response = restTemplate.exchange(getLocalUrl("/todo/items/1002"),HttpMethod.GET, entity, String.class);
		
		status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 500", 500, status);
	}
	
	@Test
	public void testGmarkTodoItemComplete() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items/1001"),
				HttpMethod.PUT, entity, String.class);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 204", 204, status); // HttpStatus.NO_CONTENT
	    
	    // verify item completed
	    response = restTemplate.exchange(getLocalUrl("/todo/items/1001"),HttpMethod.GET, entity, String.class);
	    
		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("{");
		expectedResult.append("\"task\"").append(":").append("\"Incomplete Todo Item\"").append(",");
		expectedResult.append("\"status\"").append(":").append("\"Completed\"").append(",");
		expectedResult.append("\"comments\"").append(":").append("[").append("]");
		expectedResult.append("}");
		JSONAssert.assertEquals(expectedResult.toString(), response.getBody(), false);
		
		status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 200", 200, status);
	}
	
	@Test
	public void testHAddComment() throws JSONException {

		CommentDto comment = new CommentDto("first comment!","17/09/2018");

		HttpEntity<CommentDto> entity = new HttpEntity<CommentDto>(comment, headers);

		ResponseEntity<String> response = restTemplate.exchange(getLocalUrl("/todo/items/1001/comments"),
				HttpMethod.POST, entity, String.class);
		
		int status = response.getStatusCodeValue();
	    Assert.assertEquals("failure - expected HTTP status 200", 200, status); // HttpStatus.OK
	    
	    String content = response.getBody();
	    Assert.assertTrue("failure - expected HTTP response body to have a value",content.trim().length() > 0);
	}

	private String getLocalUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

	private String getHttpAuthentication(String userId, String password) {

		String auth = userId + ":" + password;

		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));

		return ("Basic " + new String(encodedAuth));
	}
}
