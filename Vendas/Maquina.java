package Vendas;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Maquina implements Serializable {
    private static final long serialVersionUID = 1L;

    Maquina() {
        this.leitor = new Scan();
        this.transacao = new Transacao("", 0.0, "");
    }

    private ArrayList<Transacao> historico = new ArrayList<Transacao>();
    private ArrayList<Chocolate> chocolates = new ArrayList<Chocolate>();
    private ArrayList<Sandes> sandess = new ArrayList<Sandes>();
    private ArrayList<Refrigerante> refrigerantes = new ArrayList<Refrigerante>();
    private DecimalFormat decimal = new DecimalFormat("0.00");
    private transient Scan leitor;
    private Transacao transacao;
    private Saldo saldo = new Saldo();
    private transient LocalDateTime dataHoraAtual = LocalDateTime.now();
    private transient DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private transient String dataHoraFormatada = dataHoraAtual.format(formatador);
    // metodos auxiliares
    public void saveToFile() {
        File stock = new File("stock.dat");
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

        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new      FileOutputStream(stock));
            oos.writeObject(this);
            oos.close();// Serializa todo o objeto Maquina
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
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(stock));
            Maquina maquina = (Maquina) ois.readObject();
            this.chocolates = maquina.chocolates;
            this.refrigerantes = maquina.refrigerantes;
            this.sandess = maquina.sandess;
            this.historico = maquina.historico;
            this.saldo.setDinheiroTotal(maquina.saldo.getDinheiroTotal());
            this.transacao.setHistorico(maquina.transacao.getHistorico());
            System.out.println("Máquina carregada com sucesso do arquivo " + stock.getName());
            ois.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

  /*   public void addTransacao(Produto produto, ArrayList<Transacao> historico){
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = dataHoraAtual.format(formatador);
        Transacao transacao = new    Transacao(produto.getNome(),produto.getPreco(),dataHoraFormatada);
        historico.add(transacao);
    }
    */
    public void list_produtos(int tipo ) {
        if(tipo == 1){
            System.out.println("\nChocolates: ");
            for(Chocolate chocolate : chocolates){

                chocolate.getDetalhes();
                System.out.println(" ");
            }
        }
        else if(tipo == 2) {
            System.out.println("\nRefrigerantes: ");
            for(Refrigerante refrigerante : refrigerantes){
                refrigerante.getDetalhes();
                System.out.println(" ");
            }
        }
        else {
            System.out.println("\nSandes: ");
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
            System.out.println("\nInsira a palavra-passe");
            System.out.print("-> ");
            String pass = leitor.scan_String();
            if(pass.equals(password)) {
                menu_colaborador();
                return;
            }
            count++;
            System.out.println("");
            System.out.println( 3 - count + " tentativas");
        }

    }

    public void adicionar_stock() {
        if (chocolates.size() + refrigerantes.size() + sandess.size() == 45) {
            System.out.println("\nLimite de produtos atingido");
            return;
        }
        int categoria;
        while (true) {
            System.out.println("\nEscolha o produto a adicionar: ");
            System.out.println("1 -> Chocolate ");
            System.out.println("2 -> Refrigerantes ");
            System.out.println("3 -> Sandes");
            System.out.print("-> ");
            categoria = leitor.scan_Int();
            if (categoria >= 1 || categoria <= 3) break;
        }
        System.out.println("\nEscreva o nome do produto: ");
        System.out.print("-> ");
        String nome = leitor.scan_String();
        System.out.println("preco: ");
        System.out.print("-> ");
        double preco = leitor.scan_Double();
        System.out.println("validade: ");
        System.out.print("-> ");
        String validade = leitor.scan_String();
        System.out.println("tipo: ");
        System.out.print("-> ");
        String tipo =  leitor.scan_String();
        System.out.println("Escreva o nome do marca: ");
        System.out.print("-> ");
        String marca =  leitor.scan_String();
        if (categoria == 1) {
            Chocolate chocolate = new Chocolate(chocolates.size()+1, nome, preco, validade, tipo, marca);
            chocolates.add(chocolate);
            saveToFile();
            System.out.print("\nO chocolate -> ");
            chocolate.getDetalhes();
            System.out.print(" foi adicionado");
        } else if (categoria == 2) {
            Refrigerante refrigerante = new Refrigerante(refrigerantes.size()+1, nome, preco, validade, tipo, marca);
            refrigerantes.add(refrigerante);
            saveToFile();
            System.out.print("\nO refrigerante -> ");
            refrigerante.getDetalhes();
            System.out.print(" foi adicionado");
        } else {
            Sandes sandes = new Sandes(sandess.size()+1, nome, preco, validade, tipo, marca);
            sandess.add(sandes);
            saveToFile();
            System.out.print("\nO refrigerante -> ");
            sandes.getDetalhes();
            System.out.print(" foi adicionado");
        }
        System.out.println("\n\nQuer adicionar mais produtos? 1-> Sim, 2 -> Nao");
        System.out.print("-> ");
        categoria = leitor.scan_Int();
        if(categoria == 1){
            adicionar_stock();
        }

    }

    public void remove_all(){
        chocolates.clear();
        sandess.clear();
        refrigerantes.clear();
        saveToFile();
        System.out.println("\nProdutos removidos");
    }

    public void remover_stock(){
        int categoria;
        while(true) {
            System.out.println("\nEscolha o produto a remover:");
            System.out.println("1 -> Chocolates");
            System.out.println("2 -> Refrigerantes");
            System.out.println("3 -> Sandes");
            System.out.println("4 -> Sair");
            System.out.print("-> ");
            categoria = leitor.scan_Int();
            if(categoria == 1){
                if(!chocolates.isEmpty()) {
                    break;
                }
                else System.out.println("\nLista vazia");
            }
            else if (categoria == 2) {
                if(!refrigerantes.isEmpty()) {
                    break;
                }
                else System.out.println("\nLista vazia");

            }
            else if (categoria == 3){
                if(!sandess.isEmpty()) {
                    break;
                }
                else System.out.println("\nLista vazia");
            }
            else if (categoria == 4){
                return;
            }
            else {
                System.out.println("\nDigite um numero correto");
            }
        }
        int escolha;
        while(true) {
            list_produtos(categoria);
            System.out.println("\nEscolha a referencia do produto que quer retirar: ");
            System.out.print("-> ");
            escolha = leitor.scan_Int();
            if (categoria == 1 && (escolha > chocolates.size() || escolha <= 0)) {
                System.out.println("\nEscolha uma referencia valida");
            }
            else if (categoria == 2 && (escolha > refrigerantes.size() || escolha <= 0)) {
                System.out.println("\nEscolha uma referencia valida");
            }
            else if (categoria == 3 && (escolha > sandess.size() || escolha <= 0)) {
                System.out.println("\nEscolha uma referencia valida");
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
        System.out.println("\nQuer remover mais produtos? 1-> Sim, 2 -> Nao");
        System.out.print("-> ");
        escolha = leitor.scan_Int();
        if(escolha == 1){
            remover_stock();
        }
    }
/* 
    public void limpar_historico(){

        historico.clear();
        saldo.setDinheiroTotal(0);
        saveToFile();
        System.out.println("\nHistorico Limpo");
    }
/* 
    public void listar_historico(){
        if(historico.isEmpty()){
            System.out.println("\nHistorico vazio");
            return;
        }
        System.out.println("\nHistorico:");
        for (Transacao transacao : historico){
            System.out.println("Nome: " + transacao.getNome() + ", Preco: " + transacao.getPreco() + "€, Data e Hora: " + transacao.getData());
        }

    }
    */
    public void menu_colaborador(){
        int escolha = 0;
        while(escolha != 8){
            System.out.println("\nMENU COLABORADOR");
            System.out.println("1 -> ADICIONAR PRODUTO");
            System.out.println("2 -> REMOVER PRODUTO");
            System.out.println("3 -> VISUALIZAR HISTORICO");
            System.out.println("4 -> VENDAS TOTAIS");
            System.out.println("5 -> LIMPAR PRODUTOS");
            System.out.println("6 -> LIMPAR HISTORICO");
            System.out.println("7 -> SHOW PRODUTOS");
            System.out.println("8 -> SAIR");
            System.out.print("-> ");
            escolha = leitor.scan_Int();
            switch(escolha){
                case 1:
                    adicionar_stock();
                    break;
                case 2:
                    remover_stock();
                    break;
                case 3:
                    transacao.listar_historico(historico);
                    break;
                case 4:
                    System.out.println("\nTotal do valor das vendas: " + saldo.getDinheiroTotal() + "€");
                    break;
                case 5:
                    remove_all();
                    break;
                case 6:
                    transacao.limpar_historico(historico);
                    break;
                case 7:
                    show_produtos();
                case 8:
                    break;
                default:
                    System.out.println("\nNumero digitado incorreto");
                    break;


            }
        }
    }

    public void show_produtos(){
        list_produtos(1);
        list_produtos(2);
        list_produtos(3);
    }


    //métodos cliente
    public void compra_produto() {
        int categoria;
        while(true) {

            System.out.println("\nEscolha o produto a comprar:");
            System.out.println("1 -> Chocolate");
            System.out.println("2 -> Refrigerante");
            System.out.println("3 -> Sandes");
            System.out.println("4 -> Sair");
            System.out.print("-> ");
            categoria = leitor.scan_Int();
            if(categoria == 1){
                if(!chocolates.isEmpty()) {
                    break;
                }
                else System.out.println("\nLista vazia");
            }
            else if (categoria == 2) {
                if(!refrigerantes.isEmpty()) {
                    break;
                }
                else System.out.println("\nLista vazia");

            }
            else if (categoria == 3){
                if(!sandess.isEmpty()) {
                    break;
                }
                else System.out.println("\nLista vazia");
            }
            else if (categoria == 4){
                return;
            }
            else {
                System.out.println("\nDigite um numero correto");
            }
        }
        int escolha;
        while(true) {
            list_produtos(categoria);
            System.out.println("\nEscolha a referencia do produto: ");
            System.out.print("-> ");
            escolha = leitor.scan_Int();
            if (categoria == 1 && (escolha > chocolates.size() || escolha <= 0)) {
                System.out.println("\nEscolha uma referencia valida");
            }
            else if (categoria == 2 && (escolha > refrigerantes.size() || escolha <= 0)) {
                System.out.println("\nEscolha uma referencia valida");
            }
            else if (categoria == 3 && (escolha > sandess.size() || escolha <= 0)) {
                System.out.println("\nEscolha uma referencia valida");
            }
            else break;
        }
        if (categoria == 1) {
            if(saldo.getSaldo() >= chocolates.get(escolha-1).getPreco()) {
                
                Transacao compra = new Transacao(chocolates.get(escolha-1).getNome(),chocolates.get(escolha-1).getPreco(),dataHoraFormatada);
                transacao.addTransacao(compra,historico);
                saldo.remover_saldo(chocolates.get(escolha-1).getPreco());
                saldo.addDinheiroTotal(chocolates.get(escolha-1).getPreco());
                System.out.println("\nTroco de " + decimal.format(saldo.getSaldo()) + "€");
                saldo.setSaldo(0);
                chocolates.remove(escolha - 1);
                saveToFile();
                for (int i = escolha-1; i < chocolates.size(); i++) {
                    chocolates.get(i).setReferencia(chocolates.get(i).getReferencia() - 1);
                }
            }
            else {
                System.out.println("\nNão tem saldo suficiente");
            }
        } else if (categoria == 2) {
            if(saldo.getSaldo() >=  refrigerantes.get(escolha-1).getPreco()) {
                Transacao compra = new Transacao(refrigerantes.get(escolha-1).getNome(),refrigerantes.get(escolha-1).getPreco(),dataHoraFormatada);
                transacao.addTransacao(compra,historico);
                saldo.remover_saldo(refrigerantes.get(escolha-1).getPreco());
                saldo.addDinheiroTotal(refrigerantes.get(escolha-1).getPreco());
                System.out.println("\nTroco de " + decimal.format(saldo.getSaldo()) + "€");
                saldo.setSaldo(0);
                refrigerantes.remove(escolha - 1);
                saveToFile();
                for (int i = escolha-1; i < refrigerantes.size(); i++) {
                    refrigerantes.get(i).setReferencia(refrigerantes.get(i).getReferencia() - 1);
                }
            }
            else{
                System.out.println("\nNão tem saldo suficiente");
            }
        } else {
            if(saldo.getSaldo() >=  sandess.get(escolha-1).getPreco()) {
                Transacao compra = new Transacao(sandess.get(escolha-1).getNome(),sandess.get(escolha-1).getPreco(),dataHoraFormatada);
                transacao.addTransacao(compra,historico);
                saldo.remover_saldo(sandess.get(escolha-1).getPreco());
                saldo.addDinheiroTotal(sandess.get(escolha-1).getPreco());
                System.out.println("\nTroco de " + decimal.format(saldo.getSaldo()) + "€");
                saldo.setSaldo(0);
                sandess.remove(escolha - 1);
                saveToFile();
                for (int i = escolha-1; i < sandess.size(); i++) {
                    sandess.get(i).setReferencia(sandess.get(i).getReferencia() - 1);
                }
            }
            else {
                System.out.println("\nNão tem saldo suficiente");
            }
        }
    }

    public void menu_cliente() {
        int escolha = 0;
        while (escolha != 4) {
            System.out.println("\nMENU CLIENTE");
            System.out.println("SALDO : " + decimal.format(saldo.getSaldo()) + "€");
            System.out.println("1 -> ADD SALDO");
            System.out.println("2 -> COMPRAR PRODUTO");
            System.out.println("3 -> ÁEREA COLABORADOR");
            System.out.println("4 -> SAIR ");
            System.out.print("-> ");
            escolha = leitor.scan_Int();
            switch(escolha){
                case 1:
                    saldo.adicionar_saldo();
                    break;
                case 2:
                    compra_produto();
                    break;
                case 3:
                    check_colaborador();
                    break;
                case 4:
                    leitor.scan_close();
                    break;
                default:
                    System.out.println("\nNumero digitado incorreto");
                    break;
            }
        }
    }
}
