package API;
import IntervalSet.*;
public interface APIs<L> {
    /**
     * 计算两个 MultiIntervalSet 对象的相似度
     * @param s1 需要计算相似度的一个MultiIntervalSet
     * @param s2 需要计算相似度的一个MultiIntervalSet
     * @return 相似度，小数形式
     */
    public double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2)throws Exception;

    /**
     * 计算一个intervalSet中的时间冲突比例
     * @param set 需要计算的intervalSet
     * @return 返回冲突比例
     */
    public double calcConflictRatio(IntervalSet<L> set)throws Exception;

    /**
     * 计算一个MultiIntervalSet中的时间冲突比例
     * @param set 需要计算的MultiIntervalSet
     * @return 返回冲突比例
     */
    public double calcConflictRatio(MultiIntervalSet<L> set)throws Exception;

    /**
     * 计算一个IntervalSet的空闲时间占比
     * @param set intervalSet
     * @return 空闲时间占比
     */
    public double calcFreeTimeRatio(IntervalSet<L> set)throws Exception;

    /**
     * 计算一个IntervalSet的空闲时间占比
     * @param set intervalSet
     * @return 空闲时间占比
     */
    public double calcFreeTimeRatio(MultiIntervalSet<L> set)throws Exception;

    /**
     * 当需要外部指定长度的时候用这个函数
     * @param set intervalSet
     * @param length 指定长度
     * @return 空闲时间占比
     * @throws Exception 参数异常时抛出
     */
    public double calcFreeTimeRatio(IntervalSet<L> set,long length)throws Exception;

    /**
     * 当需要外部指定长度的时候用这个函数
     * @param set intervalSet
     * @param length 指定长度
     * @return 空闲时间占比
     * @throws Exception 参数异常时抛出
     */
    public double calcFreeTimeRatio(MultiIntervalSet<L> set,long length)throws Exception;
}
