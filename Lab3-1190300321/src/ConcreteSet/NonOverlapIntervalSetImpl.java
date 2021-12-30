package ConcreteSet;

import IntervalSet.IntervalSet;
import IntervalSet.MultiIntervalSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class NonOverlapIntervalSetImpl<L> implements NonOverlapIntervalSet<L>{
    @Override
    public void checkNoOverlap(IntervalSet<L> initialSet, long start, long end, L label)throws Exception
    {
        Map<L, List<Long>> checkMap=initialSet.intervals();
        for(L key:checkMap.keySet())
        {
            long tempStart=initialSet.start(key);
            long tempEnd=initialSet.end(key);
            if(start>=tempStart&&start<=tempEnd)
            {
                throw new Exception("输入的时间重叠，不符合要求");
            }
        }
    }
    @Override
    public void checkNoOverlap(MultiIntervalSet<L> multiInitialSet, long start, long end)throws Exception {
        Set<L> labelSet=multiInitialSet.labels();
        for(L key:labelSet)
        {
            IntervalSet<Integer> tempIntervalSet=multiInitialSet.intervals(key);
            Set<Integer> tempLabelSet=tempIntervalSet.labels();
            for(Integer intervalKey:tempLabelSet)
            {
                long tempStart=tempIntervalSet.start(intervalKey);
                long tempEnd=tempIntervalSet.end(intervalKey);
                if(start>=tempStart&&start<=tempEnd)
                {
                    throw new Exception("输入的时间重叠，不符合要求");
                }
            }
        }
    }
}
