package API.DutyRoster;
import APISet.DutyIntervalSet.*;
import IntervalSet.IntervalSet;
import API.*;

import java.io.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DutyRosterApp  implements DutyRoster {
    private final Set<Employee>EmployeeSet=new HashSet<>();
    private  String startTime;
    private  String endTime;
    private  IDutyIntervalSet<Employee>roster;
    private final List<Integer>arrangeList=new ArrayList<>();
    /**
     * RF:
     * RF(EmployeeSet)=存储每一个员工信息
     * RF(startTime)=值班表开始时间
     * RF(endTime)=值班表结束时间
     * RF(roster)=排班表
     * RF(arrangeList)=存储每一天是否已经安排的List
     * RI:使用private final，必要的时候使用防御式拷贝
     * Rep:
     * 1.一天不能安排多个人
     * 2.删除一个人之前要先把排班信息删掉
     * 3.所有人必须先添加才能排班
     */
    private void checkRep()
    {
        //删除一个人之前应该先把排班信息删掉
        //所有人必须先添加才能排班
        Set<Employee>label=this.roster.labels();
        for(Employee e:label)
        {
            assert EmployeeSet.contains(e);
        }
    }
    public boolean setTable(String startTime,String endTime)
    {
        DateCaculate dateCaculate=new DateCaculateImpl();
        long length=dateCaculate.sub(startTime,endTime)+1;
        if(length<0)return false;
        this.endTime=endTime;
        this.startTime=startTime;
        for(int i=0;i<length;i++)
        {
            arrangeList.add(0);
        }
        this.roster=new DutyIntervalSet<>(startTime,endTime);
        checkRep();
        return true;
    }

    public void addEmployee(String name,String position,String phoneNumber)
    {
        String regEx="[0-9]{3}-[0-9]{4}-[0-9]{4}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phoneNumber);
        if(matcher.matches())
        {
            Employee e=new Employee(name,position,phoneNumber);
            for(Employee E:EmployeeSet)
            {
                if(E.getName().equals(name))
                {
                    EmployeeSet.remove(E);
                }
            }
            EmployeeSet.add(e);
        }
        else
        {
            throw new IllegalArgumentException("输入的手机号格式不正确");
        }
    }
    public void addEmployee(Employee e)
    {
        EmployeeSet.add(e);
    }
    public void setRoster(String name,String startTime,String endTime)
    {
        try
        {
            Employee E=new Employee("","","");
            for(Employee e:EmployeeSet)
            {
                if(e.getName().equals(name))
                {
                    E=e;
                    break;
                }
            }
            DateCaculate dc=new DateCaculateImpl();
            long start=dc.sub(this.startTime,startTime);
            long end= dc.sub(this.startTime,endTime);
            for(long i=start;i<end+1;i++)
            {
                this.arrangeList.set((int)i,1);
            }
            roster.insert(start,end,E);
        }catch (Exception e)
        {
            e.printStackTrace();//显示异常的堆栈跟踪信息
        }
        checkRep();
    }
    public void setRoster()
    {
        DateCaculate dc=new DateCaculateImpl();
        int num=EmployeeSet.size();//所有员工数
        long time=dc.sub(startTime,endTime)+1;
        int dutyNum=(int)time/num;
        int i=0;//记录安排了多少个人
        long posj=0;//记录上一次结束时安排天数
        long nextj=0;//记录这一次应该安排到的天数
        for(Employee e:EmployeeSet)
        {
            if(i!=num-1)
            {
                nextj+=dutyNum;
            }
            else
            {
                nextj=time-1;
            }
            String tempStart=dc.add(startTime,(int)posj);
            String tempEnd=dc.add(startTime,(int)nextj);
            this.setRoster(e.getName(),tempStart,tempEnd);
            i++;
            for(long j=posj;j<=nextj;j++)
            {
                this.arrangeList.set((int)j,1);
            }
            posj=nextj+1;
        }
        checkRep();
    }

    public void remove(String name)
    {
        try
        {
            Employee E=new Employee("","","");
            for(Employee e:EmployeeSet)
            {
                if(e.getName().equals(name))
                {
                    E=e;
                    break;
                }
            }
            roster.remove(E);
            EmployeeSet.remove(E);
            checkRep();
        }catch (Exception e)
        {
            System.out.println("删除的人不存在");
        }
    }

    public boolean isFull()
    {
        int length=arrangeList.size();
        for(int i=0;i<length;i++)
        {
            if(arrangeList.get(i)==0)return false;
        }
        return true;
    }

    public void show()
    {
        StringBuilder sb=new StringBuilder();
        sb.append(this.toString());
        sb.append("未安排时间:\n");
        DateCaculate dc=new DateCaculateImpl();
        for(int i=0;i<this.arrangeList.size();i++)
        {
            if(arrangeList.get(i)==0)
            {
                sb.append(dc.add(startTime,i)+'\n');
            }
        }
        System.out.println(sb.toString());
    }

    public double calcFreeTimeRatio()throws Exception
    {
        double ratio=roster.calcFreeTimeRatio();
        checkRep();
        return Math.abs(ratio);
    }

    public String getPosition(String name)
    {
        String position="";
        for(Employee e:EmployeeSet)
        {
            if(name.equals(e.getName()))
            {
                position=e.getPosition();
                return position;
            }
        }
        System.out.println("不存在该员工");
        return position;
    }

    public String getPhoneNumber(String name)
    {
        String PhoneNumber="";
        for(Employee e:EmployeeSet)
        {
            if(name.equals(e.getName()))
            {
                PhoneNumber=e.getPhoneNumber();
                return PhoneNumber;
            }
        }
        System.out.println("不存在该员工");
        return PhoneNumber;
    }

    public void readFromFile(String filePath)throws Exception
    {
        String s=this.readToString(filePath);
        RegularPaeser regularPaeser=new RegularPaeser();
        List<String> partList=regularPaeser.GetThreePart(s);
        //添加时间
        Pattern p1= Pattern.compile("(.*)[,](.*)");
        Matcher m1=p1.matcher(partList.get(1));
        if(m1.find())
        {
            this.startTime=m1.group(1);
            this.endTime=m1.group(2);
            this.setTable(m1.group(1),m1.group(2));
        }
        //添加员工
        List<Employee> employeeList=regularPaeser.getEmployee(partList.get(0));
        for(int i=0;i<employeeList.size();i++)
        {
           this.addEmployee(employeeList.get(i));
        }
        //添加安排
        Pattern p2=Pattern.compile("\\s*(\\w+)\\{([^\\}]+)\\}");
        Matcher m2=p2.matcher(partList.get(2));
        while((m2).find())
        {
            String name=m2.group(1);
            String start="";
            String end="";
            Pattern p3=Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}),(\\d{4})-(\\d{2})-(\\d{2})");
            Matcher matcher3=p3.matcher(m2.group(2));
            if(matcher3.find())
            {
                start=matcher3.group(1)+"-"+matcher3.group(2)+"-"+matcher3.group(3);
                end=matcher3.group(4)+"-"+matcher3.group(5)+"-"+matcher3.group(6);
            }
            this.setRoster(name,start,end);
        }
    }
    public String readToString(String fileName) {
        try {
            String encoding="utf-8";    //设定自己需要的字符编码集
            File file = new File(fileName);
            if(file.exists() && file.isFile()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader br = new BufferedReader(read);//使用缓冲流按行读取
                String lineText = null;
                StringBuffer sb = new StringBuffer();
                while((lineText=br.readLine()) !=null){
                    sb.append(lineText);
                }
                br.close();
                read.close();
                return sb.toString();
            }else{System.out.println("找不到指定的文件");}

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(this.isFull())sb.append("这一排班表已经排满，详细信息如下：\n");
        else
        {
            try{
                double freeRatio=this.calcFreeTimeRatio();
                sb.append("这一排班表空闲率为："+freeRatio+'\n');
            }catch(Exception e)
            {
                System.out.println("信息错误");
            }
        }
        sb.append(roster.toString());
        return sb.toString();
    }
    public static void main(String[] args)
    {
        System.out.println("请输入排班表的开始时间，格式如2000-01-01");
        Scanner sc = new Scanner(System.in);
        String StartTime=sc.next();
        System.out.println("请输入排班表的结束时间，格式如2000-01-01");
        String EndTime=sc.next();
        DutyRosterApp dutyRosterApp=new DutyRosterApp();
        dutyRosterApp.setTable(StartTime,EndTime);
        while(true)
        {
            System.out.println("请输入值班人员信息，格式为姓名,职位,电话号码(xxx-xxxx-xxxx)");
            String input=sc.next();
            Pattern p1=Pattern.compile("(.*)[,](.*)[,](.*)");
            Matcher matcher=p1.matcher(input);
            if(matcher.find())
            {
                String name=matcher.group(1);
                String position=matcher.group(2);
                String phoneNumber=matcher.group(3);
                dutyRosterApp.addEmployee(name,position,phoneNumber);
            }
            else
            {
                break;
            }

        }
        System.out.println("排班表信息如下：\n");
        System.out.println(dutyRosterApp.toString());
        System.out.println("是否需要自动排班：y/n");
        if(sc.next().equals("y"))
        {
            dutyRosterApp.setRoster();
            dutyRosterApp.show();
            return;
        }
        while(!dutyRosterApp.isFull())
        {
            System.out.println("请输入排班信息，格式为:姓名，开始日期，结束日期 日期格式为2000-01-01：");
            String input=sc.next();
            String name=input.split(",")[0];
            String startTime=input.split(",")[1];
            String endTime=input.split(",")[2].replace("\n","");
            dutyRosterApp.setRoster(name,startTime,endTime);
            dutyRosterApp.show();
        }

    }
}
