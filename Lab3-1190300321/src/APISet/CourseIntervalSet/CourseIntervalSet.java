package APISet.CourseIntervalSet;

import API.APIs;
import API.APIsImpl;
import ConcreteSet.PeriodicIntervalSet;
import ConcreteSet.PeriodicIntervalSetImpl;
import IntervalSet.CommonMultiIntervalSet;
import IntervalSet.IntervalSet;
import IntervalSet.MultiIntervalSet;

import java.util.*;

public class CourseIntervalSet<L>  implements IcourseIntervalSet<L> {
    /**
     * AF(periodicIntervalSet)=安排表
     * AF(courseSet)=所有课程表
     * AF(courseMap)=所有课程的周期性记录
     * rep:安排表里的课程必须在所有课程集合里
     * RI：使用private final，必要时候使用防御式拷贝
     */
    private final PeriodicIntervalSet<L>periodicIntervalSet=new PeriodicIntervalSetImpl<>();
    private final Set<L> courseSet=new HashSet<>();
    private final Map<L, List> courseMap=new HashMap<>();
    private void checkRep()
    {
        Set<L>keySet=periodicIntervalSet.labels();
        for(L key:keySet)
        {
            assert courseSet.contains(key);
        }
    }
    public CourseIntervalSet()
    {
        periodicIntervalSet.periodLength(7);//设置周期长度为7天
    }
    public void insert(long start, long end, L label,int index)throws Exception
    {
        if(start>end||index<1||index>7)
        {
            System.out.println("插入时间错误");
            return;
        }
        courseSet.add(label);
        periodicIntervalSet.insert(start,end,label,index);
        checkRep();
    }
    public void setPeriod(L label,long start,long end)
    {
        if(start>end)
        {
            System.out.println("周期时间设置错误");
            return;
        }
        List<Long>tempList=new ArrayList<>();
        tempList.add(start);
        tempList.add(end);
        courseMap.put(label,tempList);
        checkRep();
    }

    public long totalTime(L label)
    {
        long totalTime=0;
        if(!courseSet.contains(label))
        {
            System.out.println("课程表中不存在这个课程");
            return 0;
        }
        try
        {
            MultiIntervalSet<Integer>tempSet=this.periodicIntervalSet.interval(label);
            for(Integer i:tempSet.labels())
            {
                IntervalSet<Integer>tempIntervalSet=tempSet.intervals(i);
                for(Integer tempI:tempIntervalSet.labels())
                {
                    long start=tempIntervalSet.start(tempI);
                    long end=tempIntervalSet.end(tempI);
                    totalTime+=(end-start);
                }
            }
        }catch (Exception e)
        {
            System.out.println("课程表中不存在这个课程");
            return -1;
        }
        return totalTime;
    }

    public MultiIntervalSet<Integer> interval(L label)
    {
        MultiIntervalSet<Integer>multiIntervalSet=new CommonMultiIntervalSet<Integer>();
        try
        {
            multiIntervalSet=this.periodicIntervalSet.interval(label);
            return multiIntervalSet;
        }catch (Exception e)
        {
            System.out.println("不存在这门课程");
        }
        return multiIntervalSet;
    }
    public double calcFreeTimeRatio()throws Exception
    {
        APIs api=new APIsImpl();
        double ratio=0;
        for(int i=0;i<7;i++)
        {
            MultiIntervalSet<L>multiIntervalSet=this.periodicIntervalSet.getDayArrange(i);
            ratio+=api.calcFreeTimeRatio(multiIntervalSet,10);
        }
        return ratio/7;
    }
    public Set<L>labels()
    {
        return Collections.unmodifiableSet(this.courseSet);
    }
    public MultiIntervalSet<L>getDayArrange(int index)
    {
        return this.periodicIntervalSet.getDayArrange(index);
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("所有具有周期性的课程的周期性为：\n");
        for(L key:this.courseMap.keySet())
        {
            sb.append(key+":"+this.courseMap.get(key).get(0)+"周-"+this.courseMap.get(key).get(1)+"周\n");
        }
        try
        {
            sb.append("课表空闲率为："+this.calcFreeTimeRatio()+'\n');
        }catch (Exception e)
        {
            System.out.println("课程不存在");
        }
        sb.append("周内课程安排信息为：\n");
        {
            for(int i=0;i<7;i++)
            {
                sb.append("第"+(i+1)+"天的安排\n");
                MultiIntervalSet<L> tempSet=this.periodicIntervalSet.getDayArrange(i);
                sb.append(tempSet.toString()+'\n');
            }

        }
        return sb.toString();
    }
}
