package dataHandler;

import java.util.ArrayList;
import java.util.Iterator;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class Purchase {
	private int idBill;
	private int idCategory;
	private int idSeller;
	private int purchaseTotal;
	private int amount;
	private double percentageOfCost;
	private String detail;
	
	public Purchase(int idBill, int idCategory, int idSeller, int purchaseTotal, int amount, String detail) {
		this.idBill = idBill;
		this.idCategory = idCategory;
		this.idSeller = idSeller;
		this.purchaseTotal = purchaseTotal;
		this.amount = amount;
		this.detail = detail.replaceAll("[\n\r]*$", "");
	}
	
	public Purchase(int idBill, int idCategory, int idSeller, int purchaseTotal, int amount, double percentageOfCost, String detail) {
		this.idBill = idBill;
		this.idCategory = idCategory;
		this.idSeller = idSeller;
		this.purchaseTotal = purchaseTotal;
		this.amount = amount;
		this.percentageOfCost = percentageOfCost;
		this.detail = detail.replaceAll("[\n\r]*$", "");
	}
	
	//Imprime la informacion de la compra.
	public void printPurchase() {
		System.out.println(this.idBill + " | " + this.idCategory + " | " + this.idSeller + " | " +this.purchaseTotal + " | " + this.amount + " | " + this.percentageOfCost + " | " + this.detail);
	}
	
	//Retorna la suma de todas las compras registradas en el ArrayList de entrada.
	public static int getAllMoney(ArrayList<Purchase> p) {
		int total = 0;
		Iterator<Purchase> pIt = p.iterator();
		while(pIt.hasNext())
			total += pIt.next().getPurchaseTotal();
		return total;
	}
	
	//Cambia todas los IDSELLER del ArrayList de entrada por el valor ID de entrada.
	public static void changeAllIdSeller(ArrayList<Purchase> p, int id) {
		int size = p.size();
		for(int i = 0; i < size; ++i)
			p.get(i).setIdSeller(id);
	}
	
	//Cambia todas los IDBILL del ArrayList de entrada por el valor ID de entrada.
	public static void changeAllId(ArrayList<Purchase> p, int id) {
		int size = p.size();
		for(int i = 0; i < size; ++i)
			p.get(i).setIdBill(id);
	}
	
	public int getIdBill() { return this.idBill; }
	public int getIdCategory() { return this.idCategory; }
	public int getIdSeller() { return this.idSeller; }
	public int getPurchaseTotal() { return this.purchaseTotal; }
	public int getAmount() { return this.amount; }
	public double getPercentageOfCost() { return this.percentageOfCost; }
	public String getDetail() { return this.detail; }
	
	public void setIdBill(int idBill) { this.idBill = idBill; }
	public void setIdCategory(int idCategory) { this.idCategory = idCategory; }
	public void setIdSeller(int idSeller) { this.idSeller = idSeller; }
	public void setPurchaseTotal(int purchaseTotal) { this.purchaseTotal = purchaseTotal; }
	public void setAmount(int amount) { this.amount = amount; }
	public void setPercentageOfCost(double percentageOfCost) { this.percentageOfCost = percentageOfCost; }
	public void setDetail(String detail) { this.detail = detail; }
}
