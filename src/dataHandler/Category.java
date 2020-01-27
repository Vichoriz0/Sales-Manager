package dataHandler;

import java.util.ArrayList;

import utilities.StringUtilities;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class Category {
	private int id;
	private String name;
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name.replaceAll("[\n\r]*$", "");
	}
	
	//Imprime la informacion de la categoria
	public void printCategory() {
		System.out.println(this.id + " | " + this.name);
	}
	
	//Agrega una nueva Categoria segun orden alfabetico. Si la Categoria ya existe, no se agrega y retorna falso.
	public static boolean addSorted(ArrayList<Category> c, Category newCat) {
		String name = newCat.getName();
		if(FileHandler.amountOfCategories != 1) {
			for(int i = 1; i < FileHandler.amountOfCategories; ++i) {
				if(name.equals(c.get(i).getName()))
					return false;
				if(name.equals(StringUtilities.min(name, c.get(i).getName()))) {
					c.add(i, newCat);
					return true;
				}
			}
		}
		c.add(newCat);
		return true;
	}
	
	//Retorna el proximo ID a ingresar
	public static int getNextId() {
		if(FileHandler.amountOfCategories > 0)
			return FileHandler.getCategories().get(FileHandler.amountOfCategories-1).getId() + 1;
		return 1;
	}
	
	//Retorna la posicion de la primera ocurrencia del valor NAME en el ArrayList de entrada
	public static int indexOf(ArrayList<Category> al, String name) {
		if(name.equals(StringUtilities.max(name, FileHandler.getCategories().get((int)(FileHandler.amountOfCategories/2.0+0.5)).getName()))) {
			for(int i = FileHandler.amountOfCategories - 1; i >= 0; --i)
				if(al.get(i).getName().equals(name))
					return i;
		}else {
			for(int i = 0; i < FileHandler.amountOfCategories; ++i)
				if(al.get(i).getName().equals(name))
					return i;
		}
		return -1;
	}
	
	//Busca una instancia de Category con el NAME de entrada, y retorna su ID o 0 si no lo encuentra.
	public static int search(String name) {
		for(int i = 0; i < FileHandler.amountOfCategories; ++i)
			if(FileHandler.getCategories().get(i).getName().equals(name))
				return FileHandler.getCategories().get(i).getId();
		return 0;
	}
		
	//Busca una instacia de Category con la ID de entrada, y retorna su nombre o NULL si no lo encuentra.
	public static String search(int id) {
		for(int i = 0; i < FileHandler.amountOfCategories; ++i)
			if(FileHandler.getCategories().get(i).getId() == id)
				return FileHandler.getCategories().get(i).getName();
		return null;
	}
	
	//Para todas las compras, cambia la ID de la categoria por su valor por defecto (0). Retorna TRUE si ha realizado al menos un cambio.
	public static boolean updateCategoryOnDelete(int idCategory) {
		boolean updated = false;
		for(int i = 0; i < FileHandler.amountOfSales; ++i) {
			int size = FileHandler.getSales().get(i).getPurchases().size();
			ArrayList<Purchase> p = FileHandler.getSales().get(i).getPurchases();
			for(int j = 0; j < size; ++j) {
				if(p.get(j).getIdCategory() == idCategory) {
					p.get(j).setIdCategory(0);
					updated = true;
				}
			}
		}
		return updated;
	}
	
	//Cambia el elemento de pos1 con el elemento de pos2
	public static void swap(ArrayList<Category> al, int pos1, int pos2) {
		Category aux = al.get(pos2);
		al.set(pos2, al.get(pos1));
		al.set(pos1, aux);
	}
	
	//Bubble Sort alfabetico segun el valor del campo NAME;
	public static ArrayList<Category> bubbleSort(ArrayList<Category> al) {
		int size = FileHandler.amountOfCategories - 1;
		for(int i = 0; i < size; ++i) {
			int size2 = FileHandler.amountOfCategories - i - 1;
			for(int j = 1; j < size2; ++j) {
				String str = al.get(j).getName();
				String str2 = al.get(j+1).getName();
				if(!str.equals(str2) && str2.equals(StringUtilities.min(str, str2)))
					swap(al, j, j+1);
			}
		}
		return al;
	}
	
	public int getId() { return this.id; }
	public String getName() { return this.name; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
}
