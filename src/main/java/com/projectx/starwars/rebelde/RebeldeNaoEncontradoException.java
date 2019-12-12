package com.projectx.starwars.rebelde;

public class RebeldeNaoEncontradoException extends Throwable {
    RebeldeNaoEncontradoException(){
        super("Rebelde não encontrado");
    }
}
