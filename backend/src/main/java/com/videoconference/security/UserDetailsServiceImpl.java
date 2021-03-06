package com.videoconference.security;

import com.videoconference.converter.UserConverter;
import com.videoconference.entity.Role;
import com.videoconference.entity.User;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        Set<Role> roles= user.getRoles();

        for(Role role : roles) {
            grantList.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        UserPrincipal users = new UserPrincipal(user.getUsername(),
                user.getPassword(), grantList);
        users.setUser(userConverter.toDTO(user));

//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), grantList);
        return users;
    }
}
