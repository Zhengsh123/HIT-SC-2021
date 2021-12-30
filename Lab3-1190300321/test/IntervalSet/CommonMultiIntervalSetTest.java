package IntervalSet;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CommonMultiIntervalSetTest {
    @Test
    /*
     * Testing strategy
     * 等价类划分：
     * 插入标签是否与多个时间段相关：是、否
     * 插入时间是否重叠：是，否
     * 插入时间是否满足end>start
     */
    public void InsertTest()throws Exception
    {
        MultiIntervalSet<String> multiIntervalSet=new CommonMultiIntervalSet<String>();
        //插入时间重叠
        multiIntervalSet.insert(0,10,"A");
        assertEquals("{A=[[0, 10]]}",multiIntervalSet.toString());
        multiIntervalSet.insert(5,15,"B");
        assertEquals("{A=[[0, 10]], B=[[5, 15]]}",multiIntervalSet.toString());
        //插入时间不重叠
        multiIntervalSet.insert(15,20,"C");
        assertEquals("{A=[[0, 10]], B=[[5, 15]], C=[[15, 20]]}",multiIntervalSet.toString());
        //插入标签与多个时间段相关
        multiIntervalSet.insert(20,30,"B");
        assertEquals("{A=[[0, 10]], B=[[5, 15], [20, 30]], C=[[15, 20]]}",multiIntervalSet.toString());
        //插入时间不满足start<end
        multiIntervalSet.insert(30,20,"D");
        assertEquals("{A=[[0, 10]], B=[[5, 15], [20, 30]], C=[[15, 20]]}",multiIntervalSet.toString());
    }
    @Test
    /*
     *Testing strategy
     *
     */
    public void labelsTest()throws Exception
    {
        MultiIntervalSet<String> multiIntervalSet=new CommonMultiIntervalSet<String>();
        multiIntervalSet.insert(0,10,"A");
        multiIntervalSet.insert(10,20,"B");
        multiIntervalSet.insert(20,30,"C");
        Set<String> testSet=new HashSet<>();
        testSet.add("A");
        testSet.add("B");
        testSet.add("C");
        assertEquals(testSet,multiIntervalSet.labels());
    }
    @Test
    /*
     *Testing strategy
     *等价类划分：
     * 删除的标签是否存在：是，否
     */
    public void removeTest()throws Exception
    {
        MultiIntervalSet<String> multiIntervalSet=new CommonMultiIntervalSet<String>();
        multiIntervalSet.insert(0,10,"A");
        multiIntervalSet.insert(10,20,"B");
        multiIntervalSet.insert(20,30,"C");
        assertEquals("{A=[[0, 10]], B=[[10, 20]], C=[[20, 30]]}",multiIntervalSet.toString());
        //删除的标签存在
        multiIntervalSet.remove("A");
        Set<String>testSet=new HashSet<>();
        testSet.add("B");
        testSet.add("C");
        assertEquals(testSet,multiIntervalSet.labels());
        assertEquals("{B=[[10, 20]], C=[[20, 30]]}",multiIntervalSet.toString());
        //删除标签不存在
        assertFalse(multiIntervalSet.remove("D"));
        assertEquals(testSet,multiIntervalSet.labels());
        assertEquals("{B=[[10, 20]], C=[[20, 30]]}",multiIntervalSet.toString());
    }
    @Test
    /*
     * Testing strategy
     * 输入标签是否存在：存在，不存在
     * 标签是否与多个时间段相关：是、否
     */
    public void intervalsTest()throws Exception
    {
        MultiIntervalSet<String> multiIntervalSet=new CommonMultiIntervalSet<String>();
        multiIntervalSet.insert(0,10,"A");
        multiIntervalSet.insert(10,20,"B");
        multiIntervalSet.insert(20,30,"C");
        multiIntervalSet.insert(20,30,"B");
        assertEquals("{A=[[0, 10]], B=[[10, 20], [20, 30]], C=[[20, 30]]}",multiIntervalSet.toString());
        //输入标签不存在
        assertEquals("{}",multiIntervalSet.intervals("D").toString());
        //输入标签存在且不与多个时间段相关
        assertEquals("{0=[0, 10]}",multiIntervalSet.intervals("A").toString());
        //输入标签存在且与多个时间段相关
        assertEquals("{0=[10, 20], 1=[20, 30]}",multiIntervalSet.intervals("B").toString());
    }
}
