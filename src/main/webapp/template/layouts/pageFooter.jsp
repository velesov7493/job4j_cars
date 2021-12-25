<%@ page contentType="text/html;charset=UTF-8" %>

<footer id="footer"><!--Footer-->
    <div class="footer-top">
        <div class="container">
            <div class="row">
                <div class="col-sm-3">
                    <div class="companyinfo">
                        <h2><span>auto-ad</span>-board</h2>
                        <p>Доска объявлений о продаже авто</p>
                    </div>
                </div>
                <div class="col-sm-6"></div>
                <div class="col-sm-3">
                    <div class="address">
                        <img src="<%=request.getContextPath()%>/template/images/home/map.png" alt="" />
                        <p>505 S Atlantic Ave Virginia Beach, VA(Virginia)</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="footer-bottom">
        <div class="container">
            <div class="row">
                <div class="col-sm-4"><p>Copyright © 2013 E-Shopper. All rights reserved.</p></div>
                <div class="col-sm-4"><p>Designed by <span><a target="_blank" href="http://www.themeum.com">Themeum</a></span></p></div>
                <div class="col-sm-4"><p> Автор иконок: <a href="https://www.flaticon.com/ru/authors/kiranshastry" title="Kiranshastry">Kiranshastry</a> from <a href="https://www.flaticon.com/ru/" title="Flaticon">www.flaticon.com</a></p></div>
            </div>
        </div>
    </div>

</footer><!--/Footer-->

<script src="<%=request.getContextPath()%>/template/js/lib/jquery.js"></script>
<script src="<%=request.getContextPath()%>/template/js/lib/jquery.scrollUp.min.js"></script>
<script src="<%=request.getContextPath()%>/template/js/lib/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/template/js/lib/jquery.prettyPhoto.js"></script>
<script src="<%=request.getContextPath()%>/template/js/ui/main.js"></script>
<% if (request.getAttribute("pageScript") != null) { %>
<script src="<%=request.getContextPath()%>/template/js/<%=request.getAttribute("pageScript")%>"></script>
<% } %>
</body>
</html>