
      var count = 0;

      cube_ajax();
      
      document.getElementById('item_card').addEventListener('click', function() {
        
        count++;
        
        $('.result_table').show();
         
         var reinforce_status = new Number(document.getElementById('reinforce_status').getAttribute("value"));
         var gold_status = new Number(document.getElementById('gold_amount').getAttribute("value"));
         var cube_status = new Number(document.getElementById('cube_amount').getAttribute("value"));

         var random_init   = Math.random()*100;
         var random_number = random_init.toFixed(1);

         var probability = [100, 100, 100, 100, 80, 70, 60, 50, 40, 30, 25.9, 18, 12.7, 8.3, 5.6];
         var per_cost    = [354600, 354600, 354600, 354600, 709200, 780120, 851040, 921960, 992880, 1063800, 1063800, 1773000, 2836800, 4255200, 6028200];
         var cube_cost   = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 120, 140, 160, 180, 200];

         var result = false;

         document.getElementById('gold_amount').setAttribute("value", gold_status + per_cost[reinforce_status]);
         document.getElementById('cube_amount').setAttribute("value", cube_status + cube_cost[reinforce_status]);

         console.log(document.getElementById('gold_sum'));

         if(random_number <= probability[reinforce_status]){
          //강화 성공 (10이하)  
           result = true;
           write_record(result);
           document.getElementById('reinforce_status').setAttribute("value", reinforce_status+1);
           refresh_status();
           
         }else{
            if(reinforce_status == 10 ||  reinforce_status == 11){
              // 10강 혹은 11강시 실패 결과 반영
                write_record(result);
                document.getElementById('reinforce_status').setAttribute("value", reinforce_status-3);
                refresh_status();
           }else if(reinforce_status >= 12){
             // 12강 이상에서  실패 결과 반영
                write_record(result);
                document.getElementById('reinforce_status').setAttribute("value", 0);
                refresh_status();
           }else{
            //  0~9강 실패 결과 반영
                write_record(result);
                refresh_status();
           }

         }

      });

      function cube_ajax(){
         
        $.ajax({
            url: "/tip/reinforce/cube_price" ,
            async: false,
            dataType : "json",
            success:function(result){
              
              document.getElementById('cube_amount').innerHTML = "✨ 누적 무큐  " + Number(document.getElementById('cube_amount').getAttribute("value")) + " 개  ("
                                                                                  + result.averagePrice + "  골드) =" ;
              document.getElementById('cube_gold').innerHTML = (Number(document.getElementById('cube_amount').getAttribute("value"))*result.averagePrice).toLocaleString() + "골드";
              document.getElementById('cube_gold').setAttribute("value", (Number(document.getElementById('cube_amount').getAttribute("value"))*result.averagePrice));

            }
        });
      }


      function write_record(result){

        var judge = result;
        var table = $('.result_table')[0];

        var item_name = document.getElementById("cardname").innerHTML;
        var item_img  = document.getElementById('weapon_img').src;

        var reinforce_status = document.getElementById('reinforce_status').getAttribute("value");
          
        table.innerHTML += "<div><img class='item_img' src='" + item_img + "' style='animation-name: bounce; visibility: visible;'>"
                          +   "<span class='에픽'>" + item_name + "</span>"
                          +   "<p class='"+ judge +"'>"
                          +   "<span>+ " + reinforce_status + " ▶️ +" + (Number(reinforce_status)+1) + "</span>"
                          +   "</p></div>";
      }

      function refresh_status(){

        var gold_amount = Number(document.getElementById('gold_amount').getAttribute("value"));
        

        document.getElementById('reinforce_status').innerHTML = "🔨 현재 강화 수치 : +" + document.getElementById('reinforce_status').getAttribute("value") + "&#9;&#9;(" + count + "회)";
        document.getElementById('gold_amount').innerHTML = "💰 누적 강화비용  " + gold_amount.toLocaleString() + " 골드";
        
        cube_ajax();

        var gold_cube   = Number(document.getElementById('cube_gold').getAttribute("value"));

        document.getElementById('gold_sum').setAttribute("value", (gold_amount+gold_cube));
        document.getElementById('gold_sum').innerHTML = "💸 총 합계 골드 " + (gold_amount+gold_cube).toLocaleString() + " 골드";
        
        
        var table = $('.result_table');

        table[0].scrollTo(0, table[0].scrollHeight);
      }

      $('.card_list').hide();
      $('.result_table').hide();

      function reset(){

        count = 0;

        document.getElementsByClassName('result_table')[0].innerHTML = "";

        document.getElementById('reinforce_status').setAttribute("value", Number(0));
        document.getElementById('gold_amount').setAttribute("value", Number(0));
        document.getElementById('cube_amount').setAttribute("value", Number(0));
        document.getElementById('cube_gold').setAttribute("value", Number(0));
        refresh_status();
      }

      function save_record(){

        var try_count = document.getElementById('reinforce_status').getAttribute("value");;
        
        var gold = document.getElementById('gold_sum').getAttribute("value");

        $.ajax({
          url: "/tip/reinforce/save_record?try_count=" + encodeURIComponent(try_count) + "&gold=" + encodeURIComponent(gold) + "&name=" + "test name" ,
          // test name
          async: false,
          dataType : "json",
          success:function(result){
            // console.log(result);
          }
      });

      }

      $(function(){
        $('.custom-select').change(function(){
          var url_init = 'https://img-api.neople.co.kr/df/items/';

          $('.card_list').show();

          document.getElementById('weapon_img').src =  url_init + this.value;
          document.getElementById('cardname').innerHTML = $('.custom-select option:selected').text() ; 

          reset();
        })
      });


