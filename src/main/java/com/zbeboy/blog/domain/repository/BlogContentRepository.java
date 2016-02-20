package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.BlogContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lenovo on 2016-02-20.
 */
public interface BlogContentRepository extends JpaRepository<BlogContentEntity, Integer> {
    Page<BlogContentEntity> findByBlogSimpleContentId(Pageable pageable, int blogSimpleContentId);

    List<BlogContentEntity> findByBlogSimpleContentId(int blogSimpleContentId);
}
