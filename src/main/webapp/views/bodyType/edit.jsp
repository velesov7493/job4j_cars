<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../template/layouts/pageHeader.jsp" %>
<%
    request.setAttribute("pageScript", "ui/bodyTypesElement.js");
    HttpSession s = request.getSession();
    String error = (String) s.getAttribute("error");
    int bodyTypeId = Integer.parseInt(request.getParameter("id"));
    s.removeAttribute("error");
%>
<section>
    <div class="container full-page">
        <div class="row">
            <div class="<%=error == null ? "hidden error-panel" : "error-panel"%>">
                <h3>Ошибка</h3>
                <p id="err-text"><%=error == null ? "" : error%></p>
            </div>
            <div class="col-sm-12 card">
                <div class="card-header">
                    <h2><%=bodyTypeId == 0 ? "Новый тип кузова" : "Редактирование типа кузова"%></h2>
                </div>
                <div class="card-body">
                    <form name="body-type-edit-form" method="post" action="<%=request.getContextPath()%>/body-types.do">
                        <input name="nId" id="body-type-id" type="hidden" value="<%=bodyTypeId%>" />
                        <div class="form-group">
                            <label for="body-type-name">Наименование:</label>
                            <input name="nName" id="body-type-name" class="form-control" type="text" value="" />
                        </div>
                        <div class="form-group">
                            <button id="body-type-submit" type="submit" class="pull-right btn btn-primary">Сохранить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<%@ include file="../../template/layouts/pageFooter.jsp" %>