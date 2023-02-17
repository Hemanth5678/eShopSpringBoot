package com.ecommercebackend.service.impl;


import java.util.Optional;

import com.ecommercebackend.dto.User;
import com.ecommercebackend.enums.EAccountStatus;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.UserMapper;
import com.ecommercebackend.payload.request.user.UserLoginRequest;
import com.ecommercebackend.payload.request.user.UserRegisterRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.user.UserLoginResponse;
import com.ecommercebackend.repository.UserRepository;
import com.ecommercebackend.security.CustomUserDetails;
import com.ecommercebackend.service.AuthService;
import com.ecommercebackend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
//@EnableCaching
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    
    @Autowired
    private RedisTemplate<String, UserLoginResponse> redisTemplate;

    @Override
//  @Cacheable(value = "user", key = "#userRegisterRequest.getEmail()")
  public SuccessResponse<NoData> userRegister(UserRegisterRequest userRegisterRequest) throws BadRequestException {
      Optional<User> userOpt = userRepository.findByEmail(userRegisterRequest.getEmail());
      if (userOpt.isPresent()) {
          throw new BadRequestException("This email already exists.");
      }
      userOpt = userRepository.findByPhone(userRegisterRequest.getPhone());
      if (userOpt.isPresent()) {
          throw new BadRequestException("This phone number already exists.");
      }
      User user = userMapper.toUser(userRegisterRequest);
      userRepository.save(user);
      return new SuccessResponse<>(NoData.builder().build(), "Registered successfully.");
  }
    
    @Override
//    @Cacheable(value = "user", key = "#userLoginRequest.getEmail()")
    public SuccessResponse<UserLoginResponse> loginHandler(UserLoginRequest userLoginRequest)
            throws BadRequestException {
        Optional<User> userOpt = userRepository.findByEmail(userLoginRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
                if (user.getStatus() == EAccountStatus.ACTIVE) {
                    UserLoginResponse userLoginResponse = userMapper.toUserLoginResponse(user);
                    userLoginResponse.setAccessToken(jwtTokenUtil.generateAccessToken(user));
                    userLoginResponse.setRefreshToken(jwtTokenUtil.generateRefreshToken(user));
                    return new SuccessResponse<>(userLoginResponse, "Logged in successfully.");
                }
                if (user.getStatus() == EAccountStatus.INACTIVE) {
                    throw new BadRequestException("The account has not been activated.");
                }
                if (user.getStatus() == EAccountStatus.BLOCK) {
                    throw new BadRequestException("This account has been blocked.");
                }
            }
        }
        throw new BadRequestException("Email or password is incorrect.");
    }

    @Override
    public User getUserLoggedIn() throws NotFoundException {
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }
        throw new NotFoundException("User does not exist.");
    }



}
