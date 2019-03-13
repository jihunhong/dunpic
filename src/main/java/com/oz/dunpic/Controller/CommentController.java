package com.oz.dunpic.Controller;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oz.dunpic.DAO.CommentDAO;
import com.oz.dunpic.DAO.PostDAO;
import com.oz.dunpic.Entity.Category;
import com.oz.dunpic.Entity.Comment;
import com.oz.dunpic.Entity.Post;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentDAO commentDAO;

	@Autowired
	private PostDAO postDAO;

	// private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "{id}/write", method = RequestMethod.POST)
	public String write_post(@Valid Comment comment, BindingResult bindingResult, Model model, @PathVariable int id) {
		if (bindingResult.hasErrors()) {
			logger.info(bindingResult.toString());
		}

		comment.setPost_id(id);
		comment.setRegDate(new Date());

		// String encPassword = passwordEncoder.encode(comment.getPassword());

		// comment.setPassword(encPassword);

		commentDAO.save(comment);

		Optional<Post> post = postDAO.findById(id);
	    
	    post.get().setComment_count(post.get().getComment_count()+1);
	    
	    postDAO.save(post.get());
	    
	    return "redirect:/board/" + id;
	    
	}

}
