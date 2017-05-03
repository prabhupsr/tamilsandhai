$(document).ready(function() {
    $("#register").click(function() {
        var data=new Object();
        data.userName = $("#userName").val();
        data.email = $("#email").val();
        data.password = $("#password").val();
        data.cpassword = $("#cpassword").val();
        data.phoneNumber = $("#phoneNumber").val();
        data.lang = $("#lang").val();
        if (data.userName == '' || data.email == '' || data.password == '' || data.cpassword == '' || data.phoneNumber == '' || data.lang == '') {
            alert("Please fill all fields...!!!!!!");
        } else if ((data.password.length) < 8) {
            alert("Password should atleast 8 character in length...!!!!!!");
        } else if (!(data.password).match(data.cpassword)) {
            alert("Your passwords don't match. Try again?");
        }
        /*else if(data.phoneNumber>7000000000 && data.phoneNumber<9999999999){
            alert("Please enter valid Phone Number");
        }*/

        else {

            jQuery.ajax({
                type: "POST",
                url: "/saveUser",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(data),
                async: false,
                success: function (data, status, jqXHR) {
                    $("#registrationResult").text(data.message);
                },
                error: function (jqXHR, status) {
                    alert("error");
                    // error handler
                }
            });
        };

    });
});
