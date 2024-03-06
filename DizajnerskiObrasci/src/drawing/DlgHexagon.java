package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adapter.HexagonAdapter;
import geometry.Circle;
import geometry.Point;
import hexagon.Hexagon;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgHexagon extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JLabel lblInnerColor;
	private JLabel lblOutLineColor;
	private JLabel lblRadius;
	private JLabel lblCenterY;
	private JLabel lblCenterX;
	
	private JButton btnOutLineColor;
	private JButton btnInnerColor;
	
	
	private JTextField txtCenterX;
	private JTextField txtCenterY;
	private JTextField txtRadius;
	
	
	public boolean isOk, isInnerColorOK, isOuterColorOK;
	private Color outLineColor= Color.black;
	private Color innerColor= Color.white;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgHexagon dialog = new DlgHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgHexagon() {
		setBounds(100, 100, 450, 300);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblInnerColor = new JLabel("Inner Color");
		}
		{
			lblOutLineColor = new JLabel("OutLine Color");
		}
		{
			lblRadius = new JLabel("Radius");
		}
		{
			lblCenterY = new JLabel("Center Y");
		}
		{
			lblCenterX = new JLabel("Center X");
		}
		
		txtCenterX = new JTextField();
		txtCenterX.setColumns(10);
		
		txtCenterY = new JTextField();
		txtCenterY.setColumns(10);
		
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		
		 btnOutLineColor = new JButton("OutLine Color");
		 btnOutLineColor.setBackground(Color.BLACK);
		btnOutLineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 outLineColor = JColorChooser.showDialog(null, "Choose a outline Color", btnOutLineColor.getBackground());
					if(outLineColor != null)
						btnOutLineColor.setBackground(outLineColor);
					isOuterColorOK=true;
			}
		});
		
		 btnInnerColor = new JButton("Inner Color");
		 btnInnerColor.setBackground(Color.WHITE);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				innerColor = JColorChooser.showDialog(null, "Choose a inner Color", btnInnerColor.getBackground());
				if(innerColor != null)
					btnInnerColor.setBackground(innerColor);
				isInnerColorOK=true;
				
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCenterX)
								.addComponent(lblCenterY)
								.addComponent(lblRadius))
							.addGap(33)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCenterY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCenterX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOutLineColor)
								.addComponent(lblInnerColor))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnInnerColor)
								.addComponent(btnOutLineColor))))
					.addContainerGap(211, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterX)
						.addComponent(txtCenterX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterY)
						.addComponent(txtCenterY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOutLineColor)
						.addComponent(btnOutLineColor))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerColor)
						.addComponent(btnInnerColor))
					.addContainerGap(34, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JTextField getTxtCenterX() {
		return txtCenterX;
	}

	public JTextField getTxtCenterY() {
		return txtCenterY;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
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

	public Color getInnerColor() {
		return innerColor;
	}


	public JButton getBtnOutLineColor() {
		return btnOutLineColor;
	}

	public Color getOutLineColor() {
		return outLineColor;
	}

	public void setOutLineColor(Color outLineColor) {
		this.outLineColor = outLineColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}
}
