package com.springboot.back.mapper;

import com.springboot.back.mapper.po.UserParaPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dell
 */
@Repository
public interface UserParaPoMapper extends JpaRepository<UserParaPo, Long> {
    UserParaPo findByName(String name);
    List<UserParaPo> findByPlugId(Long plugId);
}
