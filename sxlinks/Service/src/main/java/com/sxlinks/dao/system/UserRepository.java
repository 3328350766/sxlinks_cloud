package com.sxlinks.dao.system;


import com.sxlinks.bean.entity.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created  on 2019/3/21 0021.
 *
 * @author gaoliang
 */
public interface UserRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {
    User findByAccount(String account);
}
