package com.springboot.back.mapper;
import com.springboot.back.mapper.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPoMapper extends JpaRepository<UserPo, Long>{
    Optional<UserPo> findByUserName(String name);
}
