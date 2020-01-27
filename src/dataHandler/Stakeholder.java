package dataHandler;

import java.util.ArrayList;
import utilities.StringUtilities;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class Stakeholder {
	private int id;
	private String name;
	
	public Stakeholder(int id, String name) {
		this.id = id;
		this.name = name.replaceAll("[\n\r]*$", "");
	}
	
	//Imprime la informacion del stakeholder
	public void printStakeholder() {
		System.out.println(this.id + " | " + this.name);
	}
	
	//Cambia el elemento de pos1 con el elemento de pos2
	public static void swap(ArrayList<Stakeholder> al, int pos1, int pos2) {
		Stakeholder aux = al.get(pos2);
		al.set(pos2, al.get(pos1));
		al.set(pos1, aux);
	}
	
	//Agrega un nuevo Stakeholder al ArrayList sh segun orden alfabetico. Si el Stakeholder ya existe, no se agrega y retorna falso.
	public static boolean addSorted(ArrayList<Stakeholder> sh, Stakeholder newSh, int size) {
		String name = newSh.getName();
		if(size != 1) {
			for(int i = 1; i < size; ++i) {
				if(name.equals(sh.get(i).getName()))
					return false;
				if(name.equals(StringUtilities.min(name, sh.get(i).getName()))) {
					sh.add(i, newSh);
					return true;
				}
			}
		}
		sh.add(newSh);
		return true;
	}
	
	//Retorna el proximo ID a ingresar
	public static int getNextId(ArrayList<Stakeholder> sh, int size) {
		if(size > 0)
			return sh.get(size-1).getId() + 1;
		return 1;
	}
	
	//Bubble Sort alfabetico segun el valor del campo NAME;
	public static ArrayList<Stakeholder> bubbleSort(ArrayList<Stakeholder> al, int size) {
		int size2 = size - 1;
		for(int i = 0; i < size2; ++i) {
			int size3 = size - i - 1;
			for(int j = 1; j < size3; ++j) {
				String str = al.get(j).getName();
				String str2 = al.get(j+1).getName();
				if(!str.equals(str2) && str2.equals(StringUtilities.min(str, str2)))
					swap(al, j, j+1);
			}
		}
		return al;
	}
	
	//Retorna la posicion de la primera ocurrencia con el valor ID en el ArrayList de entrada
	public static int indexOf(ArrayList<Stakeholder> al, int size, int id) {
		for(int i = 0; i < size; ++i)
			if(al.get(i).getId() == id)
				return i;
		return -1;
	}
	
	//Retorna la posicion de la primera ocurrencia del valor NAME en el ArrayList de entrada
	public static int indexOf(ArrayList<Stakeholder> al, int size, String name) {
		for(int i = 0; i < size; ++i)
			if(al.get(i).getName().equals(name))
				return i;
		return -1;
	}
	
	//Busca una instancia de Stakeholders con el NAME de entrada, y retorna su ID o 0 si no lo encuentra.
	public static int search(ArrayList<Stakeholder> al, int size, String name) {
		if(name.equals(StringUtilities.max(name, al.get((int)(size/2.0+0.5)).getName()))) {
			for(int i = size - 1; i >= 0; --i)
				if(al.get(i).getName().equals(name))
					return al.get(i).getId();
		}else {
			for(int i = 0; i < size; ++i)
				if(al.get(i).getName().equals(name))
					return al.get(i).getId();
		}
		return 0;
	}
	
	//Busca una instacia de Stakeholder con la ID de entrada, y retorna su nombre o NULL si no lo encuentra.
	public static String search(ArrayList<Stakeholder> al, int size, int id) {
		if(id > (int)(size/2.0 + 0.5)) {
			for(int i = size - 1; i >= 0; --i)
				if(al.get(i).getId() == id)
					return al.get(i).getName();
		}else {
			for(int i = 0; i < size; ++i)
				if(al.get(i).getId() == id)
					return al.get(i).getName();
		}
		return null;
	}
	
	//Para todas las compras, cambia la ID del vendedor por su valor por defecto (0). Retorna TRUE si ha realizado al menos un cambio.
	public static boolean updateSellerOnDelete(int idSeller) {
		boolean updated = false;
		for(int i = 0; i < FileHandler.amountOfSales; ++i) {
			int size = FileHandler.getSales().get(i).getPurchases().size();
			ArrayList<Purchase> p = FileHandler.getSales().get(i).getPurchases();
			for(int j = 0; j < size; ++j) {
				if(p.get(j).getIdSeller() == idSeller) {
					p.get(j).setIdSeller(0);
					updated = true;
				}
			}
		}
		return updated;
	}
	
	//Para todas las ventas, cambia la ID del cliente por su valor por defecto (0). Retorna TRUE si ha realizado al menos un cambio.
	public static boolean updateClientOnDelete(int idClient) {
		boolean updated = false;
		ArrayList<Sale> s = FileHandler.getSales();
		for(int i = 0; i < FileHandler.amountOfSales; ++i) {
			if(s.get(i).getIdClient() == idClient) {
				s.get(i).setIdClient(0);
				updated = true;
			}
		}
		return updated;
	}
	
	public int getId() { return this.id; }
	public String getName() { return this.name; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
}
