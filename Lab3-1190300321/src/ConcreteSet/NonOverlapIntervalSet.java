package ConcreteSet;

import IntervalSet.IntervalSet;
import IntervalSet.MultiIntervalSet;
public interface NonOverlapIntervalSet<L>{
    /**
     * 判断需要插入的片段是否和已存在的时间片段重叠
     * @param initialSet 输入一个需要插入一个时间分配的set
     * @param start 需要插入的时间片的开始时间
     * @param end   需要插入时间片的的结束时间
     * @param label 需要插入时间片的标签
     * @throws RuntimeException 如果插入之后存在重叠片段，抛出异常
     */
    public void checkNoOverlap(IntervalSet<L> initialSet, long start, long end, L label)throws Exception;
    /**
     * 判断需要插入的片段是否和已存在的时间片段重叠
     * @param initialSet 输入一个需要插入一个时间分配的set
     * @param start 需要插入的时间片的开始时间
     * @param end   需要插入时间片的的结束时间
     * @throws RuntimeException 如果插入之后存在重叠片段，抛出异常
     */
    public void checkNoOverlap(MultiIntervalSet<L> initialSet, long start, long end)throws Exception;
}
