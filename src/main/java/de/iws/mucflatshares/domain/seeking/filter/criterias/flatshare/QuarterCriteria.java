package de.iws.mucflatshares.domain.seeking.filter.criterias.flatshare;

import java.util.Collection;

import com.google.common.base.Predicate;

import de.iws.mucflatshares.domain.offering.flatshare.Quarter;

public abstract class QuarterCriteria implements Predicate<Quarter> {

	public final static QuarterCriteria all() {
		return new All();
	}

	private final static class All extends QuarterCriteria {

		@Override
		public boolean apply(Quarter input) {
			return true;
		}

	}

	public final static QuarterCriteria in(Collection<Quarter> selection) {
		return new In(selection);
	}

	private final static class In extends QuarterCriteria {

		private final Collection<Quarter> selection;

		public In(Collection<Quarter> selection) {
			this.selection = selection;
		}

		@Override
		public boolean apply(Quarter input) {
			return selection.contains(input);
		}

	}

}
