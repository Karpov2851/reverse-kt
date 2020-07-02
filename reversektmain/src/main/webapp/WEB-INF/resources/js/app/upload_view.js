define([
    'jquery',
    'toastr',
    'bootstrap',
    'app/util'
], function($,toastr,bootstrap,util) {

    util.enableDisableDropDownMultiple(".deps",true);
    $('[data-toggle="tooltip"]').tooltip();

    $("#project-item").change(function(){
        var callbackData  = new Object();
        var selectValue = $(this).val();
        if(selectValue){
            callbackData.selectOptionId = 'skill-set';
            util.restCall('GET','',
                '/reverse-kt-main/ws/fetch-skills?projectItemSeq='+parseInt(selectValue),callbackDropDown,callbackData
            );
        }
    });


    function callbackDropDown(callbackData) {
        var selectOption = '#'+callbackData.selectOptionId;
        $(selectOption).empty().append('<option selected="selected" value="">--Select--</option>');
        if (callbackData) {
            if(callbackData.enableDisableDropDropDownMultiple){
                util.enableDisableDropDownMultiple(".deps",true);
            }
            util.enableDisableDropDownSingle(selectOption,false);
            for (var key in callbackData.responseData) {
                $(selectOption).append('<option value=' + callbackData.responseData[key] + '>' + key + '</option>');
            }
        }
    }
});