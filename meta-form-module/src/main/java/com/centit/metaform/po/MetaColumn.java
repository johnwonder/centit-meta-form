package com.centit.metaform.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.centit.metaform.formaccess.FieldType;
import com.centit.support.database.utils.DBType;
import com.centit.support.database.metadata.SimpleTableField;
import com.centit.support.database.metadata.TableField;


/**
 * create by scaffold 2016-06-02 
 
 
  字段元数据表null   
*/
@Entity
@Table(name = "F_META_COLUMN")
public class MetaColumn implements TableField,java.io.Serializable {
	private static final long serialVersionUID =  1L;

	@EmbeddedId
	private com.centit.metaform.po.MetaColumnId cid;


	/**
	 * 字段名称 null 
	 */
	@Column(name = "FIELD_LABEL_NAME")
	@NotBlank(message = "字段不能为空")
	@Length(max = 64, message = "字段长度不能大于{max}")
	private String  fieldLabelName;

	/**
	 * 字段描述 null 
	 */
	@Column(name = "COLUMN_COMMENT")
	@Length(max = 256, message = "字段长度不能大于{max}")
	private String  columnComment;
	/**
	 * 显示次序 null 
	 */
	@Column(name = "COLUMN_ORDER")
	private Long  columnOrder;
	/**
	 * 字段类型 null 
	 */
	@Column(name = "COLUMN_TYPE")
	@NotBlank(message = "字段不能为空")
	@Length(max = 32, message = "字段长度不能大于{max}")
	private String  columnFieldType;
	/**
	 * 字段长度 precision 
	 */
	@Column(name = "MAX_LENGTH")
	private Integer  maxLength;
	/**
	 * 字段精度 null 
	 */
	@Column(name = "SCALE")
	private Integer  scale;
	/**
	 * 字段类别 null 
	 */
	@Column(name = "ACCESS_TYPE")
	@NotBlank(message = "字段不能为空")
	private String  accessType;
	/**
	 * 是否必填 null 
	 */
	@Column(name = "MANDATORY")
	private String isMandatoryColumn;
	/**
	 * 是否为主键 null 
	 */
	@Column(name = "PRIMARYKEY")
	private String isPrimarykeyColumn;
	/**
	 * 状态 null 
	 */
	@Column(name = "COLUMN_STATE")
	@NotBlank(message = "字段不能为空")
	private String  columnState;
	/**
	 *  0：没有：1： 数据字典(列表)   2： 数据字典(树型)   3：JSON表达式 4：sql语句   5：SQL（树）
	   9 :框架内置字典（用户、机构、角色等等）  Y：年份 M：月份   F:文件（column_Type 必须为 varchar（64））
	 */
	@Column(name = "REFERENCE_TYPE")
	private String  referenceType;
	/**
	 * 引用数据 根据paramReferenceType类型（1,2,3）填写对应值 
	 */
	@Column(name = "REFERENCE_DATA")
	@Length(max = 1000, message = "字段长度不能大于{max}")
	private String  referenceData;
	/**
	 * 约束表达式 regex表达式 
	 */
	@Column(name = "VALIDATE_REGEX")
	@Length(max = 200, message = "字段长度不能大于{max}")
	private String  validateRegex;
	/**
	 * 约束提示 约束不通过提示信息 
	 */
	@Column(name = "VALIDATE_INFO")
	@Length(max = 200, message = "字段长度不能大于{max}")
	private String  validateInfo;
	
	/**
	 * 自动生成规则   C 常量  U uuid S sequence
	 */
	@Column(name = "AUTO_CREATE_RULE")
	@Length(max = 200, message = "字段长度不能大于{max}")
	private String  autoCreateRule;
	
	/**
	 * 自动生成参数
	 */
	@Column(name = "AUTO_CREATE_PARAM")
	@Length(max = 200, message = "字段长度不能大于{max}")
	private String  autoCreateParam;
	/**
	 * 更改时间 null 
	 */
	@Column(name = "LAST_MODIFY_DATE")
	private Date  lastModifyDate;
	/**
	 * 更改人员 null 
	 */
	@Column(name = "RECORDER")
	@Length(max = 8, message = "字段长度不能大于{max}")
	private String  recorder;

	@Transient
	private DBType databaseType;
	
	public void setDatabaseType(DBType databaseType) {
		this.databaseType = databaseType;
	}
	
