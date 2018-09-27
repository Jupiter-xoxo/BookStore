package com.co.th.bookstore.dao;

import org.springframework.data.repository.CrudRepository;

import com.co.th.bookstore.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
