package com.projectx.starwars.rebelde;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Localizacao {
    private String base;
    private Double longitude;
    private Double latitude;
}
