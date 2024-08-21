-- ############################################
-- guestbook 만들기
-- ############################################

-- --------------------------------------------
-- root 계정에서 실행
-- --------------------------------------------

-- guestbook 계정생성 
create user 'guestbook'@'%' identified by 'guestbook' ;

-- guestbook 권한 부여
grant all privileges on guestbook_db.* to 'guestbook'@'%';

-- guestbook_db 생성
create database guestbook_db
	default character set utf8mb4	-- • 이모티콘사용 케릭터셋
	collate utf8mb4_general_ci		-- • 정렬규칙
	default encryption='n'			-- • 암호화 no (기본값 생략가능)
;

-- 데이터베이스 조회
show databases;




-- -----------------------------------------------------------
-- guestbook 계정에서 실행
-- -----------------------------------------------------------

-- db 선택
use guestbook_db;

-- table 삭제
drop table guest;

-- person 테이블 생성 
create table guest(
	no integer primary key auto_increment,
    name varchar(80) not null,
    password varchar(20),
    content text,
    reg_date datetime
);

-- 조회 
select * from guest;

select no,
		name,
        password,
        content,
        reg_date
from guest;

-- 넣기 (등록)
insert into guest
values(null, '정우성', '1234', '안녕하세요', now());

insert into guest
values(null, '이정재', '5678', 'hello', now());

-- 수정
update guest
set name = '강호동',
	password = '9999',
    content = '안녕하세요',
    reg_date = now()
where no = 2;

-- 삭제
delete from guest
where no = 2
and password= '9999';

-- auto_increment 번호 변경
alter table guest auto_increment = 2;