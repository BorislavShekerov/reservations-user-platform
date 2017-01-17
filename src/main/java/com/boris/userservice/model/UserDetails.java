package com.boris.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class UserDetails {
	@Id
	private String email;
	private String password;
	private UserType userType;
	
	public UserDetails() {
	}
	
	public UserDetails(String email, String password, UserType userType) {
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public UserType getUserType() {
		return userType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userType != other.userType)
			return false;
		return true;
	}
	
}
