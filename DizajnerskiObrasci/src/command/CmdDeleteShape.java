package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdDeleteShape implements Command {

	private Shape shape;
	private DrawingModel model;
	private int index;

	public CmdDeleteShape(DrawingModel model, Shape shape, int index) {

		this.model = model;
		this.shape = shape;
		this.index = index;
	}

	@Override
	public void execute() {

		model.remove(shape);
	}

	@Override
	public void unexecute() {

		// model.add(shape);
		model.getShapes().add(index, shape);
	}

	@Override
	public String toString() {
		return "Deleted - " + shape + "\n";
	}
}
