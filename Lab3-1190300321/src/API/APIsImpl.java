package API;

import IntervalSet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class APIsImpl<L> implements APIs<L> {

    public double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2)throws Exception
    {
        long start=0;
        long end=0;
        Set<L>labelSet1=s1.labels();
        Set<L>labelSet2=s2.labels();
        //得到两个Set的起止时间
        for(L key:labelSet1)
        {
            IntervalSet<Integer>tempSet=s1.intervals(key);
            if(start>tempSet.getStart())start=tempSet.getStart();
            if(end<tempSet.getEnd())end=tempSet.getEnd();
        }
        for(L key:labelSet2)
        {
            IntervalSet<Integer>tempSet=s2.intervals(key);
            if(start>tempSet.getStart())start=tempSet.getStart();
            if(end<tempSet.getEnd())end=tempSet.getEnd();
        }

        long length=end-start;

        int count=0;
        //计算相似度
        for(L key:labelSet1)
        {
            IntervalSet<Integer>tempSet1=s1.intervals(key);
            //s2中若不存在这个key，跳过
            if(!s2.labels().contains(key))continue;

            long setLength=tempSet1.labels().size();
            long arrangeLength=tempSet1.getEnd()-tempSet1.getStart();

            List<Integer>keyList=new ArrayList<>();
            for(int i=0;i<arrangeLength+1;i++)
            {
                keyList.add(0);
            }
            for(int i=0;i<setLength;i++)
            {
                long tempStart=tempSet1.intervals().get(i).get(0)-tempSet1.getStart();
                long tempEnd=tempSet1.intervals().get(i).get(1)-tempSet1.getStart();
                for(long j=tempStart;j<tempEnd;j++)
                {
                    keyList.set((int)j,1);
                }
            }
            //计算s2中有多少相似度
            IntervalSet<Integer>tempSet2=s2.intervals(key);
            long setLength2=tempSet2.labels().size();
            for(int i=0;i<setLength2;i++)
            {
                long tempStart=tempSet2.intervals().get(i).get(0)-tempSet2.getStart();
                long tempEnd=tempSet2.intervals().get(i).get(1)-tempSet2.getStart();
                for(long j=tempStart;j<=tempEnd;j++)
                {
                   if(keyList.get((int)j)==1)count=count+1;
                }
            }
        }
        return (double)count/length;
    }


    public double calcConflictRatio(IntervalSet<L> set) {
        List<Integer>arrangeList=new ArrayList<>();
        long start=set.getStart();
        long end=set.getEnd();
        long length=end-start+1;
        for(int i=0;i<=length;i++)
        {
            arrangeList.add(0);
        }
        Set<L>keySet=set.labels();
        int count=0;
        for(L key:keySet)
        {
            long tempStart=set.start(key)-start;
            long tempEnd=set.end(key)-start;
            for(long j=tempStart;j<=tempEnd;j++)
            {
                if(arrangeList.get((int)j)==1)
                {
                    count++;
                    arrangeList.set((int)j,2);
                    continue;
                }
                arrangeList.set((int)j,1);
            }
        }
        return (double)count/length;
    }

    public double calcConflictRatio(MultiIntervalSet<L> set)throws Exception {
        List<Integer>arrangeList=new ArrayList<>();
        //获取开始和结束时间
        Set<L>keySet=set.labels();
        long start=0;
        long end=0;
        int count=0;
        for(L key:keySet)
        {
            start=Math.min(start,set.intervals(key).getStart());
            end=Math.max(end,set.intervals(key).getEnd());
        }
        long length=end-start+1;
        //初始化安排列表
        for(int i=0;i<length;i++)arrangeList.add(0);
        //计算冲突
        for(L key:keySet)
        {
            IntervalSet<Integer>tempSet=set.intervals(key);
            Set<Integer>tempKeySet=tempSet.labels();
            for(Integer tempKey:tempKeySet)
            {
                long tempStart=tempSet.start(tempKey)-start;
                long tempEnd=tempSet.end(tempKey)-start;
                for(long j=tempStart;j<=tempEnd;j++)
                {
                    if(arrangeList.get((int)j)==1)
                    {
                        arrangeList.set((int)j,2);
                        count++;
                        continue;
                    }
                    arrangeList.set((int)j,1);
                }
            }
        }
        return (double)count/length;
    }

    public double calcFreeTimeRatio(IntervalSet<L> set) {
        List<Integer>arrangeList=new ArrayList<>();
        long start=set.getStart();
        long end=set.getEnd();
        long length=end-start+1;
        return this.calcFreeTimeRatio(set,length);
    }
    public double calcFreeTimeRatio(MultiIntervalSet<L> set) throws Exception{
        List<Integer>arrangeList=new ArrayList<>();
        //获取开始和结束时间
        Set<L>keySet=set.labels();
        long start=0;
        long end=0;
        int count=0;
        for(L key:keySet)
        {
            end=Math.max(end,set.intervals(key).getEnd());
            start=Math.min(start,set.intervals(key).getStart());
        }
        long length=end-start;
        return this.calcFreeTimeRatio(set,length);
    }
    public double calcFreeTimeRatio(IntervalSet<L> set,long length)
    {
        long start=set.getStart();
        long end=set.getEnd();
        List<Integer>arrangeList=new ArrayList<>();
        for(int i=0;i<length;i++)
        {
            arrangeList.add(0);
        }
        Set<L>keySet=set.labels();
        for(L key:keySet)
        {
            long tempEnd=set.end(key)-start;
            long tempStart=set.start(key)-start;
            for(long j=tempStart;j<=tempEnd;j++)
            {
                arrangeList.set((int)j,1);
            }
        }
        int count=0;
        for(int i=0;i<length;i++)
        {
            if(arrangeList.get(i)==0)count++;
        }
        return (double)count/length;
    }

    public double calcFreeTimeRatio(MultiIntervalSet<L> set,long Length)throws Exception
    {
        List<Integer>arrangeList=new ArrayList<>();
        //获取开始和结束时间
        Set<L>keySet=set.labels();
        long start=0;
        int count=0;
        long end=0;
        for(L key:keySet)
        {
            start=Math.min(start,set.intervals(key).getStart());
            end=Math.max(end,set.intervals(key).getEnd());
        }
        long length=end-start;
        if(length==0)return 1;
        //初始化安排列表
        for(int i=0;i<length;i++)arrangeList.add(0);
        //计算空闲
        for(L key:keySet)
        {
            IntervalSet<Integer>tempSet=set.intervals(key);
            Set<Integer>tempLabel=tempSet.labels();
            for(Integer tempKey:tempLabel)
            {
                long tempEnd=tempSet.end(tempKey)-start;
                long tempStart=tempSet.start(tempKey)-start;
                for(long j=tempStart;j<tempEnd;j++)
                {
                    arrangeList.set((int)j,1);
                }
            }
        }
        for(int i=0;i<length;i++)
        {
            if(arrangeList.get(i)!=0)count++;
        }
        return 1-(double)count/Length;
    }
}
