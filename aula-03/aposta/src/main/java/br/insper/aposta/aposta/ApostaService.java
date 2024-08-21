package br.insper.aposta.aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApostaService {

    @Autowired
    private ApostaRepository apostaRepository;

    public void salvar(Aposta aposta) {
        aposta.setId(UUID.randomUUID().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RetornarPartidaDTO> partida = restTemplate.getForEntity(
                "http://localhost:8080/partida/" + aposta.getIdPartida(),
                RetornarPartidaDTO.class);

        if (partida.getStatusCode().is2xxSuccessful())  {
            aposta.setStatus("REALIZADA");
            apostaRepository.save(aposta);
        }

    }

    public Aposta checar(String id){
        Aposta aposta= apostaRepository.findById(id).get();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RetornarPartidaDTO> partida = restTemplate.getForEntity(
                "http://localhost:8080/partida/" + aposta.getIdPartida(),
                RetornarPartidaDTO.class);

        if (partida.getStatusCode().is2xxSuccessful())  {
            if (Objects.equals(partida.getBody().getStatus(), "REALIZADA")){
                if(partida.getBody().getPlacarMandante()>partida.getBody().getPlacarVisitante()) {
                    if (Objects.equals(aposta.getResultado(), "VITORIA_MANDANTE")) {
                        aposta.setStatus("GANHOU");
                    } else {
                        aposta.setStatus("PERDEU");
                    }
                }
                if(partida.getBody().getPlacarMandante()<partida.getBody().getPlacarVisitante()) {
                    if (Objects.equals(aposta.getResultado(), "VITORIA_VISITANTE")) {
                        aposta.setStatus("GANHOU");
                    } else {
                        aposta.setStatus("PERDEU");
                    }
                }
                if(Objects.equals(partida.getBody().getPlacarMandante(), partida.getBody().getPlacarVisitante())) {
                    if (Objects.equals(aposta.getResultado(), "EMPATE")) {
                        aposta.setStatus("GANHOU");
                    } else {
                        aposta.setStatus("PERDEU");
                    }
                }
            }
            return aposta;
        }
        return null;
    }

    public List<Aposta> listar() {
        return apostaRepository.findAll();
    }

}
