package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;
public class FriendshipGraph {
	private List<Person>people;
	private Set<String>nameSet;
	
	public List<Person> getPeople()
	{
		List<Person>clonePeople=new ArrayList<Person>();
		clonePeople=people;
		return clonePeople;	
	}
	public FriendshipGraph() {
		people = new ArrayList<Person>();
		nameSet=new TreeSet<String>();
	}
	
	public void addVertex(Person newPerson){
		if (nameSet.contains(newPerson.getName())) {
			System.out.println("输入有误：名字重复");
			System.exit(1);
		} else {
			nameSet.add(newPerson.getName());	
		}		
		people.add(newPerson);
	}
	public void addEdge(Person p1,Person p2)
	{
		p1.addFriend(p2);
	}
	public int getDistance(Person p1,Person p2)
	{
		if(p1==p2)return 0;
		Queue<Person> personQueue=new LinkedList<Person>();
		Map<Person,Integer>distanceMap=new HashMap<>();
		distanceMap.put(p1, 0);
		personQueue.add(p1);
		while(!personQueue.isEmpty())
		{
			Person topPerson=personQueue.poll();
			int tempDis=distanceMap.get(topPerson);
			List<Person> friends=topPerson.getFriends();
			for(Person tempP:friends)
			{
				if(!distanceMap.containsKey(tempP))
				{
					distanceMap.put(tempP, tempDis+1);
					personQueue.add(tempP);
					if(tempP==p2)return distanceMap.get(tempP);
				}
			}
		}
		return -1;
	}
	public static void main(String args[]) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1
	}
}
