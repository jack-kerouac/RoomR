package models.common.noDomain;

import com.google.common.base.Preconditions;

/**
 * Start and end are both included!
 * 
 * @author "Florian Rampp (Florian.Rampp@web.de)"
 * 
 */
public class InclusiveRange<T extends Comparable<T>> {

	private final T start;
	private final T end;

	public InclusiveRange(T start, T end) {
		Preconditions.checkArgument(start.compareTo(end) <= 0, "start of range " + start
				+ " must be less or equal than end " + end);

		this.start = start;
		this.end = end;
	}

	public boolean contains(T number) {
		return start.compareTo(number) <= 0 && number.compareTo(end) <= 0;
	}

	public boolean contains(InclusiveRange<T> range) {
		return contains(range.start) && contains(range.end);
	}

	public T getStart() {
		return start;
	}

	public T getEnd() {
		return end;
	}
}
