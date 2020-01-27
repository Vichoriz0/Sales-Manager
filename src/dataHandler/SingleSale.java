package dataHandler;

import java.util.ArrayList;
import java.util.Iterator;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class SingleSale {
	private int total;
	private int amount;
	private String detail;
	
	public SingleSale(int total, int amount, String detail) {
		this.total = total;
		this.amount = amount;
		this.detail = detail.replaceAll("[\n\r]*$", "");
	}
	
	//Imprime la informacion de la venta individual
	public void printSingleSale() {
		System.out.println(this.total + " | " + this.amount + " | " + this.detail);
	}
	
	//Entrega el total neto dado un ArrayList del tipo SingleSale de entrada
	public static int getAllMoney(ArrayList<SingleSale> al) {
		int total = 0;
		Iterator<SingleSale> ssIt = al.iterator();
		while(ssIt.hasNext())
			total += ssIt.next().getTotal();
		return total;
	}
	
	public int getTotal() { return this.total; }
	public int getAmount() { return this.amount; }
	public String getDetail() { return this.detail; }
	
	public void setTotal(int total) { this.total = total; }
	public void setAmount(int amount) { this.amount = amount; }
	public void setDetail(String detail) { this.detail = detail; }
}
