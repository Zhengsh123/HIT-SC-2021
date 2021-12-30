package P2;
import P1.graph.ConcreteVerticesGraph;
import java.util.*;

public class FriendshipGraph {
    private final ConcreteVerticesGraph<Person>personGraph;
    // Abstraction function:
    //   AF(personGraph)=关系图
    // Representation invariant:
    //   没有重复的Person
    // Safety from rep exposure:
    //   将personGraph设置为private final，同时注意防御式拷贝
    private void checkRep()
    {
        assert personGraph!=null;
    }

    public FriendshipGraph()
    {
        personGraph=new ConcreteVerticesGraph<>();
    }
    /**
     *在personGraph中添加需要加入的人
     * @param a 需要加入的人
     */
    public void addVertex(Person a)
    {
        personGraph.add(a);
        checkRep();
    }
    /**
     * 给两个人建立关系
     * @param a 需要添加关系的两个人之一
     * @param b 需要添加关系的两个人之一
     */
    public void addEdge(Person a,Person b)
    {
        personGraph.set(a,b,1);
        checkRep();
    }
    /**
     * 求两个人之间的最短距离,如果不存在边的话返回-1
     * @param a 需要求最短距离的人之一
     * @param b 需要求最短距离的人之一
     * @return 返回二者之间的最短距离
     */
    public int getDistance(Person a,Person b)
    {
        if(a.equals(b))return 0;
        Queue<Person>personQueue=new LinkedList<>();
        Map<Person,Integer>distanceMap=new HashMap<>();
        personQueue.add(a);
        distanceMap.put(a,0);
        while(!personQueue.isEmpty())
        {
            Person topPerson=personQueue.poll();
            Integer distance=distanceMap.get(topPerson);
            Set<Person>personSet=personGraph.targets(topPerson).keySet();
            for(Person p:personSet)
            {
                if(!distanceMap.containsKey(p))
                {
                    distanceMap.put(p,distance+1);
                    personQueue.add(p);
                    if(p.equals(b))
                        return distanceMap.get(p);
                }
            }
        }
        checkRep();
        return -1;
    }
    /**
     * 返回图中所有的人
     * @return
     */
    public ConcreteVerticesGraph<Person>getPeople()
    {
        return personGraph;
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
