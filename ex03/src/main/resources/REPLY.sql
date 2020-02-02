CREATE TABLE tb1_reply(
    rno number(10,0),
    bno number(10,0) NOT NULL,
    reply varchar2(1000) NOT NULL,
    replyer varchar2(50) NOT NULL,
    replyDate date default sysdate,
    updateDate date default sysdate
);

CREATE sequence seq_reply;

ALTER TABLE tb1_reply
ADD CONSTRAINT pk_reply primary key (rno);

ALTER TABLE tb1_reply
ADD COnSTRAINT fk_reply_board
FOREIGN KEY (bno) REFERENCES tb1_board (bno);


CREATE INDEX idx_reply On tb1_reply (bno DESC, rno DESC);

COMMIT