<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../template/layouts/pageHeader.jsp" %>
<%
    request.setAttribute("pageScript", "ui/carBrandsElement.js");
    HttpSession s = request.getSession();
    String error = (String) s.getAttribute("error");
    int carBrandId = Integer.parseInt(request.getParameter("id"));
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
                    <h2><%=carBrandId == 0 ? "Новая марка авто" : "Редактирование марки авто"%></h2>
                </div>
                <div class="card-body">
                    <form name="car-brand-edit-form" method="post" action="<%=request.getContextPath()%>/car-brands.do">
                        <input name="nId" id="car-brand-id" type="hidden" value="<%=carBrandId%>" />
                        <div class="form-group">
                            <label for="car-brand-name">Наименование:</label>
                            <input name="nName" id="car-brand-name" class="form-control" type="text" value="" />
                        </div>
                        <div class="form-group">
                            <button id="car-brand-submit" type="submit" class="pull-right btn btn-primary">Сохранить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<%@ include file="../../template/layouts/pageFooter.jsp" %>