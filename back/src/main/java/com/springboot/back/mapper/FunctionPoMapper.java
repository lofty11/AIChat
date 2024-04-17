package com.springboot.back.mapper;

import com.springboot.back.mapper.po.FunctionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dell
 */
@Repository
public interface FunctionPoMapper extends JpaRepository<FunctionPo, Long> {
    FunctionPo findByName(String name);

    FunctionPo findByEname(String ename);

    @Query("SELECT a.ename, a.description, a.api FROM FunctionPo a")
    List<Object[]> findAllNamesAndDescriptions();
}
