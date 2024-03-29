package com.springboot.back.mapper;
import com.springboot.back.mapper.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPoMapper extends JpaRepository<UserPo, Long>{
    UserPo findByUserTel(String tel);
    UserPo findByUserName(String name);
}
