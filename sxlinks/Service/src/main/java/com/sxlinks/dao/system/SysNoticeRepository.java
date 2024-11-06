package com.sxlinks.dao.system;


import com.sxlinks.bean.entity.system.Notice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created  on 2019/3/21 0021.
 *
 * @author gaoliang
 */
public interface SysNoticeRepository extends PagingAndSortingRepository<Notice,Long> {
    List<Notice> findByTitleLike(String name);
}
