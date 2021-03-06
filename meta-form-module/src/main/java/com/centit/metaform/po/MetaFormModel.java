package com.centit.metaform.po;

import com.alibaba.fastjson.JSONObject;
import com.centit.framework.core.dao.DictionaryMap;
import com.centit.support.database.orm.GeneratorCondition;
import com.centit.support.database.orm.GeneratorTime;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;

/**
 * create by scaffold 2016-06-02


  通用模块管理null
*/
@Data
@Entity
@Table(name = "M_META_FORM_MODEL")
public class MetaFormModel implements java.io.Serializable {
    private static final long serialVersionUID =  1L;

    @ApiModelProperty(value = "模块代码", hidden = true)
    @Id
    @Column(name = "MODEL_ID")
    //@GeneratedValue(generator = "paymentableGenerator")
    @ValueGenerator(strategy = GeneratorType.UUID22)
    //@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    private String modelId;

    @ApiModelProperty(value = "表ID")
    @Column(name = "TABLE_ID")
    private String tableId;

    @ApiModelProperty(value = "表单类型 C 卡片类型 R 日历类型 N 正常表单 S 子模块表单 L 列表表单 P 一对一子表表单 D 数据驱动表单（二阶表单）")
    @Column(name = "MODEL_TYPE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String  modelType;

    @ApiModelProperty(value = "子模块关联表ID")
    @Column(name = "RELATION_ID")
    private String relationId;

    @ApiModelProperty(value = "模块名称，如果是子摸快，这个字段名为 relationName ", required = true)
    @Column(name = "MODEL_NAME")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String  modelName;

    @ApiModelProperty(value = "表单模板")
    @Column(name = "FORM_TEMPLATE")
    @Basic(fetch = FetchType.LAZY)
    private JSONObject formTemplate;

    @ApiModelProperty(value = "移动表单模板")
    @Column(name = "MOBILE_FORM_TEMPLATE")
    @Basic(fetch = FetchType.LAZY)
    private JSONObject mobileFormTemplate;

    @ApiModelProperty(value = "更改时间")
    @Column(name = "LAST_MODIFY_DATE")
    @ValueGenerator(strategy = GeneratorType.FUNCTION, occasion = GeneratorTime.NEW_UPDATE, condition = GeneratorCondition.ALWAYS, value = "today()")
    private Date  lastModifyDate;

    @ApiModelProperty(value = "更改人员")
    @Column(name = "RECORDER")
    @Length(max = 8, message = "字段长度不能大于{max}")
    @DictionaryMap(fieldName = "recorderName", value = "userCode")
    private String  recorder;

    @ApiModelProperty(value = "业务特殊处理脚本")
    @Column(name = "EXTEND_OPT_JS")
    @Basic(fetch = FetchType.LAZY)
    private String  extendOptJs;

    @ApiModelProperty(value = "数据查询参数驱动SQL，也可以是对应表的字段列表")
    @Column(name = "DATA_FILTER_SQL")
    @Length(max = 2000, message = "字段长度不能大于{max}")
    @Basic(fetch = FetchType.LAZY)
    private String  dataFilterSql;

    @ApiModelProperty(value = "关联工作流代码")
    @Column(name = "REL_FLOW_CODE")
    @Length(max = 64, message = "字段长度不能大于{max}")
    private String  relFlowCode;

    /**
     * 工作流业务标题模板 可以通过{变量名称} 来引用其他属性
     */
    @ApiModelProperty(value = "工作流业务标题模板")
    @Column(name = "FLOW_OPT_TITLE")
    @Length(max = 500, message = "字段长度不能大于{max}")
    private String  flowOptTitle;

    @ApiModelProperty(value = "模块描述")
    @Column(name = "MODEL_COMMENT")
    @Length(max = 256, message = "字段长度不能大于{max}")
    private String  modelComment;

    @ApiModelProperty(value = "表单后台处理url")
    @Column(name = "MODE_OPT_URL")
    @Length(max = 800, message = "字段长度不能大于{max}")
    private String  modeOptUrl;

    /**
     * 所属数据库ID
     */
    @Column(name = "DATABASE_CODE")
    @ApiModelProperty(value = "数据库ID")
    @DictionaryMap(value="databaseInfo", fieldName = "databaseName")
    private String databaseCode;
    @ApiModelProperty(value = "业务模块代码")
    @Column(name = "APPLICATION_ID")
    private String  applicationId;
    @ApiModelProperty(value = "所属分组")
    @Column(name = "own_group")
    private String  ownGroup;

    @OneToMany(targetEntity = MetaFormModel.class, mappedBy="metaFormModel",
            orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "MODEL_CODE", referencedColumnName = "MODEL_CODE")
//    @OneToMany(mappedBy="parentModelCode",fetch = FetchType.EAGER)
//    @JoinColumn(name = "MODEL_CODE", referencedColumnName = "PARENT_MODEL_CODE")
//    @OrderBy(value="displayOrder asc")
    private Set<MetaFormModel> childFormModels;

    @Transient
    private String relationName;

    public String getRelationName() {
        /*if (relationId != null && !"".equals(relationId)) {
            relationName = this.modelName;
        }*/
        return relationName;
    }

    // Constructors
    /* default constructor */
    public MetaFormModel() {
    }
    /* minimal constructor */
    public MetaFormModel(
        String modelId
        ,String  modelName) {
        this.modelId = modelId;
        this.modelName= modelName;
    }


    public Set<MetaFormModel> getMetaFormModels(){
        if(this.childFormModels==null) {
            this.childFormModels = new HashSet<>();
        }
        return this.childFormModels;
    }

    public void setMetaFormModels(Set<MetaFormModel> metaFormModels) {
        this.childFormModels = metaFormModels;
    }

    public void addMetaFormModel(MetaFormModel metaFormModel ){
        if (this.childFormModels==null) {
            this.childFormModels = new HashSet<>();
        }
        this.childFormModels.add(metaFormModel);
    }

    public void removeMetaFormModel(MetaFormModel metaFormModel ){
        if (this.childFormModels==null) {
            return;
        }
        this.childFormModels.remove(metaFormModel);
    }

    /**
     * 替换子类对象数组，这个函数主要是考虑hibernate中的对象的状态，以避免对象状态不一致的问题
     *
     */
    public void replaceMetaFormModels(Set<MetaFormModel> set) {
        List<MetaFormModel> newObjs = new ArrayList<MetaFormModel>();
        for(MetaFormModel p :set){
            if(p==null)
                continue;
            MetaFormModel newdt = new MetaFormModel();
            newdt.copyNotNullProperty(p);
            newObjs.add(newdt);
        }
        //delete
        boolean found = false;
        Set<MetaFormModel> oldObjs = new HashSet<>();
        oldObjs.addAll(getMetaFormModels());

        for(Iterator<MetaFormModel> it=oldObjs.iterator(); it.hasNext();){
            MetaFormModel odt = it.next();
            found = false;
            for(MetaFormModel newdt :newObjs){
                if(odt.getModelId().equals( newdt.getModelId())){
                    found = true;
                    break;
                }
            }
            if(! found)
                removeMetaFormModel(odt);
        }
        oldObjs.clear();
        //insert or update
        for(MetaFormModel newdt :newObjs){
            found = false;
            for(Iterator<MetaFormModel> it=getMetaFormModels().iterator();
             it.hasNext();){
                MetaFormModel odt = it.next();
                if(odt.getModelId().equals( newdt.getModelId())){
                    odt.copy(newdt);
                    found = true;
                    break;
                }
            }
            if(! found)
                addMetaFormModel(newdt);
        }
    }

    public MetaFormModel copy(MetaFormModel other){
        this.setModelId(other.getModelId());
        this.tableId=other.getTableId();
        this.modelComment= other.getModelComment();
        this.modelName= other.getModelName();
        this.modelType= other.getModelType();
        this.formTemplate= other.getFormTemplate();
        this.mobileFormTemplate= other.getMobileFormTemplate();
        this.lastModifyDate= other.getLastModifyDate();
        this.recorder= other.getRecorder();
        this.extendOptJs= other.getExtendOptJs();
        this.childFormModels = other.getMetaFormModels();
        this.dataFilterSql = other.getDataFilterSql();
        this.relFlowCode = other.getRelFlowCode();
        this.modeOptUrl = other.getModeOptUrl();
        this.databaseCode = other.getDatabaseCode();
        this.applicationId = other.getApplicationId();
        return this;
    }

    public MetaFormModel copyNotNullProperty(MetaFormModel other){
        if( other.getModelId() != null)
            this.setModelId(other.getModelId());
        if( other.getTableId() != null)
            this.tableId= other.getTableId();
        if( other.getModelComment() != null)
            this.modelComment= other.getModelComment();
        if( other.getModelName() != null)
            this.modelName= other.getModelName();
        if( other.getModelType() != null)
            this.modelType= other.getModelType();
        if( other.getFormTemplate() != null)
            this.formTemplate= other.getFormTemplate();
        if( other.getMobileFormTemplate() != null)
            this.mobileFormTemplate= other.getMobileFormTemplate();
        if( other.getLastModifyDate() != null)
            this.lastModifyDate= other.getLastModifyDate();
        if( other.getRecorder() != null)
            this.recorder= other.getRecorder();
        if( other.getMetaFormModels() != null)
        this.childFormModels = other.getMetaFormModels();
        if( other.getExtendOptJs() != null)
            this.extendOptJs = other.getExtendOptJs();
        if( other.getRelFlowCode() != null)
            this.relFlowCode = other.getRelFlowCode();
        if(null!=other.getDataFilterSql())
            this.dataFilterSql = other.getDataFilterSql();
        if( other.getModeOptUrl() != null)
            this.modeOptUrl = other.getModeOptUrl();
        if( other.getDatabaseCode() != null)
            this.databaseCode = other.getDatabaseCode();
        if (other.getApplicationId()!=null)
            this.applicationId = other.getApplicationId();
        return this;
    }

}
