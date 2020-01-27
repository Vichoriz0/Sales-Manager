package dataHandler;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import interfaces.MainWindow;
import utilities.StringUtilities;
import utilities.SwingUtilities;
import dataHandler.Category;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class SalesManagerApplication {

	public static boolean cff;				//Categories File Found
	public static boolean sff;				//Sales File Found
	public static boolean clff;				//Clients File Found
	public static boolean seff;				//Sellers File Found
	
	public static void main(String[] args) {
		
		dataInitializing();
		//insertTestData();
		//printAll();
		happyBirthday();
		launchApp();
		
		//FileHandler.writeCategories("data/categories.temp.csv");
		//FileHandler.writeStakeholders(FileHandler.getClients(), "data/clients.temp.csv", FileHandler.getClientsFileDir());
		//FileHandler.writeStakeholders(FileHandler.getSellers(), "data/sellers.temp.csv", FileHandler.getSellersFileDir());
		//FileHandler.writeSales("data/sales.temp.csv");
	}
	
	//Se encarga de la lectura de archivos para cargar en la app y setea valores por defecto.
	private static void dataInitializing() {
		FileHandler.getCategories().add(new Category(0,"N/A"));
		FileHandler.getClients().add(new Stakeholder(0,"N/A"));
		FileHandler.getSellers().add(new Stakeholder(0,"N/A"));
		if(!(new File(System.getProperty("user.dir") + "/data").mkdirs())) {		//Si la carpeta DATA no existe se asume que no estaran
			FileHandler.readCategories();											//los archivos, asi que no se buscaran para su lectura.
			FileHandler.amountOfClients = FileHandler.readStakeholders(FileHandler.getClients(), FileHandler.getClientsFileDir(), FileHandler.amountOfClients);
			FileHandler.amountOfSellers = FileHandler.readStakeholders(FileHandler.getSellers(), FileHandler.getSellersFileDir(), FileHandler.amountOfSellers);
			FileHandler.readSales();
			System.out.println("Categor\u00EDas: " + FileHandler.amountOfCategories + "\nClientes: " + FileHandler.amountOfClients + "\nVendedores: " + FileHandler.amountOfSellers + "\nVentas: " + FileHandler.amountOfSales);
		}else {
			SalesManagerApplication.cff = false;
			SalesManagerApplication.sff = false;
			SalesManagerApplication.clff = false;
			SalesManagerApplication.seff = false;
		}
	}
	
	//Setea el disenio estilo Nimbus para las ventanas de la app, y lanza la aplicacion.
	private static void launchApp() {
		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		    new MainWindow();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Imprime toda la informacion registrada
	@SuppressWarnings("unused")
	private static void printAll() {
		System.out.println("\t\tCategor\u00EDas");
		int size = FileHandler.getCategories().size();
		for(int i = 0; i < size; ++i)
			FileHandler.getCategories().get(i).printCategory();
		System.out.println("\n\t\tClientes");
		size = FileHandler.getClients().size();
		for(int i = 0; i < size; ++i)
			FileHandler.getClients().get(i).printStakeholder();
		System.out.println("\n\t\tVendedores");
		size = FileHandler.getSellers().size();
		for(int i = 0; i < size; ++i)
			FileHandler.getSellers().get(i).printStakeholder();
		System.out.println("\n\t\tVentas");
		size = FileHandler.getSales().size();
		for(int i = 0; i < size; ++i)
			FileHandler.getSales().get(i).printSale();
		System.out.println("----------------------------------------------------------------------------");
	}
	
	//Insertar datos de prueba a la aplicacion
	@SuppressWarnings("unused")
	private static void insertTestData() {
		FileHandler.getCategories().add(new Category(1,"Papel"));
		FileHandler.getCategories().add(new Category(2,"Plancha"));
		FileHandler.getCategories().add(new Category(3,"Impresion Offset"));
		FileHandler.getCategories().add(new Category(4,"Impresion Laser"));
		FileHandler.getCategories().add(new Category(5,"Corte"));
		FileHandler.amountOfCategories += 5;
		
		FileHandler.getClients().add(new Stakeholder(1,"Vicente"));
		FileHandler.getClients().add(new Stakeholder(2,"Sara"));
		FileHandler.getClients().add(new Stakeholder(3,"Sebastian"));
		FileHandler.getClients().add(new Stakeholder(4,"Lorena"));
		FileHandler.getClients().add(new Stakeholder(5,"Luis"));
		FileHandler.amountOfClients =+ 5;
		
		FileHandler.getSellers().add(new Stakeholder(1,"Somebody"));
		FileHandler.getSellers().add(new Stakeholder(2,"once"));
		FileHandler.getSellers().add(new Stakeholder(3,"told"));
		FileHandler.getSellers().add(new Stakeholder(4,"me"));
		FileHandler.getSellers().add(new Stakeholder(5,"the"));
		FileHandler.amountOfSellers =+ 5;
		
		SingleSale aux = new SingleSale(10000,10,"Cuadernos");
		SingleSale aux2 = new SingleSale(30000,300,"Tarjetas");
		ArrayList<SingleSale> temp = new ArrayList<>(); 
		temp.add(aux); 
		temp.add(aux2);
		
		Purchase aux3 = new Purchase(1,1,1,5000,1000,"Papel Palpo");
		Purchase aux4 = new Purchase(2,2,2,10000,1,"Plancha 10x20");
		ArrayList<Purchase> temp2 = new ArrayList<>(); 
		temp2.add(aux3); 
		temp2.add(aux4);
		
		FileHandler.getSales().add(new Sale(1,1,1,40000,LocalDate.now(),temp,temp2));
		FileHandler.amountOfSales =+ 1;
	}
	
	//Chequea si es el cumpleanos del Lusho
	public static void happyBirthday() {
		ArrayList<String> str = StringUtilities.stringTokenizer(SwingUtilities.date.toString() , "-");
		if(str.get(1).equals("07") && str.get(2).equals("29"))
			JOptionPane.showMessageDialog(null, "<html><b>\u00A1Feliz cumplea\u00F1os, Lucho!</b></html>", "Sales Manager", JOptionPane.INFORMATION_MESSAGE);
	}
}
