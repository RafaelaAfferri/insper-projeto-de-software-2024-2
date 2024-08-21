package br.insper.aposta.aposta;

public class ApostaNaoRealizada extends RuntimeException {

    public ApostaNaoRealizada(String mensagem) {
        super(mensagem);
    }

}
