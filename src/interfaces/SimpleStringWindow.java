package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import utilities.StringUtilities;
import java.awt.FlowLayout;
import javax.swing.JSeparator;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class SimpleStringWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnAdd;
	private JButton btnCancel;
	private JLabel lblInsert;
	private JLabel lblStr;
	private JSeparator separator;
	private JTextField textFieldStr;
	private String str = "";

	public SimpleStringWindow(String lbl) {
		try {
			initialize(lbl);
			setTitle("Sales Manager");
			setModal(true);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setAlwaysOnTop(true);
			addWindowFocusListener(new WindowFocusListener() {
				public void windowGainedFocus(WindowEvent arg0) {
				}
				public void windowLostFocus(WindowEvent arg0) {
					setAlwaysOnTop(false);
				}
			});
			setVisible(true);
		}catch(Exception ex) {
			String message = "Error fatal. Contacte con soporte.\nNombre:   Vicente Barr\u00EDa Veas\nE-mail:   v.barriaveas@gmail.com";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Initialize Single Sale", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			dispose();
		};
	}
	
	public void initialize(String lbl) {
		setBounds(100, 100, 318, 183);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblInsert = new JLabel(lbl);
			lblInsert.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblInsert.setBounds(10, 11, 282, 22);
			contentPanel.add(lblInsert);
		}
		{	
			lblStr = new JLabel("Ingrese nombre:");
			lblStr.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblStr.setBounds(10, 44, 122, 22);
			contentPanel.add(lblStr);
		}
		{
			separator = new JSeparator();
			separator.setBounds(10, 30, 282, 2);
			contentPanel.add(separator);
		}
		{
			textFieldStr = new JTextField();
			textFieldStr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnAdd.doClick();
				}
			});
			textFieldStr.setBounds(10, 66, 282, 28);
			contentPanel.add(textFieldStr);
			textFieldStr.setColumns(10);
		}
		
		buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		{
			btnCancel = new JButton("Cancelar");
			btnCancel.setMargin(new Insets(0, 0, 0, 0));
			btnCancel.setBounds(227, 12, 71, 22);
			buttonPane.add(btnCancel);
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		{
			btnAdd = new JButton("Agregar");
			btnAdd.setMargin(new Insets(0, 0, 0, 0));
			btnAdd.setBounds(142, 12, 75, 22);
			buttonPane.add(btnAdd);
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(textFieldStr.getText().isEmpty()) {
						String message = "Debe llenar el campo antes de continuar.";
						JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Unfilled Fields", JOptionPane.INFORMATION_MESSAGE);
					}else {
						str = StringUtilities.firstToUpper(textFieldStr.getText());
						dispose();
					}
				}
			});
			btnAdd.setForeground(new Color(0, 0, 0));
			btnAdd.setBackground(new Color(51, 153, 204));
		}
	}
	
	public String getStr() { return this.str; }
}
