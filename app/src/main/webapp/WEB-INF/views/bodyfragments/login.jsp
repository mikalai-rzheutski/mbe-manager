<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<br>
    <h5 class="thin text-center" style="color: red">${message}</h5>
<br>
<div class="col-lg-6 offset-lg-3">
    <div class="panel panel-default border border-secondary rounded p-4">
        <div class="panel-body">
            <h4 class="thin text-center"><spring:message code="login.title"/></h4>
            <hr>
            <c:url value="/j_spring_security_check" var="loginUrl"/>
            <form action="${loginUrl}" method="post">
                <div class="top-margin">
                    <label><spring:message code="login.login"/><span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="j_username" required autofocus>
                </div>
                <div class="top-margin">
                    <label><spring:message code="login.pw"/><span class="text-danger">*</span></label>
                    <input type="password" class="form-control" name="j_password" placeholder="Password" required>
                </div>
                <hr>
                <div class="text-right">
                    <button class="btn btn-warning border border-secondary" type="submit"><spring:message code="login.signin"/></button>
                </div>
            </form>
        </div>
    </div>

    <div class="d-flex flex-column align-items-center">
        <br>
        <h5 class="thin text-center"><spring:message code="login.or"/></h5>
        <br>
        <a class="btn btn-outline-dark mt-3" href="/login/google" role="button" style="text-transform:none"><svg aria-hidden="true" class="social-button-icon mr-2" width="18" height="18" viewBox="0 0 18 18"><path d="M16.51 8H8.98v3h4.3c-.18 1-.74 1.48-1.6 2.04v2.01h2.6a7.8 7.8 0 0 0 2.38-5.88c0-.57-.05-.66-.15-1.18z" fill="#4285F4"></path><path d="M8.98 17c2.16 0 3.97-.72 5.3-1.94l-2.6-2a4.8 4.8 0 0 1-7.18-2.54H1.83v2.07A8 8 0 0 0 8.98 17z" fill="#34A853"></path><path d="M4.5 10.52a4.8 4.8 0 0 1 0-3.04V5.41H1.83a8 8 0 0 0 0 7.18l2.67-2.07z" fill="#FBBC05"></path><path d="M8.98 4.18c1.17 0 2.23.4 3.06 1.2l2.3-2.3A8 8 0 0 0 1.83 5.4L4.5 7.49a4.77 4.77 0 0 1 4.48-3.3z" fill="#EA4335"></path></svg><spring:message code="login.viaGoogle"/>
        </a>
        <br>
        <a class="btn btn-dark mt-3" href="/login/github" role="button" ><svg aria-hidden="true" class="social-button-icon mr-2" width="18" height="18" viewBox="0 0 18 18"><path d="M9 1a8 8 0 0 0-2.53 15.59c.4.07.55-.17.55-.38l-.01-1.49c-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.42 7.42 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48l-.01 2.2c0 .21.15.46.55.38A8.01 8.01 0 0 0 9 1z" fill="#ffff"></path></svg><spring:message code="login.viaGithub"/>
        </a>
    </div>
</div>
