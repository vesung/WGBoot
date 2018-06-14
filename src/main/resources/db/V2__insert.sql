-------------------------------------------------------------
--                 WaBoot 系统初始化脚本                                          --
-------------------------------------------------------------


-- 初始化超级用户信息
insert into BASE_USER(ID, PASSWORD, NAME, ROLEID, PHONE, EMAIL, ORGID, SEX, UPDATETIME) 
    values('admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', 'admin', '10010', 'admin@cx.com', 'admin', 'm', CURRENT_TIMESTAMP());
