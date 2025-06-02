window.alert = function(message, callback){
    Swal.fire({
      title: message,
      icon: "warning"
    }).then(()=> {
        if(typeof callback ==='function'){
            callback();
        }
    });
};