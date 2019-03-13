package com.oz.dunpic.Controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.oz.dunpic.DAO.ContentsDAO;
import com.oz.dunpic.DAO.TestDAO;
import com.oz.dunpic.Entity.PageMaker;
import com.oz.dunpic.Entity.Post;
import com.oz.dunpic.Entity.Test;
import com.oz.dunpic.Entity.UploadFile;

import com.oz.dunpic.Entity.Category;
import com.oz.dunpic.Entity.Contents;

@Controller
@RequestMapping("/contents")
public class ContentsController {
	
	@Autowired
	private ContentsDAO ContentsDAO;
	
	@Autowired
	private TestDAO testDAO;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@RequestMapping()
	public String mansory(Model model,
			@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
		
		List<Contents> main_contents = ContentsDAO.findTop4ByOrderByIdDesc();

		model.addAttribute("main_contents", main_contents);
		
		Page<Contents> contentsPage;
		
		contentsPage = ContentsDAO.findAll(pageable);

		model.addAttribute("contentsPage", contentsPage);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setContentsPage(contentsPage);
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "contents";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write_get(@Valid Contents contents,  BindingResult bindingResult, Model model) {
		

		
	    return "contents_write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write_post(@Valid Contents contents, BindingResult bindingResult, Model model) {
		
	    if (bindingResult.hasErrors()) {
	    	logger.info(bindingResult.toString());
	    }

	    contents.setRegDate(new Date());
	    
	    return "redirect:/contents/" + ContentsDAO.save(contents).getId();
	}
	
	
	@RequestMapping("/{id}")
	public String view(Model model, @PathVariable int id) {
		Optional<Contents> contents = ContentsDAO.findById(id);
		
		model.addAttribute("contents", contents);
		return "contents";
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String update(Model model, @PathVariable int id) {
		Optional<Contents> contents = ContentsDAO.findById(id);

        //String contents = post.get().getContent();
        //contents = contents.replaceAll("(\r\n|\r|\n|\n\r)", "");

        model.addAttribute("contents", contents);

        return "contents_write";
    }
	

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String updating(@Valid Contents contents, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "contents_write";
        }
        
        return "redirect:/contents/" + ContentsDAO.save(contents).getId();
    }

	
	 @RequestMapping("/{id}/delete")
	    public String delete(@PathVariable int id) {

	        ContentsDAO.deleteById(id);

	        return "redirect:/contents/list";
	    }
	 
	 @RequestMapping(value="/confirm_pw", method = RequestMethod.GET)
		public void confirm_pw(HttpSession session, HttpServletResponse response, @RequestParam("pw") String pw) throws IOException {
		 
			if(pw.equals("dunpic1256")) {
				response.addHeader("REQUIRES_AUTH","true");
				session.setAttribute("adminSession", "admin");
				
			}else {
				response.addHeader("REQUIRES_AUTH","false");
				
			}	
		}

	 @RequestMapping(value="/price_search", method = RequestMethod.GET)
		public @ResponseBody Object price_search(@RequestParam("itemName") String itemName) throws ClientProtocolException, IOException, ParseException{
			HttpClient httpClient = HttpClientBuilder.create().build();
			
			itemName = URLEncoder.encode(itemName, "UTF-8");
			String url = "https://api.neople.co.kr/df/auction?itemName="+ itemName
					+ "&q=minLevel%3A%3CminLevel%3E%2CmaxLevel%3A%3CmaxLevel%3E%2Crarity%3A%3Crarity%3E%2CminReinforce%3A%3CminReinforce%3E%2CmaxReinforce%3A%3CmaxReinforce%3E%2CminRefine%3A%3CminRefine%3E%2CmaxRefine%3A%3CmaxRefine%3E&sort=unitPrice:asc,reinforce:%3Creinforce%3E,auctionNo:%3CauctionNo%3E&limit=1&wordType=%3CwordType%3E&apikey=whdRL7UsbCRRZnkJ8QCflkgArX4qbjSz";		

			HttpGet httpGet = new HttpGet(url);
			
			HttpResponse response = httpClient.execute(httpGet);
			
			String json = EntityUtils.toString(response.getEntity());

			JSONParser jsonParser = new JSONParser();
	    	JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
	    	JSONArray rowArray = (JSONArray) jsonObject.get("rows");

	    	if(rowArray.size() > 0 ) {

	        	return rowArray.get(0);
	    	}
	    	else return null;

		}
	 
	 
}
