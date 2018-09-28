package th.co.bookstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostUserResponse {

	@JsonProperty("username")
	private String username;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("date_of_birth")
	private String dateOfBirth;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
