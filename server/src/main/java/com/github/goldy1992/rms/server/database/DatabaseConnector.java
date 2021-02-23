package com.github.goldy1992.rms.server.database;

import com.github.goldy1992.rms.item.dbItem.ItemDAO;
import com.github.goldy1992.rms.item.dbItem.MenuPageDAO;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



@Transactional
public class DatabaseConnector {

	@Autowired
	private SessionFactory sessionFactory;

	
	public DatabaseConnector() {
		
	}
	
	public <T, R> R insert(T item) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	R id = null;
    	try  {
    		tx = currentSession.beginTransaction();
			id = (R) currentSession.save(item);
        	tx.commit();
    	}catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         }finally {
            currentSession.close(); 
         }
    	
    	return id;
	}
	
	public <T> boolean update(T item) {
		boolean success = true;
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	try  {
    		tx = currentSession.beginTransaction();
			currentSession.update(item);
        	tx.commit();
    	}catch (HibernateException e) {
			success = false;
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         }finally {
            currentSession.close(); 
         }
		return success;
	}

	public List query(String queryString, Map<String, ?> params) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	List results = null;
    	try  {
    		tx = currentSession.beginTransaction();
    		Query query = (Query) currentSession.createQuery(queryString);
    		if (params != null && !params.keySet().isEmpty()) {
    			for (String param : params.keySet()) {
    				query.setParameter(param, params.get(param));
    			}
    		}
			results = query.list();
        	tx.commit();
    	}catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         }finally {
            currentSession.close(); 
         }
        return results;
	}
	
	public <T> T getFirst(Class<T> t, String query) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	List<T> results = null;
    	try  {
    		tx = currentSession.beginTransaction();
			results = (List<T>)currentSession.createQuery(query).list();
        	tx.commit();
    	}catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         }finally {
            currentSession.close(); 
         }

        return (results == null || results.size() < 1) ? null : results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public <T, X extends Serializable> T get(@SuppressWarnings("rawtypes") Class t, X id) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	T result = null;
    	try  {
    		tx = currentSession.beginTransaction();
			result = (T) currentSession.get(t, id);
        	tx.commit();
    	} catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         } finally {
            currentSession.close(); 
         }
        return result;
	}
	
	public void updatePosInMenuTable(List<ItemDAO> newItems, String id) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	MenuPageDAO result = null;
    	try  {
    		tx = currentSession.beginTransaction();
			result = (MenuPageDAO)currentSession.get(MenuPageDAO.class, id);
			result.getItems().addAll(newItems);
			currentSession.update(result);
        	tx.commit();
    	} catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         } finally {
            currentSession.close(); 
         }		
	}

	public boolean updateStock(Map<Integer, Integer> itemQuantityMap) {
		// TODO: write code to take each item and add the relevant amount to stock
		return true;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
