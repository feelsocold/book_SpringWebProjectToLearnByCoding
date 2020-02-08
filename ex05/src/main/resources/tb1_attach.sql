CREATE TABLE tb1_attach(
    uuid varchar2(100) NOT NULL,
    uploadPath varchar2(200) NOT NULL,
    fileName varchar2(100) NOT NULL,
    bno NUMBER(10,0)
);

ALTER TABLE tb1_attach
ADD CONSTRAINT pk_attach PRIMARY KEY(uuid);

ALTER TABLE tb1_attach 
ADD CONSTRAINT fk_board_attach FOREIGN KEY(bno)
REFERENCES  tb1_board(bno);