package com.lake.hmediacenterserver.respository;

import com.lake.hmediacenterserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问层，继承 JpaRepository 提供基本的 CRUD 操作
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return User 实体
     */
    User findByUsername(String username);
}
