package com.projectx.starwars.rebelde;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
public class ConjuntoDeItens {
    private Integer arma;
    private Integer municao;
    private Integer agua;
    private Integer comida;
}
