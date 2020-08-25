package com.example.curso.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.curso.entity.User;

public interface IUserDao extends JpaRepository<User, Long> {

	public User findByUserName(String userName);

}
