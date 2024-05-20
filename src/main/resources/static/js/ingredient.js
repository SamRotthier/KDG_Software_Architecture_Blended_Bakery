document.addEventListener("DOMContentLoaded", function (){
    function showSuccess(){
        const success = new bootstrap.Modal(document.getElementById("success"));
        success.show();
    }

    const successMessage = document.getElementById('successMessage');
    if(successMessage){
        showSuccess();
    }
});

