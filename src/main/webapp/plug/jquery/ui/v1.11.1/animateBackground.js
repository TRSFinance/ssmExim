(function($) {
if(!document.defaultView || !document.defaultView.getComputedStyle){
    var oldCurCSS = jQuery.css;
    jQuery.css = function(elem, name, force){
        if(name === 'background-position'){
            name = 'backgroundPosition';
        }
        if(name !== 'backgroundPosition' || !elem.currentStyle || elem.currentStyle[ name ]){
            return oldCurCSS.apply(this, arguments);
        }
        var style = elem.style;
        if ( !force && style && style[ name ] ){
            return style[ name ];
        }
        return oldCurCSS(elem, 'backgroundPositionX', force) +' '+ oldCurCSS(elem, 'backgroundPositionY', force);
    };
}

var oldAnim = $.fn.animate;
$.fn.animate = function(prop){
    if('background-position' in prop){
        prop.backgroundPosition = prop['background-position'];
        delete prop['background-position'];
    }
    if('backgroundPosition' in prop){
        prop.backgroundPosition = '('+ prop.backgroundPosition + ')';
    }
    return oldAnim.apply(this, arguments);
};

function toArray(strg){
    strg = strg.replace(/left|top/g,'0px');
    strg = strg.replace(/right|bottom/g,'100%');
    strg = strg.replace(/([0-9\.]+)(\s|\)|$)/g,"$1px$2");
    var res = strg.match(/(-?[0-9\.]+)(px|\%|em|pt)\s(-?[0-9\.]+)(px|\%|em|pt)/);
    return [parseFloat(res[1],10),res[2],parseFloat(res[3],10),res[4]];
}

$.fx.step.backgroundPosition = function(fx) {
    if (!fx.bgPosReady) {
        var start = $.css(fx.elem,'backgroundPosition');

        if(!start){//FF2 no inline-style fallback
            start = '0px 0px';
        }

        start = toArray(start);

        fx.start = [start[0],start[2]];

        var end = toArray(fx.end);
        fx.end = [end[0],end[2]];

        fx.unit = [end[1],end[3]];
        fx.bgPosReady = true;
    }

    var nowPosX = [];
    nowPosX[0] = ((fx.end[0] - fx.start[0]) * fx.pos) + fx.start[0] + fx.unit[0];
    nowPosX[1] = ((fx.end[1] - fx.start[1]) * fx.pos) + fx.start[1] + fx.unit[1];
    fx.elem.style.backgroundPosition = nowPosX[0]+' '+nowPosX[1];
};
})(jQuery);
/**
 * 
 * @param d divId
 * @param n number to change
 */
function show_roll_num(d,n){ 
    var it = $("#"+d+" i"); 
    var len = String(n).length;
    //console.log(len); 
    for(var i=0;i<len;i++){ 
        if(it.length<=i){ 
            $("#"+d).append("<i></i>"); 
        } 
        var num=String(n).charAt(i); 
      //  console.log(num); 
        var y = -parseInt(num)*30; //y轴位置 
    //    console.log(y); 
        var obj = $("#"+d+" i").eq(i); 
        obj.animate({ //滚动动画 
            backgroundPosition :'(0 '+String(y)+'px)'  
            }, 'slow','swing',function(){} 
        ); 
    } 
    //$("#cur_num").val(n);
} 