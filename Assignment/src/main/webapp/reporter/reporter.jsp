<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ABC News - Trang quản lý tin tức dành cho phóng viên.">
    <meta name="keywords" content="tin tức, quản lý tin, phóng viên, ABC News">
    <meta name="author" content="ABC News">
    <title>ABC News - Trang Phóng Viên</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&display=swap">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
    .action-bar a {
    display: inline-block;
    background: linear-gradient(135deg, #ffcc00, #ffb300);
    color: #000;
    padding: 10px 20px;
    border-radius: 8px;
    font-weight: 600;
    text-decoration: none;
    font-family: 'Roboto', sans-serif;
    transition: all 0.3s ease;
    box-shadow: 0 3px 8px rgba(0, 0, 0, 0.2);
}

.action-bar a:hover {
    background: linear-gradient(135deg, #ffd740, #ffc107);
    transform: translateY(-2px);
    box-shadow: 0 5px 12px rgba(0, 0, 0, 0.3);
}

.action-bar {
    margin-bottom: 15px;
    text-align: right; 
}
    
        .popup-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.6);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }
        .popup-form { position: relative; background: #fff; width: 380px; border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3); padding: 35px 25px 25px; text-align: center;
            animation: fadeIn 0.3s ease-in-out; }
        .popup-form h3 { color: #0d47a1; margin-bottom: 8px; }
        .popup-form input { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc;
            border-radius: 8px; font-size: 15px; }
        .popup-form button[type="submit"] {
            background: #ffcc00; color: #000; border: none; padding: 10px 25px; border-radius: 8px;
            font-weight: bold; cursor: pointer; transition: 0.3s; width: 100%;
        }
        .popup-form button[type="submit"]:hover { background: #fdd835; }
        .close-icon { position: absolute; top: 10px; right: 12px; background: none; border: none;
            font-size: 22px; font-weight: bold; color: #555; cursor: pointer; }
        .close-icon:hover { color: #e53935; }
        .change-pass-btn { display: inline-block; background: #ffcc00; color: #000;
            padding: 10px 18px; border-radius: 8px; font-weight: bold; text-decoration: none;
            text-align: center; margin-bottom: 20px; transition: 0.3s; }
        .change-pass-btn:hover { background: #fdd835; }
    </style>
</head>

<body>
    <!-- Header -->
    <header class="site-header">
        <div class="container">
            <div class="logo">ABC <span>News</span></div>
            <%@ include file="../includes/news_index_nav.jsp" %>
        </div>
    </header>
	
    <!-- Main Content -->
    <div class="container">
        <div class="container-3col">
            <!-- Cột trái -->
            <%@ include file="../includes/news_index_left.jsp" %>

            <!-- Cột giữa -->
            <section class="center-col">
                <h2>Quản lý tin tức của bạn</h2>
                <div class="action-bar">
					<a href="${pageContext.request.contextPath}/admin/add_edit_news">Thêm tin mới</a>
                </div>

                <table class="news-table">
                    <thead>
                        <tr>
                            <th>Tiêu đề</th>
                            <th>Loại tin</th>
                            <th>Ngày đăng</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:choose>
                            <c:when test="${empty newsList}">
                                <tr>
                                    <td colspan="4" style="text-align:center; color:gray;">
                                        Chưa có bài viết nào được đăng.
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="n" items="${newsList}">
                                    <tr>
                                        <td>${n.title}</td>
                                       <td>${n.categoryName}</td>
                                        <td><fmt:formatDate value="${n.postedDate}" pattern="dd/MM/yyyy" /></td>
                                        <td>
											<a href="${pageContext.request.contextPath}/admin/add_edit_news?id=${n.id}" class="edit-btn">Sửa</a>
                                            <a href="${pageContext.request.contextPath}/delete_news?id=${n.id}" 
                                               class="delete-btn" 
                                               onclick="return confirm('Bạn có chắc muốn xóa tin này?')">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </section>

            <!-- Cột phải -->
            <aside class="right-col">
                <a href="#" class="change-pass-btn" onclick="openPopup()">Đổi mật khẩu</a>
                <%@ include file="../includes/news_index_right.jsp" %>
            </aside>
        </div>
    </div>

    <!-- Popup Đổi mật khẩu -->
    <div class="popup-overlay" id="popupOverlay">
        <div class="popup-form">
            <button type="button" class="close-icon" onclick="closePopup()">×</button>
            <h3>Đổi mật khẩu</h3>
            <p>Vui lòng nhập thông tin để thay đổi mật khẩu của bạn</p>
            <form action="${pageContext.request.contextPath}/changePassword" method="post">
                <input type="password" name="oldPassword" placeholder="Mật khẩu cũ" required>
                <input type="password" name="newPassword" placeholder="Mật khẩu mới" required>
                <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu mới" required>
                <button type="submit">Xác nhận</button>
            </form>
        </div>
    </div>

    <!-- Footer -->
<%@ include file="../includes/news_index_footer.jsp" %>

    <script>
        function openPopup() {
            document.getElementById('popupOverlay').style.display = 'flex';
        }
        function closePopup() {
            document.getElementById('popupOverlay').style.display = 'none';
        }
    </script>
</body>
</html>
