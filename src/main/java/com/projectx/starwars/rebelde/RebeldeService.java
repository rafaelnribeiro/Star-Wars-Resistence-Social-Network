package com.projectx.starwars.rebelde;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RebeldeService {

    @Autowired
    private RebeldeRepository rebeldeRepository;

    public Rebelde save(Rebelde rebelde){
        rebelde.setTraidor(false);
        rebelde.setDenuncias(0);
        return this.rebeldeRepository.save(rebelde);
    }

    public Page<Rebelde> findAll(Pageable page){
        return this.rebeldeRepository.findAll(page);
    }

    public Optional<Rebelde> findOne(Long id){
        return this.rebeldeRepository.findById(id);
    }

    public Boolean denunciar(Long id) throws RebeldeNaoEncontradoException {
        Optional<Rebelde> rebeldeOptional = this.rebeldeRepository.findById(id);
        if(rebeldeOptional.isEmpty()){
            throw new RebeldeNaoEncontradoException();
        }
        Rebelde rebelde = rebeldeOptional.get();
        incremDenuncias(rebelde);
        this.rebeldeRepository.save(rebelde);

        return rebelde.getTraidor();
    }

    public void negociar(Long id1, Long id2, ConjuntoDeItens conjunto1, ConjuntoDeItens conjunto2)
            throws RebeldeNaoEncontradoException, NegociacaoComTraidorException, TrocaNaoEquivalenteException {
        Optional<Rebelde> rebeldeOptional1 = this.rebeldeRepository.findById(id1);
        Optional<Rebelde> rebeldeOptional2 = this.rebeldeRepository.findById(id2);

        if(rebeldeOptional1.isEmpty() || rebeldeOptional2.isEmpty()){
            throw new RebeldeNaoEncontradoException();
        }

        Rebelde rebelde1 = rebeldeOptional1.get();
        Rebelde rebelde2 = rebeldeOptional2.get();

        if(rebelde1.getTraidor() || rebelde2.getTraidor()){
            throw new NegociacaoComTraidorException();
        }

        if(!ehEquivalente(conjunto1, conjunto2)){
            throw new TrocaNaoEquivalenteException();
        }

        adicionarItens(rebelde1, conjunto2);
        removerItens(rebelde1, conjunto1);
        adicionarItens(rebelde2, conjunto1);
        removerItens(rebelde2, conjunto2);

        rebeldeRepository.save(rebelde1);
        rebeldeRepository.save(rebelde2);
    }

    private boolean ehEquivalente(ConjuntoDeItens conjunto1, ConjuntoDeItens conjunto2) {
        return getTotalPontos(conjunto1).equals(getTotalPontos(conjunto2));
    }

    private void adicionarItens(Rebelde rebelde, ConjuntoDeItens itens){
        ConjuntoDeItens inventario = rebelde.getInventario();

        inventario.setArma(inventario.getArma() + itens.getArma());
        inventario.setMunicao(inventario.getMunicao() + itens.getMunicao());
        inventario.setAgua(inventario.getAgua() + itens.getAgua());
        inventario.setComida(inventario.getComida() + itens.getComida());
    }

    private void removerItens(Rebelde rebelde, ConjuntoDeItens itens){
        ConjuntoDeItens inventario = rebelde.getInventario();

        inventario.setArma(inventario.getArma() - itens.getArma());
        inventario.setMunicao(inventario.getMunicao() - itens.getMunicao());
        inventario.setAgua(inventario.getAgua() - itens.getAgua());
        inventario.setComida(inventario.getComida() - itens.getComida());
    }

    private Integer getTotalPontos(ConjuntoDeItens conjunto){
        return conjunto.getMunicao() * 4 +
               conjunto.getArma() * 3 +
               conjunto.getAgua() * 2 +
               conjunto.getComida();
    }

    private void incremDenuncias(Rebelde rebelde){
        int denuncias = rebelde.getDenuncias();
        denuncias ++;
        rebelde.setDenuncias(denuncias);
        if(denuncias == 3){
            rebelde.setTraidor(true);
        }
    }
}
