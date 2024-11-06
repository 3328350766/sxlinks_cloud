package com.sxlinks.service.system;

import com.sxlinks.utils.DateUtil;
import com.sxlinks.utils.StringUtils;
import com.sxlinks.utils.factory.Page;
import com.sxlinks.bean.entity.system.User;
import com.sxlinks.dao.system.UserRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created  on 2018/3/23 0023.
 *
 * @author gaoliang
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(final Map<String, Object> params) {
        return userRepository.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(params.get("deptid") !=null && !Strings.isNullOrEmpty(params.get("deptid").toString())){
                    list.add(criteriaBuilder.equal(root.get("deptid").as(String.class), params.get("deptid").toString()));
                }
                if(params.get("name") !=null && !Strings.isNullOrEmpty(params.get("name").toString())){
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class), params.get("name").toString()));
                }
                if(StringUtils.isNotNullOrEmpty(params.get("account"))){
                    list.add(criteriaBuilder.like(root.get("account").as(String.class),params.get("account").toString()));
                }
                if(params.get("beginTime") != null && !Strings.isNullOrEmpty(params.get("beginTime").toString())){
                    list.add(criteriaBuilder.greaterThan(root.get("createtime").as(Date.class), DateUtil.parseDate(params.get("beginTime").toString())));
                }
                if(params.get("endTime") != null && !Strings.isNullOrEmpty(params.get("endTime").toString())){
                    list.add(criteriaBuilder.lessThan(root.get("createtime").as(Date.class),DateUtil.parseDate(params.get("endTime").toString())));
                }


                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });

    }


    public Page<User> findPage(Page<User> page, Map<String, Object> params) {
        Pageable pageable = null;
        if(page.isOpenSort()) {
            pageable = PageRequest.of(page.getCurrent()-1, page.getSize(), page.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, page.getOrderByField());
        }else{
            pageable = PageRequest.of(page.getCurrent()-1,page.getSize());
        }

        org.springframework.data.domain.Page<User> pageResult = userRepository.findAll(new Specification<User>(){


            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(params.get("deptid") !=null && !Strings.isNullOrEmpty(params.get("deptid").toString())){
                    list.add(criteriaBuilder.equal(root.get("deptid").as(String.class), params.get("deptid").toString()));
                }
                if(params.get("name") !=null && !Strings.isNullOrEmpty(params.get("name").toString())){
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class), params.get("name").toString()));
                }
                if(StringUtils.isNotNullOrEmpty(params.get("account"))){
                    list.add(criteriaBuilder.like(root.get("account").as(String.class),params.get("account").toString()));
                }
                if(params.get("beginTime") != null && !Strings.isNullOrEmpty(params.get("beginTime").toString())){
                    list.add(criteriaBuilder.greaterThan(root.get("createtime").as(Date.class), DateUtil.parseDate(params.get("beginTime").toString())));
                }
                if(params.get("endTime") != null && !Strings.isNullOrEmpty(params.get("endTime").toString())){
                    list.add(criteriaBuilder.lessThan(root.get("createtime").as(Date.class),DateUtil.parseDate(params.get("endTime").toString())));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        page.setTotal(Integer.valueOf(pageResult.getTotalElements()+""));
        page.setRecords(pageResult.getContent());
        return page;
    }

    public  User get(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
