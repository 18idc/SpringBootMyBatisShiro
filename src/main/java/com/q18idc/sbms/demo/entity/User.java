package com.q18idc.sbms.demo.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户实体类
 * @author q18idc.com QQ993143799
 */

@Table(name = "tb_user")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class User extends Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String salt;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
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