/**
 * @version jquery.dialog 1.0
 * 不要拿官方的替代，这个是丛立改过的
 */
(function($){
	$.fn.dialog=function(options){
		var defaults={titlebg:"rgb(45, 103, 154)"};
		var options = $.extend(defaults, options); 
		var thisDialog=$(this);
		var title=$(this).attr("title")
		if(!$(this).is(":visible")){
			$(this).show();
			$(this).addClass("dialog");
		}else{
			return false;
		}
		//options是后加的，为了传selection对象
		middle($(this),options);
		drag($(this));
		var toolbar=$("<div class=\"dialog-title\"><div style=\"float:left;font-weight:bold;margin:5px;\">"+title+"</div><div class=\"tool-close\"></div></div>");
		toolbar.css({"background-color":options.titlebg});
		$(this).prepend(toolbar);
		$(this).find('.tool-close').click(function(){
			$(this).closest(".dialog-title").remove();
			thisDialog.hide();
		});
		
		var footbar=$("<div class=\"dialog-bottom\"></div>");
		$(this).append(footbar);
		$(this).append(footbar);
		if(options.buttons){
			$.each(options.buttons, function(index,obj){ 
				debugger;
				var button=$("<input type='button' value="+obj.text+" class='dialog-btn'/>");
				button.click(function(){
					obj.handler();
				})
				footbar.append(button);
			});
		}
	};
	
	$.fn.dialog.close=function(obj){
		obj.hide();
		obj.find(".dialog-title").remove();
	}
	//位置  options是后加的，为了传过来selection这个参数
	function middle(obj,options){
		//居中
//		var winW=$(window).width();
//		var winH=$(window).height();
//		var sclL=$(window).scrollLeft();
//		var sclT=$(window).scrollTop();
//		var layerW=obj.width();
//		var layerH=obj.height();
//		var left=sclL+(winW-layerW)/2;
//		var top=sclT+(winH-layerH)/2;
//		obj.css({"left":left,"top":top});
		//算位置，在右侧和右侧不够的时候，放到左侧
		var winW=$(window).width();
		var layerW=obj.width();
		var layerH=obj.height();
//		var left=sclL+(winW-layerW)/2;
		var selection = options["selection"];
		var left=selection["x2"] + 5;
//		var top=sclT+(winH-layerH)/2;
		var top=selection["y2"] - selection["height"];
		if(left + layerW > winW){
			left = selection["x1"] - layerW - 5;
		}
		obj.css({"left":left,"top":top});
	}
	//可拖拽
	var offsetX=0;
	var offsetY=0;
	var flag=false;
	function drag(obj){
		obj.mousedown(function(evt){
		    flag=true;
		    offsetX=evt.pageX-parseInt(obj.css('left'),10);
		    offsetY=evt.pageY-parseInt(obj.css('top'),10); 
		    $(document).mousemove(function(evte){
			     if(!flag) return false;   
			     obj.css('top',evte.pageY-offsetY);
			     obj.css('left',evte.pageX-offsetX);     
			 });
		    $(document).mouseup(function(){    
			     flag=false; 
			     $(document).mousemove = null;
			 });
		});	
		
	}
})(jQuery);