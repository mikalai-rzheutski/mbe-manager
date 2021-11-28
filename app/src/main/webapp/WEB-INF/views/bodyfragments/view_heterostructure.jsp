<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<script type="text/javascript">
    function checkAddress(checkbox)
    {
        if (!checkbox.checked)
        {
            return '-' + checkbox.value;
        }
        return checkbox.value;
    }
</script>
<%--<html>
<head>
    <title>Browse heterostructure</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/view-heterostructures-style.css"/>">
    <script type="text/javascript">
        function checkAddress(checkbox)
        {
            if (!checkbox.checked)
            {
                return '-' + checkbox.value;
            }
            return checkbox.value;
        }
    </script>
</head>
<body>
<header class="section-header">
    <p><spring:message code="view.heterostructure"/>:   ${jspBeanHeterostructure.sampleNumber} - ${jspBeanHeterostructure.description}</p>
</header>
<div class="sect">
    <div class="sect1">
        <div class="main-frame">
            <div &lt;%&ndash;style="overflow: hidden;"&ndash;%&gt;>
                <div class="main-buttons">
                    <div id="search-form">
                        <form>
                            <button class="main-button1" type="submit" name="sampleName"
                                    value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}" formaction="openPrevious">&#60;
                            </button>
                        </form>
                        <form id="data" action="openCurrent"><input class="heterostructure-name" type="text"
                                                                    name="sampleName"
                                                                    value="${jspBeanHeterostructure.sampleNumber}">
                        </form>
                        <form>
                            <button class="main-button1" type="submit" name="sampleName"
                                    value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}" formaction="openNext">&#62;
                            </button>
                        </form>
                        <form>
                            <button class="main-button1" type="submit" name="sampleName"
                                    value="${jspBeanHeterostructure.sampleNumber}" formaction="editHeterostructure">
                                <spring:message code="view.edit"/>
                            </button>
                        </form>
                    </div>
                    <div>
                        <form>
                            <button class="main-button1" type="submit" name="sampleName"
                                    value="${jspBeanHeterostructure.sampleNumber}" formaction="exportToMSWord">
                                <spring:message code="view.export"/>
                                <img src="<c:url value="/resources/img/MSWord.svg"/>" alt="MS Word"
                                     style="vertical-align: middle" height="18px">
                            </button>
                        </form>
                    </div>
                    <div id="new-heterostructure">
                        <form>
                            <button class="main-button1" type="submit" name="sampleName"
                                    value="${jspBeanHeterostructure.sampleNumber}"
                                    formaction="createNewHeterostructure"><spring:message code="view.create"/>
                            </button>
                        </form>
                    </div>
                    <div class="end-div"></div>
                </div>
            </div>
            <div class="main-window">
                <div class="svg-structure">
                    <div class="options-on-top">
                        <select id="type-selector" onChange="window.location.href=this.value">
                            <option value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}.svg"
                                <c:if test="${image_type == 'svg'}">
                                    <c:out value="selected"/></c:if>
                            ><spring:message code="view.format.svg"/>
                            </option>
                            <option value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}.png"
                                <c:if test="${image_type == 'png'}">
                                    <c:out value="selected"/></c:if>
                            ><spring:message code="view.format.png"/>
                            </option>
                        </select>
                        <div class="show-conditions">
                            <input type="checkbox" name="showConditions" onChange="window.location.href = checkAddress(this)"
                                <c:if test="${showConditionsChar == '-'}">
                                    <c:out value="unchecked"/></c:if>
                                <c:if test="${showConditionsChar ne '-'}">
                                    <c:out value="checked"/></c:if>
                                   value="${jspBeanHeterostructure.sampleNumber}.${image_type}"
                                   style="margin-right: 0.5em;"><spring:message code="view.showconditions"/>
                        </div>
                </div>
                    <img src="get_image/${jspBeanHeterostructure.sampleNumber}${showConditionsChar}.${image_type}" width="500px"
                         height="707px" style="overflow: hidden;">
                </div>
                <div class="text-info">
                    <p class="head-info">${jspBeanHeterostructure.sampleNumber}</p>
                    <p align="center">${jspBeanHeterostructure.description}</p>
                    <br>
                    <p><b><spring:message code="view.date"/>: </b> ${jspBeanHeterostructure.date}<br>
                        <b><spring:message code="view.growers"/>: </b>${jspBeanHeterostructure.growers}<br>
                        <b><spring:message code="view.substrate"/>: </b>${jspBeanHeterostructure.substrate}, ${jspBeanHeterostructure.waferSize}<br>
                        <b><spring:message code="view.wafernumber"/>: </b>${jspBeanHeterostructure.waferNumber}<br><br>
                    </p>
                    <p class="comments">${jspBeanHeterostructure.comments}</p>
                </div>

            </div>

        </div>
    </div>
