package com.q18idc.sbms.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_user")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class User extends Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String phone;

    private String email;

    private String sex;

    private Date birthday;

    public User(Integer id, String username, String password, String salt, String phone, String email, String sex, Date birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        if (birthday != null) {
            this.birthday = (Date)birthday.clone();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        if (birthday != null) {
            return (Date) birthday.clone();
        }
        return null;
    }

    public void setBirthday(Date birthday) {
        if (birthday != null) {
            this.birthday = (Date) birthday.clone();
        }
    }
}