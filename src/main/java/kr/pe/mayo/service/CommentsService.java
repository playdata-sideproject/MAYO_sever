package kr.pe.mayo.service;

import kr.pe.mayo.dao.CommentsRepository;
import kr.pe.mayo.dao.WorkRepository;
import kr.pe.mayo.domain.Comments;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.dto.CommentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private HttpSession session;

    public void insertComment(CommentsDTO commentsDTO){

        Comments comments = new Comments();

        User user = (User)session.getAttribute("user");
        Work work = workRepository.findById(1l).get();
        comments.setTitle(commentsDTO.getCommTitle());
        comments.setContent(commentsDTO.getCommContent());
        comments.setUserIdx(user.getIdx());
        comments.setWorkIdx(work);

        commentsRepository.save(comments);
    }

}
