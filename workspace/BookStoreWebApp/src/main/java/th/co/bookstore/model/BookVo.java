package th.co.bookstore.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookVo {

	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("book_name")
	private String bookName;

	@JsonProperty("author_name")
	private String authorName;
	
	@JsonProperty("price")
	private BigDecimal price;
	
	@JsonProperty("is_recommended")
	private boolean isRecommended;

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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isRecommended() {
		return isRecommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	@Override
	public String toString() {
		return "BookVo [id=" + id + ", bookName=" + bookName + ", authorName=" + authorName + ", price=" + price
				+ ", isRecommended=" + isRecommended + "]";
	}
	
}
