package responseModel;

public class Post {
	public int postId;
	public int id;
	public String name;
	public String email;
	public String body;
	
	public Post() {
		
	}
	
	public void printPost() {
		System.out.println("PostId: "+this.postId);
	}

}
