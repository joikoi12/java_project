package vendas;

import java.io.Serializable;

public class Chocolate extends Produto implements Serializable {

    Chocolate(int referencia, String nome, double preco, String validade, String tipo, String marca ){
        super(referencia,nome,preco,validade,tipo,marca);

    }
    @Override
    public void getDetalhes() {
        System.out.print("Referencia: " + referencia + ", Nome: " + nome + ", Preco: " + preco + ", Validade: " + validade + ", Tipo: " + tipo + ", Marca: " + marca );
    }

}