package dataHandler;

//import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
//import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import utilities.*;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

//Se encarga de la lectura y escritura de los archivos que actuan como base de datos para esta aplicacion.
public class FileHandler {
	public static int amountOfCategories = 1;			//Estos int se ocupan para evitar hacer
	public static int amountOfSales = 0;				//llamados a la funcion SIZE de sus
	public static int amountOfClients = 1;				//respectivos ArrayList.
	public static int amountOfSellers = 1;
	private static String salesFileDir = "data/ventas.csv";
	private static String categoriesFileDir = "data/categorias.csv";
	private static String clientsFileDir = "data/clientes.csv";
	private static String sellersFileDir = "data/vendedores.csv";
	private static ArrayList<Category> categories = new ArrayList<>();
	private static ArrayList<Sale> sales = new ArrayList<>();
	private static ArrayList<Stakeholder> clients = new ArrayList<>();
	private static ArrayList<Stakeholder> sellers = new ArrayList<>();
	
	//Lee las categorias de costos registradas desde un archivo.
	public static void readCategories() {
		try{
			//FileReader fr = new FileReader(FileHandler.getCategoriesFileDir());
			//BufferedReader br = new BufferedReader(fr);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FileHandler.getCategoriesFileDir()), "UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				ArrayList<String> auxStringList = StringUtilities.stringTokenizer(line, ";");
				if(auxStringList.isEmpty() || !NumberUtilities.isNumber(auxStringList.get(0),false)){		//Ignora esta iteracion si la linea leida esta vacia o no sigue el formato.
					continue;
				}else{
					Category temp = new Category(Integer.parseInt(auxStringList.get(0)), auxStringList.get(1));
					FileHandler.getCategories().add(temp);
					FileHandler.amountOfCategories += 1;
				}
			}
			br.close();
		}catch(FileNotFoundException ex) {
			String message = "Advertencia: No se pudo encontrar el archivo '" + FileHandler.getCategoriesFileDir() + "'";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: File Not Found", JOptionPane.INFORMATION_MESSAGE);
		}catch(IOException ex) {
			String message = "Advertencia: Error de lectura del archivo '" + FileHandler.getCategoriesFileDir() + "'. Puede que est\u00E9 corrupto.";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: File Not Found ", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//Lee todas las ventas registradas desde un archivo.
	public static void readSales() {
		try {
			//FileReader fr = new FileReader(FileHandler.getSalesFileDir());
			//BufferedReader br = new BufferedReader(fr);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FileHandler.getSalesFileDir()), "UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				int nextInstruction = 1;
				ArrayList<SingleSale> allSales = new ArrayList<>();
				ArrayList<Purchase> purchases = new ArrayList<>();
				ArrayList<String> auxStringList = StringUtilities.stringTokenizer(line,";");
				String[] saleInfo = new String[8];
				int[] amountOfRows = new int[2];
				while(nextInstruction != -1){
					switch(nextInstruction) {
						case 1:		//Validacion de formato y obtencion de informacion de ventas.
							if(auxStringList.size() != 10 || !NumberUtilities.isNumber(auxStringList.get(0),true) || !NumberUtilities.isNumber(auxStringList.get(2),false) || !NumberUtilities.isNumber(auxStringList.get(3),false) || !NumberUtilities.isNumber(auxStringList.get(4),false) || !NumberUtilities.isNumber(auxStringList.get(5),true) || !NumberUtilities.isDecimal(auxStringList.get(6),true) || !NumberUtilities.isNumber(auxStringList.get(8),false) || !NumberUtilities.isNumber(auxStringList.get(9),false)) {
								nextInstruction = -1;
							}else{
								for(int i = 0; i < 8; ++i)
									saleInfo[i] = auxStringList.get(i);
								amountOfRows[0] = Integer.parseInt(auxStringList.get(8));
								amountOfRows[1] = Integer.parseInt(auxStringList.get(9));
								nextInstruction = 2;
							}
							break;
						case 2:		//Validacion de formato y obtencion de informacion para la clase SingleSale
							for(int i = 0; i < amountOfRows[0]; ++i) {
								if((line = br.readLine()) == null || line.equals("|FIN|")) {
									nextInstruction = -1;
									break;
								}else{
									auxStringList = StringUtilities.stringTokenizer(line,";");
									if(auxStringList.size() != 3 || !NumberUtilities.isNumber(auxStringList.get(0),false) || !NumberUtilities.isNumber(auxStringList.get(1),false)) {
										i--;
										continue;
									}else{
										int total = Integer.parseInt(auxStringList.get(0));
										int amount = Integer.parseInt(auxStringList.get(1));
										SingleSale temp = new SingleSale(total, amount, auxStringList.get(2));
										allSales.add(temp);
									}
								}
							}
							nextInstruction = 3;
							break;
						case 3:		//Validacion de formato y obtencion de informacion para la clase Purchase
							for(int i = 0; i < amountOfRows[1]; ++i) {
								if((line = br.readLine()) == null || line.equals("|FIN|")) {
									nextInstruction = -1;
									break;
								}else{
									auxStringList = StringUtilities.stringTokenizer(line,";");
									if(auxStringList.size() != 7 || !NumberUtilities.isNumber(auxStringList.get(0),false) || !NumberUtilities.isNumber(auxStringList.get(3),false) || !NumberUtilities.isNumber(auxStringList.get(4),false) || !NumberUtilities.isDecimal(auxStringList.get(5),false)) {
										i--;
										continue;
									}else{
										int idBill = Integer.parseInt(auxStringList.get(0));
										int idCategory;
										int idSeller;
										int purchaseTotal = Integer.parseInt(auxStringList.get(3));
										int amount = Integer.parseInt(auxStringList.get(4));
										double percentageOfCost = Double.parseDouble(auxStringList.get(5));
										if(!NumberUtilities.isNumber(auxStringList.get(1),false) || !doesCategoryExist(idCategory = Integer.parseInt(auxStringList.get(1))))
											idCategory = 0;
										if(!NumberUtilities.isNumber(auxStringList.get(2),false) || !doesStakeholderExist(FileHandler.getSellers(), FileHandler.amountOfSellers, idSeller = Integer.parseInt(auxStringList.get(2))))
											idSeller = 0;
										Purchase temp = new Purchase(idBill,idCategory,idSeller,purchaseTotal,amount,percentageOfCost,auxStringList.get(6));
										purchases.add(temp);
									}
								}
							}
							nextInstruction = 4;
							break;
						case 4:
							int id = Integer.parseInt(saleInfo[0]);
							int idClient;
							int ot = Integer.parseInt(saleInfo[2]);
							int totalSale = Integer.parseInt(saleInfo[3]);
							int costOfSale = Integer.parseInt(saleInfo[4]);
							int profit = Integer.parseInt(saleInfo[5]);
							double margin = Double.parseDouble(saleInfo[6]);
							LocalDate date = LocalDate.parse(saleInfo[7]);
							if(!NumberUtilities.isNumber(auxStringList.get(1),false) || !doesStakeholderExist(FileHandler.getClients(), FileHandler.amountOfClients, idClient = Integer.parseInt(saleInfo[1])))
								idClient = 0;
							Sale temp = new Sale(id, idClient, ot, totalSale, costOfSale, profit, margin, date, allSales, purchases);
							FileHandler.getSales().add(temp);
							FileHandler.amountOfSales += 1;
							nextInstruction = 0;
							break;
						default:
							while((line = br.readLine()) != null && !line.equals("|FIN|"));
							nextInstruction = -1;
							break;
					}
				}
			}
			br.close();
		}catch(FileNotFoundException ex) {
			String message = "Advertencia: No se pudo encontrar el archivo '" + FileHandler.getSalesFileDir() + "'";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: File Not Found", JOptionPane.INFORMATION_MESSAGE);
		}catch(IOException ex) {
			String message = "Advertencia: Error de lectura del archivo '" + FileHandler.getSalesFileDir() + "'. Puede que est\u00E9 corrupto.";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: File Not Found ", JOptionPane.INFORMATION_MESSAGE);
		}
	}	
	
