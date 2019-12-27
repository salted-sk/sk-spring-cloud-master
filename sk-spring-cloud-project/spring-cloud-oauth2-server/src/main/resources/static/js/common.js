/**
 * 全局js异常处理。
 */
$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
    alert(JSON.parse(jqxhr.responseText)['error']);
});

// /**
//  * 全局CSRF设置。
//  */
// $(document).ajaxSend(function (e, xhr, options) {
//     var token = $("meta[name='_csrf']").attr("content");
//     var header = $("meta[name='_csrf_header']").attr("content");
//     xhr.setRequestHeader(header, token);
// });




