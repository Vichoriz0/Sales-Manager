package dataHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import utilities.NumberUtilities;
import utilities.StringUtilities;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class Sale {
	private int id;
	private int idClient;
	private int ot;
	private int totalSale;
	private int costOfSale;
	private int profit;
	private double margin;
	private LocalDate date;
	private ArrayList<SingleSale> allSales = new ArrayList<SingleSale>();
	private ArrayList<Purchase> purchases = new ArrayList<Purchase>();
	
	public Sale(int id, int idClient, int ot, int totalSale, LocalDate date, ArrayList<SingleSale> allSales, ArrayList<Purchase> purchases) {
		this.id = id;
		this.idClient = idClient;
		this.ot = ot;
		this.totalSale = totalSale;
		this.date = date;
		this.allSales = allSales;
		this.purchases = purchases;
		calculateFieldsLeft();
	}
	
	public Sale(int id, int idClient, int ot, int totalSale, int costOfSale, int profit, double margin, LocalDate date, ArrayList<SingleSale> allSales, ArrayList<Purchase> purchases) {
		this.id = id;
		this.idClient = idClient;
		this.ot = ot;
		this.totalSale = totalSale;
		this.costOfSale = costOfSale;
		this.profit = profit;
		this.margin = margin;
		this.date = date;
		this.allSales = allSales;
		this.purchases = purchases;
	}
	
	public Sale(Sale s) {
		int sizeSS = s.getAllSales().size(), sizeP = s.getPurchases().size();
		this.id = s.getId();
		this.idClient = s.getIdClient();
		this.ot = s.getOT();
		this.totalSale = s.getTotalSale();
		this.costOfSale = s.getCostOfSale();
		this.profit = s.getProfit();
		this.margin = s.getMargin();
		this.date = LocalDate.parse(s.getDate().toString());
		for(int i = 0; i < sizeSS; ++i) {
			SingleSale aux = new SingleSale(s.getAllSales().get(i).getTotal(), s.getAllSales().get(i).getAmount(), s.getAllSales().get(i).getDetail());
			this.allSales.add(aux);
		}
		for(int i = 0; i < sizeP; ++i) {
			Purchase p = s.getPurchases().get(i);
			this.purchases.add(new Purchase(p.getIdBill(), p.getIdCategory(), p.getIdSeller(), p.getPurchaseTotal(), p.getAmount(), p.getPercentageOfCost(), p.getDetail()));
		}
	}
	
	//Calcula los campos restantes con la informacion ya dada.
	private void calculateFieldsLeft() {
		int costOfSale = 0;
		Iterator<Purchase> purchaseIterator = this.purchases.iterator();
		while(purchaseIterator.hasNext())
			costOfSale += purchaseIterator.next().getPurchaseTotal();
		purchaseIterator = this.purchases.iterator();
		for(int i = 0; purchaseIterator.hasNext(); ++i){
			int purchaseTotal = purchaseIterator.next().getPurchaseTotal();
			this.purchases.get(i).setPercentageOfCost(NumberUtilities.round((double)purchaseTotal / costOfSale * 100, 2));
		}
		this.costOfSale = costOfSale;
		this.profit = this.totalSale - costOfSale;
		this.margin = NumberUtilities.round((double) this.profit / this.totalSale * 100, 2);
	}
	
	//Retorna la posicion en que se encuentra la venta con la ID de entrada.
	public static int indexOf(int ot) {
		if(ot > (int)(FileHandler.amountOfSales/2.0 + 0.5)) {
			for(int i = FileHandler.amountOfSales - 1; i >= 0; --i)
				if(FileHandler.getSales().get(i).getOT() == ot)
					return i;
		}else {
			for(int i = 0; i < FileHandler.amountOfSales; ++i)
				if(FileHandler.getSales().get(i).getOT() == ot)
					return i;
		}
		return -1;
	}
	
	//Remueve una venta de la lista que coincida con la ID de entrada.
	public static void removeSale(int ot) {
		for(int i = 0; i < FileHandler.amountOfSales; ++i) {
			if(FileHandler.getSales().get(i).getOT() == ot) {
				FileHandler.getSales().remove(i);
				break;
			}
		}
		FileHandler.amountOfSales--;
		FileHandler.writeSales("data/sales.temp.csv");
	}
	
	//Reemplaza una venta de la lista con la venta de entrada.
	public static void editSale(int pos, Sale s) {
		FileHandler.getSales().set(pos, s);
		FileHandler.writeSales("data/sales.temp.csv");
	}
	
	//Inserta una venta a la lista de manera ordenada.
	public static void insertSale(Sale s) {
		if(FileHandler.amountOfSales == 0 || s.getOT() >= FileHandler.getSales().get(FileHandler.amountOfSales - 1).getOT()) {
			FileHandler.getSales().add(s);
			FileHandler.writeSales(s);
		}else {
			for(int i = 0; i < FileHandler.amountOfSales; ++i) {
				if(s.getOT() < FileHandler.getSales().get(i).getOT()) {
					FileHandler.getSales().add(i, s);
					break;
				}
			}
			FileHandler.writeSales("data/sales.temp.csv");
		}
		FileHandler.amountOfSales++;
	}
	
	//Imprime la informacion de la venta completa.
	public void printSale() {
		int allSalesSize = this.allSales.size();
		int purchasesSize = this.purchases.size();
		System.out.println(this.id + " | " + this.idClient + " | " + this.ot + " | " + this.totalSale + " | " + this.getCostOfSale() + " | " + this.profit + " | " + (double)this.margin + " | " + this.date + " | " + allSalesSize + " | " + purchasesSize);
		for(int i = 0; i < allSalesSize; ++i)
			this.allSales.get(i).printSingleSale();
		for(int i = 0; i < purchasesSize; ++i)
			this.purchases.get(i).printPurchase();
		System.out.print("\n");
	}
	
	//Retorna el proximo ID a ingresar
	public static int getNextId() {
		if(FileHandler.amountOfSales > 0)
			return FileHandler.getSales().get(FileHandler.amountOfSales-1).getId() + 1;
		return 1;
	}
	
	//Retorna la proxima orden de trabajo a ingresar
	public static int getNextOT() {
		if(FileHandler.amountOfSales > 0)
			return FileHandler.getSales().get(FileHandler.amountOfSales-1).getOT() + 1;
		return 1;
	}
	
	//Entrega la suma de todas las ventas individuales
	public int getAllMoney() {
		int total = 0;
		Iterator<SingleSale> ssIt = this.allSales.iterator();
		while(ssIt.hasNext())
			total += ssIt.next().getTotal();
		return total;
	}
	
	//Retorna la fecha como String en formate (Nombre MM) DD, YYYY
	public String getFormattedDate() {
		ArrayList<String> date = StringUtilities.stringTokenizer(this.date.toString(), "-");
		return StringUtilities.getMonthName(Integer.parseInt(date.get(1))) + " " + date.get(2) + ", " + date.get(0);
	}
	
	//Compara si una instancia de Sale es igual a la actual.
	public boolean equals(Sale s) {
		int allSaleSize = this.allSales.size(), purchaseSize = this.purchases.size();
		if((this == null && s != null) || (this != null && s == null))
			return false;
		if(this.id != s.getId() || this.idClient != s.getIdClient() || this.ot != s.getOT() || this.totalSale != s.getTotalSale() || !this.date.equals(s.getDate()))
			return false;
		if(allSaleSize != s.getAllSales().size() || purchaseSize != s.getPurchases().size())
			return false;
		for(int i = 0; i < allSaleSize; ++i) {
			SingleSale aux1 = this.allSales.get(i), aux2 = s.getAllSales().get(i);
			if(aux1.getTotal() != aux2.getTotal() || aux1.getAmount() != aux2.getAmount() || aux1.getDetail() != aux2.getDetail())
				return false;
		}
		for(int i = 0; i < purchaseSize; ++i) {
			Purchase aux1 = this.purchases.get(i), aux2 = s.getPurchases().get(i);
			if(aux1.getIdBill() != aux2.getIdBill() || aux1.getIdCategory() != aux2.getIdCategory() || aux1.getIdSeller() != aux2.getIdSeller() || aux1.getPurchaseTotal() != aux2.getPurchaseTotal() || aux1.getAmount() != aux2.getAmount() || !aux1.getDetail().equals(aux2.getDetail()))
				return false;
		}
		return true;
	}
	
	public int getId() { return this.id; }
	public int getIdClient() { return this.idClient; }
	public int getOT() { return this.ot; }
	public int getTotalSale() { return this.totalSale; }
	public int getCostOfSale() { return this.costOfSale; }
	public int getProfit() { return this.profit; }
	public double getMargin() { return this.margin; }
	public LocalDate getDate() { return this.date; }
	public ArrayList<SingleSale> getAllSales() { return this.allSales; }
	public ArrayList<Purchase> getPurchases() { return this.purchases; }
	
	public void setId(int id) { this.id = id; }
	public void setIdClient(int idClient) { this.idClient = idClient; }
	public void setOT(int ot) { this.ot = ot; }
	public void setTotalSale(int totalSale) { this.totalSale = totalSale; }
	public void setCostOfSale(int costOfSale) { this.costOfSale = costOfSale; }
	public void setProfit(int profit) { this.profit = profit; }
	public void setMargin(double margin) { this.margin = margin; }
	public void setDate(LocalDate date) { this.date = date; }
	public void setAllSales(ArrayList<SingleSale> allSales) { this.allSales = allSales; }
	public void setPurchases(ArrayList<Purchase> purchases) { this.purchases = purchases; }
}
