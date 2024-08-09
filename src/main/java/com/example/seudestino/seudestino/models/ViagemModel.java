package com.example.seudestino.seudestino.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "viagem")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViagemModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private DestinoModel destino;

}