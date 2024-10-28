package vendas;
import java.io.Serializable;
import java.util.ArrayList;

public class Transacao  implements Serializable {

    Transacao(String nome,double preco, String data){
        this.nome = nome;
        this.preco = preco;
        this.data = data;
    }
    protected ArrayList<Transacao> historico = new ArrayList<Transacao>();
    protected String nome;
    protected double preco;
    protected String data;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



}
