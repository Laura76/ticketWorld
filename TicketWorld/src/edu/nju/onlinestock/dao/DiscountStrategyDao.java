package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.model.DiscountStrategy;

public interface DiscountStrategyDao {
    /**
     * 新增优惠策略
     * @param discountStrategy
     */
    public void save(DiscountStrategy discountStrategy);

    /**
     * 修改优惠策略
     * @param discountStrategy
     */
    public void update(DiscountStrategy discountStrategy);

    /**
     * 查找优惠策略
     * @return
     */
    public DiscountStrategy find(int vipLevel);
}
