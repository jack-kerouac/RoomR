package models.user.seeker;

import java.util.Set;

import models.application.FlatshareApplication;

import com.google.common.base.Objects;

public class SeekerProfile {

	public Set<FlatshareApplication> applications;
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("applications", applications).toString();
	}
}
