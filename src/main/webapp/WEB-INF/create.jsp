<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login Webapp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-4">
    <nav class="navbar bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">SSC Login Webapp</a>
            <a type="button" class="btn btn-outline-secondary btn-sm" href="/logout">
                <i class="fa fa-sign-out"></i> &nbsp; Logout
            </a>
        </div>
    </nav>
    <c:if test="${not empty message}">
        <c:choose>
            <c:when test="${hasError}">
                <div class="alert alert-danger" role="alert">
                    <i class="fa fa-exclamation-triangle"></i>
                    ${message}
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-success" role="alert">
                    <i class="fa fa-check"></i>
                        ${message}
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="row justify-content-md-center">
        <div class="col-sm-12 col-md-6 col-lg-4 mt-5">
            <h2 class="text-center mb-4">Create New User</h2>
            <p>${error}</p>
            <form action="/user/create" method="post">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username"
                           autocomplete="off" value="${username}">
                    <label for="username"><i class="fa fa-user"></i> Username</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="displayName" name="displayName" placeholder="Display Name"
                          autocomplete="off" value="${displayName}">
                    <label for="displayName"><i class="fa fa-user"></i> Display Name</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                        autocomplete="off" value="${password}">
                    <label for="password"><i class="fa fa-key"></i> Password</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="cpassword" name="cpassword"
                           placeholder="Confirm Password" autocomplete="off" value="${cpassword}">
                    <label for="cpassword"><i class="fa fa-key"></i> Confirm Password</label>
                </div>
                <div class="d-grid">
                    <button class="btn btn-outline-success" type="submit"><i class="fa fa-plus"></i> Create</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

