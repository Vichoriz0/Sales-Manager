package interfaces;

import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import Renderers.ButtonColumn;
import dataHandler.FileHandler;
import dataHandler.Sale;
import dataHandler.Stakeholder;
import utilities.DateUtil;
import utilities.NumberUtilities;
import utilities.StringUtilities;
import utilities.SwingUtilities;

public class MainWindow {

	private JFrame frame = new JFrame();
	private JButton btnAddSale;
	private JButton btnExit;
	private JComboBox<String> cbDD1;
	private JComboBox<String> cbDD2;
	private JComboBox<String> cbMM1;
	private JComboBox<String> cbMM2;
	private JComboBox<String> cbYY1;
	private JComboBox<String> cbYY2;
	private JLabel lblClient;
	private JLabel lblDateSeparator;
	private JLabel lblDateSeparator_1;
	private JLabel lblDateSeparator_2;
	private JLabel lblDateSeparator_3;
	private JLabel lblEndDate;
	private JLabel lblResume;
	private JLabel lblSales;
	private JLabel lblStartDate;
	private JLabel lblTitle;
	private JScrollPane scrollPaneResume;
	private JScrollPane scrollPaneSales;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JTable tableResume;
	private JTable tableSales;
	private JTextField textFieldClient;
	private TableRowSorter<DefaultTableModel> sorter;

	public MainWindow() {
		try {
			initialize();
			frame.setTitle("Sales Manager");
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}catch(Exception ex) {
			String message = "Error fatal. Contacte con soporte.\nNombre:   Vicente Barr\u00EDa Veas\nE-mail:   v.barriaveas@gmail.com";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Initialize Single Sale", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			frame.dispose();
		};
	}
	
