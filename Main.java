package vendas;

public class Main {
    public static void main(String[] args) {
        Maquina maquina = new Maquina();
        maquina.loadFromFile();
        maquina.menu_cliente();
        maquina.scan_close();
        maquina.saveToFile();
    }
}
