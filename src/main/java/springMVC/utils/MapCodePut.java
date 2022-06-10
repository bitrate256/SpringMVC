package springMVC.utils;

import java.util.HashMap;
import java.util.Map;

// map_code 모듈화 클래스
public class MapCodePut {

	// map_code 응답
	public Map<String, Object> MapCode(String rtCode, String rtSub, String rtMsg) {

		/* result map 에 삽입할 자체 응답코드 */
		Map<String, Object> map_code = new HashMap<String, Object>();

		map_code.put("rtcode", rtCode);
		map_code.put("rtsub", rtSub);
		map_code.put("rtmsg", rtMsg);

		return map_code;
	}

	// 검색결과 map 이 비었을 때 빈값 리턴
	public Map<String, Object> MapEmpty(String userPhone, String userCnt, String regDate, String itemNum,
			String itemTime, String callYn, String enterYn) {

		/* result map 에 삽입할 map */
		Map<String, Object> map_empty = new HashMap<String, Object>();

		map_empty.put("userPhone", "");
		map_empty.put("userCnt", "");
		map_empty.put("regDate", "");
		map_empty.put("itemNum", "");
		map_empty.put("itemTime", "");
		map_empty.put("callYn", "");
		map_empty.put("enterYn", "");

		return map_empty;
	}
}
