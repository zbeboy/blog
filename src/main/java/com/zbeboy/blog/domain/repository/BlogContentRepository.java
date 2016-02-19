package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.BlogContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/2/4.
 */
public interface BlogContentRepository extends JpaRepository<BlogContentEntity,Integer > {
}
