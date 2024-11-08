package com.sxlinks.dao.system;


import com.sxlinks.bean.entity.system.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

/**
 * Created  on 2019/3/21 0021.
 *
 * @author gaoliang
 */
public interface OperationLogRepository extends PagingAndSortingRepository<OperationLog,Long>
        ,JpaSpecificationExecutor<OperationLog>
        ,JpaRepository<OperationLog,Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "delete from t_sys_operation_log")
    int clear();
}
