package API.ProcessSchedule;

public class Process {
    private String ID;
    private String name;
    private long minTime;
    private long maxTime;
    /**
     * 构造函数
     * @param ID ID
     * @param name 名称
     * @param minTime 最小执行时间
     * @param maxTime 最大执行时间
     */
    public Process(String ID,String name,long minTime,long maxTime)
    {
        this.ID=ID;
        this.name=name;
        this.minTime=minTime;
        this.maxTime=maxTime;
    }

    /**
     * 获取进程名称
     * @return 进程名称
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * 获取最小执行时间
     * @return 最小执行时间
     */
    public long getMinTime()
    {
        return this.minTime;
    }

    /**
     * 获取最大执行时间
     * @return 最大执行时间
     */
    public long getMaxTime()
    {
        return this.maxTime;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Process)
        {
            if(!((Process) obj).getName().equals(this.name))return false;
            if(((Process) obj).getMaxTime()!=this.maxTime)return false;
            if(((Process) obj).getMinTime()!=this.minTime)return false;
            return true;
        }
        return false;
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append(this.ID+"\t"+this.name);
        return sb.toString();
    }

}