</div>

</body>
</html>--%>

<link rel="stylesheet" href="<c:url value="/resources/css/view-heterostructures-style.css"/>">
<form>
<div class="d-flex justify-content-between mt-3 mb-3">
    <div class="input-group " style="width: 15em;">
        <%--<form>--%>
        <div class="input-group-prepend">
            <button class="btn btn-secondary" value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}" formaction="openPrevious"><</button>
        </div>
        <input type="text" name="sampleName" class="form-control text-center" placeholder="" aria-label="" aria-describedby="basic-addon1" value="${jspBeanHeterostructure.sampleNumber}">
        <div class="input-group-append">
            <button class="btn btn-secondary" value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}" formaction="openNext">></button>
        </div>
        <%--</form>--%>
    </div>

    <button class="btn btn-secondary btn-sm " style="width: 12em" value="${jspBeanHeterostructure.sampleNumber}" formaction="editHeterostructure">
        <spring:message code="view.edit"/></button>

    <button class="btn btn-secondary btn-sm " style="width: 8em" value="${jspBeanHeterostructure.sampleNumber}" formaction="exportToMSWord">
        <spring:message code="view.export"/><img src="/resources/img/MSWord.svg" alt="MS Word" style="vertical-align: middle; margin-left: 10px;" height="25px"></button>

    <button class="btn btn-outline-info btn-sm" value="${jspBeanHeterostructure.sampleNumber}"
            formaction="createNewHeterostructure"><spring:message code="view.create"/></button>
</div>
</form>

<div class=" flex-grow-1 d-flex">
    <div class="col-sm-7 bg-white m-1 align-items-stretch  border border-secondary rounded d-flex flex-column">
        <div class="d-flex justify-content-between align-items-center border border-secondary bg-light pl-3 p-1 mt-1" >
            <div>
                <select class="form-control form-control-sm text-center p-0 m-0" onChange="window.location.href=this.value">
                    <option value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}.svg"
                            <c:if test="${image_type == 'svg'}">
                                <c:out value="selected"/></c:if>
                    ><spring:message code="view.format.svg"/>
                    </option>
                    <option value="${jspBeanHeterostructure.sampleNumber}${showConditionsChar}.png"
                            <c:if test="${image_type == 'png'}">
                                <c:out value="selected"/></c:if>
                    ><spring:message code="view.format.png"/>
                    </option>
                </select>
            </div>
            <div class="form-check pr-5">
                <input type="checkbox" class="form-check-input" id="exampleCheck1" name="showConditions" onChange="window.location.href = checkAddress(this)"
                <c:if test="${showConditionsChar == '-'}">
                    <c:out value="unchecked"/></c:if>
                <c:if test="${showConditionsChar ne '-'}">
                    <c:out value="checked"/></c:if>
                       value="${jspBeanHeterostructure.sampleNumber}.${image_type}">
                <label class="form-check-label" for="exampleCheck1"><spring:message code="view.showconditions"/></label>
            </div>
        </div>

        <div class="d-flex justify-content-center align-items-center flex-grow-1">
            <img src="get_image/${jspBeanHeterostructure.sampleNumber}${showConditionsChar}.${image_type}" style="overflow: hidden;">
        </div>

    </div>
    <div class="flex-grow-1 bg-white m-1 border border-secondary rounded p-2">
        <p><strong>${jspBeanHeterostructure.sampleNumber}</strong></p>
        <p align="center">${jspBeanHeterostructure.description}</p>
        <br>
        <p><b><spring:message code="view.date"/>: </b> ${jspBeanHeterostructure.date}<br>
            <b><spring:message code="view.growers"/>: </b>${jspBeanHeterostructure.growers}<br>
            <b><spring:message code="view.substrate"/>: </b>${jspBeanHeterostructure.substrate}, ${jspBeanHeterostructure.waferSize}<br>
            <b><spring:message code="view.wafernumber"/>: </b>${jspBeanHeterostructure.waferNumber}<br><br>
        </p>
        <p class="comments">
            ${jspBeanHeterostructure.comments}
        </p>
    </div>
</div>
