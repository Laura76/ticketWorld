package edu.nju.onlinestock.model;

import javax.persistence.*;

@Entity
@Table(name="currentSpareRoomInfo")
public class CurrentSpareRoomInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String hostelNum;
    private int roomTypeId;
    private int spareNum;

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

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getSpareNum() {
        return spareNum;
    }

    public void setSpareNum(int spareNum) {
        this.spareNum = spareNum;
    }
}
