package com.projectx.starwars.rebelde;

import lombok.Data;

@Data
public class Negociacao {
    private Long id1;
    private Long id2;
    private ConjuntoDeItens conjunto1;
    private ConjuntoDeItens conjunto2;
}
