package com.projectx.starwars.rebelde;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "rebelde")
@Entity
@Data
public class Rebelde {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", unique = true)
    private String nome;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "genero")
    private String genero;

    @Column(name = "denuncias", columnDefinition = "integer default 0")
    private Integer denuncias;

    @Column(name = "traidor", columnDefinition = "boolean default false")
    private Boolean traidor;

    @Embedded
    private Localizacao localizacao;

    @Embedded
    private ConjuntoDeItens inventario;
}
