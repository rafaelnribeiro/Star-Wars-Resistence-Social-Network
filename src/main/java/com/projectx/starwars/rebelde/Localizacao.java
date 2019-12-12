package com.projectx.starwars.rebelde;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
public class Localizacao {
    private String base;
    private Double longitude;
    private Double latitude;
}
