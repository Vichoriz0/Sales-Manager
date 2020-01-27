package utilities;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

public class SwingUtilities {

	public static LocalDate date = LocalDate.now();
	
	//Retorna el JComboBox con los dias en el mes actual.
	public static JComboBox<String> getDayComboBox() {
		JComboBox<String> cb = new JComboBox<String>();
		cb.setModel(new DefaultComboBoxModel<String>(StringUtilities.numberFiller(1, date.lengthOfMonth())));
		cb.getModel().setSelectedItem(date.getDayOfMonth());
		return cb;
	}
	
	//Retorna el JComboBox con los meses del ano. En su selecciona modifica cbDD para que tenga la lista de dias correspondientes.
	public static JComboBox<String> getMonthComboBox() {
		JComboBox<String> cb = new JComboBox<String>();
		cb.setModel(new DefaultComboBoxModel<String>(new String[] {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"}));
		cb.getModel().setSelectedItem(StringUtilities.getMonthName(date.getMonthValue()));
		return cb;
	}
	
	//Retorna el JComboBox con los anos desde 1950. En su seleccion verifica si es un ano bisiesto, para que agregar al modelo de cbDD.
	public static JComboBox<String> getYearComboBox() {
		JComboBox<String> cb = new JComboBox<String>();
		cb.setModel(new DefaultComboBoxModel<String>(StringUtilities.numberFiller(date.getYear(), 1950)));
		cb.getModel().setSelectedItem(date.getYear());
		return cb;
	}

	//Retorna un JTextField que solo permite valores numericos positivos. Cada vez que gana atencion, se selecciona lo que este escrito.
	public static JTextField getNumberTextField() {
		JTextField tf = new JTextField();
		tf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String str = tf.getText();
				if(!str.isEmpty()){
					str = str.replaceAll("[^0-9]*", "");
					if(NumberUtilities.isNumber(str,false))
						tf.setText(str);
					else
						tf.setText("");
				}
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				tf.selectAll();
			}
		});
		tf.setColumns(10);
		return tf;
	}

	//Modifica el JComboBox cbDD (en caso de febrero) para que tenga la misma cantidad de dias del anio seleccionado
	public static void checkLeapYear(JComboBox<String> cbDD, JComboBox<String> cbMM, JComboBox<String> cbYY) {
		if(cbMM.getSelectedItem().toString().equals("Feb")) {
			String selectedYear = cbYY.getSelectedItem().toString();
			int daysInMonth = LocalDate.parse(selectedYear + "-02-01").lengthOfMonth();
			if(cbDD.getItemCount() != daysInMonth) {
				int selectedDay = Integer.parseInt(cbDD.getSelectedItem().toString());
				cbDD.setModel(new DefaultComboBoxModel<String>(StringUtilities.numberFiller(1, daysInMonth)));
				if(selectedDay <= daysInMonth)
					cbDD.getModel().setSelectedItem(selectedDay);
			}
		}
	}
	
	//Modifica el JComboBox cbDD para que tenga la cantidad de dias correctos.
	public static void checkAmountOfDays(JComboBox<String> cbDD, JComboBox<String> cbMM, JComboBox<String> cbYY) {
		int mv = NumberUtilities.getMonthValue(cbMM.getSelectedItem().toString()); 	//Valor numerico del mes
		int ne = cbDD.getItemCount(); 												//Cantidad de dias en el ComboBox para dia
		int selectedDay = Integer.parseInt(cbDD.getSelectedItem().toString());		//Dia que esta seleccionado en el ComboBox para dia
		if(mv == 1 || mv == 3 || mv == 5 || mv == 7 || mv == 8 || mv == 10 || mv == 12) {
			if(ne != 31)
				ne = 31;
		}else if(mv == 4 || mv == 6 || mv == 9 || mv == 11) {
			if(ne != 30)
				ne = 30;
		}else {
			String selectedYear = cbYY.getSelectedItem().toString();
			ne = LocalDate.parse(selectedYear + "-02-01").lengthOfMonth();
		}
		cbDD.setModel(new DefaultComboBoxModel<String>(StringUtilities.numberFiller(1, ne)));
		if(selectedDay <= ne)
			cbDD.getModel().setSelectedItem(selectedDay);
	}
	
	//Establece porcentualmente el ancho de cada columna de una JTable. Extraido de https://www.codejava.net/java-se/swing/setting-column-width-and-row-height-for-jtable
	public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
	    double total = 0;
	    for(int i = 0; i < table.getColumnModel().getColumnCount(); i++)
	        total += percentages[i];
	    for(int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	    	TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
	    }
	}
}