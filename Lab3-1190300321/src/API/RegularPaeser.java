package API;
import API.DutyRoster.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularPaeser {
    /**
     * 获取三个部分
     * @param AllString 所有内容
     * @return 三个部分分离的值
     */
    public List<String> GetThreePart(String AllString) {
       List<String> partList=new ArrayList<>();
       String reg1="^Employee\\{(.*)\\}Period\\{(.*)\\}Roster\\{(.*)\\}";
       String reg2="^Employee\\{(.*)\\}Roster\\{(.*)\\}Period\\{(.*)\\}";
       String reg3="^Period\\{(.*)\\}Employee\\{(.*)\\}Roster\\{(.*)\\}";
       String reg4="^Period\\{(.*)\\}Roster\\{(.*)\\}Employee\\{(.*)\\}";
       String reg5="^Roster\\{(.*)\\}Employee\\{(.*)\\}Period\\{(.*)\\}";
       String reg6="^Roster\\{(.*)\\}Period\\{(.*)\\}Employee\\{(.*)\\}";
       Pattern pattern1 = Pattern.compile(reg1);
       Pattern pattern2 = Pattern.compile(reg2);
       Pattern pattern3 = Pattern.compile(reg3);
       Pattern pattern4 = Pattern.compile(reg4);
       Pattern pattern5 = Pattern.compile(reg5);
       Pattern pattern6 = Pattern.compile(reg6);
       Matcher matcher1=pattern1.matcher(AllString);
       Matcher matcher2=pattern2.matcher(AllString);
       Matcher matcher3=pattern3.matcher(AllString);
       Matcher matcher4=pattern4.matcher(AllString);
       Matcher matcher5=pattern5.matcher(AllString);
       Matcher matcher6=pattern6.matcher(AllString);

       if(matcher1.find())
       {
           partList.add(matcher1.group(1));
           partList.add(matcher1.group(2));
           partList.add(matcher1.group(3));
           return partList;
       }
       else if(matcher2.find())
       {
           partList.add(matcher2.group(1));
           partList.add(matcher2.group(3));
           partList.add(matcher2.group(2));
           return partList;
       }
       else if(matcher3.find())
       {
           partList.add(matcher3.group(2));
           partList.add(matcher3.group(1));
           partList.add(matcher3.group(3));
           return partList;
       }
       else if(matcher4.find())
       {
           partList.add(matcher4.group(2));
           partList.add(matcher4.group(3));
           partList.add(matcher4.group(1));
           return partList;
       }
       else if(matcher5.find())
       {
           partList.add(matcher5.group(3));
           partList.add(matcher5.group(1));
           partList.add(matcher5.group(2));
           return partList;
       }
       else if(matcher6.find())
       {
           partList.add(matcher4.group(3));
           partList.add(matcher4.group(2));
           partList.add(matcher4.group(1));
           return partList;
       }
       return partList;
    }
    public List<Employee> getEmployee(String employeeString)
    {
        String reg="\\s*(\\w+)\\{([^,]+),(\\d{3})-(\\d{4})-(\\d{4})\\}";
        List<Employee> resEmployees = new ArrayList<>();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(employeeString);
        while ((matcher).find()) {
            String teleString=matcher.group(3)+'-' + matcher.group(4)+'-' + matcher.group(5);
            Employee employee = new Employee(matcher.group(1), matcher.group(2),teleString);
            resEmployees.add(employee);
        }

        return resEmployees;
    }
}
