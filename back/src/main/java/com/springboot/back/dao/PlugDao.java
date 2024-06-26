package com.springboot.back.dao;

import com.springboot.back.dao.bo.Plug;
import com.springboot.back.mapper.PlugPoMapper;
import com.springboot.back.mapper.po.PlugPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springboot.core.util.Common.putGmtFields;
import static com.springboot.core.util.Common.putUserFields;

/**
 * @author dell
 */
@Repository
public class PlugDao {
    private PlugPoMapper plugPoMapper;

    private final static String KEY = "E%d";

    @Autowired
    public PlugDao(PlugPoMapper plugPoMapper) {
        this.plugPoMapper=plugPoMapper;
    }

    private Plug getBo(PlugPo po) {
        return Plug.builder().id(po.getId()).name(po.getName()).purpose(po.getPurpose()).description(po.getDescription())
                .available(po.getAvailable()).open(po.getOpen()).deleted(po.getDeleted()).build();
    }

    private PlugPo getPo(Plug bo) {
        return PlugPo.builder().id(bo.getId()).name(bo.getName()).purpose(bo.getPurpose()).description(bo.getDescription())
                .available(bo.getAvailable()).open(bo.getOpen()).deleted(bo.getDeleted()).build();
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
            throw new BusinessException(ReturnNo.PLUG_EXIST, String.format(ReturnNo.PLUG_EXIST.getMessage(), po.getId()));
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
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件", id));
        }
    }

    public String save(Long id, Plug plug, UserDto user) {
        PlugPo po = getPo(plug);
        po.setId(id);
        if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }
        this.plugPoMapper.save(po);
        return String.format(KEY, plug.getId());
    }

    public List<Plug> retrieveAll(Integer page, Integer pageSize) throws RuntimeException {
        List<PlugPo> reList = this.plugPoMapper.findAll(PageRequest.of(page-1, pageSize))
                .stream().toList();
        if (reList.isEmpty()) {
            return new ArrayList<>();
        }
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }


}
