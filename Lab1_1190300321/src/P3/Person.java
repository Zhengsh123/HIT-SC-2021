package P3;
import java.util.ArrayList;
import java.util.List;
public class Person {
	private String name;
	private List<Person>friends;
	public Person(String nameString) 
	{
		this.name = nameString;
		friends = new ArrayList<Person>();
	}
	public void addFriend(Person namePerson)
	{
		friends.add(namePerson);
	}
	public String getName()
	{
		return name;
	}
	public List<Person> getFriends()
	{
		return friends;
	}
}
