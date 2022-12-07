package com.sparta.board.service;


import com.sparta.board.dto.MemoRequestDto;
import com.sparta.board.dto.MemoResponseDto;
import com.sparta.board.entity.Memo;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.MemoRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional //게시글 만들기
    public Memo createMemo(MemoRequestDto requestDto,HttpServletRequest request) {
        Memo memo = new Memo(requestDto);
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            memoRepository.save(memo);
            return memo;
        }
        return null;

    }

    @Transactional(readOnly = true) // 모든 게시글 찾기
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }




    @Transactional // 게시글의 id값으로 하나의 게시글 찾기
    public Optional<Memo> getOneMemos(Long id) {
        memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        return memoRepository.findById(id);
    }





//    @Transactional //원래 코드
//    public MemoResponseDto update(Long id, MemoRequestDto requestDto,HttpServletRequest request) {
//        Memo memo = memoRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
//        );
//
//        if (memo.getPassword().equals(requestDto.getPassword())){
//            memo.update(requestDto);
//            return new MemoResponseDto("수정하였습니다");
//        }else {
//
//            return new MemoResponseDto("비밀번호가 틀렸습니다");
//        }
//
//    }
    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, HttpServletRequest request) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );
//            memo = memoRepository.findById(id,user.getUsername()).orElseThrow(
//                    () -> new IllegalArgumentException("토큰 에러");
//            )

            if (memo.getPassword().equals(requestDto.getPassword())) {
                memo.update(requestDto);
                return new MemoResponseDto("수정하였습니다","200");
            } else {

                return new MemoResponseDto("비밀번호가 틀렸습니다","400");
            }
        }

       return null;

    }
//    @Transactional //원래코드
//    public MemoResponseDto deleteMemo(Long id, MemoRequestDto requestDto) {
//
//        memoRepository.findByIdAndPassword(id, requestDto.getPassword()).orElseThrow(
//                () -> new IllegalArgumentException("패스워드가 틀렸습니다")
//        );
//        memoRepository.deleteById(id);
//        return new MemoResponseDto("성공");
//
//    }
    @Transactional
    public MemoResponseDto deleteMemo(Long id, MemoRequestDto requestDto, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );



            memoRepository.deleteById(id);
            return new MemoResponseDto("삭제 성공","200");
        }
        return new MemoResponseDto("삭제 실패","400");

    }
}
