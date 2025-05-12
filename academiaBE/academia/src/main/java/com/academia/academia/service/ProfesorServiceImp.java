package com.academia.academia.service;

import com.academia.academia.entity.Clase;
import com.academia.academia.entity.Profesor;
import com.academia.academia.repository.ClaseRepository;
import com.academia.academia.repository.ProfesorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorServiceImp implements ProfesorService {

    private ProfesorRepository profesorRepository;
    private ClaseRepository claseRepository;


    public ProfesorServiceImp(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    @Override
    public List<Profesor> obtenerTodos() {
        return profesorRepository.findAll();
    }

    @Override
    public Optional<Profesor> obtenerPorId(Long id) {
        return profesorRepository.findById(id);
    }

    @Override
    public Profesor guardarProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    @Override
    public void eliminarProfesor(Long id) {
        profesorRepository.deleteById(id);
    }

    @Override
    public List<Profesor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido) {
        return profesorRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, apellido);
    }


    public Optional<Profesor> asignarClase(Long idProfesor, Long idClase) {
        Optional<Profesor> profesorOpt = profesorRepository.findById(idProfesor);
        Optional<Clase> claseOpt = claseRepository.findById(idClase);

        if (profesorOpt.isPresent() && claseOpt.isPresent()) {
            Profesor profesor = profesorOpt.get();
            Clase clase = claseOpt.get();

            clase.setProfesor(profesor); // asignar profesor a clase
            profesor.getClases().add(clase); // agregar clase a la lista

            claseRepository.save(clase); // guardar la clase actualizada

            return Optional.of(profesorRepository.save(profesor)); // devolver profesor actualizado
        }
        return Optional.empty();
    }


}
