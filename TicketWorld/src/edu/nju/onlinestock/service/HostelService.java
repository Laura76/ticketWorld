package edu.nju.onlinestock.service;

import edu.nju.onlinestock.model.Hostel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HostelService {
	public void sentErrorMessage(String message,HttpServletRequest req) 
			throws ServletException,IOException;

	public void sentMessage(String message,HttpServletRequest req) 
			throws ServletException,IOException;

	public void forwardPage(String page,HttpServletRequest req,HttpServletResponse resp) 
			throws ServletException,IOException;

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
     * 新注册一家客栈
     * @param hostel
     */
    public void registerHostel(Hostel hostel);

    /**
     * 获得可用客栈编号
     * @return
     */
    public String getHostelNum();

    /**
     * 更新客栈信息
     * 需要审批，默认状态为disapprove
     * @param hostel
     */
    public void updateHostel(Hostel hostel);

    /**
     * 删除客栈
     * @param hostelNum
     */
    public void deleteHostel(String hostelNum);

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
     * 根据审批状态获得订单
     * @param approveState
     * @return
     */
    public List<Hostel> queryByApprove(String approveState);

}
