<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="ABC_news.Entity.User" %>

<%
    User user = (User) session.getAttribute("user");
    String fullname = (user != null && user.getFullname() != null) ? user.getFullname() : "Quản trị viên";
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ABC News - Quản lý người dùng.">
    <meta name="keywords" content="quản lý người dùng, quản trị, ABC News">
    <meta name="author" content="ABC News">
    <title>ABC News - Quản lý người dùng</title>

    <!-- Font và CSS -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
.custom-modal {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    z-index: 1000;
    align-items: center;
    justify-content: center;
    overflow-y: auto;
}

.custom-modal-content {
    background: #fff;
    border-radius: 12px;
    padding: 32px 36px;
    width: 100%;
    max-width: 420px;
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.25);
    text-align: center;
    position: relative;
    animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
    from {
        transform: translateY(-20px);
        opacity: 0;
    }
    to {
        transform: translateY(0);
        opacity: 1;
    }
}

.custom-close-btn {
    position: absolute;
    top: 12px;
    right: 18px;
    font-size: 22px;
    color: #666;
    cursor: pointer;
    transition: color 0.3s;
}
.custom-close-btn:hover {
    color: #e53935;
}

.custom-modal-content h2 {
    color: #1a237e;
    font-size: 22px;
    font-weight: 700;
    margin-bottom: 8px;
}

.custom-form-group {
    text-align: left;
    margin-bottom: 16px;
}

.custom-form-group label {
    display: block;
    margin-bottom: 6px;
    font-weight: 600;
    font-size: 14px;
    color: #1a237e;
}

.custom-form-group input,
.custom-form-group select,
.custom-form-group textarea {
    width: 100%;
    padding: 10px 12px;
    border: 1px solid #d0d7de;
    border-radius: 6px;
    background-color: #f5f9ff;
    font-size: 14px;
    transition: border 0.3s, box-shadow 0.3s;
    box-sizing: border-box;
}

.custom-form-group input:focus,
.custom-form-group select:focus,
.custom-form-group textarea:focus {
    border-color: #1a73e8;
    box-shadow: 0 0 5px rgba(26, 115, 232, 0.4);
    outline: none;
}

.custom-submit-btn {
    width: 100%;
    background-color: #f9a825;
    border: none;
    padding: 12px;
    color: #fff;
    font-weight: 600;
    font-size: 16px;
    border-radius: 6px;
    cursor: pointer;
    transition: background 0.3s, transform 0.2s;
}
.custom-submit-btn:hover {
    background-color: #f57f17;
    transform: translateY(-2px);
}

@media (max-width: 768px) {
    .custom-modal-content {
        width: 90%;
        padding: 24px;
    }
}

body.modal-open {
    overflow: hidden;
}

    </style>
</head>

<body>
    <!-- Header -->
    <header class="site-header">
        <div class="container">
            <div class="logo">ABC <span>News</span></div>

            <nav class="menu">
                <a href="${pageContext.request.contextPath}/index"
                   class="${fn:contains(pageContext.request.requestURI, '/index') ? 'active' : ''}">Trang chủ</a>

               <a href="${pageContext.request.contextPath}/category?name=Văn hóa"
   class="${fn:contains(pageContext.request.requestURI, 'Văn hóa') ? 'active' : ''}">Văn hóa</a>

<a href="${pageContext.request.contextPath}/category?name=Pháp luật"
   class="${fn:contains(pageContext.request.requestURI, 'Pháp luật') ? 'active' : ''}">Pháp luật</a>

