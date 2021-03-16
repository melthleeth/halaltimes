# [mysql] 문자열과 정수로 된 기본키 자동 증가 구현하기

### AS-IS

```sql
table
id Varchar(45) NOT NULL AUTO_INCREMENT PRIMARY KEY,
name CHAR(30) NOT NULL,
```

id field를 `'LHPL001','LHPL002','LHPL003'` 로 표현하려고 한다.



### TO-BE

**Tables**

```sql
CREATE TABLE table1_seq
(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);
CREATE TABLE table1
(
  id VARCHAR(7) NOT NULL PRIMARY KEY DEFAULT '0', name VARCHAR(30)
);
```

**Trigger**

```sql
DELIMITER $$
CREATE TRIGGER tg_table1_insert
BEFORE INSERT ON table1
FOR EACH ROW
BEGIN
  INSERT INTO table1_seq VALUES (NULL);
  SET NEW.id = CONCAT('LHPL', LPAD(LAST_INSERT_ID(), 3, '0'));
END$$
DELIMITER ;
```

로 설정하고 table1에 row를 다음과 같이 추가하면

```sql
INSERT INTO Table1 (name) 
VALUES ('Jhon'), ('Mark');
```

결과값

```
|      ID | NAME |
------------------
| LHPL001 | Jhon |
| LHPL002 | Mark |
```





출처:https://stajun.tistory.com/entry/mysql-%EB%AC%B8%EC%9E%90%EC%97%B4%EA%B3%BC-%EC%A0%95%EC%88%98%EB%A1%9C-%EB%90%9C-%EA%B8%B0%EB%B3%B8%ED%82%A4-%EC%9E%90%EB%8F%99-%EC%A6%9D%EA%B0%80-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0

https://stackoverflow.com/questions/17893988/how-to-make-mysql-table-primary-key-auto-increment-with-some-prefix