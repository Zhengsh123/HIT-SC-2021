package IntervalSet;

import java.util.*;

public class CommonMultiIntervalSet<L> extends MultiIntervalSet<L> {
    final private Map<L, List<List<Long>>> multiIntervalMap =new HashMap<>();
    final private Map<L,IntervalSet<Integer>> multiIntervalMaptemp=new HashMap<>();
    final private Set<L>labelSet=new HashSet<>();
    /**
     * AF:
     * AF(multiIntervalMap)=标签与时间段的分配关系
     * AF(labelSet)=所有标签的集合
     * RI:
     * 1、labelSet中的label不重复
     * 2、intervalMap中的key都在labelSet中
     * Safety from Rep Exposure:rep使用final private，必要时候使用防御式拷贝
     */
    private void checkRep()
    {
        for(L key: multiIntervalMap.keySet())
        {
            assert labelSet.contains(key);
        }
    }
    public CommonMultiIntervalSet(IntervalSet<L> initial)throws Exception
    {
        super(initial);
        Map<L, List<Long>> initialMap=initial.intervals();
        Set<L> initialLabel=initialMap.keySet();
        for(L key:initialLabel)
        {
            long start=initialMap.get(key).get(0);
            long end=initialMap.get(key).get(1);
            this.insert(start,end,key);
        }
    }
    public CommonMultiIntervalSet()
    {
        super();
    }
    @Override
    public void insert(long start, long end, L label)throws Exception {
        if(start>=end)
        {
            System.out.println("start应小于end");
            return;
        }
        if(labelSet.contains(label))
        {
            Map<L,IntervalSet<Integer>> temp=multiIntervalMaptemp;
            IntervalSet<Integer> intervalTemp=temp.get(label);
            Map<Integer,List<Long>> intervalMap=new HashMap<>(intervalTemp.intervals());
            Set<Integer> keySet=intervalMap.keySet();
            int i=1;
            for(;i<Integer.MAX_VALUE;i++) {
                if (!keySet.contains(i)) {
                    break;
                }
            }
            intervalTemp.insert(start,end,i);
            multiIntervalMaptemp.remove(label);
            multiIntervalMaptemp.put(label,intervalTemp);

            List<Long> newInsert=new ArrayList<>();
            newInsert.add(start);
            newInsert.add(end);
            List<List<Long>>oldInsert= multiIntervalMap.get(label);
            List<List<Long>>tempList=new ArrayList<>(oldInsert);
            tempList.add(newInsert);
            multiIntervalMap.put(label,tempList);
        }
        else
        {
            IntervalSet<Integer> tempSet=new CommonIntervalSet<Integer>();
            tempSet.insert(start,end,0);
            multiIntervalMaptemp.put(label,tempSet);
            labelSet.add(label);
            List<Long> newInsert=new ArrayList<>();
            newInsert.add(start);
            newInsert.add(end);
            List<List<Long>>temp=new ArrayList<>();
            temp.add(newInsert);
            multiIntervalMap.put(label,temp);
        }

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
    public boolean remove(L label) {
        if(labelSet.contains(label))
        {
            labelSet.remove(label);
            multiIntervalMap.remove(label);
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }
    @Override
    public IntervalSet<Integer> intervals(L label)throws Exception
    {
        IntervalSet<Integer> timeSet=new CommonIntervalSet<Integer>();
        if(!labelSet.contains(label))
        {
            //System.out.println("不含这个标签，错误");
            return timeSet;
        }
        List<List<Long>> resList=new ArrayList<>(multiIntervalMap.get(label));
        int length=resList.size();
        for(int i=0;i<length;i++)
        {
            List<Long>tempList=resList.get(i);
            long start=tempList.get(0);
            long end=tempList.get(1);
            timeSet.insert(start,end,i);
        }
        checkRep();
        return timeSet;
    }
    @Override
    public String toString() {
        String s= multiIntervalMap.toString();
        checkRep();
        return s;
    }
}
