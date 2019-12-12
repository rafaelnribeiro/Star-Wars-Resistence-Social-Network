package com.projectx.starwars.rebelde;

public class NegociacaoComTraidorException extends Throwable {
    NegociacaoComTraidorException(){
        super("Não é possivel negociar com traidores");
    }
}
