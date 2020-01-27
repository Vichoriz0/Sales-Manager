package interfaces;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import Renderers.ButtonColumn;
import Renderers.CustomListCellRenderer;
import dataHandler.Category;
import dataHandler.FileHandler;
import dataHandler.Purchase;
import dataHandler.Stakeholder;
import utilities.NumberUtilities;
import utilities.SwingUtilities;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

/*Sales Manager Application
 * Creado por Vicente Barria Veas
 * E-mail: v.barriaveas@gmail.com
 * Pais: Chile
 */

public class PurchaseWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnAdd;
	private JButton btnAddCategory;
	private JButton btnAddInfo;
	private JButton btnAddSeller;
	private JButton btnCancel;
	private JButton btnChange;
	private JButton btnDelCategory;
	private JButton btnDelSeller;
	private JComboBox<Category> comboBoxCategory;
	private JComboBox<Stakeholder> comboBoxSellers;
	private JLabel lblAmount;
	private JLabel lblBill;
	private JLabel lblCategory;
	private JLabel lblDetail;
	private JLabel lblMoney;
	private JLabel lblMoney_2;
	private JLabel lblSeller;
	private JLabel lblTitle;
	private JScrollPane scrollPanePurchase;
	private JSeparator separator;
	private JSeparator separator_1;
	private JTable tablePurchase;
	private JTextArea textAreaDetail;
	private JTextField textFieldAmount;
	private JTextField textFieldBill;
	private JTextField textFieldMoney;
	private int idBill = 0;
	private int idSeller = -1;
	private ArrayList<Purchase> p = new ArrayList<>();
	private ArrayList<Stakeholder> sh = new ArrayList<>(FileHandler.getSellers());
	private ArrayList<Category> c = new ArrayList<>(FileHandler.getCategories());
	private JScrollPane scrollPane;
	
	public PurchaseWindow() {
		
		try {
			initialize();
			setModal(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
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
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Initialize Purchase", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			dispose();
		};
	}
	
	public PurchaseWindow(String idBill, String categoryName, String sellerName, String purchaseTotal, String amount, String detail) {
		try {
			initialize();
			textFieldBill.setText(idBill);
			textFieldMoney.setText(purchaseTotal);
			textFieldAmount.setText(amount);
			textAreaDetail.setText(detail);
			btnAdd.setVisible(false);
			btnAdd.setEnabled(false);
			btnAddInfo.setEnabled(false);
			btnChange.setVisible(true);
			btnChange.setEnabled(true);
			comboBoxSellers.setSelectedIndex(Stakeholder.indexOf(sh, FileHandler.amountOfSellers, sellerName));
			comboBoxCategory.setSelectedIndex(Category.indexOf(c, categoryName));
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
			JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Initialize Single Sale Edit", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			dispose();
		};
	}
	
	public void initialize() {
		setBounds(100, 100, 847, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			btnAddSeller = new JButton("+");
			btnAddSeller.setToolTipText("Agregar un vendedor");
			btnAddSeller.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SimpleStringWindow dialog = new SimpleStringWindow("Nuevo Vendedor");
					String name = dialog.getStr();
					if(!name.isEmpty()){
						Stakeholder aux = new Stakeholder(Stakeholder.getNextId(FileHandler.getSellers(),FileHandler.amountOfSellers), name);
						if(SaleWindow.loadStakeholderComboBox(sh, aux, comboBoxSellers, FileHandler.amountOfSellers)) {
							FileHandler.getSellers().add(aux);
							FileHandler.amountOfSellers++;
							FileHandler.writeStakeholders(aux, FileHandler.getSellersFileDir());
						}else {
							String message = "Vendedor " + name + " ya existe.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: Duplicated", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			btnAddSeller.setMargin(new Insets(0, 0, 0, 0));
			btnAddSeller.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAddSeller.setBounds(740, 50, 38, 22);
			contentPanel.add(btnAddSeller);
		}
		{
			btnAddCategory = new JButton("+");
			btnAddCategory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SimpleStringWindow dialog = new SimpleStringWindow("Nueva Categor\u00EDa");
					String name = dialog.getStr();
					if(!name.isEmpty()){
						Category aux = new Category(Category.getNextId(), name);
						if(loadCategoryComboBox(aux)){
							FileHandler.getCategories().add(aux);
							FileHandler.amountOfCategories++;
							FileHandler.writeCategories(aux);
						}else {
							String message = "Categor\u00EDa " + name + " ya existe.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: Duplicated", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			btnAddCategory.setToolTipText("Agregar un vendedor");
			btnAddCategory.setMargin(new Insets(0, 0, 0, 0));
			btnAddCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAddCategory.setBounds(740, 109, 38, 22);
			contentPanel.add(btnAddCategory);
		}
		{
			btnAddInfo = new JButton("Ingresar Informaci\u00F3n");
			btnAddInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(textFieldBill.getText().isEmpty() || textFieldMoney.getText().isEmpty() || ((Stakeholder)comboBoxSellers.getSelectedItem()).getId() == 0) {
						String message = "Los campos N\u00B0 de Factura, Monto y Vendedor no pueden estar vac\u00EDos";
						JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Unfilled Fields", JOptionPane.INFORMATION_MESSAGE);
					}else {
						int idCategory = ((Category)comboBoxCategory.getSelectedItem()).getId();
						int purchaseTotal = Integer.parseInt(textFieldMoney.getText());
						int amount = 0;
						String detail = textAreaDetail.getText();
						if(textFieldBill.getText() != String.valueOf(idBill))
							Purchase.changeAllId(p, idBill = Integer.parseInt(textFieldBill.getText()));
						if(((Stakeholder)comboBoxSellers.getSelectedItem()).getId() != idSeller)
							Purchase.changeAllIdSeller(p, idSeller = ((Stakeholder)comboBoxSellers.getSelectedItem()).getId());
						if(!textFieldAmount.getText().isEmpty())
							amount = Integer.parseInt(textFieldAmount.getText());
						Purchase aux = new Purchase(idBill, idCategory, idSeller, purchaseTotal, amount, detail);
						if(purchaseSearch(aux) && JOptionPane.showConfirmDialog(null, "Al parecer ya ha agregado esta venta. \u00BFDesea continuar?", "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
							return;
						p.add(aux);
						DefaultTableModel model = (DefaultTableModel) tablePurchase.getModel();
						model.addRow(new Object[] {"$ "+String.format("%,d", purchaseTotal), String.format("%,d", amount), ((Category)comboBoxCategory.getSelectedItem()).getName(), detail});
						textFieldMoney.setText("");
						textFieldAmount.setText("");
						textAreaDetail.setText("");
						comboBoxCategory.setSelectedIndex(0);
						textFieldMoney.requestFocus();
					}
				}
			});
			btnAddInfo.setBounds(515, 270, 214, 25);
			contentPanel.add(btnAddInfo);
		}
		{
			btnDelCategory = new JButton("-");
			btnDelCategory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int id;
					if((id = comboBoxCategory.getSelectedIndex()) == 0 || id == -1) {
						String message = "Este elemento no se puede eliminar.";
						JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: Not Able to Delete", JOptionPane.INFORMATION_MESSAGE);
					}else {
						String message = "Cada compra registrada con este elemento cambiar\u00E1 su categor\u00EDa por el valor por defecto (N/A).\n\u00BFDesea continuar?";
						if(JOptionPane.showConfirmDialog(null, message, "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							Category aux = (Category) comboBoxCategory.getSelectedItem();
							if(Category.updateCategoryOnDelete(aux.getId()))
								FileHandler.writeSales("data/sales.temp.csv");
							c.remove(id);
							comboBoxCategory.removeItemAt(id);
							FileHandler.getCategories().remove(aux);
							FileHandler.amountOfCategories--;
							FileHandler.writeCategories("data/categories.temp.csv");
						}
					}
				}
			});
			btnDelCategory.setToolTipText("Eliminar el vendedor seleccionado");
			btnDelCategory.setMargin(new Insets(0, 0, 0, 0));
			btnDelCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnDelCategory.setBounds(783, 109, 38, 22);
			contentPanel.add(btnDelCategory);
		}
		{
			btnDelSeller = new JButton("-");
			btnDelSeller.setToolTipText("Eliminar el vendedor seleccionado");
			btnDelSeller.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int id;
					if((id = comboBoxSellers.getSelectedIndex()) == 0 || id == -1) {
						String message = "Este elemento no se puede eliminar.";
						JOptionPane.showMessageDialog(null, message, "Sales Manager Warning: Not Able to Delete", JOptionPane.INFORMATION_MESSAGE);
					}else {
						String message = "Cada compra registrada con este elemento cambiar\u00E1 su vendedor por el valor por defecto (N/A).\n\u00BFDesea continuar?";
						if(JOptionPane.showConfirmDialog(null, message, "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							Stakeholder aux = (Stakeholder)comboBoxSellers.getSelectedItem();
							if(Stakeholder.updateSellerOnDelete(aux.getId()))
								FileHandler.writeSales("data/sales.temp.csv");
							sh.remove(id);
							comboBoxSellers.removeItemAt(id);
							FileHandler.getSellers().remove(aux);
							FileHandler.amountOfSellers--;
							FileHandler.writeStakeholders(FileHandler.getSellers(), "data/sellers.temp.csv", FileHandler.getSellersFileDir());
						}
					}
				}
			});
			btnDelSeller.setMargin(new Insets(0, 0, 0, 0));
			btnDelSeller.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnDelSeller.setBounds(783, 50, 38, 22);
			contentPanel.add(btnDelSeller);
		}
		{
			comboBoxCategory = new JComboBox<Category>();
			comboBoxCategory.setBounds(489, 107, 246, 28);
			comboBoxCategory.setRenderer(new CustomListCellRenderer());
			loadCategoryComboBox();
			contentPanel.add(comboBoxCategory);
		}
		{
			comboBoxSellers = new JComboBox<Stakeholder>();
			comboBoxSellers.setBounds(489, 47, 246, 28);
			comboBoxSellers.setRenderer(new CustomListCellRenderer());
			SaleWindow.loadStakeholderComboBox(this.sh, this.comboBoxSellers.getModel(), FileHandler.amountOfSellers);
			contentPanel.add(comboBoxSellers);
		}
		{
			lblAmount = new JLabel("Cantidad:");
			lblAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblAmount.setBounds(10, 145, 81, 28);
			contentPanel.add(lblAmount);
		}
		{
			lblBill = new JLabel("N\u00B0 Factura:");
			lblBill.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblBill.setBounds(10, 47, 81, 28);
			contentPanel.add(lblBill);
		}
		{
			lblCategory = new JLabel("Categor\u00EDa:");
			lblCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblCategory.setBounds(415, 106, 81, 28);
			contentPanel.add(lblCategory);
		}
		{
			lblDetail = new JLabel("Detalle:");
			lblDetail.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDetail.setBounds(10, 184, 81, 28);
			contentPanel.add(lblDetail);
		}
		{
			lblMoney = new JLabel("Monto:");
			lblMoney.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblMoney.setBounds(10, 106, 81, 28);
			contentPanel.add(lblMoney);
		}
		{
			lblMoney_2 = new JLabel("$");
			lblMoney_2.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblMoney_2.setBounds(87, 106, 10, 28);
			contentPanel.add(lblMoney_2);
		}
		{
			lblSeller = new JLabel("Vendedor:");
			lblSeller.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSeller.setBounds(415, 47, 81, 28);
			contentPanel.add(lblSeller);
		}
		{
			lblTitle = new JLabel("Detalle de Compra");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblTitle.setBounds(10, 11, 176, 22);
			contentPanel.add(lblTitle);
		}
		{
			separator = new JSeparator();
			separator.setBounds(10, 32, 811, 2);
			contentPanel.add(separator);
		}
		{
			separator_1 = new JSeparator();
			separator_1.setBounds(10, 90, 811, 2);
			contentPanel.add(separator_1);
		}
		{
			@SuppressWarnings("serial")
			Action deletePurchase = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable)e.getSource();
					int modelRow = Integer.valueOf(e.getActionCommand());
					if(JOptionPane.showConfirmDialog(null,"\u00BFDesea eliminar este elemento?", "Sales Manager Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						((DefaultTableModel)table.getModel()).removeRow(modelRow);
						p.remove(modelRow);
					}
				}
			};
			
			tablePurchase = new JTable();
			tablePurchase.setBounds(280, 195, 363, 98);
			tablePurchase.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Monto","Cantidad","Categor\u00EDa","Detalle","Eliminar"}));
			tablePurchase.setDefaultEditor(Object.class, null);
			ButtonColumn buttonColumnPurchase = new ButtonColumn(tablePurchase, deletePurchase, 4);
			buttonColumnPurchase.setMnemonic(KeyEvent.VK_D);
			tablePurchase.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					int row = tablePurchase.getSelectedRow();
					if(mouseEvent.getClickCount() == 2 && row != -1) {
						DefaultTableModel model = (DefaultTableModel) tablePurchase.getModel();
						textFieldMoney.setText(model.getValueAt(row, 0).toString().replaceAll("[$ .]", ""));
						textFieldAmount.setText(model.getValueAt(row, 1).toString().replaceAll("[$ .]", ""));
						textAreaDetail.setText(model.getValueAt(row, 3).toString());
						comboBoxCategory.setSelectedIndex(Category.indexOf(c, model.getValueAt(row, 2).toString()));
						model.removeRow(row);
						p.remove(row);
					}
				}
			});
		}
		{
			textAreaDetail = new JTextArea();
			textAreaDetail.setToolTipText("Detalle de un \u00EDtem de la compra");
			textAreaDetail.setWrapStyleWord(true);
			textAreaDetail.setLineWrap(true);
			textAreaDetail.setBounds(101, 184, 109, 111);
			textAreaDetail.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnAddInfo.doClick();
					}
				}
			});
		}
		{
			textFieldAmount = SwingUtilities.getNumberTextField();
			textFieldAmount.setToolTipText("Cantidad de un \u00EDtem de la compra");
			textFieldAmount.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						textAreaDetail.requestFocus();
					}
				}
			});
			textFieldAmount.setBounds(101, 145, 109, 28);
			contentPanel.add(textFieldAmount);
		}
		{
			textFieldBill = SwingUtilities.getNumberTextField();
			textFieldBill.setToolTipText("N\u00FAmero de factura (mayor o igual a 1)");
			textFieldBill.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						textFieldMoney.requestFocus();
					}
				}
			});
			textFieldBill.setBounds(101, 47, 109, 28);
			contentPanel.add(textFieldBill);
		}
		{
			scrollPanePurchase = new JScrollPane(tablePurchase);
			scrollPanePurchase.setToolTipText("Listado de todos los \u00EDtemes para esta compra");
			scrollPanePurchase.setBounds(423, 165, 398, 100);
			contentPanel.add(scrollPanePurchase);
		}
		{
			textFieldMoney = new JTextField();
			textFieldMoney.setToolTipText("Valor de un \u00EDtem de la compra");
			textFieldMoney.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					String str = textFieldMoney.getText();
					if(!str.isEmpty()){
						str = str.replaceAll("[^0-9]*", "");
						if(NumberUtilities.isNumber(str,false))
							textFieldMoney.setText(str);
						else
							textFieldMoney.setText("");
					}
				}
				@Override
				public void focusGained(FocusEvent e) {
					textFieldMoney.selectAll();
				}
			});
			textFieldMoney.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						textFieldAmount.requestFocus();
					}
				}
			});
			textFieldMoney.setColumns(10);
			textFieldMoney.setBounds(101, 106, 109, 28);
			contentPanel.add(textFieldMoney);
		}
		{
			scrollPane = new JScrollPane(textAreaDetail);
			scrollPane.setBounds(101, 184, 260, 111);
			contentPanel.add(scrollPane);
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCancel = new JButton("Cancelar");
				btnCancel.setMargin(new Insets(0, 0, 0, 0));
				btnCancel.setBounds(227, 12, 71, 22);
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						p.clear();
						dispose();
					}
				});
				buttonPane.add(btnCancel);
			}
			{
				btnAdd = new JButton("Agregar");
				btnAdd.setMargin(new Insets(0, 0, 0, 0));
				btnAdd.setBounds(142, 12, 75, 22);
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(p.isEmpty()) {
							String message = "Debe agregar al menos una compra para continuar.";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Unfilled Fields", JOptionPane.INFORMATION_MESSAGE);
						}else {
							dispose();
						}
					}
				});
				btnAdd.setForeground(new Color(0, 0, 0));
				btnAdd.setBackground(new Color(51, 153, 204));
				buttonPane.add(btnAdd);
			}
			{
				btnChange = new JButton("Cambiar");
				btnChange.setMargin(new Insets(0, 0, 0, 0));
				btnChange.setBounds(142, 12, 75, 22);
				btnChange.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldBill.getText().isEmpty() || textFieldMoney.getText().isEmpty()) {
							String message = "Los campos N\u00B0 de Factura y Monto no pueden estar vac\u00EDos";
							JOptionPane.showMessageDialog(null, message, "Sales Manager Error: Unfilled Fields", JOptionPane.INFORMATION_MESSAGE);
						}else {
							int idCategory = ((Category)comboBoxCategory.getSelectedItem()).getId();
							int purchaseTotal = Integer.parseInt(textFieldMoney.getText());
							int amount = 0;
							String detail = textAreaDetail.getText();
							if(textFieldBill.getText() != String.valueOf(idBill))
								Purchase.changeAllId(p, idBill = Integer.parseInt(textFieldBill.getText()));
							if(((Stakeholder)comboBoxSellers.getSelectedItem()).getId() != idSeller)
								Purchase.changeAllIdSeller(p, idSeller = ((Stakeholder)comboBoxSellers.getSelectedItem()).getId());
							if(!textFieldAmount.getText().isEmpty())
								amount = Integer.parseInt(textFieldAmount.getText());
							p.add(new Purchase(idBill, idCategory, idSeller, purchaseTotal, amount, detail));
							dispose();
						}
					}
				});
				btnChange.setForeground(new Color(0, 0, 0));
				btnChange.setBackground(new Color(51, 153, 204));
				btnChange.setEnabled(false);
				btnChange.setVisible(false);
				buttonPane.add(btnChange);
			}
		}
	}
	
	//Busca si ya existe una compra con los valores de entrada.
	private boolean purchaseSearch(Purchase aux) {
		int size = p.size();
		for(int i = 0; i < size; ++i) {
			if(p.get(i).getIdCategory() == aux.getIdCategory() && p.get(i).getPurchaseTotal() == aux.getPurchaseTotal() && p.get(i).getAmount() == aux.getAmount() && p.get(i).getDetail().equals(aux.getDetail()))
				return true;
		}
		return false;
	}
	
	//Agrega ordenamente una categoria a la lista.
	private boolean loadCategoryComboBox(Category aux) {
		DefaultComboBoxModel<Category> model = new DefaultComboBoxModel<Category>();
		if(Category.addSorted(c, aux)) {
			int size = FileHandler.amountOfCategories + 1;
			for(int i = 0; i < size; ++i)
				model.addElement(c.get(i));
			comboBoxCategory.setModel(model);
			comboBoxCategory.getModel().setSelectedItem(aux);
			return true;
		}
		return false;
	}
	
	//Ordena el ArrayList segun el orden alfabetico del campo NAME, para luego cargarlo en el modelo del ComboBox de Stakeholder
	private void loadCategoryComboBox() {
		DefaultComboBoxModel<Category> model = (DefaultComboBoxModel<Category>) comboBoxCategory.getModel();
		c = Category.bubbleSort(c);
		for(int i = 0; i < FileHandler.amountOfCategories; ++i) {
			model.addElement(c.get(i));
		}
	}
	
	public ArrayList<Purchase> getPurchase() { return this.p; }
}
