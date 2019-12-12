package com.projectx.starwars.rebelde;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/rebelde")
public class RebeldeController {

    @Autowired
    private RebeldeService rebeldeService;

    @PostMapping
    public ResponseEntity<Rebelde> save(@Valid @RequestBody Rebelde rebelde){
        Rebelde reb = this.rebeldeService.save(rebelde);
        return ResponseEntity.ok(reb);
    }

    @PostMapping("/{id}/denunciar")
    public ResponseEntity<Boolean> denunciar(@PathVariable("id") Long id){
        try{
            Boolean ehTraidor = this.rebeldeService.denunciar(id);
            return ResponseEntity.ok(ehTraidor);
        }catch (RebeldeNaoEncontradoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/negociar")
    public ResponseEntity negociar(@Valid @RequestBody Negociacao negociacao){
        try{
            this.rebeldeService.negociar(negociacao.getId1(),negociacao.getId2(),
                    negociacao.getConjunto1(), negociacao.getConjunto2());
            return ResponseEntity.ok("");
        } catch (RebeldeNaoEncontradoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (TrocaNaoEquivalenteException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (NegociacaoComTraidorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
