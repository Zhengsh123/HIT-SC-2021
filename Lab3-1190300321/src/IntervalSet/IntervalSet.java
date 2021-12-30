package IntervalSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IntervalSet<L> {
    /**
     * 静态工厂方法，创建一个空的安排表
     * @param <L>set中每一个对象的类型，应该是immutable的
     * @return 一个空的IntervalSet
     */
    public static <L> IntervalSet<L> empty() { return new CommonIntervalSet<L>();}
    /**
     * 在当前对象中插入一个新的标签和时间段，start和end都应该是非负值且应该满足start<end
     * 如果label已经存在，更新其关联的时间片段
     * @param start 时间段的开始时间
     * @param end 时间段的结束时间
     * @param label 插入的标签
     */
    public void insert(long start,long end,L label)throws Exception;
    /**
     *
     * @return 获取当前对象的所有的标签
     */
    public Set<L> labels();
    /**
     * 移除当前对象中的某一个标签
     * 并输出“错误，标签不存在”
     * @param label 需要移除的标签的名称
     * @return 如果标签存在则移除标签并返回true，如果标签不存在返回false
     */
    public boolean remove(L label)throws Exception;
    /**
     * 获取某个标签对应的开始时间
     * @param label 需要获取开始时间的标签
     * @return 如果输入的标签不存在，返回-1，并输出“错误，标签不存在”;
     * 如果标签存在，返回该标签的开始时间
     */
    public long start(L label);
    /**
     * 获取某个标签对应的结束时间
     * @param label 需要获取结束时间的标签
     * @return 如果输入的标签不存在，返回-1，并输出“错误，标签不存在”;
     * 如果标签存在，返回该标签的开始时间
     */
    public long end(L label);

    /**
     * 获取当前IntervalSet的所有分配信息
     * @return 返回当前IntervalSet的所有分配信息
     */
    public Map<L, List<Long>> intervals();

    /**
     * 返回这个Set中的开始时间
     * @return 返回这个Set中的开始时间
     */
    public long getStart();
    /**
     * 返回这个Set中的结束时间
     * @return 返回这个Set中的结束时间
     */
    public long getEnd();

}
