package IntervalSet;

import java.util.*;

public class CommonIntervalSet<L> implements IntervalSet<L> {
    final private Map<L, List<Long>>intervalMap=new HashMap<>();
    final private Set<L>labelSet=new HashSet<>();
    /**
     * AF:AF(intervalMap)=标签与时间段的分配关系
     * AF(labelSet)=所有标签的集合
     * RI:
     * 1、labelSet中的label不重复
     * 2、intervalMap中的key都在labelSet中
     * 3、intervalMap中的每一个标签只关联一个时间段
     * Safety from Rep Exposure：intervalMap和labelSet都是final private，必要时使用防御式拷贝
     */
    private void checkRep()
    {
        for(L key:intervalMap.keySet())
        {
            assert labelSet.contains(key);
            List timeAllocate=intervalMap.get(key);
            assert (timeAllocate.size()==2);
            assert (timeAllocate.get(0) instanceof Long);
            assert (timeAllocate.get(1) instanceof Long);
        }
    }
    @Override
    public void insert(long start,long end,L label)throws Exception
    {
        if(start>end)
        {
            throw new IllegalArgumentException("start应该小于end");
        }
        if(!labelSet.contains(label))
        {
            labelSet.add(label);
        }
        List<Long> newInsert=new ArrayList<>();
        newInsert.add(start);
        newInsert.add(end);
        intervalMap.put(label,newInsert);
        checkRep();
    }
    @Override
    public Set<L> labels()
    {
        Set<L> tempLabels=Collections.unmodifiableSet(labelSet);
        checkRep();
        return tempLabels;
    }
    @Override
    public boolean remove(L label)throws Exception
    {
       if(labelSet.contains(label))
       {
           labelSet.remove(label);
           intervalMap.remove(label);
           checkRep();
           return true;
       }
        checkRep();
        return false;
    }
    @Override
    public long start(L label)
    {
       if(labelSet.contains(label))
       {
           long start=intervalMap.get(label).get(0);
           checkRep();
           return start;
       }
        checkRep();
        System.out.println("该标签不存在");
        return -1;
    }
    @Override
    public long end(L label)
    {
        if(labelSet.contains(label))
        {
            long start=intervalMap.get(label).get(1);
            checkRep();
            return start;
        }
        checkRep();
        System.out.println("该标签不存在");
        return -1;
    }
    @Override
    public Map<L, List<Long>> intervals()
    {
        Map<L, List<Long>> tempintervals=Collections.unmodifiableMap(intervalMap);
        checkRep();
        return tempintervals;
    }
    @Override
    public long getStart()
    {
        long start=Long.MAX_VALUE;
        if(labelSet.isEmpty())start=0;
        for(L key:labelSet) {
            if (start > this.start(key)) {
                start = this.start(key);
            }
        }
        checkRep();
        return start;
    }
    @Override
    public long getEnd()
    {
        long end=0;
        for(L key:labelSet) {
           if(end<this.end(key))
           {
               end=this.end(key);
           }
        }
        checkRep();
        return end;
    }

    @Override
    public String toString()
    {
        String s=intervalMap.toString();
        checkRep();
        return s;
    }
}
