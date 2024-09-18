package time.service;


import br.insper.loja.time.model.Time;
import br.insper.loja.time.repository.TimeRepository;
import br.insper.loja.time.service.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TimeServiceTests {

    @InjectMocks
    private TimeService timeService;

    @Mock
    private TimeRepository timeRepository;

    @Test
    public void listarTimesWhenEstadoIsNull(){

        //preparaçao
        Mockito.when(timeRepository.findAll()).thenReturn(new ArrayList<>());

        //chamada do codigo a ser testado
        List<Time> times = timeService.listarTimes(null);

        //verificacao do resultado
        Assertions.assertTrue(times.isEmpty());

    }

    @Test
    public void listarTimesWhenEstadoIsNotNull(){

        //preparaçao
        String estado = "Teste";
        Mockito.when(timeRepository.findByEstado(estado)).thenReturn(new ArrayList<>());

        //chamada do codigo a ser testado
        List<Time> times = timeService.listarTimes(null);

        //verificacao do resultado
        Assertions.assertTrue(times.isEmpty());

    }

}
