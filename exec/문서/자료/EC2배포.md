# J4A206T.pem 활용하여 로그인
```shell
  ssh -i J4A206T.pem ubuntu@j4a206.p.ssafy.io
  ```

# Java 설치 & 환경변수 지정
### Java 버전 확인
 ```shell
 java -version
 ```
- 없으면: sudo apt install openjdk-8-jre-headless (java 8 설치)
- 설치 후 java -version으로 다시 확인
    - openjdk version "1.8.0_282" 메세지 확인
 ```shell
 javac -version
 ```
- 없으면:  sudo apt install openjdk-8-jdk-headless (java 8 설치)

### 환경변수 설정
##### JAVA_HOME 확인
 ```shell
    $ echo $JAVA_HOME
 ```
##### 경로 확인
 ```shell
   $ which java
     /usr/bin/java
   $ readlink -f /usr/bin/java
     /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
 ```
##### 경로 수정 위해 vi편집기 접근
 ```shell
   sudo vi /etc/profile
 ```
##### 편집 
 ```shell
     i
 ```
##### 경로 추가
 ```shell
   JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
   PATH=$JAVA_HOME/jre/bin:$PATH
   CLASSPATH=$JAVA_HOME/jre/lib:$JAVA_HOME/lib/tools.jar
   export JAVA_HOME PATH CLASSPATH
 ```
- 입력 후 esc눌러 insert모드를 종료하고 :wq를 입력(저장 후 종료)하여 vi편집기 종료
##### 경로 저장 되었나 확인
 ```shell
   echo $JAVA_HOME
   echo $PATH
   echo $CLASSPATH
 ```

# 깃 설치 & 업데이트
```shell
cd /home/ubuntu
```

### 깃 설치
 ```shell
   sudo apt-get install git
 ```

### git 확인
  ```shell
   git version
  ```

### git clone (git 주소)
  - 아이디, 비번 치고 로그인
  - git pull으로 업데이트

### Backend server 실행
```shell
cd backend
./mvnw clean package
cd target
cd java -jar (생성된 jar 파일)
```
 