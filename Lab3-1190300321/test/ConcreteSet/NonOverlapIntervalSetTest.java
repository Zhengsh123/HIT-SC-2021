package ConcreteSet;

import IntervalSet.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class NonOverlapIntervalSetTest {
    @Test
    /*
     * Testing strategy:
     * 插入的时间片段是否存在重叠：存在，不存在
     * 需要判断的Set的类型：IntervalSet，MultiIntervalSet
     */
    public void checkNoOverlapTest()throws Exception
    {
        //IntervalSet
        IntervalSet<String> initialSet=new CommonIntervalSet<String>();
        NonOverlapIntervalSet<String> initialNoOverlapSet=new NonOverlapIntervalSetImpl<>();
        //不存在重叠
        initialSet.insert(0,10,"A");
        assertEquals("{A=[0, 10]}",initialSet.toString());
        initialSet.insert(10,20,"B");
        assertEquals("{A=[0, 10], B=[10, 20]}",initialSet.toString());
        //存在重叠
        assertThrows(Exception.class,()->initialNoOverlapSet.checkNoOverlap(initialSet,1,12,"C"));

        //MultiIntervalSet
        MultiIntervalSet<String> multiIntervalSet=new CommonMultiIntervalSet<String>();
        multiIntervalSet.insert(0,10,"A");
        assertEquals("{A=[[0, 10]]}",multiIntervalSet.toString());
        multiIntervalSet.insert(10,20,"B");
        assertEquals("{A=[[0, 10]], B=[[10, 20]]}",multiIntervalSet.toString());
        assertThrows(Exception.class,()->initialNoOverlapSet.checkNoOverlap(multiIntervalSet,1,12));
    }

}
