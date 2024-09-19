package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.dto.HobbyBoardDto;
import com.uplus.miniproject2.dto.HobbyBoardRequest;
import com.uplus.miniproject2.dto.HobbyBoardUpdateRequest;
import com.uplus.miniproject2.entity.hobby.HobbyBoard;
import com.uplus.miniproject2.service.HobbyBoardService;
import com.uplus.miniproject2.util.ApiUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BoardController {

    private final HobbyBoardService hobbyBoardService;

    public BoardController(HobbyBoardService hobbyBoardService) {
        this.hobbyBoardService = hobbyBoardService;
    }

    // 게시물 목록 조회 (페이징)
    @GetMapping
    public ApiUtil.ApiSuccess<?> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HobbyBoardDto> posts = hobbyBoardService.getPosts(page, size);
        return ApiUtil.success(posts);
    }

    // 게시물 상세 조회
    @GetMapping("/{postId}")
    public ApiUtil.ApiSuccess<?> getPostById(@PathVariable Long postId) {
        HobbyBoardDto post = hobbyBoardService.getPostById(postId);
        return ApiUtil.success(post);
    }

    // 게시물 저장
    @PostMapping
//    public ApiUtil.ApiSuccess<?> savePost(@RequestBody HobbyBoardRequest hobbyBoardRequest, @RequestParam Long userId) {
    public ApiUtil.ApiSuccess<?> savePost(@RequestBody HobbyBoardRequest hobbyBoardRequest) {
//        return ApiUtil.success(hobbyBoardService.savePost(userId, hobbyBoardRequest));
        return ApiUtil.success(hobbyBoardService.savePost(10L, hobbyBoardRequest));
    }

    // 게시물 수정
    @PostMapping("/{postId}")
    public ApiUtil.ApiSuccess<?> updatePost(@RequestBody HobbyBoardUpdateRequest hobbyBoardRequest, @PathVariable Long postId) {
        return ApiUtil.success(hobbyBoardService.updatePost(postId, hobbyBoardRequest));
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ApiUtil.ApiSuccess<?> deletePost(@PathVariable Long postId) {
        return ApiUtil.success(hobbyBoardService.deletePost(postId));
    }

    // 따봉 추가 API
    @GetMapping("/{postId}/thumbsUp")
    public ApiUtil.ApiSuccess<?> addThumbsUp(@PathVariable Long postId) {
        return ApiUtil.success(hobbyBoardService.addThumbsUp(postId));
    }
}
