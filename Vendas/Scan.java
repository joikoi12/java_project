package Vendas;

import java.util.Scanner;
import java.io.Serializable;

public class Scan implements Serializable {

  private static final long serialVersionUID = 1L;
  protected transient Scanner scanner;

    public Scan (){
      this.scanner = new Scanner(System.in);
    }

    public String scan_String(){
     return scanner.nextLine();
    }

  public int scan_Int(){
    return Integer.parseInt(scanner.nextLine());
  }

  public double scan_Double(){
   return Double.parseDouble(scanner.nextLine());
  }


    public void scan_close(){
      scanner.close();
    }



}