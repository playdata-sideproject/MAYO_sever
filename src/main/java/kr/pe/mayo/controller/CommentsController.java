package kr.pe.mayo.controller;

import kr.pe.mayo.domain.dto.CommentsDTO;
import kr.pe.mayo.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/comment-post")
    public String registerComments(){
        return "/comment";
    }

    @PostMapping("/insertComment")
    public String insertComments(CommentsDTO commentsDTO){
        System.out.println(commentsDTO);
        commentsService.insertComment(commentsDTO);
        return "redirect:/mypage";
    }
}
