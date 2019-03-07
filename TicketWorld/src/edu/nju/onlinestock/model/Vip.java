package edu.nju.onlinestock.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="vip")
public class Vip implements Serializable {

    @Id
    private String vipNum;

    private String vipName;
    private String vipPassword;
    private String bankCardId;
    private double vipPoint;
    private double money;
    private String state;
    private String activateDate;
    private String validDate;
    private Integer vipLevel;
    //添加了邮箱账号，邮箱状态（0为未激活，1为激活）；邮箱激活码
    private String email;
    private Integer emailState;
    private String emailCode;
    private String emailCode_exptime;
    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getVipNum() {
        return vipNum;
    }

    public void setVipNum(String vipNum) {
        this.vipNum = vipNum;
    }

    public String getVipPassword() {
        return vipPassword;
    }

    public void setVipPassword(String vipPassword) {
        this.vipPassword = vipPassword;
    }

    public double getVipPoint() {
        return vipPoint;
    }

    public void setVipPoint(double vipPoint) {
        this.vipPoint = vipPoint;
    }

    public String getActivateDate() {
        return activateDate;
    }

    public void setActivateDate(String activateDate) {
        this.activateDate = activateDate;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }
    public Integer getEmailState() {
        return emailState;
    }

    public void setEmailState(Integer emailState) {
        this.emailState = emailState;
    }
    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmailCode_exptime() {
        return emailCode_exptime;
    }

    public void setEmailCode_exptime(String emailCode_exptime) {
        this.emailCode_exptime = emailCode_exptime;
    }
}
