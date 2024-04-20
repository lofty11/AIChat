package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ExtensionOutputPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtensionOutputPoMapper extends JpaRepository<ExtensionOutputPo, Long> {

    ExtensionOutputPo findByApplicationIdAndFieldName(Long applicationId, String fieldName);

    List<ExtensionOutputPo> findByApplicationId(Long applicationId);

    @Query("SELECT max(a.id) FROM ExtensionOutputPo a")
    Long findMaxId();
}
