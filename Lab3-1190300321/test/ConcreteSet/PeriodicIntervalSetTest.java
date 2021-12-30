package ConcreteSet;

import IntervalSet.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PeriodicIntervalSetTest {
    @Test
    /*
     * Testing strategy:
     * 插入的标签是否是周期性的：是，否
     * 插入的标签是否已经存在：是，否
     * 插入的相同标签是否在同一个周期中的同一个大片段内：是，否（如是否在一周中的同一天）
     */
    public void insertTest()throws Exception
    {
        PeriodicIntervalSet<String> testPeriodicIntervalSet=new PeriodicIntervalSetImpl<>();
        testPeriodicIntervalSet.periodLength(7);
        //插入标签不是周期性的，不存在
        testPeriodicIntervalSet.periodArrange(1,1,"A");
        testPeriodicIntervalSet.insert(0,10,"A",1);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10]]}, {}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1]}",testPeriodicIntervalSet.toString());
        //插入的标签是周期性的，不存在
        testPeriodicIntervalSet.periodArrange(1,2,"B");
        testPeriodicIntervalSet.insert(10,20,"B",2);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10]]}, {B=[[10, 20]]}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1], B=[1, 2]}"
                ,testPeriodicIntervalSet.toString());
        //插入的标签不是周期性的，已经存在，在一个周期中的一个大片段内
        testPeriodicIntervalSet.insert(20,30,"A",1);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10], [20, 30]]}, {B=[[10, 20]]}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1], B=[1, 2]}"
                ,testPeriodicIntervalSet.toString());
        //插入的标签不是周期性的，已经存在，在一个周期的不同大片段内
        testPeriodicIntervalSet.insert(20,30,"A",2);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10], [20, 30]]}, {A=[[20, 30]], B=[[10, 20]]}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1], B=[1, 2]}"
                ,testPeriodicIntervalSet.toString());
        //插入的标签是周期性的，已经存在，在一个周期的同一个大片段内
        testPeriodicIntervalSet.insert(0,10,"B",2);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10], [20, 30]]}, {A=[[20, 30]], B=[[10, 20], [0, 10]]}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1], B=[1, 2]}"
                ,testPeriodicIntervalSet.toString());
        //插入的标签是周期性的，已经存在，在一个周期的不同大片段内
        testPeriodicIntervalSet.insert(20,30,"B",3);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10], [20, 30]]}, {A=[[20, 30]], B=[[10, 20], [0, 10]]}, {B=[[20, 30]]}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1], B=[1, 2]}"
                ,testPeriodicIntervalSet.toString());
    }
    @Test
    /*
     *Testing strategy：
     * 需要移除的是否是周期性的：是，否
     * 需要移除的是否在一个周期内多次出现：是，否
     */
    public void removeTest()throws Exception
    {
        PeriodicIntervalSet<String> testPeriodicIntervalSet=new PeriodicIntervalSetImpl<>();
        testPeriodicIntervalSet.periodLength(7);
        testPeriodicIntervalSet.periodArrange(1,1,"A");
        testPeriodicIntervalSet.insert(0,10,"A",1);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10]]}, {}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1]}"
                ,testPeriodicIntervalSet.toString());
        //移除的不具有周期性，只出现一次
        testPeriodicIntervalSet.remove("A");
        assertEquals("一个周期内安排:[{}, {}, {}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{}"
                ,testPeriodicIntervalSet.toString());
        //移除的不具有周期性，出现多次
        testPeriodicIntervalSet.periodArrange(1,1,"A");
        testPeriodicIntervalSet.insert(0,10,"A",1);
        testPeriodicIntervalSet.insert(0,10,"A",2);
        assertEquals("一个周期内安排:[{}, {A=[[0, 10]]}, {A=[[0, 10]]}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{A=[1, 1]}"
                ,testPeriodicIntervalSet.toString());
        testPeriodicIntervalSet.remove("A");
        assertEquals("一个周期内安排:[{}, {}, {}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{}"
                ,testPeriodicIntervalSet.toString());
        //移除的具有周期性，一个周期只出现一次
        testPeriodicIntervalSet.periodArrange(1,10,"B");
        testPeriodicIntervalSet.insert(0,10,"B",1);
        assertEquals("一个周期内安排:[{}, {B=[[0, 10]]}, {}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{B=[1, 10]}"
                ,testPeriodicIntervalSet.toString());
        //移除的具有周期性，一个周期出现多次
        testPeriodicIntervalSet.periodArrange(1,10,"B");
        testPeriodicIntervalSet.insert(0,10,"B",1);
        testPeriodicIntervalSet.insert(0,10,"B",2);
        assertEquals("一个周期内安排:[{}, {B=[[0, 10], [0, 10]]}, {B=[[0, 10]]}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{B=[1, 10]}"
                ,testPeriodicIntervalSet.toString());
        testPeriodicIntervalSet.remove("B");
        assertEquals("一个周期内安排:[{}, {}, {}, {}, {}, {}, {}]  每个label的周期性(周期开始，周期结束):{}"
                ,testPeriodicIntervalSet.toString());
    }
    @Test
    /*
     *Testing strategy:
     * 需要展示的标签是否在一个周期内多次出现：是，否
     * 需要展示的标签是否存在：是，否
     */
    public void intervalTest()throws Exception
    {
        PeriodicIntervalSet<String> testPeriodicIntervalSet=new PeriodicIntervalSetImpl<>();
        testPeriodicIntervalSet.periodLength(7);
        //需要展示的标签不存在
        assertThrows(IllegalArgumentException.class,()->testPeriodicIntervalSet.interval("A"));
        //需要展示的标签存在
        //需要展示的标签在一个周期内没有多次出现
        testPeriodicIntervalSet.periodArrange(1,1,"A");
        testPeriodicIntervalSet.insert(0,10,"A",1);
        assertEquals("{1=[[0, 10]]}",testPeriodicIntervalSet.interval("A").toString());
        //需要展示的标签在一个周期内多次出现
        testPeriodicIntervalSet.insert(0,10,"A",2);
        assertEquals("{1=[[0, 10]], 2=[[0, 10]]}",testPeriodicIntervalSet.interval("A").toString());
    }
    @Test
    /*
     * Testing strategy:
     * 提取的标签是否具有周期性：是，否
     * 提取的标签是否存在
     */
    public void getPeriodArrangeTest()throws Exception
    {
        PeriodicIntervalSet<String> testPeriodicIntervalSet=new PeriodicIntervalSetImpl<>();
        testPeriodicIntervalSet.periodLength(7);
        Map<String, List<Long>>periodArrangeMap=new HashMap<>();
        testPeriodicIntervalSet.periodArrange(1,1,"A");

        //不具有周期性
        List<Long>testList=new ArrayList<>();
        long start=1;
        long end=1;
        testList.add(start);
        testList.add(end);
        periodArrangeMap.put("A",testList);
        assertEquals(periodArrangeMap,testPeriodicIntervalSet.getPeriodArrange("A"));
        //具有周期性
        List<Long>testList2=new ArrayList<>();
        start=1;
        end=10;
        testList2.add(start);
        testList2.add(end);
        periodArrangeMap.remove("A");
        periodArrangeMap.put("B",testList2);
        testPeriodicIntervalSet.periodArrange(1,10,"B");
        assertEquals(periodArrangeMap,testPeriodicIntervalSet.getPeriodArrange("B"));

    }
    @Test
    /*
     * Testing strategy:
     * 提取的信息的标签是否具有周期性：是，否
     * 提取的标签是否存在:是，否
     */
    public void labelArrangeTest()throws Exception
    {
        PeriodicIntervalSet<String> testPeriodicIntervalSet=new PeriodicIntervalSetImpl<>();
        testPeriodicIntervalSet.periodLength(7);
        Map<IntervalSet<Integer>,List> labelArrangeMap=new HashMap<>();
        testPeriodicIntervalSet.periodArrange(1,1,"A");
        testPeriodicIntervalSet.insert(0,10,"A",1);
        //不具有周期性
        assertEquals("{{1=[[0, 10]]}=[1, 1]}",testPeriodicIntervalSet.getLabelArrange("A").toString());
        //具有周期性
        testPeriodicIntervalSet.periodArrange(1,10,"B");
        testPeriodicIntervalSet.insert(10,20,"B",2);
        assertEquals("{{2=[[10, 20]]}=[1, 10]}",testPeriodicIntervalSet.getLabelArrange("B").toString());
        //提取的标签不存在
        assertThrows(IllegalArgumentException.class,()->testPeriodicIntervalSet.getLabelArrange("E"));
    }
    @Test
    public void labelsTest()throws Exception
    {
        PeriodicIntervalSet<String> testPeriodicIntervalSet=new PeriodicIntervalSetImpl<>();
        testPeriodicIntervalSet.periodLength(7);
        testPeriodicIntervalSet.periodArrange(1,10,"A");
        testPeriodicIntervalSet.periodArrange(1,10,"B");
        testPeriodicIntervalSet.periodArrange(1,10,"C");
        testPeriodicIntervalSet.insert(1,10,"A",1);
        testPeriodicIntervalSet.insert(1,10,"B",2);
        testPeriodicIntervalSet.insert(1,10,"C",3);
        Set<String> testSet=new HashSet<>();
        testSet.add("A");
        testSet.add("B");
        testSet.add("C");
        assertEquals(testSet,testPeriodicIntervalSet.labels());
    }

}
