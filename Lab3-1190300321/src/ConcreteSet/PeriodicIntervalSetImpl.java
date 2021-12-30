package ConcreteSet;

import IntervalSet.*;
import IntervalSet.MultiIntervalSet;
import java.util.*;

public class PeriodicIntervalSetImpl<L>  implements PeriodicIntervalSet<L>{
    /**
     * AF:
     * AF(labelSet)=所有的标签集合
     * AF(periodList)=在每一个周期内部的分配情况
     * AF(periodLabelMap)=存储每一个有周期性标签的周期性开始时间和结束时间
     * rep：每一个labelSet中的label必须在periodLabelMap中
     * RI:labelSet等定义为private final，必要时候防御式拷贝
     */
    private final Set<L> labelSet=new HashSet<>();
    private  List<MultiIntervalSet<L>> periodList=new ArrayList<>();
    private final Map<L,List<Long>> periodLabelMap=new HashMap<>();
    private void checkRep()
    {
        Set<L>keySet=periodLabelMap.keySet();
        for(L key:labelSet)
        {
            assert keySet.contains(key);
        }
    }
    @Override
    public void insert(long start,long end,L label,int index)throws Exception
    {
        periodList.get(index).insert(start,end,label);
        checkRep();
    }

    @Override
    public boolean remove(L label)throws IllegalArgumentException
    {
        boolean flag=false;
        if(!labelSet.contains(label))
        {
            throw new IllegalArgumentException("不存在这个label");
        }
        labelSet.remove(label);
        periodLabelMap.remove(label);
        int length=periodList.size();
        for(int i=0;i<length;i++)
        {
            flag=periodList.get(i).remove(label);
        }
        checkRep();
        return flag;
    }
    @Override
    public MultiIntervalSet<Integer> interval(L label)throws Exception
    {
        boolean flag=false;
        MultiIntervalSet<Integer> multiIntervalSet=new CommonMultiIntervalSet<Integer>();
        int length=periodList.size();
        //周期中每一个片段循环插入，例如一周中每一天
        for(int i=0;i<length;i++)
        {
            Set<L> tempLabelSet=periodList.get(i).labels();
            if(tempLabelSet.contains(label))
            {
                flag=true;
                IntervalSet<Integer>integerIntervalSet=new CommonIntervalSet<Integer>();
                integerIntervalSet=periodList.get(i).intervals(label);
                for(Integer tempLabel:integerIntervalSet.labels())
                {
                    long start=integerIntervalSet.start(tempLabel);
                    long end= integerIntervalSet.end(tempLabel);
                    multiIntervalSet.insert(start,end,i);
                }
            }
        }
        if(!flag)
        {
            throw new IllegalArgumentException("不存在这个label");
        }
        checkRep();
        return multiIntervalSet;
    }

    @Override
    public void periodArrange(long start, long end, L Label)
    {
        List<Long> timeList=new ArrayList<>();
        timeList.add(start);
        timeList.add(end);
        labelSet.add(Label);
        this.periodLabelMap.put(Label,timeList);
        checkRep();
    }

    @Override
    public Map<L,List<Long>>getPeriodArrange(L label)throws IllegalArgumentException
    {
        if(!labelSet.contains(label))throw new IllegalArgumentException("不存在这个label");
        Map<L,List<Long>> periodArrangeMap=new HashMap<>();
        periodArrangeMap.put(label,periodLabelMap.get(label));
        checkRep();
        return periodArrangeMap;
    }

    @Override
    public List<MultiIntervalSet<L>> periodLength(int length)
    {
        List<MultiIntervalSet<L>> list=new ArrayList<>();
        for(int i=0;i<length;i++)
        {
            MultiIntervalSet<L> tempSet=new CommonMultiIntervalSet<L>();
            list.add(tempSet);
        }
        this.periodList=list;
        checkRep();
        return Collections.unmodifiableList(list);
    }

    @Override
    public Map<MultiIntervalSet<Integer>,List> getLabelArrange(L label)throws Exception
    {
        if(!labelSet.contains(label))throw new IllegalArgumentException("不存在这个label");
        Map<MultiIntervalSet<Integer>,List> arrangeMap=new HashMap<>();
        MultiIntervalSet<Integer>multiIntervalSet=this.interval(label);
        List<Long>arrangeList=periodLabelMap.get(label);
        arrangeMap.put(multiIntervalSet,arrangeList);
        checkRep();
        return arrangeMap;
    }

    @Override
    public Set<L>labels()
    {
        checkRep();
        return Collections.unmodifiableSet(this.labelSet);
    }
    @Override
    public MultiIntervalSet<L> getDayArrange(int index)
    {
        return this.periodList.get(index);
    }
    @Override
    public String toString()
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("一个周期内安排:");
        stringBuilder.append(periodList.toString());
        stringBuilder.append("  每个label的周期性(周期开始，周期结束):");
        stringBuilder.append(periodLabelMap.toString());
        String s=stringBuilder.toString();
        return s;
    }
}
