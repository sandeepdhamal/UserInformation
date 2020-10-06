package com.newvision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newvision.entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
}