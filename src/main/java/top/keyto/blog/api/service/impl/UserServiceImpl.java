package top.keyto.blog.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.keyto.blog.api.dao.SysRoleDao;
import top.keyto.blog.api.dao.UserDao;
import top.keyto.blog.api.entity.SysRole;
import top.keyto.blog.api.entity.SysUser;
import top.keyto.blog.api.exception.RegisterException;
import top.keyto.blog.api.service.UserService;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Keyto
 * Created on 2019/10/28
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;
    private final SysRoleDao sysRoleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, SysRoleDao sysRoleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.sysRoleDao = sysRoleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Username: {}", username);
        Optional<SysUser> optionalUser = userDao.findByUsername(username);
        if (optionalUser.isPresent()) {
            SysUser sysUser = optionalUser.get();
            List<GrantedAuthority> authorities = new LinkedList<>();
            for (SysRole role : sysUser.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return User.withUsername(sysUser.getUsername()).password(sysUser.getPassword()).authorities(authorities).build();
        }
        throw new UsernameNotFoundException("Username Not Found");
    }

    @Override
    public SysUser register(String username, String password) {

        SysUser user = new SysUser();
        user.setUsername(username);
        if (userDao.exists(Example.of(user))) {
            throw new RegisterException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(password));
        Set<SysRole> roleSet = new HashSet<>();
        roleSet.add(sysRoleDao.getOne(2L));
        user.setRoles(roleSet);

        return userDao.save(user);
    }
}
