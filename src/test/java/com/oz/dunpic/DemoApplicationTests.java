package com.oz.dunpic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.oz.dunpic.DAO.ContentsDAO;
import com.oz.dunpic.DAO.PostDAO;
import com.oz.dunpic.DAO.ResultDAO;
import com.oz.dunpic.Entity.Contents;
import com.oz.dunpic.Entity.Post;
import com.oz.dunpic.Entity.Result;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	ContentsDAO contentsDAO;

	@Test
	public void Top3_Contents_DB_TEST(){

		
		List<Contents> contents = contentsDAO.findTop3ByOrderByIdDesc();

		
	}

	@Test
	public void URLComposer() throws ClientProtocolException, IOException, ParseException {

	
		String url = "https://api.neople.co.kr/df/servers/"+ "cain"
							+ "/characters/"+ "fbe0fa8a9d47cbdca4be09e1524e7ea9" 
							+ "/equip/" + "equipment"
							+ "?apikey=" + "whdRL7UsbCRRZnkJ8QCflkgArX4qbjSz";

		HttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpGet httpGet = new HttpGet(url);
		
		HttpResponse response = httpClient.execute(httpGet);	
		
		String json = EntityUtils.toString(response.getEntity());

		EntityUtils.consume(response.getEntity());
		
		JSONParser jsonParser = new JSONParser();
		JSONObject info_Object = (JSONObject) jsonParser.parse(json);
		// 기본정보
		
		JSONArray equipment = (JSONArray)info_Object.get("equipment");

		for(int i=0; i<equipment.size(); i++){
			JSONObject item = (JSONObject)equipment.get(i);
			JSONObject enchant = (JSONObject) item.get("enchant");
			if(item.containsKey("enchant")){

			}
		}

	}


	@Autowired
	ResultDAO resultDAO;
	@Test
		public void EnchantCardFetchingTesting(){
			List<Result> subquery = resultDAO.findBySubquery("상의", "물리 공격력");
			List<Result> main = new ArrayList<>();

			for(int i=0; i<subquery.size(); i++){
				int id = subquery.get(i).getId();
				List<Result> fetch = resultDAO.findByIdInJoinTable(id);
				main.addAll(fetch);
			}


		}
		

}

