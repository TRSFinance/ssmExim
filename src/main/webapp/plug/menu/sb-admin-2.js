$(function() {

    $('#side-menu').metisMenu();

});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse')
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse')
        }

        height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    })
})
$(function() {
	$(".navbar-default .sidebar a").each(function() {
		this.target = "frmMain";
	});
	
		$("#m1 a,#m2 a,#m3 a,#m4 a,#m6 a,#m7 a").each(function() {
			if(this.href.indexOf("?")==-1){
				this.href = this.href+"?mpId="+mpId;
			}else{
				this.href = this.href+"&mpId="+mpId;
			}
	});
	$(".nav a").click(function() {
		$(".nav .active").removeClass("active");
		$(this).addClass("active");
/*		this.href = this.href.replace(/mpId=(\d*)/,'mpId='+mpId);*/
		this.href = this.href.replace(/mpId=([+|-]?\d*)/,'mpId='+mpId);
	});
	
});
