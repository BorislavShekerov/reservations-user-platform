package com.boris.userservice.model;

public enum UserRole {
	ADMIN, OWNER, MEMBER;
	
	public String authority() {
        return "ROLE_" + this.name();
    }
}
