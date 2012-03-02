package models.user;


public interface RoomrUserRepository {

	public RoomrUser findUser(String gaeEmailAddress);

	public void add(RoomrUser newUser);
	
}
