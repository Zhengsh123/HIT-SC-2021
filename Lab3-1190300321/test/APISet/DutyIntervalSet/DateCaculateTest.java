package APISet.DutyIntervalSet;
import APISet.DutyIntervalSet.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;

public class DateCaculateTest {
    @Test
    public void subTest()throws Exception
    {
        String startDate="2021-01-10";
        String endDate="2021-03-06";
        DateCaculate testDC=new DateCaculateImpl();
        assertEquals(55,testDC.sub(startDate,endDate));
    }
    @Test
    public void addTest()throws Exception
    {
        String startDate="2021-01-10";
        int length=55;
        DateCaculate testDC=new DateCaculateImpl();
        assertEquals("2021-03-06",testDC.add(startDate,length));
    }
}
