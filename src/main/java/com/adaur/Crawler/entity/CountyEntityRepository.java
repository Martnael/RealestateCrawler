package com.adaur.Crawler.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountyEntityRepository extends JpaRepository<CountyEntity, Integer> {
    @Override
    List<CountyEntity> findAll();
}
