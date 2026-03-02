package tw.hui.spring10.service;

import static org.mockito.Mockito.when;

// assertEquals, assertThrows, assertNotNull, assertNotEquals
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tw.hui.spring10.entity.Member;
import tw.hui.spring10.repo.MemberRepo;
import tw.hui.spring10.util.BCrypt;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTestV2 {
	@Mock MemberRepo repo; // fake	
	@InjectMocks MemberService service;
	
	private Member saved;
	
	@BeforeEach
	void setUp() {
		saved = new Member();
		saved.setId(12L);
	}
	// 假設帳號存在, 因丟出例外 & 不能被儲存
	@Test
	void register_emailExists_shouldThrow_andNeverSave() {
		when(repo.existsByEmail("brad@brad.tw")).thenReturn(true);
		
		assertThrows(IllegalArgumentException.class,
				() -> service.register("brad@brad.tw", "12345678", "Brad"));
		
		// 帳號已經存在, 不該存檔
		verify(repo).existsByEmail("brad@brad.tw");
		verify(repo, never()).save(any(Member.class));
	}
	
	// 註冊成功情境: 1.這組 email 不存在 2.就會存入這組會員
	@Test
	void register_success_shouldHashPasswd_andReturnId() {
		when(repo.existsByEmail("brad@brad.tw")).thenReturn(false);
		when(repo.save(any(Member.class))).thenReturn(saved);
		// ------------ 前提 ----------------
		ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
		// ----------------------------------
		Member m = service.register("brad@brad.tw", "12345678", "Brad");
		assertEquals(12L, m.getId());
		// ----------------------------------
		
		verify(repo).existsByEmail("brad@brad.tw");
		verify(repo).save(captor.capture());
		
	 	Member arg = captor.getValue();
	 	assertEquals("brad@brad.tw", arg.getEmail());
	 	
	 	assertNotNull(arg.getPasswd());
	 	assertNotEquals("12345678", arg.getPasswd());
	 	assertTrue(BCrypt.checkpw("12345678", arg.getPasswd()));
		
		
	}
}
