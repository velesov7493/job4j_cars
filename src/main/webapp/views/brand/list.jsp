<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../template/layouts/pageHeader.jsp" %>
<%
    request.setAttribute("pageScript", "ui/carBrandsList.js");
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
            <div class="col-sm-12 card">
                <div class="card-header">
                    <h2>Марки автомобилей</h2>
                </div>
                <div class="card-body">
                    <ul class="nav navbar-nav">
                        <li><a class="btn btn-primary" href="<%=request.getContextPath()%>/car-brands.do?id=0"><i class="fa fa-plus"></i> Добавить</a></li>
                    </ul>
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>№ п/п</th>
                            <th>Наименование</th>
                            <th>Операции</th>
                        </tr>
                        </thead>
                        <tbody id="brands-container">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="../../template/layouts/pageFooter.jsp" %>