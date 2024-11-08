package com.sxlinks.dao.system;

import com.sxlinks.bean.entity.system.Relation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created  on 2019/3/21 0021.
 *
 * @author gaoliang
 */
public interface RelationRepository extends PagingAndSortingRepository<Relation,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from t_sys_relation where roleid=?1")
    int deleteByRoleId(Long roleId);
}
