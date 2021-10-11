package com.mabo.entity;

public class UserBaseInfo {
    private String id;
    private String name;
    private String birthday;
    private String email;
    private String phone;
    private String sex;
    private String idCardNumber;
    private String type;

    public UserBaseInfo(String id, String name, String birthday, String email, String phone, String sex, String idCardNumber, String type) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.idCardNumber = idCardNumber;
        this.type = type;
    }
    public UserBaseInfo() {
    }

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
