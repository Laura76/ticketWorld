package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.model.Approval;

import java.util.List;

public interface ApprovalDao {

    /**
     * 查找申请
     * @return
     */
    public Approval find(int approvalNum);

    /**
     * 新增申请
     * @param approval
     */
    public void save(Approval approval);

    /**
     * 更新申请
     * @param approval
     */
    public void update(Approval approval);

    /**
     * 根据状态查询申请
     */
    public List<Approval> queryByState(String state);

    /**
     * 根据类型查询申请
     * @param type
     */
    public List<Approval> queryByType(String type);

    /**
     * 获得客栈的申请情况
     * @param hostelNum
     * @return
     */
    public List<Approval> queryByHostel(String hostelNum);

    /**
     * 获得所有申请
     */
    public List<Approval> getAllList();
}
