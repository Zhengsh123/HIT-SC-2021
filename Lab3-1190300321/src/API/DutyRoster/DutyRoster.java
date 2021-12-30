package API.DutyRoster;

public interface DutyRoster {
    /**
     * 设置排班表的开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public boolean setTable(String startTime, String endTime) throws Exception;

    /**
     * 向排班表管理系统中加入员工
     *
     * @param name        员工姓名
     * @param position    员工职位
     * @param phoneNumber 员工电话号码
     */
    public void addEmployee(String name, String position, String phoneNumber) throws Exception;

    /**
     * 向排班表中插入安排
     *
     * @param name      员工
     * @param startTime 排班开始时间
     * @param endTime   排班结束时间
     */
    public void setRoster(String name, String startTime, String endTime) throws Exception;

    /**
     * 根据已经插入的员工自动安排值班表
     */
    public void setRoster() throws Exception;

    /**
     * 移除某个员工
     *
     * @param name 员工
     */
    public void remove(String name) throws Exception;

    /**
     * 判断排班表是否已经排满了
     *
     * @return 如果排满了返回true；如果没有排满返回false
     */
    public boolean isFull() throws Exception;

    /**
     * 展示排班情况
     */
    public void show() throws Exception;

    /**
     * 计算排班表中的空闲时间比例
     *
     * @return 返回空闲比例
     */
    public double calcFreeTimeRatio() throws Exception;

    /**
     * 获得某个员工的职位
     *
     * @param name 需要获得职工的名字
     * @return 该职工的职位
     */
    public String getPosition(String name) throws Exception;

    /**
     * 获得某个职工的电话号码
     *
     * @param name 需要获得电话号码的职工姓名
     * @return 职工的电话号码
     */
    public String getPhoneNumber(String name) throws Exception;

    /**
     * 从文件中读取信息
     *
     * @param filePath 文件相对路径
     */
    public void readFromFile(String filePath) throws Exception;

    /**
     * 添加员工
     * @param e 员工
     */
    public void addEmployee(Employee e);
}
