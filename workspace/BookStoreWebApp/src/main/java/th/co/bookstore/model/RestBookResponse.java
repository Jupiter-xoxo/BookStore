package th.co.bookstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestBookResponse {

	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("book_name")
	private String bookName;

	@JsonProperty("author_name")
	private Integer authorName;
	
	@JsonProperty("price")
	private Integer price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getAuthorName() {
		return authorName;
	}

	public void setAuthorName(Integer authorName) {
		this.authorName = authorName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
