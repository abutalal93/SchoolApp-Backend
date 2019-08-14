package com.decoders.school.security;

import com.decoders.school.entities.Parent;
import com.decoders.school.entities.School;
import com.decoders.school.exception.ResourceException;
import com.decoders.school.service.ParentService;
import com.decoders.school.service.SchoolService;
import com.decoders.school.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SchoolUserDetails implements UserDetailsService {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ParentService parentService;

    @Autowired
    private StatusService statusService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final School school = schoolService.findSchoolByUsername(username);

        final Parent parent = parentService.findParent(username, statusService.findStatusByCode("ACTIVE"));

        if (school == null) {
            if(parent == null){
                throw new ResourceException(HttpStatus.NOT_FOUND, "User '" + username + "' not found");
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password( (school == null) ? (parent.getMobile()): (school.getPassword()))
                .authorities("admin")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
