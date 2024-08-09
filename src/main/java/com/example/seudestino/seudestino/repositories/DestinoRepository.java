package com.example.seudestino.seudestino.repositories;

import com.example.seudestino.seudestino.models.DestinoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinoRepository extends JpaRepository<DestinoModel, Long> {
    List<DestinoModel> findByPais(String pais);
}
