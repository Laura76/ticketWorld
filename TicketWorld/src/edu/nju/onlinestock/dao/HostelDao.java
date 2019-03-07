package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.model.Hostel;

import java.util.List;

public interface HostelDao {
    /**
     * 验证客栈是否存在
     * @param hostelNum
     * @return
     */
    public boolean checkHostel(String hostelNum);

    /**
     * 验证客栈是否被审批通过
     * @param hostelNum
     * @return
     */
    public boolean checkApprove(String hostelNum);

    /**
     * 验证客栈编号与密码正确
     * @param hostelNum
     * @param hostelPassword
     * @return
     */
    public boolean checkPassword(String hostelNum,String hostelPassword);

    /**
     * 新增客栈
     * @param hostel
     */
    public void save(Hostel hostel);

    /**
     * 获得可用客栈编号
     * @return
     */
    public String getHostelNum();

    /**
     * 根据客栈id删除客栈
     * @param hostelNum
     */
    public void deleteHostel(String hostelNum);

    /**
     * 修改客栈信息
     * @param hostel
     */
    public void updateHostel(Hostel hostel);

    /**
     * 根据省市查找客栈
     * @param province
     * @return
     */
    public List<Hostel> queryHostelByProvince(String province);
    public List<Hostel> queryHostelByCity(String city);

    /**
     * 根据客栈名查找客栈
     * @param hostelName
     * @return
     */
    public List<Hostel> queryHostelByName(String hostelName);


    /**
     * 根据客栈编号查找客栈
     * @param hostelNum
     * @return
     */
    public Hostel queryHostelByNum(String hostelNum);

    /**
     * 根据客栈等级筛选客栈
     * @param level
     * @return
     */
    public List<Hostel> queryHostelByLevel(int level);

    /**
     * 获得所有客栈
     * @return
     */
    public List<Hostel> queryAll();

    /**
     * 根据审批情况获得客栈信息
     * @param approveState
     * @return
     */
    public List<Hostel> queryByApprove(String approveState);
}
