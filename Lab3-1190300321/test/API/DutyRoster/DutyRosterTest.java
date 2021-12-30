package API.DutyRoster;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class DutyRosterTest {
    @Test
    /*
     * Testing strategy:
     * 输入的时间是否满足格式要求：是，否
     * 输入的时间是否满足start<=end
     *
     */
    public void setTableTest()throws Exception
    {
        //输入时间不满足格式要求
        String startTime1="2020-1-1";
        String endTime1="11-1";
        DutyRoster dr=new DutyRosterApp();
        dr.setTable(startTime1,endTime1);
        //输入时间满足格式要求
        String startTime2="2020-01-01";
        String endTime2="2020-02-01";
        dr.setTable(startTime2,endTime2);
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2020-01-01————2020-02-01\n" +
                "已经安排的日期及每天安排人为：\n",dr.toString());
        //输入时间不满足start<=end
        String startTime3="2020-02-01";
        String endTime3="2020-01-01";
        assertFalse(dr.setTable(startTime3,endTime3));
    }
    @Test
    /*
     * Testing Strategy：
     * 输入的人是否已经存在：是，否
     * 输入的电话号码格式是否满足要求：是，否
     */
    public void addEmployeeTest()throws Exception
    {
        DutyRoster dr=new DutyRosterApp();
        dr.setTable("2021-01-01","2021-01-20");
        //输入的人不存在
        dr.addEmployee("ZhangSan","Manager","111-1111-1111");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n",dr.toString());
        //输入的人存在
        dr.addEmployee("ZhangSan","Manager","111-1111-1112");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n",dr.toString());
        //输入的电话号码格式不符合要求
        assertThrows(IllegalArgumentException.class,()->dr.addEmployee("LiSi","Manager","111"));
    }
    @Test
    /*
     * Testing Strategy:
     * 安排是否排满：是，否
     * 是否是自动安排：是，否
     */
    public void setRosterTest()throws Exception {
        DutyRoster dr = new DutyRosterApp();
        dr.setTable("2021-01-01", "2021-01-20");
        dr.addEmployee("ZhangSan", "Manager", "111-1111-1111");
        dr.addEmployee("LiSi", "Secretary", "222-2222-2222");
        dr.addEmployee("WangWu", "WangWu", "333-3333-3333");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n", dr.toString());
        //手动安排
        dr.setRoster("ZhangSan", "2021-01-01", "2021-01-19");
        //未排满
        assertEquals("这一排班表空闲率为：0.05\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-01--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-02--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-03--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-04--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-05--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-06--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-07--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-08--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-09--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-10--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-11--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-12--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-13--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-14--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-15--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-16--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-17--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-18--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-19--ZhangSan    Manager   111-1111-1111\n", dr.toString());
        dr.setRoster("LiSi", "2021-01-20", "2021-01-20");
        //排满
        assertEquals("这一排班表已经排满，详细信息如下：\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-01--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-02--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-03--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-04--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-05--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-06--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-07--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-08--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-09--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-10--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-11--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-12--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-13--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-14--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-15--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-16--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-17--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-18--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-19--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-20--LiSi    Secretary   222-2222-2222\n", dr.toString());
        //自动安排
        dr = new DutyRosterApp();
        dr.setTable("2021-01-01", "2021-01-15");
        dr.addEmployee("ZhangSan", "Manager", "111-1111-1111");
        dr.addEmployee("LiSi", "Secretary", "222-2222-2222");
        dr.addEmployee("WangWu", "Associate Dean", "333-3333-3333");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-15\n" +
                "已经安排的日期及每天安排人为：\n", dr.toString());
        dr.setRoster();
    }
    @Test
    /*
     * Testing Strategy：
     * 需要删除的人是否存在：是，否
     *
     */
    public void removeTest()throws Exception
    {
        DutyRoster dr=new DutyRosterApp();
        dr.setTable("2021-01-01","2021-01-20");
        dr.addEmployee("ZhangSan","Manager","111-1111-1111");
        dr.addEmployee("LiSi","Secretary","222-2222-2222");
        dr.addEmployee("WangWu","WangWu","333-3333-3333");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n",dr.toString());
        dr.setRoster("ZhangSan","2021-01-01","2021-01-19");
        assertEquals("这一排班表空闲率为：0.05\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-01--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-02--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-03--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-04--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-05--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-06--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-07--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-08--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-09--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-10--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-11--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-12--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-13--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-14--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-15--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-16--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-17--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-18--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-19--ZhangSan    Manager   111-1111-1111\n",dr.toString());
        //需要删除的人不存在
        dr.remove("B");
        assertEquals("这一排班表空闲率为：0.05\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-01--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-02--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-03--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-04--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-05--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-06--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-07--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-08--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-09--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-10--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-11--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-12--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-13--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-14--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-15--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-16--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-17--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-18--ZhangSan    Manager   111-1111-1111\n" +
                "2021-01-19--ZhangSan    Manager   111-1111-1111\n",dr.toString());
        //需要删除的人存在
        dr.remove("ZhangSan");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n",dr.toString());
    }
    @Test
    /*
     * Testing Strategy:
     * 值班表是否排满：是，否
     */
    public void isFullTest()throws Exception
    {
        DutyRoster dr=new DutyRosterApp();
        dr.setTable("2021-01-01","2021-01-25");
        dr.addEmployee("ZhangSan","Manager","111-1111-1111");
        dr.addEmployee("LiSi","Secretary","222-2222-2222");
        dr.addEmployee("WangWu","WangWu","333-3333-3333");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-25\n" +
                "已经安排的日期及每天安排人为：\n",dr.toString());
        dr.setRoster("ZhangSan","2021-01-01","2021-01-19");
        //未排满
        assertEquals(false,dr.isFull());
        //排满
        dr.setRoster("LiSi","2021-01-20","2021-01-25");
        assertEquals(true,dr.isFull());
    }
    @Test
    /*
     * Testing Strategy:
     * 值班表是否排满：是，否
     * 值班表是否是空表：是，否
     */
    public void calcFreeTimeRatio()throws Exception
    {
        DutyRoster dr=new DutyRosterApp();
        dr.setTable("2021-01-01","2021-01-20");
        dr.addEmployee("ZhangSan","Manager","111-1111-1111");
        dr.addEmployee("LiSi","Secretary","222-2222-2222");
        dr.addEmployee("WangWu","WangWu","333-3333-3333");
        assertEquals("这一排班表空闲率为：1.0\n" +
                "需要安排的日期为：2021-01-01————2021-01-20\n" +
                "已经安排的日期及每天安排人为：\n",dr.toString());
        //空表
        assertEquals(1.0,dr.calcFreeTimeRatio(),0.001);
        dr.setRoster("ZhangSan","2021-01-01","2021-01-19");
        //未排满
        assertEquals(0.05,dr.calcFreeTimeRatio(),0.001);
        //排满
        dr.setRoster("LiSi","2021-01-20","2021-01-20");
        assertEquals(0,dr.calcFreeTimeRatio(),0.001);
    }
    @Test
    /*
     * 需要获取的人是否存在:是，否
     */
    public void getPosition()throws Exception
    {

        DutyRoster dr=new DutyRosterApp();

        dr.addEmployee("ZhangSan","Manager","111-1111-1111");
        dr.addEmployee("LiSi","Secretary","222-2222-2222");
        dr.addEmployee("WangWu","WangWu","333-3333-3333");
        //需要获取的人存在
        assertEquals("Manager",dr.getPosition("ZhangSan"));
        //需要获取的人不存在
        assertEquals("",dr.getPosition("ZhaoLiu"));
    }
    @Test
    /*
     * 需要获取的人是否存在:是，否
     */
    public void getPhoneNumber()throws Exception
    {
        DutyRoster dr=new DutyRosterApp();
        dr.addEmployee("ZhangSan","Manager","111-1111-1111");
        dr.addEmployee("LiSi","Secretary","222-2222-2222");
        dr.addEmployee("WangWu","WangWu","333-3333-3333");
        //需要获取的人存在
        assertEquals("111-1111-1111",dr.getPhoneNumber("ZhangSan"));
        //需要获取的人不存在
        assertEquals("",dr.getPhoneNumber("ZhaoLiu"));
    }
    @Test
    public void readFromFile()throws Exception
    {
        DutyRoster dr1=new DutyRosterApp();
        dr1.readFromFile("test\\API\\DutyRoster\\test1.txt");
        assertEquals("这一排班表已经排满，详细信息如下：\n" +
                "需要安排的日期为：2021-01-10————2021-03-06\n" +
                "已经安排的日期及每天安排人为：\n" +
                "2021-01-10--ZhangSan    Manger   139-0451-0000\n" +
                "2021-01-11--ZhangSan    Manger   139-0451-0000\n" +
                "2021-01-12--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-13--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-14--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-15--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-16--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-17--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-18--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-19--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-20--LiSi    Secretary   151-0101-0000\n" +
                "2021-01-21--WangWu    Associate Dean   177-2021-0301\n" +
                "2021-01-22--ZhaoLiua    Professor   138-1920-3912\n" +
                "2021-01-23--ZhaoLiub    Lecturer   138-1921-3912\n" +
                "2021-01-24--ZhaoLiub    Lecturer   138-1921-3912\n" +
                "2021-01-25--ZhaoLiub    Lecturer   138-1921-3912\n" +
                "2021-01-26--ZhaoLiub    Lecturer   138-1921-3912\n" +
                "2021-01-27--ZhaoLiub    Lecturer   138-1921-3912\n" +
                "2021-01-28--ZhaoLiub    Lecturer   138-1921-3912\n" +
                "2021-01-29--ZhaoLiub    Lecturer   138-1921-3912\n" +
                "2021-01-30--ZhaoLiuc    Professor   138-1922-3912\n" +
                "2021-01-31--ZhaoLiuc    Professor   138-1922-3912\n" +
                "2021-02-01--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-02--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-03--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-04--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-05--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-06--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-07--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-08--ZhaoLiud    Lecturer   198-1920-3912\n" +
                "2021-02-09--ZhaoLiue    Professor   178-1920-3912\n" +
                "2021-02-10--ZhaoLiue    Professor   178-1920-3912\n" +
                "2021-02-11--ZhaoLiue    Professor   178-1920-3912\n" +
                "2021-02-12--ZhaoLiue    Professor   178-1920-3912\n" +
                "2021-02-13--ZhaoLiue    Professor   178-1920-3912\n" +
                "2021-02-14--ZhaoLiue    Professor   178-1920-3912\n" +
                "2021-02-15--ZhaoLiue    Professor   178-1920-3912\n" +
                "2021-02-16--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-17--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-18--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-19--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-20--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-21--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-22--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-23--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-24--ZhaoLiuf    Lecturer   138-1929-3912\n" +
                "2021-02-25--ZhaoLiug    Professor   138-1920-0000\n" +
                "2021-02-26--ZhaoLiug    Professor   138-1920-0000\n" +
                "2021-02-27--ZhaoLiug    Professor   138-1920-0000\n" +
                "2021-02-28--ZhaoLiug    Professor   138-1920-0000\n" +
                "2021-03-01--ZhaoLiuh    AssciateProfessor   138-1929-0000\n" +
                "2021-03-02--ZhaoLiui    Professor   138-1920-0200\n" +
                "2021-03-03--ZhaoLiui    Professor   138-1920-0200\n" +
                "2021-03-04--ZhaoLiui    Professor   138-1920-0200\n" +
                "2021-03-05--ZhaoLiuj    AssciateProfessor   138-1920-0044\n" +
                "2021-03-06--ZhaoLiuk    Professor   188-1920-0000\n",dr1.toString());
    }
}
