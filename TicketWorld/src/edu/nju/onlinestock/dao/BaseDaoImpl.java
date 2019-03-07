package edu.nju.onlinestock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.utils.HibernateUtil;


public class BaseDaoImpl implements BaseDao {
	
	public BaseDaoImpl() {
	}

	public void flush() {
		HibernateUtil.getSession().flush();
	}

	public void clear() {
		HibernateUtil.getSession().clear();
	}

	
	
	/** * 根据 id 查询信息 * * @param id * @return */
	@SuppressWarnings("rawtypes")
	public Object load(Class c, String id) {
		try {
			Session session = HibernateUtil.getSession();
			Transaction tx=session.beginTransaction();
			Object o=session.get(c, id);
			tx.commit();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	public Object load(Class c, int id) {
		try {
			Session session = HibernateUtil.getSession();
			Transaction tx=session.beginTransaction();
			Object o=session.get(c, id);
			tx.commit();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	
	/** * 获取所有信息 * * @param c * * @return */

	public List getAllList(Class c) {
		String hql="from "+c.getName();
        Session session=HibernateUtil.getSession();
        Transaction tx=session.beginTransaction();
        List result=session.createQuery(hql).list();
        tx.commit();
        return result;

	}

	/** * 获取总数量 * * @param c * @return */

	public Long getTotalCount(Class c) {
		Session session=HibernateUtil.getSession();
		Transaction transaction=session.beginTransaction();
        String hql="select count(*) from "+c.getName();
        Long count=(Long) session.createQuery(hql).uniqueResult();
        transaction.commit();
        session.close();
        return count!=null?count.longValue():0;
	}

	/** * 保存 * * @param bean * */
	public void save(Object bean) {
		try {
//			System.out.println("ready to getsession");	
			Session session =HibernateUtil.getSession() ;
			Transaction tx=session.beginTransaction();
			session.merge(bean);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	/** * 更新 * * @param bean * */
	public void update(Object bean) {
		//
		try {
//			System.out.println("ready to getsession");	
			Session session =HibernateUtil.getSession() ;
			Transaction tx=session.beginTransaction();
			session.update(bean);;
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	/** * 删除 * * @param bean * */
	public void delete(Object bean) {

		try {
		//	System.out.println("ready to getsession");	
			Session session =HibernateUtil.getSession() ;
			Transaction tx=session.beginTransaction();
			session.delete(bean);;
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/** * 根据ID删除 * * @param c 类 * * @param id ID * */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String id) {
		//
		Session session=HibernateUtil.getSession();
		Transaction tx=session.beginTransaction();
		Object obj=session.get(c,id);
        if(obj!=null){
            session.delete(obj);
            tx.commit();
        }
	}

	/** * 批量删除 * * @param c 类 * * @param ids ID 集合 * */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String[] ids) {
		//
		Session session=HibernateUtil.getSession();
		Transaction tx=session.beginTransaction();
		for(String id:ids){
            Object obj=session.get(c,id);
            if(obj!=null){
            session.delete(obj);
            }
        }
		tx.commit();
	}
	
	public List query(String hql) {
        Session session=HibernateUtil.getSession();
        Transaction tx=session.beginTransaction();
        List result=session.createQuery(hql).list();
        tx.commit();
        return result;
    }

    public List querySQL(String sql) {
        Session session=HibernateUtil.getSession();
        Transaction tx=session.beginTransaction();
        List result=session.createNativeQuery(sql).list();
        tx.commit();
        return result;
    }

    public Long getCount(String hql) {
        Session session=HibernateUtil.getSession();
        Transaction tx=session.beginTransaction();
        List result=session.createQuery(hql).list();
        Long count=(Long) session.createQuery(hql).uniqueResult();
        tx.commit();
        session.close();
        return count!=null? count.longValue():0;
    }

    public int excuteBySql(String sql) {
       
        Session session=HibernateUtil.getSession();
        Transaction tx=session.beginTransaction();
        int result=session.createNativeQuery(sql).executeUpdate();
        
        return result;
    }
}
