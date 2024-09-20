package com.uplus.miniproject2.service;

import com.uplus.miniproject2.dto.HobbyBoardDto;
import com.uplus.miniproject2.dto.HobbyBoardRequest;
import com.uplus.miniproject2.dto.HobbyBoardUpdateRequest;
import com.uplus.miniproject2.entity.hobby.HobbyBoard;
import com.uplus.miniproject2.entity.user.User;
import com.uplus.miniproject2.repository.CustomUserRepository;
import com.uplus.miniproject2.repository.HobbyBoardRepository;
import com.uplus.miniproject2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HobbyBoardService {

    private final HobbyBoardRepository hobbyBoardRepository;
    private final UserRepository userRepository;

    public Page<HobbyBoardDto> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HobbyBoard> boardPage = hobbyBoardRepository.findAllWithUserAndProfile(pageable);

        List<HobbyBoardDto> boardDtos = boardPage.getContent().stream()
                .map(hobbyBoard -> new HobbyBoardDto(
                        hobbyBoard.getId(),
                        hobbyBoard.getUser().getName(),
                        hobbyBoard.getUser().getId(),
                        hobbyBoard.getTitle(),
                        hobbyBoard.getDescription(),
                        hobbyBoard.getVideoLink(),
                        hobbyBoard.getHobbyCategory(),
                        hobbyBoard.getThumbsUp()

                ))
                .collect(Collectors.toList());

        return new PageImpl<>(boardDtos, pageable, boardPage.getTotalElements());
    }

    public HobbyBoardDto getPostById(Long id) {
        HobbyBoard hobbyBoard = hobbyBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        HobbyBoardDto boardDto = new HobbyBoardDto(
                hobbyBoard.getId(),
                hobbyBoard.getUser().getName(),
                hobbyBoard.getUser().getId(),
                hobbyBoard.getTitle(),
                hobbyBoard.getDescription(),
                hobbyBoard.getVideoLink(),
                hobbyBoard.getHobbyCategory(),
                hobbyBoard.getThumbsUp()
        );
        return boardDto;
    }

    public boolean savePost(Long userId, HobbyBoardRequest hobbyBoardRequest) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException());
            HobbyBoard hobbyBoard = new HobbyBoard(
                    user,
                    hobbyBoardRequest.getTitle(),
                    hobbyBoardRequest.getDescription(),
                    hobbyBoardRequest.getVideoLink(),
                    hobbyBoardRequest.getHobbyCategory(),
                    0
            );
            hobbyBoardRepository.save(hobbyBoard);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePost(Long id) {
        try {
            hobbyBoardRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int addThumbsUp(Long postId) {
        HobbyBoard hobbyBoard = hobbyBoardRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        hobbyBoard.updateThumbsUp(hobbyBoard.getThumbsUp() + 1);
        hobbyBoardRepository.save(hobbyBoard);
        return hobbyBoard.getThumbsUp();
    }

    public boolean updatePost(Long postId, HobbyBoardUpdateRequest hobbyBoardRequest) {
        try {
            HobbyBoard hobbyBoard = hobbyBoardRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
            hobbyBoard.updateBoard(
                    hobbyBoardRequest.getTitle(),
                    hobbyBoardRequest.getDescription(),
                    hobbyBoardRequest.getHobbyCategory(),
                    hobbyBoardRequest.getVideoLink()
                    );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
