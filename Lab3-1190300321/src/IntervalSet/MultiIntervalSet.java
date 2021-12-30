package IntervalSet;

import java.util.Set;

public abstract class MultiIntervalSet<L> {

    /**
     * 静态工厂方法，创建一个空的安排表
     * @param <L>set中每一个对象的类型，应该是immutable的
     * @return 一个空的IntervalSet
     */
    public static <L> MultiIntervalSet<L> empty()
    {
        return new CommonMultiIntervalSet<L>();
    }

    /**
     * 构造函数
     * @param initial 初始参数
     */
    public MultiIntervalSet(IntervalSet<L> initial) {
        Set<L> keySet=initial.labels();
        for(L key:keySet)
        {
            try
            {
                this.insert(initial.start(key),initial.end(key),key);
            }catch (Exception e)
            {
                System.out.println("参数不正确");
                e.printStackTrace();
            }
        }
    }
    public MultiIntervalSet()
    {}
    /**
     * 在当前对象中插入一个新的标签和时间段，start和end都应该是非负值且应该满足start<end
     * @param start 时间段的开始时间
     * @param end 时间段的结束时间
     * @param label 插入的标签
     */
     abstract public void insert(long start,long end,L label)throws Exception;
    /**
     *
     * @return 获取当前对象的所有的标签
     */
    abstract public Set<L> labels();
    /**
     * 移除当前对象中的某一个标签
     * 并输出“错误，标签不存在”
     * @param label 需要移除的标签的名称
     * @return 如果标签存在则移除标签并返回true，如果标签不存在返回false
     */
    abstract public boolean remove(L label);
    abstract public IntervalSet<Integer> intervals(L label)throws Exception;

}
