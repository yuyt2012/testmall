package io.ecp.testmall.member;


import io.ecp.testmall.category.entity.Category;
import io.ecp.testmall.category.repository.CategoryRepository;
import io.ecp.testmall.member.entity.Address;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.Role;
import io.ecp.testmall.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitDatabase {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner startDatabase() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Address address = new Address("city", "street", "zipcode");
                Member admin = Member.builder()
                        .email("admin@test.com")
                        .name("admin")
                        .password(passwordEncoder.encode("admin")) // 비밀번호를 암호화하여 저장
                        .role(Role.ADMIN)
                        .address(address) // 주소 설정
                        .build();
                memberRepository.save(admin);
            }
        };
    }
}