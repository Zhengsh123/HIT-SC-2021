package API.ProcessSchedule;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;

public class ProcessScheduleTest {
    @Test
    public void addProcessTest()
    {
        ProcessScheduleApp processScheduleApp=new ProcessScheduleApp();
        Process process1=new Process("1","A",10,20);
        Process process2=new Process("2","B",0,20);
        Process process3=new Process("3","C",10,20);
        Process process4=new Process("4","D",10,20);
        processScheduleApp.addProcess(process1);
        processScheduleApp.addProcess(process2);
        processScheduleApp.addProcess(process3);
        processScheduleApp.addProcess(process4);
        Set<Process>testProcess=new HashSet<>();
        testProcess.add(process1);
        testProcess.add(process2);
        testProcess.add(process3);
        testProcess.add(process4);
        assertEquals(testProcess,processScheduleApp.labels());
    }
//    @Test
//    public void randomScheduleTest()
//    {
//        ProcessScheduleApp processScheduleApp=new ProcessScheduleApp();
//        Process process1=new Process("1","A",10,20);
//        Process process2=new Process("2","B",0,20);
//        Process process3=new Process("3","C",10,20);
//        Process process4=new Process("4","D",10,20);
//        processScheduleApp.addProcess(process1);
//        processScheduleApp.addProcess(process2);
//        processScheduleApp.addProcess(process3);
//        processScheduleApp.addProcess(process4);
//        assertEquals("时间\tID\t进程名称\n" +
//                "详细安排如下：\n" +
//                "安排表空闲率：1.0",processScheduleApp.toString());
//        processScheduleApp.randomSchedule();
//        assertEquals("时间\tID\t进程名称\n" +
//                "详细安排如下：\n" +
//                "0——9:2\tB\n" +
//                "9——22:3\tC\n" +
//                "22——30:1\tA\n" +
//                "30——42:4\tD\n" +
//                "42——49:1\tA\n" +
//                "安排表空闲率：0.0",processScheduleApp.toString());
//    }
    @Test
    public void optimizeScheduleTest()
    {
        ProcessScheduleApp processScheduleApp=new ProcessScheduleApp();
        Process process1=new Process("1","A",10,20);
        Process process2=new Process("2","B",5,20);
        Process process3=new Process("3","C",18,20);
        Process process4=new Process("4","D",15,20);
        processScheduleApp.addProcess(process1);
        processScheduleApp.addProcess(process2);
        processScheduleApp.addProcess(process3);
        processScheduleApp.addProcess(process4);
        assertEquals("时间\tID\t进程名称\n" +
                "详细安排如下：\n" +
                "安排表空闲率：1.0",processScheduleApp.toString());
        processScheduleApp.optimizeSchedule();
        assertEquals("时间\tID\t进程名称\n" +
                "详细安排如下：\n" +
                "0——5:2\tB\n" +
                "5——15:1\tA\n" +
                "15——30:4\tD\n" +
                "30——48:3\tC\n" +
                "安排表空闲率：0.0",processScheduleApp.toString());
    }
}