	// Constructors
	/** default constructor */
	public MetaColumn() {
		this.columnState="0";
	}
	/** minimal constructor */
	public MetaColumn(com.centit.metaform.po.MetaColumnId id 
				
		,String  fieldLabelName,String  columnType,String  accessType,String  columnState) {
		this.cid = id; 
			
	
		this.fieldLabelName= fieldLabelName; 
		this.columnFieldType= columnType; 
		this.accessType= accessType; 
		this.columnState= columnState; 		
	}

/** full constructor */
	public MetaColumn(com.centit.metaform.po.MetaColumnId id			
	, String  fieldLabelName, String  columnComment, Long  columnOrder, String  columnType,
					  Integer  maxLength, Integer  scale, String  accessType, String isMandatoryColumn,
					  String isPrimarykeyColumn, String  columnState, String  referenceType, String  referenceData,
					  String  validateRegex, String  validateInfo, String  autoCreateRule, String  autoCreateParam,
					  Date  lastModifyDate, String  recorder) {
		this.cid = id; 
			
	
		this.fieldLabelName= fieldLabelName;
		this.columnComment= columnComment;
		this.columnOrder= columnOrder;
		this.columnFieldType= columnType;
		this.maxLength= maxLength;
		this.scale= scale;
		this.accessType= accessType;
		this.isMandatoryColumn = isMandatoryColumn;
		this.isPrimarykeyColumn = isPrimarykeyColumn;
		this.columnState= columnState;
		this.referenceType= referenceType;
		this.referenceData= referenceData;
		this.validateRegex= validateRegex;
		this.validateInfo= validateInfo;
		this.autoCreateRule= autoCreateRule;
		this.autoCreateParam= autoCreateParam;
		this.lastModifyDate= lastModifyDate;
		this.recorder= recorder;		
	}

	public MetaColumn(PendingMetaColumn next) {
		this.setTableId(next.getTableId());  
		this.setColumnName(next.getColumnName());
		this.fieldLabelName= next.getFieldLabelName();  
		this.columnComment= next.getColumnComment();  
		this.columnOrder= next.getColumnOrder();  
		this.columnFieldType= next.getColumnFieldType();  
		this.maxLength= next.getMaxLength();  
		this.scale= next.getScale();  
		this.accessType= next.getAccessType();  
		this.isMandatoryColumn = next.getMandatory();
		this.isPrimarykeyColumn = next.getPrimarykey();
		this.columnState= next.getColumnState();  
		this.referenceType= next.getReferenceType();  
		this.referenceData= next.getReferenceData();  
		this.validateRegex= next.getValidateRegex();  
		this.validateInfo= next.getValidateInfo();  
		this.autoCreateRule= next.getAutoCreateRule();  
		this.autoCreateParam= next.getAutoCreateParam();  
		this.lastModifyDate= next.getLastModifyDate();  
		this.recorder= next.getRecorder();
	}
	public com.centit.metaform.po.MetaColumnId getCid() {
		return this.cid;
	}
	
	public void setCid(com.centit.metaform.po.MetaColumnId id) {
		this.cid = id;
	}
  
	public Long getTableId() {
		if(this.cid==null)
			return null;
		return this.cid.getTableId();
	}
	
	public void setTableId(Long tableId) {
		if(this.cid==null)
			this.cid = new com.centit.metaform.po.MetaColumnId();
		this.cid.setTableId(tableId);
	}
  
	public String getColumnName() {
		if(this.cid==null)
			this.cid = new com.centit.metaform.po.MetaColumnId();
		return this.cid.getColumnName();
	}
	
	public void setColumnName(String columnName) {
		if(this.cid==null)
			this.cid = new com.centit.metaform.po.MetaColumnId();
		this.cid.setColumnName(columnName);
	}
	
	

	// Property accessors
  
	public String getFieldLabelName() {
		return this.fieldLabelName;
	}
	
	public void setFieldLabelName(String fieldLabelName) {
		this.fieldLabelName = fieldLabelName;
	}
  
	public String getColumnComment() {
		return this.columnComment;
	}
	
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
  
	public Long getColumnOrder() {
		return this.columnOrder;
	}
	
	public void setColumnOrder(Long columnOrder) {
		this.columnOrder = columnOrder;
	}
  
	public String getColumnFieldType() {
		return this.columnFieldType;		
	}
	
	public void setColumnFieldType(String columnType) {
		this.columnFieldType = columnType;
	}

	public String getAccessType() {
		return this.accessType;
	}
	
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
  
	public String getIsMandatoryColumn() {
		return this.isMandatoryColumn;
	}
	
	public void setIsMandatoryColumn(String isMandatoryColumn) {
		this.isMandatoryColumn = isMandatoryColumn;
	}
  
	public String getIsPrimarykeyColumn() {
		return this.isPrimarykeyColumn;
	}
	
	public void setIsPrimarykeyColumn(String isPrimarykeyColumn) {
		this.isPrimarykeyColumn = isPrimarykeyColumn;
	}
  
