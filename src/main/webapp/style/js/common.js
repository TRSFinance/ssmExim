$(document).ready(function () {
    //导入企业浮层弹出
    $('.btn-comlist').click(function (e) {
        e.preventDefault();
        $('#comModal').modal('show');
    });
    //新增企业用户浮层弹出
    $('.btn-addComuser').click(function (e) {
        e.preventDefault();
        $('#addComUserModal').modal('show');
    });
    //新增用户浮层弹出
    $('.btn-adduser').click(function (e) {
        e.preventDefault();
        $('#addUserModal').modal('show');
    }); 
     //提示浮层弹出
    $('.btn-warn').click(function (e) {
        e.preventDefault();
        $('#warnModal').modal('show');
    });
    //提示浮层弹出
    $('.btn-system').click(function (e) {
        e.preventDefault();
        $('#systemModal').modal('show');
    });    
    
    //保存浮层弹出
    $('.btn-save').click(function (e) {
        e.preventDefault();
        $('#saveModal').modal('show');
    });
    $('#saveModal').on('show.bs.modal', function () {
	   $('#addComUserModal,#systemModal,#addUserModal').css("z-index","100");
	})
    $('#saveModal').on('hide.bs.modal', function () {
	   $('#addComUserModal,#systemModal,#addUserModal').css("z-index","1050");
	})
    
    //下拉菜单
    $('.accordion > a').click(function (e) {
    	var index1 = $(this).parent().index();
        e.preventDefault();
        var $ul = $(this).siblings('ul');
        var $li = $(this).parent();
        if ($ul.is(':visible')){
        	$li.removeClass('active');
        	//下拉箭头
        	$(this).children(".pull-right").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
        	$.cookie("navstation" + index1, null, {path: "/"});
        	//console.log(navstation)
        }
        else{
        	$li.addClass('active');
        	$(this).children(".pull-right").addClass("glyphicon-chevron-up").removeClass("glyphicon-chevron-down");
        	$.cookie("navstation" + index1, $(this).text(), {path: "/"});
        }
        $ul.slideToggle();
        
    });
    

    $(".accordion > a").each(function(){
    	for(var i=0;i<$('.accordion').length;i++){
	        if($(this).text() == $.cookie("navstation" + i)){
	        	$(this).siblings('ul').show();
	        	$(this).parent().addClass('active');
	        	$(this).children(".pull-right").addClass("glyphicon-chevron-up").removeClass("glyphicon-chevron-down");
	        }	
       }

    });



    $('.accordion li.active:first').parents('ul').slideDown();
    
    //下拉菜单
    $('.nav > li > .ajax-link').click(function (e) {
    	var index1 = $(this).parent().index();
        e.preventDefault();
        var $dl = $(this).siblings('dl');
        var $li = $(this).parent();
        var $sibli = $(this).parent("li").siblings("li");
        //去除二级栏目选中效果
        $(".accordion .nav a").removeClass("active");
        
        if ($dl.is(':visible')){
        	$(this).children(".pull-right").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
        	$.cookie("subnavstation" + index1, null, {path: "/"});
        }
        else{
        	$(this).children(".pull-right").addClass("glyphicon-chevron-up").removeClass("glyphicon-chevron-down");
        	$(this).addClass("active");
        	$.cookie("subnavstation" + index1, $(this).text(), {path: "/"});
        }
        $dl.slideToggle();
    });
    
    $(".nav > li > .ajax-link").each(function(){
    	for(var i=0;i<$('.ajax-link').length;i++){
	        if($(this).text() == $.cookie("subnavstation" + i)){
	        	$(this).siblings('dl').show();
	        	$(this).children(".pull-right").addClass("glyphicon-chevron-up").removeClass("glyphicon-chevron-down");
        	    $(this).addClass("active");
	        }	
       }
    });

    //下拉菜单
    $('.nav > li > dl  a').click(function (e) {
        e.preventDefault();
        var $a = $(this).parents('li').children(".ajax-link");
        $a.addClass("active");
        $a.parent("li").siblings().children("a").removeClass("active");
    });

    //全选/反选
	$("table :checkbox:first").change(function(){
	    $(this).closest("table")
	           .find(":checkbox:not(:first)")
	           .prop("checked", this.checked);
	});

//    docReady();
    
    
        
});

function docReady() {

    //datatable
    $('.datatable').dataTable({
        "sDom": "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        "sPaginationType": "bootstrap",
        "bLengthChange": false,
        "oLanguage": {
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "没有您要搜索的内容",
            "sInfo": "",
            "sInfoEmpty": "",
            "sInfoFiltered": "",
            "sSearch": "搜索：",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            },
        }
    });	
}

//additional functions for data table
$.fn.dataTableExt.oApi.fnPagingInfo = function (oSettings) {
    return {
        "iStart": oSettings._iDisplayStart,
        "iEnd": oSettings.fnDisplayEnd(),
        "iLength": oSettings._iDisplayLength,
        "iTotal": oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage": Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
        "iTotalPages": Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
    };
}
$.extend($.fn.dataTableExt.oPagination, {
    "bootstrap": {
        "fnInit": function (oSettings, nPaging, fnDraw) {
            var oLang = oSettings.oLanguage.oPaginate;
            var fnClickHandler = function (e) {
                e.preventDefault();
                if (oSettings.oApi._fnPageChange(oSettings, e.data.action)) {
                    fnDraw(oSettings);
                }
            };

            $(nPaging).addClass('pagination').append(
                '<ul class="pagination">' +
                    '<li class="prev disabled"><a href="#">' + oLang.sPrevious + '</a></li>' +
                    '<li class="next disabled"><a href="#">' + oLang.sNext + '</a></li>' +
                    '</ul>'
            );
            var els = $('a', nPaging);
            $(els[0]).bind('click.DT', { action: "previous" }, fnClickHandler);
            $(els[1]).bind('click.DT', { action: "next" }, fnClickHandler);
        },

        "fnUpdate": function (oSettings, fnDraw) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, j, sClass, iStart, iEnd, iHalf = Math.floor(iListLength / 2);

            if (oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if (oPaging.iPage <= iHalf) {
                iStart = 1;
                iEnd = iListLength;
            } else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }

            for (i = 0, iLen = an.length; i < iLen; i++) {
                // remove the middle elements
                $('li:gt(0)', an[i]).filter(':not(:last)').remove();

                // add the new list items and their event handlers
                for (j = iStart; j <= iEnd; j++) {
                    sClass = (j == oPaging.iPage + 1) ? 'class="active"' : '';
                    $('<li ' + sClass + '><a href="#">' + j + '</a></li>')
                        .insertBefore($('li:last', an[i])[0])
                        .bind('click', function (e) {
                            e.preventDefault();
                            oSettings._iDisplayStart = (parseInt($('a', this).text(), 10) - 1) * oPaging.iLength;
                            fnDraw(oSettings);
                        });
                }

                // add / remove disabled classes from the static elements
                if (oPaging.iPage === 0) {
                    $('li:first', an[i]).addClass('disabled');
                } else {
                    $('li:first', an[i]).removeClass('disabled');
                }

                if (oPaging.iPage === oPaging.iTotalPages - 1 || oPaging.iTotalPages === 0) {
                    $('li:last', an[i]).addClass('disabled');
                } else {
                    $('li:last', an[i]).removeClass('disabled');
                }
            }
        }
    }
});