package model.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.User;

public class UserDao extends BaseEntityDao<User> {
	
	private UserDao(){
		super(User.class);
	}
	
	private static UserDao instance = new UserDao();
	
	public static UserDao getInstance(){return instance;};
	
	public User getByUsernick(EntityManager em, String usernick){
	    CriteriaBuilder builder = em.getCriteriaBuilder();
	    CriteriaQuery<User> criteria = builder.createQuery(User.class);
	    Root<User> from = criteria.from(User.class);
	    criteria.select(from);
	    criteria.where(builder.equal(from.get("usernick"), usernick));
	    TypedQuery<User> typed = em.createQuery(criteria);
	    try {
	        return typed.getSingleResult();
	    } catch (final NoResultException nre) {
	        return null;
	    }
	}
	
}
