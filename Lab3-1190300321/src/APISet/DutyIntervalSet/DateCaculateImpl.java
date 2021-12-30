package APISet.DutyIntervalSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCaculateImpl implements DateCaculate
{
    @Override
    public long sub(String start,String end){
        try
        {
            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Date star = dft.parse(start);//开始时间
            Date endDay=dft.parse(end);//结束时间
            Long starTime=star.getTime();
            Long endTime=endDay.getTime();
            return (endTime-starTime)/24/60/60/1000;
        }catch (ParseException e)
        {
            System.out.println("输入时间格式错误");
        }
        return -1;
    }

    public String add(String startDate,int length)
    {
        String s=" ";
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date star = dft.parse(startDate);//开始时间
            Date nextDay=star;
            int i=0;
            while(i<length){//当明天不在结束时间之前是终止循环
                Calendar cld = Calendar.getInstance();
                cld.setTime(star);
                cld.add(Calendar.DATE, 1);
                star = cld.getTime();
                //获得下一天日期字符串
                nextDay = star;
                i++;
            }
            s=dft.format(nextDay.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

}
