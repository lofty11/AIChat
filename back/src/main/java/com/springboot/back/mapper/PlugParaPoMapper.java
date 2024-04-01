package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ExtensionInputPo;
import com.springboot.back.mapper.po.PlugParaPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dell
 */
@Repository
public interface PlugParaPoMapper extends JpaRepository<PlugParaPo, Long> {
    PlugParaPo findByName(String name);
    List<PlugParaPo> findByPlugId(Long plugId);
}
