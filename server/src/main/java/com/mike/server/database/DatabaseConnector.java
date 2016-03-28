package com.mike.server.database;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mike.server.database.tables.FOOD_OR_DRINK;
import com.mike.server.database.tables.ITEMS;


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
	
	public <T> void update(T item) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	try  {
    		tx = currentSession.beginTransaction();
			currentSession.update(item);
        	tx.commit();
    	}catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         }finally {
            currentSession.close(); 
         }		
	}

	public List query(String query) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	List results = null;
    	try  {
    		tx = currentSession.beginTransaction();
			results = currentSession.createQuery(query).list();
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
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
