package vendas;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Maquina implements Serializable {
    private static final long serialVersionUID = 1L;

    Maquina() {

    }

    protected ArrayList<Transacao> historico = new ArrayList<Transacao>();
    private ArrayList<Chocolate> chocolates = new ArrayList<Chocolate>();
    private ArrayList<Sandes> sandess = new ArrayList<Sandes>();
    private ArrayList<Refrigerante> refrigerantes = new ArrayList<Refrigerante>();
    private double saldo = 0;
    private double dinheiro_total_gasto;
    private DecimalFormat decimal = new DecimalFormat("0.00");
    private transient Scanner scanner;

    // metodos auxiliares

    public void scan_init(){
        scanner = new Scanner(System.in);
    }

    public void scan_close(){
        scanner.close();
    }

    public void saveToFile() {
        File stock = new File("C:/Users/amora/IdeaProjects/Java_project/stock.dat");
        // Verifica se o arquivo já existe
        if (!stock.exists()) {
            try {
                if (stock.createNewFile()) {
                    System.out.println("Arquivo criado com sucesso: " + stock.getName());
                } else {
                    System.out.println("Erro ao criar o arquivo: " + stock.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return; // Sai da função caso ocorra um erro ao criar o arquivo
            }
        }
        // Continua com a serialização e gravação dos dados
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(stock))) {
            oos.writeObject(this);  // Serializa todo o objeto Maquina
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        File stock = new File("stock.dat");
        // Verifica se o arquivo existe antes de tentar lê-lo
        if (!stock.exists()) {
            System.out.println("Arquivo não encontrado. Criando novo arquivo na próxima gravação.");
            return;
        }
        // Verifica se o arquivo tem dados antes de tentar ler
        if (stock.length() == 0) {
            System.out.println("O arquivo está vazio.");
            return;
        }
        // Tenta carregar o objeto do arquivo
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(stock))) {
            Maquina maquina = (Maquina) ois.readObject();
            this.chocolates = maquina.chocolates;
            this.refrigerantes = maquina.refrigerantes;
            this.sandess = maquina.sandess;
            this.historico = maquina.historico;
            this.dinheiro_total_gasto = maquina.dinheiro_total_gasto;
            System.out.println("Máquina carregada com sucesso do arquivo " + stock.getName());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void listar_historico(){
        if(historico.isEmpty()){
            System.out.println("Historico vazio");
            return;
        }
        System.out.println("Historico:");
        for (Transacao transacao : historico){
            System.out.println("Nome: " + transacao.getNome() + ", Preco: " + transacao.getPreco() + "€, Data e Hora: " + transacao.getData());
        }
        int escolha = 0;
        while(escolha != 1){
            System.out.println("\nPrima 1 para sair");
            System.out.print("-> ");
            escolha = scanner.nextInt();
            scanner.nextLine();
        }
    }

    public void addTransacao(Produto produto, ArrayList<Transacao> historico){
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = dataHoraAtual.format(formatador);
        Transacao transacao = new Transacao(produto.getNome(),produto.getPreco(),dataHoraFormatada);
        historico.add(transacao);
    }

    public void list_produtos(int tipo ) {
        if(tipo == 1){
            System.out.println("Chocolates: ");
            for(Chocolate chocolate : chocolates){

                chocolate.getDetalhes();
                System.out.println(" ");
            }
        }
        else if(tipo == 2) {
            System.out.println("Refrigerantes: ");
            for(Refrigerante refrigerante : refrigerantes){
                refrigerante.getDetalhes();
                System.out.println(" ");
            }
        }
        else {
            System.out.println("Sandes: ");
            for(Sandes sandes : sandess){

                sandes.getDetalhes();
                System.out.println(" ");
            }
        }


    }

    // metodos colaborador

    public void check_colaborador(){
        int count = 0;
        while(count < 3) {
            String password = "colaborador";
            System.out.println("Insira a palavra-passe");
            System.out.print("-> ");
            String pass = scanner.nextLine();
            if(pass.equals(password)) {
                menu_colaborador();
                return;
            }
            count++;
            System.out.println(3 - count + " tentativas");
        }

    }

    public void adicionar_stock() {
        if (chocolates.size() + refrigerantes.size() + sandess.size() == 45) {
            System.out.println("Limite de produtos atingido");
            return;
        }
        while (true) {
            System.out.println("Escolha o produto a adicionar: ");
            System.out.println("1 -> Chocolate ");
            System.out.println("2 -> Refrigerantes ");
            System.out.println("3 -> Sandes");
            System.out.print("-> ");
            int categoria = scanner.nextInt();
            scanner.nextLine();
            adicionar_produto(categoria);
            System.out.println("\nQuer adicionar mais produtos? 1-> Sim, 2 -> Nao");
            System.out.print("-> ");
            categoria = scanner.nextInt();
            scanner.nextLine();

            if (categoria == 2) break;

        }
    }

    public void adicionar_produto(int escolha) {
        System.out.println("Escreva o nome do produto: ");
        System.out.print("-> ");
        String nome = scanner.nextLine();
        System.out.println("preco: ");
        System.out.print("-> ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.println("validade: ");
        System.out.print("-> ");
        String validade = scanner.nextLine();
        System.out.println("tipo: ");
        System.out.print("-> ");
        String tipo = scanner.nextLine();
        System.out.println("Escreva o nome do marca: ");
        System.out.print("-> ");
        String marca = scanner.nextLine();
        if (escolha == 1) {
            Chocolate chocolate = new Chocolate(chocolates.size()+1, nome, preco, validade, tipo, marca);
            chocolates.add(chocolate);
            saveToFile();
            System.out.print("O chocolate -> ");
            chocolate.getDetalhes();
            System.out.print(" foi adicionado");
        } else if (escolha == 2) {
            Refrigerante refrigerante = new Refrigerante(refrigerantes.size()+1, nome, preco, validade, tipo, marca);
            refrigerantes.add(refrigerante);
            saveToFile();
            System.out.print("O refrigerante -> ");
            refrigerante.getDetalhes();
            System.out.print(" foi adicionado");
        } else {
            Sandes sandes = new Sandes(sandess.size()+1, nome, preco, validade, tipo, marca);
            sandess.add(sandes);
            saveToFile();
            System.out.print("O refrigerante -> ");
            sandes.getDetalhes();
            System.out.print(" foi adicionado");
        }


    }

    public void remove_all(){
        chocolates.clear();
        sandess.clear();
        refrigerantes.clear();
        saveToFile();
    }

    public void remover_stock(){
        int categoria;
        while(true) {
            System.out.println("Escolha o produto a remover:");
            System.out.println("1 -> Chocolates");
            System.out.println("2 -> Refrigerantes");
            System.out.println("3 -> Sandes");
            System.out.println("4 -> Sair");
            System.out.print("-> ");
            categoria = scanner.nextInt();
            scanner.nextLine();
            if(categoria == 1){
                if(!chocolates.isEmpty()) {
                    break;
                }
                else System.out.println("Lista vazia");
            }
            else if (categoria == 2) {
                if(!refrigerantes.isEmpty()) {
                    break;
                }
                else System.out.println("Lista vazia");

            }
            else if (categoria == 3){
                if(!sandess.isEmpty()) {
                    break;
                }
                else System.out.println("Lista vazia");
            }
            else if (categoria == 4){
                return;
            }
            else {
                System.out.println("Digite um numero correto");
            }
        }
        int escolha;
        while(true) {
            list_produtos(categoria);
            System.out.println("\nEscolha a referencia do produto que quer retirar: ");
            System.out.print("-> ");
            escolha = scanner.nextInt();
            scanner.nextLine();
            if (categoria == 1 && (escolha > chocolates.size() || escolha <= 0)) {
                System.out.println("Escolha uma referencia valida");
            }
            else if (categoria == 2 && (escolha > refrigerantes.size() || escolha <= 0)) {
                System.out.println("Escolha uma referencia valida");
            }
            else if (categoria == 3 && (escolha > sandess.size() || escolha <= 0)) {
                System.out.println("Escolha uma referencia valida");
            }
            else break;
        }
        if(categoria==1){
            chocolates.remove(escolha-1);
            saveToFile();
            for (int i = escolha-1; i < chocolates.size(); i++) {
                chocolates.get(i).setReferencia(chocolates.get(i).getReferencia()-1);
            }
        }
        else if (categoria==2){
            refrigerantes.remove(escolha-1);
            saveToFile();
            for (int i = escolha-1; i < refrigerantes.size(); i++) {
                refrigerantes.get(i).setReferencia(refrigerantes.get(i).getReferencia()-1);
            }
        }
        else {
            sandess.remove(escolha-1);
            saveToFile();
            for (int i = escolha-1; i < sandess.size(); i++) {
                sandess.get(i).setReferencia(sandess.get(i).getReferencia()-1);
            }
        }
        System.out.println("\nQuer adicionar mais produtos? 1-> Sim, 2 -> Nao");
        System.out.print("-> ");
        escolha = scanner.nextInt();
        scanner.nextLine();
        if(escolha == 1){
            remover_stock();
        }
    }

    public void limpar_historico(){
        historico.clear();
    }

    public void menu_colaborador(){
        int escolha = 0;
        while(escolha != 7){
            System.out.println("1 -> ADICIONAR PRODUTO");
            System.out.println("2 -> REMOVER PRODUTO");
            System.out.println("3 -> VISUALIZAR HISTORICO");
            System.out.println("4 -> VENDAS TOTAIS");
            System.out.println("5 -> LIMPAR PRODUTOS");
            System.out.println("6 -> LIMPAR HISTORICO");
            System.out.println("7 -> SAIR");
            System.out.print("-> ");
            escolha = scanner.nextInt();
            scanner.nextLine();
            switch(escolha){
                case 1:
                    adicionar_stock();
                    break;
                case 2:
                    remover_stock();
                    break;
                case 3:
                    listar_historico();
                    break;
                case 4:
                    System.out.println("Total do valor das vendas: " + dinheiro_total_gasto + "€");
                    break;
                case 5:
                    remove_all();
                    break;
                case 6:
                    limpar_historico();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Numero digitado incorreto");
                    break;


            }
        }
    }

    // metodos cliente

    public void compra_produto() {
        int categoria;
        while(true) {
            System.out.println("Escolha o produto a comprar:");
            System.out.println("1 -> Chocolate");
            System.out.println("2 -> Refrigerante");
            System.out.println("3 -> Sandes");
            System.out.println("4 -> Sair");
            System.out.print("-> ");
            categoria = scanner.nextInt();
            scanner.nextLine();
            if(categoria == 1){
                if(!chocolates.isEmpty()) {
                    break;
                }
                else System.out.println("Lista vazia");
            }
            else if (categoria == 2) {
                if(!refrigerantes.isEmpty()) {
                    break;
                }
                else System.out.println("Lista vazia");

            }
            else if (categoria == 3){
                if(!sandess.isEmpty()) {
                    break;
                }
                else System.out.println("Lista vazia");
            }
            else if (categoria == 4){
                return;
            }
            else {
                System.out.println("Digite um numero correto");
            }
        }
        int escolha;
        while(true) {
            list_produtos(categoria);
            System.out.println("\nEscolha a referencia do produto: ");
            System.out.print("-> ");
            escolha = scanner.nextInt();
            scanner.nextLine();
            if (categoria == 1 && (escolha > chocolates.size() || escolha <= 0)) {
                System.out.println("Escolha uma referencia valida");
            }
            else if (categoria == 2 && (escolha > refrigerantes.size() || escolha <= 0)) {
                System.out.println("Escolha uma referencia valida");
            }
            else if (categoria == 3 && (escolha > sandess.size() || escolha <= 0)) {
                System.out.println("Escolha uma referencia valida");
            }
            else break;
        }
        if (categoria == 1) {
            if(saldo >= chocolates.get(escolha-1).getPreco()) {
                addTransacao(chocolates.get(escolha-1),historico);
                saldo -= chocolates.get(escolha-1).getPreco();
                dinheiro_total_gasto += chocolates.get(escolha-1).getPreco();
                System.out.println("Troco de " + decimal.format(saldo) + "€");
                saldo = 0;
                chocolates.remove(escolha - 1);
                saveToFile();
                for (int i = escolha-1; i < chocolates.size(); i++) {
                    chocolates.get(i).setReferencia(chocolates.get(i).getReferencia() - 1);
                }
            }
            else {
                System.out.println("Não tem saldo suficiente");
            }
        } else if (categoria == 2) {
            if(saldo >=  refrigerantes.get(escolha-1).getPreco()) {
                addTransacao(refrigerantes.get(escolha-1),historico);
                saldo -= refrigerantes.get(escolha-1).getPreco();
                dinheiro_total_gasto += refrigerantes.get(escolha-1).getPreco();
                System.out.println("Troco de " + decimal.format(saldo) + "€");
                saldo = 0;
                refrigerantes.remove(escolha - 1);
                saveToFile();
                for (int i = escolha-1; i < refrigerantes.size(); i++) {
                    refrigerantes.get(i).setReferencia(refrigerantes.get(i).getReferencia() - 1);
                }
            }
            else{
                System.out.println("Não tem saldo suficiente");
            }
        } else {
            if(saldo >=  sandess.get(escolha-1).getPreco()) {
                addTransacao(sandess.get(escolha-1),historico);
                saldo -= sandess.get(escolha-1).getPreco();
                dinheiro_total_gasto += sandess.get(escolha-1).getPreco();
                System.out.println("Troco de " + decimal.format(saldo) + "€");
                saldo = 0;
                sandess.remove(escolha - 1);
                saveToFile();
                for (int i = escolha-1; i < sandess.size(); i++) {
                    sandess.get(i).setReferencia(sandess.get(i).getReferencia() - 1);
                }
            }
            else {
                System.out.println("Não tem saldo suficiente");
            }
        }
    }

    public void adicionar_saldo() {
        while (true) {
            System.out.println("Adicione saldo: ");
            System.out.print("-> ");
            double valor = Double.parseDouble(scanner.nextLine());
            if(valor <= 0){
                System.out.println("Insira um valor correto");
            }
            else {
                saldo += valor;
                break;
            }
        }
    }

    public void menu_cliente() {
        scan_init();
        int escolha = 0;
        while (escolha != 4) {
            System.out.println("MENU");
            System.out.println("Saldo : " + decimal.format(saldo) + "€");
            System.out.println("1 -> ADD SALDO");
            System.out.println("2 -> COMPRAR PRODUTO");
            System.out.println("3 -> ÁEREA COLABORADOR");
            System.out.println("4 -> SAIR ");
            System.out.print("-> ");
            escolha = scanner.nextInt();
            scanner.nextLine();
            switch(escolha){
                case 1:
                    adicionar_saldo();
                    break;
                case 2:
                    compra_produto();
                    break;
                case 3:
                    check_colaborador();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Numero digitado incorreto");
                    break;
            }
        }
    }
}
