package utilities;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StringUtilities {

	//Inserta un String dentro de otro. Extraido de https://www.geeksforgeeks.org/insert-a-string-into-another-string-in-java/
	public static String insertString(String original, String toInsert, int index) {
		String newStr = new String();
		int size = original.length();
		for(int i = 0; i < size; ++i) {
			newStr += original.charAt(i);
			if(i == index)
				newStr += toInsert;
		}
		return newStr;
	}
	
	//Retorna la fecha como String en el formato por defecto de LocalDate
	public static String getDate(String day, String month, String year) {
		if(day.matches("^[0-9]{1}$"))
			day = "0" + day;
		return year + "-" + month + "-" + day;
	}
	
	//Retorna la fecha como String en el formato "DD de MM(Nombre) de YYYY".
	public static String getFormattedDate(String date) {
		ArrayList<String> al = StringUtilities.stringTokenizer(date, "-");
		String month = StringUtilities.getFullMonthName(Integer.parseInt(al.get(1))).toLowerCase();
		return al.get(2) + " de " + month + " de " + al.get(0);
	}
	
	//Retorna una lista con todos los tokens que se pudieron obtener de "line" segun un delimitador.
	public static ArrayList<String> stringTokenizer(String line, String delimiter) {
		ArrayList<String> strFound = new ArrayList<>();
		try{
			StringTokenizer st = new StringTokenizer(line,delimiter);
			while (st.hasMoreTokens())
				strFound.add(st.nextToken());
		}catch(NullPointerException npe){
			return null;
		}
		return strFound;
	}
	
	//Llena un arreglo de String con valor numericos que van desde la variable FIRST a la variable LAST
	public static String[] numberFiller(int first, int last) {
		String[] filled;
		if(last < first) {
			int amountOfChar = String.valueOf(first).length();
			filled = new String[first-last+1];
			for(int i = 0, actualValue = first; actualValue >= last; ++i, --actualValue) {
				String temp = String.valueOf(actualValue);
				if(temp.length() < amountOfChar)
					for(int j = amountOfChar - temp.length(); j > 0; --j)
						temp = "0" + temp;
				filled[i] = temp;
			}
		}else if(last > first) {
			int amountOfChar = String.valueOf(last).length();
			filled = new String[last-first+1];
			for(int i = 0, actualValue = first; actualValue <= last; ++i, ++actualValue) {
				String temp = String.valueOf(actualValue);
				if(temp.length() < amountOfChar)
					for(int j = amountOfChar - temp.length(); j > 0; --j)
						temp = "0" + temp;
				filled[i] = String.valueOf(actualValue);
			}
		}else {
			filled = new String[1];
			String temp = String.valueOf(first);
			if(temp.length() == 1)
				filled[0] = String.valueOf("0" + first);
			else
				filled[0] = String.valueOf(first);
		}
		return filled;
	}
	
	//Retorna el nombre completo del mes dado su valor numerico.
	public static String getFullMonthName(int monthValue) {
		if(monthValue == 1) {
			return "Enero";
		}else if(monthValue == 2) {
			return "Febrero";
		}else if(monthValue == 3) {
			return "Marzo";
		}else if(monthValue == 4) {
			return "Abril";
		}else if(monthValue == 5) {
			return "Mayo";
		}else if(monthValue == 6) {
			return "Junio";
		}else if(monthValue == 7) {
			return "Julio";
		}else if(monthValue == 8) {
			return "Agosto";
		}else if(monthValue == 9) {
			return "Septiembre";
		}else if(monthValue == 10) {
			return "Octubre";
		}else if(monthValue == 11) {
			return "Noviembre";
		}else if(monthValue == 12) {
			return "Diciembre";
		}else {
			return "0";
		}
	}
	
	//Retorna el nombre mes dado su valor numerico.
	public static String getMonthName(int monthValue) {
		if(monthValue == 1) {
			return "Ene";
		}else if(monthValue == 2) {
			return "Feb";
		}else if(monthValue == 3) {
			return "Mar";
		}else if(monthValue == 4) {
			return "Abr";
		}else if(monthValue == 5) {
			return "May";
		}else if(monthValue == 6) {
			return "Jun";
		}else if(monthValue == 7) {
			return "Jul";
		}else if(monthValue == 8) {
			return "Ago";
		}else if(monthValue == 9) {
			return "Sep";
		}else if(monthValue == 10) {
			return "Oct";
		}else if(monthValue == 11) {
			return "Nov";
		}else if(monthValue == 12) {
			return "Dic";
		}else {
			return "0";
		}
	}
	
	//Retorna el valor numerico del mes en forma de String
	public static String getMonthValue(String monthName) {
		if(monthName.equals("Ene")) {
			return "01";
		}else if(monthName.equals("Feb")) {
			return "02";
		}else if(monthName.equals("Mar")) {
			return "03";
		}else if(monthName.equals("Abr")) {
			return "04";
		}else if(monthName.equals("May")) {
			return "05";
		}else if(monthName.equals("Jun")) {
			return "06";
		}else if(monthName.equals("Jul")) {
			return "07";
		}else if(monthName.equals("Ago")) {
			return "08";
		}else if(monthName.equals("Sep")) {
			return "09";
		}else if(monthName.equals("Oct")) {
			return "10";
		}else if(monthName.equals("Nov")) {
			return "11";
		}else if(monthName.equals("Dic")) {
			return "12";
		}else {
			return "0";
		}
	}
	
	//Capitaliza la primera letra al inicio y luego de cada espacio.
	public static String firstToUpper(String str) {
		ArrayList<String> upper = StringUtilities.stringTokenizer(str," ");
		int size = upper.size();
		str = upper.get(0).substring(0, 1).toUpperCase() + upper.get(0).substring(1);
		for(int i = 1; i < size; ++i)
			str = str + " " + upper.get(i).substring(0, 1).toUpperCase() + upper.get(i).substring(1);
		return str;
	}
	
	//Retorna el String mas alto segun orden alfabetico (Ej: 'b' > 'a')
	public static String max(String a, String b) {
		int a_length = a.length();
		int b_length = b.length();
		int lowest = Math.min(a_length, b_length);
		for(int i = 0; i < lowest; ++i) {
			char a_char = Character.toLowerCase(a.charAt(i)), b_char = Character.toLowerCase(b.charAt(i));
			if(a_char > b_char)
				return a;
			if(a_char < b_char)
				return b;
		}
		if(a_length > b_length)
			return a;
		return b;
	}
	
	//Retorna el String mas bajo segun orden alfabetico (Ej: 'a' < 'b')
	public static String min(String a, String b) {
		int a_length = a.length();
		int b_length = b.length();
		int lowest = Math.min(a_length, b_length);
		for(int i = 0; i < lowest; ++i) {
			char a_char = Character.toLowerCase(a.charAt(i)), b_char = Character.toLowerCase(b.charAt(i));
			if(a_char < b_char)
				return a;
			if(a_char > b_char)
				return b;
		}
		if(a_length < b_length)
			return a;
		return b;
	}
}