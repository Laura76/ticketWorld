package edu.nju.onlinestock.service;

import edu.nju.onlinestock.model.RoomType;

public interface RoomTypeService {

    /**
     * 根据id查找对应房间类型
     * @param id
     * @return
     */
    public RoomType find(int id);

    /**
     * 根据类型查找对应id
     * @param type
     * @return
     */
    public RoomType queryByType(String type);
}
