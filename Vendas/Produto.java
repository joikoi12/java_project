package Vendas;

import java.io.Serializable;

public abstract class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int referencia;
    protected String nome;
    protected double preco;
    protected String validade;
    
    Produto(int referencia, String nome, double preco, String validade, String tipo, String marca) {
        this.referencia = referencia;
        this.nome = nome;
        this.preco = preco;
        this.validade = validade;
        this.tipo = tipo;
        this.marca = marca;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    protected String tipo;
    protected String marca;

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    abstract public void getDetalhes();
}
