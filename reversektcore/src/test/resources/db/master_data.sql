/*Inserting companies on startup since they are required  in all entities*/
INSERT INTO company_mstr (COMPANY_NAME,COMPANY_CD,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) VALUES ('LNT Infotech','LTI_CD',0,0,null,null,'A');



INSERT INTO user_role (COMPANY_MSTR_SEQ,USER_ROLE_DESC,USER_ROLE_CD,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) values (1,'Project Manager in company','ROLE_PROJECT_MANAGER',0,0,null,null,'A');
