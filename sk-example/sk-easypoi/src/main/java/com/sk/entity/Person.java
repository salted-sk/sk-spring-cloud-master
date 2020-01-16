package com.sk.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class Person {

    /**
     * id
     *
     * 导出时不加@Excel注释则不会导出该字段
     */
    private Integer id;

    @Excel(name = "姓名", orderNum = "0")
    private String name;

    /**
     * 带转换的属性
     */
    @Excel(name = "性别", replace = {"男_1", "女_2", "人妖_3"}, orderNum = "1")
    private String sex;

    @Excel(name = "生日", exportFormat = "yyyy-MM-dd", width = 20, orderNum = "2")
    private Date birthday;

    @Excel(name = "爱好", width = 45, orderNum = "3")
    private String habit;

    /**
     * 此处注意必须要有空构造函数，否则调用ExcelImportUtil.importExcel(file, Person.class, importParams)时会报错“对象创建错误”
     */
    public Person() {
    }

    public Person(Integer id, String name, String sex, Date birthday, String habit) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.habit = habit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }
}
