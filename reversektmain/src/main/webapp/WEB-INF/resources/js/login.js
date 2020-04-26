function validateConfirmedPassword(e) {
    var arePwdSame = false;
    var pwd = $('#reg-form').find('input[id="pwd"]').val();
    var confPwd = $('#reg-form').find('input[name="conf-password"]').val();
    if(pwd && confPwd && pwd != confPwd){
        alert('Password Mismatch');
        $('#reg-form').find('input[name="conf-password"]').val('');
    }
}