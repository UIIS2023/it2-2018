package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ButtonObserver {

	private boolean selectButtonEnable;
	private boolean deleteButtonEnable;
	private boolean modifyButtonEnable;

	private boolean bringToFrontButtonEnable;
	private boolean bringToBackButtonEnable;
	private boolean toBackButtonEnable;
	private boolean toFrontButtonEnable;

	private PropertyChangeSupport propertyChangeSupport;

	public ButtonObserver() {

		propertyChangeSupport = new PropertyChangeSupport(this);

	}

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);

	}

	public void removePropertyChangeListen(PropertyChangeListener propertyChangeListener) {

		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public void setSelectButtonEnabled(boolean selectButtonEnable) {

		propertyChangeSupport.firePropertyChange("selectBtn", this.selectButtonEnable, selectButtonEnable);
		this.selectButtonEnable = selectButtonEnable;

	}

	public void setDeleteButtonEnabled(boolean deleteButtonEnable) {

		propertyChangeSupport.firePropertyChange("deleteBtn", this.deleteButtonEnable, deleteButtonEnable);
		this.deleteButtonEnable = deleteButtonEnable;

	}

	public void setModifyButtonEnabled(boolean modifyButtonEnable) {

		propertyChangeSupport.firePropertyChange("modifyBtn", this.modifyButtonEnable, modifyButtonEnable);
		this.modifyButtonEnable = modifyButtonEnable;

	}

	public void setBringToBackButtonEnabled(boolean bringToBackButtonEnable) {

		propertyChangeSupport.firePropertyChange("btnBringToBack", this.bringToBackButtonEnable,
				bringToBackButtonEnable);
		this.bringToBackButtonEnable = bringToBackButtonEnable;

	}

	public void setBringToFrontButtonEnabled(boolean bringToFrontButtonEnable) {

		propertyChangeSupport.firePropertyChange("btnBringToFront", this.bringToFrontButtonEnable,
				bringToFrontButtonEnable);
		this.bringToFrontButtonEnable = bringToFrontButtonEnable;

	}

	public void setToBackButtonEnabled(boolean toBackButtonEnable) {

		propertyChangeSupport.firePropertyChange("btnToBack", this.toBackButtonEnable, toBackButtonEnable);
		this.toBackButtonEnable = toBackButtonEnable;

	}

	public void setToFrontButtonEnabled(boolean toFrontButtonEnable) {

		propertyChangeSupport.firePropertyChange("btnToFront", this.toFrontButtonEnable, toFrontButtonEnable);
		this.toFrontButtonEnable = toFrontButtonEnable;

	}

}
