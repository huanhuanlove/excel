function doTemplateExcel() {
    window.open("/user/importTemplate");
}

//导出用户列表
function doExportExcel(){
    window.open("/user/exportExcel");
}
//导入
function doImportExcel(){
    document.forms[0].action = "/user/importExcel";
    document.forms[0].submit();
}

function getFileName() {
    var fileName = $('.myfile').val();
    $('.myfile2').val(fileName);
}
