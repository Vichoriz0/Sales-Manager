package Renderers;

//Extraido de no recuerdo, pero no es de mi autoria :(

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import dataHandler.Category;
import dataHandler.Stakeholder;

public class CustomListCellRenderer extends DefaultListCellRenderer {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if(value instanceof Stakeholder) {
			value = ((Stakeholder)value).getName();
		}else if(value instanceof Category) {
			value = ((Category)value).getName();
		}
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}
