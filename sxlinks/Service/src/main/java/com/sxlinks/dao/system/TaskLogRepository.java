
package com.sxlinks.dao.system;


import com.sxlinks.bean.entity.system.TaskLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TaskLogRepository  extends PagingAndSortingRepository<TaskLog,Long>
        ,JpaSpecificationExecutor<TaskLog>
        ,JpaRepository<TaskLog,Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "delete from t_sys_task_log")
    int clear();
}
