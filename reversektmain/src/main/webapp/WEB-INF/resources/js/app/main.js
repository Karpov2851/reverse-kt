define([
	'jquery',
	'toastr'
	
], function($,toastr, factory) {
	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};

	function toasterOptions() {
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-center",
        "preventDuplicates": true,
        "onclick": null,
        "showDuration": "100",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "show",
        "hideMethod": "hide"
    }
};
    console.log('main js loaded');
    toasterOptions();
	fullHeight();
	$('#sidebarCollapse').on('click', function () {
    $('#sidebar').toggleClass('active');
    $('.close').css("display", "none");
	});
});

