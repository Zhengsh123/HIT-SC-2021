package APISet.ProcessIntervalSet;
import API.APIs;
import API.APIsImpl;
import ConcreteSet.NonOverlapIntervalSet;
import ConcreteSet.NonOverlapIntervalSetImpl;
import IntervalSet.*;
import IntervalSet.IntervalSet;

import java.util.*;

public class ProcessIntervalSet<L> extends CommonMultiIntervalSet<L>
        implements IProcessIntervalSet<L> {
    /**
     * AF:
     * AF(labels)=所有进程的集合
     * AF(multiIntervalSet)=所有安排的集合
     * AF(dutyList)=所有安排情况的List表示
     * Rep:
     * 1.dutyList和multiIntervalSet同步
     * 2.所有进程必须在labels里才能操作
     * RI:使用private final，必要时候防御式拷贝
     */
    private final Set<L>labels=new HashSet<>();
    private final MultiIntervalSet<L>multiIntervalSet=new CommonMultiIntervalSet<L>();
    private final List<Integer>dutyList=new ArrayList<>();

    private NonOverlapIntervalSet<L> nois=new NonOverlapIntervalSetImpl<>();

    private void checkRep()
    {
        for(L key:multiIntervalSet.labels())
        {
            assert labels.contains(key);
        }
        for(L key:multiIntervalSet.labels())
        {
            try
            {
                IntervalSet<Integer>tempSet=multiIntervalSet.intervals(key);
                for(Integer tempKey:tempSet.labels())
                {
                    long start=tempSet.start(tempKey);
                    long end=tempSet.end(tempKey);
                    for(long i=start;i<end;i++)
                    {
                        assert dutyList.get((int)i)==1;
                    }
                }
            }catch (Exception e)
            {
                System.out.println("参数设置存在问题");
            }
        }
    }
    /**
     * 计算空闲时间占比
     * @return 空闲时间占比
     */
    public double calcFreeTimeRatio()throws Exception
    {
        APIs<L> api=new APIsImpl<>();
        double ratio=api.calcFreeTimeRatio(this.multiIntervalSet);
        checkRep();
        return ratio;
    }
    /**
     * 检测安排是否存在重叠，如果存在重叠抛出异常
     * @throws Exception 如果安排中存在重叠，抛出异常
     */
    public void checkNoOverlap()throws Exception
    {
        long start=Long.MAX_VALUE;
        long end=0;
        for(L key:this.multiIntervalSet.labels())
        {
            IntervalSet<Integer>tempSet=multiIntervalSet.intervals(key);
            start=Math.min(start,tempSet.getStart());
            end=Math.max(end,tempSet.getEnd());
        }
        nois.checkNoOverlap(this.multiIntervalSet,start,end);
    }
    /**
     * 插入安排
     * @param start 开始时间
     * @param end 结束时间
     * @param label 人员
     * @throws IllegalArgumentException 如果start>=end,抛出异常
     */
    @Override
    public void insert(long start, long end, L label)
    {
        if(!labels.contains(label))
        {
            labels.add(label);
        }
        try
        {
            multiIntervalSet.insert(start,end,label);
            if(this.dutyList.size()<end)
            {
                for(int i=dutyList.size();i<=end;i++)
                {
                    dutyList.add(0);
                }
            }
            for(long i=start;i<end;i++)
            {
                dutyList.set((int)i,1);
            }
        }catch (Exception e)
        {
            System.out.println("参数存在问题");
        }
        checkRep();
    }

    /**
     * 移除某个片段
     * @param label 需要移除的进程label
     */
    public boolean remove(L label)
    {
        labels.remove(label);
        try
        {
            IntervalSet<Integer>intervalSet=this.multiIntervalSet.intervals(label);
            for(Integer i:intervalSet.labels())
            {
                long start=intervalSet.start(i);
                long end=intervalSet.end(i);
                for(long j=start;j<end;j++)
                {
                    dutyList.set((int)j,0);
                }
            }
        }catch (Exception e)
        {
            System.out.println("不存在这个进程");
        }

        boolean res=this.multiIntervalSet.remove(label);
        checkRep();
        return res;
    }
    /**
     * 获取所有进程的安排情况
     * @param label 需要查询的进程的信息
     * @return  返回进程的执行时间
     */
    public IntervalSet<Integer> interval(L label)
    {
        IntervalSet<Integer> intervalSet=new CommonIntervalSet<Integer>();
        try
        {
            intervalSet=this.multiIntervalSet.intervals(label);
        }catch (Exception e)
        {
            System.out.println("不存在这个label");
        }
        return intervalSet;
    }
    /**
     * 返回所有的进程
     * @return 返回所有的进程
     */
    public Set<L> labels()
    {
        return Collections.unmodifiableSet(labels);
    }
    public long totalTime(L label)
    {
        long totalTime=0;
        try
        {
            IntervalSet<Integer>integerIntervalSet=this.multiIntervalSet.intervals(label);
            for(Integer i:integerIntervalSet.labels())
            {
                long start=integerIntervalSet.start(i);
                long end=integerIntervalSet.end(i);
                totalTime+=(end-start);
            }
        }catch (Exception e)
        {
            System.out.println("查找的进程不存在");
        }
        checkRep();
       return totalTime;
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        int length=this.dutyList.size();
        int i=0;

        sb.append("详细安排如下：\n");
        while(i<length){
            if(dutyList.get(i)==0)
            {
                i++;
                continue;
            }
            for(L key:multiIntervalSet.labels())
            {
                try
                {
                    IntervalSet<Integer>tempIntevalSet=multiIntervalSet.intervals(key);
                    for(Integer tempKey:tempIntevalSet.labels())
                    {
                        if(tempIntevalSet.start(tempKey)==i)
                        {
                            sb.append(tempIntevalSet.start(tempKey)+"——"+tempIntevalSet.end(tempKey));
                            sb.append(":"+key+'\n');
                            i+=tempIntevalSet.end(tempKey)-tempIntevalSet.start(tempKey);
                            break;
                        }
                    }
                }catch (Exception e)
                {

                }
            }
        }
        sb.append("安排表空闲率：");
        try
        {
            sb.append(this.calcFreeTimeRatio());
        }catch (Exception e)
        {
            sb.append("");
        }
        return sb.toString();
    }
}
