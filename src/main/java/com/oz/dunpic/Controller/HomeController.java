package com.oz.dunpic.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oz.dunpic.DAO.ContentsDAO;
import com.oz.dunpic.DAO.SearchLogDAO;
import com.oz.dunpic.Entity.Contents;
import com.oz.dunpic.Entity.SearchLog;

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
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	String apikey = "whdRL7UsbCRRZnkJ8QCflkgArX4qbjSz";
	
	@Autowired
	ContentsDAO contentsDAO;

	@Autowired
	SearchLogDAO searchlogDAO;
	
	
	@RequestMapping("/")
	public String Home(Model model){
		List<Contents> contents = contentsDAO.findTop3ByOrderByIdDesc();
		
		model.addAttribute("contents", contents);
		
		return "index";
	}
	
	
	@RequestMapping("/searching")
	public String searching(HttpServletRequest request, Model model) throws ClientProtocolException, IOException, ParseException {
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		String server = request.getParameter("server");
		String userName = request.getParameter("userName");
		String option = request.getParameter("option");
		
		if(server.equals("all")) {

			
			JSONArray cain = (JSONArray) Search("cain", userName, option);
			JSONArray diregie = (JSONArray) Search("diregie", userName, option);
			JSONArray siroco = (JSONArray) Search("siroco", userName, option);
			JSONArray casillas = (JSONArray) Search("casillas", userName, option);
			JSONArray prey = (JSONArray) Search("prey", userName, option);
			JSONArray hilder = (JSONArray) Search("hilder", userName, option);
			JSONArray anton = (JSONArray) Search("anton", userName, option);
			JSONArray bakal = (JSONArray) Search("bakal", userName, option);
			
			model.addAttribute("cain", cain);

			model.addAttribute("diregie", diregie);
						
			model.addAttribute("siroco", siroco);
			
			model.addAttribute("casillas", casillas);
			
			model.addAttribute("prey", prey);
			
			model.addAttribute("hilder", hilder);
			
			model.addAttribute("anton", anton);
			
			model.addAttribute("bakal", bakal);
			
		}else {
			model.addAttribute("rowArray", Search(server, userName, option));
			model.addAttribute("server", server);
		}

		return "searching";
		
	}
	
	@RequestMapping("/info/character")
	public String character_info(HttpServletRequest request, Model model) throws ClientProtocolException, IOException, ParseException {

		String server = request.getParameter("server");
		String characterId = request.getParameter("characterId");
		
		
		URLComposer(Default_info(server,characterId), "info", model);
    	// 기본정보
    	
    	URLComposer(Equip(server,characterId,"equipment"), "equipment", model);
    	// 장착장비
    	
    	URLComposer(Equip(server,characterId,"avatar"), "avatar", model);
    	// 아바타
    	
    	URLComposer(Equip(server,characterId,"creature"), "creature", model);
    	// 크리쳐
    	
    	URLComposer(Equip(server,characterId,"flag"), "flag", model);
    	// 휘장
    	
    	URLComposer(Default_info(server,characterId+"/status"), "status", model);
    	// 스탯
    	
    	URLComposer(Buff(server,characterId,"equipment"), "buff_equip", model);
    	// 버프 장비
    	
    	URLComposer(Buff(server,characterId,"avatar"), "buff_avatar", model);
    	// 버프 아바타
    	
    	URLComposer(Buff(server,characterId,"creature"), "buff_creature", model);
    	// 버프 크리쳐
    	
		model.addAttribute("server", server);

		// SearchLog log = new SearchLog();
		
		// log.setServer(server);
		// log.setRegDate(new Date());
		// log.setCharacterId(characterId);

		// searchlogDAO.save(log);

    	return "info";

	}
	
	
	private String Firstsearch(String server, String name, String option){
	    String url = "https://api.neople.co.kr/df/servers/" + server
	    			+"/characters?characterName="+ name
	    			+"&limit=20"
	    			+"&wordType="+option+"&apikey="+apikey;
		return url;
	}
	
	private String Default_info(String server, String characterId){
		
		String url = "https://api.neople.co.kr/df/servers/"+ server
											+"/characters/"+ characterId
											
											+ "?apikey="+ apikey;

		return url;
		
	}
	
	private String Equip(String server, String characterId, String parameter) {
		
		String url = "https://api.neople.co.kr/df/servers/"+ server
										   + "/characters/"+ characterId 
				
										   + "/equip/" + parameter
										   + "?apikey=" + apikey;
		return url;
	}
	
	private String Buff(String server, String characterId, String parameter) {
		
		String url = "https://api.neople.co.kr/df/servers/" 	 + server
										   		+ "/characters/" + characterId
										   + "/skill/buff/equip/"+ parameter
										   
										   + "?apikey=" + apikey;
		return url;
	}
	
	private void URLComposer(String url, String modelName, Model model) throws ClientProtocolException, IOException, ParseException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpGet httpGet = new HttpGet(url);
		
		HttpResponse response = httpClient.execute(httpGet);	
		
		String json = EntityUtils.toString(response.getEntity());

		EntityUtils.consume(response.getEntity());
		
		JSONParser jsonParser = new JSONParser();
    	JSONObject info_Object = (JSONObject) jsonParser.parse(json);
    	
    	// 기본정보
    	
    	if(info_Object.get(modelName) != null)
    	{
    		model.addAttribute(modelName, info_Object.get(modelName));
    		//버프 제외한 장비들
    	}
    	else
    	{
    		if(modelName.contains("buff") == true)
    		{
    			model.addAttribute(modelName, info_Object.get("skill"));
    			//버프 정보
    		}
    		else
    		{
    			model.addAttribute(modelName, info_Object);
    			//기본정보만
    		}
    		
    	}
    	
	}
	

	public static String encodeURIComponent(String component) {    
	    String result = null;      

	    try {       
	        result = URLEncoder.encode(component, "UTF-8");}    
	    catch (UnsupportedEncodingException e) {       
	        result = component;     
	    }      

	    return result;
	}
	
	public Object Search(String server, String userName, String option) throws ClientProtocolException, IOException, ParseException {
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpGet httpGet = new HttpGet(Firstsearch(server,userName,option));
		
		HttpResponse response = httpClient.execute(httpGet);	
		
		String json = EntityUtils.toString(response.getEntity());

		JSONParser jsonParser = new JSONParser();
    	JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
    	JSONArray rowArray = (JSONArray) jsonObject.get("rows");
    	
    	if(rowArray.size() > 0 ) {
        	return rowArray;
    	}
    	else return null;
	}
	
	    
}
