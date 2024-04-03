package com.springboot.back.dao;

import com.springboot.back.dao.bo.Plug;
import com.springboot.back.mapper.PlugPoMapper;
import com.springboot.back.mapper.po.PlugPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.Constants;
import com.springboot.core.model.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Plug bo = Plug.builder().id(po.getId()).name(po.getName()).purpose(po.getPurpose()).description(po.getDescription())
                .available(po.getAvailable()).open(po.getOpen()).deleted(po.getDeleted()).build();
        return bo;
    }

    private PlugPo getPo(Plug bo) {
        PlugPo po = PlugPo.builder().id(bo.getId()).name(bo.getName()).purpose(bo.getPurpose()).description(bo.getDescription())
                .available(bo.getAvailable()).open(bo.getOpen()).deleted(bo.getDeleted()).build();
        return po;
    }

    public Long insert(Plug plug) throws RuntimeException{
        PlugPo po = this.plugPoMapper.findByName(plug.getName());
        if (null == po) {
            PlugPo plugPo = getPo(plug);
            /*putUserFields(plugPo, "creator", user);
            putGmtFields(plugPo, "create");*/
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

    public String save(Long id, Plug plug) {
        PlugPo po = getPo(plug);
        po.setId(id);
        /*if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }*/
        this.plugPoMapper.save(po);
        return String.format(KEY, plug.getId());
    }

    public Long findByName(String plugName) {
        PlugPo po = this.plugPoMapper.findByName(plugName);
        if (null == po) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), null));
        }
        return po.getId();
    }

    public List<Plug> retrieveAll(Integer page, Integer pageSize) throws RuntimeException {
        List<PlugPo> reList = this.plugPoMapper.findAll(PageRequest.of(0, Constants.MAX_RETURN))
                .stream().toList();
        if (reList.isEmpty()) {
            return new ArrayList<>();
        }
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }

}
