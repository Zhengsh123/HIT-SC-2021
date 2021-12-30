package APISet.DutyIntervalSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
public class DutyIntervalSetTest {
    @Test
    /*
     * Testing strategy:
     * 输入起止时间:start==end,start<end,start>end
     */
    public void dutyIntervalSetTest()throws Exception
    {
        //start>end
        String startTime1="2021-01-10";
        String endTime1="2021-01-09";
        //assertThrows(AssertionError.class,()->new DutyIntervalSet<>(startTime1,endTime1));
        //start==end
        String endTime2="2021-01-10";
        DutyIntervalSet<String> dutyIntervalSet1=new DutyIntervalSet<>(startTime1,endTime2);
        assertEquals("需要安排的日期为：2021-01-10————2021-01-10\n" +
                "已经安排的日期及每天安排人为：\n",dutyIntervalSet1.toString());
        //start<end
        String endTime3="2021-02-01";
        DutyIntervalSet<String> dutyIntervalSet2=new DutyIntervalSet<>(startTime1,endTime3);
        assertEquals("需要安排的日期为：2021-01-10————2021-02-01\n" +
                "已经安排的日期及每天安排人为：\n",dutyIntervalSet2.toString());
    }
    @Test
    /*
     * Testing strategy:
     * 是否存在空白：是，否
     */
    public void checkNoBlankTest()throws Exception
    {
        String startTime="2021-01-10";
        String endTime="2021-01-15";
        DutyIntervalSet<String> dutyIntervalSet=new DutyIntervalSet<>(startTime,endTime);
        dutyIntervalSet.insert(0,0,"张三");
        dutyIntervalSet.insert(1,1,"李四");
        //存在空白
        assertThrows(Exception.class,()->dutyIntervalSet.checkNoBlank());
        //不存在空白
        dutyIntervalSet.insert(2,5,"王五");
        assertEquals("需要安排的日期为：2021-01-10————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-10--张三\n" +
                "2021-01-11--李四\n" +
                "2021-01-12--王五\n" +
                "2021-01-13--王五\n" +
                "2021-01-14--王五\n" +
                "2021-01-15--王五\n",dutyIntervalSet.toString());
        dutyIntervalSet.checkNoBlank();
    }
    @Test
    /*
     * Testing strategy:
     * 插入的时间：start>end,start<=end
     * 插入的人是否连续多天：是，否
     * 是否有一天插入多个人：是，否
     */
    public void insertTest()throws Exception
    {
        String startTime="2021-01-10";
        String endTime="2021-01-15";
        DutyIntervalSet<String> dutyIntervalSet=new DutyIntervalSet<>(startTime,endTime);
        //start>end
        assertThrows(IllegalArgumentException.class,()->dutyIntervalSet.insert(2,1,"A"));
        //start<=end
        dutyIntervalSet.insert(0,0,"A");
        assertEquals("需要安排的日期为：2021-01-10————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-10--A\n",dutyIntervalSet.toString());
        //插入的人连续多天
        dutyIntervalSet.insert(1,3,"B");
        assertEquals("需要安排的日期为：2021-01-10————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-10--A\n" +
                "2021-01-11--B\n" +
                "2021-01-12--B\n" +
                "2021-01-13--B\n",dutyIntervalSet.toString());
        //一个人连续插入多次
        dutyIntervalSet.insert(4,4,"A");
        assertEquals("需要安排的日期为：2021-01-10————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-11--B\n" +
                "2021-01-12--B\n" +
                "2021-01-13--B\n" +
                "2021-01-14--A\n",dutyIntervalSet.toString());
        //一天插入多个人
        assertThrows(Exception.class,()->dutyIntervalSet.insert(4,4,"C"));
    }
    @Test
    /*
     *Testing Strategy:
     * 需要查询的人是否存在：是，否
     */
    public void intervalTest()throws Exception
    {
        String startTime="2021-01-10";
        String endTime="2021-01-15";
        DateCaculate testDC=new DateCaculateImpl();
        DutyIntervalSet<String> dutyIntervalSet=new DutyIntervalSet<>(startTime,endTime);
        //需要查询的人不存在
        assertThrows(IllegalArgumentException.class,()->dutyIntervalSet.interval("A"));
        //需要查询的人存在
        dutyIntervalSet.insert(0,5,"A");
        List<String>dutyList=dutyIntervalSet.interval("A");
        List<String>testList=new ArrayList<>();
        for(int i=0;i<=5;i++)
        {
            testList.add(testDC.add(startTime,i));
        }
        assertEquals(testList,dutyList);
    }
    @Test
    /*
     * Testing strategy:
     * 需要删除的人是否存在：存在，不存在
     */
    public void removeTest()throws Exception
    {
        String startTime="2021-01-10";
        String endTime="2021-01-15";
        DateCaculate testDC=new DateCaculateImpl();
        DutyIntervalSet<String> dutyIntervalSet=new DutyIntervalSet<>(startTime,endTime);
        dutyIntervalSet.insert(0,0,"A");
        assertEquals("需要安排的日期为：2021-01-10————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-10--A\n",dutyIntervalSet.toString());
        //需要删除的人不存在
        dutyIntervalSet.remove("B");
        assertEquals("需要安排的日期为：2021-01-10————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-10--A\n",dutyIntervalSet.toString());
        //需要删除的人存在
        dutyIntervalSet.remove("A");
        assertEquals("需要安排的日期为：2021-01-10————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n",dutyIntervalSet.toString());
    }
}
