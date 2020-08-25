package com.example.curso.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.curso.dao.IUserDao;
import com.example.curso.entity.User;


/*
 * 
 * UserDetailsService:
 * 
 * Interfaz principal que carga datos específicos del usuario.
 * Se utiliza en todo el marco como un DAO de usuario y es la estrategia utilizada por DaoAuthenticationProvider.
 * La interfaz requiere solo un método de solo lectura, lo que simplifica el soporte para nuevas estrategias de acceso a datos.
 * */

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private IUserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario no valido");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}

	@Override
	@Transactional
	public void save(User user) {
			userDao.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return  userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public User findUser(User user) {
		// TODO Auto-generated method stub
		return (User) userDao.findByUserName(user.getUserName());
	}

}
