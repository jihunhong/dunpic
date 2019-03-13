function part_click(clicked_id)
{
    var part = clicked_id;

    $.ajax({
		url: "/tip/enchant/equip?part=" + encodeURIComponent(part),
		type: 'GET',
		cache : false,
		dataType : "json",
		success:function(result, textStatus, xhr, response){
			
			if(document.querySelector("#group").classList != ""){
				document.querySelector("#group").classList.remove(document.querySelector("#group").classList);
				document.querySelector('.equip_list').querySelector('.active_part').classList.remove('active_part');
			}
			
			document.getElementById(part).classList.add("active_part");
			document.querySelector("#group").classList.add(part);
			
			document.querySelector("#group").innerHTML  = "";
			document.querySelector("#option").innerHTML = "";
			document.querySelector("#cards").innerHTML	= "";
			
			for(i=0; i<result.length; i++){
				if(result[i] != null){
					document.querySelector("#group").innerHTML += 
					"<li id='"+result[i]+"' onClick='group_click(this.id)'  class='wow bounce' style='animation-name: bounce;'><a>"+result[i]+"</a></li>";
				}
				
			}
			
		}
	});

}


function group_click(clicked_id)
{
	var groupname 	= clicked_id;										// 옵션
	var part	= document.querySelector("#group").classList;      		// 부위 
	
		$.ajax({
			url: "/tip/enchant/effect?part=" + encodeURIComponent(part) + "&groupname=" + encodeURIComponent(groupname), 
			type: 'GET',
			dataType : "json",
			success:function(result, textStatus, xhr, response){
				
				if(document.getElementsByClassName("active").length != 0)
					{
						document.querySelector(".active").classList.remove("active");
					}
				
				document.getElementById(groupname).classList.add("active");
				
				document.querySelector("#option").innerHTML = ""
				document.querySelector("#cards").innerHTML="";
				
				for(i=0; i<result.length; i++){
						document.querySelector("#option").innerHTML += 
						"<li id='"+result[i]+"' onClick='option_click(this.id)'  class='wow bounce' style='animation-name: bounce;'><a>"+result[i]+"</a></li>";
				}
				
			}
		});

}

function option_click(clicked_id){
	
	$('#my-spinner').show();

	var effect	 	= clicked_id;														// 카드 효과
	var part		= document.querySelector("#group").classList;      					// 부위 
	var effectgroup	= document.querySelector("#group").querySelector(".active").id;		// 효과 그룹
	
		$.ajax({
			url: "/tip/enchant/card?part=" + encodeURIComponent(part) + "&effect=" + encodeURIComponent(effect), 
			type: 'GET',
			dataType : "json",
			success:function(result, textStatus, xhr, response){
				
				$('#my-spinner').hide();

				if(document.getElementsByClassName("active").length == 2 )
				{
					document.getElementsByClassName("active")[1].classList.remove("active");
				}
				
				document.getElementById(effect).classList.add("active");
				
				document.querySelector("#cards").innerHTML="";
				
				for (i = 0; i < result.length; i++) {
				
					if(document.getElementById(result[i].cardname) == null)
					{
						document.querySelector("#cards").innerHTML += 
							
							"<li id='"+ result[i].cardname + "'' class='wow bounce' style='animation-name: bounce;'>" 
												+ "<div class='card_header' style='    text-align: left;'>"
												+ "<span class='card_thumb'>" + "<img src='https://img-api.neople.co.kr/df/items/"+result[i].item_id+"'>" + "</span>"
												+ "<span class='cardname'>" + result[i].cardname + "</span>"
												+ "</div>"
												+ "<span class='card_effect'>"
												+ "<p class='cardeffect "+result[i].effect+"'>" + result[i].effect +"   +"+ result[i].value +"</p>"
												+ "<span class='item_price'></span>"
												+ "<span class='average_price'></span>"
												+ "</span>"
												
							+"</li>";
					}else if (document.getElementById(result[i].cardname) != null){
						document.getElementById(result[i].cardname).getElementsByClassName('card_effect')[0].innerHTML += "<p class='cardeffect "+result[i].effect+"'>" + result[i].effect +"   +"+ result[i].value +"</p>";
					}
					
					if(result[i].upgrade > 0){
						document.getElementById(result[i].cardname).getElementsByClassName(result[i].effect)[0].innerHTML += "<a href='#' data-toggle='tooltip' title='(최대: +"+ result[i].max + ")&nbsp;&nbsp; 0/" + result[i].upgrade + "'>  ? </a>";
																																// <a href="#" data-toggle="tooltip" title="Default tooltip">you probably</a>
					}
					
					$('[data-toggle="tooltip"]').tooltip({
				        placement : 'top'
				    });
				}
				
				var option = document.getElementsByClassName(effect);
				for (var i = 0; i < option.length; i++) {
					  option[i].style.fontWeight = "bold"; 
					}
				window.Load_price();
			}
			});
	
		
    }
    

    function Load_price(){ 

        for(i=0; i<document.querySelector('#cards').children.length; i++){
    
                $.ajax({
                    url: "/tip/enchant/price_search?itemid=" + document.querySelector('#cards').children[i].getElementsByTagName('img')[0].src.slice(38,70), 
                    async: false,
                    dataType : "json",
                    success:function(result){
                        
                        if(document.querySelector('#cards').children[i].getElementsByTagName('img')[0].src.slice(38,70) == result.itemId){
                            document.querySelector('#cards').children[i].children[0].style.backgroundColor="#bdf580";
                        }
                        document.querySelector('#cards').children[i].querySelector('.item_price').innerHTML    = '📉최저가  ' + result.unitPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                        document.querySelector('#cards').children[i].querySelector('.average_price').innerHTML = '📊 평균가  ' + result.averagePrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    
                    }
                });
            
            if(document.getElementsByClassName('card_thumb')[i].children[0].src.indexOf('?') != -1)
                {
                document.getElementsByClassName('card_thumb')[i].children[0].src = "/img/dummy_card.png"
                }
            
            
        }
    
    }