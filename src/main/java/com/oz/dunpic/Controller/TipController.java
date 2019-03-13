package com.oz.dunpic.Controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oz.dunpic.DAO.CardDAO;
import com.oz.dunpic.DAO.ResultDAO;
import com.oz.dunpic.Entity.Card;
import com.oz.dunpic.Entity.Option;
import com.oz.dunpic.Entity.Result;




@Controller
@RequestMapping("/tip")
public class TipController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	EntityManager em;
	
	@Autowired
	private CardDAO cardDAO;
	
	@Autowired
	private ResultDAO resultDAO;
	
	
	@RequestMapping(value="")
	public String tip_index(){
		
		return "redirect:/tip/enchant";
	}

	@RequestMapping("/enchant")
	public String enchant() {
		return "enchant";
	}
	
	@RequestMapping(value="/enchant/equip", method = RequestMethod.GET)
	public @ResponseBody List<?> group_search(@RequestParam("part") String part){
		
		List<Card> card = cardDAO.findByPartForGroup(part);
	
		return card;
	}
	
	@RequestMapping(value="/enchant/effect", method = RequestMethod.GET)
	public @ResponseBody List<Card> effect_search(@RequestParam("part") String part, @RequestParam("groupname") String groupname ){
		
		List<Card> card = cardDAO.findByGroupForEffect(part,groupname);
		
		return card;
	}
	
	@RequestMapping(value="/enchant/card", method = RequestMethod.GET)
	public @ResponseBody List<Result> card_search(@RequestParam("part") String part, @RequestParam("effect") String effect){
		// List<Result> result = resultDAO.findBySearch(part, effect, effectgroup);
		
		List<Result> subquery = resultDAO.findBySubquery(part, effect);
		List<Result> main = new ArrayList<>();

			for(int i=0; i<subquery.size(); i++){
				int id = subquery.get(i).getId();
				List<Result> fetch = resultDAO.findByIdInJoinTable(id);
				main.addAll(fetch);
			}
		
		
		
		return main;
	}
	
	@RequestMapping(value="/enchant/price_search", method = RequestMethod.GET)
	public @ResponseBody Object price_search(@RequestParam("itemid") String itemid) throws ClientProtocolException, IOException, ParseException{
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		itemid = URLEncoder.encode(itemid, "UTF-8");
		String url = "https://api.neople.co.kr/df/auction?itemId="+itemid
				+ "&q=minLevel%3A%3CminLevel%3E%2CmaxLevel%3A%3CmaxLevel%3E%2Crarity%3A%3Crarity%3E%2CminReinforce%3A%3CminReinforce%3E%2CmaxReinforce%3A%3CmaxReinforce%3E%2CminRefine%3A%3CminRefine%3E%2CmaxRefine%3A%3CmaxRefine%3E&sort=unitPrice:asc,reinforce:%3Creinforce%3E,auctionNo:%3CauctionNo%3E&limit=1&wordType=%3CwordType%3E&apikey=";		

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
	
	@RequestMapping(value="/enchant/id_commit", method = RequestMethod.GET)
	public @ResponseBody Object card_commit(@RequestParam("cardname") String cardname) throws ClientProtocolException, IOException, ParseException{
		
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		cardname = URLEncoder.encode(cardname, "UTF-8");
		String url = "https://api.neople.co.kr/df/items?itemName="+cardname
				+ "&q=minLevel%3A%3CminLevel%3E%2CmaxLevel%3A%3CmaxLevel%3E%2Crarity%3A%3Crarity%3E%2Ctrade%3A%3Ctrade%3E&limit=%3Climit%3E&wordType=match&apikey=";		

		HttpGet httpGet = new HttpGet(url);
		
		HttpResponse response = httpClient.execute(httpGet);
		
		String json = EntityUtils.toString(response.getEntity());

		JSONParser jsonParser = new JSONParser();
    	JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
    	JSONArray rowArray = (JSONArray) jsonObject.get("rows");
		
    	if(rowArray.get(0) != null) {

        	return rowArray.get(0);
    	}
    	else return null;
	}
	
	@RequestMapping(value="/enchant/id_commit2", method = RequestMethod.GET)
	public @ResponseBody void card_commit2(@RequestParam("cardname") String cardname,@RequestParam("itemId") String itemId) throws ClientProtocolException, IOException, ParseException{
		Card card = cardDAO.findByCardname(cardname);
		card.setItemId(itemId);
		cardDAO.save(card);
	}


	@RequestMapping("/reinforce")
	public String 강화() {
		return "reinforce";
	}



	
}
