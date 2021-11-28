<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #080815;" id="navigation">
    <div class="container d-flex justify-content-between align-items-center">
        <ul class="m-0"> <h2 class="page-title text-white p-0 m-2" href="#"><i style="white-space: nowrap;">MBE Manager </i></h2></ul>
        <ul>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".navbar-collapse" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span>
            </button>
        </ul>
        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal.username" var="username"/>
        <ul class="m-0">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ">
                    <li class="nav-item">
                        <a class="nav-link" style="white-space: nowrap;" href="<c:url value = "/browseHeterostructure/"/>"><spring:message code="menu.heterostructures.button"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value = "/settings"/>"><spring:message code="menu.settings.button"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" style="white-space: nowrap;" href="<c:url value = "/about"/>"><spring:message code="menu.about.button"/></a>
                    </li>
                </ul>
            </div>
        </ul>

        <ul class="m-0">
            <div class="collapse navbar-collapse">
                <div class="d-flex flex-column align-items-center justify-content-center">
                    <ul><h6 style="color: white;">${username}</h6></ul>
                    <ul>
                        <form class="form-inline" action="<c:url value="/logout"/>">
                            <button class="btn btn-outline-warning" type="submit" ><spring:message code="logout"/></button>
                        </form>
                    </ul>
                </div>
            </div>
        </ul>
        </sec:authorize>

        <ul class="m-0">
            <div class="collapse navbar-collapse">
                <select class="bg-dark text-white" id="locales">
                    <option value="ru"
                            <c:if test="${pageContext.response.locale == 'ru'}">
                                <c:out value="selected"/></c:if>
                    >Ru</option>
                    <option value="en" value="selected"
                            <c:if test="${pageContext.response.locale == 'en'}">
                                <c:out value="selected"/></c:if>
                    >En</option>
                </select>
            </div>
        </ul>
    </div> <!-- container -->
</nav>