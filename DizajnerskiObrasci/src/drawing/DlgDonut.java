package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Donut;
import geometry.Point;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgDonut extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public boolean isOk, isInnerColorOK, isOuterColorOK;
	Donut donut;
	private Color outLineColor = Color.black;
	private Color innerColor= Color.white;
	private JTextField txtCenterX;
	private JTextField txtCenterY;
	private JTextField txtInnerRadius;
	private JTextField txtOuterRadius;
	private JButton btnOutLineColor;
	private JButton btnInnerColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDonut dialog = new DlgDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgDonut() {
		setBounds(100, 100, 450, 395);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
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
			JLabel lblInnerRadius = new JLabel("Inner Radius");
			GridBagConstraints gbc_lblInnerRadius = new GridBagConstraints();
			gbc_lblInnerRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblInnerRadius.gridx = 0;
			gbc_lblInnerRadius.gridy = 4;
			contentPanel.add(lblInnerRadius, gbc_lblInnerRadius);
		}
		{
			txtInnerRadius = new JTextField();
			GridBagConstraints gbc_txtInnerRadius = new GridBagConstraints();
			gbc_txtInnerRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtInnerRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtInnerRadius.gridx = 2;
			gbc_txtInnerRadius.gridy = 4;
			contentPanel.add(txtInnerRadius, gbc_txtInnerRadius);
			txtInnerRadius.setColumns(10);
		}
		{
			JLabel lblOuterRadius = new JLabel("Outer Radius");
			GridBagConstraints gbc_lblOuterRadius = new GridBagConstraints();
			gbc_lblOuterRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblOuterRadius.gridx = 0;
			gbc_lblOuterRadius.gridy = 6;
			contentPanel.add(lblOuterRadius, gbc_lblOuterRadius);
		}
		{
			txtOuterRadius = new JTextField();
			GridBagConstraints gbc_txtOuterRadius = new GridBagConstraints();
			gbc_txtOuterRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtOuterRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtOuterRadius.gridx = 2;
			gbc_txtOuterRadius.gridy = 6;
			contentPanel.add(txtOuterRadius, gbc_txtOuterRadius);
			txtOuterRadius.setColumns(10);
		}
		{
			JLabel lblOutLineColor = new JLabel("OutLine Color");
			GridBagConstraints gbc_lblOutLineColor = new GridBagConstraints();
			gbc_lblOutLineColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblOutLineColor.gridx = 0;
			gbc_lblOutLineColor.gridy = 8;
			contentPanel.add(lblOutLineColor, gbc_lblOutLineColor);
		}
		{
			btnOutLineColor = new JButton("Outline Color");
			btnOutLineColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 outLineColor = JColorChooser.showDialog(null, "Choose a outline Color",
							btnOutLineColor.getBackground());
					if (outLineColor != null)
						btnOutLineColor.setBackground(outLineColor);
					isOuterColorOK=true;
				}
			});
			btnOutLineColor.setBackground(Color.BLACK);
			GridBagConstraints gbc_btnOutLineColor = new GridBagConstraints();
			gbc_btnOutLineColor.insets = new Insets(0, 0, 5, 0);
			gbc_btnOutLineColor.gridx = 2;
			gbc_btnOutLineColor.gridy = 8;
			contentPanel.add(btnOutLineColor, gbc_btnOutLineColor);

		}

		JLabel lblInnerColor = new JLabel("Inner Color");
		GridBagConstraints gbc_lblInnerColor = new GridBagConstraints();
		gbc_lblInnerColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblInnerColor.gridx = 0;
		gbc_lblInnerColor.gridy = 10;
		contentPanel.add(lblInnerColor, gbc_lblInnerColor);
		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 innerColor = JColorChooser.showDialog(null, "Choose a inner color",
						btnInnerColor.getBackground());
				if (innerColor != null)
					btnInnerColor.setBackground(innerColor);
				isInnerColorOK=true;
			}
		});
		btnInnerColor.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
		gbc_btnInnerColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnInnerColor.gridx = 2;
		gbc_btnInnerColor.gridy = 10;
		contentPanel.add(btnInnerColor, gbc_btnInnerColor);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (txtCenterX.getText().trim().isEmpty() || txtCenterY.getText().trim().isEmpty()
								|| txtInnerRadius.getText().trim().isEmpty()
								|| txtOuterRadius.getText().trim().isEmpty()) {

							isOk = false;
							JOptionPane.showMessageDialog(null, "All fields must be entered!");

						} else {
							try {
								if (Integer.parseInt(txtInnerRadius.getText()) < 0
										|| Integer.parseInt(txtOuterRadius.getText()) < 0) {
									JOptionPane.showMessageDialog(null, "Values must be greater than 0!");
									isOk = false;
								} else if ((Integer.parseInt(txtInnerRadius.getText())) > (Integer
										.parseInt(txtOuterRadius.getText()))) {
									JOptionPane.showMessageDialog(null,
											"Outer radius must be greater than inner radius");
									isOk = false;
								}
								else if ((Integer.parseInt(txtInnerRadius.getText())) == (Integer
										.parseInt(txtOuterRadius.getText()))) {
									JOptionPane.showMessageDialog(null,
											"Outer radius must be greater than inner radius");
									isOk = false;
								}
								else {
									
									isOk = true;
									setVisible(false);
								}
							} catch (NumberFormatException e1) {
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

	public Donut getDonut() {
		return donut;
	}

	public void setDonut(Donut donut) {
		this.donut = donut;
	}

	public JTextField getTxtCenterX() {
		return txtCenterX;
	}

	public JTextField getTxtCenterY() {
		return txtCenterY;
	}

	public JTextField getTxtInnerRadius() {
		return txtInnerRadius;
	}

	public JTextField getTxtOuterRadius() {
		return txtOuterRadius;
	}

	public JButton getBtnOutLineColor() {
		return btnOutLineColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public boolean isOk() {
		return isOk;
	}

	public Color getOutLineColor() {
		return outLineColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public boolean isInnerColorOK() {
		return isInnerColorOK;
	}

	public boolean isOuterColorOK() {
		return isOuterColorOK;
	}

}
