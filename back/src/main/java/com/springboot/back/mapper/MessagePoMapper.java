package com.springboot.back.mapper;

import com.springboot.back.mapper.po.MessagePo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagePoMapper extends JpaRepository<MessagePo,Long> {
    Page<MessagePo> findByChatId(long id, Pageable pageable);
    void deleteAllByChatId(long id);
}
