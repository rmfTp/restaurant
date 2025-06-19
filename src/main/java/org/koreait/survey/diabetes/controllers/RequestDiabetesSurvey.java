package org.koreait.survey.diabetes.controllers;

import lombok.Data;
import org.koreait.global.constants.Gender;
import org.koreait.survey.diabetes.constants.SmokingHistory;

@Data
public class RequestDiabetesSurvey {
    private String mode;
    private Gender gender;
    private int age;
    private boolean hypertension;           // 혈압
    private boolean heartDisease;           // 심장병 유무
    private SmokingHistory smokingHistory;
    private double height;                  // cm
    private double weight;                  // kg
    private double hbA1c;                   // 당화혈색소(2~3개월 평균 혈당 수치) (mg/dL)
    private double bloodGlucoseLevel;       // 혈당 수치 (mg/dL)
}
