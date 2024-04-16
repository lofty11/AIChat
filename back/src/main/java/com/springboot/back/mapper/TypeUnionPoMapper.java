package com.springboot.back.mapper;

import com.springboot.back.mapper.po.TypeUnionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeUnionPoMapper extends JpaRepository<TypeUnionPo, Long> {
}
