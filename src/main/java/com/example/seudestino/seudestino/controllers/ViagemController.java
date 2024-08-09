package com.example.seudestino.seudestino.controllers;

import com.example.seudestino.seudestino.dtos.ViagemRecordDto;
import com.example.seudestino.seudestino.models.DestinoModel;
import com.example.seudestino.seudestino.models.ViagemModel;
import com.example.seudestino.seudestino.repositories.DestinoRepository;
import com.example.seudestino.seudestino.repositories.ViagemRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viagem")
public class ViagemController {

    @Autowired
    ViagemRepository viagemRepository;

    @Autowired
    DestinoRepository destinoRepository;

    @GetMapping
    public ResponseEntity<List<ViagemModel>> getAllViagens(){
        return ResponseEntity.status(HttpStatus.OK).body(viagemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getViagemById(@PathVariable(value="id") Long id) {
        ViagemModel viagemO = viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrado"));
        return ResponseEntity.status(HttpStatus.OK).body(viagemO);
    }

    @GetMapping("/destino/{destinoId}/viagens")
    public ResponseEntity<List<ViagemModel>> getViagensByDestino(@PathVariable(value = "destinoId") Long destinoId){
        List<ViagemModel> viagemO = viagemRepository.findByDestinoId(destinoId);
        if(viagemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(viagemO);
    }

    //Get para retornar uma lista de viagens que estão ativas
    @GetMapping("/ativas")
    public ResponseEntity<Object> getViagensAtivas() {
        LocalDate hoje = LocalDate.now();
        List<ViagemModel> viagensAtivas = viagemRepository.findAll().stream()
                .filter(viagem -> viagem.getDataInicio().isBefore(hoje) && viagem.getDataFim().isAfter(hoje))
                .toList();

        if(viagensAtivas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma viagem encontrada");
        }

        return ResponseEntity.status(HttpStatus.OK).body(viagensAtivas);
    }

    //Get para retornar a lista das viagens mais recentes associadas a um destino
    @GetMapping("/{id}/viagens/recentes")
    public ResponseEntity<Object> getViagensRecentesByDestino(@PathVariable(value = "id") Long destinoId) {
        Optional<DestinoModel> destinoO = destinoRepository.findById(destinoId);
        if (destinoO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destino não encontrado");
        }

        List<ViagemModel> viagensRecentes = viagemRepository.findByDestinoId(destinoId).stream()
                .sorted((d1, d2) -> d2.getDataInicio().compareTo(d1.getDataInicio()))
                .toList();

        if (viagensRecentes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma viagem recente encontrada para este destino");
        }

        return ResponseEntity.status(HttpStatus.OK).body(viagensRecentes);
    }

    //Get para retornar uma lista de viagens que ocorrem em um determinado período
    @GetMapping("/periodo")
    public ResponseEntity<Object> getViagensByPeriodo(@RequestParam(value = "dataInicio") LocalDate dataInicio, @RequestParam(value = "dataFim") LocalDate dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início não pode ser posterior à data de término");
        }

        List<ViagemModel> viagens = viagemRepository.findAll().stream()
                .filter(viagem -> !viagem.getDataInicio().isAfter(dataFim) && !viagem.getDataFim().isBefore(dataInicio))
                .toList();

        if (viagens.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma viagem encontrada para o período especificado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(viagens);
    }



    @PostMapping
    public ResponseEntity<Object> addViagem(@RequestBody @Valid ViagemRecordDto viagemDto){
        Optional<DestinoModel> destinoO = destinoRepository.findById(viagemDto.DestinoId());
        if (destinoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destino não encontrado");
        }

        var viagemModel = new ViagemModel();
        BeanUtils.copyProperties(viagemDto, viagemModel);
        viagemModel.setDestino(destinoO.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemRepository.save(viagemModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateViagem(@PathVariable(value = "id") Long id, @RequestBody @Valid ViagemRecordDto viagemDto){
        Optional<ViagemModel> viagemO = viagemRepository.findById(id);
        if(viagemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada");
        }

        Optional<DestinoModel> destinoO = destinoRepository.findById(viagemDto.DestinoId());
        if(destinoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destinonão encontrado");
        }

        var viagemModel = viagemO.get();
        BeanUtils.copyProperties(viagemDto, viagemModel);
        viagemModel.setDestino(destinoO.get());
        return ResponseEntity.status(HttpStatus.OK).body(viagemRepository.save(viagemModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteViagem(@PathVariable(value = "id") Long id){
        Optional<ViagemModel> viagemO = viagemRepository.findById(id);
        if(viagemO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada");
        } else {
            viagemRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Viagem excluida com sucesso!");
        }
    }
}
