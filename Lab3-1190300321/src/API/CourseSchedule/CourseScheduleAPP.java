package API.CourseSchedule;

import APISet.CourseIntervalSet.CourseIntervalSet;
import APISet.CourseIntervalSet.IcourseIntervalSet;
import IntervalSet.CommonMultiIntervalSet;
import IntervalSet.MultiIntervalSet;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseScheduleAPP {
    /**
     * AF(courseSet)=所有课程集合
     * AF(courseIntervalSet)=课程安排集合
     * AF(arrangeMap)=表示某门课是否安排完的Map
     * AF(startTime)=学期开始时间
     * AF(length)=学期周数
     * Rep:
     * 1.所有课程应该都先加入courseSet
     * 2.课程安排不能超过周最大学时
     * RI:使用private final，必要时候防御式拷贝
     */
    private final Set<Course>courseSet=new HashSet<>();
    private final IcourseIntervalSet<Course>courseIntervalSet=new CourseIntervalSet<>();
    private final Map<Course,Boolean> arrangeMap=new HashMap<>();
    private final List<Course> courseList=new ArrayList<>();
    private String startTime;
    private int length;
    private void checkRep()
    {
        for(Course c:courseIntervalSet.labels())
        {
            assert courseSet.contains(c);
            long time=courseIntervalSet.totalTime(c);
            long weekTime=c.getWeekTime();
            assert time<=weekTime;
        }
    }
    /**
     * 向课表添加课程
     * @param c 课程
     */
    public void addCourse(Course c)
    {
        courseSet.add(c);
        arrangeMap.put(c,false);
        courseList.add(c);
    }
    /**
     * 设置学期开始日期与周数
     * @param startDate 开始日期
     * @param length 周数
     */
    public void setWeek(String startDate,int length)
    {
        this.length=length;
        this.startTime=startDate;
        checkRep();
    }
    /**
     * 向课表中插入课程
     * @param ID 课程ID
     * @param start 开始时间（只能是8，10,13,15，19）之一
     * @param end 结束时间（只能是10,12,15,17,21）之一
     * @param day 一周中的星期几
     * @return 返回是否成功插入
     */
    public boolean setCourse(String ID,long start,long end,int day)
    {
        for(Course c:this.courseSet)
        {
            if(c.getID().equals(ID))
            {
                if(this.arrangeMap.get(c))return false;//已经达到时长
                if((start==8&&end==10)||(start==10&&end==12)||(start==13&&end==15)||(start==15&&end==17)||(start==19&&end==21))
                {
                    try
                    {
                        this.courseIntervalSet.insert(start,end,c,day);
                    }catch (Exception e)
                    {
                        System.out.println("start与end不符合要求");
                    }
                    if(this.courseIntervalSet.totalTime(c)==c.getWeekTime())
                    {
                        this.arrangeMap.put(c,true);
                    }
                    checkRep();
                    return true;
                }
                break;
            }
        }
        return false;
    }
    /**
     * 查看某门课是否已经达到最大周学时
     * @param ID 课程ID
     * @return 返回是否达到最大学时
     */
    public boolean isArranged(String ID)
    {
        for(Course c:this.courseSet)
        {
            if(c.getID().equals(ID))
            {
                checkRep();
                return this.arrangeMap.get(c);
            }
        }
        System.out.println("不存在这门课程");
        checkRep();
        return false;
    }

    /**
     * 计算周空闲比例
     * @return 返回空闲比例
     */
    public double calcFreeTimeRatio()
    {
        double ratio=0;
        try
        {
          ratio=this.courseIntervalSet.calcFreeTimeRatio();
        }catch (Exception e)
        {
            System.out.println("参数存在问题");
        }
        return ratio;
    }
    /**
     * 查看一周中某一天的课程安排
     * @param week 周数
     * @param day 星期几
     * @return 课程安排列表
     */
    public MultiIntervalSet<Course>checkArrange(int week,int day)
    {
       return this.courseIntervalSet.getDayArrange(day);
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("学期开始时间："+this.startTime+" 学期周数"+this.length);
        sb.append("\n所有课程信息：\n");
        for(int i=0;i<this.courseList.size();i++)
        {
            sb.append(courseList.get(i).toString());
        }
        sb.append(this.courseIntervalSet.toString());
        return sb.toString();
    }
    public static void main(String[] args)
    {
        CourseScheduleAPP courseScheduleAPP =new CourseScheduleAPP();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入学期开始日期与学期周数，格式如：2020-01-01,18");
        String input=sc.next();
        Pattern p=Pattern.compile("(.*)[,](.*)");
        Matcher matcher = p.matcher(input);
        if(matcher.find())
        {
            courseScheduleAPP.setWeek(matcher.group(1),Integer.valueOf(matcher.group(2)));
        }
        else
        {
            System.out.println("输入格式错误");
            return;
        }
        while(true)
        {
            System.out.println("请输入课程信息，格式为：ID,name,教师姓名,地点，周学时数（偶数）");
            String input1=sc.next();
            Pattern p1 = Pattern.compile("(.*)[,](.*)[,](.*)[,](.*)[,](.*)");
            Matcher matcher1 = p1.matcher(input1);
            if(matcher1.find())
            {
                String ID=matcher1.group(1);
                String name=matcher1.group(2);
                String teacherName=matcher1.group(3);
                String address=matcher1.group(4);
                int weekTime=Integer.valueOf(matcher1.group(5));
                Course course=new Course(ID,name,teacherName,address,weekTime);
                courseScheduleAPP.addCourse(course);
            }
            else
            {
                break;
            }
        }
        System.out.println("课程信息如下：\n");
        System.out.println(courseScheduleAPP.toString());
        while(true)
        {
            System.out.println("请输入排课信息，格式为：课程ID，开始时间，结束时间（周期只能为8-10,10-12,13-15,15-17,19-21）,星期几[0-6]");
            String input2=sc.next();
            Pattern p2 = Pattern.compile("(.*)[,](.*)[,](.*)[,](.*)");
            Matcher matcher2 = p2.matcher(input2);
            if(matcher2.find())
            {
                String ID=matcher2.group(1);
                long startTime=Long.valueOf(matcher2.group(2));
                long endTime=Long.valueOf(matcher2.group(3));
                int day=Integer.valueOf(matcher2.group(4));
                courseScheduleAPP.setCourse(ID,startTime,endTime,day);
            }
            else
            {
                break;
            }
            System.out.println("是否需要查看课表安排情况:y/n");
            String input3=sc.next().replace("\n","");
            if(input3.equals("y"))
            {
                System.out.println(courseScheduleAPP.toString());
            }
        }
        while(true)
        {
            System.out.println("如果需要查看某一天的安排请输入:[0-6],输入7退出");
            int index=sc.nextInt();
            if(index==7)break;
            System.out.println(courseScheduleAPP.checkArrange(1,index));

        }
    }

}
