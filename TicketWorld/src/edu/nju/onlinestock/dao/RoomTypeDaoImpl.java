package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.RoomTypeDao;
import edu.nju.onlinestock.model.RoomType;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeDaoImpl extends BaseDaoImpl implements RoomTypeDao {
    private BaseDao baseDao;

    private static RoomTypeDaoImpl roomTypeDao = new RoomTypeDaoImpl();

	private RoomTypeDaoImpl() {

	}

	public static RoomTypeDaoImpl getInstance() {
		return roomTypeDao;
	}
    @Override
    public void save(RoomType roomType) {
        super.save(roomType);
    }

    @Override
    public void delete(int id) {
        super.delete(RoomType.class,String.valueOf(id));
    }

    @Override
    public RoomType find(int id) {
        RoomType roomType =(RoomType)super.load(RoomType.class,id);
        return roomType;
    }

    @Override
    public RoomType queryByType(String type) {
        String sql="select * from hostelworld.roomType rt where rt.roomType=\""+type+"\";";
        List<Object[]> objects =super.querySQL(sql);
        return this.getRoomType(objects).get(0);
    }

    @Override
    public List<RoomType> getAllType() {
        return super.getAllList(RoomType.class);
    }

    @Override
    public boolean isExist(String type) {
        if(this.queryByType(type)!=null){
            return true;
        }
        return false;
    }

    @Override
    public long getNextId() {
        return super.getTotalCount(RoomType.class)+1;
    }

    private List<RoomType> getRoomType(List<Object[]> objects){
        List<RoomType> roomTypes=new ArrayList<>();
        for(Object[] object:objects){
            RoomType roomType=new RoomType();
            roomType.setId((int)object[0]);
            roomType.setRoomType(String.valueOf(object[1]));
            roomTypes.add(roomType);
        }
        return roomTypes;
    }

}
