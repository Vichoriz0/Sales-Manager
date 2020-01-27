package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import dataHandler.Purchase;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PurchaseInfoWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnOk;
	private JLabel lblAmount;
	private JLabel lblBillId;
	private JLabel lblCategory;
	private JLabel lblDetail;
	private JLabel lblPercentage;
	private JLabel lblSeller;
	private JLabel lblTitle;
	private JLabel lblTotal;
	private JSeparator separator_1;
	private JTextArea textAreaDetail;
	private JTextField textFieldAmount;
	private JTextField textFieldBillId;
	private JTextField textFieldCategory;
	private JTextField textFieldPercentage;
	private JTextField textFieldSeller;
	private JTextField textFieldTotal;
	private JScrollPane scrollPane;

	public PurchaseInfoWindow(Purchase p, String seller, String category) {
		try {
			initialize();
			fillLabels(p, seller, category);
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
	
	public void initialize() {
		setBounds(100, 100, 574, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblAmount = new JLabel("Cantidad:");
			lblAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblAmount.setBounds(10, 124, 81, 28);
			contentPanel.add(lblAmount);
		}
		{
			lblBillId = new JLabel("N\u00B0 Factura:");
			lblBillId.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblBillId.setBounds(10, 44, 81, 28);
			contentPanel.add(lblBillId);
		}
		{
			lblCategory = new JLabel("Categor\u00EDa:");
			lblCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblCategory.setBounds(232, 84, 81, 28);
			contentPanel.add(lblCategory);
		}
		{
			lblDetail = new JLabel("Detalle:");
			lblDetail.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDetail.setBounds(10, 164, 81, 28);
			contentPanel.add(lblDetail);
		}
		{
			lblPercentage = new JLabel("% Costo:");
			lblPercentage.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblPercentage.setBounds(232, 124, 81, 28);
			contentPanel.add(lblPercentage);
		}
		{
			lblSeller = new JLabel("Vendedor:");
			lblSeller.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSeller.setBounds(232, 44, 81, 28);
			contentPanel.add(lblSeller);
		}
		{
			lblTitle = new JLabel("Detalle de \u00CDtem de Compra");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitle.setBounds(10, 11, 263, 22);
			contentPanel.add(lblTitle);
		}
		{
			lblTotal = new JLabel("Monto:");
			lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTotal.setBounds(10, 84, 81, 28);
			contentPanel.add(lblTotal);
		}
		{
			separator_1 = new JSeparator();
			separator_1.setBounds(10, 31, 538, 2);
			contentPanel.add(separator_1);
		}
		{
			textAreaDetail = new JTextArea();
			textAreaDetail.setWrapStyleWord(true);
			textAreaDetail.setLineWrap(true);
			textAreaDetail.setText("<dynamic>");
			textAreaDetail.setEditable(false);
		}
		{
			scrollPane = new JScrollPane(textAreaDetail);
			scrollPane.setBounds(82, 164, 466, 96);
			contentPanel.add(scrollPane);
		}
		{
			textFieldAmount = new JTextField();
			textFieldAmount.setText("<dynamic>");
			textFieldAmount.setEditable(false);
			textFieldAmount.setColumns(10);
			textFieldAmount.setBounds(82, 124, 94, 28);
			contentPanel.add(textFieldAmount);
		}
		{
			textFieldBillId = new JTextField();
			textFieldBillId.setText("<dynamic>");
			textFieldBillId.setEditable(false);
			textFieldBillId.setColumns(10);
			textFieldBillId.setBounds(82, 44, 94, 28);
			contentPanel.add(textFieldBillId);
		}
		{
			textFieldCategory = new JTextField();
			textFieldCategory.setText("<dynamic>");
			textFieldCategory.setEditable(false);
			textFieldCategory.setColumns(10);
			textFieldCategory.setBounds(301, 84, 247, 28);
			contentPanel.add(textFieldCategory);
		}
		{
			textFieldPercentage = new JTextField();
			textFieldPercentage.setText("<dynamic>");
			textFieldPercentage.setEditable(false);
			textFieldPercentage.setColumns(10);
			textFieldPercentage.setBounds(301, 124, 94, 28);
			contentPanel.add(textFieldPercentage);
		}
		{
			textFieldSeller = new JTextField();
			textFieldSeller.setText("<dynamic>");
			textFieldSeller.setEditable(false);
			textFieldSeller.setColumns(10);
			textFieldSeller.setBounds(301, 44, 247, 28);
			contentPanel.add(textFieldSeller);
		}
		{
			textFieldTotal = new JTextField();
			textFieldTotal.setText("<dynamic>");
			textFieldTotal.setEditable(false);
			textFieldTotal.setColumns(10);
			textFieldTotal.setBounds(82, 84, 94, 28);
			contentPanel.add(textFieldTotal);
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
		}
	}
	
	public void fillLabels(Purchase p, String seller, String category) {
		textAreaDetail.setText(p.getDetail());
		textFieldAmount.setText(String.format("%,d", p.getAmount()));
		textFieldBillId.setText(String.format("%,d", p.getIdBill()));
		textFieldCategory.setText(category);
		textFieldPercentage.setText(String.valueOf(p.getPercentageOfCost()));
		textFieldSeller.setText(seller);
		textFieldTotal.setText(String.format("%,d", p.getPurchaseTotal()));
	}

}
