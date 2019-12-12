package com.projectx.starwars;

import com.projectx.starwars.rebelde.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class RebeldeServiceTest {

    @TestConfiguration
    static class RebeldeServiceTestContextConfiguration {
        @Bean
        public RebeldeService rebeldeService() {
            return new RebeldeService();
        }
    }

    @Autowired
    private RebeldeService rebeldeService;

    @MockBean
    private RebeldeRepository rebeldeRepository;

    @Before
    public void setUp() {
        Rebelde joao = new Rebelde(1L, "Joao", 15, "M", 0, false,
                new Localizacao("Base A", 150.0, 53.5),
                new ConjuntoDeItens(1, 2, 4, 5));

        Rebelde maria = new Rebelde(2L, "Maria", 15, "M", 0, false,
                new Localizacao("Base A", 43.0, 64.5),
                new ConjuntoDeItens(3, 0, 2, 1));

        Mockito.when(rebeldeRepository.findById(joao.getId())).thenReturn(Optional.of(joao));
        Mockito.when(rebeldeRepository.findById(maria.getId())).thenReturn(Optional.of(maria));
    }

    @Test
    public void whenFoundAndValid_thenNegociarShouldUpdateInventario()
            throws NegociacaoComTraidorException, RebeldeNaoEncontradoException, TrocaNaoEquivalenteException {
        Long id1 = 1L;
        Long id2 = 2L;

        ConjuntoDeItens c1 = new ConjuntoDeItens(0, 1, 0, 0);
        ConjuntoDeItens c2 = new ConjuntoDeItens(0, 0, 1, 1);

        this.rebeldeService.negociar(id1, id2, c1, c2);

        Rebelde r1 = rebeldeRepository.findById(id1).get();
        Rebelde r2 = rebeldeRepository.findById(id2).get();

        ConjuntoDeItens i1 = new ConjuntoDeItens(1, 1, 5, 6);
        ConjuntoDeItens i2 = new ConjuntoDeItens(3, 1, 1, 0);

        assert(r1.getInventario().equals(i1));
        assert(r2.getInventario().equals(i2));
    }

    @Test(expected = TrocaNaoEquivalenteException.class)
    public void whenPontosNotEquivalent_thenThrowNaoEquivalenteException()
            throws NegociacaoComTraidorException, RebeldeNaoEncontradoException, TrocaNaoEquivalenteException {
        Long id1 = 1L;
        Long id2 = 2L;

        ConjuntoDeItens c1 = new ConjuntoDeItens(1, 0, 0, 0);
        ConjuntoDeItens c2 = new ConjuntoDeItens(0, 0, 1, 1);

        this.rebeldeService.negociar(id1, id2, c1, c2);
    }
}
