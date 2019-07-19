<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <style type="text/css">
        body{
            background: url("/static/img/bg2.jpg");
        }

        .myfile{
            opacity: 0;
            border: red 1px solid;
            position: absolute;
        }

        .myfile2{
            width: 187px;
            position: relative;
            border: gray 1px solid;
            top: -34px;
            left: 58px;
        }

        .myfile2:hover{
            border: #374580 1px solid;
        }

        .myuser{
            height:300px;
            width: 900px;
            overflow:auto;
            margin: 0 auto;
        }
    </style>
</head>
<body class="container">
    <div class="col-md-9 text-center" style="margin: 100px 0 60px 0;">
        <button type="button" onclick="doTemplateExcel();" class="btn btn-primary col-lg-3 col-md-push-2">导入Excel模板下载</button>
        <button type="button" onclick="doExportExcel();" class="btn btn-primary col-lg-2 col-md-push-4">导  出</button>
        <form class="col-lg-4 col-md-push-6" method="post" enctype="multipart/form-data">
            <div class="input-group">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" onclick="doImportExcel();">导 入</button>
                </span>
                <input type="file" onchange="getFileName();" name="userExcel" class="form-control myfile">
            </div><!-- /input-group -->
            <input type="text" class="form-control myfile2" placeholder="请选择文件">
        </form>
    </div>

    <div class="myuser" data-spy="scroll" data-target="#navbar-example" data-offset="0">
    <table class="table">
        <caption class="text-center" style="color: #2e6da4;font-size: larger;">用戶列表</caption>
        <thead>
        <tr class="danger">
            <th>id编号</th>
            <th>用戶名</th>
            <th>密  碼</th></tr>
        </thead>
        <tbody>
        <#list userList as user>
            <tr class="success">
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td></tr>
            </tr>
        </#list>
        </tbody>
    </table>
    </div>

    <script src="/static/js/jquery-3.3.1.min.js"></script>
    <script src="/static/js/bootstrap.js"></script>
    <script type="text/javascript">
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

    </script>
</body>
</html>