package Vendas;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Saldo saldo = new Saldo();
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

    
    public ArrayList<Transacao> getHistorico() {
        return historico;
    }

    public void setHistorico(ArrayList<Transacao> historico) {
        this.historico = historico;
    }

  public void addTransacao(Transacao compra, ArrayList<Transacao> historico){
        historico.add(compra);
    }

    public void limpar_historico(ArrayList<Transacao> historico){

        historico.clear();
        saldo.setDinheiroTotal(0);
        System.out.println("\nHistorico Limpo");
    }

    public void listar_historico(ArrayList<Transacao> historico){
        if(historico.isEmpty()){
            System.out.println("\nHistorico vazio");
            return;
        }
        System.out.println("\nHistorico:");
        for (Transacao transacao : historico){
            System.out.println("Nome: " + transacao.getNome() + ", Preco: " + transacao.getPreco() + "€, Data e Hora: " + transacao.getData());
        }

    }


}
