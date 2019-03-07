package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.model.BankCard;


public interface BankCardDao {
    /**
     * 新建银行卡
     * @param bankCard
     */
    public void save(BankCard bankCard);

    /**
     * 修改余额
     * @param bankCard
     */
    public void update(BankCard bankCard);

    /**
     * 查询余额
     * @param bankCardId
     * @return
     */
    public BankCard find(String bankCardId);
}
