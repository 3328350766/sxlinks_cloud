
package com.sxlinks.dao.system;


import com.sxlinks.bean.entity.system.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByNameLike(String name);

    List<Task> findByNameLike(String name);
    List<Task> findAllByDisabled(boolean disable);
}
