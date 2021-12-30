package API.CourseSchedule;

import org.junit.Test;
import static org.junit.Assert.*;
public class CourseScheduleTest {
    @Test
    public void addCourse()
    {
        CourseScheduleAPP courseScheduleAPP=new CourseScheduleAPP();
        courseScheduleAPP.setWeek("2021-03-09",18);
        Course c1=new Course("111","A","a","a1",18);
        Course c2=new Course("222","B","b","b1",10);
        assertEquals("学期开始时间：2021-03-09 学期周数18\n" +
                "所有课程信息：\n" +
                "所有具有周期性的课程的周期性为：\n" +
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
                "{}\n",courseScheduleAPP.toString());
        courseScheduleAPP.addCourse(c1);
        assertEquals("学期开始时间：2021-03-09 学期周数18\n" +
                "所有课程信息：\n" +
                "111\tA\n" +
                "所有具有周期性的课程的周期性为：\n" +
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
                "{}\n",courseScheduleAPP.toString());
        courseScheduleAPP.addCourse(c2);
        assertEquals("学期开始时间：2021-03-09 学期周数18\n" +
                "所有课程信息：\n" +
                "111\tA\n" +
                "222\tB\n" +
                "所有具有周期性的课程的周期性为：\n" +
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
                "{}\n",courseScheduleAPP.toString());
    }
    @Test
    public void setWeek()
    {
        CourseScheduleAPP courseScheduleAPP=new CourseScheduleAPP();
        courseScheduleAPP.setWeek("2021-03-09",18);
        assertEquals("学期开始时间：2021-03-09 学期周数18\n" +
                "所有课程信息：\n" +
                "所有具有周期性的课程的周期性为：\n" +
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
                "{}\n",courseScheduleAPP.toString());
    }
    @Test
    public void setCourse()
    {
        CourseScheduleAPP courseScheduleAPP=new CourseScheduleAPP();
        courseScheduleAPP.setWeek("2021-03-09",18);
        Course c1=new Course("111","A","a","a1",18);
        Course c2=new Course("222","B","b","b1",10);
        courseScheduleAPP.addCourse(c1);
        courseScheduleAPP.addCourse(c2);
        assertEquals("学期开始时间：2021-03-09 学期周数18\n" +
                "所有课程信息：\n" +
                "111\tA\n" +
                "222\tB\n" +
                "所有具有周期性的课程的周期性为：\n" +
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
                "{}\n",courseScheduleAPP.toString());
        courseScheduleAPP.setCourse("111",8,10,1);
        courseScheduleAPP.setCourse("222",8,10,2);
        assertEquals("学期开始时间：2021-03-09 学期周数18\n" +
                "所有课程信息：\n" +
                "111\tA\n" +
                "222\tB\n" +
                "所有具有周期性的课程的周期性为：\n" +
                "课表空闲率为：0.9428571428571428\n" +
                "周内课程安排信息为：\n" +
                "第1天的安排\n" +
                "{}\n" +
                "第2天的安排\n" +
                "{111\tA\n" +
                "=[[8, 10]]}\n" +
                "第3天的安排\n" +
                "{222\tB\n" +
                "=[[8, 10]]}\n" +
                "第4天的安排\n" +
                "{}\n" +
                "第5天的安排\n" +
                "{}\n" +
                "第6天的安排\n" +
                "{}\n" +
                "第7天的安排\n" +
                "{}\n",courseScheduleAPP.toString());

    }
    @Test
    public void isArranged()
    {
        CourseScheduleAPP courseScheduleAPP=new CourseScheduleAPP();
        courseScheduleAPP.setWeek("2021-03-09",18);
        Course c1=new Course("111","A","a","a1",2);
        Course c2=new Course("222","B","b","b1",10);
        courseScheduleAPP.addCourse(c1);
        courseScheduleAPP.setCourse("111",8,10,1);
        assertFalse(courseScheduleAPP.isArranged("222"));
        assertTrue(courseScheduleAPP.isArranged("111"));
    }
    @Test
    public void calcFreeTimeRatio()
    {
        CourseScheduleAPP courseScheduleAPP=new CourseScheduleAPP();
        courseScheduleAPP.setWeek("2021-03-09",18);
        Course c1=new Course("111","A","a","a1",18);
        Course c2=new Course("222","B","b","b1",10);
        courseScheduleAPP.addCourse(c1);
        courseScheduleAPP.setCourse("111",8,10,1);
        assertEquals(0.9714285714285714,courseScheduleAPP.calcFreeTimeRatio(),0.001);
    }
    @Test
    public void checkArrange()
    {
        CourseScheduleAPP courseScheduleAPP=new CourseScheduleAPP();
        courseScheduleAPP.setWeek("2021-03-09",18);
        Course c1=new Course("111","A","a","a1",18);
        Course c2=new Course("222","B","b","b1",10);
        courseScheduleAPP.addCourse(c1);
        courseScheduleAPP.addCourse(c2);
        courseScheduleAPP.setCourse("111",8,10,1);
        assertEquals("{111\tA\n" +
                "=[[8, 10]]}",courseScheduleAPP.checkArrange(1,1).toString());
        assertEquals("{}",courseScheduleAPP.checkArrange(1,2).toString());
    }
}
