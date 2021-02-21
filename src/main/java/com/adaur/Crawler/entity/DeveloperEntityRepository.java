package com.adaur.Crawler.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperEntityRepository extends JpaRepository<DeveloperEntity, Integer> {

    List<DeveloperEntity> findByDeveloperName(String name);
}
