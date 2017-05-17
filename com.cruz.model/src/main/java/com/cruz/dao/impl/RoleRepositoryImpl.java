package com.cruz.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.RoleRepository;
import com.cruz.model.user.Role;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		this.entityManager.persist(role);
		return role;
	}

	@Override
	public void deleteRole(Role role) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(role)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			role.setEnabled(!role.isEnabled());
			updateRole(role);
		} else {
			Role deleteRole = findRole(role.getId());
			deleteRole.setEnabled(!deleteRole.isEnabled());
			updateRole(deleteRole);
		}

	}

	@Override
	public Role updateRole(Role role) {
		// TODO Auto-generated method stub
		Role updatedRole = entityManager.merge(role);
		this.entityManager.flush();

		return updatedRole;
	}

	@Override
	public Role findRole(Long roleID) {
		// TODO Auto-generated method stub
		if (roleID == null)
			return null;
		try {
			return this.entityManager.find(Role.class, roleID);
		} catch (NoResultException ex) {
			return null;
		}
	}

}
