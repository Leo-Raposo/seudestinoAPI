package com.example.seudestino.seudestino.repositories;

import com.example.seudestino.seudestino.models.ViagemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ViagemRepository extends JpaRepository<ViagemModel, Long> {
    List<ViagemModel> findByDestinoId(Long destinoId);
    List<ViagemModel> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);

}
