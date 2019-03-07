package edu.nju.onlinestock.service;

import edu.nju.onlinestock.model.DiscountStrategy;

public interface DiscountStrategyService {
    /**
     * 获得对应的优惠策略
     * @param level
     * @return
     */
    public DiscountStrategy getDiscount(int level);
}
