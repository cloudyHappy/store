<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <style>
        form{
            width: 270px;
            height:150px;
            border: 1px solid black;
            padding-left: 50px;
            padding-top: 50px;
            margin-top: 50px ;
            margin-left: 50px;
        }
        form input[type="text"]{
            height:40px;
            margin-left: 0;
        }
        #message{
            font-family: "楷体";
            font-size: 20px;
            margin-left: -30px;
        }
        form div input{
            margin-left: 20px;
            margin-top: 15px;
            height: 25px;
            width: 50px;
        }
    </style>
</head>
<script type="text/javascript">
    function close(){

        alert("z");
        window.close();
    }
</script>
<body style="background:green;width: 450px;height: 250px;" >
<form action="" method="post">
    <span id="message">分类名称:</span><input type="text" name="cname">
    <br>
    <div><input type="submit" value="提交">
        <input type="reset" value="重置">
        <input type="button" value="返回" onclick="close()"></div>
</form>

</body>
</html>
