package IntervalSet;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
public class CommonIntervalSetTest {
    @Test
    /*
     * Testing strategy
     * 等价类划分：
     * 插入标签是否与多个时间段相关：是，否
     * 插入时间是否重叠：是，否
     * 插入时间是否满足end>start
     */
    public void InsertTest()throws Exception
    {
        IntervalSet<String> intervalSet=new CommonIntervalSet<String>();
        //插入时间重叠
        intervalSet.insert(0,10,"A");
        assertEquals("{A=[0, 10]}",intervalSet.toString());
        intervalSet.insert(5,15,"B");
        assertEquals("{A=[0, 10], B=[5, 15]}",intervalSet.toString());
        //插入时间不重叠
        intervalSet.insert(15,20,"C");
        assertEquals("{A=[0, 10], B=[5, 15], C=[15, 20]}",intervalSet.toString());
        //插入标签与多个时间段相关
        intervalSet.insert(20,30,"B");
        assertEquals("{A=[0, 10], B=[20, 30], C=[15, 20]}",intervalSet.toString());
        //插入时间不满足start<end
        assertThrows(IllegalArgumentException.class,()->intervalSet.insert(30,20,"D"));

    }
    @Test
    /*
     *Testing strategy
     *
     */
    public void labelsTest()throws Exception
    {
        IntervalSet<String> intervalSet=new CommonIntervalSet<String>();
        intervalSet.insert(0,10,"A");
        intervalSet.insert(10,20,"B");
        intervalSet.insert(20,30,"C");
        Set<String>testSet=new HashSet<>();
        testSet.add("A");
        testSet.add("B");
        testSet.add("C");
        assertEquals(testSet,intervalSet.labels());
    }
    @Test
    /*
     *Testing strategy
     *等价类划分：
     * 删除的标签是否存在：是，否
     */
    public void removeTest()throws Exception
    {
        IntervalSet<String> intervalSet=new CommonIntervalSet<String>();
        intervalSet.insert(0,10,"A");
        intervalSet.insert(10,20,"B");
        intervalSet.insert(20,30,"C");
        assertEquals("{A=[0, 10], B=[10, 20], C=[20, 30]}",intervalSet.toString());
        //删除的标签存在
        intervalSet.remove("A");
        Set<String>testSet=new HashSet<>();
        testSet.add("B");
        testSet.add("C");
        assertEquals(testSet,intervalSet.labels());
        assertEquals("{B=[10, 20], C=[20, 30]}",intervalSet.toString());
        //删除标签不存在
        assertFalse(intervalSet.remove("D"));
        assertEquals(testSet,intervalSet.labels());
        assertEquals("{B=[10, 20], C=[20, 30]}",intervalSet.toString());
    }
    @Test
    /*
     *Testing strategy
     *等价类划分：
     * 标签是否存在：是，否
     */
    public void startTest()throws Exception
    {
        IntervalSet<String> intervalSet=new CommonIntervalSet<String>();
        intervalSet.insert(0,10,"A");
        intervalSet.insert(10,20,"B");
        intervalSet.insert(20,30,"C");
        assertEquals("{A=[0, 10], B=[10, 20], C=[20, 30]}",intervalSet.toString());
        //标签存在
        assertEquals(0,intervalSet.start("A"));
        assertEquals(10,intervalSet.start("B"));
        //标签不存在
        assertEquals(-1,intervalSet.start("D"));
    }
    @Test
    /*
     *Testing strategy
     *等价类划分：
     * 标签是否存在：是，否
     */
    public void endTest()throws Exception
    {
        IntervalSet<String> intervalSet=new CommonIntervalSet<String>();
        intervalSet.insert(0,10,"A");
        intervalSet.insert(10,20,"B");
        intervalSet.insert(20,30,"C");
        assertEquals("{A=[0, 10], B=[10, 20], C=[20, 30]}",intervalSet.toString());
        //标签存在
        assertEquals(10,intervalSet.end("A"));
        assertEquals(20,intervalSet.end("B"));
        //标签不存在
        assertEquals(-1,intervalSet.end("D"));
    }
}
