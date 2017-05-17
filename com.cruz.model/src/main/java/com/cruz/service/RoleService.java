package com.cruz.service;

import com.cruz.model.user.Role;

public interface RoleService {
	Role saveRole(Role role);

	void deleteRole(Role role);

	Role updateRole(Role role);

	Role findRole(Long roleID);
}
