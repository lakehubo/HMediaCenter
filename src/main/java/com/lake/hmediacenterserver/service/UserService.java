package com.lake.hmediacenterserver.service;

import com.lake.hmediacenterserver.common.BizException;
import com.lake.hmediacenterserver.common.JwtUtil;
import com.lake.hmediacenterserver.entity.User;
import com.lake.hmediacenterserver.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lake.hmediacenterserver.security.SecurityUser;
/**
 * 用户服务层，处理与用户相关的业务逻辑
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 登录验证、token生成方法示例
    public boolean checkPassword(String raw, String encoded) {
        // 注入 PasswordEncoder
        return new BCryptPasswordEncoder().matches(raw, encoded);
    }

    public String generateToken(User user) {
        return JwtUtil.generateToken(user.getUsername());
    }

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return User 对象
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 保存用户信息
     * @param user User 对象
     * @return 保存后的 User
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    @PostConstruct
    public void initAdminUser() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("已自动初始化管理员账号：admin / 123456（请及时修改密码）");
        }
    }

    /**
     * 修改密码
     * @return 是否修改成功
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = findByUsername(username);
        if (user == null) throw new BizException(404, "用户不存在");
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) return false;
        if (newPassword == null || newPassword.length() < 6)
            throw new BizException(400, "新密码长度不能小于6位");
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    /**
     * 登录生成token
     */
    public String login(String username, String password) {
        User user = findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword()))
            throw new BizException(401, "用户名或密码错误");
        if (!Boolean.TRUE.equals(user.getEnabled()))
            throw new BizException(403, "账号已被禁用");
        return JwtUtil.generateToken(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("用户不存在");
        return new SecurityUser(user.getUsername(), user.getPassword(),
                user.getEnabled() != null && user.getEnabled());
    }
}
