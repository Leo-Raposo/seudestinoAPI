package com.example.seudestino.seudestino.controllers;

import com.example.seudestino.seudestino.dtos.DestinoRecordDto;
import com.example.seudestino.seudestino.models.DestinoModel;
import com.example.seudestino.seudestino.repositories.DestinoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping
    public ResponseEntity<List<DestinoModel>> getAllDestinos() {
        return ResponseEntity.status(HttpStatus.OK).body(destinoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDestinoById(@PathVariable(value="id") Long id) {
        DestinoModel destinoO = destinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino não encontrado"));
        return ResponseEntity.status(HttpStatus.OK).body(destinoO);
    }

    //Get para retornar o número total de destinos cadastrados
    @GetMapping("/contagem")
    public ResponseEntity<Long> getDestinoContagem() {
        long contagem = destinoRepository.count();
        return ResponseEntity.status(HttpStatus.OK).body(contagem);
    }

    //Get para retornar o TOP 10 destinos mais visitados
    @GetMapping("/populares")
    public ResponseEntity<Object> getDestinosMaisVisitados() {
        List<DestinoModel> destinos = destinoRepository.findAll();

        if (destinos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum destino encontrado.");
        }

        destinos.sort((d1, d2) -> Integer.compare(d2.getViagens().size(), d1.getViagens().size()));

        List<DestinoModel> destinosPopulares = destinos.stream()
                .filter(destino -> !destino.getViagens().isEmpty())
                .limit(10)
                .toList();

        if (destinosPopulares.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum destino popular encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(destinosPopulares);
    }

    @PostMapping
    public ResponseEntity<DestinoModel> addDestino(@RequestBody @Valid DestinoRecordDto destinoDto){
        var destinoModel = new DestinoModel();
        BeanUtils.copyProperties(destinoDto, destinoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(destinoRepository.save(destinoModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDestino(@PathVariable(value = "id") Long id, @RequestBody @Valid DestinoRecordDto destinoDto) {
        Optional<DestinoModel> destinoO = destinoRepository.findById(id);
        if(destinoO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destino não encontrado");
        }else {
            var destinoModel = destinoO.get();
            BeanUtils.copyProperties(destinoDto, destinoModel);
            return ResponseEntity.status(HttpStatus.OK).body(destinoRepository.save(destinoModel));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDestino(@PathVariable(value = "id") Long id){
        Optional<DestinoModel> destinoO = destinoRepository.findById(id);
        if(destinoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destino não encontrado");
        }else {
            destinoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Destino excluido com sucesso!");
        }
    }

}
