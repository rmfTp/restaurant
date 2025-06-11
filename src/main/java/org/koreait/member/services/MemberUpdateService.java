package org.koreait.member.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.exceptions.script.AlertException;
import org.koreait.global.libs.Utils;
import org.koreait.member.entities.Member;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
public class MemberUpdateService {
    private final Utils utils;
    private final HttpServletRequest request;
    private final MemberRepository repository;

    public void processBatch(List<Integer> chks){
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("처리할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        String method = request.getMethod();
        List<Member> members = new ArrayList<>();
        for (int chk : chks) {
            Long seq = Long.valueOf(utils.getParams("seq_" + chk));
            Member member = repository.findById(seq).orElse(null);
            if (member == null) continue;
            if (method.equalsIgnoreCase("DELETE")) {
                member.setDeletedAt(LocalDateTime.now());
            } else {
                boolean updateCredentialAt = Boolean.parseBoolean(Objects.requireNonNullElse(utils.getParams("updateCredentialAt_" + chk),"false"));
                if (updateCredentialAt) member.setCredentialChangedAt(LocalDateTime.now());
                boolean cancelResign = Boolean.parseBoolean(Objects.requireNonNullElse(utils.getParams("cancelResign_" + chk), "false"));
                if (cancelResign) {
                    member.setDeletedAt(null);
                }
            }
            members.add(member);
        }
        repository.saveAll(members);
    }
}
