package models.common;

import com.google.common.base.Preconditions;

public final class Score implements Comparable<Score> {

	private final double value;
	private final boolean defined;

	private Score(double value) {
		Preconditions.checkArgument(0.0 <= value && value <= 1.0);
		this.value = value;
		this.defined = true;
	}
	
	private Score() {
		this.value = -1.0;
		this.defined = false;
	}
	
	public static Score defined(double value) {
		return new Score(value);
	}
	
	public static Score undefined() {
		return new Score();
	}
	

	public double getValue() {
		return value;
	}
	
	public boolean isDefined() {
		return defined;
	}

	@Override
	public String toString() {
		return defined ? Math.round(value * 100.0) + "%" : "???";
	}
	
	@Override
	public int compareTo(Score score) {
		return Double.compare(this.value, score.value);
	}

}
