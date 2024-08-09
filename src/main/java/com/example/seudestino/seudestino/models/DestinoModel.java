package com.example.seudestino.seudestino.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "destino")
@Entity
@Data
public class DestinoModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String pais;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ViagemModel> viagens;
}
