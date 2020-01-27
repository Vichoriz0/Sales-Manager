package interfaces;

import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;

import Renderers.ButtonColumn;
import Renderers.CustomListCellRenderer;
import dataHandler.Category;
import dataHandler.FileHandler;
import dataHandler.Purchase;
import dataHandler.Sale;
import dataHandler.SingleSale;
import dataHandler.Stakeholder;
import utilities.NumberUtilities;
import utilities.StringUtilities;
import utilities.SwingUtilities;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class SaleWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnAddClient;
	private JButton btnAddPurchase;
	private JButton btnAddSale;
	private JButton btnAddSingleSale;
	private JButton btnDelClient;
	private JButton btnCancel;
	private JComboBox<Stakeholder> comboBoxClient;
	private JComboBox<String> comboBoxDateDD;
	private JComboBox<String> comboBoxDateMM;
	private JComboBox<String> comboBoxDateYY;
	private JLabel lblClient;
	private JLabel lblDate;
	private JLabel lblDateSeparator;
	private JLabel lblDateSeparator_1;
	private JLabel lblId;
	private JLabel lblMoneySymbol;
	private JLabel lblOT;
	private JLabel lblPurchaseTable;
	private JLabel lblSingleSaleTable;
	private JLabel lblTitle;
	private JLabel lblTotalSale;
	private JScrollPane scrollPaneSingleSale;
	private JScrollPane scrollPanePurchase;
	private JSpinner spinnerId;
	private JSpinner spinnerOT;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JTable tablePurchase;
	private JTable tableSingleSale;
	private JTextField textFieldTotalSale;
	private Sale s = null;
	private boolean canceled = false;
	private ArrayList<SingleSale> alss = new ArrayList<>();
	private ArrayList<Purchase> alp = new ArrayList<>();
	private ArrayList<Stakeholder> sh = new ArrayList<>(FileHandler.getClients());

	public SaleWindow() {
		try {
			initialize();
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
	
	public SaleWindow(Sale aux) {
		this.s = new Sale(aux);
		try {
			initialize();
			setModal(true);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setValues();
			setVisible(true);
		}catch(Exception ex) {
			String message = "Error fatal. Contacte con soporte.\nNombre:   Vicente Barr\u00EDa Veas\nE-mail:   v.barriaveas@gmail.com";
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Initialize Single Sale Edit", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			dispose();
		};
	}
	
	public void initialize() {
		setBounds(100, 100, 987, 628);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			btnAddClient = new JButton("+");
			btnAddClient.setToolTipText("Agregar un cliente");
			btnAddClient.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SimpleStringWindow dialog = new SimpleStringWindow("Nuevo Cliente");
					String name = dialog.getStr();
					if(!name.isEmpty()){
						Stakeholder aux = new Stakeholder(Stakeholder.getNextId(FileHandler.getClients(),FileHandler.amountOfClients), name);
						if(loadStakeholderComboBox(sh, aux, comboBoxClient, FileHandler.amountOfClients)) {
							FileHandler.getClients().add(aux);
							FileHandler.amountOfClients++;
							FileHandler.writeStakeholders(aux, FileHandler.getClientsFileDir());
						}else {
							String message = "Cliente " + name + " ya existe.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: Duplicated", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			btnAddClient.setMargin(new Insets(0,0,0,0));
			btnAddClient.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAddClient.setBounds(608, 50, 38, 22);
			contentPanel.add(btnAddClient);
		}
		{
			btnAddPurchase = new JButton("+");
			btnAddPurchase.setToolTipText("Agregar una compra");
			btnAddPurchase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					PurchaseWindow temp = new PurchaseWindow();
					ArrayList<Purchase> p = temp.getPurchase();
					if(!p.isEmpty()) {
						int size = p.size();
						String billId = String.valueOf(p.get(0).getIdBill());
						String sName = Stakeholder.search(FileHandler.getSellers(), FileHandler.amountOfSellers, p.get(0).getIdSeller());
						String message = "Ya existe una compra registrada del vendedor " + sName + ", con N\u00B0 de Factura " + billId + ".\n\u00BFDesea continuar?";
						DefaultTableModel model = (DefaultTableModel) tablePurchase.getModel();
						if(purchaseSearch(billId, sName) && JOptionPane.showConfirmDialog(null, message, "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
							return;
						for(int i = 0; i < size; ++i) {
							String cName = Category.search(p.get(i).getIdCategory());
							alp.add(p.get(i));
							model.addRow(new Object[] {billId, cName, sName, "$ "+p.get(i).getPurchaseTotal(), p.get(i).getAmount(), p.get(i).getDetail()});
						}
					}
				}
			});
			btnAddPurchase.setMargin(new Insets(0, 0, 0, 0));
			btnAddPurchase.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAddPurchase.setBounds(923, 316, 38, 22);
			contentPanel.add(btnAddPurchase);
		}
		{
			btnAddSingleSale = new JButton("+");
			btnAddSingleSale.setToolTipText("Agregar \u00EDtem a la venta");
			btnAddSingleSale.setMargin(new Insets(0, 0, 0, 0));
			btnAddSingleSale.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAddSingleSale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SingleSaleWindow temp = new SingleSaleWindow();
					if(temp.getSingleSale() != null) {
						DefaultTableModel model = (DefaultTableModel) tableSingleSale.getModel();
						if(singleSaleSearch(temp.getSingleSale()) && JOptionPane.showConfirmDialog(null, "Al parecer ya ha agregado este \u00EDtem. \u00BFDesea continuar?", "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
							return;
						alss.add(temp.getSingleSale());	
						model.addRow(new Object[] {"$ "+temp.getSingleSale().getTotal(), temp.getSingleSale().getAmount(), temp.getSingleSale().getDetail()});
					}
				}
			});
			btnAddSingleSale.setBounds(923, 189, 38, 22);
			contentPanel.add(btnAddSingleSale);
		}
		{
			btnDelClient = new JButton("-");
			btnDelClient.setToolTipText("Eliminar cliente seleccionado");
			btnDelClient.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int id;
					if((id = comboBoxClient.getSelectedIndex()) == 0 || id == -1) {
						String message = "Este elemento no se puede eliminar.";
						JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: Not Able to Delete", JOptionPane.INFORMATION_MESSAGE);
					}else {
						String message = "Cada venta registrada con este elemento cambiar\u00E1 su cliente por el valor por defecto (N/A).\n\u00BFDesea continuar?";
						if(JOptionPane.showConfirmDialog(null, message, "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							Stakeholder aux = (Stakeholder)comboBoxClient.getSelectedItem();
							if(Stakeholder.updateClientOnDelete(aux.getId()))
								FileHandler.writeSales("data/sales.temp.csv");
							sh.remove(id);
							comboBoxClient.removeItemAt(id);
							FileHandler.getClients().remove(aux);
							FileHandler.amountOfClients--;
							FileHandler.writeStakeholders(FileHandler.getClients(), "data/clients.temp.csv", FileHandler.getClientsFileDir());
						}
					}
				}
			});
			btnDelClient.setMargin(new Insets(0, 0, 0, 0));
			btnDelClient.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnDelClient.setBounds(651, 50, 38, 22);
			contentPanel.add(btnDelClient);
		}
		{
			comboBoxClient = new JComboBox<Stakeholder>();
			comboBoxClient.setBounds(357, 47, 246, 28);
			comboBoxClient.setRenderer(new CustomListCellRenderer());
			loadStakeholderComboBox(this.sh, this.comboBoxClient.getModel(), FileHandler.amountOfClients);
			contentPanel.add(comboBoxClient);
		}
		{
			comboBoxDateDD = SwingUtilities.getDayComboBox();
			comboBoxDateDD.setBounds(357, 87, 54, 28);
			contentPanel.add(comboBoxDateDD);
		}
		{	
			comboBoxDateMM = SwingUtilities.getMonthComboBox();
			comboBoxDateMM.setBounds(441, 87, 66, 28);
			comboBoxDateMM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.checkAmountOfDays(comboBoxDateDD, comboBoxDateMM, comboBoxDateYY);
				}
			});
			contentPanel.add(comboBoxDateMM);
		}
		{
			comboBoxDateYY = SwingUtilities.getYearComboBox();
			comboBoxDateYY.setBounds(539, 87, 65, 28);
			comboBoxDateYY.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.checkLeapYear(comboBoxDateDD, comboBoxDateMM, comboBoxDateYY);
				}
			});
			contentPanel.add(comboBoxDateYY);
		}
		{
			lblClient = new JLabel("Cliente:");
			lblClient.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblClient.setBounds(293, 47, 81, 28);
			contentPanel.add(lblClient);
		}
		{
			lblDate = new JLabel("Fecha:");
			lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDate.setBounds(293, 87, 64, 28);
			contentPanel.add(lblDate);
		}
		{
			lblDateSeparator = new JLabel("/");
			lblDateSeparator.setBounds(523, 89, 16, 20);
			contentPanel.add(lblDateSeparator);
		}
		{
			lblDateSeparator_1 = new JLabel("/");
			lblDateSeparator_1.setBounds(423, 89, 18, 20);
			contentPanel.add(lblDateSeparator_1);
		}
		{
			lblId = new JLabel("N\u00B0 Factura:");
			lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblId.setBounds(10, 87, 81, 28);
			contentPanel.add(lblId);
		}
		{
			lblMoneySymbol = new JLabel("$");
			lblMoneySymbol.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblMoneySymbol.setBounds(87, 127, 10, 22);
			contentPanel.add(lblMoneySymbol);
		}
		{
			lblOT = new JLabel("Orden:");
			lblOT.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblOT.setBounds(10, 47, 81, 28);
			contentPanel.add(lblOT);
		}
		{
			lblPurchaseTable = new JLabel("Listado de Compras de esta Venta:");
			lblPurchaseTable.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblPurchaseTable.setBounds(10, 316, 246, 22);
			contentPanel.add(lblPurchaseTable);
		}
		{	
			lblSingleSaleTable = new JLabel("Listado de \u00CDtemes de esta Venta:");
			lblSingleSaleTable.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSingleSaleTable.setBounds(10, 189, 231, 22);
			contentPanel.add(lblSingleSaleTable);
		}
		{
			lblTitle = new JLabel("Nueva Venta");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitle.setBounds(10, 11, 126, 22);
			contentPanel.add(lblTitle);
		}
		{
			lblTotalSale = new JLabel("V. Neto:");
			lblTotalSale.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTotalSale.setBounds(10, 127, 81, 28);
			contentPanel.add(lblTotalSale);
		}
		{
			separator = new JSeparator();
			separator.setBounds(10, 30, 951, 2);
			contentPanel.add(separator);
		}
		{
			separator_1 = new JSeparator();
			separator_1.setBounds(10, 169, 951, 2);
			contentPanel.add(separator_1);
		}
		{
			separator_2 = new JSeparator();
			separator_2.setBounds(10, 299, 951, 2);
			contentPanel.add(separator_2);
		}
		{
			spinnerId = new JSpinner();
			JComponent editor = spinnerId.getEditor();
			JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
			spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEADING);
			//spinnerEditor.getTextField().setText(String.valueOf(Sale.getNextId()));
			spinnerEditor.getTextField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						textFieldTotalSale.requestFocus();
					}
				}
			});
			spinnerId.setBounds(101, 87, 109, 28);
			contentPanel.add(spinnerId);
		}
		{
			spinnerOT = new JSpinner();
			JComponent editor = spinnerOT.getEditor();
			JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
			spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEADING);
			spinnerEditor.getTextField().setText(String.valueOf(Sale.getNextOT()));
			spinnerEditor.getTextField().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						spinnerId.requestFocus();
					}
				}
			});
			spinnerOT.setBounds(101, 47, 109, 28);
			contentPanel.add(spinnerOT);
		}
		{
			@SuppressWarnings("serial")
			Action deletePurchase = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable)e.getSource();
					int modelRow = Integer.valueOf(e.getActionCommand());
					if(JOptionPane.showConfirmDialog(null,"\u00BFDesea eliminar este elemento?", "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						((DefaultTableModel)table.getModel()).removeRow(modelRow);
						alp.remove(modelRow);
					}
				}
			};
			
			tablePurchase = new JTable();
			tablePurchase.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"N\u00B0 Factura","Categor\u00EDa","Vendedor","Compra ($)","Cantidad","Detalle","Eliminar"}));
			tablePurchase.setDefaultEditor(Object.class, null);
			ButtonColumn buttonColumnPurchase = new ButtonColumn(tablePurchase, deletePurchase, 6);
			buttonColumnPurchase.setMnemonic(KeyEvent.VK_D);
			tablePurchase.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					int row = tablePurchase.getSelectedRow();
					if(mouseEvent.getClickCount() == 2 && row != -1) {
						DefaultTableModel model = (DefaultTableModel) tablePurchase.getModel();
						String idBill = model.getValueAt(row, 0).toString();
						String categoryName = model.getValueAt(row, 1).toString();
						String sellerName = model.getValueAt(row, 2).toString();
						String purchaseTotal = model.getValueAt(row, 3).toString().replaceAll("[$ .]","");
						String amount = model.getValueAt(row, 4).toString().replaceAll("[.]", "");
						String detail = model.getValueAt(row, 5).toString();
						PurchaseWindow temp = new PurchaseWindow(idBill, categoryName, sellerName, purchaseTotal, amount, detail);
						ArrayList<Purchase> p = temp.getPurchase();
						if(!temp.getPurchase().isEmpty()) {
							int size = tablePurchase.getRowCount();
							String idBillNew = String.valueOf(p.get(0).getIdBill());
							String sellerNameNew = Stakeholder.search(FileHandler.getSellers(), FileHandler.amountOfSellers, p.get(0).getIdSeller());
							if(!(idBill.equals(idBillNew) && sellerName.equals(sellerNameNew)) && nextInPurchaseTable(row, size, idBill, sellerName)) {
								String message = "Se ha detectado cambios en N\u00B0 de Factura y/o Vendedor.\n\u00BFDesea actualizar los otros \u00EDtemes de esta compra?";
								if(JOptionPane.showConfirmDialog(null, message, "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
									changePurchases(row, size, idBillNew, sellerNameNew, idBill, sellerName);
							}
							model.setValueAt((Object) idBillNew, row, 0);
							model.setValueAt((Object) Category.search(p.get(0).getIdCategory()), row, 1);
							model.setValueAt((Object) sellerNameNew, row, 2);
							model.setValueAt("$ "+String.format("%,d", (Object) p.get(0).getPurchaseTotal()), row, 3);
							model.setValueAt(String.format("%,d", (Object) p.get(0).getAmount()), row, 4);
							model.setValueAt((Object) p.get(0).getDetail(), row, 5);
							alp.get(row).setIdBill(p.get(0).getIdBill());
							alp.get(row).setIdCategory(p.get(0).getIdCategory());
							alp.get(row).setIdSeller(p.get(0).getIdSeller());
							alp.get(row).setPurchaseTotal(p.get(0).getPurchaseTotal());
							alp.get(row).setAmount(p.get(0).getAmount());
							alp.get(row).setDetail(p.get(0).getDetail());
						}
					}
				}
			});
			SwingUtilities.setJTableColumnsWidth(tablePurchase, 951, 10, 17, 17, 10, 10, 30, 6);
		}
		{
			@SuppressWarnings("serial")
			Action deleteSingleSale = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable)e.getSource();
					int modelRow = Integer.valueOf(e.getActionCommand());
					if(JOptionPane.showConfirmDialog(null,"\u00BFDesea eliminar este elemento?", "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						((DefaultTableModel)table.getModel()).removeRow(modelRow);
						alss.remove(modelRow);
					}
				}
			};
			
			tableSingleSale = new JTable();
			tableSingleSale.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Total", "Cantidad", "Detalle","Eliminar"}));
			tableSingleSale.setDefaultEditor(Object.class, null);
			ButtonColumn buttonColumnSingleSale = new ButtonColumn(tableSingleSale, deleteSingleSale, 3);
			buttonColumnSingleSale.setMnemonic(KeyEvent.VK_D);
			tableSingleSale.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					int row = tableSingleSale.getSelectedRow();
					if(mouseEvent.getClickCount() == 2 && row != -1) {
						DefaultTableModel model = (DefaultTableModel) tableSingleSale.getModel();
						String total = model.getValueAt(row, 0).toString().replaceAll("[$ .]", "");
						String amount = model.getValueAt(row, 1).toString().replaceAll("[.]", "");
						String detail = model.getValueAt(row, 2).toString();
						SingleSaleWindow temp = new SingleSaleWindow(total, amount, detail);
						if(temp.getSingleSale() != null) {
							model.setValueAt("$ "+String.format("%,d", (Object)temp.getSingleSale().getTotal()), row, 0);
							model.setValueAt(String.format("%,d", (Object)temp.getSingleSale().getAmount()), row, 1);
							model.setValueAt((Object)temp.getSingleSale().getDetail(), row, 2);
							alss.get(row).setTotal(temp.getSingleSale().getTotal());
							alss.get(row).setAmount(temp.getSingleSale().getAmount());
							alss.get(row).setDetail(temp.getSingleSale().getDetail());
						}
					}
				}
			});
			SwingUtilities.setJTableColumnsWidth(tableSingleSale, 951, 15, 15, 64, 6);
		}
		{	
			textFieldTotalSale = SwingUtilities.getNumberTextField();
			textFieldTotalSale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					comboBoxClient.requestFocus();
				}
			});
			textFieldTotalSale.setToolTipText("Monto neto total de la venta");
			textFieldTotalSale.setBounds(101, 127, 109, 28);
			contentPanel.add(textFieldTotalSale);
		}
		{
			scrollPaneSingleSale = new JScrollPane(tableSingleSale);
			scrollPaneSingleSale.setBounds(10, 211, 951, 74);
			contentPanel.add(scrollPaneSingleSale);
		}
		{
			scrollPanePurchase = new JScrollPane(tablePurchase);
			scrollPanePurchase.setBounds(10, 341, 951, 212);
			contentPanel.add(scrollPanePurchase);
		}
		{
			buttonPane = new JPanel();
			buttonPane.setBounds(0, 418, 718, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCancel = new JButton("Cancelar");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						canceled = true;
						dispose();
					}
				});
				btnCancel.setMargin(new Insets(0,0,0,0));
				buttonPane.add(btnCancel);
			}
			{
				btnAddSale = new JButton("Agregar");
				btnAddSale.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int id = (Integer) spinnerId.getValue();
						int idClient = ((Stakeholder)comboBoxClient.getSelectedItem()).getId();
						int ot = (Integer) spinnerOT.getValue();
						boolean idExists, otExists;
						if((textFieldTotalSale.getText().isEmpty() && tableSingleSale.getRowCount() < 1) || ot < 1 || idClient == 0) {
							String message = "Los datos ingresados no son v\u00E1lido. Por favor, revise que:\n- La orden de trabajo sea mayor a 0.\n- El valor neto no est\u00E9 vac\u00EDo, o haya ingresado al menos un \u00EDtem a la venta.\n- El cliente ha sido seleccionado.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Unfilled Fields", JOptionPane.INFORMATION_MESSAGE);
						}else if((otExists = FileHandler.doesOTExist(ot)) && s != null && s.getOT() != ot) {
							String message = "Ya existe una venta con la Orden de Trabajo " + ot + ". Por favor, ingrese un valor nuevo para continuar.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Warning", JOptionPane.INFORMATION_MESSAGE);
						}else if(otExists && s == null) {
							String message = "Ya existe una venta con la Orden de Trabajo " + ot + ". Por favor, ingrese un valor nuevo para continuar.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Warning", JOptionPane.INFORMATION_MESSAGE);
						}else if((idExists = FileHandler.doesSaleExist(id)) && id != 0 && s != null && s.getId() != id) {
							String message = "Ya existe una venta con el N\u00B0 de Factura " + id + ". Por favor, ingrese un valor nuevo para continuar.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Warning", JOptionPane.INFORMATION_MESSAGE);
						}else if(id != 0 && idExists && s == null) {
							String message = "Ya existe una venta con el N\u00B0 de Factura " + id + ". Por favor, ingrese un valor nuevo para continuar.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Warning", JOptionPane.INFORMATION_MESSAGE);
						}else {
							int totalSale, allMoney = SingleSale.getAllMoney(alss);
							String date = StringUtilities.getDate(comboBoxDateDD.getSelectedItem().toString(), StringUtilities.getMonthValue(comboBoxDateMM.getSelectedItem().toString()), comboBoxDateYY.getSelectedItem().toString());
							if(!textFieldTotalSale.getText().isEmpty())
								totalSale = Integer.parseInt(textFieldTotalSale.getText());
							else
								totalSale = allMoney;
							if(totalSale != allMoney && tableSingleSale.getRowCount() > 0) {
								String message = "El valor neto de la venta ingresada no coincide con el valor de los items ingresados.\n\u00BFDesea continuar?";
								if(JOptionPane.showConfirmDialog(null,message, "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
									return;
							}
							s = new Sale(id, idClient, ot, totalSale, LocalDate.parse(date), alss, alp);
							dispose();
						}
					}
				});
				btnAddSale.setMargin(new Insets(0, 0, 0, 0));
				btnAddSale.setForeground(Color.BLACK);
				btnAddSale.setBackground(new Color(51, 153, 204));
				btnAddSale.setBounds(618, 422, 90, 23);
				buttonPane.add(btnAddSale);
			}
		}
	}
	
	//Inserta en Stakeholder de manera ordenada al ArrayList, generando un nuevo modelo y cargandolo al ComboBox de Stakeholder
	public static boolean loadStakeholderComboBox(ArrayList<Stakeholder> al, Stakeholder newSh, JComboBox<Stakeholder> cb, int size) {
		DefaultComboBoxModel<Stakeholder> model = new DefaultComboBoxModel<Stakeholder>();
		if(Stakeholder.addSorted(al, newSh, size)) {
			size++;
			for(int i = 0; i < size; ++i)
				model.addElement(al.get(i));
			cb.setModel(model);
			cb.getModel().setSelectedItem(newSh);
			return true;
		}
		return false;
	}
	
	//Ordena el ArrayList segun el orden alfabetico del campo NAME, para luego cargarlo en el modelo del ComboBox de Stakeholder
	public static void loadStakeholderComboBox(ArrayList<Stakeholder> al, ComboBoxModel<Stakeholder> cbm, int size) {
		DefaultComboBoxModel<Stakeholder> model = (DefaultComboBoxModel<Stakeholder>) cbm;
		al = Stakeholder.bubbleSort(al, size);
		for(int i = 0; i < size; ++i) {
			model.addElement(al.get(i));
		}
	}
	
	//Busca si ya existe una venta con los valores de entrada.
	private boolean singleSaleSearch(SingleSale ss) {
		int size = alss.size();
		for(int i = 0; i < size; ++i) {
			if(alss.get(i).getTotal() == ss.getTotal() && alss.get(i).getAmount() == ss.getAmount() && alss.get(i).getDetail().equals(ss.getDetail()))
				return true;
		}
		return false;
	}
	
	//Busca si ya existe una compra con los valores de entrada.
	private boolean purchaseSearch(String idBill, String sellerName) {
		int size = tablePurchase.getRowCount();
		for(int i = 0; i < size; ++i) {
			if(tablePurchase.getValueAt(i, 0).equals(idBill) && tablePurchase.getValueAt(i, 2).equals(sellerName))
				return true;
		}
		return false;
	}
	
	//Retorna TRUE si la siguiente fila en la tabla pertenece a la misma compra.
	private boolean nextInPurchaseTable(int row, int size, String idBill, String sellerName) {
		DefaultTableModel model = (DefaultTableModel) tablePurchase.getModel();
		if(size > row + 1 && idBill.equals(model.getValueAt(row + 1, 0).toString()) && sellerName.equals(model.getValueAt(row + 1, 2).toString()))
			return true;
		if(row - 1 >= 0 && idBill.equals(model.getValueAt(row - 1, 0).toString()) && sellerName.equals(model.getValueAt(row - 1, 2).toString()))
			return true;
		return false;
	}
	
	//Actualiza los valores de las siguientes filas en caso de pertenecer a la misma compra.
	private void changePurchases(int row, int size, String idBill, String sellerName, String idBillOld, String sellerNameOld) {
		int i = row + 1;
		DefaultTableModel model = (DefaultTableModel) tablePurchase.getModel();
		while(i < size && idBillOld.equals(model.getValueAt(i, 0).toString()) && sellerNameOld.equals(model.getValueAt(i, 2).toString())) {
			model.setValueAt((Object) idBill, i, 0);
			model.setValueAt((Object) sellerName, i, 2);
			i++;
		}
		i = row - 1;
		while(i >= 0 && idBillOld.equals(model.getValueAt(i, 0).toString()) && sellerNameOld.equals(model.getValueAt(i, 2).toString())) {
			model.setValueAt((Object) idBill, i, 0);
			model.setValueAt((Object) sellerName, i, 2);
			i--;
		}
	}
	
	//Establece los valores de la venta de entrada en los campos de esta ventana.
	private void setValues() {
		ArrayList<String> alS = StringUtilities.stringTokenizer(s.getDate().toString(), "-");
		int amountOfYears = Integer.parseInt(alS.get(0)) - 1950;
		btnAddSale.setText("Cambiar");
		spinnerId.setValue(s.getId());
		spinnerOT.setValue(s.getOT());
		textFieldTotalSale.setText(String.valueOf(s.getTotalSale()));
		comboBoxClient.setSelectedIndex(Stakeholder.indexOf(sh, FileHandler.amountOfClients, s.getIdClient()));
		comboBoxDateYY.setSelectedIndex(amountOfYears - (Integer.parseInt(alS.get(0)) - 1950));
		comboBoxDateMM.setSelectedIndex(NumberUtilities.getMonthValue(alS.get(1)) - 1);
		comboBoxDateDD.setSelectedIndex(Integer.parseInt(alS.get(2)) - 1);
		
		DefaultTableModel model = (DefaultTableModel) tableSingleSale.getModel();
		ArrayList<SingleSale> alSS = s.getAllSales();
		int size = alSS.size();
		alss = new ArrayList<SingleSale>(s.getAllSales());
		for(int i = 0; i < size; ++i)
			model.addRow(new Object[] {"$ "+String.format("%,d", alSS.get(i).getTotal()), String.format("%,d", alSS.get(i).getAmount()), alSS.get(i).getDetail()});
		
		model = (DefaultTableModel) tablePurchase.getModel();
		ArrayList<Purchase> alP = s.getPurchases();
		size = alP.size();
		alp = new ArrayList<Purchase>(s.getPurchases());
		for(int i = 0; i < size; ++i) {
			String categoryName = Category.search(alP.get(i).getIdCategory());
			String sellerName = Stakeholder.search(FileHandler.getSellers(), FileHandler.amountOfSellers, alP.get(i).getIdSeller());
			model.addRow(new Object[] {String.valueOf(alP.get(i).getIdBill()), categoryName, sellerName, "$ "+String.format("%,d", alP.get(i).getPurchaseTotal()), String.format("%,d", alP.get(i).getAmount()), alP.get(i).getDetail()});
		}
	}

	public Sale getSale() { return this.s; }
	public boolean getCanceled() { return this.canceled; }
}
