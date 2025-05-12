package com.academia.academia.service;

import com.academia.academia.entity.User;
import com.academia.academia.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Obtener todos los alumnos
    @Override
    public List<User> obtenerTodos() {
        return userRepository.findAll();
    }

    // Obtener un alumno por ID
    @Override
    public Optional<User> obtenerPorId(Long id) {
        return userRepository.findById(id);
    }

    // Crear o actualizar un alumno
    @Override
    public User guardar(User user) {
        return userRepository.save(user);
    }

    // Eliminar un alumno
    @Override
    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
}
