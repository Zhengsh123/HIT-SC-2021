package ConcreteSet;

import IntervalSet.IntervalSet;
import IntervalSet.MultiIntervalSet;
public interface NoBlankIntervalSet<L> {
    /**
     * 检测时间段上是否存在空白
     * @param initialSet 需要检测的intervalSet
     * @param start 这一段时间的开始时间,开始时间小于结束时间
     * @param end 这一段时间的结束时间
     * @throws Exception 如果存在空白，抛出异常
     */
    public void checkNoBlank(IntervalSet<L> initialSet,long start,long end)throws Exception;
    /**
     * 检测时间段上是否存在空白
     * @param initialSet 需要检测的MultiIntervalSet
     * @param start 这一段时间的开始时间，开始时间小于结束时间
     * @param end 这一段时间的结束时间
     * @throws Exception 如果存在空白，抛出异常
     */
    public void checkNoBlank(MultiIntervalSet<L> initialSet,long start,long end)throws Exception;
}
