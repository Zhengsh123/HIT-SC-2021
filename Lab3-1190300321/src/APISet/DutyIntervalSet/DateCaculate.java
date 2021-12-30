package APISet.DutyIntervalSet;

import java.text.ParseException;

public interface DateCaculate {
    /**
     * 计算两个输入时间之间的天数，日期格式均为类似2000-01-01
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 中间间隔天数
     * @throws ParseException 出现异常时抛出异常
     */
    public long sub(String startDate,String endDate);

    /**
     * 计算某个日期加上几天之后日期的String格式，格式为类似2000-01-01
     * @param startDate 开始日期
     * @param length 需要加的天数
     * @return 返回最后得到的日期的String格式
     * @throws ParseException 出现异常时抛出异常
     */
    public String add(String startDate,int length);
}