<a href="${pageContext.request.contextPath}/category?name=Thể thao"
   class="${fn:contains(pageContext.request.requestURI, 'Thể thao') ? 'active' : ''}">Thể thao</a>
               
                <a href="${pageContext.request.contextPath}/admin"
                   class="${fn:contains(pageContext.request.requestURI, '/admin') ? 'active' : ''}">Quản trị</a>
            </nav>

            <div class="header-actions">
                Xin chào <strong><%= fullname %></strong>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Đăng xuất</a>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <div class="container">
        <section class="center-col">
            <h2>Quản lý người dùng</h2>

            <div class="action-bar">
                <a href="#add-user" class="add-news-btn" onclick="showModal('add-user-modal')">+ Thêm người dùng</a>
            </div>

            <table class="news-table">
                <thead>
                    <tr>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th>Vai trò</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
               <tbody>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${fn:escapeXml(u.fullname)}</td>
            <td>${fn:escapeXml(u.email)}</td>
            <td>
                <c:choose>
				    <c:when test="${u.role}">Quản trị</c:when>
				    <c:otherwise>Phóng viên</c:otherwise>
				</c:choose>

            </td>
            <td>
                <a href="#edit-user"
                   class="edit-btn"
                   data-id="${u.id}"
                   data-fullname="${fn:escapeXml(u.fullname)}"
                   data-email="${fn:escapeXml(u.email)}"
                   data-role="${u.role}"
                   data-birthday="${u.birthday}"
                   data-gender="${u.gender}"
                   data-mobile="${u.mobile}"
                   onclick="openEditUser(this)">Sửa</a>
                <a href="${pageContext.request.contextPath}/admin/delete_user?id=${u.id}"
                   class="delete-btn"
                   onclick="return confirm('Bạn có chắc muốn xóa người dùng này?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</tbody>

            </table>
        </section>
    </div>

    <!-- Add User Modal -->
    <div id="add-user-modal" class="custom-modal">
        <div class="custom-modal-content">
            <span class="custom-close-btn" onclick="closeModal('add-user-modal')">&times;</span>
            <h2>Thêm người dùng</h2>
            <form action="${pageContext.request.contextPath}/admin/add_user" method="post">
                <div class="custom-form-group">
                    <label for="add-userId">Mã người dùng</label>
                    <input type="text" id="add-userId" name="id" placeholder="Nhập mã người dùng (ví dụ: pv01)" required>
                </div>
                <div class="custom-form-group">
                    <label for="add-fullName">Họ và tên</label>
                    <input type="text" id="add-fullName" name="fullname" placeholder="Nhập họ và tên" required>
                </div>
                <div class="custom-form-group">
                    <label for="add-birthday">Ngày sinh</label>
                    <input type="date" id="add-birthday" name="birthday" required>
                </div>
                <div class="custom-form-group">
                    <label for="add-gender">Giới tính</label>
                    <select id="add-gender" name="gender" required>
                        <option value="1">Nam</option>
                        <option value="0">Nữ</option>
                    </select>
                </div>
                <div class="custom-form-group">
                    <label for="add-mobile">Số điện thoại</label>
                    <input type="text" id="add-mobile" name="mobile" placeholder="Nhập số điện thoại" required>
                </div>
                <div class="custom-form-group">
                    <label for="add-email">Email</label>
                    <input type="email" id="add-email" name="email" placeholder="Nhập email" required>
                </div>
                <div class="custom-form-group">
                    <label for="add-password">Mật khẩu</label>
                    <input type="password" id="add-password" name="password" placeholder="Nhập mật khẩu" required>
                </div>
                <div class="custom-form-group">
                    <label for="add-role">Vai trò</label>
                    <select id="add-role" name="role" required>
                        <option value="0">Phóng viên</option>
                        <option value="1">Quản trị</option>
                    </select>
                </div>
                <button type="submit" class="custom-submit-btn">Thêm người dùng</button>
            </form>
        </div>
    </div>

    <!-- Edit User Modal -->
    <div id="edit-user-modal" class="custom-modal">
        <div class="custom-modal-content">
            <span class="custom-close-btn" onclick="closeModal('edit-user-modal')">&times;</span>
            <h2>Sửa người dùng</h2>
            <form action="${pageContext.request.contextPath}/admin/edit_user" method="post">
                <input type="hidden" name="id" id="edit-user-id">
                <div class="custom-form-group">
                    <label for="edit-fullName">Họ và tên</label>
                    <input type="text" id="edit-fullName" name="fullname" required>
                </div>
                <div class="custom-form-group">
                    <label for="edit-birthday">Ngày sinh</label>
                    <input type="date" id="edit-birthday" name="birthday" required>
                </div>
                <div class="custom-form-group">
                    <label for="edit-gender">Giới tính</label>
                    <select id="edit-gender" name="gender" required>
                        <option value="1">Nam</option>
                        <option value="0">Nữ</option>
                    </select>
                </div>
                <div class="custom-form-group">
                    <label for="edit-mobile">Số điện thoại</label>
                    <input type="text" id="edit-mobile" name="mobile" required>
                </div>
                <div class="custom-form-group">
                    <label for="edit-email">Email</label>
                    <input type="email" id="edit-email" name="email" required>
                </div>
                <div class="custom-form-group">
                    <label for="edit-password">Mật khẩu</label>
                    <input type="password" id="edit-password" name="password" placeholder="Để trống nếu không đổi">
                </div>
                <div class="custom-form-group">
                    <label for="edit-role">Vai trò</label>
                    <select id="edit-role" name="role" required>
                        <option value="0">Phóng viên</option>
                        <option value="1">Quản trị</option>
                    </select>
                </div>
                <button type="submit" class="custom-submit-btn">Lưu thay đổi</button>
            </form>
        </div>
    </div>

    <!-- Footer -->
<%@ include file="../includes/news_index_footer.jsp" %>

    <!-- Script -->
    <script>
        function showModal(modalId, id = '', fullname = '', email = '', role = '', birthday = '', gender = '', mobile = '', address = '') {
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.style.display = 'flex';
                document.body.classList.add('modal-open');

                if (modalId === 'edit-user-modal') {
                    document.getElementById('edit-user-id').value = id;
                    document.getElementById('edit-fullName').value = fullname;
                    document.getElementById('edit-email').value = email;
                    document.getElementById('edit-role').value = role ? '1' : '0';
                    document.getElementById('edit-birthday').value = birthday || '';
                    document.getElementById('edit-gender').value = gender || '';
                    document.getElementById('edit-mobile').value = mobile || '';
                } else if (modalId === 'add-user-modal') {
                    const form = modal.querySelector('form');
                    if (form) form.reset();
                }
            }
        }

        function closeModal(modalId) {
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.style.display = 'none';
                document.body.classList.remove('modal-open');
                const form = modal.querySelector('form');
                if (form) form.reset();
            }
        }

        window.onclick = function(event) {
            const modals = document.getElementsByClassName('custom-modal');
            for (let modal of modals) {
                if (event.target === modal) {
                    modal.style.display = 'none';
                    document.body.classList.remove('modal-open');
                    const form = modal.querySelector('form');
                    if (form) form.reset();
                }
            }
        };
        function openEditUser(element) {
            const modal = document.getElementById('edit-user-modal');
            if (!modal) return;

            document.getElementById('edit-user-id').value = element.dataset.id || '';
            document.getElementById('edit-fullName').value = element.dataset.fullname || '';
            document.getElementById('edit-email').value = element.dataset.email || '';
            document.getElementById('edit-role').value = element.dataset.role || '0';
            document.getElementById('edit-birthday').value = element.dataset.birthday || '';
            document.getElementById('edit-gender').value = element.dataset.gender || '1';
            document.getElementById('edit-mobile').value = element.dataset.mobile || '';

            modal.style.display = 'flex';
            document.body.classList.add('modal-open');
        }
    </script>
</body>
</html>