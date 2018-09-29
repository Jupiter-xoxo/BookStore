package th.co.bookstore.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderUserRequest {

	@JsonProperty("orders")
	private List<Integer> orders = new ArrayList<>();

	public List<Integer> getOrders() {
		return orders;
	}

	public void setOrders(List<Integer> orders) {
		this.orders = orders;
	}
	
}
