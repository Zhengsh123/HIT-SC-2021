/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        Graph<String>VerticesGraph = new ConcreteVerticesGraph<>();
        return VerticesGraph;
    }
    /*
     * Testing ConcreteVerticesGraph...
     */
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   划分等价类：空图，有点无边图，有边也有点的图
    
    // TODO tests for ConcreteVerticesGraph.toString()
    /* Testing strategy
     * 按照图的是否为空图划分：是、否
     */
    @Test
    public void testConcreteVerticesGraphtoString() {
        Graph<String> graph =emptyInstance();
        //图为空
        assertEquals("",graph.toString());
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a", "b", 1);
        graph.set("b", "c", 2);
        graph.set("c", "a", 3);
        //图非空
        assertEquals("点a，指向它的点和边的权值为：{c=3}，它指向的点和边的权值为：{b=1}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n"+"点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{a=3}\n",graph.toString());
    }
    /* Testing strategy
     * 按照source是否存在划分：是、否
     */
    @Test
    public void testConcreteVerticesGraphsources()
    {
        Graph<String> graph =emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        //source不存在
        Map<String,Integer>testMap1=new HashMap<>();
        Map<String,Integer>testMap2=new HashMap<>();
        assertEquals(testMap1,graph.sources("b"));
        assertEquals(testMap2,graph.targets("c"));
        graph.set("a", "b", 1);
        graph.set("b", "c", 2);
        graph.set("c", "a", 3);
        testMap1.put("a",1);
        testMap2.put("a",3);
        //source存在
        assertEquals(testMap1,graph.sources("b"));
        assertEquals(testMap2,graph.targets("c"));
    }
    /*Testing strategy
     * 按照删除的点划分：删除的点存在，删除点不存在
     */
    @Test
    public void testConcreteVerticesGraphremove()
    {
        Graph<String> graph =emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a", "b", 1);
        graph.set("b", "c", 2);
        graph.set("c", "a", 3);
        graph.remove("b");
        Map<String,Integer>testMap1=new HashMap<>();
        Map<String,Integer>testMap2=new HashMap<>();
        assertEquals(testMap1,graph.targets("a"));
        graph.remove("a");
        //删除的点存在
        assertEquals(testMap2,graph.targets("c"));
        //删除的点不存在
        assertEquals(false,graph.remove("e"));
    }
    /* Testing strategy
     * 按照需要操作的点划分：两点都存在，两点都不存在，两点有一个存在
     * 按照weight划分：weight>0,weight<0,weight=0
     * 按照边存在情况划分：存在，不存在
     */
    @Test
    public void testConcreteVerticesGraphset() {
        Graph<String> graph =emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("a","c",3);
        //两点都存在，weight>0
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1, c=3}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{a=3, b=2}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点都存在，weight<0
        assertEquals(-1,graph.set("a","b",-1));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1, c=3}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{a=3, b=2}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点都存在，weight=0
        graph.set("a","c",0);
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点都不存在，weight>0
        graph.set("d","e",5);
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n"+"点d，指向它的点和边的权值为：{}，它指向的点和边的权值为：{e=5}\n"+
                "点e，指向它的点和边的权值为：{d=5}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点都不存在，weight=0
        assertEquals(0,graph.set("f","g",0));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n"+"点d，指向它的点和边的权值为：{}，它指向的点和边的权值为：{e=5}\n"+
                "点e，指向它的点和边的权值为：{d=5}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点都不存在，weight<0
        assertEquals(-1,graph.set("f","g",-1));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n"+"点d，指向它的点和边的权值为：{}，它指向的点和边的权值为：{e=5}\n"+
                "点e，指向它的点和边的权值为：{d=5}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点中有一点存在，weight>0
        assertEquals(0,graph.set("a","g",2));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1, g=2}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n"+"点d，指向它的点和边的权值为：{}，它指向的点和边的权值为：{e=5}\n"+
                "点e，指向它的点和边的权值为：{d=5}，它指向的点和边的权值为：{}\n"+"点g，指向它的点和边的权值为：{a=2}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点中有一点存在，weight=0
        assertEquals(0,graph.set("a","h",0));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1, g=2}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n"+"点d，指向它的点和边的权值为：{}，它指向的点和边的权值为：{e=5}\n"+
                "点e，指向它的点和边的权值为：{d=5}，它指向的点和边的权值为：{}\n"+"点g，指向它的点和边的权值为：{a=2}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点中有一点存在，weight<0
        assertEquals(-1,graph.set("a","h",-1));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1, g=2}\n"+"点b，指向它的点和边的权值为：{a=1}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n"+"点d，指向它的点和边的权值为：{}，它指向的点和边的权值为：{e=5}\n"+
                "点e，指向它的点和边的权值为：{d=5}，它指向的点和边的权值为：{}\n"+"点g，指向它的点和边的权值为：{a=2}，它指向的点和边的权值为：{}\n",graph.toString());
        //两点都存在，更新边
        assertEquals(1,graph.set("a","b",5));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=5, g=2}\n"+"点b，指向它的点和边的权值为：{a=5}，它指向的点和边的权值为：{c=2}\n" +
                "点c，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n"+"点d，指向它的点和边的权值为：{}，它指向的点和边的权值为：{e=5}\n"+
                "点e，指向它的点和边的权值为：{d=5}，它指向的点和边的权值为：{}\n"+"点g，指向它的点和边的权值为：{a=2}，它指向的点和边的权值为：{}\n",graph.toString());
    }
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   测试getName，getSources，getTargets等方法的返回值
    /* Testing strategy
     * 观察是否能正常返回节点名字
     */
    @Test
    public void testgetName()
    {
        Vertex<String> testVertex = new Vertex<>("a");
        assertEquals("a", testVertex.getName());
    }
    /* Testing strategy
     * 按照sources分类：为空，非空
     */
    @Test
    public void testgetSources()
    {
        Vertex<String> testVertex = new Vertex<>("a");
        //sources为空
        Map<String,Integer>testMap=new HashMap<>();
        assertEquals(testMap,testVertex.getSources());
        testVertex.setSource("b",1);
        testVertex.setSource("c",2);
        testMap.put("b",1);
        testMap.put("c",2);
        //sources非空
        assertEquals(testMap,testVertex.getSources());
    }
    /* Testing strategy
     * 按照targets分类：为空，非空
     */
    @Test
    public void testgetTargets()
    {
        Vertex<String> testVertex = new Vertex<>("a");
        Map<String,Integer>testMap=new HashMap<>();
        assertEquals(testMap,testVertex.getTargets());
        //targets为空
        testVertex.setTarget("b",1);
        testVertex.setTarget("c",2);
        testMap.put("b",1);
        testMap.put("c",2);
        //targets非空
        assertEquals(testMap,testVertex.getTargets());
    }
    // TODO tests for operations of Vertex

    /* Testing strategy
     * 按照加入的点划分：点已经存在，点不存在
     * 按照加入的权值划分：权值为0，权值大于0，权值小于0
     */
    @Test
    public void testaddTarget() {
        Vertex<String> testVertex=new Vertex<>("a");
        //点不存在，权值大于0
        assertEquals(0,testVertex.setTarget("b",1));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1}\n",testVertex.toString());
        //点不存在，权值等于0
        assertEquals(0,testVertex.setTarget("c",0));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1}\n",testVertex.toString());
        //点不存在，权值小于0
        assertEquals(-1,testVertex.setTarget("c",-1));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=1}\n",testVertex.toString());
        //点存在，权值大于0
        assertEquals(1,testVertex.setTarget("b",2));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=2}\n",testVertex.toString());
        //点存在，权值小于0
        assertEquals(-1,testVertex.setTarget("b",-1));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{b=2}\n",testVertex.toString());
        //点存在，权值等于0
        assertEquals(2,testVertex.setTarget("b",0));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{}\n",testVertex.toString());
    }
    /* Testing strategy
     * 按照加入的点划分：点已经存在，点不存在
     * 按照加入的权值划分：权值为0，权值大于0，权值小于0
     */
    @Test
    public void testaddSource() {
        Vertex<String> testVertex=new Vertex<>("a");
        //点不存在，权值大于0
        assertEquals(0,testVertex.setSource("b",1));
        assertEquals("点a，指向它的点和边的权值为：{b=1}，它指向的点和边的权值为：{}\n",testVertex.toString());
        //点不存在，权值等于0
        assertEquals(0,testVertex.setSource("c",0));
        assertEquals("点a，指向它的点和边的权值为：{b=1}，它指向的点和边的权值为：{}\n",testVertex.toString());
        //点不存在，权值小于0
        assertEquals(-1,testVertex.setSource("c",-1));
        assertEquals("点a，指向它的点和边的权值为：{b=1}，它指向的点和边的权值为：{}\n",testVertex.toString());
        //点存在，权值大于0
        assertEquals(1,testVertex.setSource("b",2));
        assertEquals("点a，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n",testVertex.toString());
        //点存在，权值小于0
        assertEquals(-1,testVertex.setSource("b",-1));
        assertEquals("点a，指向它的点和边的权值为：{b=2}，它指向的点和边的权值为：{}\n",testVertex.toString());
        //点存在，权值等于0
        assertEquals(2,testVertex.setSource("b",0));
        assertEquals("点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{}\n",testVertex.toString());
    }
    /* Testing strategy
     * 按照图是否空分类：空、非空
     * 覆盖每个取值如下：
     */
    @Test
    public void testtoString()
    {
        Vertex<String> testVertex = new Vertex<>("a");
        //空
        assertEquals(testVertex.toString(),"点a，指向它的点和边的权值为：{}，它指向的点和边的权值为：{}\n");
        testVertex.setSource("b",1);
        testVertex.setTarget("c",2);
        //非空
        assertEquals(testVertex.toString(),"点a，指向它的点和边的权值为：{b=1}，它指向的点和边的权值为：{c=2}\n");
    }
}
