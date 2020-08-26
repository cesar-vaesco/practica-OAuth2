package com.example.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso.entity.User;
import com.example.curso.service.IUserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private IUserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; // Codificador de contraseñas

	/*
	 * URL: http://localhost:8071/api/user
	 * 
	 * Ejemplo de cuerpo de respuesta:
	 * 
	 * [ { "id": 1, 
	 * "userName": "Vero", 
	 * "password":"$2a$10$8/rQ/8onxtQKAYbLT3Ke9O1E6CyHBDgT1wlBYRriGdcb9r7Vn.d7a", 
	 * "createAt":"2020-08-26" 
	 * }]
	 */
	@GetMapping("/user")
	public ResponseEntity<?> obtenerUsuarios() {
		List<User> users = userService.findAll();
		if (users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * URL:http://localhost:8071/api/create_user
	 * 
	 * Cuerpo de la petición: { "userName":"cesar", "password": 1234 }
	 */
	@PostMapping("/create_user")
	public ResponseEntity<?> create(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		this.userService.save(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Creando un usuario con validación
	 * 
	 * * URL:http://localhost:8071/api/create_user2
	 * 
	 * Cuerpo de la petición: { "userName":"cesar", "password": 1234 }
	 */
	@PostMapping("/create_user2")
	public ResponseEntity<?> create2(@RequestBody User user) {
		if (userService.findUser(user) == null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			this.userService.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

}

/*
 * Clase BCryptPasswordEncoder
 * 
 * Implementación de PasswordEncoder que utiliza la función de hash fuerte de
 * BCrypt. Los clientes pueden proporcionar opcionalmente una "versión" ($ 2a, $
 * 2b, $ 2y) y una "fuerza" (también conocida como rondas de registro en BCrypt)
 * y una instancia de SecureRandom. Cuanto mayor sea el parámetro de fuerza, más
 * trabajo tendrá que hacerse (exponencialmente) para codificar las contraseñas.
 * El valor predeterminado es 10.
 */
