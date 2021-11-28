<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsf/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<div class="d-flex justify-content-between align-items-center mt-3 mb-3">
    <div class="d-inline-flex col-sm-5 col-lg-3">
        <input id="search" type="text" class="name form-control ml-5 " style="width: 12em" autocomplete="off" placeholder="<spring:message code="browse.search"/>" >
        <button  class="btn btn-outline-dark ml-2" onclick="clearSearch()">X</button>
    </div>
    <form class="form-inline m-0 " style="height: 2em; float: right;">
        <button class="btn btn-outline-info m-0" formaction="createNewHeterostructure"><spring:message code="browse.create"/></button>
    </form>
</div>

<div class="tableFixHead mt-2 bg-white flex-grow-1">
    <table class="table table-hover" id="tableOfHeterostructures">
        <thead class="thead-dark">
        <tr align="center">
            <th style="width:8em"><spring:message code="browse.data"/></th>
            <th style="width:8em"><spring:message code="browse.heterostructure"/></th>
            <th style="width:40em"><spring:message code="browse.description"/></th>
            <th style="width:8em"></th>
        </tr>
        </thead>
        <tbody id="tableBody">
            <form>
                <c:forEach items="${listOfAllHeterostructures}" var="heterostructure">
                    <tr align="center">
                        <td>${heterostructure[0]}</td>
                        <td>${heterostructure[1]}</td>
                        <td>${heterostructure[2]}</td>
                        <td><button class="btn btn-outline-primary btn-sm" formaction="viewHeterostructure/${heterostructure[1]}.svg">
                        <spring:message code="browse.open"/></button></td>
                    </tr>
                </c:forEach>
            </form>
        </tbody>
    </table>
</div>

<script src="<c:url value="/resources/js/heterostructureTable.js"/>"></script>

