package com.emeka.delivery;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Proyecto Lenguajes de Programacion (Delivery)", version = "1.0.0", description = """
            API para gestionar pedidos.
            Este proyecto fue desarrollado como parte de una colaboración grupal para
            la asignatura de Lenguajes de Programacion impartida por el ingeniero Harold Coello.

            **Equipo de Desarrollo:**           
            - Eduardo Gabriel Martínez Zelaya (20121010326)
     
        """, license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), contact = @Contact(name = "Soporte del Microservicio", email = "emartinez@unah.hn", url = "http://api.whatsapp.com/send?phone=50431512355")), servers = {
        @Server(description = "Ambiente Local", url = "http://localhost:8080/")
})
@SpringBootApplication
@ComponentScan(basePackages = "com.emeka.delivery")
public class DeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}

}
