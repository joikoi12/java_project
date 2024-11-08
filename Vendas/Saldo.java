package Vendas;

import java.io.Serializable;

public class Saldo implements Serializable {
  
   protected double saldo = 0;
   private Scan leitor = new Scan();
   protected double dinheiro_total_gasto; 
    
    Saldo(){
    }
  
    public void adicionar_saldo() {
        while (true) {
            System.out.println("\nAdicione saldo: ");
            System.out.print("-> ");
            double valor = leitor.scan_Double();
            if(valor <= 0){
                System.out.println("\nInsira um valor correto");
            }
            else {
                saldo += valor;
                break;
            }
        }
    }

    public void remover_saldo(double saldo_gasto) {
       saldo -= saldo_gasto;
    }

    
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getDinheiroTotal() {
        return dinheiro_total_gasto;
    }

    public void setDinheiroTotal(double dinheiro_total_gasto) {
        this.dinheiro_total_gasto = dinheiro_total_gasto;
    }

    public void addDinheiroTotal(double dinheiro) {
        dinheiro_total_gasto += dinheiro;
    }

    



}