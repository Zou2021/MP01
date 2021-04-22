package com.zou.test;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zou.beans.Employee;
import com.zou.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

/**
 * @author: 邹祥发
 * @date: 2021/4/15 14:04
 */
public class MpTest {
    private final ApplicationContext iocContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    private final EmployeeMapper employeeMapper = iocContext.getBean("employeeMapper",EmployeeMapper.class);
    @Test
    public void mpTest() throws Exception{
        DataSource dataSource = iocContext.getBean("dataSource",DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    /**
     * 插入
     */
    @Test
    public void insertTest() {
        //初始化Employee
        Employee employee = new Employee();
        employee.setLastName("jack");
        employee.setEmail("mp@zou.com");
        employee.setGender(1);
        employee.setAge(22);
        //插入到数据库
        int result = employeeMapper.insert(employee);
        System.out.println(result);
    }

    /**
     * 修改
     */
    @Test
    public void updateById(){
        //初始化Employee
        Employee employee = new Employee();
        employee.setId(14);
        employee.setLastName("小邹");
        employee.setEmail("com@zou.cn");
        employee.setAge(18);
        employee.setGender(0);
        Integer result = employeeMapper.updateById(employee);
        System.out.println("result："+result);
    }
    /**
     * 通过id查询
     */
    @Test
    public void selectByIdTest(){
        Employee employee = employeeMapper.selectById(14);
        System.out.println(employee);
    }
    /**
     * 通过多列查询
     * 注：查询到的结果只能有一条，否则报异常
     */
    @Test
    public void selectOneTest(){
        Employee employee = new Employee();
        employee.setId(14);
        employee.setLastName("小邹");
        Employee result = employeeMapper.selectOne(employee);
        System.out.println("result："+result);
    }
    /**
     * 通过多个id查询
     */
    @Test
    public void selectBatchIdTest(){
        List<Integer> idList = new ArrayList<>();
        idList.add(14);
        idList.add(1);
        idList.add(2);
        idList.add(4);
        List<Employee> list = employeeMapper.selectBatchIds(idList);
        System.out.println("result："+list);
    }
    /**
     * 通过map封装条件查询
     */
    @Test
    public void selectByMapTest(){
        Map<String,Object> map = new HashMap<>(3);
        map.put("id",3);
        map.put("last_name","Black");
        List<Employee> result = employeeMapper.selectByMap(map);
        System.out.println(result);
    }
    /**
     * 分页查询
     */
    @Test
    public void selectPageTest(){
        List<Employee> page = employeeMapper.selectPage(new Page<>(2, 2), null);
        System.out.println(page);
    }
    /**
     * 通过id删除
     */
    @Test
    public void deleteByIdTest(){
        Integer result = employeeMapper.deleteById(15);
        System.out.println(result);
    }
    /**
     * 通过id批量删除
     */
    @Test
    public void deleteBatchIdTest(){
        List<Integer> idList = new ArrayList<>();
        idList.add(16);
        idList.add(17);
        Integer result = employeeMapper.deleteBatchIds(idList);
        System.out.println(result);
    }
    /**
     * 分页查询
     * EntityWrapper
     * 注：使用的是数据库的属性
     * 年龄为18-50，性别为男，名字为jack
     */
    @Test
    public void selectEntityWrapperTest(){
        List<Employee> employees = employeeMapper.selectPage(new Page<Employee>(1, 2),
                new EntityWrapper<Employee>()
                        .between("age", 18, 50)
                        .eq("gender", 1)
                        .eq("last_name", "jack"));
        System.out.println(employees);
    }
    /**
     * 模糊查询
     * 年龄在18-30之间 且 名字带有y 或者 邮箱带有w
     * or(): 在原sql语句拼接or
     * orNew(): 在新的sql语句拼接一个or
     */
    @Test
    public void selectListTest(){
        List<Employee> employees = employeeMapper.selectList(new EntityWrapper<Employee>()
                .between("age", 18, 30)
                .like("last_name", "y")
                .orNew()
                .like("email", "w")
        );
        System.out.println(employees);
    }
    /**
     * 条件构造器 修改操作
     * 将last_name为"小邹"且gender为0的邮箱和年龄修改
     */
    @Test
    public void updateEntityWrapperTest(){
        Employee employee = new Employee();
        employee.setEmail("com@hq.cn");
        employee.setAge(19);

        Integer result = employeeMapper.update(employee, new EntityWrapper<Employee>()
                .eq("last_name", "小邹")
                .eq("gender", 0)
        );
        System.out.println(result);
    }
    /**
     * 条件构造器 删除操作
     * 将last_name为"jack"且age为21的邮箱和年龄删除
     */
    @Test
    public void deleteEntityWrapperTest(){
        Integer result = employeeMapper.delete(new EntityWrapper<Employee>()
                .eq("last_name", "jack")
                .eq("age", 21)
        );
        System.out.println(result);
    }
    /**
     * 查询数据库中gender为0的数据且按 降序 排列
     * orderBy 默认升序排列
     * orderDesc 传入的值是一个集合类型(Collection)
     */
    @Test
    public void selectByDescTest(){
        List<Employee> employees = employeeMapper.selectList(new EntityWrapper<Employee>()
                .eq("gender", 0)
                .orderDesc(Collections.singletonList("age"))
        );
        System.out.println(employees);
    }
    /**
     * 分页查询
     * Condition
     * 注：使用的是数据库的属性
     * 年龄为18-50，性别为男，名字为jack
     */
    @Test
    public void selectConditionTest(){
        List list = employeeMapper.selectPage(new Page<Employee>(1, 2)
                , Condition.create()
                        .between("age", 18, 50)
                        .eq("gender", 1)
                        .eq("last_name", "jack")
        );
        System.out.println(list);
    }
}
