package com.salmon.board.repository;

import com.salmon.board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByPasswordAndEmail(String email, String password);
}
