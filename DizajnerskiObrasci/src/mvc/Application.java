package mvc;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		

		DrawingModel model=new DrawingModel();
		DrawingFrame frame= new DrawingFrame();
		frame.getView().setModel(model);
		DrawingController controller= new DrawingController(model,frame);
		frame.setController(controller);
		
		frame.setTitle("IT2-2018 Stefan Bulaja");
		frame.setSize(1000,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

}
