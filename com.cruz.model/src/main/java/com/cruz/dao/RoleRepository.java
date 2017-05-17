package com.cruz.dao;

import com.cruz.model.user.Role;

public interface RoleRepository {
	Role saveRole(Role role);

	void deleteRole(Role role);

	Role updateRole(Role role);

	Role findRole(Long roleID);
}
