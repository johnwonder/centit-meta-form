-- 序列 主键生成策略为sequence时使用
EQUENCE  "METAFORM"."SEQ_PENDINGTABLEID"  MINVALUE 0 MAXVALUE 99999999999999 INCREMENT BY 1 START WITH 10 CACHE 10 NOORDER  NOCYCLE
-- 序列表 主键为tableGenerator时使用
CREATE TABLE "METAFORM"."HIBERNATE_SEQUENCES" (
"SEQ_NAME" VARCHAR2(15 BYTE) NOT NULL ,
"SEQ_VALUE" NUMBER(20) NULL 
)