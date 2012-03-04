package models.user.resident;

import models.flatshare.Flatshare;

import com.google.code.twig.annotation.Embedded;
import com.google.code.twig.annotation.Independent;
import com.google.common.base.Objects;

@Embedded
public class ResidentProfile {

	@Independent
	public Flatshare currentFlatshare;
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("flatshare", currentFlatshare).toString();
	}
	
}
