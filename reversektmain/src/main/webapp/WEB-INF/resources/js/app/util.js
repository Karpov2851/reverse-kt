define([
    'jquery'
], function($) {
    return {
        restCall: function(requestMethod,requestBody,url,callback,callbackData) {
            var responseData = '';

            $.ajax({
                url: url,
                data:requestBody,
                type:requestMethod,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function(result){
                    responseData = result;
                    callbackData.responseData = responseData;
                    callback(callbackData);
                },
                error: function(status,error){
                }
            });
        },
        fileUploadRestCall: function(requestMethod,requestBody,url,callback){
            $.ajax({
                url: url,
                data: requestBody,
                processData: false,
                contentType: false,
                type: requestMethod,
                success: function(data){
                    callback(data,true);
                },
                error: function(status,error){
                    callback(error,false);
                }
            });
        },

        enableDisableDropDownMultiple : function(classAttr,isDisabled){
            $(classAttr).each(function( index ) {
                $(this).prop('disabled', isDisabled);
            });
        },

        enableDisableDropDownSingle : function(idAttribute,isDisabled){
            $(idAttribute).prop('disabled', isDisabled);
        },
    }

});