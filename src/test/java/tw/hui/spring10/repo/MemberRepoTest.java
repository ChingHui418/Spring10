package tw.hui.spring10.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import tw.hui.spring10.entity.Member;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MemberRepoTest {
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private Member testMember;
	
	@BeforeEach
	void setUp() {
		testMember = new Member();
		
//		testMember.setId(123L);
		testMember.setEmail("brad777@brad.tw");
		testMember.setPasswd("12345678");
		testMember.setName("Brad");
		
		entityManager.persist(testMember);
		entityManager.flush();
	}
	
	@Test
	@DisplayName("Email 查資料 -- 成功")
	void testFindByEmail_Found() {
		Member member = memberRepo.findByEmail("brad777@brad.tw");
		
		assertThat(member).isNotNull();
		assertThat(member.getEmail()).isEqualTo("brad777@brad.tw");
		assertThat(member.getPasswd()).isEqualTo("12345678");
		assertThat(member.getName()).isEqualTo("Brad");
	}

	@Test
	@DisplayName("Email 查資料 -- 找不到 => null")
	void testFindByEmail_NotFound() {
		Member member = memberRepo.findByEmail("brad888@brad.tw");
		
		assertThat(member).isNull();
	}	

	@Test
	@DisplayName("Email 查資料是否存在 -- true")
	void testExistsByEmail_True() {
		boolean exists = memberRepo.existsByEmail("brad777@brad.tw");
		assertThat(exists).isTrue();
	}
	
	@Test
	@DisplayName("Email 查資料是否存在 -- false")
	void testExistsByEmail_False() {
		boolean exists = memberRepo.existsByEmail("brad888@brad.tw");
		assertThat(exists).isFalse();
	}
	
}
