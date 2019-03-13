package com.oz.dunpic.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;

import com.oz.dunpic.DAO.CategoryDAO;
import com.oz.dunpic.DAO.CommentDAO;
import com.oz.dunpic.DAO.PostDAO;
import com.oz.dunpic.Entity.PageMaker;
import com.oz.dunpic.Entity.Post;
import com.oz.dunpic.Entity.UploadFile;
import com.oz.dunpic.Entity.Category;
import com.oz.dunpic.Entity.Comment;

@Controller
@RequestMapping("/board")
public class PostController {
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private CommentDAO commentDAO;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/list")
	public String list(Model model, 
			@RequestParam(value="category", required = false, defaultValue = "0") int categoryId,
			@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
		
		Page<Post> postPage;
		
		if(categoryId > 1) {
			// 전체를 제외한 다른 카테고리일 경우 해당카테고리 글만 보여줄수 있게함.
			postPage = postDAO.findByCategoryId(categoryId, pageable);
		}else {
			// 1번 id를 가진 전체 카테고리의 경우도 포함.
			postPage = postDAO.findAll(pageable);
		}

		model.addAttribute("categoryList",categoryDAO.findAll());
		model.addAttribute("postPage", postPage);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPostPage(postPage);
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "board";
	}
	
	@RequestMapping("/list/recommend")
	public String Recommend(Model model, @RequestParam(value="category", required = false, defaultValue = "0") int categoryId,
			@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
		
		Page<Post> postPage;
		
		postPage = postDAO.Push(pageable);
		model.addAttribute("postPage", postPage);
		
		model.addAttribute("categoryList",categoryDAO.findAll());
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPostPage(postPage);
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "board";
	}
	
	
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write_get(@Valid Post post,  BindingResult bindingResult, Model model) {

		List<Category> categoryList = categoryDAO.findAll();
		
		categoryList.remove(0); // List의 첫번쨰 원소인 전체 카테고리는 글작성할때 선택할수 없게함.
		
		model.addAttribute("categoryMap", categoryList.stream().collect(Collectors.toMap(Category::getId,  Category::getName)));

		
	    return "write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write_post(@Valid Post post, BindingResult bindingResult, Model model) {
		
	    if (bindingResult.hasErrors()) {
	    	logger.info(bindingResult.toString());
	    }

	    post.setRegDate(new Date());
	    
	    // String encPassword = passwordEncoder.encode(post.getPassword());
	    
	    // post.setPassword(encPassword);
	    
	    return "redirect:/board/" + postDAO.save(post).getId();
	}
	
	
	@RequestMapping("/{id}")
	public String view(Model model, @PathVariable int id) {
		Optional<Post> post = postDAO.findById(id);
		post.get().setViewcount(post.get().getViewcount()+1);
		
		postDAO.save(post.get());
		
		List<Comment> comment = commentDAO.findByPost_id(id);

		Comment commentVO = new Comment();
		
		model.addAttribute("comment", comment);
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("post", post);
		return "post";
	}
	
	@RequestMapping(value="/confirm_pw", method = RequestMethod.GET)
	public void confirm_pw(HttpSession session, HttpServletResponse response, @RequestParam("id") int id, @RequestParam("pw") String pw) throws IOException {
		
		Optional<Post> post = postDAO.findById(id);
		
		// if(passwordEncoder.matches(pw, post.getPassword())) {
		// 	response.addHeader("REQUIRES_AUTH","true");
		// 	session.setAttribute("boardSession", post.getId());

		// }else {
		// 	response.addHeader("REQUIRES_AUTH","false");			
		// }	
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String update(Model model, @PathVariable int id) {
        Optional<Post> post = postDAO.findById(id);

        //String contents = post.get().getContent();
        //contents = contents.replaceAll("(\r\n|\r|\n|\n\r)", "");

        model.addAttribute("post", post);

        return "write";
    }
	

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String updating(@Valid Post post, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "write";
        }

        // String encPassword = passwordEncoder.encode(post.getPassword());
	    
	    // post.setPassword(encPassword);
	    
        
        return "redirect:/board/" + postDAO.save(post).getId();
    }

	
	 @RequestMapping("/{id}/delete")
	    public String delete(@PathVariable int id) {

	        Optional<Post> post = postDAO.findById(id);

	        postDAO.delete(post.get());

	        return "redirect:/board/list";
	    }
	
	 @RequestMapping("/{id}/push")
	 public @ResponseBody int push(@PathVariable int id) {
		 Optional<Post> post = postDAO.findById(id);
		 
		 post.get().setPush(post.get().getPush()+1);
		 
		 postDAO.save(post.get());
		 
		return post.get().getPush();
	 }
	
}
