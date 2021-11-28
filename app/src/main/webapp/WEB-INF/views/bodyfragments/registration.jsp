<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsf/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
    $(document).ready(function($){
        var $inputs = $('input[name="password"], input[name="email"]');
        $inputs.on('input', function () {
            $inputs.not(this).prop('required', !$(this).val().length);
        });
    });
</script>
<form action="newUser">
<div class="d-flex flex-column justify-content-between align-items-center mt-5">

    <div class="input-group mb-4" style="width: 25em;">
        <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon0"><spring:message code="registration.name"/></span>
        </div>
        <input type="text" class="form-control" aria-label="Username" required name="userLogin" aria-describedby="basic-addon0">
    </div>

    <div class="input-group mb-4" style="width: 15em;">
        <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelect01"><spring:message code="registration.role"/></label>
        </div>
        <select class="custom-select" id="inputGroupSelect01" name="userRole">
            <option selected>Choose...</option>
            <option value="role_user">User</option>
            <option value="role_admin">Admin</option>
            <option value="role_superadmin">Superadmin</option>
            <option value="role_banned">Banned</option>
        </select>
    </div>

    <div class="d-flex align-items-center">
        <div class="input-group mb-4 mr-5" style="width: 18em;">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1"><spring:message code="registration.pw"/></span>
            </div>
            <input type="password" class="form-control" aria-label="Username" name="password" aria-describedby="basic-addon1" required>
        </div>
        <h6 class="lead"><spring:message code="registration.andOr"/></h6>
        <div class="input-group mb-4 ml-5" style="width: 18em;">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon2" name="email"><spring:message code="registration.email"/></span>
            </div>
            <input type="text" class="form-control" aria-label="Username" name="email" aria-describedby="basic-addon2" required>
        </div>
    </div>
    <button class="btn btn-outline-info mb-2" name="create" type="submit"><spring:message code="registration.create"/></button>

    <div class="tableFixHead2 m-3 bg-white ml-10">
        <table class="table table-bordered table-hover" id="tableOfHeterostructures">
            <thead class="thead-dark">
            <tr align="center">
                <th style="width:4em">No.</th>
                <th style="width:15em">Username</th>
                <th style="width:15em">e-mail</th>
                <th style="width:10em">Role</th>
            </tr>
            </thead>
            <tbody id="tableBody">
            <c:forEach items="${listOfUsers}" var="aUser" varStatus="loop">
                <tr>
                    <td align="center" style="width:3em">${loop.index+1}</td>
                    <td align="center" style="width:10em">${aUser[0]}</td>
                    <td align="center" style="width:15em">${aUser[1]}</td>
                    <td align="center" style="width:10em">${aUser[3]}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</form>
