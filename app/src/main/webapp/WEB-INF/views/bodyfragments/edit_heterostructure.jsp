<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page import="by.ifanbel.data.database.entities.Material" %>

<sf:form modelAttribute="jspBeanHeterostructure" action="saveHeterostructure" method="post">
<div class="d-flex flex-column align-items-stretch">
    <div class="d-flex ">
        <div class="flex-grow-1 d-flex flex-column align-items-start justify-content-start" >
            <div class="input-group input-group-sm mt-1" style="width: 16em;">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1" style="width: 8em;"><spring:message code="edit.samplename"/></span>
                </div>
                <sf:input type="text" autocomplete="off" path="sampleNumber" required="true" class="form-control" aria-describedby="basic-addon1"/>
            </div>
            <div class="input-group input-group-sm mt-1" style="width: 16em;">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3" style="width: 8em;"><spring:message code="edit.date"/></span>
                </div>
                <sf:input type="date" path="date" class="form-control" name="date" required="true" aria-describedby="basic-addon3"/>
            </div>
            <div class="input-group input-group-sm mt-1" style="width: 30em;">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon4" style="width: 8em;"><spring:message code="edit.growers"/></span>
                </div>
                <sf:input class="form-control" type="text" path="growers" aria-describedby="basic-addon4"/>
            </div>
        </div>

        <div class="flex-grow-1 d-flex flex-column align-items-start justify-content-start">
            <div class="input-group input-group-sm mt-1" style="width: 16em;">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon4" style="width: 8em;"><spring:message code="edit.substrate"/></span>
                </div>
                <sf:input path="substrate" autocomplete="off" list="substrateTypeOptions" class="form-control" aria-describedby="basic-addon4"/>
                <datalist id="substrateTypeOptions">
                    <option value="Al2O3(0001)">
                    <option value="Si(111)">
                    <option value="6H-SiC">
                    <option value="4H-SiC">
                </datalist>
            </div>
            <div class="input-group input-group-sm mt-1" style="width: 16em;">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3" style="width: 8em;"><spring:message code="edit.waferdiameter"/></span>
                </div>
                <sf:input path="waferSize" autocomplete="off" list="waferSizeOptions" class="form-control" aria-describedby="basic-addon3"/>
                <datalist id="waferSizeOptions">
                    <option value="2&quot;">
                    <option value="3&quot;">
                    <option value="4&quot;">
                </datalist>
            </div>
            <div class="input-group input-group-sm mt-1" style="width: 16em;">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon2" style="width: 8em;"><spring:message code="edit.wafernumber"/></span>
                </div>
                <sf:input type="text"  autocomplete="off" path="waferNumber" class="form-control" aria-describedby="basic-addon2"/>
            </div>
        </div>
    </div>
    <div class="input-group input-group-sm mt-1">
        <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon5" style="width: 8em;"><spring:message code="edit.description"/></span>
        </div>
        <sf:input class="form-control" type="text" autocomplete="off" path="description" required="true" aria-describedby="basic-addon5"/>
    </div>
    <div class="input-group input-group-sm mt-1">
        <div class="input-group-prepend">
            <span class="input-group-text" style="width: 8em;"><spring:message code="edit.comments"/></span>
        </div>
        <sf:textarea class="form-control" autocomplete="off" path="comments" aria-label="With textarea" style="height: 8em;"></sf:textarea>
    </div>
