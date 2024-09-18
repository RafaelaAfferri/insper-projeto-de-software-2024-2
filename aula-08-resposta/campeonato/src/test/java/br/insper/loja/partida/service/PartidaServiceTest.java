package br.insper.loja.partida.service;


import br.insper.loja.partida.dto.EditarPartidaDTO;
import br.insper.loja.partida.dto.RetornarPartidaDTO;
import br.insper.loja.partida.dto.SalvarPartidaDTO;
import br.insper.loja.partida.exception.PartidaNaoEncontradaException;
import br.insper.loja.partida.model.Partida;
import br.insper.loja.partida.repository.PartidaRepository;
import br.insper.loja.time.exception.TimeNaoEncontradoException;
import br.insper.loja.time.model.Time;
import br.insper.loja.time.service.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class PartidaServiceTest {

    @InjectMocks
    private PartidaService partidaService;

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private TimeService timeService;


    @Test
    public void testCadastrarPartida() {

        SalvarPartidaDTO salvarPartidaDTO = new SalvarPartidaDTO();
        salvarPartidaDTO.setMandante(1);
        salvarPartidaDTO.setVisitante(2);


        Time mandante = new Time();
        mandante.setId(1);
        mandante.setNome("Time Mandante");

        Time visitante = new Time();
        visitante.setId(2);
        visitante.setNome("Time Visitante");

        Mockito.when(timeService.getTime(1)).thenReturn(mandante);
        Mockito.when(timeService.getTime(2)).thenReturn(visitante);

        Partida partida = new Partida();
        partida.setMandante(mandante);
        partida.setVisitante(visitante);
        partida.setStatus("AGENDADA");

        Mockito.when(partidaRepository.save(Mockito.any(Partida.class))).thenReturn(partida);

        RetornarPartidaDTO resultado = partidaService.cadastrarPartida(salvarPartidaDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("AGENDADA", resultado.getStatus());
        Assertions.assertEquals("Time Mandante", resultado.getNomeMandante());
        Assertions.assertEquals("Time Visitante", resultado.getNomeVisitante());
    }

    @Test
    public void testeListarParitdasWhenMandanteIsNull() {
        Time mandante = new Time();
        mandante.setIdentificador("time-1");
        mandante.setNome("time-1");

        Time visitante = new Time();
        visitante.setIdentificador("time-2");
        visitante.setNome("time-2");

        Time mandante2 = new Time();
        mandante2.setIdentificador("time-3");
        mandante2.setNome("time-3");

        Partida partida = new Partida();
        partida.setMandante(mandante);
        partida.setVisitante(visitante);
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(2);
        partida.setStatus("AGENDADA");
        partida.setId(1);

        Partida partida2 = new Partida();
        partida2.setMandante(mandante2);
        partida2.setVisitante(visitante);
        partida2.setPlacarMandante(1);
        partida2.setPlacarVisitante(2);
        partida2.setStatus("AGENDADA");
        partida2.setId(2);

        List<Partida> partidas = new ArrayList<>();
        partidas.add(partida);
        partidas.add(partida2);

        Mockito.when(partidaRepository.findAll()).thenReturn(partidas);

        List<RetornarPartidaDTO> resultado = partidaService.listarPartidas(null);

        Assertions.assertTrue(resultado.size() == 2);
        Assertions.assertEquals("time-1", resultado.get(0).getNomeMandante());
        Assertions.assertEquals("time-2", resultado.get(0).getNomeVisitante());

    }

    @Test
    public void testListarPartidasWhenMandanteIsNotNull(){
        Time mandante = new Time();
        mandante.setIdentificador("time-1");
        mandante.setNome("time-1");

        Time visitante = new Time();
        visitante.setIdentificador("time-2");
        visitante.setNome("time-2");

        Time mandante2 = new Time();
        mandante2.setIdentificador("time-3");
        mandante2.setNome("time-3");

        Partida partida = new Partida();
        partida.setMandante(mandante);
        partida.setVisitante(visitante);
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(2);
        partida.setStatus("AGENDADA");
        partida.setId(1);

        Partida partida2 = new Partida();
        partida2.setMandante(mandante2);
        partida2.setVisitante(visitante);
        partida2.setPlacarMandante(1);
        partida2.setPlacarVisitante(2);
        partida2.setStatus("AGENDADA");
        partida2.setId(2);

        Partida partida3 = new Partida();
        partida3.setMandante(mandante);
        partida3.setVisitante(mandante2);
        partida3.setPlacarMandante(1);
        partida3.setPlacarVisitante(2);
        partida3.setStatus("AGENDADA");
        partida3.setId(3);



        List<Partida> partidas = new ArrayList<>();
        partidas.add(partida);
        partidas.add(partida2);
        partidas.add(partida3);


        Mockito.when(partidaRepository.findAll()).thenReturn(partidas);

        List<RetornarPartidaDTO> resultado = partidaService.listarPartidas("time-1");

        Assertions.assertTrue(resultado.size() == 2);
        Assertions.assertEquals("time-1", resultado.get(0).getNomeMandante());
        Assertions.assertEquals("time-2", resultado.get(0).getNomeVisitante());
        Assertions.assertEquals(1, resultado.get(0).getPlacarMandante());
        Assertions.assertEquals(2, resultado.get(0).getPlacarVisitante());
        Assertions.assertEquals("AGENDADA", resultado.get(0).getStatus());
        //Assertions.assertEquals(1, resultado.get(0).getId());

    }

    @Test
    public void testEditarPartida() {
        Time mandante = new Time();
        mandante.setIdentificador("time-1");
        mandante.setNome("time-1");

        Time visitante = new Time();
        visitante.setIdentificador("time-2");
        visitante.setNome("time-2");

        Partida partida = new Partida();
        partida.setMandante(mandante);
        partida.setVisitante(visitante);
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(2);
        partida.setStatus("AGENDADA");
        partida.setId(1);

        EditarPartidaDTO dto = new EditarPartidaDTO();
        dto.setPlacarMandante(3);
        dto.setPlacarVisitante(4);

        Partida partidaEditada = new Partida();
        partidaEditada.setMandante(mandante);
        partidaEditada.setVisitante(visitante);
        partidaEditada.setPlacarMandante(3);
        partidaEditada.setPlacarVisitante(4);
        partidaEditada.setStatus("REALIZADA");
        partidaEditada.setId(1);


        Mockito.when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));
        Mockito.when(partidaRepository.save(partida)).thenReturn(partidaEditada);

        RetornarPartidaDTO resultado = partidaService.editarPartida(dto, 1);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("REALIZADA", resultado.getStatus());
        Assertions.assertEquals("time-1", resultado.getNomeMandante());
        Assertions.assertEquals("time-2", resultado.getNomeVisitante());
        Assertions.assertEquals(3, resultado.getPlacarMandante());
        Assertions.assertEquals(4, resultado.getPlacarVisitante());

    }

    @Test
    public void testGetPartidaWhenPartidaIsNull(){
        Mockito.when(partidaRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(PartidaNaoEncontradaException.class,
                () -> partidaService.getPartida(1));
    }



    @Test
    public void testGetPartidaWhenPartidaIsPresent(){
        Time mandante = new Time();
        mandante.setIdentificador("time-1");
        mandante.setNome("time-1");

        Time visitante = new Time();
        visitante.setIdentificador("time-2");
        visitante.setNome("time-2");

        Partida partida = new Partida();
        partida.setMandante(mandante);
        partida.setVisitante(visitante);
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(2);
        partida.setId(1);

        Mockito.when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));

        RetornarPartidaDTO resultado = partidaService.getPartida(1);

        Assertions.assertNotNull(resultado);

        Assertions.assertEquals("time-1", resultado.getNomeMandante());
        Assertions.assertEquals("time-2", resultado.getNomeVisitante());
        Assertions.assertEquals(1, resultado.getPlacarMandante());
        Assertions.assertEquals(2, resultado.getPlacarVisitante());
    }


}
