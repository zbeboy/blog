package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.AuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/2/4.
 */
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity,String > {
}
