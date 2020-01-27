package utilities;

public class NumberUtilities {
	
	//Retorna el valor double de entrada con la precision especificada. Extraido de https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
	public static double round(double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	//Retorna 0.0 para los casos en que la division es indeterminada.
	public static double safeDivision(double a, double b) {
		if(b > 0)
			 return a/b;
		return 0.0;
	}
	
	//Verifica si "str" corresponde a un numero valido. Si hasNegative es falso, retorna falso para numeros negativos.
	public static boolean isNumber(String str, boolean hasNegative) {
		try {
			if(Integer.parseInt(str) < 0 && !hasNegative)
				return false;
			return true;
		}catch(Exception e) {
			return false;
		}
	}
		
	//Verifica si "str" corresponde a un decimal valido.
	public static boolean isDecimal(String str, boolean hasNegative) {
		try {
			if(Double.parseDouble(str) < 0 && !hasNegative)
				return false;
			return true;
		}catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	//Entrega el valor numerico segun String monthName
	public static int getMonthValue(String monthName) {
		if(monthName.equals("Ene") || monthName.equals("01")) {
			return 1;
		}else if(monthName.equals("Feb") || monthName.equals("02")) {
			return 2;
		}else if(monthName.equals("Mar") || monthName.equals("03")) {
			return 3;
		}else if(monthName.equals("Abr") || monthName.equals("04")) {
			return 4;
		}else if(monthName.equals("May") || monthName.equals("05")) {
			return 5;
		}else if(monthName.equals("Jun") || monthName.equals("06")) {
			return 6;
		}else if(monthName.equals("Jul") || monthName.equals("07")) {
			return 7;
		}else if(monthName.equals("Ago") || monthName.equals("08")) {
			return 8;
		}else if(monthName.equals("Sep") || monthName.equals("09")) {
			return 9;
		}else if(monthName.equals("Oct") || monthName.equals("10")) {
			return 10;
		}else if(monthName.equals("Nov") || monthName.equals("11")) {
			return 11;
		}else if(monthName.equals("Dic") || monthName.equals("12")) {
			return 12;
		}else {
			return 0;
		}
	}
}