package com.joonyoung.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joonyoung.board.entity.SiteMember;

public interface MemberRepository extends JpaRepository<SiteMember, Integer> {
	
	public Optional<SiteMember> findByUsername(String username);
	
}
