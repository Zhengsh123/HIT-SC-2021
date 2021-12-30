package API.DutyRoster;

public class Employee {
    /**
     * AF(name)=值班人员的名字
     * AF(position)值班人员的职位
     * AF(position)值班人员的电话号码
     */
    private final String name;
    private final String position;
    private final String phoneNumber;

    /**
     * 构造函数
     * @param name 值班人员的名字
     * @param position 值班人员的职位
     * @param phoneNumber 值班人员的电话号码
     */
    public Employee(String name, String position, String phoneNumber)
    {
        this.name=name;
        this.position=position;
        this.phoneNumber=phoneNumber;
    }

    /**
     * 返回值班人员的名字
     * @return 返回值班人员的名字
     */
    public String getName() {
        String name = this.name;
        return name;
    }

    /**
     * 返回值班人员的职位
     * @return 返回值班人员的职位
     */
    public String getPosition()
    {
        String position=this.position;
        return position;
    }

    /**
     * 返回值班人员的电话号码
     * @return 返回值班人员的电话号码
     */
    public String getPhoneNumber()
    {
        String phoneNumber=this.phoneNumber;
        return phoneNumber;
    }
    @Override
    public String toString()
    {
        String s=this.name+"    "+this.position+"   "+this.phoneNumber;
        return s;
    }

}
