package com.zizser.zizserservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
	@Info(
			title = "Zizser API",
			version = "0.0.0"
	)
)
public class ZizserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZizserServiceApplication.class, args);
	}

}
