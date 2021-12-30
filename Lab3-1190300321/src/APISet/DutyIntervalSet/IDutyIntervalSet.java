package APISet.DutyIntervalSet;
import ConcreteSet.*;
import IntervalSet.IntervalSet;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface IDutyIntervalSet<L>
{
    /**
     * 检测安排是否存在空白，如果存在空白抛出异常
     * @throws Exception 如果安排中存在空白，抛出异常
     */
    public void checkNoBlank()throws Exception;
    /**
     * 插入安排，如果安排仅有一天那么start==end
     * @param start 值班开始时间
     * @param end 值班结束时间
     * @param label 值班人员
     * @throws IllegalArgumentException 如果start>end,抛出异常
     */
    public void insert(long start, long end, L label)throws Exception;

    /**
     * 移除某个值班人员
     * @param label 需要移除的值班人员的名字
     */
    public boolean remove(L label)throws Exception;
    /**
     * 获取某个值班人员的所有安排信息
     * @param label 需要查询的值班人员的信息
     * @return  返回值班人员的排班时间
     */
    public List<String> interval(L label)throws Exception;

    /**
     * 返回所有有安排的员工
     * @return 返回所有有安排的员工
     */
    public Set<L> labels();

    /**
     * 计算空闲时间占比
     * @return 空闲时间占比
     */
    public double calcFreeTimeRatio()throws Exception;
}
