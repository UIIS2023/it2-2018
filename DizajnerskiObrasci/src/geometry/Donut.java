package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) { 
		this(center, radius, innerRadius, selected);
		setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) { 
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, Color color, Color innerColor) {
		this(center,radius,innerRadius);
		setColor(color);
		setInnerColor(innerColor);
	}
	
	public void draw(Graphics g) {
		/*super.draw(g);
		g.setColor(getColor());
		g.drawOval(getCenter().getX() - this.innerRadius, getCenter().getY() - this.innerRadius, this.innerRadius * 2, this.innerRadius * 2);*/
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		java.awt.Shape outer = new Ellipse2D.Double(this.getCenter().getX() - this.getRadius(),
				this.getCenter().getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2);

		java.awt.Shape inner = new Ellipse2D.Double(this.getCenter().getX() - this.getInnerRadius(),
				this.getCenter().getY() - this.getInnerRadius(), this.getInnerRadius() * 2, this.getInnerRadius() * 2);

		Area circle = new Area(outer);
		circle.subtract(new Area(inner));

		g2d.setColor(this.getInnerColor());
		g2d.fill(circle);

		g2d.setColor(this.getColor());
		g2d.draw(circle);

		g2d.dispose();

		if (isSelected()) {
			super.selectCircle(g);
		}
	}
	
	
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - getInnerRadius(), getCenter().getY() - getInnerRadius(), getInnerRadius() * 2, getInnerRadius() * 2);
	}
	
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return dFromCenter > innerRadius && super.contains(x, y);
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return dFromCenter > innerRadius && super.contains(p.getX(), p.getY());
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) &&
					this.getRadius() == d.getRadius() &&
					this.innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int getInnerRadius() {
		return this.innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	
	
	public Donut clone(Donut donut) {
		donut.getCenter().setX(this.getCenter().getX());
		donut.getCenter().setY(this.getCenter().getY());
		try {
			donut.setRadius(this.getRadius());
			donut.setInnerRadius(getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		donut.setColor(this.getColor());
		donut.setInnerColor(this.getInnerColor());
		
		return donut;
	}
	
	@Override
	public String toString() {
		return "Donut: (" + getCenter().getX() + ", " + getCenter().getY() + "), " + "Radius=" + radius + ", Inner Radius=" +  this.innerRadius + ","
				+ " Outer Color: (" + Integer.toString(super.getColor().getRGB()) + ")" 
				+ ", Inner Color: ("+ Integer.toString(super.getInnerColor().getRGB()) + ")";
	}
	
}
