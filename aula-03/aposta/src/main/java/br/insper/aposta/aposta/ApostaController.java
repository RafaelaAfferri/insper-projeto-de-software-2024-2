package br.insper.aposta.aposta;

import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aposta")
public class ApostaController {

    @Autowired
    private ApostaService apostaService;

    @GetMapping()
    public List<Aposta> listar(@RequestParam(required = false) String status) {
        if (status != null) {
            return apostaService.listar(status);
        }
        return apostaService.listar(null);
    }

    @PostMapping
    public void salvar(@RequestBody Aposta aposta) {
        apostaService.salvar(aposta);
    }

    @GetMapping("/{id}")
    public Aposta checar(@PathVariable String id) {
        return apostaService.checar(id);
    }


}
