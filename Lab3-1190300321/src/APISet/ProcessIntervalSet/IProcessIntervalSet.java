package APISet.ProcessIntervalSet;

import IntervalSet.IntervalSet;

import java.util.List;
import java.util.Set;

public interface IProcessIntervalSet<L> {
    /**
     * 计算空闲时间占比
     * @return 空闲时间占比
     */
    public double calcFreeTimeRatio()throws Exception;
    /**
     * 检测安排是否存在重叠，如果存在重叠抛出异常
     * @throws Exception 如果安排中存在重叠，抛出异常
     */
    public void checkNoOverlap()throws Exception;
    /**
     * 插入安排，
     * @param start 开始时间
     * @param end 结束时间
     * @param label 人员
     * @throws IllegalArgumentException 如果start>=end,抛出异常
     */
    public void insert(long start, long end, L label)throws Exception;

    /**
     * 移除某个片段
     * @param label 需要移除的进程label
     */
    public boolean remove(L label)throws Exception;
    /**
     * 获取某个进程的所有安排信息
     * @param label 需要查询的进程的信息
     * @return  返回进程的执行时间
     */
    public IntervalSet<Integer> interval(L label)throws Exception;
    /**
     * 返回所有的进程
     * @return 返回所有的进程
     */
    public Set<L> labels();

    /**
     * 计算某个进程一共执行时间
     * @param label 需要查询的进程
     * @return 总运行时间
     */
    public long totalTime(L label);
}
