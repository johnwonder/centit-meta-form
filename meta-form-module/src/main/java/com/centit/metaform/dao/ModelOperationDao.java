package com.centit.metaform.dao;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.metaform.po.ModelOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;



/**
 * ModelOperationDao  Repository.
 * create by scaffold 2016-06-21

 * 模块操作定义null
*/

@Repository
public class ModelOperationDao extends BaseDaoImpl<ModelOperation, HashMap<String,Object>>
    {

    public static final Log log = LogFactory.getLog(ModelOperationDao.class);

    @Override
    public Map<String, String> getFilterField() {
        if( filterField == null){
            filterField = new HashMap<String, String>();

            filterField.put("modelCode" , CodeBook.EQUAL_HQL_ID);

            filterField.put("operation" , CodeBook.EQUAL_HQL_ID);


            filterField.put("optModelCode" , CodeBook.EQUAL_HQL_ID);

            filterField.put("method" , CodeBook.EQUAL_HQL_ID);

            filterField.put("label" , CodeBook.EQUAL_HQL_ID);

            filterField.put("displayOrder" , CodeBook.EQUAL_HQL_ID);

            filterField.put("openType" , CodeBook.EQUAL_HQL_ID);

            filterField.put("returnOperation" , CodeBook.EQUAL_HQL_ID);

            filterField.put("optHintType" , CodeBook.EQUAL_HQL_ID);

            filterField.put("optHintInfo" , CodeBook.EQUAL_HQL_ID);

            filterField.put("extendOptions" , CodeBook.EQUAL_HQL_ID);

        }
        return filterField;
    }
}
