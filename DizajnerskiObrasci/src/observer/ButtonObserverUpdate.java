package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;

public class ButtonObserverUpdate implements PropertyChangeListener {

	private DrawingFrame frame;
	
	public ButtonObserverUpdate(DrawingFrame frame) {
		
		this.frame=frame;
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		if(event.getPropertyName().equals("selectBtn")) {
			frame.getTglbtnSelect().setEnabled((boolean) event.getNewValue());
		}
		
		if(event.getPropertyName().equals("deleteBtn")) {
			frame.getBtnDelete().setEnabled((boolean) event.getNewValue());
		}
		
		if(event.getPropertyName().equals("modifyBtn")) {
			frame.getBtnModify().setEnabled((boolean) event.getNewValue());
		}
		
		if(event.getPropertyName().equals("btnBringToBack")) {
			frame.getBtnBringToBack().setEnabled((boolean) event.getNewValue());
		}
		
		if(event.getPropertyName().equals("btnBringToFront")) {
			frame.getBtnBringToFront().setEnabled((boolean) event.getNewValue());
		}
		if(event.getPropertyName().equals("btnToFront")) {
			frame.getBtnToFront().setEnabled((boolean) event.getNewValue());
		}
		if(event.getPropertyName().equals("btnToBack")) {
			frame.getBtnToBack().setEnabled((boolean) event.getNewValue());
		}
	
	}
	
	
	
}
