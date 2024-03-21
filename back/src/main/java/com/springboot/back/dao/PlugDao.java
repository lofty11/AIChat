package com.springboot.back.dao;

import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.back.dao.bo.Plug;
import com.springboot.back.mapper.PlugPoMapper;
import com.springboot.back.mapper.po.ApplicationServicePo;
import com.springboot.back.mapper.po.PlugPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.springboot.core.util.Common.putGmtFields;
import static com.springboot.core.util.Common.putUserFields;

@Repository
public class PlugDao {
    private PlugPoMapper plugPoMapper;

    @Autowired
    public PlugDao(PlugPoMapper plugPoMapper) {
        this.plugPoMapper=plugPoMapper;
    }

    private Plug getBo(PlugPo po) {
        Plug bo = Plug.builder().id(po.getId()).name(po.getName()).purpose(po.getPurpose()).description(po.getDescription())
                .available(po.getAvailable()).open(po.getOpen()).deleted(po.getDeleted()).build();
        return bo;
    }

    private PlugPo getPo(Plug bo) {
        PlugPo po = PlugPo.builder().id(bo.getId()).name(bo.getName()).purpose(bo.getPurpose()).description(bo.getDescription())
                .available(bo.getAvailable()).open(bo.getOpen()).deleted(bo.getDeleted()).build();
        return po;
    }

    public void delete(Long id) {
        this.plugPoMapper.deleteById(id);
    }

    public Long insert(Plug plug, UserDto user) throws RuntimeException{
        PlugPo po = this.plugPoMapper.findByName(plug.getName());
        if (null == po) {
            PlugPo plugPo = getPo(plug);
            putUserFields(plugPo, "creator", user);
            putGmtFields(plugPo, "create");
            this.plugPoMapper.save(plugPo);
            return plugPo.getId();
        } else {
            throw new BusinessException(ReturnNo.APPLICATION_EXIST, String.format(ReturnNo.APPLICATION_EXIST.getMessage(), po.getId()));
        }
    }

    public Plug findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }
        Optional<PlugPo> po = this.plugPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "应用服务API", id));
        }
    }

}
