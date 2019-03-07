package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BankCardDao;
import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.model.BankCard;

public class BankCardDaoImpl extends BaseDaoImpl  implements BankCardDao {
    private BaseDao baseDao;
    private static BankCardDaoImpl bankCardDao = new BankCardDaoImpl();

	private BankCardDaoImpl() {

	}

	public static BankCardDaoImpl getInstance() {
		return bankCardDao;
	}

    @Override
    public void save(BankCard bankCard) {
        super.save(bankCard);
    }

    @Override
    public void update(BankCard bankCard) {
    		super.update(bankCard);
    }

    @Override
    public BankCard find(String bankCardId) {
        BankCard bankCard=(BankCard)super.load(BankCard.class,bankCardId);
        return bankCard;
    }
}
