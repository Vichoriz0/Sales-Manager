package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dataHandler.Category;
import dataHandler.FileHandler;
import dataHandler.Purchase;
import dataHandler.Sale;
import dataHandler.SingleSale;
import dataHandler.Stakeholder;
import utilities.StringUtilities;
import utilities.SwingUtilities;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JScrollPane;

public class SaleInfoWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnBack;
	private JButton btnEdit;
	private JLabel lblBillId;
	private JLabel lblBillId_d;
	private JLabel lblClient;
	private JLabel lblClient_d;
	private JLabel lblCost;
	private JLabel lblCost_d;
	private JLabel lblDate;
	private JLabel lblDate_d;
	private JLabel lblMargin;
	private JLabel lblMargin_d;
	private JLabel lblOT;
	private JLabel lblOT_d;
	private JLabel lblProfit;
	private JLabel lblProfit_d;
	private JLabel lblTitle;
	private JLabel lblTotal;
	private JLabel lblTotal_d;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JScrollPane scrollPaneCost;
	private JScrollPane scrollPaneSell;
	private JTable tableCost;
	private JTable tableSell;
	private Sale sale;
	private boolean changed = false;

	public SaleInfoWindow(Sale s) {
		this.sale = new Sale(s);
		try {
			initialize();
			fillLabels();
			setTitle("Sales Manager");
			setModal(true);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setVisible(true);
		}catch(Exception ex) {
			String message = "Error fatal. Contacte con soporte.\nNombre:   Vicente Barr\u00EDa Veas\nE-mail:   v.barriaveas@gmail.com";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Initialize Single Sale", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			dispose();
		};
	}
	
	private void initialize() {
		setBounds(100, 100, 1018, 512);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblBillId = new JLabel("N\u00B0 Factura:");
			lblBillId.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblBillId.setBounds(10, 76, 81, 28);
			contentPanel.add(lblBillId);
		}
		{
			lblBillId_d = new JLabel("Factura ID");
			lblBillId_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblBillId_d.setBounds(200, 76, 130, 28);
			contentPanel.add(lblBillId_d);
		}
		{
			lblClient = new JLabel("Cliente:");
			lblClient.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblClient.setBounds(10, 105, 81, 28);
			contentPanel.add(lblClient);
		}
		{
			lblClient_d = new JLabel("Cliente");
			lblClient_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblClient_d.setBounds(200, 105, 242, 28);
			contentPanel.add(lblClient_d);
		}
		{
			lblCost = new JLabel("Costo de Venta Total:");
			lblCost.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblCost.setBounds(10, 251, 147, 28);
			contentPanel.add(lblCost);
		}
		{
			lblCost_d = new JLabel("$ Total Costo");
			lblCost_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCost_d.setBounds(155, 251, 136, 28);
			contentPanel.add(lblCost_d);
		}
		{
			lblDate = new JLabel("Fecha:");
			lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDate.setBounds(10, 134, 81, 28);
			contentPanel.add(lblDate);
		}
		{
			lblDate_d = new JLabel("Fecha");
			lblDate_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblDate_d.setBounds(200, 134, 332, 28);
			contentPanel.add(lblDate_d);
		}
		{
			lblMargin = new JLabel("Margen Bruto:");
			lblMargin.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblMargin.setBounds(10, 192, 122, 28);
			contentPanel.add(lblMargin);
		}
		{
			lblMargin_d = new JLabel("Margen");
			lblMargin_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblMargin_d.setBounds(200, 192, 130, 28);
			contentPanel.add(lblMargin_d);
		}
		{
			lblOT = new JLabel("Orden:");
			lblOT.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblOT.setBounds(10, 47, 81, 28);
			contentPanel.add(lblOT);
		}
		{
			lblOT_d = new JLabel("0");
			lblOT_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblOT_d.setBounds(200, 47, 130, 28);
			contentPanel.add(lblOT_d);
		}
		{
			lblProfit = new JLabel("<html><font color='green'>Utilidad</font>/<font color='red'>P\u00E9rdida</font>:</html>");
			lblProfit.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblProfit.setBounds(10, 163, 122, 28);
			contentPanel.add(lblProfit);
		}
		{
			lblProfit_d = new JLabel("Profit");
			lblProfit_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblProfit_d.setBounds(200, 163, 130, 28);
			contentPanel.add(lblProfit_d);
		}
		{
			lblTitle = new JLabel("Informaci\u00F3n de Venta");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitle.setBounds(10, 11, 215, 22);
			contentPanel.add(lblTitle);
		}
		{
			lblTotal = new JLabel("Monto total neto de las ventas:");
			lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTotal.setBounds(495, 47, 215, 28);
			contentPanel.add(lblTotal);
		}
		{
			lblTotal_d = new JLabel("$ Total Neto");
			lblTotal_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblTotal_d.setBounds(703, 46, 136, 28);
			contentPanel.add(lblTotal_d);
		}
		{
			separator = new JSeparator();
			separator.setBounds(10, 30, 982, 2);
			contentPanel.add(separator);
		}
		{
			separator_1 = new JSeparator();
			separator_1.setBounds(10, 234, 982, 2);
			contentPanel.add(separator_1);
		}
		{
			separator_2 = new JSeparator();
			separator_2.setOrientation(SwingConstants.VERTICAL);
			separator_2.setBounds(155, 45, 2, 178);
			contentPanel.add(separator_2);	
		}
		{
			@SuppressWarnings("serial")
			DefaultTableModel model = new DefaultTableModel(insertValuesTablePurchase(), new String[] {"Vendedor", "Categor\u00EDa", "Compra ($)", "% Costo"}) {
				@Override
				public Class<?> getColumnClass(int column) {
					switch(column) {
						case 3:
							return Double.class;
						default:
							return String.class;
					}
				}
			};
			
			tableCost = new JTable(model);
			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(((DefaultTableModel) tableCost.getModel()));
			ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
			sorter.setSortKeys(sortKeys);
			tableCost.setRowSorter(sorter);
			tableCost.setDefaultEditor(Object.class, null);
			tableCost.setDefaultEditor(Number.class, null);
			tableCost.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					int row = tableCost.getSelectedRow();
					if(mouseEvent.getClickCount() == 2 && row != -1) {
						DefaultTableModel model = (DefaultTableModel) tableCost.getModel();
						row = tableCost.convertRowIndexToModel(row);
						Purchase p = sale.getPurchases().get(row);
						String seller = model.getValueAt(row, 0).toString();
						String category = model.getValueAt(row, 1).toString();
						new PurchaseInfoWindow(p, seller, category);
					}
				}
			});
			SwingUtilities.setJTableColumnsWidth(tableCost, 982, 30, 30, 20, 20);
		}
		{
			tableSell = new JTable();
			tableSell.setModel(new DefaultTableModel(insertValuesTableSell(), new String[] {"Total","Cantidad","Detalle"}));
			tableSell.setDefaultEditor(Object.class, null);
			tableSell.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					int row = tableSell.getSelectedRow();
					if(mouseEvent.getClickCount() == 2 && row != -1) {
						DefaultTableModel model = (DefaultTableModel) tableSell.getModel();
						row = tableSell.convertRowIndexToModel(row);
						String total = model.getValueAt(row, 0).toString();
						String amount = model.getValueAt(row, 1).toString();
						String detail = model.getValueAt(row, 2).toString();
						new SingleSaleInfoWindow(total, amount, detail);
					}
				}
			});
			SwingUtilities.setJTableColumnsWidth(tableSell, 612, 15, 15, 70);
		}
		{
			scrollPaneCost = new JScrollPane(tableCost);
			scrollPaneCost.setBounds(10, 278, 982, 146);
			contentPanel.add(scrollPaneCost);
		} 
		{
			scrollPaneSell = new JScrollPane(tableSell);
			scrollPaneSell.setBounds(495, 74, 497, 146);
			contentPanel.add(scrollPaneSell);
			
		}
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(463, 42, 2, 178);
		contentPanel.add(separator_3);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnBack = new JButton("Volver");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnBack.setMargin(new Insets(0,0,0,0));
				buttonPane.add(btnBack);
			}
			{
				btnEdit = new JButton("Editar");
				btnEdit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						SaleWindow sw = new SaleWindow(sale);
						if(!sw.getCanceled() && !sale.equals(sw.getSale())) {
							sale = sw.getSale();
							changed = true;
							fillLabels();
							
						}
						setVisible(true);
					}
				});
				btnEdit.setMargin(new Insets(0, 0, 0, 0));
				btnEdit.setBounds(142, 12, 75, 22);
				btnEdit.setForeground(new Color(0, 0, 0));
				btnEdit.setBackground(new Color(51, 153, 204));
				buttonPane.add(btnEdit);
			}
		}
	}
	
	//Inserta todas las compras a la tabla destinada a compras.
	private Object[][] insertValuesTablePurchase() {
		int size = sale.getPurchases().size();
		Object[][] data = new Object[size][4];
		for(int i = 0; i < size; ++i) {
			Purchase p = sale.getPurchases().get(i);
			double percentage = p.getPercentageOfCost();
			String seller = Stakeholder.search(FileHandler.getSellers(), FileHandler.amountOfSellers, p.getIdSeller());
			String category = Category.search(p.getIdCategory());
			String totalPurchase = "$ " + String.format("%,d", p.getPurchaseTotal());
			data[i] = new Object[] {seller, category, totalPurchase, new Double(percentage)};
		}
		return data;
	}
	
	//Inserta todas las itemes de la venta a la tabla correspondiente.
	private Object[][] insertValuesTableSell() {
		int size = sale.getAllSales().size();
		Object[][] data = new Object[size][3];
		for(int i = 0; i < size; ++i) {
			SingleSale ss = sale.getAllSales().get(i);
			String total = "$ " + String.format("%,d", ss.getTotal());
			String amount = String.format("%,d", ss.getAmount());
			String detail = ss.getDetail();
			data[i] = new Object[] {total, amount, detail};
		}
		return data;
	}
	
	//Rellena las etiquetas con la informacion correspondiente.
	public void fillLabels() {
		String profit = "$ " + String.format("%,d", sale.getProfit()).replace("-", "");
		lblBillId_d.setText(String.format("%,d", sale.getId()));
		lblClient_d.setText(Stakeholder.search(FileHandler.getClients(), FileHandler.amountOfClients, sale.getIdClient()));
		lblClient_d.setToolTipText(lblClient_d.getText());
		lblOT_d.setText(String.format("%,d", sale.getOT()));
		lblDate_d.setText(StringUtilities.getFormattedDate(sale.getDate().toString()));
		lblMargin_d.setText(String.valueOf(sale.getMargin()) + "%");
		lblTotal_d.setText("$ " + String.format("%,d", sale.getTotalSale()));
		lblCost_d.setText("$ " + String.format("%,d", sale.getCostOfSale()));
		if(sale.getProfit() >= 0)
			lblProfit_d.setText("<html><font color='green'>" + profit + "</font></html>");
		else
			lblProfit_d.setText("<html><font color='red'>" + profit + "</font></html>");
		if(changed) {
			int size = sale.getPurchases().size();
			DefaultTableModel model = (DefaultTableModel) tableCost.getModel();
			model.setRowCount(0);
			for(int i = 0; i < size; ++i) {
				Purchase p = sale.getPurchases().get(i);
				double percentage = p.getPercentageOfCost();
				String seller = Stakeholder.search(FileHandler.getSellers(), FileHandler.amountOfSellers, p.getIdSeller());
				String category = Category.search(p.getIdCategory());
				String totalPurchase = "$ " + String.format("%,d", p.getPurchaseTotal());
				model.addRow(new Object[] {seller, category, totalPurchase, new Double(percentage)});
			}
			size = sale.getAllSales().size();
			model = (DefaultTableModel) tableSell.getModel();
			model.setRowCount(0);
			for(int i = 0; i < size; ++i) {
				SingleSale ss = sale.getAllSales().get(i);
				String total = "$ " + String.format("%,d", ss.getTotal());
				String amount = String.format("%,d", ss.getAmount());
				String detail = ss.getDetail();
				model.addRow(new Object[] {total, amount, detail});
			}
		}
	}
	
	public Sale getSale() { return this.sale; }
	public boolean getChanged() { return this.changed; }
}
