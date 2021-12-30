package APISet.CourseIntervalSet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourseIntervalSetTest {
    @Test
    public void insertTest()
    {
        IcourseIntervalSet<String>courseIntervalSet=new CourseIntervalSet<>();
        assertEquals("所有具有周期性的课程的周期性为：\n" +
                "课表空闲率为：1.0\n" +
                "周内课程安排信息为：\n" +
                "第1天的安排\n" +
                "{}\n" +
                "第2天的安排\n" +
                "{}\n" +
                "第3天的安排\n" +
                "{}\n" +
                "第4天的安排\n" +
                "{}\n" +
                "第5天的安排\n" +
                "{}\n" +
                "第6天的安排\n" +
                "{}\n" +
                "第7天的安排\n" +
                "{}\n",courseIntervalSet.toString());
        try
        {
            courseIntervalSet.setPeriod("A",1,18);
            courseIntervalSet.insert(8,10,"A",1);
            assertEquals("所有具有周期性的课程的周期性为：\n" +
                    "A:1周-18周\n" +
                    "课表空闲率为：0.9714285714285714\n" +
                    "周内课程安排信息为：\n" +
                    "第1天的安排\n" +
                    "{}\n" +
                    "第2天的安排\n" +
                    "{A=[[8, 10]]}\n" +
                    "第3天的安排\n" +
                    "{}\n" +
                    "第4天的安排\n" +
                    "{}\n" +
                    "第5天的安排\n" +
                    "{}\n" +
                    "第6天的安排\n" +
                    "{}\n" +
                    "第7天的安排\n" +
                    "{}\n",courseIntervalSet.toString());
            courseIntervalSet.insert(8,10,"A",2);
            assertEquals("所有具有周期性的课程的周期性为：\n" +
                    "A:1周-18周\n" +
                    "课表空闲率为：0.9428571428571428\n" +
                    "周内课程安排信息为：\n" +
                    "第1天的安排\n" +
                    "{}\n" +
                    "第2天的安排\n" +
                    "{A=[[8, 10]]}\n" +
                    "第3天的安排\n" +
                    "{A=[[8, 10]]}\n" +
                    "第4天的安排\n" +
                    "{}\n" +
                    "第5天的安排\n" +
                    "{}\n" +
                    "第6天的安排\n" +
                    "{}\n" +
                    "第7天的安排\n" +
                    "{}\n",courseIntervalSet.toString());

        }catch (Exception e)
        {
            System.out.println(" ");
        }
    }

    @Test
    public void setPeriodTest()
    {
        IcourseIntervalSet<String>courseIntervalSet=new CourseIntervalSet<>();
        assertEquals("所有具有周期性的课程的周期性为：\n" +
                "课表空闲率为：1.0\n" +
                "周内课程安排信息为：\n" +
                "第1天的安排\n" +
                "{}\n" +
                "第2天的安排\n" +
                "{}\n" +
                "第3天的安排\n" +
                "{}\n" +
                "第4天的安排\n" +
                "{}\n" +
                "第5天的安排\n" +
                "{}\n" +
                "第6天的安排\n" +
                "{}\n" +
                "第7天的安排\n" +
                "{}\n",courseIntervalSet.toString());
        courseIntervalSet.setPeriod("A",1,18);
        assertEquals("所有具有周期性的课程的周期性为：\n" +
                "A:1周-18周\n" +
                "课表空闲率为：1.0\n" +
                "周内课程安排信息为：\n" +
                "第1天的安排\n" +
                "{}\n" +
                "第2天的安排\n" +
                "{}\n" +
                "第3天的安排\n" +
                "{}\n" +
                "第4天的安排\n" +
                "{}\n" +
                "第5天的安排\n" +
                "{}\n" +
                "第6天的安排\n" +
                "{}\n" +
                "第7天的安排\n" +
                "{}\n",courseIntervalSet.toString());
        courseIntervalSet.setPeriod("B",1,18);
        assertEquals("所有具有周期性的课程的周期性为：\n" +
                "A:1周-18周\n" +
                "B:1周-18周\n" +
                "课表空闲率为：1.0\n" +
                "周内课程安排信息为：\n" +
                "第1天的安排\n" +
                "{}\n" +
                "第2天的安排\n" +
                "{}\n" +
                "第3天的安排\n" +
                "{}\n" +
                "第4天的安排\n" +
                "{}\n" +
                "第5天的安排\n" +
                "{}\n" +
                "第6天的安排\n" +
                "{}\n" +
                "第7天的安排\n" +
                "{}\n",courseIntervalSet.toString());
    }
    @Test
    public void totalTimeTest()
    {
        IcourseIntervalSet<String>courseIntervalSet=new CourseIntervalSet<>();
        courseIntervalSet.setPeriod("A",1,18);
        assertEquals(0,courseIntervalSet.totalTime("A"));
        try
        {
            courseIntervalSet.insert(8,10,"A",1);
            assertEquals(2,courseIntervalSet.totalTime("A"));
            courseIntervalSet.insert(8,10,"A",2);
            assertEquals(4,courseIntervalSet.totalTime("A"));
        }catch (Exception e)
        {
            System.out.println(" ");
        }
    }
    @Test
    public void intervalTest()
    {
        IcourseIntervalSet<String>courseIntervalSet=new CourseIntervalSet<>();
        courseIntervalSet.setPeriod("A",1,18);
        assertEquals("{}",courseIntervalSet.interval("A").toString());
        try
        {
            courseIntervalSet.insert(8,10,"A",1);
            assertEquals("{1=[[8, 10]]}",courseIntervalSet.interval("A").toString());
        }catch (Exception e)
        {
            System.out.println(" ");
        }
        assertEquals("{}",courseIntervalSet.interval("B").toString());
    }
}
