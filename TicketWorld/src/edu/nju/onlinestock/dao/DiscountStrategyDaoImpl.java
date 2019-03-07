package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.DiscountStrategyDao;
import edu.nju.onlinestock.model.DiscountStrategy;

public class DiscountStrategyDaoImpl extends BaseDaoImpl implements DiscountStrategyDao {
    private BaseDao baseDao;

    private static DiscountStrategyDaoImpl discountStrategyDao = new DiscountStrategyDaoImpl();

	private DiscountStrategyDaoImpl() {

	}

	public static DiscountStrategyDaoImpl getInstance() {
		return discountStrategyDao;
	}
    @Override
    public void save(DiscountStrategy discountStrategy) {
        super.save(discountStrategy);
    }

    @Override
    public void update(DiscountStrategy discountStrategy) {
        super.update(discountStrategy);
    }

    @Override
    public DiscountStrategy find(int vipLevel) {
        DiscountStrategy discountStrategy=(DiscountStrategy)super.load(DiscountStrategy.class,String.valueOf(vipLevel));
        return discountStrategy;
    }
}
