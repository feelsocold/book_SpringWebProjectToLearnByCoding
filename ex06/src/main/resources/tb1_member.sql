CREATE table tb1_member(
    userid VARCHAR2(50) NOT NULL PRIMARY KEY,
    userpw VARCHAR2(100) NOT NULL,
    username VARCHAR2(100) NOT NULL,
    regdate date DEFAULT SYSDATE,
    updatedate DATE DEFAULT SYSDATE,
    ENABLED CHAR(1) default '1'     );
    
CREATE TABLE tb1_member_auth (
    userid VARCHAR2(50) NOT NULL,
    auth VARCHAR2(50) NOT NULL,
    CONSTRAINT fk_member_auth FOREIGN KEY(userid) REFERENCES tb1_member(userid) );


    

