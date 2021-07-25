package com.demo.example.TestService.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.demo.example.TestService.dto.UserResource;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<UserResource> findUserByCitys(Set<String> cities) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserResource> cQuery = cb.createQuery(UserResource.class);
		Root<UserResource> root = cQuery.from(UserResource.class);
		cQuery.select(root);
//		List<Predicate> predicates = new ArrayList<>();
//		for(String city : cities) {
//			predicates.add(cb.equal(root.get("city"), city));
//		}
//		cQuery.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		cQuery.where(root.get("city").in(cities));
		cQuery.orderBy(cb.desc(root.get("createdAt")));
		return entityManager.createQuery(cQuery).getResultList();
	}

}
