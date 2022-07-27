package com.sting.ordenadores;

import com.sting.ordenadores.entities.Laptop;
import com.sting.ordenadores.repositories.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OrdenadoresApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OrdenadoresApplication.class, args);

		LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null, "Compaq", 120.00, "4gb Ram");
		Laptop laptop2 = new Laptop(null, "Samsung", 200.00, "8gb Ram");
		Laptop laptop3 = new Laptop(null, "Asus", 400.00, "32gb Ram");
		Laptop laptop4 = new Laptop(null, "Apple", 400.00, "16gb Ram");

		laptopRepository.save(laptop1);
		laptopRepository.save(laptop2);
		laptopRepository.save(laptop3);
		laptopRepository.save(laptop4);

	}

}
