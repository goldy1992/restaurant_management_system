package com.mike.server.database;

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
	
	public <T> void insert(T item) {
		Session currentSession = sessionFactory.openSession();
    	Transaction tx = null;
    	Long id = null;
    	try  {
    		tx = currentSession.beginTransaction();
			currentSession.save(item);
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
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
