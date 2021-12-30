package P2;

import static org.junit.Assert.*;
import org.junit.Test;

public class FriendshipGraphTest {
	/**
	 * Tests addVertex
	 */
	@Test
	public void addVertexTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		Person john = new Person("John");
		Person michael = new Person("Michael");
		Person david = new Person("David");

		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addVertex(john);
		graph.addVertex(michael);
		graph.addVertex(david);
		assertTrue(graph.getPeople().vertices().contains(rachel));
		assertTrue(graph.getPeople().vertices().contains(ross));
		assertTrue(graph.getPeople().vertices().contains(ben));
		assertTrue(graph.getPeople().vertices().contains(john));
		assertTrue(graph.getPeople().vertices().contains(michael));
		assertTrue(graph.getPeople().vertices().contains(david));
	}
	/**
	 * Tests addEdge
	 */
	@Test
	public void addEdgeTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		Person john = new Person("John");
		Person michael = new Person("Michael");
		Person david = new Person("David");

		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addVertex(john);
		graph.addVertex(michael);
		graph.addVertex(david);

		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		graph.addEdge(rachel, john);
		graph.addEdge(john, rachel);
		graph.addEdge(john, michael);
		graph.addEdge(michael, john);
		graph.addEdge(michael, david);
		graph.addEdge(david, michael);
		graph.addEdge(michael, ross);
		graph.addEdge(ross, michael);
		assertTrue(graph.getPeople().targets(rachel).containsKey(ross));
	}
	/**
	 * Tests getDistance.
	 */
	@Test
	public void getDistanceTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		Person john = new Person("John");
		Person michael=new Person("Michael");
		Person david=new Person("David");

		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addVertex(john);
		graph.addVertex(david);

		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		graph.addEdge(rachel, john);
		graph.addEdge(john, rachel);
		graph.addEdge(john, michael);
		graph.addEdge(michael, john);
		graph.addEdge(michael, david);
		graph.addEdge(david, michael);
		graph.addEdge(michael, ross);
		graph.addEdge(ross, michael);

		assertEquals(1,graph.getDistance(rachel, ross));
		assertEquals(2,graph.getDistance(rachel, ben));
		assertEquals(0,graph.getDistance(rachel, rachel));
		assertEquals(-1,graph.getDistance(rachel, kramer));
		assertEquals(1,graph.getDistance(rachel, john));
		assertEquals(2,graph.getDistance(rachel, michael));
		assertEquals(3,graph.getDistance(rachel, david));
		assertEquals(2,graph.getDistance(ross, david));
	}
}