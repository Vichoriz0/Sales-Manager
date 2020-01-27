package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SingleSaleInfoWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnOk;
	private JLabel lblAmount;
	private JLabel lblDetail;
	private JLabel lblTitle;
	private JLabel lblTotal;
	private JSeparator separator;
	private JScrollPane scrollPaneDetail;
	private JTextArea textAreaDetail;
	private JTextField textFieldAmount;
	private JTextField textFieldTotal;

	public SingleSaleInfoWindow(String total, String amount, String detail) {
		try {
			initialize();
			fillLabels(total, amount, detail);
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
		setBounds(100, 100, 384, 252);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblAmount = new JLabel("Cantidad:");
			lblAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblAmount.setBounds(211, 44, 81, 28);
			contentPanel.add(lblAmount);
		}
		{
			lblDetail = new JLabel("Detalle:");
			lblDetail.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDetail.setBounds(10, 84, 81, 28);
			contentPanel.add(lblDetail);
		}
		{
			lblTitle = new JLabel("Detalle de \u00CDtem de Venta");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitle.setBounds(10, 11, 243, 22);
			contentPanel.add(lblTitle);
		}
		{
			lblTotal = new JLabel("Monto:");
			lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTotal.setBounds(10, 44, 81, 28);
			contentPanel.add(lblTotal);
		}
		{
			separator = new JSeparator();
			separator.setBounds(10, 29, 348, 2);
			contentPanel.add(separator);
		}
		{
			textAreaDetail = new JTextArea();
			textAreaDetail.setWrapStyleWord(true);
			textAreaDetail.setLineWrap(true);
			textAreaDetail.setEditable(false);
			textAreaDetail.setBounds(123, 101, 152, 56);
		}
		{
			scrollPaneDetail = new JScrollPane(textAreaDetail);
			scrollPaneDetail.setBounds(65, 84, 293, 85);
			contentPanel.add(scrollPaneDetail);
		}
		{
			textFieldAmount = new JTextField();
			textFieldAmount.setEditable(false);
			textFieldAmount.setBounds(277, 44, 81, 28);
			contentPanel.add(textFieldAmount);
			textFieldAmount.setColumns(10);
		}
		{
			textFieldTotal = new JTextField();
			textFieldTotal.setEditable(false);
			textFieldTotal.setColumns(10);
			textFieldTotal.setBounds(65, 44, 94, 28);
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
	
	public void fillLabels(String total, String amount, String detail) {
		textFieldAmount.setText(amount);
		textFieldTotal.setText(total);
		textAreaDetail.setText(detail);
	}
}
