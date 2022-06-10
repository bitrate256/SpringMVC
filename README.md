# AndroidPOS

## 개발환경
* 빌드 도구 : maven
* 자바버전 : 1.8
* DB : mysql 5.7.15
* 톰캣 : 9
* 자동 병합+빌드 체계 : 리눅스+젠킨스 기반으로 개발중   


## 확인된 오류
1. plugin not found / not resolved 등 오류
   Settings > Build, Execution, Deployment > Build Tools > Maven    
   Use plugin registry 체크

2. SpringJUnit4ClassRunner requires JUnit 4.12 or higher
   Junit 버전을 4.12 이상을 쓸 것. 주의할 사항은, Junit 의 표기법에 따르면 4.12는 4.7보다 높은 버전이다.   

3. 각종 경로 오류 전반   
   * 인텔리제이 imi 설정파일과 스프링 xml 파일을 점검해서 필요한 각각의 경로를 점검한다   
     (AndroidPOS.imi / root-context.xml / applicationContext.xml)    
   * 점검해도 문제가 지속된다면 인텔리제이의 Artifacts와 Libraries를 확인하고 필요한 라이브러리가 제대로 들어가 있는지 확인한다. 확인하거나 재설정을 했다면 maven 탭에서 소스/문서를 다시 다운로드하고 라이프사이클에서 clean 및 install을 다시 시도한다 

## 기존 STS(이클립스)와의 프로젝트 구조 차이점 (현재는 STS 구조 사용중)
* ___현재는 STS 방식의 프로젝트 구조로 변경했습니다___   
  * 기본적으로 프로젝트를 생성하면 인텔리제이의 디렉토리 구조로 생성되고 실행도 되나 이전 방식으로 변경한 이유는, 본질적인 내용에는 변함이 없음에도 설정파일 이름과 디렉토리 구조 때문에 불필요한 혼동과 각종 설정오류등이 발생하였기 때문입니다      
  * 인텔리제이에서 프로젝트를 처음 생성하였을 때 기준으로 STS프로젝트와의 차이는 아래와 같습니다. _새로운 mvc 프로젝트 생성시 참고하십시오. 아직 STS 에서는 실행 확인되지 않았습니다_

|                   역할                   |         인텔리제이          |                                                  STS(이클립스)                                                  |   
|:--------------------------------------:|:----------------------:|:-----------------------------------------------------------------------------------------------------------:|   
| was 실행되고 웹 어플리케이션에 필요한 설정을 구성하는 xml 파일 |        web.xml         |                                                   web.xml                                                   |   
| 애플리케이션 컨텍스트(웹어플리케이션 공통으로 사용되는 빈을 설정함)  | applicationContext.xml |                                              root-context.xml                                               |   
|   서블릿 컨텍스트 (필요한 상황에 맞춰 사용되는 빈을 설정함)    | dispatcher-servlet.xml |                                             servlet-context.xml                                             |   
|                메이븐 설정파일                |        pom.xml         |                                                   pom.xml                                                   |   
|               웹 리소스 디렉토리               |          web           |                                               src/main/webapp                                               |
|                기타 디렉토리                 |      web/WEB-INF/      | src/main/webapp/WEB-INF/classes   src/main/webapp/WEB-INF/spring/appServlet   src/main/webapp/WEB-INF/views |

* 프로젝트 구조 차이에 대한 자세한 설명은 아래 링크를 추가로 참고하시기 바랍니다   
  https://whitepaek.tistory.com/56

* * *
## Project 내 클래스, 변수 명명 규칙
* 카멜케이스 명명법 사용 : 첫글자 소문자, 이후 연결단어들의 첫글자를 대문자   
  ex) camelCaseParameter
* 일반적으로 통용되는 명명 규칙을 시범 적용하겠습니다. 제안사항이 있다면 말씀 주시기 바랍니다    
* 이하 구체적인 명명법   
### class (클래스)
대문자로 시작하고, 명사를 사용합니다
```java
class Thread;
class Raster;
class ImageSprite;
```
### interface (인터페이스)
대문자로 시작하고, 각 단어 시작글자는 대문자 입니다   
형용사를 사용합니다   
```java
interface Runnable;
interface RasterDelegate;
interface Storing;
```
### method (함수)
소문자로 시작하고, 각 단어 시작글자는 대문자 입니다   
동사를 사용합니다   
```java
add();
runFast();
getBackground();
```
### variable (변수)
소문자로 시작하고, 각 단어 시작글자는 대문자 입니다   
```java
int i;
char c;
float myWidth;
String phoneNumber;
```
### Package (패키지)
소문자로 작성합니다
```java
com.sun.eng
com.apple.quicktime.v2
edu.cmu.cs.bovik.cheese
```
### constant (상수)
대문자와 언더바(_)로 작성합니다
```java
static final int MAX_WIDTH = 999;
static final int GET_THE_CPU = 1;
```
* * *
