	package command;
	
	import geometry.Shape;
	import mvc.DrawingController;
	import mvc.DrawingModel;
	
	public class CmdSelectShape implements Command {
		
		private Shape shape;
		private DrawingController controller;
		
	public CmdSelectShape (DrawingController controller, Shape shape) {
			
			this.controller=controller;
			this.shape=shape;
		}
		
	
	@Override
	public void execute() {
		
		this.shape.setSelected(true);
		this.controller.getSelectedShapeList().add(this.shape);
		
		
	}
	
	@Override
	public void unexecute() {
		
		this.shape.setSelected(false);
		this.controller.getSelectedShapeList().remove(this.shape);
		
		
	}
	@Override
	public String toString() {
		return "Selected - " + shape +  "\n";
	}
	
	
	}
