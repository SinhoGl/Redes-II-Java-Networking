public class Pergunta {
    private final String enunciado;
    private final String[] opcoes;
    private final int respostaCorreta;

    public Pergunta(String enunciado, String[] opcoes, int respostaCorreta) {
        this.enunciado = enunciado;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }
     public String getEnunciado() {
        return enunciado;
     }

     public String[] getOpcoes() {
        return opcoes;
     }

     public boolean isRespostaCorreta(int resposta) {
        return resposta == respostaCorreta;
     }
}