	public String getColumnState() {
		return this.columnState;
	}
	
	public void setColumnState(String columnState) {
		this.columnState = columnState;
	}
	/**
	 *  0：没有：1： 数据字典(列表)   2： 数据字典(树型)   3：JSON表达式 4：sql语句   5：SQL（树）
	   9 :框架内置字典（用户、机构、角色等等）  Y：年份 M：月份   F:文件（column_Type 必须为 varchar（64））
	 * @return
	 */
	public String getReferenceType() {
		return this.referenceType;
	}
	/**
	 *  0：没有：1： 数据字典(列表)   2： 数据字典(树型)   3：JSON表达式 4：sql语句   5：SQL（树）
	   9 :框架内置字典（用户、机构、角色等等）  Y：年份 M：月份   F:文件（column_Type 必须为 varchar（64））
	 * @param referenceType
	 */
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
  
	public String getReferenceData() {
		return this.referenceData;
	}
	
	public void setReferenceData(String referenceData) {
		this.referenceData = referenceData;
	}
  
	public String getValidateRegex() {
		return this.validateRegex;
	}
	
	public void setValidateRegex(String validateRegex) {
		this.validateRegex = validateRegex;
	}
  
	public String getValidateInfo() {
		return this.validateInfo;
	}
	
	public void setValidateInfo(String validateInfo) {
		this.validateInfo = validateInfo;
	}
  
	/**
	 * 这个是用于生产数据库表创建语句的，不是用来生成表单默认值的
	 */
	public String getDefaultValue() {
		return "C".equals(autoCreateRule)?autoCreateParam:null;
		/*switch(autoCreateRule){
		case "C":
			return autoCreateParam;
		case "U":
			return UuidOpt.getUuidAsString();
		case "S":
			return null;
		default:
			return null;
		}*/
	}
 
	public Date getLastModifyDate() {
		return this.lastModifyDate;
	}
	
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
  
