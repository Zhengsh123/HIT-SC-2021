package ConcreteSet;

import IntervalSet.IntervalSet;
import IntervalSet.MultiIntervalSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PeriodicIntervalSet<L> {
    /**
     *用来处理周期性的插入
     * @param start 开始时间
     * @param end 结束时间
     * @param label 需要插入的标签
     * @param index 需要插入的标签对应在一个周期中的哪一个部分
     */
    public void insert(long start,long end,L label,int index)throws Exception;
    /**
     用来处理周期性的移除
     * @param label 需要移除的标签
     * @throws IllegalArgumentException 如果不存在这个标签抛出异常
     */
    public boolean remove(L label)throws IllegalArgumentException;
    /**
     * 返回某个标签的在一个周期内的所有安排
     * @param label 需要返回安排的标签
     * @return 返回一个周期内某个标签的安排情况
     */
    public MultiIntervalSet<Integer> interval(L label)throws Exception;
    /**
     * 用来处理某个标签是否具有周期性，周期性延续时间的情况
     * @param start 周期开始的时间,如果start==end表示没有周期性
     * @param end 周期结束的时间
     * @param Label 需要处理的标签
     */
    public void periodArrange(long start,long end,L Label);

    /**
     * 返回某个label的周期性情况
     * @param label 需要返回的label
     * @return 该label的周期性情况
     * @throws Exception 如果不存在这个标签抛出异常
     */
    public Map<L,List<Long>> getPeriodArrange(L label)throws Exception;
    /**
     * 对每一个周期长度的安排
     * @param length 每一个周期的长度
     * @return 返回的List表示在每一个周期内单个片段的分配情况
     */
    public List<MultiIntervalSet<L>> periodLength(int length);
    /**
     * 返回某个标签的所有安排，包括一个周期内以及周期信息
     * @param label 需要查询的标签
     * @return 返回某个标签的所有安排
     * @throws Exception 如果不存在这个标签抛出异常
     */
    public Map<MultiIntervalSet<Integer>,List> getLabelArrange(L label)throws Exception;
    /**
     * 返回所有的label
     * @return 返回label
     */
    public Set<L> labels();

    /**
     * 返回周期内某一天的安排
     * @param index 需要查询的时间
     * @return 返回周期内某一天的安排
     */
    public MultiIntervalSet<L> getDayArrange(int index);
}
