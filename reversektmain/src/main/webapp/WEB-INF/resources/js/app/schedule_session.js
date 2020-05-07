define([
    'jquery',
    'datepicker',
    'moment',
    'datatables.net',
    'datatables'
], function($,datetimepicker,moment,datatable,datatableboot) {
    console.log('scheduled js loaded');
    $('#date-time').datetimepicker({format: 'YYYY-MM-DD hh:mm:ss'});
    $('#example').DataTable({
        fixedHeader: true
    } );
});