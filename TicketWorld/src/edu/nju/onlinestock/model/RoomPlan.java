package edu.nju.onlinestock.model;

import javax.persistence.*;

@Entity
@Table(name= "roomPlan")
public class RoomPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String hostelNum;
    private int roomTypeId;
    private int roomNum;
    private double roomPrice;
    private String startDate;
    private String endDate;
    private String date;
    private String showType;
    private String showInfo;

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getHostelNum() {
        return hostelNum;
    }

    public void setHostelNum(String hostelNum) {
        this.hostelNum = hostelNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
}
