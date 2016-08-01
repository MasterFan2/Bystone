package com.proton.bystone.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Brightbeacon on 2016/7/4 0004.
 */
@Table(name = "Student")
public class Student {

    @Column(name = "_id", isId = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private int gender;

    public Student() {//如果要用数据缓存，必须要有默认构造函数
    }

    public Student(long id, String name, int gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
