package com.projectx.starwars.rebelde;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation("Adiciona um rebelde")
    @PostMapping
    public ResponseEntity<Rebelde> save(@Valid @RequestBody Rebelde rebelde){
        Rebelde reb = this.rebeldeService.save(rebelde);
        return ResponseEntity.ok(reb);
    }


    @ApiOperation("Denuncia um rebelde como traidor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Rebelde não encontrado")
    })
    @PostMapping("/{id}/denunciar")
    public ResponseEntity<Boolean> denunciar(@PathVariable("id") Long id){
        try{
            Boolean ehTraidor = this.rebeldeService.denunciar(id);
            return ResponseEntity.ok(ehTraidor);
        }catch (RebeldeNaoEncontradoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


    @ApiOperation("Realiza negocição entre dois rebeldes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Rebelde não encontrado"),
            @ApiResponse(code = 403, message = "Negociação não equivalente")
    })
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
