package API.ProcessSchedule;

import APISet.ProcessIntervalSet.IProcessIntervalSet;
import APISet.ProcessIntervalSet.ProcessIntervalSet;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessScheduleApp {
    /**
     * AF(processIntervalSet)=进程安排表
     * AF(processSet)=所有的进程集合
     * AF(processMap)=标志每个进程是否运行结束
     * Rep:processIntervalSet的label必须在processSet里
     * RI:使用private final 必要的时候使用防御式拷贝
     */
    private final IProcessIntervalSet<Process>processIntervalSet=new ProcessIntervalSet<>();
    private final Set<Process>processSet=new HashSet<>();
    private final Map<Process,Boolean>processMap=new HashMap<>();
    private void checkRep()
    {
        Set<Process>tempSet=processIntervalSet.labels();
        for(Process p:tempSet)
        {
            assert processSet.contains(p);
        }
    }
    /**
     * 添加进程
     * @param p 进程
     */
    public void addProcess(Process p)
    {
        processSet.add(p);
        processMap.put(p,false);
        checkRep();
    }
    /**
     * 随机安排进程执行
     */
    public void randomSchedule()
    {
        long start=0;
        boolean flag=false;
        while(!flag)
        {
            boolean tempFlag=true;
            for(Process p:this.processSet)
            {
                if(!processMap.get(p))
                {
                    int time=new Random().nextInt((int)p.getMaxTime()-(int)processIntervalSet.totalTime(p));
                    if(time+processIntervalSet.totalTime(p)>p.getMinTime())
                    {
                        processMap.put(p,true);
                    }
                    try{
                        processIntervalSet.insert(start,(long)time+start,p);
                    }catch (Exception e)
                    {
                        System.out.println("start和end不满足要求");
                    }
                    start+=time;
                    tempFlag=false;
                }
            }
            flag=tempFlag;
        }

    }
    /**
     * 优化安排进程执行
     */
    public void optimizeSchedule()
    {
        long start=0;
        boolean flag=false;
        while(!flag)
        {
            Process P=new Process("","",Long.MAX_VALUE,Long.MAX_VALUE);
            for(Process p:this.processSet)
            {
                if(!processMap.get(p))
                {
                    if(P.getMinTime()-processIntervalSet.totalTime(P)>p.getMinTime()-processIntervalSet.totalTime(p))
                    {
                        P=p;
                    }
                }
            }
            long time=P.getMinTime()-processIntervalSet.totalTime(P)+start;
            try{
                processIntervalSet.insert(start,time,P);
            }catch (Exception e)
            {
                System.out.println("start和end不满足要求");
            }
            processMap.put(P,true);
            start=time;
            boolean tempFlag=true;
            for(Process temp:processMap.keySet())
            {
                if(!processMap.get(temp))
                {
                    tempFlag=false;
                    break;
                }
            }
            flag=tempFlag;
        }

    }
    public Set<Process> labels()
    {
        return Collections.unmodifiableSet(this.processSet);
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("时间\tID\t进程名称\n");
        sb.append(processIntervalSet.toString());
        return sb.toString();
    }
    public static void main(String[] args)
    {
        ProcessScheduleApp processScheduleApp=new ProcessScheduleApp();
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("请输入进程信息，格式为：ID,name,最短执行时间,最长执行时间");
            String input=sc.next();
            Pattern p1 = Pattern.compile("(.*)[,](.*)[,](.*)[,](.*)");
            Matcher matcher = p1.matcher(input);
            if(matcher.find())
            {
                String ID=matcher.group(1);
                String name=matcher.group(2);
                int minTime=Integer.valueOf(matcher.group(3));
                int maxTime=Integer.valueOf(matcher.group(4));
                Process process=new Process(ID,name,minTime,maxTime);
                processScheduleApp.addProcess(process);
            }
            else
            {
                break;
            }
        }
        System.out.println("进程信息如下：\n");
        System.out.println(processScheduleApp.toString());
        System.out.println("是否需要优化进程安排：y/n");
        if(sc.next().equals("y"))
        {
            processScheduleApp.optimizeSchedule();
            System.out.println(processScheduleApp.toString());
        }
        else
        {
            processScheduleApp.randomSchedule();
            System.out.println(processScheduleApp.toString());
        }
    }
}
