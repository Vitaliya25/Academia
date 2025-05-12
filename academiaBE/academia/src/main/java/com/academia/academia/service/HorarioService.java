package com.academia.academia.service;

import com.academia.academia.entity.Horario;

import java.util.List;
import java.util.Optional;

public interface HorarioService {
    List<Horario> getAllHorarios();
    Optional<Horario> getHorarioById(Long id);
    Horario saveHorario(Horario horario);
    void deleteHorario(Long id);
}