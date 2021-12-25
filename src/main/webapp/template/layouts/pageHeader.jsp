<%@ page import="ru.job4j.cars.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    User user = (User) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="velesov7493@yandex.ru">
    <title>Доска объявлений о продаже</title>
    <link href="<%=request.getContextPath()%>/template/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/template/css/font-awesome.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/template/css/animate.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/template/css/main.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/template/css/responsive.css" rel="stylesheet">
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/template/images/ico/favicon.png">
</head><!--/head-->

<body>
<header id="header"><!--header-->
    <div class="header_top"><!--header_top-->
        <div class="container">
            <div class="row">
                <div class="col-sm-6 ">
                    <div class="contactinfo">
                        <ul class="nav nav-pills">
                            <li><a href=""><i class="fa fa-phone"></i> +7 962 167 16 81</a></li>
                            <li><a href=""><i class="fa fa-envelope"></i> velesov7493@yandex.ru</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="social-icons pull-right">
                        <ul class="nav navbar-nav">
                            <li><a href=""><i class="fa fa-facebook"></i></a></li>
                            <li><a href=""><i class="fa fa-twitter"></i></a></li>
                            <li><a href=""><i class="fa fa-linkedin"></i></a></li>
                            <li><a href=""><i class="fa fa-dribbble"></i></a></li>
                            <li><a href=""><i class="fa fa-google-plus"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header_top-->

    <div class="header-middle"><!--header-middle-->
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    <div class="logo pull-left">
                        <a href="posts.do"><img src="<%=request.getContextPath()%>/template/images/home/logo.png" alt="" /></a>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="shop-menu pull-right">
                        <ul class="nav navbar-nav">
                            <% if (user == null) { %>
                            <li class="pull-right"><a href="<%=request.getContextPath()%>/login.do"><i class="fa fa-users"></i> Авторизация</a></li>
                            <% } else { %>
                            <li class="pull-right"><a href="<%=request.getContextPath()%>/logout.do"><i class="fa fa-unlock"></i> Выйти</a></li>
                            <% } %>
                            <li class="pull-left"><a href="<%=request.getContextPath()%>/body-types.do"><i class="fa fa-book"></i> Типы кузова</a></li>
                            <li class="pull-left"><a href="<%=request.getContextPath()%>/car-brands.do"><i class="fa fa-book"></i> Марки авто</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header-middle-->
</header>