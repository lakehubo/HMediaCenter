package com.lake.hmediacenterserver.repository;

import com.lake.hmediacenterserver.entity.MediaResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaResourceRepository extends JpaRepository<MediaResource, Long> {
    Optional<MediaResource> findByFilePath(String filePath);
}

