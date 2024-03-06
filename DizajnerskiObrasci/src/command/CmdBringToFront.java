package command;


import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdBringToFront implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private int shapeIndex;
	private int lastIndex;
	
	public CmdBringToFront(DrawingModel model,Shape shape) {
		
		this.model= model;
		this.shape=shape;
		shapeIndex=model.getShapes().indexOf(shape);
		lastIndex=model.getShapes().size()-1;
	}
	
	@Override
	public void execute() {
		
		for(int i=shapeIndex; i<lastIndex;i++) {
			Collections.swap(model.getShapes(), i, i+1);
		}
		
	}

	@Override
	public void unexecute() {
		
		for(int i=lastIndex; i>shapeIndex;i--) {
			Collections.swap(model.getShapes(), i, i-1);
		}
		
	}
	
	@Override
	public String toString() {
		return "Bringed to front - " + this.shape + "\n"; 
	}
	
	
}