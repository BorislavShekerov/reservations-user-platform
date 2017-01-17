package com.boris.userservice.dao;

import org.springframework.data.repository.CrudRepository;

import com.boris.userservice.model.UserDetails;

public interface UserServiceDao extends CrudRepository<UserDetails, String>{

}
