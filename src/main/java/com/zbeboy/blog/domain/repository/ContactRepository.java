package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/2/4.
 */
public interface ContactRepository extends JpaRepository<ContactEntity,Integer > {
}
