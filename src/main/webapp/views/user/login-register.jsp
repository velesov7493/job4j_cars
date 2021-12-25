<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../template/layouts/pageHeader.jsp" %>

<%
    String error = (String) request.getAttribute("error");
%>

<section id="form"><!--form-->
    <div class="container">
        <div class="row">
            <div class="col-sm-12 <%=error == null ? "hidden error-panel" : "error-panel"%>">
                <h3 class="title">Ошибка</h3>
                <p id="err-text"><%=error == null ? "" : error%></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 col-sm-offset-1">
                <div class="login-form"><!--login form-->
                    <h2>Вход в свой аккаунт</h2>
                    <form name="login-form" method="post" action="<%=request.getContextPath()%>/login.do">
                        <input name="nLogin" type="text" placeholder="Логин" />
                        <input name="nPassword" type="password" placeholder="Пароль" />
                        <button type="submit" class="btn btn-default">Войти</button>
                    </form>
                </div><!--/login form-->
            </div>
            <div class="col-sm-1">
                <h2 class="or">ИЛИ</h2>
            </div>
            <div class="col-sm-4">
                <div class="signup-form"><!--sign up form-->
                    <h2>Регистрация</h2>
                    <form name="register-form" method="post" action="<%=request.getContextPath()%>/register.do">
                        <input name="nName" type="text" placeholder="Ф.И.О."/>
                        <input name="nLogin" type="text" placeholder="Логин"/>
                        <input name="nEmail" type="email" placeholder="Email"/>
                        <input name="nPhone" type="text" placeholder="Телефон"/>
                        <input name="nPassword" type="password" placeholder="Пароль"/>
                        <input name="nCheckPassword" type="password" placeholder="Проверка пароля"/>
                        <button type="submit" class="btn btn-default">Зарегистрироваться</button>
                    </form>
                </div><!--/sign up form-->
            </div>
        </div>
    </div>
</section><!--/form-->

<%@ include file="../../template/layouts/pageFooter.jsp" %>