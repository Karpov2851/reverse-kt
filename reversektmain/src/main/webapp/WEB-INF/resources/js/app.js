// For any third party dependencies, like jQuery, place them in the lib folder.

// Configure loading modules from the lib directory,
// except for 'app' ones, which are in a sibling
// directory.
requirejs.config({
    baseUrl: 'resources/js/vendor',
    paths: {
        "app": '../app',
        "jquery": 'jquery.min',
        "bootstrap": 'bootstrap.min',
        "popper":'popper',
        "datepicker":'bootstrap-datepicker',
        "datatables.net":'data-table.min',
        "datatables":'data-table-bootstrap.min',
        "moment":'moment-2.25.2',
        "toastr":"https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min"
    }
});
