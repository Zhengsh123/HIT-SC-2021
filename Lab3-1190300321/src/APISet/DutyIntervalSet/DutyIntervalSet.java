package APISet.DutyIntervalSet;
import API.APIs;
import API.APIsImpl;
import IntervalSet.*;
import ConcreteSet.*;

import java.text.ParseException;
import java.util.*;

public class DutyIntervalSet<L> extends CommonIntervalSet<L>
        implements IDutyIntervalSet<L> {
    /**
     * AF(intervalSet)=排班表
     * AF(startTime)=排班表的开始时间
     * AF(endTime)=排班表的结束时间
     * AF(dutyList)=记录在需要排班的时间段内每一天的安排情况
     *
     * rep:1.startTime>endTime
     * 2.dutyList中记录安排的情况需要与IntervalSet一致
     * 3.同一天不能连续安排人
     * 4.一个人如果安排多天只能是连续多天
     * 5.intervalSet中的安排不能超过总的安排天数
     *
     * RI：IntervalSet等采用private final,必要的时候防御式拷贝
     */
    private final IntervalSet<L> intervalSet=new CommonIntervalSet<L>();
    private String startTime="";
    private String endTime="";
    private final List<Integer>dutyList=new ArrayList<>();
    private final Set<L>labelSet=new HashSet<>();

    private NonOverlapIntervalSet<L> nois=new NonOverlapIntervalSetImpl<>();
    private NoBlankIntervalSet<L> nbis=new NoBlankIntervalSetImpl<>();

    public void checkRep()throws ParseException
    {
        DateCaculate dc=new DateCaculateImpl();
        //判断起止时间是否满足要求
        long total=dc.sub(startTime,endTime);
        assert total>=0;
        //判断intervalSet安排是否超出总安排时间
        Set<L> keySet=intervalSet.labels();
        for(L key:keySet)
        {
            long start=intervalSet.start(key);
            long end=intervalSet.end(key);
            assert start<=total;
            assert end<=total;
        }
        //判断dutyList是否和IntervalSet同步
        for(L key:keySet)
        {
            long start=intervalSet.start(key);
            long end=intervalSet.end(key);
            for(long i=start;i<=end;i++)
            {
                int j=(int)i;
                assert dutyList.get(j)==1;
            }
        }
        //判断是否存在一天安排多个人
        List<Integer>testList=new ArrayList<>();
        int length=this.dutyList.size();
        for(int i=0;i<length;i++)
        {
            testList.add(0);
        }
        for(L key:keySet)
        {
            long start=intervalSet.start(key);
            long end=intervalSet.end(key);
            for(long i=start;i<end;i++)
            {
                int j=(int)i;
                assert testList.get(j)==0;
                testList.set(j,1);
            }
        }
        //判断一个人是否被安排多天但不是连续的
        //由于使用intervalSet，自然成立
    }
    public DutyIntervalSet(String startTime,String endTime){
        try
        {
            this.endTime=endTime;
            this.startTime=startTime;
            DateCaculate dc=new DateCaculateImpl();
            int end=(int)dc.sub(startTime, endTime);
            for(int i=0;i<=end;i++)
            {
                dutyList.add(0);
            }
        }catch (Exception e)
        {
            System.out.println("输入时间错误");
        }
    }
    @Override
    public void checkNoBlank()throws ParseException,Exception {
        long start=0;
        DateCaculate dc=new DateCaculateImpl();
        long end=dc.sub(startTime, endTime);
        nbis.checkNoBlank(this.intervalSet,start,end);
        checkRep();
    }
    @Override
    public void insert(long start, long end, L label)throws IllegalArgumentException,Exception
    {
        if(start>end)
        {
            throw new IllegalArgumentException("start应该比end小");
        }
        labelSet.add(label);
        for(long i=start;i<=end;i++)
        {
            nois.checkNoOverlap(this.intervalSet,start,end,label);
            dutyList.set((int)i,1);
        }
        intervalSet.insert(start,end,label);
        checkRep();
    }
    @Override
    public List<String> interval(L label)throws Exception
    {
        if(!labelSet.contains(label))
        {
            throw new IllegalArgumentException("不存在这个人的安排");
        }
        List<String>intervalList=new ArrayList<>();
        long start=this.intervalSet.start(label);
        long end=this.intervalSet.end(label);
        DateCaculate dc=new DateCaculateImpl();
        for(long i=start;i<=end;i++)
        {
            intervalList.add(dc.add(this.startTime,(int)i));
        }
        checkRep();
        return intervalList;
    }
    @Override
    public boolean remove(L label)throws Exception
    {
        labelSet.remove(label);
        boolean temp=intervalSet.remove(label);
        checkRep();
        return temp;
    }
    @Override
    public Set<L> labels()
    {
        return Collections.unmodifiableSet(labelSet);
    }
    @Override
    public double calcFreeTimeRatio()throws Exception
    {
        APIs<L>api=new APIsImpl<>();
        DateCaculate dc=new DateCaculateImpl();
        long length=dc.sub(startTime,endTime)+1;
        return api.calcFreeTimeRatio(intervalSet,length);
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("需要安排的日期为：");
        sb.append(startTime+"————"+endTime);
        sb.append("\n已经安排的日期及每天安排人为：\n");
        int length=this.dutyList.size();
        DateCaculate dc=new DateCaculateImpl();
        for(int i=0;i<length;i++)
        {
            if(dutyList.get(i)==1)
            {
                for(L key:labelSet) {
                    long start = intervalSet.start(key);
                    long end = intervalSet.end(key);
                    if (i <= end && i >= start)
                    {

                        sb.append(dc.add(this.startTime,i)+"--");
                        sb.append(key);
                        sb.append('\n');
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }
}
