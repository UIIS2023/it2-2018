package mvc;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class DrawingFrame extends JFrame {

	private DrawingView view = new DrawingView();
	private DrawingController controller;

	private JButton btnModify, btnDelete, btnUndo, btnRedo, btnLoadNext;
	private JToggleButton tglbtnPoint, tglbtnLine, tglbtnCircle, tglbtnDonut, tglbtnRectangle, tglbtnHexagon,
			tglbtnSelect;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel pnlColors;
	private JButton btnCurrentOuterColor;
	private JButton btnCurrentInnerColor;
	private Color currentInnerColor = Color.white;
	private Color currentOuterColor = Color.black;
	private boolean isCurrentInnerColorOK, isCurrentOuterColorOK;
	private JTextArea textArea;
	private JPanel panelUndoRedo;
	private JPanel PanelBringFront;
	private JButton btnBringToBack;
	private JButton btnBringToFront;
	private JButton btnToBack;
	private JButton btnToFront;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mnOpenPaint;
	private JMenuItem mnSavePaint;
	private JMenuItem mnSaveLog;
	private JMenuItem mnOpenLog;

	public DrawingFrame() {
		setBounds(100, 100, 1000, 700);

		JPanel pnlMain = new JPanel();
		pnlMain.setBackground(Color.WHITE);

		JPanel pnlShapes = new JPanel();

		JPanel pnlActions = new JPanel();

		pnlColors = new JPanel();

		JPanel panelLog = new JPanel();
		
		panelUndoRedo = new JPanel();
		
		PanelBringFront = new JPanel();
		
	     btnLoadNext = new JButton("Load next");
	     btnLoadNext.setEnabled(false);
	     btnLoadNext.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		controller.loadNext();
	     	}
	     });
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(pnlShapes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(pnlColors, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 131, Short.MAX_VALUE))
						.addComponent(PanelBringFront, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pnlActions, GroupLayout.PREFERRED_SIZE, 401, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelUndoRedo, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnLoadNext))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(view, GroupLayout.PREFERRED_SIZE, 694, GroupLayout.PREFERRED_SIZE)
							.addComponent(panelLog, GroupLayout.PREFERRED_SIZE, 694, GroupLayout.PREFERRED_SIZE)))
					.addGap(110))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlColors, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlActions, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelUndoRedo, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLoadNext))
					.addGap(2)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(28)
							.addComponent(view, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelLog, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(pnlShapes, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(PanelBringFront, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
							.addContainerGap())))
		);
		
		btnBringToBack = new JButton("Bring to back");
		btnBringToBack.setEnabled(false);
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
			
		});
		
		btnBringToFront = new JButton("Bring to front");
		btnBringToFront.setEnabled(false);
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		
		btnToBack = new JButton("To back");
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		
		btnToFront = new JButton("To front");
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		GroupLayout gl_PanelBringFront = new GroupLayout(PanelBringFront);
		gl_PanelBringFront.setHorizontalGroup(
			gl_PanelBringFront.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PanelBringFront.createSequentialGroup()
					.addGroup(gl_PanelBringFront.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBringToFront, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
						.addComponent(btnToBack, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
						.addComponent(btnToFront, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
						.addComponent(btnBringToBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_PanelBringFront.setVerticalGroup(
			gl_PanelBringFront.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PanelBringFront.createSequentialGroup()
					.addComponent(btnBringToBack)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBringToFront)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnToBack)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnToFront)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		PanelBringFront.setLayout(gl_PanelBringFront);
		
		 btnUndo = new JButton("Undo");
		 btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
				
			}
			
		});
		
		 btnRedo = new JButton("Redo");
		 btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		GroupLayout gl_panelUndoRedo = new GroupLayout(panelUndoRedo);
		gl_panelUndoRedo.setHorizontalGroup(
			gl_panelUndoRedo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUndoRedo.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panelUndoRedo.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRedo)
						.addComponent(btnUndo))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_panelUndoRedo.setVerticalGroup(
			gl_panelUndoRedo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUndoRedo.createSequentialGroup()
					.addGap(11)
					.addComponent(btnUndo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRedo)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panelUndoRedo.setLayout(gl_panelUndoRedo);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panelLog = new GroupLayout(panelLog);
		gl_panelLog.setHorizontalGroup(gl_panelLog.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE));
		gl_panelLog.setVerticalGroup(gl_panelLog.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE));

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		panelLog.setLayout(gl_panelLog);

		btnCurrentInnerColor = new JButton("Inner Color");
		btnCurrentInnerColor.setBackground(Color.WHITE);
		btnCurrentInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentInnerColor = JColorChooser.showDialog(null, "Choose color", currentInnerColor);
				btnCurrentInnerColor.setBackground(currentInnerColor);
				isCurrentInnerColorOK = true;
			}
		});
		pnlColors.add(btnCurrentInnerColor);

		btnCurrentOuterColor = new JButton("Outer Color");
		btnCurrentOuterColor.setBackground(Color.BLACK);
		btnCurrentOuterColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentOuterColor = JColorChooser.showDialog(null, "Choose color", currentOuterColor);
				btnCurrentOuterColor.setBackground(currentOuterColor);
				isCurrentOuterColorOK = true;
			}
		});
		pnlColors.add(btnCurrentOuterColor);

		tglbtnSelect = new JToggleButton("Select");
		tglbtnSelect.setEnabled(false);

		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.DeleteShape();
			}
		});

		btnModify = new JButton("Modify");
		btnModify.setEnabled(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modifyShape();
			}
		});
		GroupLayout gl_pnlActions = new GroupLayout(pnlActions);
		gl_pnlActions.setHorizontalGroup(gl_pnlActions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlActions.createSequentialGroup().addContainerGap().addComponent(tglbtnSelect)
						.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE).addComponent(btnDelete)
						.addGap(81).addComponent(btnModify).addContainerGap()));
		gl_pnlActions.setVerticalGroup(gl_pnlActions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlActions.createSequentialGroup().addContainerGap()
						.addGroup(gl_pnlActions.createParallelGroup(Alignment.BASELINE).addComponent(tglbtnSelect)
								.addComponent(btnModify).addComponent(btnDelete))
						.addContainerGap(14, Short.MAX_VALUE)));
		pnlActions.setLayout(gl_pnlActions);

		tglbtnPoint = new JToggleButton("Point");

		tglbtnLine = new JToggleButton("Line");

		tglbtnCircle = new JToggleButton("Circle");

		tglbtnRectangle = new JToggleButton("Rectangle");

		tglbtnDonut = new JToggleButton("Donut");

		tglbtnHexagon = new JToggleButton("Hexagon");
		GroupLayout gl_pnlShapes = new GroupLayout(pnlShapes);
		gl_pnlShapes.setHorizontalGroup(
			gl_pnlShapes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlShapes.createSequentialGroup()
					.addGroup(gl_pnlShapes.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(tglbtnPoint, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tglbtnLine, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tglbtnCircle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tglbtnHexagon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tglbtnDonut, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tglbtnRectangle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		gl_pnlShapes.setVerticalGroup(
			gl_pnlShapes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlShapes.createSequentialGroup()
					.addComponent(tglbtnPoint)
					.addGap(27)
					.addComponent(tglbtnLine)
					.addGap(31)
					.addComponent(tglbtnCircle)
					.addGap(31)
					.addComponent(tglbtnRectangle)
					.addGap(38)
					.addComponent(tglbtnDonut)
					.addGap(44)
					.addComponent(tglbtnHexagon)
					.addContainerGap(46, Short.MAX_VALUE))
		);
		pnlShapes.setLayout(gl_pnlShapes);
		GroupLayout gl_pnlMain = new GroupLayout(pnlMain);
		gl_pnlMain
				.setHorizontalGroup(gl_pnlMain.createParallelGroup(Alignment.LEADING).addGap(0, 694, Short.MAX_VALUE));
		gl_pnlMain.setVerticalGroup(gl_pnlMain.createParallelGroup(Alignment.LEADING).addGap(0, 355, Short.MAX_VALUE));
		pnlMain.setLayout(gl_pnlMain);
		getContentPane().setLayout(groupLayout);

		buttonGroup.add(tglbtnSelect);
		buttonGroup.add(tglbtnPoint);
		buttonGroup.add(tglbtnLine);
		buttonGroup.add(tglbtnCircle);
		buttonGroup.add(tglbtnDonut);
		buttonGroup.add(tglbtnRectangle);
		buttonGroup.add(tglbtnHexagon);

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				controller.draw(e);
			}
		});

		

		pnlMain.add(view, GroupLayout.DEFAULT_SIZE);
		view.setBackground(Color.WHITE);
		view.setPreferredSize(new Dimension(1000, 800));
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mnOpenPaint = new JMenuItem("Open paint");
		mnOpenPaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.openPaint();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mnOpenPaint);
		
		mnSavePaint = new JMenuItem("Save paint");
		mnSavePaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.savePaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mnSavePaint);
		
		mnSaveLog = new JMenuItem("Save log");
		mnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		mnFile.add(mnSaveLog);
	
		mnOpenLog = new JMenuItem("Open log");
		mnOpenLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.openLog();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mnOpenLog);

	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public JButton getBtnCurrentOuterColor() {
		return btnCurrentOuterColor;
	}

	public JButton getBtnCurrentInnerColor() {
		return btnCurrentInnerColor;
	}

	public boolean isCurrentInnerColorOK() {
		return isCurrentInnerColorOK;
	}

	public boolean isCurrentOuterColorOK() {
		return isCurrentOuterColorOK;
	}

	public Color getCurrentInnerColor() {
		return currentInnerColor;
	}

	public Color getCurrentOuterColor() {
		return currentOuterColor;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnLoadNext() {
		return btnLoadNext;
	}
}
