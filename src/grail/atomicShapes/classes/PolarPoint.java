package grail.atomicShapes.classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import grail.atomicShapes.interfaces.PointInterface;
import util.annotations.Visible;

public class PolarPoint implements PointInterface {
	private double radius;
	private double angleRadians;
	private List<PropertyChangeListener> propertyChangeListeners;

	public PolarPoint(double theRadius, double theAngleRadians) {
		radius = theRadius;
		angleRadians = theAngleRadians;
		propertyChangeListeners = new ArrayList<PropertyChangeListener>();
	}

	public PolarPoint(int theX, int theY) {
		radius = Math.sqrt(theX * theX + theY * theY);
		angleRadians = Math.atan2(theY, theX);
		propertyChangeListeners = new ArrayList<PropertyChangeListener>();
	}

	@Override
	public int getX() {
		return (int) Math.round(radius * Math.cos(angleRadians));
	}

	@Override
	public int getY() {
		return (int) Math.round(radius * Math.sin(angleRadians));
	}

	@Override
	public double getAngle() {
		return angleRadians;
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public void setX(int x) {
		int oldX = getX();
		int currentY = getY();
		radius = Math.sqrt(x * x + currentY * currentY);
		angleRadians = Math.atan2(currentY, x);
		notifyAllListeners(new PropertyChangeEvent(this, "X", oldX, x));
	}

	@Override
	public void setY(int y) {
		int oldY = getY();
		int currentX = getX();
		radius = Math.sqrt(currentX * currentX + y * y);
		angleRadians = Math.atan2(y, currentX);
		notifyAllListeners(new PropertyChangeEvent(this, "Y", oldY, y));
	}

	@Override
	public void setRadius(double newRadius) {
		double oldRadius = this.radius;
		this.radius = newRadius;
		notifyAllListeners(new PropertyChangeEvent(this, "Radius", oldRadius, newRadius));
	}

	@Override
	public void setAngle(double newAngleRadians) {
		double oldAngle = this.angleRadians;
		this.angleRadians = newAngleRadians;
		notifyAllListeners(new PropertyChangeEvent(this, "Angle", oldAngle, newAngleRadians));
	}

	@Override
	@Visible(false)
	public List<PropertyChangeListener> getPropertyChangeListeners() {
		return this.propertyChangeListeners;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeListeners.add(listener);
	}

	private void notifyAllListeners(PropertyChangeEvent event) {
		for (PropertyChangeListener listener : this.propertyChangeListeners) {
			listener.propertyChange(event);
		}
	}
}
