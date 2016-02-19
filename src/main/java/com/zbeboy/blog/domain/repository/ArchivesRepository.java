package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.ArchivesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/2/15.
 */
public interface ArchivesRepository extends JpaRepository<ArchivesEntity,Integer > {
}
