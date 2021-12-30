package ConcreteSet;

import IntervalSet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NoBlankIntervalSetImpl<L> implements NoBlankIntervalSet<L> {
    @Override
    public void checkNoBlank(IntervalSet<L> initialSet, long start, long end)throws Exception
    {
        List<Long> intervalList=new ArrayList<>();
        for(long i=start;i<end;i++)
        {
            intervalList.add(i);
        }
        Set<L>keySet=initialSet.labels();
        for(L key:keySet)
        {
            long tempStart=initialSet.start(key);
            long tempEnd=initialSet.end(key);
            for(long j=tempStart;j<=tempEnd;j++)
            {
                if(intervalList.contains(j))
                {
                    intervalList.remove(j);
                }
            }
        }
        if(intervalList.size()>0)
        {
            throw new Exception("存在空白片段");
        }
    }
    @Override
    public void checkNoBlank(MultiIntervalSet<L> initialSet, long start, long end)throws Exception
    {
        List<Long> intervalList=new ArrayList<>();
        for(long i=start;i<end;i++)
        {
            intervalList.add(i);
        }
        Set<L>keySet=initialSet.labels();
        for(L key:keySet)
        {
            IntervalSet<Integer> tempIntervalSet=initialSet.intervals(key);
            Set<Integer>tempKeySet=tempIntervalSet.labels();
            for(Integer tempKey:tempKeySet)
            {
                long tempStart=tempIntervalSet.start(tempKey);
                long tempEnd=tempIntervalSet.end(tempKey);
                for(long j=tempStart;j<=tempEnd;j++)
                {
                    if(intervalList.contains(j))
                    {
                        intervalList.remove(j);
                    }
                }
            }
        }
        if(intervalList.size()>0)
        {
            throw new Exception("存在空白片段");
        }
    }
}
