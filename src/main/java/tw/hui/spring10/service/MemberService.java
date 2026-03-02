package tw.hui.spring10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.hui.spring10.entity.Member;
import tw.hui.spring10.repo.MemberRepo;
import tw.hui.spring10.util.BCrypt;

@Service
public class MemberService {
	@Autowired
	private MemberRepo repo;
	
	public Member register(String email, String passwd, String name) {
		if(repo.existsByEmail(email)) throw new IllegalArgumentException("email exist");
		
		Member member = new Member();
		member.setEmail(email);
		member.setPasswd(BCrypt.hashpw(passwd, BCrypt.gensalt()));
		member.setName(name);
		Member saveMember = repo.save(member);
		return saveMember;
		
	}
}
