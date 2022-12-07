package com.sparta.board.controller;


import com.sparta.board.dto.MemoRequestDto;
import com.sparta.board.dto.MemoResponseDto;
import com.sparta.board.entity.Memo;
import com.sparta.board.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/") // "/" Url에 요청하는 ModelAndView
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.createMemo(requestDto,request);
    }

    @GetMapping("/api/memos")
    public List<Memo> getMemos( ){
        return memoService.getMemos();
    }

    @PutMapping("/api/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto,HttpServletRequest request){
        return memoService.update(id, requestDto,request);
    }

    @DeleteMapping("api/memos/{id}")
    public MemoResponseDto deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.deleteMemo(id,requestDto,request);
    }

    @GetMapping("/api/memos/{id}")
    public Optional<Memo> getOneMemos(@PathVariable Long id){
        return memoService.getOneMemos(id);
    }

}