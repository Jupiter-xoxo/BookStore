package th.co.bookstore.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostUserRequest {

	@JsonProperty("username")
	private String username;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
