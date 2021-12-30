package P2;
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
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Person)
		{
			return this.name.equals(((Person) obj).name)&&this.friends.equals(((Person) obj).friends);
		}
		return false;
	}
}
