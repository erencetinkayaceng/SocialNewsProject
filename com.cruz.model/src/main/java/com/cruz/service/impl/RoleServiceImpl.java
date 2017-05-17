package com.cruz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.RoleRepository;
import com.cruz.model.user.Role;
import com.cruz.service.RoleService;

@Service("RoleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.saveRole(role);
	}

	@Override
	public void deleteRole(Role role) {
		// TODO Auto-generated method stub
		roleRepository.deleteRole(role);
	}

	@Override
	public Role updateRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.updateRole(role);
	}

	@Override
	public Role findRole(Long roleID) {
		// TODO Auto-generated method stub
		return roleRepository.findRole(roleID);
	}

}
