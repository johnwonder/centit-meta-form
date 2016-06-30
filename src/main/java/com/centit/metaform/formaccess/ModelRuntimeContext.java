package com.centit.metaform.formaccess;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centit.framework.security.model.CentitUserDetails;
import com.centit.metaform.po.MetaFormModel;
import com.centit.metaform.po.MetaTable;
import com.centit.support.database.QueryAndNamedParams;
import com.centit.support.database.jsonmaptable.JsonObjectDao;

public interface ModelRuntimeContext {
	
	public JsonObjectDao getJsonObjectDao();
	
	public String getModelCode();
	
	public MetaFormModel getMetaFormModel();
	
	public MetaTable getTableInfo();
	
	public Map<String,Object> fetchPkFromRequest( HttpServletRequest request);
	
	public Map<String,Object> fetchObjectFromRequest( HttpServletRequest request);	
	
	public void close();
	
	public void commitAndClose();
	
	public void rollbackAndClose();
	
	public void setUserEvniData(String key,Object value);
	
	public void setCurrentUserDetails(
			CentitUserDetails userDetails);
	
	public QueryAndNamedParams getMetaFormFilter();
}
