package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dataHandler.SingleSale;
import utilities.SwingUtilities;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JScrollPane;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class SingleSaleWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnAdd;
	private JButton btnCancel;
	private JLabel lblAmount;
	private JLabel lblDetail;
	private JLabel lblSale;
	private JLabel lblTitle;
	private JSeparator separator;
	private JTextArea textAreaDetail;
	private JTextField textFieldAmount;
	private JTextField textFieldSale;
	private SingleSale ss = null;
	private JLabel label;
	private JScrollPane scrollPane;

	public SingleSaleWindow() {
		try {
			initialize();
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
	
	public SingleSaleWindow(String total, String amount, String detail) {
		try {
			initialize();
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
			textFieldAmount.setText(amount);
			textFieldSale.setText(total);
			textAreaDetail.setText(detail);
			btnAdd.setText("Cambiar");
			setVisible(true);
		}catch(Exception ex) {
			String message = "Error fatal. Contacte con soporte.\nNombre:   Vicente Barr\u00EDa Veas\nE-mail:   v.barriaveas@gmail.com";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Initialize Single Sale Edit", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			dispose();
		};
	}
	
	private void initialize() {
		setBounds(100, 100, 299, 312);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblAmount = new JLabel("Cantidad:");
			lblAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblAmount.setBounds(10, 83, 81, 28);
			contentPanel.add(lblAmount);
		}
		{
			lblDetail = new JLabel("Detalle:");
			lblDetail.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDetail.setBounds(10, 122, 81, 28);
			contentPanel.add(lblDetail);
		}
		{
			lblSale = new JLabel("V. Neto:");
			lblSale.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSale.setBounds(10, 44, 72, 28);
			contentPanel.add(lblSale);
		}
		{
			lblTitle = new JLabel("Detalle de \u00CDtem de Venta");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitle.setBounds(10, 11, 231, 22);
			contentPanel.add(lblTitle);
		}
		{
			separator = new JSeparator();
			separator.setBounds(10, 30, 414, 2);
			contentPanel.add(separator);
		}
		{
			textAreaDetail = new JTextArea();
			textAreaDetail.setWrapStyleWord(true);
			textAreaDetail.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnAdd.doClick();
					}
				}
			});
			textAreaDetail.setLineWrap(true);
			textAreaDetail.setBounds(10, 143, 414, 85);
		}
		{
			textFieldAmount = SwingUtilities.getNumberTextField();
			textFieldAmount.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						textAreaDetail.requestFocus();
					}
				}
			});
			textFieldAmount.setToolTipText("Numero de factura (mayor o igual a 1)");
			textFieldAmount.setBounds(101, 83, 126, 28);
			contentPanel.add(textFieldAmount);
		}
		{
			textFieldSale = SwingUtilities.getNumberTextField();
			textFieldSale.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						textFieldAmount.requestFocus();
					}
				}
			});
			textFieldSale.setToolTipText("Monto total de un art\u00EDculo de la venta total");
			textFieldSale.setBounds(101, 44, 126, 28);
			contentPanel.add(textFieldSale);
		}
		{
			label = new JLabel("$");
			label.setFont(new Font("Tahoma", Font.BOLD, 15));
			label.setBounds(87, 44, 10, 22);
			contentPanel.add(label);
		}
		{
			scrollPane = new JScrollPane(textAreaDetail);
			scrollPane.setBounds(10, 149, 263, 85);
			contentPanel.add(scrollPane);
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCancel = new JButton("Cancelar");
				btnCancel.setMargin(new Insets(0, 0, 0, 0));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonPane.add(btnCancel);
			}
			{
				btnAdd = new JButton("Agregar");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldSale.getText().isEmpty() || textFieldAmount.getText().isEmpty() || textAreaDetail.getText().isEmpty()) {
							String message = "Debe llenar todos los campos antes de continuar.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Unfilled Fields", JOptionPane.INFORMATION_MESSAGE);
							if(textFieldSale.getText().isEmpty())
								textFieldSale.requestFocus();
							else if(textFieldAmount.getText().isEmpty())
								textFieldAmount.requestFocus();
							else
								textAreaDetail.requestFocus();
						}else {
							int amount = Integer.parseInt(textFieldAmount.getText()), total = Integer.parseInt(textFieldSale.getText());
							ss = new SingleSale(total, amount, textAreaDetail.getText());
							dispose();
						}
					}
				});
				btnAdd.setMargin(new Insets(0, 0, 0, 0));
				btnAdd.setForeground(new Color(0, 0, 0));
				btnAdd.setBackground(new Color(51, 153, 204));
				buttonPane.add(btnAdd);
			}
		}
	}
	
	public SingleSale getSingleSale() { return this.ss; }
}
