package de.iws.mucflatshares.domain.internal.repos.impl.appengine;

import javax.annotation.PostConstruct;
import javax.persistence.Id;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import de.iws.mucflatshares.domain.common.Age;
import de.iws.mucflatshares.domain.common.Gender;
import de.iws.mucflatshares.domain.common.PersonalProfile;
import de.iws.mucflatshares.domain.seeking.RoomSeeker;
import de.iws.mucflatshares.domain.seeking.RoomSeekerRepository;

@Repository
public class ObjectifyRoomSeekerRepository implements RoomSeekerRepository {

	private static class MutableRoomSeeker {

		@Id
		private String id;
		private int age;
		private Gender gender;

		@SuppressWarnings("unused")
		private MutableRoomSeeker() {}

		MutableRoomSeeker(RoomSeeker toCopy) {
			id = toCopy.getId();
			age = toCopy.getProfile().getAge().getValue();
			gender = toCopy.getProfile().getGender();
		}

		RoomSeeker toRoomSeeker() {
			return new RoomSeeker(id, new PersonalProfile(gender, new Age(age)));
		}

	}

	@PostConstruct
	public void init() {
		ObjectifyService.register(MutableRoomSeeker.class);
	}

	@Override
	public RoomSeeker findForId(String emailAddress) {
		Objectify ofy = ObjectifyService.begin();

		return ofy.get(MutableRoomSeeker.class, emailAddress).toRoomSeeker();
	}

	@Override
	public void add(RoomSeeker seeker) {
		Objectify ofy = ObjectifyService.begin();

		ofy.put(new MutableRoomSeeker(seeker));
	}

}
