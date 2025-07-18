package org.koreait.survey.diabetes.services;

import lombok.RequiredArgsConstructor;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.survey.diabetes.controllers.RequestDiabetesSurvey;
import org.koreait.survey.diabetes.entities.DiabetesSurvey;
import org.koreait.survey.diabetes.repositories.DiabetesSurveyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class DiabetesSurveyService {
    private final DiabetesSurveyPredictService predictService;
    private final DiabetesSurveyRepository repository;
    private final MemberUtil memberUtil;
    private final ModelMapper mapper;

    /**
     * 설문답변으로 예측 결과 가져오기
     * 로그인한 회원정보 가져오기
     * db에 저장 처리
     * @param form
     */
    public DiabetesSurvey process(RequestDiabetesSurvey form){
        boolean diabetes = predictService.isDiabetes(form);
        Member member = memberUtil.getMember();
        double bmi = predictService.getBmi(form.getHeight(), form.getWeight());

        DiabetesSurvey item = mapper.map(form, DiabetesSurvey.class);

        item.setDiabetes(diabetes);
        item.setBmi(bmi);
        if (memberUtil.isLogin()){
            item.setMember(member);
        }

        repository.saveAndFlush(item);/* 그냥 save로 사용가능
        암묵적으로 플러쉬가 되어있을 것이지만 그냥 쓰는게 실수를 줄이는 방법임 */

        return repository.findById(item.getSeq()).orElse(null);
    }
}
