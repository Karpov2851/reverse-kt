INSERT INTO company_mstr (COMPANY_NAME,COMPANY_CD,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) VALUES ('LNT Infotech','LTI_CD',0,0,null,null,'A');
INSERT INTO user_role (COMPANY_MSTR_SEQ,USER_ROLE_DESC,USER_ROLE_CD,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) values (1,'Project Manager in company','ROLE_PROJECT_MANAGER',0,0,null,null,'A');
INSERT INTO user_profile (CREATED_BY, CREATED_DATE, STATUS, UPDATED_BY, UPDATED_DATE, PASSWORD, USER_ID, USER_PROFILE_IMAGE_PATH, USER_PROFILE_IMAGE_REF_CD, COMPANY_MSTR_SEQ, USER_ROLE_SEQ) VALUES (
0, null, 'A', 0, null, '$2a$10$45dDLeCNcVX9UFzJbC1.n.StkPPX5d2R4O.W/e9wiD705QPP.6Z.C', 'user_id', NULL, NULL, 1, 1);
INSERT INTO business_unit (BUSINESS_CD,BUSINESS_DESC,BUSINESS_UNIT_NAME,COMPANY_MSTR_SEQ,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) VALUES ('business_cd','Banking related domain','BU-BANK',1,0,0,null,null,'A');
INSERT INTO project (PROJECT_DESC,PROJECT_NAME,COMPANY_MSTR_SEQ,BUSINESS_UNIT_SEQ,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) VALUES ('Proect 1 of BU-BANK','PROJ_1_BU_BANK',1,1,0,0,null,null,'A');
INSERT INTO project_item (PROJECT_SEQ,PROJECT_ITEM_DESC,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) VALUES (1,'Project item one',0,0,null,null,'A');
INSERT INTO video_details (VIDEO_NAME,VIDEO_DETAILS,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS) VALUES ('video','videodetails',0,0,null,null,'A');
INSERT INTO scheduled_session (PROJECT_ITEM_SEQ,VIDEO_DETAIL_SEQ,SESSION_START_DATE_TIME,SESSION_END_DATE_TIME,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE,STATUS)
VALUES (1,1,null,null,0,0,null,null,'A');