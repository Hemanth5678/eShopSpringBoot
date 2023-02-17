package com.ecommercebackend.graphQL.resolver;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.repository.UserRepository;
import com.ecommercebackend.service.UserService;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class UserResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userServices;


	
    public Collection<User> findAllUsers() {
        return userRepository.findAll();
    }
}
