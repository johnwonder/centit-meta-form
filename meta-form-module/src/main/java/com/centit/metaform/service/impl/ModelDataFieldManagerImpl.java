package com.centit.metaform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.centit.framework.jdbc.service.BaseEntityManagerImpl;
import com.centit.metaform.dao.ModelDataFieldDao;
import com.centit.metaform.po.ModelDataField;
import com.centit.metaform.service.ModelDataFieldManager;
import com.centit.support.database.utils.PageDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * ModelDataField  Service.
 * create by scaffold 2016-06-02 
 
 * 数据模板字段null   
*/
@Service
public class ModelDataFieldManagerImpl 
        extends BaseEntityManagerImpl<ModelDataField, Serializable, ModelDataFieldDao>
    implements ModelDataFieldManager{

    public static final Log log = LogFactory.getLog(ModelDataFieldManager.class);


    private ModelDataFieldDao modelDataFieldDao ;

    @Resource(name = "modelDataFieldDao")
    @NotNull
    public void setModelDataFieldDao(ModelDataFieldDao baseDao)
    {
        this.modelDataFieldDao = baseDao;
        setBaseDao(this.modelDataFieldDao);
    }

/*
     @PostConstruct
    public void init() {
        
    }

 */
    @Override
    @Transactional(propagation=Propagation.REQUIRED) 
    public JSONArray listModelDataFieldsAsJson(
            String[] fields,
            Map<String, Object> filterMap, PageDesc pageDesc){

        return baseDao.listObjectsAsJson(filterMap, pageDesc);
    }

}

