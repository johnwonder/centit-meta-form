package com.centit.metaform.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.hibernate.dao.BaseDaoImpl;
import com.centit.metaform.po.PendingMdRelDetial;



/**
 * PendingMdRelDetialDao  Repository.
 * create by scaffold 2016-06-01 
 * @author codefan@sina.com
 * 未落实表关联细节表null   
*/

@Repository
public class PendingMdRelDetialDao extends BaseDaoImpl<PendingMdRelDetial,com.centit.metaform.po.PendingMdRelDetialId>
	{

	public static final Log log = LogFactory.getLog(PendingMdRelDetialDao.class);
	
	@Override
	public Map<String, String> getFilterField() {
		if( filterField == null){
			filterField = new HashMap<String, String>();

			filterField.put("relationId" , CodeBook.EQUAL_HQL_ID);

			filterField.put("parentColumnName" , CodeBook.EQUAL_HQL_ID);


			filterField.put("childColumnName" , CodeBook.EQUAL_HQL_ID);

		}
		return filterField;
	} 
}
