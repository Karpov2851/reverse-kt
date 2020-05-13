define([
    'jquery',
    'toastr',
    'bootstrap',
    'app/util'

], function($,toastr,bootstrap,util) {

    var projectSelected = $( "#project option:selected" ).val();
    var projectItemSelected  = $( "#project_item option:selected" ).val();

    if(projectSelected && projectItemSelected){
        $("#projectSelected").val(projectSelected);
        $("#projectItemSelected").val(projectItemSelected);
    }

    util.enableDisableDropDownMultiple(".deps",true);

    $('[data-toggle="tooltip"]').tooltip();

    $("#profile-image").click(function(){
        $("#upload-image").click();
    });

    $(".project-related").change(function(){
        var id = $(this).attr('id');
        var value = $(this).val();
        if(id == 'project'){
            $("#projectSelected").val(value);
        }else if(id == 'project_item'){
            $("#projectItemSelected").val(value);
        }
    })

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
    console.log('view profile js loaded');
    toasterOptions();

    $("#upload-image").change(function(){
        var file = $( this )[0].files[0];
        var fd = new FormData();
        fd.append( 'imageFile', file );
        util.fileUploadRestCall('POST',fd,'/reverse-kt-main/ws/saveimage',imageFileUploadCallback);
   });

    $(".info-dropdown").change(function(){
        var selectValue = $(this).val();
        var dropdownId = $(this).attr("id");
        if(selectValue){
            var requestBody = new Object();
            var callbackData  = new Object();
            switch (dropdownId) {
                case 'business_unit':
                    requestBody.companyRelatedParams = {"bu":selectValue};
                    requestBody.companyFetchOps = 'PROJ';
                    callbackData.selectOptionId = 'project';
                    callbackData.enableDisableDropDropDownMultiple = true;
                    break;
                case 'project':
                    requestBody.companyRelatedParams = {"projectSeq":parseInt(selectValue)};
                    requestBody.companyFetchOps = 'PROJITEM';
                    callbackData.selectOptionId = 'project_item';
                    break;
            }

            util.restCall('POST',JSON.stringify(requestBody),
                '/reverse-kt-main/ws/ddown',callbackDropDown,callbackData
            );
        }
    });

    function imageFileUploadCallback(resp,isSuccess){
        if(isSuccess){
            toastr.success('Image uploaded successfully');
            $("#profile-image").attr('src',resp.imageFileSrc);
        }else{
            toastr.error('Something went wrong');
        }
    }

    function callbackDropDown(callbackData) {
        var selectOption = '#'+callbackData.selectOptionId;
        $(selectOption).empty().append('<option selected="selected" value="">--Select--</option>');
        if (callbackData) {
            if(callbackData.enableDisableDropDropDownMultiple){
                util.enableDisableDropDownMultiple(".deps",true);
            }
            util.enableDisableDropDownSingle(selectOption,false);
            for (var key in callbackData.responseData) {
                $(selectOption).append('<option value=' + key + '>' + callbackData.responseData[key] + '</option>');
            }
        }
    }



});
