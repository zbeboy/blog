package com.zbeboy.blog.domain.repository;

import com.zbeboy.blog.domain.entity.ArchivesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/2/15.
 */
public interface ArchivesRepository extends JpaRepository<ArchivesEntity, Integer> {
    List<ArchivesEntity> findByTimes(String times);
}
