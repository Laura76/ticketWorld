package edu.nju.onlinestock.dao;

import java.util.List;

import org.hibernate.Session;


public interface BaseDao {

//	public Session getSession();

//	public Session getNewSession();
	
	public void flush();

	public void clear() ;

	public List getAllList(Class c) ;
	
	public Long getTotalCount(Class c) ;
	
	public Object load(Class c, String id) ;
	
	public Object load(Class c, int id) ;

	public void save(Object bean) ;

	public void update(Object bean) ;

	public void delete(Object bean) ;
	
	public void delete(Class c, String id) ;

	public void delete(Class c, String[] ids) ;
	
	public List query(String hql);

    public List querySQL(String sql);

    public Long getCount(String hql);

    public int excuteBySql(String sql);
}
