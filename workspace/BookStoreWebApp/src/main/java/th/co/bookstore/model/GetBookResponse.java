package th.co.bookstore.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetBookResponse {

	@JsonProperty("books")
	List<BookVo> books = new ArrayList<>();

	public List<BookVo> getBooks() {
		return books;
	}

	public void setBooks(List<BookVo> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "GetBookResponse [books=" + books + "]";
	}
	
}
