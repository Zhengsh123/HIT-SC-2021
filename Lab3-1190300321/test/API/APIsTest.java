package API;
import IntervalSet.CommonMultiIntervalSet;
import IntervalSet.*;
import IntervalSet.MultiIntervalSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
public class APIsTest {
    @Test
    /*
     * Testing strategy:
     * 两个MultiIntervalSet是否有相似度：是，否
     */
    public void SimilarityTest()throws Exception
    {
        MultiIntervalSet<String>s1=new CommonMultiIntervalSet<String>();
        MultiIntervalSet<String>s2=new CommonMultiIntervalSet<String>();
        APIs<String>api=new APIsImpl<>();
        //没有相似度
        s1.insert(0,10,"A");
        s2.insert(0,10,"B");
        assertEquals(0,api.Similarity(s1,s2),0.001);
        //存在相似度
        s1.insert(11,20,"A");
        s2.insert(15,20,"A");
        assertEquals(0.3,api.Similarity(s1,s2),0.001);
    }
    @Test
    /*
     * Testing strategy:
     * 计算的Set类型：IntervalSet，MultiIntervalSet
     * 计算的Set是否存在冲突：是，否
     */
    public void calcConflictRatioTest()throws Exception
    {
        IntervalSet<String>i1=new CommonIntervalSet<String>();
        MultiIntervalSet<String>m1=new CommonMultiIntervalSet<String>();
        APIs<String>api=new APIsImpl<>();
        //计算的是IntervalSet
        //不存在冲突
        i1.insert(0,10,"A");
        assertEquals(0.0,api.calcConflictRatio(i1),0.001);
        i1.insert(11,20,"B");
        assertEquals(0,api.calcConflictRatio(i1),0.001);
        //存在冲突
        i1.insert(15,20,"C");
        assertEquals(0.2857142857142857,api.calcConflictRatio(i1),0.001);
        i1.insert(5,15,"D");
        assertEquals(0.7619047619047619,api.calcConflictRatio(i1),0.001);

        //计算的是MultiIntervalSet
        //不存在冲突
        m1.insert(0,10,"A");
        assertEquals(0,api.calcConflictRatio(m1),0.001);
        m1.insert(15,20,"B");
        assertEquals(0,api.calcConflictRatio(m1),0.001);
        //存在冲突
        m1.insert(15,20,"C");
        assertEquals(0.2857142857142857,api.calcConflictRatio(m1),0.001);
    }
    @Test
    /*
     * Testing strategy:
     * 计算的Set类型：IntervalSet，MultiIntervalSet
     * 计算的Set是否存在存在时间空白：是，否
     * 计算的Set是否为空:是，否
     */
    public void calcFreeTimeRatio()throws Exception
    {
        IntervalSet<String>i1=new CommonIntervalSet<String>();
        MultiIntervalSet<String>m1=new CommonMultiIntervalSet<String>();
        APIs<String>api=new APIsImpl<>();
        //计算的Set为空
        assertEquals(1,api.calcFreeTimeRatio(i1),0.001);
        assertEquals(1,api.calcFreeTimeRatio(m1),0.001);
        //计算的Set是IntervalSet
        //不存在空白
        i1.insert(0,5,"A");
        assertEquals(0,api.calcFreeTimeRatio(i1),0.001);
        i1.insert(6,10,"B");
        assertEquals(0,api.calcFreeTimeRatio(i1),0.001);
        //存在空白
        i1.insert(15,20,"C");
        assertEquals(0.19047619047619047,api.calcFreeTimeRatio(i1),0.001);

        //计算的Set是MultiIntervalSet
        //不存在空闲
        m1.insert(0,5,"A");
        assertEquals(0,api.calcFreeTimeRatio(m1),0.001);
        m1.insert(5,10,"B");
        assertEquals(0,api.calcFreeTimeRatio(m1),0.001);
        //存在空闲
        m1.insert(15,20,"C");
        assertEquals(0.25,api.calcFreeTimeRatio(m1),0.001);
    }
}