	//Lee todos los stakeholders registrados desde un archivo.
	public static int readStakeholders(ArrayList<Stakeholder> al, String dir, int amountRead) {
		try {
			//FileReader fr = new FileReader(dir);
			//BufferedReader br = new BufferedReader(fr);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dir), "UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				ArrayList<String> auxStringList = StringUtilities.stringTokenizer(line, ";");
				if(auxStringList.isEmpty() || !NumberUtilities.isNumber(auxStringList.get(0),false)) {
					continue;				//Ignora esta iteracion si la linea leida esta vacia o no sigue el formato.
				}else {
					int id = Integer.parseInt(auxStringList.get(0));
					Stakeholder temp = new Stakeholder(id, auxStringList.get(1));
					al.add(temp);
					amountRead += 1;
				}
			}
			br.close();
		}catch(FileNotFoundException ex) {
			String message = "Advertencia: No se pudo encontrar el archivo '" + dir + "'";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: File Not Found", JOptionPane.INFORMATION_MESSAGE);
		}catch(IOException ex) {
			String message = "Advertencia: Error de lectura del archivo '" + dir + "'. Puede que est\u00E9 corrupto.";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: File Not Found ", JOptionPane.INFORMATION_MESSAGE);
		}
		return amountRead;
	}
	
	//Para reescribir todas las categorias (ademas de las nuevas). Uso para cuando se modifica una categoria ya existente.
	public static void writeCategories(String tempFile) {
		try {
			//FileWriter fw = new FileWriter(tempFile);
            //BufferedWriter bw = new BufferedWriter(fw);
			Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));
            Iterator<Category> categoryIterator = FileHandler.getCategories().iterator();
            categoryIterator.next();
            while(categoryIterator.hasNext()) {
            	Category aux = categoryIterator.next();
            	bw.write(String.valueOf(aux.getId() + ";" + aux.getName() + "\n"));
            }
            bw.close();
            FileUtilities.renameFile(FileHandler.getCategoriesFileDir(),tempFile);
        }
        catch(IOException ex) {
        	String message = "Error escribiendo a archivo '" + FileHandler.getCategoriesFileDir() + "'\nNo se han guardado cambios.";
        	File f = new File(tempFile);
        	JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Writing Failed", JOptionPane.INFORMATION_MESSAGE);
        	f.delete();
        }
	}
	
	//Para agregar una categoria a la lista.
	public static void writeCategories(Category aux) {
		try {
			//FileWriter fw = new FileWriter(FileHandler.getCategoriesFileDir(), true);
			//BufferedWriter bw = new BufferedWriter(fw);
			Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileHandler.getCategoriesFileDir(), true), "UTF-8"));
			bw.write(aux.getId() + ";" + aux.getName() + "\n");
			bw.close();
		}catch(IOException ex) {
			String message = "Error escribiendo a archivo '" + FileHandler.getCategoriesFileDir() + "'\nNo se han guardado cambios.";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Writing Failed", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//Para reescribir todas las ventas (ademas de las nuevas). Uso para cuando se modifica una venta ya existente.
	public static void writeSales(String tempFile) {
		try{
			//FileWriter fw = new FileWriter(tempFile);
			//BufferedWriter bw = new BufferedWriter(fw);
			Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));
			Iterator<Sale> saleIt = FileHandler.getSales().iterator();
			while(saleIt.hasNext()) {
				Sale aux = saleIt.next();
				int allSalesSize = aux.getAllSales().size();
				int purchasesSize = aux.getPurchases().size();
				bw.write(aux.getId() + ";" + aux.getIdClient() + ";" + aux.getOT() + ";" + aux.getTotalSale() + ";" + aux.getCostOfSale() + ";" + aux.getProfit() + ";" + aux.getMargin() + ";" + aux.getDate() + ";" + allSalesSize + ";" + purchasesSize + "\n");
				for(int i = 0; i < allSalesSize; ++i) {
					SingleSale aux2 = aux.getAllSales().get(i);
					bw.write(aux2.getTotal() + ";" + aux2.getAmount() + ";" + aux2.getDetail() + "\n");
				}
				for(int i = 0; i < purchasesSize; ++i) {
					Purchase aux2 = aux.getPurchases().get(i);
					bw.write(aux2.getIdBill() + ";" + aux2.getIdCategory() + ";" + aux2.getIdSeller() + ";" + aux2.getPurchaseTotal() + ";" + aux2.getAmount() + ";" + aux2.getPercentageOfCost() + ";" + aux2.getDetail() + "\n");
				}
				bw.write("|FIN|\n");
			}
			bw.close();
			FileUtilities.renameFile(FileHandler.getSalesFileDir(),tempFile);
		}catch(IOException ex){
			String message = "Error escribiendo a archivo '" + FileHandler.getSalesFileDir() + "'\nNo se han guardado cambios.";
			File f = new File(tempFile);
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Writing Failed", JOptionPane.INFORMATION_MESSAGE);
			f.delete();
		}
	}
	
	//Para agregar una venta a la lista.
	public static void writeSales(Sale aux) {
		try {
			//FileWriter fw = new FileWriter(FileHandler.getSalesFileDir(), true);
			//BufferedWriter bw = new BufferedWriter(fw);
			Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileHandler.getSalesFileDir(), true), "UTF-8"));
			int allSalesSize = aux.getAllSales().size();
			int purchasesSize = aux.getPurchases().size();
			bw.write(aux.getId() + ";" + aux.getIdClient()  + ";" + aux.getOT() + ";" + + aux.getTotalSale() + ";" + aux.getCostOfSale() + ";" + aux.getProfit() + ";" + aux.getMargin() + ";" + aux.getDate() + ";" + allSalesSize + ";" + purchasesSize + "\n");
			for(int i = 0; i < allSalesSize; ++i) {
				SingleSale aux2 = aux.getAllSales().get(i);
				bw.write(aux2.getTotal() + ";" + aux2.getAmount() + ";" + aux2.getDetail() + "\n");
			}
			for(int i = 0; i < purchasesSize; ++i) {
				Purchase aux2 = aux.getPurchases().get(i);
				bw.write(aux2.getIdBill() + ";" + aux2.getIdCategory() + ";" + aux2.getIdSeller() + ";" + aux2.getPurchaseTotal() + ";" + aux2.getAmount() + ";" + aux2.getPercentageOfCost() + ";" + aux2.getDetail() + "\n");
			}
			bw.write("|FIN|\n");
			bw.close();
		}catch(IOException ex) {
			String message = "Error escribiendo a archivo '" + FileHandler.getSalesFileDir() + "'\nNo se han guardado cambios.";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Writing Failed", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	//Para reescribir todos los stakeholders (ademas de los nuevos). Uso para cuando se modifica un cliente ya existente.
	public static void writeStakeholders(ArrayList<Stakeholder> al, String tempFile, String dir) {
		try {
			//FileWriter fw = new FileWriter(tempFile);
			//BufferedWriter bw = new BufferedWriter(fw);
			Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));
			Iterator<Stakeholder> shIterator = al.iterator();
			shIterator.next();
			while(shIterator.hasNext()){
				Stakeholder aux = shIterator.next();
				bw.write(aux.getId() + ";" + aux.getName() + "\n");
			}
			bw.close();
			FileUtilities.renameFile(dir, tempFile);
		}catch(IOException ex) {
			String message = "Error escribiendo a archivo '" + dir + "'\nNo se han guardado cambios.";
			File f = new File(tempFile);
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Writing Failed", JOptionPane.INFORMATION_MESSAGE);
			f.delete();
			ex.printStackTrace();
		}
	}
	
	//Para gregar un stakeholder a la lista.
	public static void writeStakeholders(Stakeholder aux, String dir) {
		try {
			//FileWriter fw = new FileWriter(dir, true);
			//BufferedWriter bw = new BufferedWriter(fw);
			Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir, true), "UTF-8"));
			bw.write(aux.getId() + ";" + aux.getName() + "\n");
			bw.close();
		}catch(IOException ex) {
			String message = "Error escribiendo a archivo '" + dir + "'\n. No se han guardado cambios.";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Writing Failed", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//Busca si existe una categoria con el mismo valor para el campo ID.
	public static boolean doesCategoryExist(int id) {
		for(int i = 0; i < FileHandler.amountOfCategories; ++i)
			if(FileHandler.getCategories().get(i).getId() == id)
				return true;
		return false;
	}
	
	//Busca si existe una categoria con el mismo valor para el campo NAME.
	public static boolean doesCategoryExist(String name) {
		for(int i = 0; i < FileHandler.amountOfCategories; ++i)
			if(FileHandler.getCategories().get(i).getName().equals(name))
				return true;
		return false;
	}
	
	//Busca si existe un cliente con el mismo valor para el campo NAME;
	public static boolean doesStakeholderExist(ArrayList<Stakeholder> al, int size, String name) {
		for(int i = 0; i < size; ++i)
			if(al.get(i).getName().equals(name))
				return true;
		return false;
	}
	
	//Busca si existe un vendedor con el mismo valor para el campo ID;
	public static boolean doesStakeholderExist(ArrayList<Stakeholder> al, int size, int id) {
		for(int i = 0; i < size; ++i) {
			if(al.get(i).getId() == id)
				return true;
		}
		return false;
	}
	
	//Busca si existe una venta con el mismo valor para el campo ID.
	public static boolean doesSaleExist(int id) {
		for(int i = 0; i < FileHandler.amountOfSales; ++i)
			if(FileHandler.getSales().get(i).getId() == id)
				return true;
		return false;
	}
	
	//Busca si existe una venta con el mismo valor para el orden de trabajo.
	public static boolean doesOTExist(int ot) {
		for(int i = 0; i < FileHandler.amountOfSales; ++i)
			if(FileHandler.getSales().get(i).getOT() == ot)
				return true;
		return false;
	}
	
	public static String getSalesFileDir() { return FileHandler.salesFileDir; }
	public static String getCategoriesFileDir() { return FileHandler.categoriesFileDir; }
	public static String getClientsFileDir() { return FileHandler.clientsFileDir; }
	public static String getSellersFileDir() { return FileHandler.sellersFileDir; }
	public static ArrayList<Category> getCategories() { return FileHandler.categories; }
	public static ArrayList<Sale> getSales() { return FileHandler.sales; }
	public static ArrayList<Stakeholder> getClients() { return FileHandler.clients; }
	public static ArrayList<Stakeholder> getSellers() { return FileHandler.sellers; }
	
	public static void setCategoriesFileDir(String categoriesFileDir) { FileHandler.categoriesFileDir = categoriesFileDir; }
	public static void setSalesFileDir(String salesFileDir) { FileHandler.salesFileDir = salesFileDir; }
	public static void setClientsFileDir(String clientsFileDir) { FileHandler.clientsFileDir = clientsFileDir; }
	public static void setSellersFileDir(String sellersFileDir) { FileHandler.sellersFileDir = sellersFileDir; }
	public static void setCategories(ArrayList<Category> categories) { FileHandler.categories = categories; }
	public static void setSales(ArrayList<Sale> sales) { FileHandler.sales = sales; }
	public static void setClients(ArrayList<Stakeholder> clients) { FileHandler.clients = clients; }
	public static void setSellers(ArrayList<Stakeholder> sellers) { FileHandler.sellers = sellers; }
}