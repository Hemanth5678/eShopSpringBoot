//package com.ecommercebackend.service.impl;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ecommercebackend.dto.Role;
//import com.ecommercebackend.exception.IdNotFoundException;
//import com.ecommercebackend.repository.RoleRepository;
//import com.ecommercebackend.service.RoleService;
//
//
//@Service
//public class RoleServiceImpl implements RoleService{
//
//	@Autowired
//	RoleRepository Rolerepository;
//	
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public String addRole(Role role) {
//		// TODO Auto-generated method stub
//		Role role2 = Rolerepository.save(role);
//		if(role2!=null)
//			return "roles added";
//		return "fail";
//	}
//
//	@Override
//	public void deleteRole(int roleId) throws IdNotFoundException {
//		// TODO Auto-generated method stub
//		Optional<Role> optional;
//		optional = this.getRoleById(roleId);
//		if(optional.isEmpty()) {
//			throw new IdNotFoundException("record not found");
//		}
//		else {
//			Rolerepository.deleteById(roleId);
//			
//	}
//	}
//
//	@Override
//	public Optional<Role> getRoleById(int roleId) {
//		// TODO Auto-generated method stub
//		return Rolerepository.findById(roleId);
//	}
//}
