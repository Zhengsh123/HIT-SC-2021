package APISet.ProcessIntervalSet;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProcessIntervalSetTest {
    @Test
    public void calcFreeTimeRatioTest()throws Exception
    {
        IProcessIntervalSet<String> processIntervalSet=new ProcessIntervalSet<>();
        //空表
        assertEquals(1,processIntervalSet.calcFreeTimeRatio(),0.001);
        processIntervalSet.insert(0,10,"A");
        //满
        assertEquals(0,processIntervalSet.calcFreeTimeRatio(),0.001);
        //不满
        processIntervalSet.insert(15,20,"B");
        assertEquals(0.25,processIntervalSet.calcFreeTimeRatio(),0.001);
    }
    @Test
    public void checkNoOverlapTest()throws Exception
    {
        IProcessIntervalSet<String> processIntervalSet=new ProcessIntervalSet<>();
    }
    @Test
    public void insertTest()throws Exception
    {
        IProcessIntervalSet<String> processIntervalSet=new ProcessIntervalSet<>();
        assertEquals("详细安排如下：\n" +
                "安排表空闲率：1.0",processIntervalSet.toString());
        //插入的进程不存在
        processIntervalSet.insert(0,10,"A");
        assertEquals("详细安排如下：\n" +
                "0——10:A\n" +
                "安排表空闲率：0.0",processIntervalSet.toString());
        //插入的进程存在
        processIntervalSet.insert(15,20,"A");
        assertEquals("详细安排如下：\n" +
                "0——10:A\n" +
                "15——20:A\n" +
                "安排表空闲率：0.25",processIntervalSet.toString());
    }
    @Test
    public void removeTest()throws Exception
    {
        IProcessIntervalSet<String> processIntervalSet=new ProcessIntervalSet<>();
        assertEquals("详细安排如下：\n" +
                "安排表空闲率：1.0",processIntervalSet.toString());
        //移除的进程存在
        processIntervalSet.insert(0,10,"A");
        processIntervalSet.remove("A");
        assertEquals("详细安排如下：\n" +
                "安排表空闲率：1.0",processIntervalSet.toString());
        //移除的进程不存在
        processIntervalSet.insert(0,10,"A");
        processIntervalSet.remove("B");
        assertEquals("详细安排如下：\n" +
                "0——10:A\n" +
                "安排表空闲率：0.0",processIntervalSet.toString());
    }
    @Test
    public void intervalsTest()throws Exception
    {
        IProcessIntervalSet<String> processIntervalSet=new ProcessIntervalSet<>();
        processIntervalSet.insert(0,10,"A");
        processIntervalSet.insert(15,20,"A");
        //查询的进程存在
        assertEquals("{0=[0, 10], 1=[15, 20]}",processIntervalSet.interval("A").toString());
        //查询的进程不存在
        assertEquals("{}",processIntervalSet.interval("B").toString());
    }
    @Test
    public void labelsTest()throws Exception
    {
        IProcessIntervalSet<String> processIntervalSet=new ProcessIntervalSet<>();
        Set<String>tempSet=new HashSet<>();
        assertEquals(tempSet,processIntervalSet.labels());
        processIntervalSet.insert(0,10,"A");
        processIntervalSet.insert(15,20,"B");
        tempSet.add("A");
        tempSet.add("B");
        assertEquals(tempSet,processIntervalSet.labels());
    }
    @Test
    public void totalTimeTest()throws Exception
    {

        IProcessIntervalSet<String> processIntervalSet=new ProcessIntervalSet<>();
        processIntervalSet.insert(0,10,"A");
        processIntervalSet.insert(15,20,"B");
        assertEquals(10,processIntervalSet.totalTime("A"));
        assertEquals(0,processIntervalSet.totalTime("C"));
        processIntervalSet.insert(20,25,"A");
        assertEquals(15,processIntervalSet.totalTime("A"));
    }

}
