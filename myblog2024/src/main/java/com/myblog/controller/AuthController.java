package com.myblog.controller;

import com.myblog.entity.Role;
import com.myblog.entity.User;
import com.myblog.payload.JWTAuthResponse;
import com.myblog.payload.LoginDto;
import com.myblog.payload.SignUpDto;
import com.myblog.repository.RoleRepository;
import com.myblog.repository.UserRepository;
import com.myblog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
if(userRepository.existsByEmail(signUpDto.getEmail())){
new ResponseEntity<>("Email already exists"+signUpDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
}
if(userRepository.existsByUsername(signUpDto.getUsername())){
new ResponseEntity<>("Username already exists"+signUpDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
}
User user =new User();
user.setName(signUpDto.getName());
user.setEmail(signUpDto.getEmail());
user.setUsername(signUpDto.getUsername());
user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        Set<Role> role = new HashSet<Role>();
        role.add(roles);
        user.setRoles(role);

// By default for all sign up it will create admin role

        userRepository.save(user);
return new ResponseEntity<>("user Registered successfully",HttpStatus.CREATED);
    }

//    @PostMapping("/signin")
 //   public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        //This is old one Used for authentication
//        Authentication authentication = authenticationManager.authenticate
//                (new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
//                        loginDto.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
          @PostMapping("/signin")
    //http://localhost:8080/api/auth/signin
          public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                          loginDto){
              Authentication authentication = authenticationManager.authenticate(new
                      UsernamePasswordAuthenticationToken(
                      loginDto.getUsernameOrEmail(), loginDto.getPassword()));
              SecurityContextHolder.getContext().setAuthentication(authentication);
              // get token form tokenProvider
              String token = tokenProvider.generateToken(authentication);
              return ResponseEntity.ok(new JWTAuthResponse(token));
          }

}







