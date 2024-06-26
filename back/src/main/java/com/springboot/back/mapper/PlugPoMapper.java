package com.springboot.back.mapper;

import com.springboot.back.mapper.po.PlugPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 */
@Repository
public interface PlugPoMapper extends JpaRepository<PlugPo, Long> {
    PlugPo findByName(String name);
}
