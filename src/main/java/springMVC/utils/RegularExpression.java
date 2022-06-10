package springMVC.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

//유효성검사 (정규표현식)
public class RegularExpression {

	// 로깅
	private Logger log = LogManager.getLogger(getClass());
	
	// 글자수 제한
	// 숫자 최소1 최대10
	public boolean isNumericCount(String str) {
		if(Pattern.matches("^[1-9]{1,10}$", str)){
			log.info("올바른 숫자 입력됨(숫자 최소1 최대10)");
			return true;
		} else {
			log.info("올바르지 않은 숫자. 숫자는 최소1글자 이상, 최대 10글자 까지 입력 가능함(숫자 최소1 최대10)");
			return false;
		}
	}
	
	// 공백/특수문자 검사기
	public boolean isBlankSpecial(String str) {
		if(Pattern.matches("^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$", str)){
			log.info("공백/특수문자 입력되지 않음");
			return true;
		} else {
			log.info("공백/특수문자 입력됨");
			return false;
		}
	}
	
	// 공백 검사기
	public boolean isBlank(String str) {
		if(Pattern.matches("[ \\s]", str)) {
			log.info("공백 입력됨");
			return true;
		} else {
			log.info("공백 입력되지 않음");
			return false;
		}
	}
	
	// 공백 혹은 탭문자 검사기
	public boolean isBlankTab(String str) {
		if(Pattern.matches("[ \\S]", str)) {
			log.info("공백/탭문자 입력되지 않음");
			return true;
		} else {
			log.info("공백/탭문자 입력됨");
			return false;
		}
	}
	// 숫자 검사기
	public boolean isNumeric(String str) {
		return Pattern.matches("^[0-9]*$", str);
	}
	// 영어 검사기
	public boolean isAlpha(String str) {
		return Pattern.matches("^[a-zA-Z]*$", str);
	}
	// 한국어 혹은 영어 검사기
	public boolean isAlphaNumeric(String str) {
		return Pattern.matches("[a-zA-Z0-9]*$", str);
	}
	// 한글 검사기
	public boolean isKorean(String str) {
		if (Pattern.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣0-9!?()]+.*", str.replace("\n",""))) {
			log.info("한글 입력됨");
			return true;
		} else {
			log.info("한글 입력되지 않음");
			return false;
		}
	}
	// 대문자 검사기
	public boolean isUpper(String str) {
		return Pattern.matches("^[A-Z]*$", str);
	}
	// 소문자 검사기
	public boolean isDowner(String str) {
		return Pattern.matches("^[a-z]*$", str);
	}
	// 이메일 검사기
	public boolean isEmail(String str) {
		return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", str);
	}
	// URL 검사기 (Http, Https 제외)
	public boolean isURL(String str) {
		return Pattern.matches("^[^((http(s?))\\:\\/\\/)]([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$", str);
	}
	// 휴대전화 번호 검사 1
	// - 제외
	public boolean isMob1(String str) {
		if (Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str)) {
			log.info("휴대전화번호 입력됨(- 제외)");
			return true;
		} else {
			log.info("휴대전화번호 입력되지 않음(- 제외)");
			return false;
		}
	}
	// 휴대전화 번호 검사 2 (미사용)
	// - 포함
	// 일반적으로 휴대폰번호는 대쉬(-) 없이 저장하기 때문에 사용하지 않음
