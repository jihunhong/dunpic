package com.oz.dunpic.Controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeLineController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    String apikey = "";

    @RequestMapping("/timeline")
    public String Timeline(HttpServletRequest request, Model model)
            throws ClientProtocolException, IOException, ParseException {
                
        String url = "";

        if(request.getParameter("start") == null || request.getParameter("end") == null){
            url = "https://api.neople.co.kr/df/servers/"+ request.getParameter("server")
                        +"/characters/" + request.getParameter("characterId")
                        +"/timeline?limit=50"
                        + "&code="  
                        + "&startDate="
                        + "&endDate="
                        + "&apikey="+ apikey;

        }else{
            
            url = "https://api.neople.co.kr/df/servers/"+ request.getParameter("server")
                        +"/characters/" + request.getParameter("characterId")
                        +"/timeline?limit=50"
                        + "&code="
                        + "&startDate=" + request.getParameter("start").replace(" ", "%20")
                        + "&endDate="   + request.getParameter("end").replace(" ", "%20")
                        + "&apikey="+ apikey;

        }
        
        
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(url);
        
        HttpResponse response = httpClient.execute(httpGet);	
        
        String json = EntityUtils.toString(response.getEntity());

        EntityUtils.consume(response.getEntity());
        
        JSONParser jsonParser = new JSONParser();
        JSONObject info_Object = (JSONObject) jsonParser.parse(json);

        JSONObject timeline = (JSONObject) info_Object.get("timeline");

        JSONArray timelinerow = (JSONArray) timeline.get("rows");
        
        model.addAttribute("server", request.getParameter("server"));
        model.addAttribute("info", info_Object);
        model.addAttribute("timeline", timelinerow);

        return "timeline";
    }

    @RequestMapping("/timelineload")
    public @ResponseBody Object TimeCode(HttpServletRequest request, Model model) throws ClientProtocolException, IOException, ParseException {
        String url = "";
        
        

        if(request.getParameter("start") == null || request.getParameter("end") == null){

            url = "https://api.neople.co.kr/df/servers/"+ request.getParameter("server")
                        +"/characters/" + request.getParameter("characterId")
                        +"/timeline?limit=100"
                        + "&code="      + request.getParameter("code")
                        + "&startDate="
                        + "&endDate="
                        + "&apikey="+ apikey;
        
        }else{

            url = "https://api.neople.co.kr/df/servers/"+ request.getParameter("server")
                        +"/characters/" + request.getParameter("characterId")
                        +"/timeline?limit=100"
                        + "&code="      + request.getParameter("code")
                        + "&startDate=" + request.getParameter("start").replace(" ", "%20")
                        + "&endDate="   + request.getParameter("end").replace(" ", "%20")
                        + "&apikey="+ apikey;

        }

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(url);
        
        HttpResponse response = httpClient.execute(httpGet);	
        
        String json = EntityUtils.toString(response.getEntity());

        EntityUtils.consume(response.getEntity());
        
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

        JSONObject timeline = (JSONObject) jsonObject.get("timeline");

        JSONArray timelinerow = (JSONArray) timeline.get("rows");
        
        return timelinerow;
    }
    

}