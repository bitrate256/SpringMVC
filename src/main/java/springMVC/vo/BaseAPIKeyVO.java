package springMVC.vo;

public class BaseAPIKeyVO {

    // APIKey 전용 VO
    // 원래는 사용자별로 고유 APIKey가 있으므로 불필요 하지만, 개발 및 테스트를 위해 사용합니다.
    // 차후 사용자/데이터별 TABLE을 따로 생성하여 실제 서비스에 가까운 형태로 수정하겠습니다.
    private String APIKey;

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String aPIKey) {
        APIKey = aPIKey;
    }
}