	public String getRecorder() {
		return this.recorder;
	}
	
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}



	public String getAutoCreateRule() {
		return autoCreateRule;
	}
	public void setAutoCreateRule(String autoCreateRule) {
		this.autoCreateRule = autoCreateRule;
	}
	public String getAutoCreateParam() {
		return autoCreateParam;
	}
	public void setAutoCreateParam(String autoCreateParam) {
		this.autoCreateParam = autoCreateParam;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	
	public MetaColumn copy(MetaColumn other){
  
		this.setTableId(other.getTableId());  
		this.setColumnName(other.getColumnName());
  
		this.fieldLabelName= other.getFieldLabelName();  
		this.columnComment= other.getColumnComment();  
		this.columnOrder= other.getColumnOrder();  
		this.columnFieldType= other.getColumnFieldType();  
		this.maxLength= other.getMaxLength();  
		this.scale= other.getScale();  
		this.accessType= other.getAccessType();  
		this.isMandatoryColumn = other.getIsMandatoryColumn();
		this.isPrimarykeyColumn = other.getIsPrimarykeyColumn();
		this.columnState= other.getColumnState();  
		this.referenceType= other.getReferenceType();  
		this.referenceData= other.getReferenceData();  
		this.validateRegex= other.getValidateRegex();  
		this.validateInfo= other.getValidateInfo();  
		this.autoCreateRule= other.getAutoCreateRule();  
		this.autoCreateParam= other.getAutoCreateParam();  
		this.lastModifyDate= other.getLastModifyDate();  
		this.recorder= other.getRecorder();

		return this;
	}
	
	public MetaColumn copyNotNullProperty(MetaColumn other){
  
		if( other.getTableId() != null)
			this.setTableId(other.getTableId());  
		if( other.getColumnName() != null)
			this.setColumnName(other.getColumnName());
		
		if( other.getFieldLabelName() != null)
			this.fieldLabelName= other.getFieldLabelName();  
		if( other.getColumnComment() != null)
			this.columnComment= other.getColumnComment();  
		if( other.getColumnOrder() != null)
			this.columnOrder= other.getColumnOrder();  
		if( other.getColumnFieldType() != null)
			this.columnFieldType= other.getColumnFieldType();  
		//if( other.getMaxLength() != null)
		this.maxLength= other.getMaxLength();  
		this.scale= other.getScale();  
		if( other.getAccessType() != null)
			this.accessType= other.getAccessType();  
		if( other.getIsMandatoryColumn() != null)
			this.isMandatoryColumn = other.getIsMandatoryColumn();
		if( other.getIsPrimarykeyColumn() != null)
			this.isPrimarykeyColumn = other.getIsPrimarykeyColumn();
		if( other.getColumnState() != null)
			this.columnState= other.getColumnState();  
		if( other.getReferenceType() != null)
			this.referenceType= other.getReferenceType();  
		if( other.getReferenceData() != null)
			this.referenceData= other.getReferenceData();  
		if( other.getValidateRegex() != null)
			this.validateRegex= other.getValidateRegex();  
		if( other.getValidateInfo() != null)
			this.validateInfo= other.getValidateInfo();
		if( other.getAutoCreateRule() != null)
			this.autoCreateRule= other.getAutoCreateRule();  
		if( other.getAutoCreateParam() != null)
			this.autoCreateParam= other.getAutoCreateParam();  
		if( other.getLastModifyDate() != null)
			this.lastModifyDate= other.getLastModifyDate();  
		if( other.getRecorder() != null)
			this.recorder= other.getRecorder();		

		return this;
	}

	public MetaColumn clearProperties(){
  
		this.fieldLabelName= null;  
		this.columnComment= null;  
		this.columnOrder= null;  
		this.columnFieldType= null;  
		this.maxLength= null;  
		this.scale= null;  
		this.accessType= null;  
		this.isMandatoryColumn = null;
		this.isPrimarykeyColumn = null;
		this.columnState= null;  
		this.referenceType= null;  
		this.referenceData= null;  
		this.validateRegex= null;  
		this.validateInfo= null;  
		this.autoCreateRule= null;  
		this.lastModifyDate= null;  
		this.recorder= null;

		return this;
	}
	@Override
	public String getPropertyName() {
		return SimpleTableField.mapPropName(getColumnName());
	}
	
	public static String mapToFieldType(String columnType,int scale)
	{
		if("NUMBER".equalsIgnoreCase(columnType) ||
		   "INTEGER".equalsIgnoreCase(columnType)||
		   "DECIMAL".equalsIgnoreCase(columnType) ){
			if( scale > 0 )
				return FieldType.FLOAT;
			else
				return FieldType.INTEGER;
		}else if("FLOAT".equalsIgnoreCase(columnType)){
			return FieldType.FLOAT;
		}else if("CHAR".equalsIgnoreCase(columnType) ||
			   "VARCHAR".equalsIgnoreCase(columnType)||
			   "VARCHAR2".equalsIgnoreCase(columnType)||
			   "STRING".equalsIgnoreCase(columnType) ){
			return FieldType.STRING;
		}else if("DATE".equalsIgnoreCase(columnType) ||
				   "TIME".equalsIgnoreCase(columnType) ){
			return FieldType.DATE;
		}else if("TIMESTAMP".equalsIgnoreCase(columnType)||
				   "DATETIME".equalsIgnoreCase(columnType) ){
			return FieldType.DATETIME;
		}else if("CLOB".equalsIgnoreCase(columnType) /*||
				   "LOB".equalsIgnoreCase(sDBType)||
				   "BLOB".equalsIgnoreCase(sDBType)*/ ){
			return FieldType.TEXT;
		}else if("BOOLEAN".equalsIgnoreCase(columnType) ){
			return FieldType.BOOLEAN;
		}else
			return columnType;
	}
	
	@Override
	public String getJavaType() {
		return MetaColumn.mapToFieldType(this.columnFieldType,this.scale==null?0:this.scale);
	}

	@Override
	public boolean isMandatory() {
		return "T".equals(isMandatoryColumn) || "Y".equals(isMandatoryColumn) || "1".equals(isMandatoryColumn);
	}
	
	public boolean isPrimaryKey() {
		return "T".equals(isPrimarykeyColumn) || "Y".equals(isPrimarykeyColumn) || "1".equals(isPrimarykeyColumn);
	}
	
	@Override
	public int getMaxLength() {
		if("string".equalsIgnoreCase(this.columnFieldType) ||
				"integer".equalsIgnoreCase(this.columnFieldType)||
				"float".equalsIgnoreCase(this.columnFieldType) ||
				"varchar".equalsIgnoreCase(this.columnFieldType)||
				"number".equalsIgnoreCase(this.columnFieldType))
			return maxLength==null? 0 : maxLength.intValue();
		return 0;
	}
	@Override
	public int getPrecision() {
		return getMaxLength();
	}
	@Override
	public int getScale() {
		if("float".equalsIgnoreCase(this.columnFieldType) ||
				"number".equalsIgnoreCase(this.columnFieldType))
			return scale==null?0:scale.intValue();
		return 0;
	}
	
	@Override
	@JSONField(serialize=false)
	public String getColumnType() {		
		return FieldType.mapToDBColumnType(this.databaseType, this.columnFieldType);
	}
}
