<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../template/layouts/pageHeader.jsp" %>

<%
    request.setAttribute("pageScript", "ui/postsList.js");
%>

<section>
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <div class="left-sidebar">
                    <h2>Кузов</h2>
                    <div class="panel-group category-products" id="accordian">
                        <ul id="body-types" class="nav nav-pills nav-stacked">

                        </ul>
                    </div>

                    <div class="brands_products">
                        <h2>Марки</h2>
                        <div class="brands-name">
                            <ul id="car-brands" class="nav nav-pills nav-stacked">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-9 padding-horizontal-zero card">
                <div class="card-header">
                    <h2>Объявления</h2>
                </div>
                <div class="card-body">
                    <ul class="nav navbar-nav">
                        <li><a class="btn btn-primary" href="<%=request.getContextPath()%>/posts.do?id=0"><i class="fa fa-plus"></i> Новое объявление</a></li>
                        <% if (user != null) { %>
                        <li><a id="action-show-main" class="btn btn-default" href="#" onclick="refreshPosts(null, null, <%=user.getId()%>)"><i class="fa fa-user"></i> Мои объявления</a></li>
                        <% } %>
                        <li><a id="action-show-all" class="btn btn-default" href="#" onclick="refreshPosts(null, null, null)"><i class="fa fa-globe"></i> Все объявления</a></li>
                    </ul>
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>Модель</th>
                            <th>Тип кузова</th>
                            <th>Цена</th>
                            <th>Операции</th>
                        </tr>
                        </thead>
                        <tbody id="posts-container">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="../../template/layouts/pageFooter.jsp" %>