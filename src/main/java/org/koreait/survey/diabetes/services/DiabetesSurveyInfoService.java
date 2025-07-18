package org.koreait.survey.diabetes.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.exceptions.UnAuthorizedException;
import org.koreait.global.search.CommonSearch;
import org.koreait.global.search.ListData;
import org.koreait.global.search.Pagination;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.survey.diabetes.entities.DiabetesSurvey;
import org.koreait.survey.diabetes.entities.QDiabetesSurvey;
import org.koreait.survey.diabetes.repositories.DiabetesSurveyRepository;
import org.koreait.survey.exceptions.SurveyNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Lazy
@Service
@Transactional
@RequiredArgsConstructor
public class DiabetesSurveyInfoService {
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;
    private final DiabetesSurveyRepository repository;
    private final JPAQueryFactory queryFactory;

    public DiabetesSurvey get(Long seq) {
        DiabetesSurvey item = repository.findById(seq).orElseThrow(SurveyNotFoundException::new);

        Member member = item.getMember();
        Member loggedMember = memberUtil.getMember();
        if (
            !memberUtil.isLogin() || (!memberUtil.isAdmin() && !loggedMember.getSeq().equals(member.getSeq()))
        )throw new UnAuthorizedException();
        return item;
    }

    public ListData<DiabetesSurvey> getList(CommonSearch search){
        if (!memberUtil.isLogin())return new ListData<>();
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        int offset = (page - 1) * limit;

        Member loggedMember = memberUtil.getMember();
        QDiabetesSurvey diabetesSurvey = QDiabetesSurvey.diabetesSurvey;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(diabetesSurvey.member.eq(loggedMember));

        List<DiabetesSurvey> items = queryFactory.selectFrom(diabetesSurvey)
                .leftJoin(diabetesSurvey.member)
                .fetchJoin()
                .where(andBuilder)
                .orderBy(diabetesSurvey.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        long total =repository.count(andBuilder);

        Pagination pagination = new Pagination(page, (int) total, 10, limit, request);
        return new ListData<>(items,pagination);
    }
}
