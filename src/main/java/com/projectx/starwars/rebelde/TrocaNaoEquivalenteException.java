package com.projectx.starwars.rebelde;

public class TrocaNaoEquivalenteException extends Throwable {
    TrocaNaoEquivalenteException(){
        super("Itens negociados não possuem o mesmo total de pontos");
    }
}
