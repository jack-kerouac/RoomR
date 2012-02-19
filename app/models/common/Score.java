package models.common;

import com.google.common.base.Preconditions;

public final class Score implements Comparable<Score> {

	public static final Score MAX = defined(1.0);
	public static final Score MIN = defined(0.0);
	
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
	
	public boolean isMax() {
		return this.equals(MAX);
	}

	public double getValue() {
		return value;
	}
	
	public boolean isDefined() {
		return defined;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (defined ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (defined != other.defined)
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
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
