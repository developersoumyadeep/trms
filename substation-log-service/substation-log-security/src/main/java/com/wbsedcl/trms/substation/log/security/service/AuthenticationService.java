package com.wbsedcl.trms.substation.log.security.service;

import com.wbsedcl.trms.substation.log.security.dto.AuthenticationRequest;
import com.wbsedcl.trms.substation.log.security.dto.AuthenticationResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response);
}
