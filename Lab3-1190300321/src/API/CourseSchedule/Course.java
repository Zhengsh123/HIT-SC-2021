package API.CourseSchedule;

public class Course {
    /**
     * AF(ID)=课程ID
     * AF(name)=课程名
     * AF(teacherName)=教师名
     * AF(address)=上课地址
     * AF(weekTime)=周学时数
     * Rep：周学时数是偶数
     * RI：使用private，必要时防御式拷贝
     */
    private String ID;
    private String name;
    private String teacherName;
    private String address;
    private int weekTime;

    private void checkRep()
    {
        assert this.weekTime%2==0;
    }

    public Course(String ID,String name,String teacherName,String address,int weekTime)
    {
        this.ID=ID;
        this.name=name;
        this.teacherName=teacherName;
        this.address=address;
        this.weekTime=weekTime;
        checkRep();
    }
    public int getWeekTime()
    {
        return this.weekTime;
    }
    public String getID()
    {
        return this.ID;
    }
    public String getName()
    {
        return this.name;
    }
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append(this.ID+'\t'+this.name+'\n');
        return sb.toString();
    }

}
