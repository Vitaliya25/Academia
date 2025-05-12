package com.academia.academia;

import com.academia.academia.entity.Alumno;
import com.academia.academia.entity.Profesor;
import com.academia.academia.entity.Rol;
import com.academia.academia.entity.User;
import com.academia.academia.repository.AlumnoRepository;
import com.academia.academia.repository.ProfesorRepository;
import com.academia.academia.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(App.class, args);
		AlumnoRepository alumnoRepository = context.getBean(AlumnoRepository.class);
		ProfesorRepository profesorRepository = context.getBean(ProfesorRepository.class);
		UserRepository userRepository = context.getBean(UserRepository.class);

// Creando una lista de alumnos
		List<Alumno> alumnos = List.of(
				new Alumno("Juan", "Pérez", 14, "2º de Secundaria", "1234567890", "juan.perez@example.com"),
				new Alumno("María", "López", 16, "4º de Secundaria", "9876543210", "maria.lopez@example.com"),
				new Alumno("Carlos", "Gómez", 13, "1º de Secundaria", "4561237890", "carlos.gomez@example.com"),
				new Alumno("Ana", "Ramírez", 17, "1º de Bachiller", "7893216540", "ana.ramirez@example.com"),
				new Alumno("Pedro", "Sánchez", 15, "3º de Secundaria", "3216549870", "pedro.sanchez@example.com"),
				new Alumno("Juan", "Pérez", 14, "2º de Secundaria", "1234567890", "juan.perez@example.com"),
				new Alumno("María", "López", 16, "4º de Secundaria", "9876543210", "maria.lopez@example.com"),
				new Alumno("Carlos", "Gómez", 13, "1º de Secundaria", "4561237890", "carlos.gomez@example.com"),
				new Alumno("Ana", "Ramírez", 15, "3º de Secundaria", "7893216540", "ana.ramirez@example.com"),
				new Alumno("Pedro", "Sánchez", 12, "1º de Secundaria", "3216549870", "pedro.sanchez@example.com"),
				new Alumno("Laura", "Díaz", 14, "2º de Secundaria", "9517534682", "laura.diaz@example.com"),
				new Alumno("Sergio", "Fernández", 16, "4º de Secundaria", "8529637410", "sergio.fernandez@example.com"),
				new Alumno("Natalia", "Castro", 15, "3º de Secundaria", "7896541230", "natalia.castro@example.com"),
				new Alumno("Andrés", "García", 13, "1º de Secundaria", "4567893210", "andres.garcia@example.com"),
				new Alumno("Elena", "Hernández", 12, "1º de Secundaria", "1237896540", "elena.hernandez@example.com"),
				new Alumno("Miguel", "Torres", 17, "1º de Bachiller", "7412589630", "miguel.torres@example.com"),
				new Alumno("Isabel", "Ruiz", 18, "2º de Bachiller", "9638527410", "isabel.ruiz@example.com"),
				new Alumno("Fernando", "Gómez", 17, "1º de Bachiller", "8527413690", "fernando.gomez@example.com"),
				new Alumno("Lucía", "Morales", 18, "2º de Bachiller", "7891236540", "lucia.morales@example.com"),
				new Alumno("Raúl", "Jiménez", 17, "1º de Bachiller", "3697412580", "raul.jimenez@example.com"),
				new Alumno("Claudia", "Ortega", 18, "2º de Bachiller", "2589637410", "claudia.ortega@example.com"),
				new Alumno("Javier", "Santos", 17, "1º de Bachiller", "1597534860", "javier.santos@example.com"),
				new Alumno("Beatriz", "Luna", 18, "2º de Bachiller", "6549873210", "beatriz.luna@example.com"),
				new Alumno("Alejandro", "Molina", 17, "1º de Bachiller", "3698521470", "alejandro.molina@example.com"),
				new Alumno("Sofía", "Vega", 18, "2º de Bachiller", "7419638520", "sofia.vega@example.com")
);

// Creando una lista de profesores

		List<Profesor> profesores = List.of(
				new Profesor("Luis", "Martínez", "Matemáticas", "1234567890", "luis.martinez@example.com"),
				new Profesor("Ana", "Gómez",  "Historia", "9876543210", "ana.gomez@example.com"),
				new Profesor("Carlos", "Fernández", "Física", "4561237890", "carlos.fernandez@example.com"),
				new Profesor("Elena", "Ruiz", "Química", "7893216540", "elena.ruiz@example.com"),
				new Profesor("Miguel", "Sánchez",  "Lengua", "3216549870", "miguel.sanchez@example.com")
		);

		List<User> usuarios = List.of(
				new User("Vitaliya", "admin", "vitaliya@gmail.com", Rol.ADMIN)
		);


		alumnoRepository.saveAll(alumnos);
		profesorRepository.saveAll(profesores);
		userRepository.saveAll(usuarios);
	}
}
