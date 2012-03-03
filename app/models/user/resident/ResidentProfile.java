package models.user.resident;

import models.flatshare.Flatshare;

import com.google.common.base.Objects;

public class ResidentProfile {

	public Flatshare currentFlatshare;
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("flatshare", currentFlatshare).toString();
	}
	
}
