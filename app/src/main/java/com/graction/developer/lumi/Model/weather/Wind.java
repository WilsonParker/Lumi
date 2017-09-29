package com.graction.developer.lumi.Model.weather;

public class Wind {
	private double speed;	// Wind speed, Unit Default: meter/sec, Metricr: meer/sec, Imperial: miles/hour
	private int deg;		// Wind direction, degrees (meteorological)

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDeg() {
		return deg;
	}

	public void setDeg(int deg) {
		this.deg = deg;
	}

	@Override
	public String toString() {
		return "Wind [speed=" + speed + ", deg=" + deg + "]";
	}

}
