package command;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdDeselectShape implements Command {

	private Shape shape;
	private DrawingController controller;

	public CmdDeselectShape(DrawingController controller, Shape shape) {

		this.controller = controller;
		this.shape = shape;
	}

	@Override
	public void execute() {

		this.shape.setSelected(false);
		this.controller.getSelectedShapeList().remove(this.shape);

	}

	@Override
	public void unexecute() {

		this.shape.setSelected(true);
		this.controller.getSelectedShapeList().add(this.shape);

	}

	@Override
	public String toString() {
		return "Deselected - " + shape + "\n";
	}

}