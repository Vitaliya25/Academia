package com.academia.academia.service;


import com.academia.academia.entity.Horario;
import com.academia.academia.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioServiceImp implements HorarioService {

    private final HorarioRepository horarioRepository;

    public HorarioServiceImp(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    @Override
    public List<Horario> getAllHorarios() {
        return horarioRepository.findAll();
    }

    @Override
    public Optional<Horario> getHorarioById(Long id) {
        return horarioRepository.findById(id);
    }

    @Override
    public Horario saveHorario(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public void deleteHorario(Long id) {
        horarioRepository.deleteById(id);
    }
}