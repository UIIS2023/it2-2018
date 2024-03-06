package command;


import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdToBack implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private int shapeIndex;
	private int lastIndex;
	
	
	public CmdToBack(DrawingModel model, Shape shape) {
		
		this.model=model;
		this.shape=shape;
		this.shapeIndex=model.getShapes().indexOf(this.shape);
		this.lastIndex=model.getShapes().size()-1;
	}
	
	@Override
	public void execute() {
		
		if(shapeIndex>0) {
			Collections.swap(model.getShapes(), shapeIndex, shapeIndex-1);
			this.shapeIndex-=1;
		}
		
		
	}
	
	@Override
	public void unexecute() {
		
		if(shapeIndex< lastIndex) {
			Collections.swap(model.getShapes(), shapeIndex, shapeIndex+1);
			this.shapeIndex+=1;
		}
		
	}


	
	@Override
	public String toString() {
		return "Moved to back - " + this.shape + "\n"; 
	}
	
	
}