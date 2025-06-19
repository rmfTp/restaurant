package org.koreait.survey.diabetes.constants;

public enum SmokingHistory {
    NO_INFO(0),     // 응답없음
    NEVER(1),       // 한번도 흡연 안함
    NOT_CURRENT(2), // 현재는 흡연 안함(과거 미상)
    FORMER(3),      // 과거에 흡연 했지만 현제 끊음
    EVER(4),        // 과거에 흡연 함(현제 미상)
    CURRENT(5);     // 흡연 중

    private final int num;

    SmokingHistory(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
