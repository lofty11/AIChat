package com.springboot.back.mapper;

import com.springboot.back.mapper.po.FunctionTypePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 */
@Repository
public interface FunctionTypePoMapper extends JpaRepository<FunctionTypePo, Long> {
}