//	public boolean isMob2(String str) {
//		if (Pattern.matches("^\\\\d{3}-\\\\d{3,4}-\\\\d{4}$", str)) {
//			System.out.println("휴대전화번호 입력됨(- 포함)");
//			return true;
//		} else {
//			System.out.println("휴대전화번호 입력되지 않음(- 포함)");
//			return false;
//		}
//	}
	// 일반 전화 검사기
	public boolean isPhone(String str) {
		return Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);
	}
	// IP 검사기
	public boolean isIp(String str) {
		return Pattern.matches("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})", str);
	}
	// 우편번호 검사기
	public boolean isPost(String str) {
		return Pattern.matches("^\\d{3}\\d{2}$", str);
	}
	// 주민등록번호 검사기
	public boolean isPersonalID(String str) {
		return Pattern.matches("^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$", str);
	}
	// 한자를 포함하고 있는지 여부
	public boolean containsChineseCharacters(String str) {
		String regEx = ".*[\u2e80-\u2eff\u31c0-\u31ef\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fbf\uf900-\ufaff].*";
		return str.matches(regEx);
	}
	// 신용 카드 양식(xxxx-xxxx-xxxx-xxxx)
	public boolean isCreditCard(String cardNumber) {
		return Pattern.matches("((5[1-5]\\d{14})|(4\\d{12}(\\d{3})?)|(3[47]\\d{13})|(6011\\d{12})|((30[0-5]|3[68]\\d)\\d{11}))", cardNumber);
	}
	// 신용카드 유효기간 양식
	public boolean isExpireDate(String expireDate) {
		return Pattern.matches("[0-9]{2}(?:0[1-9]|1[0-2])", expireDate);
	}
	
	/*
.
 하나의 문자. 개행문자가 제외 되기도 한다. 브라켓 안에 표현된 [.]은 단순한 마침표로 처리된다.
[ ]
 브라켓 안에 포함된 문자 중 하나를 의미한다. 연속된 문자는 -로 연결해서 표시할 수도 있다. 
 -기호가 맨 앞이나 맨뒤에 있는 때에는 단순히 -문자로 이해된다.
[^ ]
 브라켓 안에 포함되지 않은 문자 중하나를 의미한다.
^
 문자열의 시작 위치를 나타낸다. 여러 줄에서 처리하는 때에는 각 줄의 시작을 나타낸다.
$
 문자열의 마지막 위치 또는 개행문자의 바로 앞 위치를 나타낸다.
( )
 괄호 안의 일치되는 부분을 묶어서 나중에 사용할 수 있다.
\1
 1~9까지의 숫자를 사용할 수 있으며, 괄호로 묶여진 그룹을 호출할 때 사용된다.
*
 바로 앞의 패턴이 0번 이상 일치됨을 의미한다.
{1, 2}
 바로 앞의 패턴이 최소 1번 최대 2번 일치됨을 의미한다.
?
 바로 앞의 패턴의 0 또는 1번 일치됨을 의미한다.
+
 바로 앞의 패턴이 1번 이상 일치됨을 의미한다.
|
 앞의 패턴 또는 뒤의 패턴 중 하나와 일치되는 경우이다.
[:alnum:]
[A-Za-z0-9]
 영문자와 숫자
[:word:]
\w
[A-Za-z0-9_]
 영문자와 숫자 그리고 밑줄 문자
\W
[^A-Za-z0-9]
 영문자와 숫자 그리고 밑줄 문자 이외의 문자
[:alpha:]
[A-Za-z]
 영문자
[:blank:]
[ \t]
 공백과 탭 문자
\b
[(?<=\W)(?=\w)|(?<=\w)(?=\W)]
 단어 사이의 경계
[:cntrl:]
[\x00-\x1F\x7F]
 제어문자
[:digit:]
\d
[0-9]
 숫자
\D
[^0-9]
 숫자 이외의 문자
[:graph:]
[\x21-\x7E]
 보여지는 문자
[:lower:]
[a-z]
 소문자
[:print:]
[\x20-\7E]
 보여지는 문자와 공백 문자
[:punct:]
[][!#$%&’()*+,./:;<=>?@\^_`{|}~-]
 문장부호
[:space:]
\s
[ \t\r\n\v\f]
 공백문자
\S
[^ \t\r\n\v\f]
 공백문자 이외의 문자
[:upper:]
[A-Z]
 대문자
[:xdigit:]
[A-Fa-f0-9]
 16진 숫자 */
}