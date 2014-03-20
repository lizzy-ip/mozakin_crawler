package jp.co.lizzy.common;

public class ElapsedTime {

	private final long startTime;
	private long stopTime;

	public ElapsedTime() {
		startTime = System.currentTimeMillis();
	}

	public double get() {
		stopTime = System.currentTimeMillis();
		return (stopTime - startTime) / 1000d;
	}

	public double getPrevious() {
		return (stopTime - startTime) / 1000d;
	}

}
