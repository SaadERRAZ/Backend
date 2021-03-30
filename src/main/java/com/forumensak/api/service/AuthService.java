package com.forumensak.api.service;

import com.forumensak.api.exception.AppException;
import com.forumensak.api.exception.BadRequestException;
import com.forumensak.api.model.*;
import com.forumensak.api.model.company.AboutCompany;
import com.forumensak.api.model.cv.About;
import com.forumensak.api.payload.*;
import com.forumensak.api.repository.RoleRepository;
import com.forumensak.api.repository.UserRepository;
import com.forumensak.api.repository.VerificationTokenRepository;
import com.forumensak.api.security.JwtTokenProvider;
import com.forumensak.api.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider tokenProvider;
    VerificationTokenRepository verificationTokenRepository;
    MailService mailService;

    public ResponseEntity<?> signIn(SignInRequest signInRequest) {

        if (userRepository.findByUsernameOrEmail(signInRequest.getUsernameOrEmail(), signInRequest.getUsernameOrEmail()).get().getEnabled() == false) {
            return new ResponseEntity(new ApiResponse(false, "Enable your account first!"),
                    HttpStatus.UNAUTHORIZED);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getUsernameOrEmail(),
                            signInRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } catch (Exception e) {
            return new ResponseEntity(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<?> signUp(SignUpRequest signUpRequest, long roleId) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getCompanyName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        user.setEnabled(false);
        if (userRole.getId() == 1) {
            user.setCv(new Cv());
            user.getCv().setFlag(false);
            user.getCv().setAbout(new About());
        } else if (userRole.getId() == 3) {
            user.setCompany(new Company());
            user.getCompany().setFlag(false);
            user.getCompany().setAboutCompany(new AboutCompany());
        }
        else if(userRole.getId() == 2){
            user.setEnabled(true);
        }
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        //Generate token
        String token = generateVerificationToken(user);
        if (userRole.getId() == 1) {
            mailService.sendEmail(new NotificationEmail("Please activate your account",
                    user.getEmail(), "Thank you for signing up to forum ensak," +
                    "please click on the below url to activate your account :\n" +
                    "<a href=\"http://localhost:3000/confirm?token=" + token + "\">Validate</a>"));
        }
        if (userRole.getId() == 3) {
            mailService.sendEmail(new NotificationEmail("Activate accout for enterprise manager",
                    "aymane.elmouhtarim@gmail.com", "Activate user," +user.getUsername()+", "+ user.getEmail() +
                    "by clicking here :\n" +
                    "<a href=\"http://localhost:3000/admin"+"\">Check it out</a>"));
        }

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully, Please enable your account through your mail box"));
    }
    public ResponseEntity<?> verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new AppException("Invalid Token"));
        User user=fetchUserAndEnable(verificationToken.get());
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        ),null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public User fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("User no longer exist in database"));
        user.setEnabled(true);
        userRepository.save(user);
        return user;

    }

}
