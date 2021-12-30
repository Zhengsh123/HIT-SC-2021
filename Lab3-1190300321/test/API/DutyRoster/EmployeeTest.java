package API.DutyRoster;
import static org.junit.Assert.*;
import org.junit.Test;

public class EmployeeTest {
    @Test
    public void getNameTest()
    {
        Employee testEmployee =new Employee("张三","书记","12345678");
        assertEquals("张三", testEmployee.getName());
    }
    @Test
    public void getPositionTest()
    {
        Employee testEmployee =new Employee("张三","书记","12345678");
        assertEquals("书记", testEmployee.getPosition());
    }
    @Test
    public void getPhoneNumberTest()
    {
        Employee testEmployee =new Employee("张三","书记","12345678");
        assertEquals("12345678", testEmployee.getPhoneNumber());
    }
}
