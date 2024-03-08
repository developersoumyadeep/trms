package com.wbsedcl.trms.substation.log.application.security.service;


import com.wbsedcl.trms.substation.log.application.security.dto.AuthenticationRequest;
import com.wbsedcl.trms.substation.log.application.security.dto.AuthenticationResponse;
import com.wbsedcl.trms.substation.log.application.security.entity.User;
import com.wbsedcl.trms.substation.log.domain.entity.Office;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final OfficeRepository officeRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, OfficeRepository officeRepository) {
        this.authenticationManager = authenticationManager;
        this.officeRepository = officeRepository;
        this.securityContextRepository = new HttpSessionSecurityContextRepository();
        this.securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) {
        //Convert the authentication request into UsernamePasswordAuthentication token
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        //Use the AuthenticationManager to authenticate the user
        Authentication userAuthentication = authenticationManager.authenticate(authentication);
//        logger.debug("Authenticated authentication object {}", userAuthentication.toString());
        //If the user is authenticated then return successful response entity
        if (userAuthentication.isAuthenticated()) {
            //Get the principal from the Authentication object
            User user = (User) userAuthentication.getPrincipal();
            //Get the office name
//            String officeName = officeRepository.findOffice(user.getOfficeId()).map(Office::getOfficeText).orElseThrow();
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(userAuthentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            logger.debug("user authentication successful!");
            return new AuthenticationResponse(user.getUserId(), user.getFirstName(), user.getLastName(), "officeName");
        }
        //Otherwise send unsuccessful response entity with empty details
        return new AuthenticationResponse();

    }

}
