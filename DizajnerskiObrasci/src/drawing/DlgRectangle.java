package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import geometry.Point;
import geometry.Rectangle;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public boolean isOk;
	Rectangle rectangle;
	private Color outLineColor= Color.black;
	private Color innerColor= Color.white;
	private JTextField txtHeight;
	private JTextField txtWidth;
	private JTextField txtUpperLeftPointY;
	private JTextField txtUpperLeftPointX;
	private JButton btnInnerColor;
	private JButton btnOutLineColor;
	public boolean isInnerColorOK,isOuterColorOK;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setBounds(100, 100, 450, 488);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{103, 69, 243, 0};
		gbl_contentPanel.rowHeights = new int[]{22, 38, 22, 38, 22, 38, 22, 38, 25, 38, 25, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblHeight = new JLabel("Height");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 0;
			gbc_lblHeight.gridy = 0;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			txtHeight = new JTextField();
			GridBagConstraints gbc_txtHeight = new GridBagConstraints();
			gbc_txtHeight.anchor = GridBagConstraints.NORTH;
			gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHeight.insets = new Insets(0, 0, 5, 0);
			gbc_txtHeight.gridx = 2;
			gbc_txtHeight.gridy = 0;
			contentPanel.add(txtHeight, gbc_txtHeight);
			txtHeight.setColumns(10);
		}
		{
			JLabel lblWidth = new JLabel("Width");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidth.gridx = 0;
			gbc_lblWidth.gridy = 2;
			contentPanel.add(lblWidth, gbc_lblWidth);
		}
		{
			txtWidth = new JTextField();
			GridBagConstraints gbc_txtWidth = new GridBagConstraints();
			gbc_txtWidth.anchor = GridBagConstraints.NORTH;
			gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWidth.insets = new Insets(0, 0, 5, 0);
			gbc_txtWidth.gridx = 2;
			gbc_txtWidth.gridy = 2;
			contentPanel.add(txtWidth, gbc_txtWidth);
			txtWidth.setColumns(10);
		}
		{
			JLabel lblUpperLeftPointX = new JLabel("Upper Left Point X");
			GridBagConstraints gbc_lblUpperLeftPointX = new GridBagConstraints();
			gbc_lblUpperLeftPointX.anchor = GridBagConstraints.WEST;
			gbc_lblUpperLeftPointX.insets = new Insets(0, 0, 5, 5);
			gbc_lblUpperLeftPointX.gridx = 0;
			gbc_lblUpperLeftPointX.gridy = 4;
			contentPanel.add(lblUpperLeftPointX, gbc_lblUpperLeftPointX);
		}
		{
			txtUpperLeftPointX = new JTextField();
			GridBagConstraints gbc_txtUpperLeftPointX = new GridBagConstraints();
			gbc_txtUpperLeftPointX.anchor = GridBagConstraints.NORTH;
			gbc_txtUpperLeftPointX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftPointX.insets = new Insets(0, 0, 5, 0);
			gbc_txtUpperLeftPointX.gridx = 2;
			gbc_txtUpperLeftPointX.gridy = 4;
			contentPanel.add(txtUpperLeftPointX, gbc_txtUpperLeftPointX);
			txtUpperLeftPointX.setColumns(10);
		}
		{
			JLabel lblUpperLeftPointY = new JLabel("Upper Left Point Y");
			GridBagConstraints gbc_lblUpperLeftPointY = new GridBagConstraints();
			gbc_lblUpperLeftPointY.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblUpperLeftPointY.insets = new Insets(0, 0, 5, 5);
			gbc_lblUpperLeftPointY.gridx = 0;
			gbc_lblUpperLeftPointY.gridy = 6;
			contentPanel.add(lblUpperLeftPointY, gbc_lblUpperLeftPointY);
		}
		{
			txtUpperLeftPointY = new JTextField();
			GridBagConstraints gbc_txtUpperLeftPointY = new GridBagConstraints();
			gbc_txtUpperLeftPointY.anchor = GridBagConstraints.NORTH;
			gbc_txtUpperLeftPointY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftPointY.insets = new Insets(0, 0, 5, 0);
			gbc_txtUpperLeftPointY.gridx = 2;
			gbc_txtUpperLeftPointY.gridy = 6;
			contentPanel.add(txtUpperLeftPointY, gbc_txtUpperLeftPointY);
			txtUpperLeftPointY.setColumns(10);
		}
		{
			JLabel lblInnerColor = new JLabel("Inner Color");
			GridBagConstraints gbc_lblInnerColor = new GridBagConstraints();
			gbc_lblInnerColor.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblInnerColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblInnerColor.gridx = 0;
			gbc_lblInnerColor.gridy = 8;
			contentPanel.add(lblInnerColor, gbc_lblInnerColor);
		}
		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				innerColor=JColorChooser.showDialog(null, "Choose a inner Color", btnInnerColor.getBackground());
				if(innerColor!=null)
					btnInnerColor.setBackground(innerColor);
				isInnerColorOK=true;
			}
		});
		btnInnerColor.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
		gbc_btnInnerColor.anchor = GridBagConstraints.NORTH;
		gbc_btnInnerColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInnerColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnInnerColor.gridx = 2;
		gbc_btnInnerColor.gridy = 8;
		contentPanel.add(btnInnerColor, gbc_btnInnerColor);
		btnOutLineColor = new JButton("Outline Color");
		btnOutLineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 outLineColor=JColorChooser.showDialog(null, "Choose a outline Color", btnOutLineColor.getBackground());
				if(outLineColor!=null)
					btnOutLineColor.setBackground(outLineColor);
				isOuterColorOK=true;
			}
		});
		btnOutLineColor.setBackground(Color.BLACK);
		{
			JLabel lblOutLineColor = new JLabel("Outline Color");
			GridBagConstraints gbc_lblOutLineColor = new GridBagConstraints();
			gbc_lblOutLineColor.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblOutLineColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblOutLineColor.gridx = 0;
			gbc_lblOutLineColor.gridy = 10;
			contentPanel.add(lblOutLineColor, gbc_lblOutLineColor);
		}
		GridBagConstraints gbc_btnOutLineColor = new GridBagConstraints();
		gbc_btnOutLineColor.anchor = GridBagConstraints.NORTH;
		gbc_btnOutLineColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOutLineColor.gridx = 2;
		gbc_btnOutLineColor.gridy = 10;
		contentPanel.add(btnOutLineColor, gbc_btnOutLineColor);
		{
		}
		{
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					if(txtUpperLeftPointY.getText().trim().isEmpty() || txtUpperLeftPointX.getText().trim().isEmpty() || txtHeight.getText().trim().isEmpty() || txtWidth.getText().trim().isEmpty()){
						isOk=false;
						JOptionPane.showMessageDialog(null, "All fields must be entered!");
						
					}
					else{
						try{
							if(Integer.parseInt(txtHeight.getText())<=0 || Integer.parseInt(txtWidth.getText())<=0){
								JOptionPane.showMessageDialog(null, "Values must be greater than 0!");
								isOk=false;
							}
							else{
								
								
								isOk=true;
								setVisible(false);
								}
						}catch(NumberFormatException e1){
							JOptionPane.showMessageDialog(null, "Values must be numbers!");
						}
						
					}
				}
							
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public JTextField getTxtUpperLeftPointY() {
		return txtUpperLeftPointY;
	}

	public JTextField getTxtUpperLeftPointX() {
		return txtUpperLeftPointX;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JButton getBtnOutLineColor() {
		return btnOutLineColor;
	}

	public boolean isInnerColorOK() {
		return isInnerColorOK;
	}

	public boolean isOuterColorOK() {
		return isOuterColorOK;
	}

	public Color getOutLineColor() {
		return outLineColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public boolean isOk() {
		return isOk;
	}

}
