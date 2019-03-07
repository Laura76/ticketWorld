package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.ManagerDao;
import edu.nju.onlinestock.model.Manager;
import java.text.DecimalFormat;
import java.util.List;

public class ManagerDaoImpl extends BaseDaoImpl implements ManagerDao {

    private BaseDao baseDao;

    private static ManagerDaoImpl managerDao = new ManagerDaoImpl();

	private ManagerDaoImpl() {

	}

	public static ManagerDaoImpl getInstance() {
		return managerDao;
	}
    @Override
    public boolean checkManager(String managerNum) {
        Manager manager= (Manager) super.load(Manager.class,managerNum);
        if(null!=manager){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPassword(String managerNum, String password) {
        Manager manager= (Manager) super.load(Manager.class,managerNum);
        if(manager.getManagerPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public void save(Manager manager) {
    		super.save(manager);
    }

    @Override
    public void update(Manager manager) {
    		super.update(manager);
    }

    @Override
    public List<Manager> getAllList() {
        return super.getAllList(Manager.class);
    }

    @Override
    public Manager queryByNum(String managerNum) {
        Manager manager=(Manager) super.load(Manager.class,managerNum);
        return manager;
    }

    @Override
    public String getManagerNum() {
        DecimalFormat df=new DecimalFormat("000000");
        long currentNum=super.getTotalCount(Manager.class);
        String newNum="M"+df.format(currentNum+1);
        return newNum;
    }
}
