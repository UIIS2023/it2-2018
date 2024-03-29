package mvc;

import java.util.ArrayList;

import geometry.Shape;

public class DrawingModel {

	
	private ArrayList<Shape> shapes= new ArrayList<Shape>();
	
	public DrawingModel() {
		
		
		
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	
	
	public void add(Shape s) {
		shapes.add(s);
	}
	
	public void remove(Shape s) {
		shapes.remove(s);
	}
	
	public Shape getShape(int index) {
		return shapes.get(index);
	}
	
}
