package grail.atomicShapes.classes;

import grail.atomicShapes.interfaces.PointInterface;

public class PolarPoint implements PointInterface {
	private double radius;
	private double angleRadians;

	public PolarPoint(double theRadius, double theAngleRadians) {
		radius = theRadius;
		angleRadians = theAngleRadians;
	}

	public PolarPoint(int theX, int theY) {
		radius = Math.sqrt(theX * theX + theY * theY);
		angleRadians = Math.atan2(theY, theX);
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
		int currentY = getY();
		radius = Math.sqrt(x * x + currentY * currentY);
		angleRadians = Math.atan2(currentY, x);
	}

	@Override
	public void setY(int y) {
		int currentX = getX();
		radius = Math.sqrt(currentX * currentX + y * y);
		angleRadians = Math.atan2(y, currentX);
	}

	@Override
	public void setRadius(double newRadius) {
		this.radius = newRadius;
	}

	@Override
	public void setAngle(double newAngleRadians) {
		this.angleRadians = newAngleRadians;
	}
}
