package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Circle;
import geometry.Point;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgCircle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public boolean isOk;
	public boolean isInnerColorOK;
	public boolean isOuterColorOK;
	Circle circle;
	private Color outLineColor= Color.black;
	private Color innerColor= Color.white;
	private JTextField txtCenterX;
	private JTextField txtRadius;
	private JButton btnOutLineColor;
	private JButton btnInnerColor;
	private JTextField txtCenterY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircle dialog = new DlgCircle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircle() {
		setBounds(100, 100, 450, 344);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCenterX = new JLabel("Center X");
			GridBagConstraints gbc_lblCenterX = new GridBagConstraints();
			gbc_lblCenterX.insets = new Insets(0, 0, 5, 5);
			gbc_lblCenterX.gridx = 0;
			gbc_lblCenterX.gridy = 0;
			contentPanel.add(lblCenterX, gbc_lblCenterX);
		}
		{
			txtCenterX = new JTextField();
			GridBagConstraints gbc_txtCenterX = new GridBagConstraints();
			gbc_txtCenterX.insets = new Insets(0, 0, 5, 0);
			gbc_txtCenterX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCenterX.gridx = 2;
			gbc_txtCenterX.gridy = 0;
			contentPanel.add(txtCenterX, gbc_txtCenterX);
			txtCenterX.setColumns(10);
			
		}
		{
		}
		{
			JLabel lblCenterY = new JLabel("Center Y");
			GridBagConstraints gbc_lblCenterY = new GridBagConstraints();
			gbc_lblCenterY.insets = new Insets(0, 0, 5, 5);
			gbc_lblCenterY.gridx = 0;
			gbc_lblCenterY.gridy = 2;
			contentPanel.add(lblCenterY, gbc_lblCenterY);
		}
		{
			txtCenterY = new JTextField();
			GridBagConstraints gbc_txtCenterY = new GridBagConstraints();
			gbc_txtCenterY.insets = new Insets(0, 0, 5, 0);
			gbc_txtCenterY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCenterY.gridx = 2;
			gbc_txtCenterY.gridy = 2;
			contentPanel.add(txtCenterY, gbc_txtCenterY);
			txtCenterY.setColumns(10);
		}
		{
			JLabel lblRadius = new JLabel("Radius");
			GridBagConstraints gbc_lblRadius = new GridBagConstraints();
			gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblRadius.gridx = 0;
			gbc_lblRadius.gridy = 4;
			contentPanel.add(lblRadius, gbc_lblRadius);
		}
		{
		}
		{
			txtRadius = new JTextField();
			GridBagConstraints gbc_txtRadius = new GridBagConstraints();
			gbc_txtRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRadius.gridx = 2;
			gbc_txtRadius.gridy = 4;
			contentPanel.add(txtRadius, gbc_txtRadius);
			txtRadius.setColumns(10);
		}
		{
			JLabel lblOutlineColor = new JLabel("OutLine Color");
			GridBagConstraints gbc_lblOutlineColor = new GridBagConstraints();
			gbc_lblOutlineColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblOutlineColor.gridx = 0;
			gbc_lblOutlineColor.gridy = 6;
			contentPanel.add(lblOutlineColor, gbc_lblOutlineColor);
		}
		btnOutLineColor = new JButton("OutLine Color");
		btnOutLineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					 outLineColor = JColorChooser.showDialog(null, "Choose a outline Color", outLineColor);
					if(outLineColor != null)
						btnOutLineColor.setBackground(outLineColor);
					isOuterColorOK=true;
			}
		});
		btnOutLineColor.setBackground(Color.BLACK);
		GridBagConstraints gbc_btnOutLineColor = new GridBagConstraints();
		gbc_btnOutLineColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnOutLineColor.gridx = 2;
		gbc_btnOutLineColor.gridy = 6;
		contentPanel.add(btnOutLineColor, gbc_btnOutLineColor);
		{
			JLabel lblInnerColor = new JLabel("Inner Color");
			GridBagConstraints gbc_lblInnerColor = new GridBagConstraints();
			gbc_lblInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblInnerColor.gridx = 0;
			gbc_lblInnerColor.gridy = 8;
			contentPanel.add(lblInnerColor, gbc_lblInnerColor);
		}
		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 innerColor = JColorChooser.showDialog(null, "Choose a inner Color", innerColor);
				if(innerColor != null)
					btnInnerColor.setBackground(innerColor);
				isInnerColorOK=true;
				
			}
		});
		btnInnerColor.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnInnerColor_1 = new GridBagConstraints();
		gbc_btnInnerColor_1.gridx = 2;
		gbc_btnInnerColor_1.gridy = 8;
		contentPanel.add(btnInnerColor, gbc_btnInnerColor_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(txtCenterX.getText().trim().isEmpty() || txtCenterY.getText().trim().isEmpty() ||  txtRadius.getText().trim().isEmpty()){
							
							isOk=false;
							JOptionPane.showMessageDialog(null, "All fields must be entered!");
								
							}
							else {
								try{
									if(Integer.parseInt(txtRadius.getText())<0){
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

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public JTextField getTxtCenterX() {
		return txtCenterX;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public JButton getBtnOutLineColor() {
		return btnOutLineColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JTextField getTxtCenterY() {
		return txtCenterY;
	}

	public boolean isOk() {
		return isOk;
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


}
