package com.ecommercebackend.service;

import java.util.Optional;

import com.ecommercebackend.enums.ERoles;
import com.ecommercebackend.exception.IdNotFoundException;

public interface RoleService {
	
	public String addRole(ERoles role);
	public void deleteRole(int roleId) throws IdNotFoundException;
	public Optional<ERoles> getRoleById(int roleId);
}
