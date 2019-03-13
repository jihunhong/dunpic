package com.oz.dunpic.Controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oz.dunpic.DAO.CategoryDAO;
import com.oz.dunpic.Entity.Category;


@Controller
public class CategoryController {
	
	@Autowired
	public CategoryDAO categoryDAO;
	
	
	@RequestMapping(value = "/category/add.do", method = RequestMethod.GET)
	public String getadd(Category category, Model model ) {

		List<Category> categoryList = categoryDAO.findAll();
		
		model.addAttribute("categoryMap", categoryList.stream().collect(Collectors.toMap(Category::getId,  Category::getName)));

		model.addAttribute("categoryList",categoryDAO.findAll());
		
		return "categoryadd";
		
	}
	
	@RequestMapping(value = "/category/add.do", method = RequestMethod.POST)
	public String postadd(HttpServletRequest request ) {

		Category category = new Category();
		category.setName(request.getParameter("name"));
		
		categoryDAO.save(category);
		
		return "redirect:" + "/";
		
	}

}
