package model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public abstract class BaseEntityDao<T> {
	private Class<T> clazz;

	public BaseEntityDao(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T getById(EntityManager em, long id) {
		return em.find(clazz, id);
	}

	public List<T> findAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> rootEntry = cq.from(clazz);
		CriteriaQuery<T> all = cq.select(rootEntry);
		TypedQuery<T> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	public void persist(EntityManager em, T entity) {
		em.persist(entity);
	}

	public T update(EntityManager em, T entity) {
		return em.merge(entity);
	}

	public void delete(EntityManager em, T entity) {
		em.remove(entity);
	}

	public void deleteById(EntityManager em, long entityId) {
		T entity = getById(em, entityId);
		delete(em, entity);
	}
}
