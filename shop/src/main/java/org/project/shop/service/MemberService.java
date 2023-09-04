package org.project.shop.service;

import org.project.shop.domain.Member;

import java.util.List;

public interface MemberService {
    public Long join(Member member);
    public List<Member> findAllMember();
    public Member findOneMember(Long memberId);

    public List<Member> findByEmail(String email);
}
