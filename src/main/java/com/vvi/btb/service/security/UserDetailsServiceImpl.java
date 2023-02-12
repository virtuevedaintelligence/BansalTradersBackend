package com.vvi.btb.service.security;

import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static com.vvi.btb.constant.UserImplConstant.BY;

@Component
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(username).map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(UserImplConstant.USER_NOT_FOUND));
    }
}
