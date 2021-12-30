package ConcreteSet;
import org.junit.Test;
import IntervalSet.*;

import static org.junit.Assert.*;

public class NoBlankIntervalSetTest {
    @Test(expected = Exception.class)
    /*
     *Testing strategy：
     * 输入的Set的类型：IntervalSet，MultiIntervalSet
     * 是否存在空白：是，否
     */
    public void checkNoBlank ()throws Exception
    {
        //IntervalSet
        IntervalSet<String> intervalSet=new CommonIntervalSet<String>();
        NoBlankIntervalSet<String> noBlankSet=new NoBlankIntervalSetImpl<>();
        intervalSet.insert(0,10,"A");
        intervalSet.insert(10,20,"B");
        //存在空白
        assertThrows(Exception.class,()->noBlankSet.checkNoBlank(intervalSet,0,30));
        intervalSet.insert(20,30,"C");
        //不存在空白
        noBlankSet.checkNoBlank(intervalSet,0,30);
        assertEquals("{A=[0, 10], B=[10, 20], C=[20, 30]}",intervalSet.toString());
        //存在空白
        intervalSet.remove("A");
        assertThrows(Exception.class,()->noBlankSet.checkNoBlank(intervalSet,0,30));

        //MultiIntervalSet
        MultiIntervalSet<String> multiIntervalSet=new CommonMultiIntervalSet<String>();
        multiIntervalSet.insert(0,10,"A");
        multiIntervalSet.insert(10,20,"A");
        multiIntervalSet.insert(30,40,"B");
        //存在空白
        assertThrows(Exception.class,()->noBlankSet.checkNoBlank(multiIntervalSet,0,40));
        //不存在空白
        multiIntervalSet.insert(20,30,"C");
        noBlankSet.checkNoBlank(intervalSet,0,40);
        //存在空白
        multiIntervalSet.remove("B");
        assertThrows(Exception.class,()->noBlankSet.checkNoBlank(multiIntervalSet,0,40));
    }
}
