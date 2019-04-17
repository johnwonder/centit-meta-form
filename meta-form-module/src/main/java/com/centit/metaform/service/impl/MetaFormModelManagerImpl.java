package com.centit.metaform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.jdbc.service.BaseEntityManagerImpl;
import com.centit.metaform.dao.MetaFormModelDao;
import com.centit.metaform.po.MetaFormModel;
import com.centit.metaform.service.MetaFormModelManager;
import com.centit.product.dbdesign.service.MetaTableManager;
import com.centit.product.metadata.dao.MetaColumnDao;
import com.centit.product.metadata.po.MetaTable;
import com.centit.support.database.utils.PageDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * MetaFormModel  Service.
 * create by scaffold 2016-06-02

 * 通用模块管理null
*/
@Service
public class MetaFormModelManagerImpl
        extends BaseEntityManagerImpl<MetaFormModel,java.lang.String, MetaFormModelDao>
    implements MetaFormModelManager{

    public static final Log log = LogFactory.getLog(MetaFormModelManager.class);


    @Resource
    private MetaColumnDao metaColumnDao;

    private MetaFormModelDao metaFormModelDao ;

    @Resource(name = "metaFormModelDao")
    @NotNull
    public void setMetaFormModelDao(MetaFormModelDao baseDao)
    {
        this.metaFormModelDao = baseDao;
        setBaseDao(this.metaFormModelDao);
    }

    @Resource
    MetaTableManager metaTableManager;

/*
     @PostConstruct
    public void init() {

    }

 */
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public JSONArray listMetaFormModelsAsJson(
            String[] fields,
            Map<String, Object> filterMap, PageDesc pageDesc){

        JSONArray resultArray =
                baseDao.listObjectsAsJson(filterMap, pageDesc);
        return resultArray;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void updateMetaFormModel(MetaFormModel mtaFormModel) {
           metaFormModelDao.updateObject(mtaFormModel);
        metaFormModelDao.saveObjectReferences(mtaFormModel);
    }

    @Override
    public JSONArray addTableNameToList(JSONArray listObjects) {
        if (listObjects != null && listObjects.size()>0) {
            for (int i=0; i<listObjects.size(); i++) {
                JSONObject tempObj = listObjects.getJSONObject(i);
                Long tableId = tempObj.getLong("tableId");
                List<MetaTable> tableObjects = metaTableManager.listObjects();
                if (tableObjects != null && tableObjects.size()>0) {
                    for (int j=0; j<tableObjects.size(); j++) {
                        if (tableId != null &&tableId.equals(tableObjects.get(j).getTableId())) {
                            tempObj.put("tableLabelName", tableObjects.get(j).getTableLabelName());
                            break;
                        }
                    }
                }
            }
        }
        return listObjects;
    }

}

