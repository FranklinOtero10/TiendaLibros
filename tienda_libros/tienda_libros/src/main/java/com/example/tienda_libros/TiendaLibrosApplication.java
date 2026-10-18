package com.example.tienda_libros;

import com.example.tienda_libros.vista.LibroForm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class TiendaLibrosApplication {

	public static void main(String[] args) {
		//SpringApplication.run(TiendaLibrosApplication.class, args);
		ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(TiendaLibrosApplication.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);

		//Ejectar el codigo para cargar el formulario
		EventQueue.invokeLater(()->{
			//Obtener el objeto forma traves de spring
			LibroForm libroForm = contextSpring.getBean(LibroForm.class);
			libroForm.setVisible(true);
		});
	}
}