</div>
    <div id="error">
        <c:forEach items="${jspBeanHeterostructure.errorMessages}" var="errorMessage" varStatus="loop">
            <br>
            <i style="color: red">${jspBeanHeterostructure.errorMessages[loop.index]}</i>
        </c:forEach>
    </div>

    <div class=" mt-2 bg-white ">
        <table class="table table-hover table-sm table-bordered" id="editTab" onclick="rowNumbering(this.id)">
            <caption class="text-center bg-secondary text-white" style="caption-side: top;"><spring:message code="edit.layers"/></caption>
            <thead class="thead bg-secondary text-white">
            <tr align="center">
                <th style="width: 2em" class="align-middle"><spring:message code="edit.layernumber"/></th>
                <th style="width: 4em" class="align-middle"><spring:message code="edit.mode"/></th>
                <th style="width: 6em" class="align-middle"><spring:message code="edit.thickness"/></th>
                <th style="width: 6em" class="align-middle"><i><spring:message code="edit.x"/></i></th>
                <th style="width: 6em" class="align-middle"><i><spring:message code="edit.y"/></i></th>
                <th style="width: 8em" class="align-middle"><spring:message code="edit.temperature"/></th>
                <th style="width: 6em" class="align-middle"><spring:message code="edit.heat"/></th>
                <th style="width: 4em" class="align-middle"><spring:message code="edit.nitrflow"/></th>
                <th style="width: 4em" class="align-middle"><spring:message code="edit.doping"/></th>
                <th style="width: 10em" class="align-middle"><spring:message code="edit.comment"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody id="tableBody">
            <c:forEach items="${jspBeanHeterostructure.layerId}" var="id" varStatus="loop">
                <tr id="layerRow">
                    <td>
                        <input class="form-control-plaintext text-center p-0 m-0" readonly type="text" name="layerId" value="${id}"/>
                    </td>
                    <td align="center">
                        <select class="form-control form-control-sm text-center p-0 m-0" id="mode" name="growthMode" value="${jspBeanHeterostructure.growthMode[loop.index]}">
                            <option value=${Material.AMMONIA}
                                    <c:if test="${jspBeanHeterostructure.growthMode[loop.index] == Material.AMMONIA}">
                                        <c:out value="selected"/></c:if>
                            >NH<sub>3</sub></option>
                            <option value=${Material.N_PLASMA}
                                    <c:if test="${jspBeanHeterostructure.growthMode[loop.index] == Material.N_PLASMA}">
                                        <c:out value="selected"/></c:if>
                            >N*
                            </option>
                        </select>
                    </td>
                    <td>
                        <input class="form-control form-control-sm text-center p-0 m-0" type="number" name="thickness" autocomplete="off"
                                              value="${jspBeanHeterostructure.thickness[loop.index]}" size="7"/>
                    </td>
                    <td>
                        <input class="form-control form-control-sm text-center p-0 m-0" type="text" name="x" autocomplete="off"
                                              value="${jspBeanHeterostructure.x[loop.index]}" size="7"/>
                    </td>
                    <td>
                        <input class="form-control form-control-sm text-center p-0 m-0" type="text" name="y" autocomplete="off"
                                              value="${jspBeanHeterostructure.y[loop.index]}" size="7"/>
                    </td>
                    <td>
                        <input class="form-control form-control-sm text-center p-0 m-0" type="text" name="temperature" autocomplete="off"
                                              value="${jspBeanHeterostructure.temperature[loop.index]}" size="7"/>
                    </td>
                    <td>
                        <input class="form-control form-control-sm text-center p-0 m-0" type="text" name="heat" autocomplete="off"
                                              value="${jspBeanHeterostructure.heat[loop.index]}" size="7"/>
                    </td>
                    <td>
                        <input class="form-control form-control-sm text-center p-0 m-0" type="text" name="nflow" autocomplete="off"
                                              value="${jspBeanHeterostructure.nflow[loop.index]}" size="7"/>
                    </td>
                    <td>
                        <select class="form-control form-control-sm text-center p-0 m-0" id="dopant" name="dopant" value="${jspBeanHeterostructure.dopant[loop.index]}">
                            <option value=${Material.NO_DOPANT}
                                    <c:if test="${jspBeanHeterostructure.dopant[loop.index] == Material.NO_DOPANT}">
                                        <c:out value="selected"/></c:if>
                            >-
                            </option>
                            <option value=${Material.Si_DOPANT}
                                    <c:if test="${jspBeanHeterostructure.dopant[loop.index] == Material.Si_DOPANT}">
                                        <c:out value="selected"/></c:if>
                            >Si
                            </option>
                            <option value=${Material.Mg_DOPANT}
                                    <c:if test="${jspBeanHeterostructure.dopant[loop.index] == Material.Mg_DOPANT}">
                                        <c:out value="selected"/></c:if>
                            >Mg
                            </option>
                        </select>
                    </td>
                    <td>
                        <input class="form-control form-control-sm text-center p-0 m-0" type="text" name="layerComment" autocomplete="off"
                                              value="${jspBeanHeterostructure.layerComment[loop.index]}" size="7"/>
                    </td>
                    <td id="buttonsCells" align="center">
                        <div style="display: block; margin: 0 auto; width: 4.5em;">
                            <input class="btn btn-info btn-sm" type="button" value="+" onclick="addLayer(this.parentNode.parentNode.parentNode.rowIndex)">
                            <input class="btn btn-danger btn-sm" type="button" value="—" onclick="deleteLayer(this.parentNode.parentNode.parentNode.rowIndex)">
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="d-flex justify-content-between align-items-center mb-5">
        <button class="btn btn-outline-primary" formaction="openHeterostructure"><spring:message code="edit.open"/></button>
        <button class="btn btn-outline-secondary" style="width: 15em;" formaction="saveHeterostructure"><spring:message code="edit.save"/></button>
        <button class="btn btn-outline-danger" formaction="deleteHeterostructure"><spring:message code="edit.delete"/></button>
    </div>
</sf:form>

<script src="<c:url value="/resources/js/editTable.js"/>"></script>