package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/2/4.
 */
public interface UsersRepository extends JpaRepository<UsersEntity,String > {
}
