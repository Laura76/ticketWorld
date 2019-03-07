package edu.nju.onlinestock.service;

import edu.nju.onlinestock.model.BalanceSettle;

import java.util.List;

public interface BalanceSettleService {

    /**
     * 获得所有结算信息
     * @return
     */
    public List<BalanceSettle> getAll(String date);

    /**
     * 获得所有结算历史
     * @return
     */
    public List<BalanceSettle> getAllList();
}
