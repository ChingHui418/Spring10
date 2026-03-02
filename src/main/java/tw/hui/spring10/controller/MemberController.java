package tw.hui.spring10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.hui.spring10.dto.Register;
import tw.hui.spring10.entity.Member;
import tw.hui.spring10.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	private MemberService service;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Register login) {
		try {
			Member member = service.register(
					login.email(), login.passed(), login.name());
			return ResponseEntity.ok(member);
		}catch(RuntimeException e) {
			return ResponseEntity.ok("error:" + e.getMessage());
		}	
	}
	
}
