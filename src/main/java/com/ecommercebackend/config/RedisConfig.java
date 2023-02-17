package com.ecommercebackend.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.ecommercebackend.model.Url;
import com.ecommercebackend.payload.response.user.UserLoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

	  @Autowired
	  ObjectMapper mapper;

	  @Autowired
	  RedisConnectionFactory connectionFactory;

	  @Bean
	  RedisTemplate<String, UserLoginResponse> redisTemplate() {
	    final RedisTemplate<String, UserLoginResponse> redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(connectionFactory);
	    return redisTemplate;
	  }
}
