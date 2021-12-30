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
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices)=图中所有的节点
    //   AF(edges)=图中所有的边
    // Representation invariant:
    //   所有的边两端都应该存在，weight>0
    // Safety from rep exposure:
    //   vertices和edges都设置为private final，
    //   必要的时候使用防御式拷贝

    // TODO constructor
    public ConcreteEdgesGraph() {
    }
    // TODO checkRep

    /*
     * 所有的边两端都应该存在，且weight>0
     */
    public void checkRep()
    {
        for(Edge<L>E:edges)
        {
            L source=E.getSource();
            L target=E.getTarget();
            int weight=E.getWeight();
            assert vertices.contains(source);
            assert vertices.contains(target);
            assert  weight>0;
        }
    }

    @Override public boolean add(L vertex) {
        if(vertices.contains(vertex))
        {
            System.out.println("节点已存在");
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }

    @Override public int set(L source, L target, int weight) {
        int preWeight=0;
        boolean flagSource=false;
        boolean flagTarget=false;
        if(weight<0)
        {
            System.out.println("weight不可以是负数，错误");
            return -1;
        }
        else if(weight==0)
        {
            for(Iterator<Edge<L>>it=edges.iterator();it.hasNext();)
            {
                Edge<L>E=it.next();
                if(E.getSource().equals(source)&&E.getTarget().equals(target))
                {
                    preWeight=E.getWeight();
                    it.remove();
                    break;
                }
            }
            //如果删除之后已经没有边与之相关，即出现孤立点，删除该点
            for(Iterator<Edge<L>>it=edges.iterator();it.hasNext();)
            {
                Edge<L>E=it.next();
                if(E.getTarget().equals(source)||E.getSource().equals(source))
                {
                    flagSource=true;
                }
                if(E.getTarget().equals(target)||E.getSource().equals(target))
                {
                    flagTarget=true;
                }
                if(flagSource&&flagTarget)break;
            }
            if(!flagSource)vertices.remove(source);
            if(!flagTarget)vertices.remove(target);
        }
        else//weight>0
        {
            for(Iterator<Edge<L>>it=edges.iterator();it.hasNext();)
            {
                Edge<L>E=it.next();
                if(E.getSource().equals(source)&&E.getTarget().equals(target))
                {
                    preWeight=E.getWeight();
                    it.remove();
                    break;
                }
            }
            if(!vertices.contains(source))vertices.add(source);
            if(!vertices.contains(target))vertices.add(target);
            Edge<L>E=new Edge<>(source,target,weight);
            edges.add(E);
        }
        checkRep();
        return preWeight;
    }

    @Override public boolean remove(L vertex) {
        if(vertices.contains(vertex))
        {
            for(Iterator<Edge<L>>it=edges.iterator();it.hasNext();)
            {
                Edge<L>E=it.next();
                if(E.getSource().equals(vertex)||E.getTarget().equals(vertex))
                {
                    it.remove();
                }
            }
            vertices.remove(vertex);
            checkRep();
            return true;
        }
        checkRep();
        System.out.println("删除的节点不存在");
        return false;
    }

    @Override public Set<L> vertices() {
        Set<L>verticesSet=new HashSet<>(vertices);
        checkRep();
        return verticesSet;
    }

    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer>sourcesMap=new HashMap<>();
        for(Iterator<Edge<L>>it=edges.iterator();it.hasNext();) {
            Edge<L> E = it.next();
            if(E.getTarget().equals(target))
            {
                sourcesMap.put(E.getSource(),E.getWeight());
            }
        }
        checkRep();
        return sourcesMap;
    }
    @Override public Map<L, Integer> targets(L source) {
        Map<L,Integer>targetsMap=new HashMap<>();
        for(Iterator<Edge<L>>it=edges.iterator();it.hasNext();) {
            Edge<L> E = it.next();
            if(E.getSource().equals(source))
            {
                targetsMap.put(E.getTarget(),E.getWeight());
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
        for(Edge<L> E1:edges)
        {
            s.append(E1.toString());
        }
        return s.toString();
    }
}
/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    // TODO fields
    private final L source,target;
    private final int weight;

    // Abstraction function:
    //   AF(source)=边的出节点
    //   AF(target)=边的入节点
    //   AF(weight)=边的权重
    // Representation invariant:
    //   边的两端都存在且weight>0
    // Safety from rep exposure:
    //   source和target以及weight都设置为private final

    // TODO constructor
    public Edge(L source,L target,int weight)
    {
        this.source=source;
        this.target=target;
        this.weight=weight;
        checkRep();
    }
    // TODO checkRep
    /*
     *    边的两端都存在且weight>0
     */
    private void checkRep()
    {
        assert this.target!=null;
        assert this.source!=null;
        assert this.weight>0;
    }
    // TODO methods

    /**
     *
     * @return 返回该边的出节点
     */
    public L getSource()
    {
        return this.source;
    }
    /**
     *
     * @return 返回该边的入节点
     */
    public L getTarget()
    {
        return this.target;
    }
    /**
     *
     * @return 返回该边的权重
     */
    public int getWeight()
    {
        return this.weight;
    }

    // TODO toString()
    @Override
    public String toString()
    {
        return this.source+"->"+this.target+" weight:"+weight+'\n';
    }
}
