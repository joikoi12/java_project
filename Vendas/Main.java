package Vendas;

// javac Vendas/*.java
// java -cp . Vendas.Main

public class Main {
    public static void main(String[] args) {
        Maquina maquina = new Maquina();
        maquina.loadFromFile();
        maquina.menu_cliente();
        maquina.saveToFile();
    }
}
