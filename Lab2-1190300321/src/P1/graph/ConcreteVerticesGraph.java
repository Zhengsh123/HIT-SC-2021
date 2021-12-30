/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices)=图中的所有节点
    // Representation invariant:
    //   不存在重复节点
    // Safety from rep exposure:
    //   vertices设置为private final ,必要的时候防御式拷贝

    // TODO constructor
    public ConcreteVerticesGraph(){
    }
    // TODO checkRep
    /*
     * 检查不变性，不存在重复节点
     */
    private void checkRep()
    {
        long count = vertices.stream().distinct().count();
        assert count==vertices.size();
    }
    @Override public boolean add(L vertex) {
       for(Vertex<L>V:vertices)
       {
           if(V.getName().equals(vertex))
           {
               System.out.println("加入重复节点，错误");
               return false;
           }
       }
       Vertex<L> V=new Vertex<>(vertex);
       vertices.add(V);
       checkRep();
       return true;
    }
    @Override public int set(L source, L target, int weight) {
        int preWeight=0;
        if(weight<0)
        {
            System.out.println("weight小于0，错误");
            return -1;
        }
        else if(weight>0)
        {
           add(source);
           add(target);
        }
        for(Iterator<Vertex<L>>it=vertices.iterator();it.hasNext();)
        {
            Vertex<L>V=it.next();
            if(V.getName().equals(source))
            {
                preWeight=V.setTarget(target,weight);
            }
            if(V.getName().equals(target))
            {
                preWeight=V.setSource(source,weight);
            }
        }
        checkRep();
        return preWeight;
    }
    @Override public boolean remove(L vertex) {
        boolean flag=false;
        for(Iterator<Vertex<L>>it=vertices.iterator();it.hasNext();)
        {
            Vertex<L> V=it.next();
            if(V.getName().equals(vertex))
            {
                flag=true;
                it.remove();
            }
            else
            {
                if(V.getSources().containsKey(vertex))
                {
                    V.setSource(vertex,0);
                }
                if(V.getTargets().containsKey(vertex))
                {
                    V.setTarget(vertex,0);
                }
            }
        }
        checkRep();
        return flag;
    }
    @Override public Set<L> vertices() {
        Set<L>verticesMap=new HashSet<>();
        for(Vertex<L>v:vertices)
        {
            verticesMap.add(v.getName());
        }
        checkRep();
        return verticesMap;
    }
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer>sourcesMap=new HashMap<>();
        for(Vertex<L>V:vertices)
        {
            if(V.getName().equals(target))
            {
                sourcesMap=V.getSources();
            }
        }
        checkRep();
        return sourcesMap;
    }
    @Override public Map<L, Integer> targets(L source) {
        Map<L,Integer>targetsMap=new HashMap<>();
        for(Vertex<L>V:vertices)
        {
            if(V.getName().equals(source))
            {
                targetsMap=V.getTargets();
            }
        }
        checkRep();
        return targetsMap;
    }
    // TODO toString()
    /**
     *
     * @return 图的字符串表示
     */
    @Override public String toString() {
        StringBuilder s = new StringBuilder("");
        for(Vertex<L>V:vertices)
        {
            s.append(V.toString());
        }
        return s.toString();
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    // TODO fields
    private final Map<L,Integer>sourceMap=new HashMap<>();
    private final Map<L,Integer>targetMap=new HashMap<>();
    private final L name;
    // Abstraction function:
    //   AF(sourceMap)表示所有指向当前节点的点及权重
    //   AF(targetMap)表示所有当前节点指向的点及权重
    //   AF(name)表示所有当前节点
    // Representation invariant:
    //   weight>0
    // Safety from rep exposure:
    //   sourceMap,targetMap,name都设置为private final，适当时候防御式拷贝

    // TODO constructor
    /**
     *
     * @param name 需要新建点的自身信息
     */
    public Vertex(L name)
    {
        this.name=name;
        checkRep();
    }
    // TODO checkRep
    /**
     *   所有的weight>0
     */
    private void checkRep()
    {
        int weight;
        Set<L>sourceKey=this.sourceMap.keySet();
        for(L source:sourceKey)
        {
            weight=this.sourceMap.get(source);
            assert weight>0;
        }
        Set<L>targetKey=this.targetMap.keySet();
        for(L target:targetKey)
        {
            weight=this.targetMap.get(target);
            assert weight>0;
        }
    }
    // TODO methods
    /**
     *
     * @return 返回当前节点的自身信息
     */
    public L getName()
    {
        return this.name;
    }
    /**
     *
     * @return 返回当前点的sourcesMap
     */
    public Map<L,Integer>getSources()
    {
        Map<L,Integer>map=new HashMap<>(this.sourceMap);
        checkRep();
        return map;
    }
    /**
     *
     * @return 返回当前节点的targetMap
     */
    public Map<L,Integer>getTargets()
    {
        Map<L,Integer>map=new HashMap<>(this.targetMap);
        checkRep();
        return map;
    }
    /**
     * 在targetsMap中加入节点，若weight不为0，则将其加入targets中(若终点已存在，则更新其weight并返回原weight，不存在则直接构建新点并返回0)
     * 若weight为0，则移除target节点(不存在返回0，存在返回原weight)
     * 若weight小于0，则返回-1并输出“weight小于0，错误”
     * @param target 需要加入的目标节点（当前点指向的点）
     * @param weight 需要加入点对应的边的权重
     * @return 返回更新前的weight，如果不存在则返回0
     */
    public int setTarget(L target,int weight)
    {
        Integer preWeight;
        if(weight<0)
        {
            System.out.println("weight小于0，错误");
            return -1;
        }
        else if(weight==0)
        {
            preWeight=targetMap.remove(target);
        }
        else
        {
            preWeight=targetMap.remove(target);
            targetMap.put(target,weight);
        }
        if(preWeight==null)preWeight=0;
        checkRep();
        return preWeight;
    }
    /**
     * 在sourcesMap中加入节点，若weight不为0，则将其加入sources中(若终点已存在，则更新其weight并返回原weight，不存在则直接构建新点并返回0)
     * 若weight为0，则移除source节点(不存在返回0，存在返回原weight)
     * 若weight小于0，则返回-1并输出“weight不能为负数”
     * @param source 需要加入的目标节点（指向当前节点的点）
     * @param weight 需要加入点对应的边的权重
     * @return 返回更新前的weight，如果不存在返回0
     */
    public int setSource(L source,int weight)
    {
        Integer preWeight;
        if(weight<0)
        {
            System.out.println("weight小于0，错误");
            return -1;
        }
        else if(weight==0)
        {
            preWeight=sourceMap.remove(source);
        }
        else
        {
            preWeight=sourceMap.remove(source);
            sourceMap.put(source,weight);
        }
        if(preWeight==null)preWeight=0;
        checkRep();
        return preWeight;
    }
    // TODO toString()
    @Override
    public String toString()
    {
        return "点"+this.name.toString()+"，指向它的点和边的权值为："+this.sourceMap.toString()+"，它指向的点和边的权值为："+this.targetMap.toString()+"\n";
    }
}
