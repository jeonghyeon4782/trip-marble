package com.dj.trip.domain.member.service;

import com.dj.trip.domain.mail.MailVo;
import com.dj.trip.domain.mail.mapper.MailMapper;
import com.dj.trip.domain.mail.service.MailService;
import com.dj.trip.domain.mail.service.MailServiceImpl;
import com.dj.trip.domain.member.Member;
import com.dj.trip.domain.member.dto.AuthenticationEmailResponseDto;
import com.dj.trip.domain.member.dto.CreateMemberRequestDto;
import com.dj.trip.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final MailMapper mailMapper;

    // 회원가입
    @Override
    public void createMember(CreateMemberRequestDto dto) throws Exception {
        dto.setPassword(encoder.encode(dto.getPassword()));
        dto.setRole('U');
        memberMapper.insertMember(modelMapper.map(dto, Member.class));
    }

    @Override
    public boolean duplicateCheckMemberId(String memberId) throws Exception {
        Member member = memberMapper.selectMemberByMemberId(memberId);
        return member == null;
    }

    @Override
    public boolean duplicateCheckNickname(String nickname) throws Exception {
        Member member = memberMapper.selectMemberByNickname(nickname);
        return member == null;
    }

    @Override
    public AuthenticationEmailResponseDto authenticationEmail(String email) throws Exception {
        if (memberMapper.selectMemberByEmail(email) != null) {
            return null;
        }
        String key = mailService.generateRandomCode();
        mailService.sendMessage(email, "<두정> 이메일 인증 인증번호입니다.", "인증번호는 " + key + "입니다.");
        mailMapper.insertMail(new MailVo(email, key, null));
        return new AuthenticationEmailResponseDto(email, key);
    }
}
