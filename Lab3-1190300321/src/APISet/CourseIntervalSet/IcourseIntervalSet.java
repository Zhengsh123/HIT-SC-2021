package APISet.CourseIntervalSet;

import API.APIs;
import API.APIsImpl;
import IntervalSet.MultiIntervalSet;

import java.util.Set;

public interface IcourseIntervalSet<L>  {
    /**
     * 插入安排
     * @param start 课程开始时间
     * @param end 课程结束时间
     * @param label 课程名称
     * @throws IllegalArgumentException 如果start>end,抛出异常
     */
    public void insert(long start, long end, L label,int index)throws Exception;

    /**
     * 设置某门课程的周期性
     * @param label 课程
     * @param start 周期开始时间
     * @param end 周期结束时间
     */
    public void setPeriod(L label,long start,long end);

    /**
     * 返回某门课程在某个周期内已经安排的时长
     * @param label 课程
     * @return 已经安排时长
     */
    public long totalTime(L label);
    /**
     * 返回某个周期内某门课程的安排
     * @param label 课程
     * @return 课程安排
     */
    public MultiIntervalSet<Integer> interval(L label);
    /**
     * 计算空闲时间比
     * @return 空闲时间占比
     * @throws Exception 参数异常时抛出异常
     */
    public double calcFreeTimeRatio()throws Exception;

    /**
     * 获取label集合
     * @return label集合
     */
    public Set<L>labels();

    /**
     * 获取某一天的课表
     * @param index 星期几
     * @return 某一天的课表
     */
    public MultiIntervalSet<L>getDayArrange(int index);
}
