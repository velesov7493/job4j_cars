<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../template/layouts/pageHeader.jsp" %>

<%
    request.setAttribute("pageScript", "ui/postsElement.js");
    String postId = request.getParameter("id");
    HttpSession s = request.getSession();
    String error = (String) s.getAttribute("error");
    s.removeAttribute("error");
%>

<section>
    <div class="container">
        <div class="row">
            <div class="<%=error == null ? "hidden error-panel" : "error-panel"%>">
                <h3>Ошибка</h3>
                <p id="err-text"><%=error == null ? "" : error%></p>
            </div>
            <div class="col-sm-12 padding-right">
                <div class="product-details"><!--product-details-->
                    <% if (!"0".equals(postId)) { %>
                    <div class="col-sm-5">
                        <div class="view-product">
                            <img id="post-image" src="<%=request.getContextPath()%>/images.do?id=<%=postId%>" alt="" />
                            <a id="post-image-view" class="btn btn-primary" href="<%=request.getContextPath()%>/images.do?id=<%=postId%>" target="_blank"><i class="fa fa-eye"></i> Просмотр</a>
                            <h2 class="text-center">Об авторе</h2>
                            <table class="author-information">
                                <tr>
                                    <th>Ф.И.О.</th>
                                    <td id="author-name"></td>
                                </tr>
                                <tr>
                                    <th>Телефон</th>
                                    <td id="author-phone"></td>
                                </tr>
                                <tr>
                                    <th>Email</th>
                                    <td id="author-email"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <% } %>
                    <div class="<%="0".equals(postId) ? "col-sm-12" : "col-sm-7"%>">
                        <div class="product-information"><!--/product-information-->
                            <h2 class="text-center"><%="0".equals(postId) ? "Новое объявление" : "Объявление"%></h2>
                            <form id="post-edit-form" method="post" action="<%=request.getContextPath()%>/posts.do" enctype="multipart/form-data">
                                <input id="post-id" type="hidden" name="nId" value="<%=postId%>" />
                                <div class="form-group">
                                    <label for="post-model">Модель:</label>
                                    <input id="post-model" class="form-control" type="text" name="nModel" required />
                                </div>
                                <div class="form-group">
                                    <label for="post-price">Цена:</label>
                                    <input id="post-price" class="form-control" type="number" name="nPrice" required />
                                </div>
                                <div class="form-group">
                                    <label for="post-body-type">Тип кузова:</label>
                                    <select id="post-body-type" class="form-control" name="nBodyType" required></select>
                                </div>
                                <div class="form-group">
                                    <label for="post-brand">Производитель (торговая марка):</label>
                                    <select id="post-brand" class="form-control" name="nBrand" required></select>
                                </div>
                                <div class="form-group">
                                    <label for="post-description">Описание:</label>
                                    <textarea id="post-description" class="form-control" name="nDescription" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="post-photo">Фото:</label>
                                    <input id="post-photo" class="form-horizontal" type="file" name="nPhoto" />
                                </div>
                                <div class="form-check">
                                    <input name="nSold" class="form-check-input" id="post-sold" type="checkbox" />
                                    <label class="form-check-label" for="post-sold">Продано</label>
                                </div>
                                <div class="form-group">
                                    <button id="post-delete" class="pull-right btn btn-danger">Удалить</button>
                                    <button id="post-submit" type="submit" class="pull-right btn btn-primary">Сохранить</button>
                                </div>
                            </form>
                        </div><!--/product-information-->
                    </div>
                </div><!--/product-details-->
            </div>
        </div>
    </div>
</section>

<%@ include file="../../template/layouts/pageFooter.jsp" %>