	private void initialize() {
		frame.setBounds(100, 100, 1103, 571);
		frame.getContentPane().setLayout(null);
		{
			btnAddSale = new JButton("Agregar venta");
			btnAddSale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false);
					SaleWindow sale = new SaleWindow();
					frame.setVisible(true);
					Sale aux = sale.getSale();
					if(aux != null) {
						Sale.insertSale(aux);
						insertValuesTableSale(aux);
						updateResume();
					}
				}
			});
			btnAddSale.setToolTipText("Agregar venta");
			btnAddSale.setMargin(new Insets(0, 0, 0, 0));
			btnAddSale.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAddSale.setBounds(951, 249, 126, 22);
			frame.getContentPane().add(btnAddSale);
		}
		{
			btnExit = new JButton("Salir");
			btnExit.setBackground(new Color(0, 153, 204));
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
				}
			});
			btnExit.setActionCommand("Cancel");
			btnExit.setMargin(new Insets(0, 0, 0, 0));
			btnExit.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnExit.setBounds(1008, 502, 69, 28);
			frame.getContentPane().add(btnExit);
		}
		{
			cbDD1 = SwingUtilities.getDayComboBox();
			cbDD1.setBounds(111, 47, 54, 28);
			cbDD1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					prepareFilter();
				}
			});
			frame.getContentPane().add(cbDD1);
		}
		{
			cbDD2 = SwingUtilities.getDayComboBox();
			cbDD2.setBounds(111, 87, 54, 28);
			cbDD2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					prepareFilter();
				}
			});
			frame.getContentPane().add(cbDD2);
		}
		{
			cbMM1 = SwingUtilities.getMonthComboBox();
			cbMM1.setBounds(195, 47, 67, 28);
			cbMM1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.checkAmountOfDays(cbDD1, cbMM1, cbYY1);
					prepareFilter();
				}
			});
			frame.getContentPane().add(cbMM1);
		}
		{
			cbMM2 = SwingUtilities.getMonthComboBox();
			cbMM2.setBounds(195, 87, 67, 28);
			cbMM2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.checkAmountOfDays(cbDD2, cbMM2, cbYY2);
					prepareFilter();
				}
			});
			frame.getContentPane().add(cbMM2);
		}
		{
			cbYY1 = SwingUtilities.getYearComboBox();
			cbYY1.setBounds(294, 47, 65, 28);
			cbYY1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.checkLeapYear(cbDD1, cbMM1, cbYY1);
					prepareFilter();
				}
			});
			frame.getContentPane().add(cbYY1);
		}
		{
			cbYY2 = SwingUtilities.getYearComboBox();
			cbYY2.setBounds(294, 87, 65, 28);
			cbYY2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.checkLeapYear(cbDD2, cbMM2, cbYY2);
					prepareFilter();
				}
			});
			frame.getContentPane().add(cbYY2);
		}
		{
			lblClient = new JLabel("Buscar cliente:");
			lblClient.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblClient.setBounds(551, 47, 103, 28);
			frame.getContentPane().add(lblClient);
		}
		{
			lblDateSeparator = new JLabel("/");
			lblDateSeparator.setBounds(178, 51, 16, 20);
			frame.getContentPane().add(lblDateSeparator);
		}
		{
			lblDateSeparator_1 = new JLabel("/");
			lblDateSeparator_1.setBounds(178, 91, 16, 20);
			frame.getContentPane().add(lblDateSeparator_1);
		}
		{
			lblDateSeparator_2 = new JLabel("/");
			lblDateSeparator_2.setBounds(277, 51, 16, 20);
			frame.getContentPane().add(lblDateSeparator_2);
		}
		{
			lblDateSeparator_3 = new JLabel("/");
			lblDateSeparator_3.setBounds(277, 91, 16, 20);
			frame.getContentPane().add(lblDateSeparator_3);
		}
		{
			lblEndDate = new JLabel("T\u00E9rmino:");
			lblEndDate.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblEndDate.setBounds(10, 87, 103, 28);
			frame.getContentPane().add(lblEndDate);
		}
		{
			lblResume = new JLabel("Resumen:");
			lblResume.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblResume.setBounds(10, 146, 69, 28);
			frame.getContentPane().add(lblResume);
		}
		{
			lblSales = new JLabel("Listado de Ventas:");
			lblSales.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSales.setBounds(10, 249, 126, 28);
			frame.getContentPane().add(lblSales);
		}
		{
			lblStartDate = new JLabel("Inicio:");
			lblStartDate.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblStartDate.setBounds(10, 47, 91, 28);
			frame.getContentPane().add(lblStartDate);
		}
		{
			lblTitle = new JLabel("Bienvenido");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitle.setBounds(10, 11, 126, 22);
			frame.getContentPane().add(lblTitle);
		}
		{
			separator = new JSeparator();
			separator.setBounds(10, 30, 1067, 2);
			frame.getContentPane().add(separator);
		}
		{
			separator_1 = new JSeparator();
			separator_1.setBounds(10, 129, 1067, 2);
			frame.getContentPane().add(separator_1);
		}
		{
			separator_2 = new JSeparator();
			separator_2.setBounds(10, 232, 1067, 2);
			frame.getContentPane().add(separator_2);
		}
		{
			tableResume = new JTable();
			tableResume.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Trabajos realizados", "Mejor cliente", "Total neto", "Utilidad / P\u00E9rdida total", "% Margen Bruto total"}));
			tableResume.setDefaultEditor(Object.class, null);
			tableResume.setBounds(10, 106, 812, 53);
			SwingUtilities.setJTableColumnsWidth(tableResume, 812, 10, 47, 15, 15, 13);
		}
		{
			@SuppressWarnings("serial")
			Action deleteSale = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					int modelRow = tableSales.getSelectedRow();
					if(JOptionPane.showConfirmDialog(null,"\u00BFDesea eliminar este elemento?", "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						int ot = Integer.parseInt(tableSales.getValueAt(modelRow, 0).toString());
						((DefaultTableModel) tableSales.getModel()).removeRow(tableSales.convertRowIndexToModel(modelRow));
						Sale.removeSale(ot);
						updateResume();
					}
				}
			};
			
			@SuppressWarnings("serial")
			DefaultTableModel model = new DefaultTableModel(insertValuesTableSale(), new String[] {"Orden", "N\u00B0 Factura", "Cliente", "Fecha", "Monto neto", "Utilidad / P\u00E9rdida", "% Margen Bruto", "Eliminar"}) {
				@Override
				public Class<?> getColumnClass(int column) {
					switch(column) {
						case 0:
							return Integer.class;
						case 1:
							return Integer.class;
						case 3:
							return Date.class;
						case 6:
							return Double.class;
						default:
							return String.class;
					}
				}
			};
			
			tableSales = new JTable(model);
			sorter = new TableRowSorter<DefaultTableModel>(((DefaultTableModel) tableSales.getModel()));
			ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
			sorter.setSortKeys(sortKeys);
			tableSales.setRowSorter(sorter);
			setFilters("", Date.valueOf(getStartDate()), Date.valueOf(getEndDate()));
			tableSales.setDefaultEditor(Object.class, null);
			tableSales.setDefaultEditor(Number.class, null);
			ButtonColumn buttonColumnSingleSale = new ButtonColumn(tableSales, deleteSale, 7);
			buttonColumnSingleSale.setMnemonic(KeyEvent.VK_D);
			tableSales.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					int row = tableSales.getSelectedRow();
					if(mouseEvent.getClickCount() == 2 && row != -1) {
						int ot = Integer.parseInt(tableSales.getValueAt(row, 0).toString());
						int pos = Sale.indexOf(ot);
						frame.setVisible(false);
						if(pos != -1) {
							SaleInfoWindow siw = new SaleInfoWindow(new Sale(FileHandler.getSales().get(pos)));
							if(siw.getChanged()) {
								Sale aux = siw.getSale();
								Sale.editSale(pos, aux);
								tableSales.setValueAt((Object) aux.getOT(), row, 0);
								tableSales.setValueAt((Object) aux.getId(), row, 1);
								tableSales.setValueAt((Object) Stakeholder.search(FileHandler.getClients(), FileHandler.amountOfClients, aux.getIdClient()), row, 2);
								tableSales.setValueAt((Object) Date.valueOf(aux.getDate()), row, 3);
								tableSales.setValueAt((Object) "$ "+String.format("%,d", aux.getTotalSale()), row, 4);
								tableSales.setValueAt((Object) "$ "+String.format("%,d", aux.getProfit()), row, 5);
								tableSales.setValueAt((Object) aux.getMargin(), row, 6);
								updateResume();
							}
						}
						frame.setVisible(true);
					}
				}
			});
			SwingUtilities.setJTableColumnsWidth(tableSales, 812, 6, 6, 34, 10, 13, 13, 13, 5);
		}
		{
			textFieldClient = new JTextField();
			textFieldClient.addKeyListener(new KeyAdapter() {
				@Override
				//Va filtrando la tabla de ventas por el valor del campo de ventas, segun se vaya escribiendo en este.
				public void keyTyped(KeyEvent arg0) {
					String key = Character.toString(arg0.getKeyChar()), str = textFieldClient.getText();
					if(key.matches("^[a-zA-Z0-9]"))
						str = str + key;
					setFilters(str, Date.valueOf(getStartDate()), Date.valueOf(getEndDate()));
				}
			});
			textFieldClient.setBounds(645, 47, 156, 28);
			frame.getContentPane().add(textFieldClient);
			textFieldClient.setColumns(10);
		}
		{
			scrollPaneResume = new JScrollPane(tableResume);
			scrollPaneResume.setBounds(10, 171, 1067, 44);
			frame.getContentPane().add(scrollPaneResume);
		}
		{
			scrollPaneSales = new JScrollPane(tableSales);
			scrollPaneSales.setBounds(10, 274, 1067, 217);
			frame.getContentPane().add(scrollPaneSales);
		}
	}
	
	//Captura la fecha de los JComboBox y los pasa a la funcion para establecer los filtros.
	public void prepareFilter() {
		Date startDate = Date.valueOf(getStartDate());
		Date endDate = Date.valueOf(getEndDate());
		setFilters(textFieldClient.getText(), startDate, endDate);
	}
	
	//Retorna como LocalDate la fecha de inicio para filtrar.
	public LocalDate getStartDate() {
		String date = StringUtilities.getDate(cbDD1.getSelectedItem().toString(), StringUtilities.getMonthValue(cbMM1.getSelectedItem().toString()), cbYY1.getSelectedItem().toString());
		return LocalDate.parse(date);
	}
	
	//Retorna como LocalDate la fecha de termina para filtrar.
	public LocalDate getEndDate() {
		String date = StringUtilities.getDate(cbDD2.getSelectedItem().toString(), StringUtilities.getMonthValue(cbMM2.getSelectedItem().toString()), cbYY2.getSelectedItem().toString());
		return LocalDate.parse(date);
	}
	
	//Setea los filtros de la JTable de ventas.
	public void setFilters(String str, Date startDate, Date endDate) {
		ArrayList<RowFilter<Object,Object>> filters = new ArrayList<>(3);
		filters.add(RowFilter.regexFilter("(?i)^" + str, 2));
		filters.add(RowFilter.dateFilter(ComparisonType.AFTER, DateUtil.addDays(startDate,-1), 3));
		filters.add(RowFilter.dateFilter(ComparisonType.BEFORE, DateUtil.addDays(endDate,1), 3));
		RowFilter<Object,Object> rf = RowFilter.andFilter(filters);
		sorter.setRowFilter(rf);
		updateResume();
	}
	
	//Inserta una nueva venta a la tabla destinada a ventas.
	private void insertValuesTableSale(Sale s) {
		DefaultTableModel model = (DefaultTableModel) tableSales.getModel();
		int id = s.getId();
		int ot = s.getOT();
		double margin = s.getMargin();
		String client = Stakeholder.search(FileHandler.getClients(), FileHandler.amountOfClients, s.getIdClient());
		Date date = Date.valueOf(s.getDate());
		String totalSale = "$ " + String.format("%,d", s.getTotalSale());
		String profit = "$ " + String.format("%,d", s.getProfit());
		model.addRow(new Object[] {new Integer(ot), new Integer(id), client, date, totalSale, profit, new Double(margin)});
	}
	
	//Inserta todas las ventas a la tabla destinada a ventas, una vez que el programa se ejecute.
	private Object[][] insertValuesTableSale() {
		Object[][] data = new Object[FileHandler.amountOfSales][7];
		for(int i = 0; i < FileHandler.amountOfSales; ++i) {
			Sale s = FileHandler.getSales().get(i);
			int id = s.getId();
			int ot = s.getOT();
			double margin = s.getMargin();
			String client = Stakeholder.search(FileHandler.getClients(), FileHandler.amountOfClients, s.getIdClient());
			Date date = Date.valueOf(s.getDate());
			String totalSale = "$ " + String.format("%,d", s.getTotalSale());
			String profit = "$ " + String.format("%,d", s.getProfit());
			data[i] = new Object[] {new Integer(ot), new Integer(id), client, date, totalSale, profit, new Double(margin)};
		}
		return data;
	}
	
	//Retorna el nombre y cantidad del cliente que mas se repite segun el periodo seleccionado.
	private String getBestClient(ArrayList<String> bc, ArrayList<Integer> bcc, int size) {
		if(size == 0) {
			return "N/A";
		}else if(size == 1) {
			return bc.get(0) + " (" + bcc.get(0) + ")";
		}else {
			int pos = 0;
			String client = bc.get(0);
			for(int i = 1; i < size; ++i) {
				if(bcc.get(i) == bcc.get(pos)) {
					client = client + " ; " + bc.get(i);
				}else if(bcc.get(i) > bcc.get(pos)) {
					pos = i;
					client = bc.get(i);
				}
			}
			return client + " (" + bcc.get(pos) + ")";	
		}
	}
	
	//Entrega los datos de la tabla resumen para su actualizacion.
	private String[] getResumeValues() {
		int size = tableSales.getRowCount(), total = 0, profit = 0, amountOfClients = 0, workDone = 0;
		String[] val = new String[5];
		ArrayList<String> bestClient = new ArrayList<>();
		ArrayList<Integer> ct = new ArrayList<>();
		for(int i = 0; i < size; ++i) {
			int id = Integer.parseInt(tableSales.getValueAt(i, 1).toString());
			if(id != 0) {
				String client = tableSales.getValueAt(i, 2).toString();
				int pos = bestClient.indexOf(client);
				total += Integer.parseInt(tableSales.getValueAt(i, 4).toString().replaceAll("[$ .]", ""));
				profit += Integer.parseInt(tableSales.getValueAt(i, 5).toString().replaceAll("[$ .]", ""));
				workDone++;
				if(pos != -1) {
					int valueAtPos = ct.get(pos) + 1;
					ct.set(pos, valueAtPos);
				}else {
					if(client.equals("N/A"))
						continue;
					bestClient.add(client.toString());
					ct.add(1);
					amountOfClients++;
				}
			}
		}
		val[0] = String.format("%,d", workDone);
		val[1] = getBestClient(bestClient, ct, amountOfClients);
		val[2] = "$ " + String.format("%,d", total);
		val[3] = "$ " + String.format("%,d", profit);
		val[4] = String.valueOf(NumberUtilities.round((double) profit / total * 100, 2));
		return val;
	}
	
	//Actualiza la tabla destinada al resumen del periodo seleccionado.
	private void updateResume() {
		DefaultTableModel model = (DefaultTableModel) tableResume.getModel();
		int size = model.getRowCount();
		String[] val = getResumeValues();
		if(size == 0) {
			model.addRow(new Object[] {val[0], val[1], val[2], val[3], val[4]});
		}else{
			model.setValueAt((Object) val[0], 0, 0);
			model.setValueAt((Object) val[1], 0, 1);
			model.setValueAt((Object) val[2], 0, 2);
			model.setValueAt((Object) val[3], 0, 3);
			model.setValueAt((Object) val[4], 0, 4);
		}
	}
}
