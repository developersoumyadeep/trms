package com.wbsedcl.trms.substation.log.application.security.service;

import com.wbsedcl.trms.substation.log.application.security.dto.AuthenticationRequest;
import com.wbsedcl.trms.substation.log.application.security.dto.AuthenticationResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response);
}
