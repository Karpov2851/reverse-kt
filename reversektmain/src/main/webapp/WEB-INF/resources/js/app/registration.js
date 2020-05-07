define([
    'jquery',
    'toastr'
], function($,toastr) {

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
    };
    $("#conf-pwd").change(function(e){
        var pwd = $('#reg-form').find('input[id="pwd"]').val();
        var confPwd = $('#reg-form').find('input[name="conf-password"]').val();
        if(pwd && confPwd && pwd != confPwd){
            toastr.error('Password Mismatch');
            $('#reg-form').find('input[name="conf-password"]').val('');
        }
    });
});