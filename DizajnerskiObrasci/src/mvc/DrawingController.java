package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import command.CmdAddShape;
import command.CmdBringToBack;
import command.CmdBringToFront;
import command.CmdDeleteShape;
import command.CmdDeselectShape;
import command.CmdModifyCircle;
import command.CmdModifyDonut;
import command.CmdModifyHexagon;
import command.CmdModifyLine;
import command.CmdModifyPoint;
import command.CmdModifyRectangle;
import command.CmdSelectShape;
import command.CmdToBack;
import command.CmdToFront;
import command.Command;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import hexagon.Hexagon;
import observer.ButtonObserver;
import observer.ButtonObserverUpdate;
import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SavePaint;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;

	private Command command;
	private Point startPoint;
	private Point endPoint;
	private Color pointColor, circleInnerColor, circleOuterColor, lineColor, donutInnerColor, donutOuterColor,
			rectangleInnerColor, rectangleOuterColor, hexagonInnerColor, hexagonOuterColor;

	private int undoCounter = 0;
	private int redoCounter = 0;
	
	

	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();

	private ArrayList<Shape> undoShapesList = new ArrayList<Shape>();
	private ArrayList<Shape> redoShapesList = new ArrayList<Shape>();

	private ArrayList<Shape> selectedShapeList = new ArrayList<Shape>();

	private ArrayList<String> logList = new ArrayList<String>();
	private int logCounter=0;
	
	private ButtonObserver buttonObserver = new ButtonObserver();
	private ButtonObserverUpdate buttonObserverUpdate;

	public DrawingController(DrawingModel model, DrawingFrame frame) {

		this.model = model;
		this.frame = frame;
		buttonObserverUpdate = new ButtonObserverUpdate(frame);
		buttonObserver.addPropertyChangeListener(buttonObserverUpdate);
	}

	public void draw(MouseEvent e) {
		if (frame.getTglbtnSelect().isSelected()) {
			Shape selectedShape = null;
			Shape shape = null;

			Iterator<Shape> it = this.model.getShapes().iterator();

			Command command = null;

			while (it.hasNext()) {
				shape = it.next();

				if (shape.contains(e.getX(), e.getY())) {
					selectedShape = shape;
				}

			}

			if (selectedShape != null) {

				if (selectedShape.isSelected()) {
					command = new CmdDeselectShape(this, selectedShape);
					undoStack.push(command);
					redoStack.clear();
					command.execute();
					frame.getTextArea().append(command.toString());
				} else {
					command = new CmdSelectShape(this, selectedShape);
					undoStack.push(command);
					redoStack.clear();
					command.execute();
					frame.getTextArea().append(command.toString());
				}
				undoCounter++;
			}
			undoRedoButtons();
			frame.repaint();
		} else {
			if (frame.getTglbtnPoint().isSelected()) {
				DlgPoint dlgPoint = new DlgPoint();
				dlgPoint.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlgPoint.getTxtCenterY().setText(Integer.toString(e.getY()));
				dlgPoint.getTxtCenterX().setEnabled(false);
				dlgPoint.getTxtCenterY().setEnabled(false);

				dlgPoint.getBtnColor().setBackground(frame.getCurrentOuterColor());
				dlgPoint.setVisible(true);

				if (dlgPoint.isOk()) {
					Point p = new Point(e.getX(), e.getY(), false, Color.black);
					if (dlgPoint.isColorOk()) {
						pointColor = dlgPoint.getColor();
						p.setColor(pointColor);
					} else {
						pointColor = frame.getCurrentOuterColor();
						p.setColor(pointColor);
					}

					command = new CmdAddShape(model, p);
					command.execute();
					undoCounter++;
					undoStack.push(command);
					redoStack.clear();
					frame.getTextArea().append(command.toString());
				}
			} else if (frame.getTglbtnLine().isSelected()) {

				if (startPoint == null) {
					
					startPoint = new Point(e.getX(), e.getY());

				} else {
					endPoint = new Point(e.getX(), e.getY());

					DlgLine dlgLine = new DlgLine();

					dlgLine.getTxtStartPointX().setText(Integer.toString(startPoint.getX()));
					dlgLine.getTxtStartPointY().setText(Integer.toString(startPoint.getY()));
					dlgLine.getTxtEndPointX().setText(Integer.toString(endPoint.getX()));
					dlgLine.getTxtEndPointY().setText(Integer.toString(endPoint.getY()));

					dlgLine.getTxtStartPointX().setEnabled(false);
					dlgLine.getTxtStartPointY().setEnabled(false);
					dlgLine.getTxtEndPointX().setEnabled(false);
					dlgLine.getTxtEndPointY().setEnabled(false);

					dlgLine.getBtnColor().setBackground(frame.getCurrentOuterColor());
					dlgLine.setVisible(true);

					if (dlgLine.isOk()) {

						Line l = new Line(startPoint, endPoint, false, Color.black);

						if (dlgLine.isColorOk()) {
							lineColor = dlgLine.getColor();
							l.setColor(lineColor);
						} else {
							lineColor = frame.getCurrentOuterColor();
							l.setColor(lineColor);
						}

						command = new CmdAddShape(model, l);
						command.execute();
						undoCounter++;
						undoStack.push(command);
						redoStack.clear();
						frame.getTextArea().append(command.toString());

					}

					startPoint = null;
				}

			} else if (frame.getTglbtnCircle().isSelected()) {

				Point p = new Point(e.getX(), e.getY());

				DlgCircle dlgCircle = new DlgCircle();

				dlgCircle.getTxtCenterX().setText(Integer.toString(p.getX()));
				dlgCircle.getTxtCenterY().setText(Integer.toString(p.getY()));

				dlgCircle.getTxtCenterX().setEnabled(false);
				dlgCircle.getTxtCenterY().setEnabled(false);

				dlgCircle.getBtnInnerColor().setBackground(frame.getCurrentInnerColor());
				dlgCircle.getBtnOutLineColor().setBackground(frame.getCurrentOuterColor());
				dlgCircle.setVisible(true);

				if (dlgCircle.isOk()) {

					int radius = Integer.parseInt(dlgCircle.getTxtRadius().getText().toString());

					Circle c = new Circle(p, radius, false, Color.black, Color.WHITE);

					if (dlgCircle.isInnerColorOK()) {

						circleInnerColor = dlgCircle.getInnerColor();
						c.setInnerColor(circleInnerColor);
					} else {

						circleInnerColor = frame.getCurrentInnerColor();
						c.setInnerColor(circleInnerColor);

					}

					if (dlgCircle.isOuterColorOK()) {

						circleOuterColor = dlgCircle.getOutLineColor();
						c.setColor(circleOuterColor);
					} else {

						circleOuterColor = frame.getCurrentOuterColor();
						c.setColor(circleOuterColor);

					}

					command = new CmdAddShape(model, c);
					command.execute();
					undoCounter++;
					undoStack.push(command);
					redoStack.clear();
					frame.getTextArea().append(command.toString());
				}

			}

			else if (frame.getTglbtnRectangle().isSelected()) {

				Point upperLeftPoint = new Point(e.getX(), e.getY());

				DlgRectangle dlgRectangle = new DlgRectangle();

				dlgRectangle.getTxtUpperLeftPointX().setText(Integer.toString(upperLeftPoint.getX()));
				dlgRectangle.getTxtUpperLeftPointY().setText(Integer.toString(upperLeftPoint.getY()));

				dlgRectangle.getTxtUpperLeftPointX().setEnabled(false);
				dlgRectangle.getTxtUpperLeftPointY().setEnabled(false);

				dlgRectangle.getBtnInnerColor().setBackground(frame.getCurrentInnerColor());
				dlgRectangle.getBtnOutLineColor().setBackground(frame.getCurrentOuterColor());

				dlgRectangle.setVisible(true);

				if (dlgRectangle.isOk()) {

					int width = Integer.parseInt(dlgRectangle.getTxtWidth().getText().toString());
					int height = Integer.parseInt(dlgRectangle.getTxtHeight().getText().toString());

					Rectangle r = new Rectangle(upperLeftPoint, height, width, false, Color.black, Color.WHITE);

					if (dlgRectangle.isInnerColorOK()) {

						rectangleInnerColor = dlgRectangle.getInnerColor();
						r.setInnerColor(rectangleInnerColor);
					} else {

						rectangleInnerColor = frame.getCurrentInnerColor();
						r.setInnerColor(rectangleInnerColor);

					}

					if (dlgRectangle.isOuterColorOK()) {

						rectangleOuterColor = dlgRectangle.getOutLineColor();
						r.setColor(rectangleOuterColor);
					} else {

						rectangleOuterColor = frame.getCurrentOuterColor();
						r.setColor(rectangleOuterColor);

					}

					command = new CmdAddShape(model, r);
					command.execute();
					undoCounter++;
					undoStack.push(command);
					redoStack.clear();
					frame.getTextArea().append(command.toString());
				}

			} else if (frame.getTglbtnDonut().isSelected()) {

				Point center = new Point(e.getX(), e.getY());

				DlgDonut dlgDonut = new DlgDonut();

				dlgDonut.getTxtCenterX().setText(Integer.toString(center.getX()));
				dlgDonut.getTxtCenterY().setText(Integer.toString(center.getY()));

				dlgDonut.getTxtCenterX().setEnabled(false);
				dlgDonut.getTxtCenterY().setEnabled(false);

				dlgDonut.getBtnInnerColor().setBackground(frame.getCurrentInnerColor());
				dlgDonut.getBtnOutLineColor().setBackground(frame.getCurrentOuterColor());

				dlgDonut.setVisible(true);

				if (dlgDonut.isOk()) {

					int innerRadius = Integer.parseInt(dlgDonut.getTxtInnerRadius().getText().toString());
					int outerRadius = Integer.parseInt(dlgDonut.getTxtOuterRadius().getText().toString());

					Donut d = new Donut(center, outerRadius, innerRadius, false, Color.black, Color.WHITE);

					if (dlgDonut.isInnerColorOK()) {

						donutInnerColor = dlgDonut.getInnerColor();
						d.setInnerColor(donutInnerColor);
					} else {

						donutInnerColor = frame.getCurrentInnerColor();
						d.setInnerColor(donutInnerColor);

					}

					if (dlgDonut.isOuterColorOK()) {

						donutOuterColor = dlgDonut.getOutLineColor();
						d.setColor(donutOuterColor);
					} else {

						donutOuterColor = frame.getCurrentOuterColor();
						d.setColor(donutOuterColor);

					}

					command = new CmdAddShape(model, d);
					command.execute();
					undoCounter++;
					undoStack.push(command);
					redoStack.clear();
					frame.getTextArea().append(command.toString());

				}

			}

			else if (frame.getTglbtnHexagon().isSelected()) {

				Point p = new Point(e.getX(), e.getY());

				DlgHexagon dlgHexagon = new DlgHexagon();

				dlgHexagon.getTxtCenterX().setText(Integer.toString(p.getX()));
				dlgHexagon.getTxtCenterY().setText(Integer.toString(p.getY()));

				dlgHexagon.getTxtCenterX().setEnabled(false);
				dlgHexagon.getTxtCenterY().setEnabled(false);

				dlgHexagon.getBtnInnerColor().setBackground(frame.getCurrentInnerColor());
				dlgHexagon.getBtnOutLineColor().setBackground(frame.getCurrentOuterColor());
				dlgHexagon.setVisible(true);

				if (dlgHexagon.isOk()) {

					int radius = Integer.parseInt(dlgHexagon.getTxtRadius().getText().toString());

					HexagonAdapter h = new HexagonAdapter(p, radius, false, Color.black, Color.WHITE);

					if (dlgHexagon.isInnerColorOK()) {

						hexagonInnerColor = dlgHexagon.getInnerColor();
						h.setHexagonInnerColor(hexagonInnerColor);
					} else {

						hexagonInnerColor = frame.getCurrentInnerColor();
						h.setHexagonInnerColor(hexagonInnerColor);

					}

					if (dlgHexagon.isOuterColorOK()) {

						hexagonOuterColor = dlgHexagon.getOutLineColor();
						h.setHexagonBorderColor(hexagonOuterColor);
					} else {

						hexagonOuterColor = frame.getCurrentOuterColor();
						h.setHexagonBorderColor(hexagonOuterColor);

					}

					command = new CmdAddShape(model, h);
					command.execute();
					undoCounter++;
					undoStack.push(command);
					redoStack.clear();
					frame.getTextArea().append(command.toString());
				}

			}
		}

		enableDisableButtons();
		undoRedoButtons();
		frame.repaint();
	}

	public void DeleteShape() {

		Shape shape;
		for (int i = selectedShapeList.size() - 1; i >= 0; i--) {
			shape = selectedShapeList.get(0);
			command = new CmdDeleteShape(model, shape, model.getShapes().indexOf(shape));
			undoCounter++;
			undoStack.push(command);
			redoStack.clear();
			undoShapesList.add(shape);
			command.execute();
			selectedShapeList.remove(shape);
			frame.getTextArea().append(command.toString());

		}
		redoStack.clear();
		enableDisableButtons();
		undoRedoButtons();
		frame.repaint();

	}

	public void modifyShape() {

		if (selectedShapeList.get(0) instanceof Donut) {

			DlgDonut dlgDonut = new DlgDonut();

			Donut oldDonut = (Donut) selectedShapeList.get(0);

			dlgDonut.getTxtCenterX().setText(Integer.toString(oldDonut.getCenter().getX()));
			dlgDonut.getTxtCenterY().setText(Integer.toString(oldDonut.getCenter().getY()));
			dlgDonut.getTxtOuterRadius().setText(Integer.toString(oldDonut.getRadius()));
			dlgDonut.getTxtInnerRadius().setText(Integer.toString(oldDonut.getInnerRadius()));

			dlgDonut.getBtnInnerColor().setBackground(oldDonut.getInnerColor());
			dlgDonut.getBtnOutLineColor().setBackground(oldDonut.getColor());

			dlgDonut.setVisible(true);

			if (dlgDonut.isOk()) {

				Donut newDonut = new Donut(
						new Point(Integer.parseInt(dlgDonut.getTxtCenterX().getText()),
								Integer.parseInt(dlgDonut.getTxtCenterY().getText())),
						Integer.parseInt(dlgDonut.getTxtOuterRadius().getText()),
						Integer.parseInt(dlgDonut.getTxtInnerRadius().getText()), true,
						dlgDonut.getBtnOutLineColor().getBackground(), dlgDonut.getBtnInnerColor().getBackground());

				donutInnerColor = oldDonut.getInnerColor();
				donutOuterColor = oldDonut.getColor();

				if (dlgDonut.isInnerColorOK()) {

					newDonut.setInnerColor(dlgDonut.getInnerColor());

				} else {
					newDonut.setInnerColor(donutInnerColor);
				}

				if (dlgDonut.isOuterColorOK()) {

					newDonut.setColor(dlgDonut.getOutLineColor());

				} else {
					newDonut.setColor(donutOuterColor);
				}

				command = new CmdModifyDonut(oldDonut, newDonut);
				undoCounter++;
				undoStack.push(command);
				redoStack.clear();
				command.execute();
				frame.getTextArea().append(command.toString());
			}
		} else if (selectedShapeList.get(0) instanceof Circle) {

			DlgCircle dlgCircle = new DlgCircle();

			Circle oldCircle = (Circle) selectedShapeList.get(0);

			dlgCircle.getTxtCenterX().setText(Integer.toString(oldCircle.getCenter().getX()));
			dlgCircle.getTxtCenterY().setText(Integer.toString(oldCircle.getCenter().getY()));
			dlgCircle.getTxtRadius().setText(Integer.toString(oldCircle.getRadius()));

			dlgCircle.getBtnInnerColor().setBackground(oldCircle.getInnerColor());
			dlgCircle.getBtnOutLineColor().setBackground(oldCircle.getColor());

			dlgCircle.setVisible(true);

			if (dlgCircle.isOk()) {

				Circle newCircle = new Circle(
						new Point(Integer.parseInt(dlgCircle.getTxtCenterX().getText()),
								Integer.parseInt(dlgCircle.getTxtCenterY().getText())),
						Integer.parseInt(dlgCircle.getTxtRadius().getText()), true,
						dlgCircle.getBtnOutLineColor().getBackground(), dlgCircle.getBtnInnerColor().getBackground());

				circleInnerColor = oldCircle.getInnerColor();
				circleOuterColor = oldCircle.getColor();

				if (dlgCircle.isInnerColorOK()) {

					newCircle.setInnerColor(dlgCircle.getInnerColor());

				} else {
					newCircle.setInnerColor(circleInnerColor);
				}

				if (dlgCircle.isOuterColorOK()) {

					newCircle.setColor(dlgCircle.getOutLineColor());

				} else {
					newCircle.setColor(circleOuterColor);
				}

				command = new CmdModifyCircle(oldCircle, newCircle);
				undoCounter++;
				undoStack.push(command);
				redoStack.clear();
				command.execute();
				frame.getTextArea().append(command.toString());
			}

		}

		else if (selectedShapeList.get(0) instanceof HexagonAdapter) {

			DlgHexagon dlgHexagon = new DlgHexagon();

			HexagonAdapter oldHexagon = (HexagonAdapter) selectedShapeList.get(0);

			dlgHexagon.getTxtCenterX().setText(Integer.toString(oldHexagon.getHexagonCenter().getX()));
			dlgHexagon.getTxtCenterY().setText(Integer.toString(oldHexagon.getHexagonCenter().getY()));
			dlgHexagon.getTxtRadius().setText(Integer.toString(oldHexagon.getHexagonRadius()));

			dlgHexagon.getBtnInnerColor().setBackground(oldHexagon.getHexagonInnerColor());
			dlgHexagon.getBtnOutLineColor().setBackground(oldHexagon.getHexagonBorderColor());

			dlgHexagon.setVisible(true);

			if (dlgHexagon.isOk()) {

				HexagonAdapter newHexagon = new HexagonAdapter(
						new Point(Integer.parseInt(dlgHexagon.getTxtCenterX().getText()),
								Integer.parseInt(dlgHexagon.getTxtCenterY().getText())),
						Integer.parseInt(dlgHexagon.getTxtRadius().getText()), true,
						dlgHexagon.getBtnOutLineColor().getBackground(), dlgHexagon.getBtnInnerColor().getBackground());

				hexagonInnerColor = oldHexagon.getHexagonInnerColor();
				hexagonOuterColor = oldHexagon.getHexagonBorderColor();

				if (dlgHexagon.isInnerColorOK()) {

					newHexagon.setHexagonInnerColor(dlgHexagon.getInnerColor());
				} else {
					newHexagon.setHexagonInnerColor(hexagonInnerColor);
				}

				if (dlgHexagon.isOuterColorOK()) {

					newHexagon.setHexagonBorderColor(dlgHexagon.getOutLineColor());
				} else {

					newHexagon.setHexagonBorderColor(hexagonOuterColor);

				}

				command = new CmdModifyHexagon(oldHexagon, newHexagon);
				undoCounter++;
				undoStack.push(command);
				redoStack.clear();
				command.execute();
				frame.getTextArea().append(command.toString());
			}

		}

		else if (selectedShapeList.get(0) instanceof Line) {

			DlgLine dlgLine = new DlgLine();

			Line oldLine = (Line) selectedShapeList.get(0);

			dlgLine.getTxtStartPointX().setText(Integer.toString(oldLine.getStartPoint().getX()));
			dlgLine.getTxtStartPointY().setText(Integer.toString(oldLine.getStartPoint().getY()));
			dlgLine.getTxtEndPointX().setText(Integer.toString(oldLine.getEndPoint().getX()));
			dlgLine.getTxtEndPointY().setText(Integer.toString(oldLine.getEndPoint().getY()));

			dlgLine.getBtnColor().setBackground(oldLine.getColor());

			dlgLine.setVisible(true);

			if (dlgLine.isOk()) {

				Line newLine = new Line(
						new Point(Integer.parseInt(dlgLine.getTxtStartPointX().getText()),
								Integer.parseInt(dlgLine.getTxtStartPointY().getText())),
						new Point(Integer.parseInt(dlgLine.getTxtEndPointX().getText()),
								Integer.parseInt(dlgLine.getTxtEndPointY().getText())),
						true, dlgLine.getBtnColor().getBackground());

				lineColor = oldLine.getColor();

				if (dlgLine.isColorOk()) {

					newLine.setColor(dlgLine.getColor());

				} else {
					newLine.setColor(lineColor);
				}

				command = new CmdModifyLine(oldLine, newLine);
				undoCounter++;
				undoStack.push(command);
				redoStack.clear();
				command.execute();
				frame.getTextArea().append(command.toString());
			}

		}

		else if (selectedShapeList.get(0) instanceof Point) {

			DlgPoint dlgPoint = new DlgPoint();

			Point oldPoint = (Point) selectedShapeList.get(0);

			dlgPoint.getTxtCenterX().setText(Integer.toString(oldPoint.getX()));
			dlgPoint.getTxtCenterY().setText(Integer.toString(oldPoint.getY()));

			dlgPoint.getBtnColor().setBackground(oldPoint.getColor());

			dlgPoint.setVisible(true);

			if (dlgPoint.isOk()) {

				Point newPoint = new Point((Integer.parseInt(dlgPoint.getTxtCenterX().getText())),
						(Integer.parseInt(dlgPoint.getTxtCenterY().getText())), true,
						dlgPoint.getBtnColor().getBackground());

				pointColor = oldPoint.getColor();

				if (dlgPoint.isColorOk()) {

					newPoint.setColor(dlgPoint.getColor());

				} else {
					newPoint.setColor(pointColor);
				}

				if (dlgPoint.isColorOk()) {

					newPoint.setColor(dlgPoint.getColor());

				} else {
					newPoint.setColor(pointColor);
				}

				command = new CmdModifyPoint(oldPoint, newPoint);
				undoCounter++;
				undoStack.push(command);
				redoStack.clear();
				command.execute();
				frame.getTextArea().append(command.toString());
			}

		}

		else if (selectedShapeList.get(0) instanceof Rectangle) {

			DlgRectangle dlgRectangle = new DlgRectangle();

			Rectangle oldRectangle = (Rectangle) selectedShapeList.get(0);

			dlgRectangle.getTxtUpperLeftPointX().setText(Integer.toString(oldRectangle.getUpperLeftPoint().getX()));
			dlgRectangle.getTxtUpperLeftPointY().setText(Integer.toString(oldRectangle.getUpperLeftPoint().getY()));

			dlgRectangle.getTxtHeight().setText(Integer.toString(oldRectangle.getHeight()));
			dlgRectangle.getTxtWidth().setText(Integer.toString(oldRectangle.getWidth()));

			dlgRectangle.getBtnOutLineColor().setBackground(oldRectangle.getColor());
			dlgRectangle.getBtnInnerColor().setBackground(oldRectangle.getInnerColor());

			dlgRectangle.setVisible(true);

			if (dlgRectangle.isOk()) {

				Rectangle newRectangle = new Rectangle(
						new Point(Integer.parseInt(dlgRectangle.getTxtUpperLeftPointX().getText()),
								Integer.parseInt(dlgRectangle.getTxtUpperLeftPointY().getText())),
						Integer.parseInt(dlgRectangle.getTxtHeight().getText()),
						Integer.parseInt(dlgRectangle.getTxtWidth().getText()), true,
						dlgRectangle.getBtnOutLineColor().getBackground(),
						dlgRectangle.getBtnInnerColor().getBackground());

				rectangleInnerColor = oldRectangle.getInnerColor();
				rectangleOuterColor = oldRectangle.getColor();

				if (dlgRectangle.isInnerColorOK()) {

					newRectangle.setInnerColor(dlgRectangle.getInnerColor());

				} else {
					newRectangle.setInnerColor(rectangleInnerColor);
				}

				if (dlgRectangle.isOuterColorOK()) {

					newRectangle.setColor(dlgRectangle.getOutLineColor());

				} else {
					newRectangle.setColor(rectangleOuterColor);
				}

				command = new CmdModifyRectangle(oldRectangle, newRectangle);
				undoCounter++;
				undoStack.push(command);
				redoStack.clear();
				command.execute();
				frame.getTextArea().append(command.toString());
			}

		}
		enableDisableButtons();
		undoRedoButtons();
		frame.repaint();
	}

	public void undo() {
		command = undoStack.peek();

		if (command instanceof CmdDeleteShape) {
			while (command instanceof CmdDeleteShape) {
				command.unexecute();
				redoShapesList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
				selectedShapeList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
				undoShapesList.remove(this.undoShapesList.size() - 1);
				frame.getTextArea().append("Undo " + undoStack.peek().toString());
				undoCounter--;
				redoCounter++;
				undoStack.pop();
				redoStack.push(command);
				command = undoStack.peek();
			}
		} else {
			command.unexecute();
			this.frame.getTextArea().append("Undo " + undoStack.peek().toString());
			undoCounter--;
			redoCounter++;
			undoStack.pop();
			redoStack.push(command);
		}
		enableDisableButtons();
		undoRedoButtons();
		frame.repaint();

	}

	public void redo() {
		command = redoStack.peek();

		if (command instanceof CmdDeleteShape) {
			while (command instanceof CmdDeleteShape) {
				command.execute();
				undoShapesList.add(this.redoShapesList.get(this.redoShapesList.size() - 1));
				selectedShapeList.remove(this.redoShapesList.get(this.redoShapesList.size() - 1));
				redoShapesList.remove(this.redoShapesList.size() - 1);
				frame.getTextArea().append("Redo " + redoStack.peek().toString());
				undoCounter++;
				redoCounter--;
				redoStack.pop();
				undoStack.push(command);
				if (!redoStack.isEmpty()) {
					command = redoStack.peek();
				} else {
					command = null;
				}

			}
		} else {
			command.execute();
			this.frame.getTextArea().append("Redo " + redoStack.peek().toString());
			undoCounter++;
			redoCounter--;
			redoStack.pop();
			undoStack.push(command);
		}
		enableDisableButtons();
		undoRedoButtons();
		frame.repaint();

	}

	public void undoRedoButtons() {
		if (undoCounter < 1) {
			frame.getBtnUndo().setEnabled(false);
		} else {
			frame.getBtnUndo().setEnabled(true);
		}

		if (redoCounter < 1 || redoStack.isEmpty()) {
			frame.getBtnRedo().setEnabled(false);
		} else {
			frame.getBtnRedo().setEnabled(true);
		}
	}

	public void bringToBack() {

		Shape shape = selectedShapeList.get(0);
		CmdBringToBack cmdBringToBack = new CmdBringToBack(model, shape);
		cmdBringToBack.execute();
		frame.getTextArea().append(cmdBringToBack.toString());
		undoStack.push(cmdBringToBack);
		undoCounter++;
		redoStack.clear();
		undoRedoButtons();
		enableDisableButtons();
		frame.repaint();

	}

	public void bringToFront() {

		Shape shape = selectedShapeList.get(0);
		CmdBringToFront cmdBringToFront = new CmdBringToFront(model, shape);
		cmdBringToFront.execute();
		frame.getTextArea().append(cmdBringToFront.toString());
		undoStack.push(cmdBringToFront);
		undoCounter++;
		redoStack.clear();
		undoRedoButtons();
		enableDisableButtons();
		frame.repaint();

	}

	public void toFront() {

		Shape shape = selectedShapeList.get(0);
		CmdToFront cmdToFront = new CmdToFront(model, shape);
		cmdToFront.execute();
		frame.getTextArea().append(cmdToFront.toString());
		undoStack.push(cmdToFront);
		undoCounter++;
		redoStack.clear();
		undoRedoButtons();
		enableDisableButtons();
		frame.repaint();

	}

	public void toBack() {

		Shape shape = selectedShapeList.get(0);
		CmdToBack cmdToBack = new CmdToBack(model, shape);
		cmdToBack.execute();
		frame.getTextArea().append(cmdToBack.toString());
		undoStack.push(cmdToBack);
		undoCounter++;
		redoStack.clear();
		undoRedoButtons();
		enableDisableButtons();
		frame.repaint();

	}

	public void enableDisableButtons() {

		if (model.getShapes().size() != 0) {
			buttonObserver.setSelectButtonEnabled(true);
			if (selectedShapeList.size() != 0) {

				if (selectedShapeList.size() == 1) {

					buttonObserver.setModifyButtonEnabled(true);

					buttonUpdate();
				}

				else {

					buttonObserver.setModifyButtonEnabled(false);

					buttonObserver.setBringToFrontButtonEnabled(false);
					buttonObserver.setBringToBackButtonEnabled(false);
					buttonObserver.setToBackButtonEnabled(false);
					buttonObserver.setToFrontButtonEnabled(false);
				}
				buttonObserver.setDeleteButtonEnabled(true);
			} else {

				buttonObserver.setBringToBackButtonEnabled(false);
				buttonObserver.setBringToFrontButtonEnabled(false);
				buttonObserver.setToBackButtonEnabled(false);
				buttonObserver.setToFrontButtonEnabled(false);

				buttonObserver.setDeleteButtonEnabled(false);
				buttonObserver.setModifyButtonEnabled(false);

			}
		} else {
			buttonObserver.setBringToBackButtonEnabled(false);
			buttonObserver.setBringToFrontButtonEnabled(false);
			buttonObserver.setToBackButtonEnabled(false);
			buttonObserver.setToFrontButtonEnabled(false);

			buttonObserver.setSelectButtonEnabled(false);
			buttonObserver.setDeleteButtonEnabled(false);
			buttonObserver.setModifyButtonEnabled(false);
		}

	}

	public void buttonUpdate() {

		Iterator<Shape> it = this.model.getShapes().iterator();
		Shape shape;

		while (it.hasNext()) {
			shape = it.next();

			if (shape.isSelected()) {
				if (this.model.getShapes().size() == 1) {
					buttonObserver.setBringToBackButtonEnabled(false);
					buttonObserver.setBringToFrontButtonEnabled(false);
					buttonObserver.setToBackButtonEnabled(false);
					buttonObserver.setToFrontButtonEnabled(false);
				} else {

					if (shape.equals(model.getShape(this.model.getShapes().size() - 1))) {

						buttonObserver.setBringToBackButtonEnabled(true);
						buttonObserver.setBringToFrontButtonEnabled(false);
						buttonObserver.setToBackButtonEnabled(true);
						buttonObserver.setToFrontButtonEnabled(false);
					} else if (shape.equals(model.getShape(0))) {
						buttonObserver.setBringToBackButtonEnabled(false);
						buttonObserver.setBringToFrontButtonEnabled(true);
						buttonObserver.setToBackButtonEnabled(false);
						buttonObserver.setToFrontButtonEnabled(true);

					} else {
						buttonObserver.setBringToBackButtonEnabled(true);
						buttonObserver.setBringToFrontButtonEnabled(true);
						buttonObserver.setToBackButtonEnabled(true);
						buttonObserver.setToFrontButtonEnabled(true);
					}

				}
			}
		}
	}

	public void openPaint() throws IOException, ClassNotFoundException {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(".bin", "bin");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(fileNameExtensionFilter);

		fileChooser.setDialogTitle("Open paint");
		int userSelection = fileChooser.showOpenDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File paintToLoad = fileChooser.getSelectedFile();
			loadPaint(paintToLoad);

		}

	}

	public void savePaint() throws IOException, NotSerializableException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save paint");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File paintToSave = fileChooser.getSelectedFile();
			File logToSave;
			String filePath = paintToSave.getAbsolutePath();
			if (!filePath.endsWith(".bin") && !filePath.contains(".")) {
				paintToSave = new File(filePath + ".bin");
				logToSave = new File(filePath + ".txt");
			}

			String filename = paintToSave.getPath();
			System.out.println("Paint saved at: " + paintToSave.getAbsolutePath());
			System.out.println(filename.substring(filename.lastIndexOf("."), filename.length()));
			if (filename.substring(filename.lastIndexOf("."), filename.length()).contains(".bin")) {
				filename = paintToSave.getAbsolutePath().substring(0, filename.lastIndexOf(".")) + ".txt";
				logToSave = new File(filename);
				SaveManager savePaint = new SaveManager(new SavePaint());
				SaveManager saveLog = new SaveManager(new SaveLog());
				savePaint.save(model, paintToSave);
				saveLog.save(frame, logToSave);
			} else {
				JOptionPane.showMessageDialog(null, "Wrong file extension!");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void loadPaint(File paintToLoad) throws FileNotFoundException, IOException, ClassNotFoundException {
		frame.getTextArea().setText("");

		File file = new File(paintToLoad.getAbsolutePath().replace("bin", "txt"));

		if (file.length() == 0) {
			System.out.println("\"" + paintToLoad.getName() + "\" file is empty!");
			return;
		}

		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String logLine;

		while ((logLine = bufferedReader.readLine()) != null) {
			frame.getTextArea().append(logLine + "\n");
		}
		bufferedReader.close();

		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(paintToLoad));
		try {

			model.getShapes().addAll((ArrayList<Shape>) objectInputStream.readObject());
			objectInputStream.close();

		} catch (InvalidClassException ice) {
			ice.printStackTrace();
		} catch (SocketTimeoutException ste) {
			ste.printStackTrace();
		} catch (EOFException eofe) {
			eofe.printStackTrace();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		frame.repaint();
	}

	
	
	public void saveLog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save log");
		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(fileNameExtensionFilter);

		if (fileChooser.showSaveDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {
			System.out.println("Successfully saved " + fileChooser.getSelectedFile().getName() + " file!");
			File file = fileChooser.getSelectedFile();
			String filePath = file.getAbsolutePath();
			File logToSave = new File(filePath + ".txt");

			SaveManager manager = new SaveManager(new SaveLog());
			manager.save(frame, logToSave);
		}
		frame.getView().repaint();
	}


	public void openLog() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open log");
		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(fileNameExtensionFilter);

		int userSelection = fileChooser.showOpenDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File logToLoad = fileChooser.getSelectedFile();
			loadLog(logToLoad);
		}
	}
	
	
	public void loadLog(File logToLoad) throws IOException {
		try {
			frame.getTextArea().setText("");
			if (logToLoad.length() == 0) {
				System.out.println("\"" + logToLoad.getName() + "\" file is empty!");
				return;
			}
			BufferedReader br = new BufferedReader(new FileReader(logToLoad));
			String stringLine;
			// read log line by line 
			while ((stringLine = br.readLine()) != null) {
				logList.add(stringLine);
			}
			br.close();
			frame.getBtnLoadNext().setEnabled(true);

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	public void loadNext() {
		Shape shape = null;

		if (logCounter < logList.size()) {

			String line = logList.get(logCounter);

			if (line.contains("Point")) {	
				int x = Integer.parseInt(line.substring(findLeftBracket(1, line) + 1, findComma(1, line)));
				int y = Integer.parseInt(line.substring(findComma(1, line) + 2, findRightBracket(1, line)));
				int Color = Integer.parseInt(line.substring(findLeftBracket(2, line) + 1, findRightBracket(2, line)));

				shape = new Point(x, y, new Color(Color));

			} else if (line.contains("Line")) {
				int startPointX = Integer.parseInt(line.substring(findLeftBracket(1, line) + 1, findComma(1, line)));
				int startPointY = Integer.parseInt(line.substring(findComma(1, line) + 2, findRightBracket(1, line)));
				int endPointX = Integer.parseInt(line.substring(findLeftBracket(2, line) + 1, findComma(2, line)));
				int endPointY = Integer.parseInt(line.substring(findComma(2, line) + 2, findRightBracket(2, line)));
				int color = Integer.parseInt(line.substring(findLeftBracket(3, line) + 1, findRightBracket(3, line)));
				
				shape = new Line(new Point(startPointX, startPointY), new Point(endPointX, endPointY), new Color(color));

			} else if (line.contains("Rectangle")) {

				int upperLeftPointX = Integer.parseInt(line.substring(findLeftBracket(1, line) + 1, findComma(1, line)));
				int upperLeftPointY = Integer.parseInt(line.substring(findComma(1, line) + 2, findRightBracket(1, line)));
				int height = Integer.parseInt(line.substring(findEqualSign(1, line) + 1, findComma(3, line)));
				int width = Integer.parseInt(line.substring(findEqualSign(2, line) + 1, findComma(4, line)));
				int OutLineColor = Integer.parseInt(line.substring(findLeftBracket(2, line) + 1, findRightBracket(2, line)));
				int innerColor = Integer.parseInt(line.substring(findLeftBracket(3, line) + 1, findRightBracket(3, line)));

				shape = new Rectangle(new Point(upperLeftPointX, upperLeftPointY), height, width, new Color(OutLineColor), new Color(innerColor));
				
			} else if (line.contains("Circle")) {

				int x = Integer.parseInt(line.substring(findLeftBracket(1, line) + 1, findComma(1, line)));
				int y = Integer.parseInt(line.substring(findComma(1, line) + 2, findRightBracket(1, line)));
				int radius = Integer.parseInt(line.substring(findEqualSign(1, line) + 1, findComma(3, line)));
				int outLineColor = Integer.parseInt(line.substring(findLeftBracket(2, line) + 1, findRightBracket(2, line)));
				int innerColor = Integer.parseInt(line.substring(findLeftBracket(3, line) + 1, findRightBracket(3, line)));

				shape = new Circle(new Point(x, y), radius, new Color(outLineColor), new Color(innerColor));
			} else if (line.contains("Donut")) {

				int x = Integer.parseInt(line.substring(findLeftBracket(1, line) + 1, findComma(1, line)));
				int y = Integer.parseInt(line.substring(findComma(1, line) + 2, findRightBracket(1, line)));
				int radius = Integer.parseInt(line.substring(findEqualSign(1, line) + 1, findComma(3, line)));
				int innerRadius = Integer.parseInt(line.substring(findEqualSign(2, line) + 1, findComma(4, line))); 
				int outLineColor = Integer.parseInt(line.substring(findLeftBracket(2, line) + 1, findRightBracket(2, line)));
				int innerColor = Integer.parseInt(line.substring(findLeftBracket(3, line) + 1, findRightBracket(3, line)));

				shape = new Donut(new Point(x, y), radius, innerRadius, new Color(outLineColor), new Color(innerColor));
				
			} else if (line.contains("Hexagon")) {

				int x = Integer.parseInt(line.substring(findLeftBracket(1, line) + 1, findComma(1, line)));
				int y = Integer.parseInt(line.substring(findComma(1, line) + 2, findRightBracket(1, line)));
				int radius = Integer.parseInt(line.substring(findEqualSign(1, line) + 1, findComma(3, line)));
				int outLineColor = Integer.parseInt(line.substring(findLeftBracket(2, line) + 1, findRightBracket(2, line)));
				int innerColor = Integer.parseInt(line.substring(findLeftBracket(3, line) + 1, findRightBracket(3, line)));
				
				shape = new HexagonAdapter(new Point(x, y), radius, new Color(outLineColor), new Color(innerColor));
			}
	
			if(line.contains("Added")) {	
				CmdAddShape cmdAddShape;
				
				if(line.contains("Undo")) {
					cmdAddShape = (CmdAddShape) undoStack.peek();
					cmdAddShape.unexecute();
					undoStack.pop();
					redoStack.push(cmdAddShape);
					frame.getTextArea().append("Undo " + cmdAddShape.toString());
				} else if (line.contains("Redo")) {
					cmdAddShape = (CmdAddShape) redoStack.peek();
					cmdAddShape.execute(); 
					redoStack.pop();
					undoStack.push(cmdAddShape);
					frame.getTextArea().append("Redo " + cmdAddShape.toString());
				} else {
					cmdAddShape = new CmdAddShape(model, shape);
					cmdAddShape.execute();
					undoStack.push(cmdAddShape);
					redoStack.clear(); 
					frame.getTextArea().append(cmdAddShape.toString());
				}
		
			} else if(line.contains("Selected")) {
				CmdSelectShape cmdSelectShape;
				
				if (line.contains("Undo")) {
					cmdSelectShape = (CmdSelectShape) undoStack.peek();
					cmdSelectShape.unexecute(); 
					undoStack.pop();
					redoStack.push(cmdSelectShape);
					frame.getTextArea().append("Undo " + cmdSelectShape.toString());
				} else if (line.contains("Redo")) {
					cmdSelectShape = (CmdSelectShape) redoStack.peek();
					cmdSelectShape.execute(); 
					redoStack.pop();
					undoStack.push(cmdSelectShape);
					frame.getTextArea().append("Undo " + cmdSelectShape.toString());
				} else {
					shape = model.getShapes().get(model.getShapes().indexOf(shape));
					cmdSelectShape = new CmdSelectShape(this, shape);
					cmdSelectShape.execute(); 
					undoStack.push(cmdSelectShape);
					redoStack.clear();
					frame.getTextArea().append(cmdSelectShape.toString());
				}
		
			} else if (line.contains("Deselected")) {
				CmdDeselectShape cmdDeselectShape;
				
				if(line.contains("Undo")) {
					cmdDeselectShape = (CmdDeselectShape) undoStack.peek();
					cmdDeselectShape.unexecute(); 
					undoStack.pop();
					redoStack.push(cmdDeselectShape);
					frame.getTextArea().append("Undo " + cmdDeselectShape.toString());
				} else if (line.contains("Redo")) {
					cmdDeselectShape = (CmdDeselectShape) redoStack.peek();
					cmdDeselectShape.execute(); 
					redoStack.pop();
					undoStack.push(cmdDeselectShape);
					frame.getTextArea().append("Redo " + cmdDeselectShape.toString());
				} else {
					shape = selectedShapeList.get(selectedShapeList.indexOf(shape));
					cmdDeselectShape = new CmdDeselectShape(this, shape);
					cmdDeselectShape.execute(); 
					undoStack.push(cmdDeselectShape);
					redoStack.clear(); 
					frame.getTextArea().append(cmdDeselectShape.toString());
				}
	
			} else if (line.contains("Deleted")) {
				CmdDeleteShape cmdDeleteShape;
				
				if(line.contains("Undo")) {
					cmdDeleteShape = (CmdDeleteShape) undoStack.peek();
					cmdDeleteShape.unexecute(); 
					redoShapesList.add(undoShapesList.get(undoShapesList.size() - 1));
					selectedShapeList.add(undoShapesList.get(undoShapesList.size() - 1));
					undoShapesList.remove(undoShapesList.size() - 1);
					undoStack.pop();
					redoStack.push(cmdDeleteShape);
					frame.getTextArea().append("Undo " + cmdDeleteShape.toString());
				} else if (line.contains("Redo")) {
					cmdDeleteShape = (CmdDeleteShape) redoStack.peek();
					cmdDeleteShape.execute(); 
					undoShapesList.add(redoShapesList.get(redoShapesList.size() - 1));
					selectedShapeList.remove(redoShapesList.get(redoShapesList.size() - 1));
					redoShapesList.remove(redoShapesList.size() - 1);
					redoStack.pop();
					undoStack.push(cmdDeleteShape);
					frame.getTextArea().append("Redo " + cmdDeleteShape.toString());
				} else {
					shape = selectedShapeList.get(0);
					cmdDeleteShape = new CmdDeleteShape(model, shape, model.getShapes().indexOf(shape));
					cmdDeleteShape.execute();
					selectedShapeList.remove(shape);
					undoShapesList.add(shape);
					undoStack.push(cmdDeleteShape);
					redoStack.clear();
					frame.getTextArea().append(cmdDeleteShape.toString());
				}
		
			} else if (line.contains("Moved to back")) {
				CmdToBack cmdToBack;
				
				if (line.contains("Undo")) {
					cmdToBack = (CmdToBack) undoStack.peek();
					cmdToBack.unexecute();
					undoStack.pop();
					redoStack.push(cmdToBack);
					frame.getTextArea().append("Undo " + cmdToBack.toString());
				} else if (line.contains("Redo")) {
					cmdToBack = (CmdToBack) redoStack.peek();
					cmdToBack.execute();
					redoStack.pop();
					undoStack.push(cmdToBack);
					frame.getTextArea().append("Redo " + cmdToBack.toString());
				} else {
					shape = selectedShapeList.get(0);
					cmdToBack = new CmdToBack(model, shape);
					cmdToBack.execute(); 
					undoStack.push(cmdToBack);
					redoStack.clear();
					frame.getTextArea().append(cmdToBack.toString());
				}
			} else if (line.contains("Moved to front")) {
				CmdToFront cmdToFront;
				
				if(line.contains("Undo")) {
					cmdToFront = (CmdToFront) undoStack.peek();
					cmdToFront.unexecute(); 
					undoStack.pop();
					redoStack.push(cmdToFront);
					frame.getTextArea().append("Undo " + cmdToFront.toString());
				} else if (line.contains("Redo")) {
					cmdToFront = (CmdToFront) redoStack.peek();
					cmdToFront.execute(); 
					redoStack.pop();
					undoStack.push(cmdToFront);
					frame.getTextArea().append("Redo " + cmdToFront.toString());
				} else {
					shape = selectedShapeList.get(0);
					cmdToFront = new CmdToFront(model, shape);
					cmdToFront.execute(); 
					undoStack.push(cmdToFront);
					redoStack.clear();
					frame.getTextArea().append(cmdToFront.toString());
				}
			} else if (line.contains("Bringed to back")) {
				CmdBringToBack cmdBringToBack;

				if (line.contains("Undo")) {
					cmdBringToBack = (CmdBringToBack) undoStack.peek();
					cmdBringToBack.unexecute();
					undoStack.pop();
					redoStack.push(cmdBringToBack);
					frame.getTextArea().append("Undo " + cmdBringToBack.toString());
				} else if (line.contains("Redo")) {
					cmdBringToBack = (CmdBringToBack) redoStack.peek();
					cmdBringToBack.execute();
					redoStack.pop();
					undoStack.push(cmdBringToBack);
					frame.getTextArea().append("Redo " + cmdBringToBack.toString());
				} else {
					shape = selectedShapeList.get(0);
					cmdBringToBack = new CmdBringToBack(model, shape);
					cmdBringToBack.execute();
					undoStack.push(cmdBringToBack);
					redoStack.clear();
					frame.getTextArea().append(cmdBringToBack.toString());
				}
			} else if (line.contains("Bringed to front")) {
				CmdBringToFront cmdBringToFront;

				if (line.contains("Undo")) {
					cmdBringToFront = (CmdBringToFront) undoStack.peek();
					cmdBringToFront.unexecute();
					undoStack.pop();
					redoStack.push(cmdBringToFront);
					frame.getTextArea().append("Undo " + cmdBringToFront.toString());
				} else if (line.contains("Redo")) {
					cmdBringToFront = (CmdBringToFront) redoStack.peek();
					cmdBringToFront.execute();
					redoStack.pop();
					undoStack.push(cmdBringToFront);
					frame.getTextArea().append("Redo " + cmdBringToFront.toString());
				} else {
					shape = selectedShapeList.get(0);
					cmdBringToFront = new CmdBringToFront(model, shape);
					cmdBringToFront.execute();
					undoStack.push(cmdBringToFront);
					redoStack.clear();
					frame.getTextArea().append(cmdBringToFront.toString());
				}
		
			} else if (line.contains("Modified")) {
				if (shape instanceof Point) {
					CmdModifyPoint cmdModifyPoint;
					
					if(line.contains("Undo")) {
						cmdModifyPoint = (CmdModifyPoint) undoStack.peek();
						cmdModifyPoint.unexecute();
						undoStack.pop();
						redoStack.push(cmdModifyPoint);
						frame.getTextArea().append("Undo " + cmdModifyPoint.toString());
					} else if (line.contains("Redo")) {
						cmdModifyPoint = (CmdModifyPoint) redoStack.peek();
						cmdModifyPoint.execute(); 
						redoStack.pop();
						undoStack.push(cmdModifyPoint);
						frame.getTextArea().append("Redo " + cmdModifyPoint.toString());
					} else {
						shape = selectedShapeList.get(0);
						Point newPoint = new Point();
						
						newPoint.setX(Integer.parseInt(line.substring(findLeftBracket(3, line) + 1, findComma(3, line))));
						newPoint.setY(Integer.parseInt(line.substring(findComma(3, line) + 2, findRightBracket(3, line))));
						newPoint.setColor(new Color(Integer.parseInt(line.substring(findLeftBracket(4, line) + 1, findRightBracket(4, line)))));

						cmdModifyPoint = new CmdModifyPoint((Point) shape, newPoint);
						cmdModifyPoint.execute();
						undoStack.push(cmdModifyPoint);
						redoStack.clear();
						frame.getTextArea().append(cmdModifyPoint.toString());
					}
				} else if (shape instanceof Line) {
					CmdModifyLine cmdModifyLine;
					
					if(line.contains("Undo")) {
						cmdModifyLine = (CmdModifyLine) undoStack.peek();
						cmdModifyLine.unexecute();
						undoStack.pop();
						redoStack.push(cmdModifyLine);
						frame.getTextArea().append("Undo " + cmdModifyLine.toString());
					} else if (line.contains("Redo")) {
						cmdModifyLine = (CmdModifyLine) redoStack.peek();
						cmdModifyLine.execute(); 
						redoStack.pop();
						undoStack.push(cmdModifyLine);
						frame.getTextArea().append("Redo " + cmdModifyLine.toString());
					} else {
						shape = selectedShapeList.get(0);
						Point newStartPoint = new Point();
						Point newEndPoint = new Point();
						
						newStartPoint.setX(Integer.parseInt(line.substring(findLeftBracket(4, line) + 1, findComma(4, line))));
						newStartPoint.setY(Integer.parseInt(line.substring(findComma(4, line) + 2, findRightBracket(4, line))));
						newEndPoint.setX(Integer.parseInt(line.substring(findLeftBracket(5, line) + 1, findComma(5, line))));
						newEndPoint.setY(Integer.parseInt(line.substring(findComma(5, line) + 2, findRightBracket(5, line))));
						
						Line newLine = new Line(newStartPoint, newEndPoint);
						newLine.setColor(new Color(Integer.parseInt(line.substring(findLeftBracket(6, line) + 1, findRightBracket(6, line)))));

						cmdModifyLine = new CmdModifyLine((Line) shape, newLine);
						cmdModifyLine.execute();
						undoStack.push(cmdModifyLine);
						redoStack.clear();
						frame.getTextArea().append(cmdModifyLine.toString());
					}
				} else if (shape instanceof HexagonAdapter) {
					CmdModifyHexagon cmdModifyHexagon;
					
					if(line.contains("Undo")) {
						cmdModifyHexagon = (CmdModifyHexagon) undoStack.peek();
						cmdModifyHexagon.unexecute(); 
						undoStack.pop();
						redoStack.push(cmdModifyHexagon);
						frame.getTextArea().append("Undo " + cmdModifyHexagon.toString());
					} else if (line.contains("Redo")) {
						cmdModifyHexagon = (CmdModifyHexagon) redoStack.peek();
						cmdModifyHexagon.execute(); 
						redoStack.pop();
						undoStack.push(cmdModifyHexagon);
						frame.getTextArea().append("Redo " + cmdModifyHexagon.toString());
					} else {
						shape = selectedShapeList.get(0);
						Point center = new Point();
						int radius, outLineColor, innerColor;
						
						center.setX(Integer.parseInt(line.substring(findLeftBracket(4, line) + 1, findComma(5, line))));
						center.setY(Integer.parseInt(line.substring(findComma(5, line) + 2, findRightBracket(4, line))));
						radius = (Integer.parseInt(line.substring(findEqualSign(2, line) + 1, findComma(7 ,line))));
						outLineColor = (Integer.parseInt(line.substring(findLeftBracket(5, line) + 1, findRightBracket(5, line))));
						innerColor = (Integer.parseInt(line.substring(findLeftBracket(6, line) + 1, findRightBracket(6, line))));
						
						
						HexagonAdapter newHexagon = new HexagonAdapter(center, radius, new Color(outLineColor), new Color(innerColor));
						
						cmdModifyHexagon = new CmdModifyHexagon((HexagonAdapter) shape, newHexagon);
						cmdModifyHexagon.execute();
						undoStack.push(cmdModifyHexagon);
						redoStack.clear();
						frame.getTextArea().append(cmdModifyHexagon.toString());
					}
				} else if (shape instanceof Rectangle) {
					CmdModifyRectangle cmdModifyRectangle;
					
					if (line.contains("Undo")) {
						cmdModifyRectangle = (CmdModifyRectangle) undoStack.peek();
						cmdModifyRectangle.unexecute();
						undoStack.pop();
						redoStack.push(cmdModifyRectangle);
						frame.getTextArea().append(cmdModifyRectangle.toString());
					} else if (line.contains("Redo")) {
						cmdModifyRectangle = (CmdModifyRectangle) redoStack.peek();
						cmdModifyRectangle.execute(); 
						redoStack.pop();
						undoStack.push(cmdModifyRectangle);
						frame.getTextArea().append("Redo " + cmdModifyRectangle.toString());
					} else {
						shape = selectedShapeList.get(0);
						Point upperLeftPoint = new Point();
						int width, height, outLineColor, innerColor;
						
						upperLeftPoint.setX(Integer.parseInt(line.substring(findLeftBracket(4, line) + 1, findComma(6, line))));
						upperLeftPoint.setY(Integer.parseInt(line.substring(findComma(6, line) + 2, findRightBracket(4, line))));
						
						width = Integer.parseInt(line.substring(findEqualSign(3, line) + 1, findComma(8, line)));
						height = Integer.parseInt(line.substring(findEqualSign(4, line) + 1, findComma(9, line)));
						
						innerColor = Integer.parseInt(line.substring(findLeftBracket(5, line) + 1, findRightBracket(5, line)));
						outLineColor = Integer.parseInt(line.substring(findLeftBracket(6, line) +1, findRightBracket(6, line)));						
					
						Rectangle newRectangle = new Rectangle(upperLeftPoint, width, height, new Color(innerColor), new Color(outLineColor));
						
						cmdModifyRectangle = new CmdModifyRectangle((Rectangle) shape, newRectangle);
						cmdModifyRectangle.execute(); 
						undoStack.push(cmdModifyRectangle);
						redoStack.clear();
						frame.getTextArea().append(cmdModifyRectangle.toString());
					}
				} else if (shape instanceof Donut) {
					CmdModifyDonut cmdModifyDonut;
					
					if(line.contains("Undo")) {
						cmdModifyDonut = (CmdModifyDonut) undoStack.peek();
						cmdModifyDonut.unexecute(); 
						undoStack.pop();
						redoStack.push(cmdModifyDonut);
						frame.getTextArea().append("Undo " + cmdModifyDonut.toString());
					} else if (line.contains("Redo")) {
						cmdModifyDonut = (CmdModifyDonut) redoStack.peek();
						cmdModifyDonut.execute(); 
						redoStack.pop();
						undoStack.push(cmdModifyDonut);
						frame.getTextArea().append("Redo " + cmdModifyDonut.toString());
					} else {
						shape = selectedShapeList.get(0);
						Point center = new Point();
						int radius, innerRadius, outLineColor, innerColor;
						
						center.setX(Integer.parseInt(line.substring(findLeftBracket(4, line) + 1, findComma(6, line))));
						center.setY(Integer.parseInt(line.substring(findComma(6, line) + 2, findRightBracket(4, line))));
						
						radius = Integer.parseInt(line.substring(findEqualSign(3, line) + 1, findComma(8, line)));
						innerRadius = Integer.parseInt(line.substring(findEqualSign(4, line) + 1, findComma(9, line)));
						
						outLineColor = Integer.parseInt(line.substring(findLeftBracket(5, line) + 1, findRightBracket(5, line)));
						innerColor = Integer.parseInt(line.substring(findLeftBracket(6, line) + 1, findRightBracket(6, line)));
						
						Donut newDonut = new Donut(center, radius, innerRadius, new Color(outLineColor), new Color(innerColor));
						
						cmdModifyDonut = new CmdModifyDonut((Donut) shape, newDonut);
						cmdModifyDonut.execute(); 
						undoStack.push(cmdModifyDonut);
						redoStack.clear();
						frame.getTextArea().append(cmdModifyDonut.toString());	
					}
				} else if (shape instanceof Circle) {
					CmdModifyCircle cmdModifyCircle;
					
					if(line.contains("Undo")) {
						cmdModifyCircle = (CmdModifyCircle) undoStack.peek();
						cmdModifyCircle.unexecute(); 
						undoStack.pop();
						redoStack.push(cmdModifyCircle);
						frame.getTextArea().append("Undo " + cmdModifyCircle.toString());
					} else if (line.contains("Redo")) {
						cmdModifyCircle = (CmdModifyCircle) redoStack.peek();
						cmdModifyCircle.execute(); 
						redoStack.pop();
						undoStack.push(cmdModifyCircle);
						frame.getTextArea().append("Redo " + cmdModifyCircle.toString());
					} else {
						shape = selectedShapeList.get(0);
						Point center = new Point();
						int radius, outLineColor, innerColor;
						
						center.setX(Integer.parseInt(line.substring(findLeftBracket(4, line) + 1, findComma(5, line))));
						center.setY(Integer.parseInt(line.substring(findComma(5, line) + 2, findRightBracket(4, line))));
						radius = Integer.parseInt(line.substring(findEqualSign(2, line) + 1, findComma(7, line)));
						outLineColor = Integer.parseInt(line.substring(findLeftBracket(5, line) + 1, findRightBracket(5, line)));
						innerColor = Integer.parseInt(line.substring(findLeftBracket(6, line) + 1, findRightBracket(6, line)));
						
						Circle newCircle = new Circle(center, radius, new Color(outLineColor), new Color(innerColor));
						
						cmdModifyCircle = new CmdModifyCircle((Circle) shape, newCircle);
						cmdModifyCircle.execute(); 
						undoStack.push(cmdModifyCircle);
						redoStack.clear();
						frame.getTextArea().append(cmdModifyCircle.toString());
					}
				}
			}
			logCounter++;
			frame.repaint();
		} else {
			frame.getBtnLoadNext().setEnabled(false);
			frame.getBtnUndo().setEnabled(false);
			frame.getTglbtnPoint().setEnabled(true);
			frame.getTglbtnLine().setEnabled(true);
			frame.getTglbtnCircle().setEnabled(true);
			frame.getTglbtnDonut().setEnabled(true);
			frame.getTglbtnRectangle().setEnabled(true);
			frame.getTglbtnHexagon().setEnabled(true);
			enableDisableButtons();
		}
	}
	
	public int findLeftBracket(int n, String line) {
        int occurr = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '(') {
                occurr += 1;
            }
            if (occurr == n) {
                return i;
            }
        }
        return -1;
    }
	
	public int findRightBracket(int n, String line) {
        int occurr = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == ')') {
                occurr += 1;
            }
            if (occurr == n) {
                return i;
            }
        }
        return -1;
    }
	
	public int findComma(int n, String line) {
        int occurr = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == ',') {
                occurr += 1;
            }
            if (occurr == n) {
                return i;
            }
        }
        return -1;
    }
	
	public int findEqualSign(int n, String line) {
        int occurr = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '=') {
                occurr += 1;
            }
            if (occurr == n) {
                return i;
            }
        }
        return -1;
    }
	
	
	public ArrayList<Shape> getSelectedShapeList() {
		return selectedShapeList;
	}

}

