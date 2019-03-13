$(function() {
            
    var start = moment().subtract(88, 'days');
    var end = moment();

    function cb(start, end) {
        $('#reportrange span').html(start.format('YYYY-MM-DD') + ' ~ ' + end.format('YYYY-MM-DD'));
    }

    $('#reportrange').daterangepicker({
        timePicker: true,
        startDate: start,
        endDate: end,
        ranges: {
           '오늘': [moment(), moment()],
           '지난 일주일': [moment().subtract(6, 'days'), moment()],
           '이번 달': [moment().startOf('month'), moment().endOf('month')],
        },
        locale: {
          format: 'YYYYMMDD'
        }
    }, cb);

    cb(start, end);

});
