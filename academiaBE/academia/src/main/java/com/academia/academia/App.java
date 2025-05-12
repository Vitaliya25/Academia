package com.academia.academia;

import com.academia.academia.entity.*;
import com.academia.academia.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(App.class, args);

		AlumnoRepository alumnoRepository = context.getBean(AlumnoRepository.class);
		ProfesorRepository profesorRepository = context.getBean(ProfesorRepository.class);
		ClaseRepository claseRepository = context.getBean(ClaseRepository.class);
		HorarioRepository horarioRepository = context.getBean(HorarioRepository.class);
		UserRepository userRepository = context.getBean(UserRepository.class);
		PagoRepository pagoRepository = context.getBean(PagoRepository.class);


		List<Alumno> alumnos = crearAlumnos();
		alumnoRepository.saveAll(alumnos);

		List<Clase> clases = crearClases();
		claseRepository.saveAll(clases);

		List<Horario> horarios = crearHorarios();
		// Asociar horarios a clases
		horarios.get(0).setClase(clases.get(0));
		horarios.get(1).setClase(clases.get(1));
		horarios.get(2).setClase(clases.get(2));
		horarios.get(3).setClase(clases.get(4));
		horarios.get(4).setClase(clases.get(7));
		horarios.get(5).setClase(clases.get(9));

		horarioRepository.saveAll(horarios);

//		// Asignar horarios a clases
		clases.get(0).setHorarios(List.of(horarios.get(0)));
		clases.get(1).setHorarios(List.of(horarios.get(1)));
		clases.get(2).setHorarios(List.of(horarios.get(2)));
		clases.get(4).setHorarios(List.of(horarios.get(3)));
		clases.get(7).setHorarios(List.of(horarios.get(4)));
		clases.get(9).setHorarios(List.of(horarios.get(5)));


		List<Profesor> profesores = crearProfesores();
		asignarClasesAProfesores(profesores, clases);
		profesorRepository.saveAll(profesores);
		claseRepository.saveAll(clases);


		List<Alumno> alumnosGuardados = alumnoRepository.findAll();
		List<Pago> pagos = new ArrayList<>();

		for (Alumno alumno : alumnosGuardados) {
			pagos.add(new Pago(LocalDate.of(2024, 3, 1), 50.0, LocalDate.of(2024, 3, 5))); // pagado
			pagos.add(new Pago(LocalDate.of(2024, 5, 1), 50.0, null));                     // sin pagar
			pagos.add(new Pago(LocalDate.of(2024, 4, 1), 50.0, LocalDate.of(2024, 4, 2))); // pagado

			for (int i = pagos.size() - 3; i < pagos.size(); i++) {
				pagos.get(i).setAlumno(alumno);
			}
		}

		pagoRepository.saveAll(pagos);






		List<Usuario> usuarios = List.of(
				new Usuario("admin", "123456", "admin@gmail.com")
		);
		userRepository.saveAll(usuarios);

		System.out.println("¡Datos iniciales insertados correctamente!");
	}


	private static List<Alumno> crearAlumnos() {
		return List.of(
				new Alumno("Juan", "Pérez", 14, "2º de Secundaria", "1234567890", "juan.perez@example.com"),
				new Alumno("María", "López", 16, "4º de Secundaria", "9876543210", "maria.lopez@example.com"),
				new Alumno("Carlos", "Gómez", 13, "1º de Secundaria", "4561237890", "carlos.gomez@example.com"),
				new Alumno("Ana", "Ramírez", 17, "1º de Bachiller", "7893216540", "ana.ramirez@example.com"),
				new Alumno("Pedro", "Sánchez", 15, "3º de Secundaria", "3216549870", "pedro.sanchez@example.com"),
				new Alumno("Juan", "Pérez", 14, "2º de Secundaria", "1122334455", "juan.perez2@example.com"),
				new Alumno("Marta", "López", 16, "4º de Secundaria", "9876543210", "marta.lopez@example.com"),
				new Alumno("Mark", "Gómez", 13, "1º de Secundaria", "4561237890", "mark.gomez@example.com"),
				new Alumno("Alba", "Ramírez", 15, "3º de Secundaria", "7893216540", "alba.ramirez@example.com"),
				new Alumno("Pedro", "Sánchez", 12, "1º de Secundaria", "3216549870", "pedro.sanchez2@example.com"),
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
	}

	private static List<Profesor> crearProfesores() {
		return List.of(
				new Profesor("Luis", "Martínez", "1234567890", "luis.martinez@example.com"),
				new Profesor("Ana", "Gómez", "9876543210", "ana.gomez@example.com"),
				new Profesor("Carlos", "Fernández", "4561237890", "carlos.fernandez@example.com"),
				new Profesor("Elena", "Ruiz", "7893216540", "elena.ruiz@example.com"),
				new Profesor("Miguel", "Sánchez", "3216549870", "miguel.sanchez@example.com")
		);
	}

	private static List<Clase> crearClases() {
		return List.of(
				new Clase("Matemáticas", "1º de Secundaria"),
				new Clase("Matemáticas", "2º de Secundaria"),
				new Clase("Matemáticas", "3º de Secundaria"),
				new Clase("Matemáticas", "4º de Secundaria"),
				new Clase("Matemáticas", "1º de Bachiller"),
				new Clase("Matemáticas", "2º de Bachiller"),
				new Clase("Lengua", "3º de Secundaria"),
				new Clase("Física", "4º de Secundaria"),
				new Clase("Historia", "1º de Secundaria"),
				new Clase("Química", "2º de Bachiller"),
				new Clase("Inglés", "1º de Bachiller")
		);
	}

	private static List<Horario> crearHorarios() {
		return List.of(
				new Horario(DiaSemana.LUNES, LocalTime.of(9, 0), LocalTime.of(10, 0)),
				new Horario(DiaSemana.MARTES, LocalTime.of(10, 0), LocalTime.of(11, 0)),
				new Horario(DiaSemana.MIERCOLES, LocalTime.of(11, 0), LocalTime.of(12, 0)),
				new Horario(DiaSemana.JUEVES, LocalTime.of(9, 0), LocalTime.of(10, 0)),
				new Horario(DiaSemana.VIERNES, LocalTime.of(10, 0), LocalTime.of(11, 0)),
				new Horario(DiaSemana.LUNES, LocalTime.of(13, 0), LocalTime.of(14, 0))
		);
	}

	private static void asignarClasesAProfesores(List<Profesor> profesores, List<Clase> clases) {
		clases.get(0).setProfesor(profesores.get(0));
		clases.get(1).setProfesor(profesores.get(0));
		profesores.get(0).setClases(List.of(clases.get(0), clases.get(1)));

		clases.get(2).setProfesor(profesores.get(1));
		clases.get(3).setProfesor(profesores.get(1));
		profesores.get(1).setClases(List.of(clases.get(2), clases.get(3)));

		clases.get(4).setProfesor(profesores.get(2));
		clases.get(5).setProfesor(profesores.get(2));
		profesores.get(2).setClases(List.of(clases.get(4), clases.get(5)));

		clases.get(6).setProfesor(profesores.get(3));
		clases.get(7).setProfesor(profesores.get(3));
		profesores.get(3).setClases(List.of(clases.get(6), clases.get(7)));

		clases.get(8).setProfesor(profesores.get(4));
		clases.get(9).setProfesor(profesores.get(4));
		profesores.get(4).setClases(List.of(clases.get(8), clases.get(9)));

	}
}