package tw.hui.spring10.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.hui.spring10.entity.Member;

public interface MemberRepo extends JpaRepository<Member, Long> {
	boolean existsByEmail(String email);
	Member findByEmail(String email);
}
