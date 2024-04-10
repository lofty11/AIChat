package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ChatPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatPoMapper extends JpaRepository<ChatPo,Long> {
    Page<ChatPo> findAllByUserId(Long id, Pageable pageable);
    void deleteById(Long id);
}
