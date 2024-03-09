package com.wbsedcl.trms.substation.log.security.service;


import com.wbsedcl.trms.substation.log.security.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SublogUserDetailsService implements UserDetailsService {
    private final UserDetailsRepository userRepository;

    @Autowired
    public SublogUserDetailsService(UserDetailsRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByMobileNumber(username).orElseThrow();
    }
}
