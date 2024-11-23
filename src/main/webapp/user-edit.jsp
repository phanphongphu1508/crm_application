<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>User Management</title>
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
<link rel="stylesheet" href="./css/custom.css">
<script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
<script src="bootstrap/dist/js/bootstrap.min.js"></script>
</head>

<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-static-top m-b-0">
            <div class="navbar-header">
                <a class="logo" href="index.html">
                    <b><img src="plugins/images/pixeladmin-logo.png" alt="home" /></b>
                    <span class="hidden-xs"><img src="plugins/images/pixeladmin-text.png" alt="home" /></span>
                </a>
            </div>
        </nav>
        
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row bg-title">
                    <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                        <h4 class="page-title">Danh sách thành viên</h4>
                    </div>
                    <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                        <a href="/crm_application/user-add" class="btn btn-sm btn-success">Thêm mới</a>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <div class="table-responsive">
                                <table class="table" id="userTable">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Username</th>
                                            <th>Role</th>
                                            <th>#</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listUsers}" var="user" varStatus="status">
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td>${user.firstName}</td>
                                                <td>${user.lastName}</td>
                                                <td>${user.email}</td>
                                                <td>${user.roleName}</td>
                                                <td>
                                                    <button type="button" class="btn btn-sm btn-primary editBtn">Sửa</button>
                                                    <a href="/crm_application/users?id=${user.id}" class="btn btn-sm btn-danger">Xóa</a>
                                                    <a href="user-details.html" class="btn btn-sm btn-info">Xem</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer text-center"> 2018 &copy; myclass.com </footer>
        </div>
    </div>

    <!-- Edit Modal HTML -->
    <div id="editUserModal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Edit User</h4>
                </div>
                <form id="editUserForm" action="/crm_application/user-update" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="id" id="editUserId">
                        <div class="form-group">
                            <label for="editFirstName">First Name</label>
                            <input type="text" class="form-control" name="firstName" id="editFirstName" required>
                        </div>
                        <div class="form-group">
                            <label for="editLastName">Last Name</label>
                            <input type="text" class="form-control" name="lastName" id="editLastName" required>
                        </div>
                        <div class="form-group">
                            <label for="editEmail">Username (Email)</label>
                            <input type="email" class="form-control" name="email" id="editEmail" required>
                        </div>
                        <div class="form-group">
                            <label for="editRole">Role</label>
                            <input type="text" class="form-control" name="roleName" id="editRole" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save Changes</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- JavaScript to open the modal with user data -->
    <script>
        $(document).ready(function() {
            // When edit button is clicked
            $('.editBtn').on('click', function() {
                var row = $(this).closest('tr');
                var id = row.find('td:eq(0)').text();
                var firstName = row.find('td:eq(1)').text();
                var lastName = row.find('td:eq(2)').text();
                var email = row.find('td:eq(3)').text();
                var roleName = row.find('td:eq(4)').text();

                // Populate modal fields
                $('#editUserId').val(id);
                $('#editFirstName').val(firstName);
                $('#editLastName').val(lastName);
                $('#editEmail').val(email);
                $('#editRole').val(roleName);

                // Show the modal
                $('#editUserModal').modal('show');
            });
        });
    </script>
</body>
</html>
