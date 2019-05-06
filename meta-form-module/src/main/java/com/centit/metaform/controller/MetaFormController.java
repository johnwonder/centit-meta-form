package com.centit.metaform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.appclient.HttpReceiveJSON;
import com.centit.framework.common.ObjectException;
import com.centit.framework.common.WebOptUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.metaform.po.MetaFormModel;
import com.centit.metaform.service.MetaFormModelManager;
import com.centit.product.metadata.service.MetaObjectService;
import com.centit.support.algorithm.CollectionsOpt;
import com.centit.support.algorithm.NumberBaseOpt;
import com.centit.support.compiler.Lexer;
import com.centit.support.database.utils.PageDesc;
import com.centit.workflow.client.service.FlowEngineClient;
import com.centit.workflow.commons.WorkflowException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/formaccess")
@Api(value = "自定义表单数据处理", tags = "自定义表单数据处理")
public class MetaFormController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(MetaFormController.class);

    @Autowired
    private MetaFormModelManager metaFormModelManager;

    @Autowired
    private MetaObjectService metaObjectService;

    @Autowired
    private FlowEngineClient flowEngineClient;

    private int runJSEvent(String js, Map<String, Object> object, String event){
        if(StringUtils.isBlank(js)){
            return 0;
        }
        JSMateObjectEvent jsMateObjectEvent = new JSMateObjectEvent(metaObjectService, js);
        return jsMateObjectEvent.runEvent(event,object);
    }


    @ApiOperation(value = "分页查询表单数据列表")
    @RequestMapping(value = "/{modelId}/list", method = RequestMethod.GET)
    @WrapUpResponseBody
    public PageQueryResult<Object> listObjects(@PathVariable String modelId, PageDesc pageDesc,
                                               String [] fields, HttpServletRequest request) {
        Map<String, Object> params = collectRequestParameters(request);//convertSearchColumn(request);
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        String sql = model.getDataFilterSql();

        if(StringUtils.isBlank(sql)) {
            JSONArray ja = metaObjectService.pageQueryObjects(
                    model.getTableId(), params, pageDesc);
            return PageQueryResult.createJSONArrayResult(ja, pageDesc, fields);
        }
        if(StringUtils.equalsIgnoreCase("select",Lexer.getFirstWord(sql))) {
            JSONArray ja = metaObjectService.pageQueryObjects(
                    model.getTableId(), sql, params, pageDesc);
            return PageQueryResult.createJSONArrayResult(ja, pageDesc);
        }
        JSONArray ja = metaObjectService.pageQueryObjects(
                model.getTableId(), params, sql.split(","), pageDesc);
        return PageQueryResult.createJSONArrayResult(ja, pageDesc);
    }

    @ApiOperation(value = "获取一个数据，主键作为参数以key-value形式提交")
    @RequestMapping(value = "/{modelId}", method = RequestMethod.GET)
    @WrapUpResponseBody
    public Map<String, Object> getObject(@PathVariable String modelId,
                                               HttpServletRequest request) {
        Map<String, Object> parameters = collectRequestParameters(request);
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        return metaObjectService.getObjectById(model.getTableId(), parameters);
    }

    @ApiOperation(value = "修改表单数据")
    @RequestMapping(value = "/{modelId}", method = RequestMethod.PUT)
    @WrapUpResponseBody
    public void updateObject(@PathVariable String modelId,
                                            @RequestBody String jsonString) {
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        JSONObject object = JSON.parseObject(jsonString);
        if(runJSEvent(model.getExtendOptJs(), object, "beforeUpdate")==0) {
            metaObjectService.updateObject(model.getTableId(), object);
        }
    }

    @ApiOperation(value = "新增表单数据")
    @RequestMapping(value = "/{modelId}", method = RequestMethod.POST)
    @WrapUpResponseBody
    public void saveObject(@PathVariable String modelId,
                                          @RequestBody String jsonString) {
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        JSONObject object = JSON.parseObject(jsonString);
        if(runJSEvent(model.getExtendOptJs(), object, "beforeSave")==0) {
            metaObjectService.saveObject(model.getTableId(), object);
        }
    }

    @ApiOperation(value = "删除表单数据")
    @RequestMapping(value = "/{modelId}", method = RequestMethod.DELETE)
    @WrapUpResponseBody
    public void deleteObject(@PathVariable String modelId,
                                     HttpServletRequest request) {
        Map<String, Object> parameters = collectRequestParameters(request);
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        if(runJSEvent(model.getExtendOptJs(), parameters, "beforeDelete")==0) {
            metaObjectService.deleteObject(model.getTableId(), parameters);
        }
    }

    @ApiOperation(value = "获取一个数据带子表，主键作为参数以key-value形式提交")
    @RequestMapping(value = "/{modelId}/withChildren", method = RequestMethod.GET)
    @WrapUpResponseBody
    public Map<String, Object> getObjectWithChildren(@PathVariable String modelId,
                                         HttpServletRequest request) {
        Map<String, Object> parameters = collectRequestParameters(request);
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        return metaObjectService.getObjectWithChildren(model.getTableId(), parameters, 1);
    }

    @ApiOperation(value = "修改表单数据带子表")
    @RequestMapping(value = "/{modelId}/withChildren", method = RequestMethod.PUT)
    @WrapUpResponseBody
    public void updateObjectWithChildren(@PathVariable String modelId,
                                     @RequestBody String jsonString) {
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        JSONObject object = JSON.parseObject(jsonString);
        if(runJSEvent(model.getExtendOptJs(), object, "beforeUpdate")==0) {
            metaObjectService.updateObjectWithChildren(model.getTableId(), object);
        }
    }

    @ApiOperation(value = "新增表单数据带子表")
    @RequestMapping(value = "/{modelId}/withChildren", method = RequestMethod.POST)
    @WrapUpResponseBody
    public void saveObjectWithChildren(@PathVariable String modelId,
                                   @RequestBody String jsonString) {
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        JSONObject object = JSON.parseObject(jsonString);
        if(runJSEvent(model.getExtendOptJs(), object, "beforeSave")==0) {
            metaObjectService.saveObjectWithChildren(model.getTableId(), object);
        }
    }

    @ApiOperation(value = "删除表单数据带子表")
    @RequestMapping(value = "/{modelId}/withChildren", method = RequestMethod.DELETE)
    @WrapUpResponseBody
    public void deleteObjectWithChildren(@PathVariable String modelId,
                                     HttpServletRequest request) {
        Map<String, Object> parameters = collectRequestParameters(request);
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        if(runJSEvent(model.getExtendOptJs(), parameters, "beforeDelete")==0) {
            metaObjectService.deleteObjectWithChildren(model.getTableId(), parameters);
        }
    }

    /**
     * 提交工作流 ; 分两种情况
     * 一： 新建业务，操作流程为， 保存表单，提交流程，这时表单对应的表中flowInstId字段必然为空，所以:
     *  1,创建流程,获取流程实例编号回填到这个表单中
     *  2,提交首节点，进入下一个节点(这个操作同时在流程引擎中执行)
     * 二： 提交节点，操作流程如下:
     *  1, 从工作流引擎中获得待办列表,通过这个待办列表进入节点实例
     *  2, 进入节点实例分两种情况,新建表单或者修改表单,无论是那种情况表单中都有两个字段flowInstId和nodeInstId
     *  3, 保存表单
     *  4, 提交表单
     * @param modelId 模块id
     * @param jsonString 模块对象对应的主键
     */
    @ApiOperation(value = "提交工作流")
    @RequestMapping(value = "/{modelId}/submit", method = RequestMethod.PUT)
    @WrapUpResponseBody
    public void submitFlow(@PathVariable String modelId,
                           @RequestBody String jsonString,
                           HttpServletRequest request) {
        MetaFormModel model = metaFormModelManager.getObjectById(modelId);
        JSONObject parameters = JSON.parseObject(jsonString);
        Map<String, Object> object = metaObjectService.getObjectById(model.getTableId(), parameters);
        Object flowInstId = object.get("flowInstId");
        if(flowInstId == null){
            //TODO create flow instance
            try {
                //TODO 这个接口需要修改，需要和 flowEngine 一致
                String json = flowEngineClient.createInstance(model.getRelFlowCode(),
                        "",// 这边需要添加一个title表达式
                        jsonString,
                        WebOptUtils.getCurrentUserCode(request),
                        WebOptUtils.getCurrentUnitCode(request));
                HttpReceiveJSON obj = HttpReceiveJSON.valueOfJson(json);
                object.put("flowInstId", obj.getData("flowInstId"));
                metaObjectService.updateObjectByProperties(model.getTableId(),
                        CollectionsOpt.createList("flowInstId","nodeInstId"), object);
            } catch (Exception e) {
                throw new ObjectException(e);
            }
        } else {
            Long nodeInstId = NumberBaseOpt.castObjectToLong(object.get("nodeInstId"));
            if(nodeInstId == null){
                throw new ObjectException(WorkflowException.NodeInstNotFound,"找不到对应的节点实例号！" + jsonString);
            }
            // TODO submit flow
            flowEngineClient.submitOpt(nodeInstId , WebOptUtils.getCurrentUserCode(request),
                    WebOptUtils.getCurrentUnitCode(request), null, null);
        }
    }

}
