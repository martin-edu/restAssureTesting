package test;

import org.testng.annotations.Test;

import responseModel.Post;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;




public class HTTPrequest {
	private String url = "https://jsonplaceholder.typicode.com";
	
	@Test
	void getAllElements() 
	{
		given()
		
		.when()
			.get(url+"/posts")
		
		.then()
			.statusCode(200)
			.and()
			.assertThat()
			.body("body", notNullValue())
			.body("body[0]", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"))
			.body("userId[0]", equalTo(1));
	}
	
	@Test
	void getElementById()
	{
		given()
		
		.when()
			.get(url+"/posts/1")
		.then()
			.statusCode(200)
		.and()
			.body("userId", equalTo(1))
			.body("id", equalTo(1))
			.body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
		
	}
	
	@Test
	void getElementByIdWithNonExistedId()
	{
		given()
		
		.when()
			.get(url+"/posts/1000000")
		.then()
			.statusCode(404)
		.and()
		.assertThat()
			.body(equalTo("{}"))
			.log().all();
		
		given()
		
		.when()
			.get(url+"/posts/99999999")
		.then()
			.statusCode(404)
		.and()
			.body(equalTo("{}"));
	}
	
	
	@Test
	void getElementByIdWithWrongParameter()
	{
		given()
		
		.when()
			.get(url+"/posts/a")
		.then()
			.statusCode(404)
		.and()
		.assertThat()
			.body(equalTo("{}"));
	}
	
	@Test
	void getElementCommentsById()
	{
		//Post[] posts = get(url+"/posts/1/comments").as(Post[].class);
		
		given()
		
		.when()
			.get(url+"/posts/1/comments")
		.then()
			.statusCode(200)
		.and()
			.body("body", notNullValue())
			.body("body[0]", equalTo("laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"))
			.body("body[1]", equalTo("est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"))
			.log().all();
			
	}
	
	@Test
	void getElementCommentsByIdWithWrongParameter()
	{
		//Post[] posts = get(url+"/posts/1/comments").as(Post[].class);
		
		given()
		
		.when()
			.get(url+"/posts/a")
		.then()
			.statusCode(404)
		.and()
		.assertThat()
			.body(equalTo("{}"));	
	}
	
	void getElementCommentsByIdWithUrlParameter()
	{
		
		given()
			.queryParam ("postId", 1)
		.when()
			.get(url+"/comments")
		.then()
			.statusCode(200)
		.and()
			.body("body", notNullValue())
			.body("body[0]", equalTo("laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"))
			.body("body[1]", equalTo("est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"));
			
	}
	
	@Test
	void getElementCommentsByIdWithWrongUrlParameter()
	{
		//Post[] posts = get(url+"/posts/1/comments").as(Post[].class);
		
		given()
		.queryParam ("postId", "a")
	.when()
		.get(url+"/comments")
		.then()
			.statusCode(200)
		.and()
		.assertThat()
			.body(equalTo("[]"));	
	}
	
	@Test
	void postAnElement()
	{
		HashMap element = new HashMap();
		
		element.put("userId", "1");
		element.put("title", "Testing api post");
		element.put("body", "Testing api body");
		
		given()
			.contentType("application/json")
			.body(element)
		.when()
			.post(url+"/posts")
			.then()
				.statusCode(201)
			.and()
				.assertThat()
				.body("id", equalTo(101))
				.body("title", equalTo("Testing api post"))
				.body("body", equalTo("Testing api body"));
	}
	
	@Test
	void putAnElementById()
	{
		HashMap element = new HashMap();
		
		element.put("userId", "1");
		element.put("title", "Updating api element");
		element.put("body", "Testing api update body");
		
		given()
			.contentType("application/json")
			.body(element)
		.when()
			.put(url+"/posts/1")
			.then()
				.statusCode(200)
			.and()
				.assertThat()
				.body("id", equalTo(1))
				.body("title", equalTo("Updating api element"))
				.body("body", equalTo("Testing api update body"));
	}
	
	@Test
	void patchAnElementById()
	{
		HashMap element = new HashMap();
		
		element.put("userId", "1");
		element.put("title", "Updating api element");
		
		given()
			.contentType("application/json")
			.body(element)
		.when()
			.patch(url+"/posts/1")
			.then()
				.statusCode(200)
			.and()
				.assertThat()
				.body("id", equalTo(1))
				.body("title", equalTo("Updating api element"))
				.body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur"
						+ " expedita et cum\nreprehenderit molestiae ut ut quas totam\nno"
						+ "strum rerum est autem sunt rem eveniet architecto"));
	}
	
	@Test
	void deleteAnElementById()
	{
		
		given()
			.contentType("application/json")
		.when()
			.delete(url+"/posts/1")
			.then()
				.statusCode(200)
			.and()
				.assertThat()
				.body(equalTo("{}"));
	}
	
	
}
