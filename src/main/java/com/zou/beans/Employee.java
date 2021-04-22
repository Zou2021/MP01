package com.zou.beans;

/**
 * @author: 邹祥发
 * @date: 2021/4/7 13:36
 * @TableName(value = "tbl_employee")
 * 指定数据库的表名(默认value为实体类的类名)
 * <property name="tablePrefix" value="tbl_"/>
 * 在applicationContext.xml中配置表前缀
 * 二者等效
 */
public class Employee{
    /**
     * 指定数据库表中的id字段为自增
     * @TableId(type = IdType.AUTO)
     * <property name="idType" value="0"/>
     * 二者等效
     */
    private Integer id ;
    private String lastName;
    private String email ;
    private Integer gender ;
    private Integer age ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
