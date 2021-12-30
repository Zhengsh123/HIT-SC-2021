/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<>();
    }
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   测试add，set，remove
    
    // TODO tests for ConcreteEdgesGraph.toString()
    /* Testing strategy
     * 测试空图、非空图
     */
    @Test
    public void testConcreteEdgesGraphtoString() {
        Graph<String> graph =emptyInstance();
        //空图
        assertEquals("",graph.toString());
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("a","c",3);
        //非空图
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"a->c weight:3\n",graph.toString());
    }
    /* Testing strategy
     * 按照加入的点划分：点已经存在，点不存在
     */
    @Test
    public void testConcreteEdgesGraphadd() {
        Graph<String> graph =emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        //点已经存在
        assertFalse(graph.add("a"));
        Set<String> test=new HashSet<>();
        test.add("a");
        test.add("b");
        test.add("c");
        //点不存在
        assertEquals(test,graph.vertices());
    }
    /* Testing strategy
     * 按照需要删除的点划分：点存在，点不存在
     */
    @Test
    public void testConcreteEdgesGraphremove() {
        Graph<String> graph =emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        Set<String> test=new HashSet<>();
        test.add("a");
        test.add("b");
        test.add("c");
        assertEquals(test,graph.vertices());
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("a","c",3);
        graph.remove("b");
        //点存在
        assertEquals("a->c weight:3\n",graph.toString());
        //点不存在
        assertFalse(graph.remove("d"));
        assertEquals("a->c weight:3\n",graph.toString());
    }
    /* Testing strategy
     * 按照需要操作的点划分：两点都存在，两点都不存在，两点有一个存在
     * 按照weight划分：weight>0,weight<0,weight=0
     * 按照边存在情况划分：存在，不存在
     */
    @Test
    public void testConcreteEdgesGraphset() {
        Graph<String> graph =emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("a","c",3);
        //两点都存在，weight>0
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"a->c weight:3\n",graph.toString());
        //两点都存在，weight<0
        assertEquals(-1,graph.set("a","b",-1));
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"a->c weight:3\n",graph.toString());
        //两点都存在，weight=0
        graph.set("a","c",0);
        assertEquals("a->b weight:1\n"+"b->c weight:2\n",graph.toString());
        //两点都不存在，weight>0
        graph.set("d","e",5);
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"d->e weight:5\n",graph.toString());
        //两点都不存在，weight=0
        assertEquals(0,graph.set("f","g",0));
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"d->e weight:5\n",graph.toString());
        //两点都不存在，weight<0
        assertEquals(-1,graph.set("f","g",-1));
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"d->e weight:5\n",graph.toString());
        //两点中有一点存在，weight>0
        assertEquals(0,graph.set("a","g",2));
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"d->e weight:5\n"+"a->g weight:2\n",graph.toString());
        //两点中有一点存在，weight=0
        assertEquals(0,graph.set("a","h",0));
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"d->e weight:5\n"+"a->g weight:2\n",graph.toString());
        //两点中有一点存在，weight<0
        assertEquals(-1,graph.set("a","h",-1));
        assertEquals("a->b weight:1\n"+"b->c weight:2\n"+"d->e weight:5\n"+"a->g weight:2\n",graph.toString());
        //两点都存在，更新边
        assertEquals(1,graph.set("a","b",5));
        assertEquals("b->c weight:2\n"+"d->e weight:5\n"+"a->g weight:2\n"+"a->b weight:5\n",graph.toString());
    }

    @Test
    public void testConcreteEdgesGraphtargets()
    {
        Graph<String> graph =emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a","b",1);
        graph.set("b","c",2);
        graph.set("a","c",3);
        Map<String,Integer> targetMap=new HashMap<>();
        Map<String,Integer> sourcesMap=new HashMap<>();
        targetMap.put("b",1);
        targetMap.put("c",3);
        sourcesMap.put("a",3);
        sourcesMap.put("b",2);
        assertEquals(targetMap,graph.targets("a"));
        assertEquals(sourcesMap,graph.sources("c"));
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   测试getSource,getTarget等功能
    
    // TODO tests for operations of Edge
    /*
     * 返回source
     */
    @Test
    public void testGetsource() {
        Edge<String> test=new Edge<>("a","b",5);
        assertEquals("a", test.getSource());
    }

    /* Testing strategy
     * 返回target
     */
    @Test
    public void testGettarget() {
        Edge<String> test=new Edge<>("a","b",5);
        assertEquals("b", test.getTarget());
    }
    /* Testing strategy
     * 返回weight
     */
    @Test
    public void testGetweight() {
        Edge<String> test=new Edge<>("a","b",5);
        assertEquals(5, test.getWeight());
    }
}
