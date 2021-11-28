<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="main-button-group">
    <form method="get">
        <div>
            <button class="main-button" formaction="<c:url value = "/browseHeterostructure/"/>">
                <spring:message code="menu.heterostructures.button"/>
            </button>
        </div>
        <%--
        <div>
            <button class="main-button" formaction="<c:url value = "/noSuchPage"/>">
                <spring:message code="menu.setup.button"/>
            </button>
        </div>
        <div>
            <button class="main-button" formaction="<c:url value = "/noSuchPage"/>">
                <spring:message code="menu.liqnitrogen.button"/>
            </button>
        </div>
        <div>
            <button class="main-button" formaction="<c:url value = "/noSuchPage"/>">
                <spring:message code="menu.precursors.button"/>
            </button>
        </div>
        --%>
        <div>
            <button class="main-button" formaction="<c:url value = "/settings"/>">
                <spring:message code="menu.settings.button"/>
            </button>
        </div>
        <div>
            <button id="about-button" class="main-button" formaction="<c:url value = "/about"/>">
                <spring:message code="menu.about.button"/>
            </button>
        </div>
    </form>
</div>