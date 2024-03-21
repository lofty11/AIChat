package com.springboot.back.mapper;

import com.springboot.back.mapper.po.FunctionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 */
@Repository
public interface FunctionPoMapper extends JpaRepository<FunctionPo, Long> {
    FunctionPo findByName(String name);
}
