package com.example.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso.entity.MovimientoBancario;
import com.example.curso.entity.User;
import com.example.curso.service.IMovimientoBancarioService;

@RestController
@RequestMapping("/api/secure/oauth2")
public class MovimientoBancarioRestController {

	@Autowired
	private IMovimientoBancarioService movimientoBancarioSevice;

	
	/*
	 * http://localhost:8071/api/secure/oauth2/movimiento_bancario
	 * */
	@GetMapping("/movimiento_bancario")
	public ResponseEntity<?> movimientos() {
		List<MovimientoBancario> movimientosBancarios = movimientoBancarioSevice.findAll();
		if (movimientosBancarios != null) {
			return new ResponseEntity<>(movimientosBancarios, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	@PostMapping("/movimiento_bancario_user")
	public ResponseEntity<?> movimientoUser(@RequestBody User user){
		List<MovimientoBancario> movimientosBancarios = movimientoBancarioSevice.getMovimientoUser(user.getId());
		if (movimientosBancarios != null) {
			return new ResponseEntity<>(movimientosBancarios, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
