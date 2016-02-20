package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.ArchivesEntity;
import com.zbeboy.blog.domain.entity.BlogContentTypeEntity;
import com.zbeboy.blog.domain.entity.BlogSimpleContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/2/4.
 */
public interface BlogSimpleContentRepository extends JpaRepository<BlogSimpleContentEntity, Integer> {
    Page<BlogSimpleContentEntity> findByArchivesId(Pageable pageable, ArchivesEntity archivesId);

    Page<BlogSimpleContentEntity> findByBlogTypeId(Pageable pageable, BlogContentTypeEntity BlogTypeId);

    List<BlogSimpleContentEntity> findByBlogTitleLike(String BlogTitle);
}
