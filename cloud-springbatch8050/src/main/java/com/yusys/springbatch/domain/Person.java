package com.yusys.springbatch.domain;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * person.csv中的字段与之对应，并在该实体中可以添加校验注解，
 * 如@Size表示该字段的长度范围，如果超过规定。则会被校验检测到，批处理将不会进行！
 *
 * @author huyang
 * @date 2020/7/27 15:16
 */
public class Person implements Serializable {
    
    private final long serialVersionUID = 1L;

    private String id;

    @Size(min = 2, max = 8)
    private String name;

    private int age;

    private String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
