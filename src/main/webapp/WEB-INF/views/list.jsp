<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>📋 게시판</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f9f9f9;
        }
        h1 {
            color: #333;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .search-bar {
            margin-bottom: 20px;
        }
        .search-bar form {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .search-bar form input[type="text"] {
            padding: 5px;
            width: 300px;
        }
        .search-bar form button {
            padding: 6px 10px;
        }
        .controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }
        .sort-controls, .page-controls {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .sort-controls a {
            margin-right: 10px;
            text-decoration: none;
            color: #007bff;
        }
        .sort-controls a.active {
            font-weight: bold;
            color: #dc3545;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
            margin-top: 10px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f0f0f0;
            cursor: pointer;
        }
        th:hover {
            background-color: #e0e0e0;
        }
        .pagination {
            margin-top: 20px;
            text-align: center;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #333;
        }
        .pagination a.active {
            font-weight: bold;
            color: #007bff;
        }
        .btn {
            padding: 8px 16px;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .stats {
            margin-top: 10px;
            font-size: 12px;
            color: #666;
        }
        .btn-disabled {
            background-color: #ccc;
            color: #666;
            cursor: not-allowed;
        }
    </style>
    <script>
        function showNotAvailable() {
            alert('현재는 메인으로 버튼만 활성화되어 있습니다.');
            return false;
        }
    </script>
</head>
<body>

<div class="header">
    <h1>📋 게시글 목록</h1>
    <div class="user-info">
        <c:choose>
            <c:when test="${sessionScope.currentUser != null}">
                <span>안녕하세요,
                    <c:url var="userDetailUrl" value="/users/${sessionScope.currentUser.id}" />
                    <a href="${userDetailUrl}" style="color: #007bff; text-decoration: none;">
                        ${sessionScope.currentUser.name}
                    </a>님!
                    <c:if test="${sessionScope.currentUser.roles == 'ADMIN'}">
                        <span style="background: #dc3545; color: white; padding: 2px 6px; border-radius: 10px; font-size: 11px; margin-left: 5px;">ADMIN</span>
                    </c:if>
                </span>
                <c:url var="logoutUrl" value="/users/logout" />
                <form action="${logoutUrl}" method="post" style="display:inline;">
                    <button type="submit" class="btn btn-secondary">로그아웃</button>
                </form>
            </c:when>
            <c:otherwise>
                <c:url var="loginUrl" value="/users/login" />
                <c:url var="signupUrl" value="/users/new" />
                <a href="${loginUrl}" class="btn btn-primary">로그인</a>
                <a href="${signupUrl}" class="btn btn-secondary">회원가입</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- 🔍 검색 폼 -->
<div class="search-bar">
    <c:url var="searchUrl" value="/posts" />
    <form action="${searchUrl}" method="get">
        <input type="text" name="keyword" placeholder="제목, 내용, 작성자명 검색" value="${keyword}">
        <input type="hidden" name="sort" value="${sort}">
        <input type="hidden" name="order" value="${order}">
        <input type="hidden" name="pageSize" value="${pageSize}">
        <button type="submit">검색</button>
        <c:if test="${hasKeyword}">
            <c:url var="clearSearchUrl" value="/posts">
                <c:param name="sort" value="${sort}" />
                <c:param name="order" value="${order}" />
                <c:param name="pageSize" value="${pageSize}" />
            </c:url>
            <a href="${clearSearchUrl}" class="btn btn-secondary">검색 해제</a>
        </c:if>
    </form>
</div>

<!-- 컨트롤 바 -->
<div class="controls">
    <div class="sort-controls">
        <span>정렬:</span>

        <!-- 정렬 링크들 -->
        <c:url var="sortUrl" value="/posts">
            <c:param name="pageSize" value="${pageSize}" />
            <c:if test="${hasKeyword}">
                <c:param name="keyword" value="${keyword}" />
            </c:if>
        </c:url>

        <a href="${sortUrl}&sort=id&order=desc"
           class="${sort == 'id' && order == 'desc' ? 'active' : ''}">최신순</a>
        <a href="${sortUrl}&sort=title&order=asc"
           class="${sort == 'title' && order == 'asc' ? 'active' : ''}">제목순</a>
        <a href="${sortUrl}&sort=userId&order=asc"
           class="${sort == 'userId' && order == 'asc' ? 'active' : ''}">작성자순</a>
        <a href="${sortUrl}&sort=viewCount&order=desc"
           class="${sort == 'viewCount' && order == 'desc' ? 'active' : ''}">조회순</a>
        <a href="${sortUrl}&sort=likeCount&order=desc"
           class="${sort == 'likeCount' && order == 'desc' ? 'active' : ''}">좋아요순</a>
    </div>

    <div class="page-controls">
        <span>페이지당:</span>
        <c:forEach var="size" items="${pageSizeOptions}">
            <c:url var="pageSizeUrl" value="/posts">
                <c:param name="sort" value="${sort}" />
                <c:param name="order" value="${order}" />
                <c:param name="pageSize" value="${size}" />
                <c:if test="${hasKeyword}">
                    <c:param name="keyword" value="${keyword}" />
                </c:if>
            </c:url>
            <a href="${pageSizeUrl}" class="${pageSize == size ? 'active' : ''}">${size}개</a>
        </c:forEach>
    </div>
</div>

<!-- 액션 버튼들 -->
<div style="margin-bottom: 15px;">
    <c:if test="${sessionScope.currentUser != null}">
        <c:url var="newPostUrl" value="/posts/new" />
        <a href="${newPostUrl}" class="btn btn-primary">✏️ 게시글 작성</a>
    </c:if>

    <!-- 관리자만 볼 수 있는 관리 버튼들 -->
    <c:if test="${sessionScope.currentUser != null && sessionScope.currentUser.roles == 'ADMIN'}">
        <c:url var="boardsUrl" value="/boards" />
        <c:url var="usersUrl" value="/users" />
        <a href="${boardsUrl}" class="btn btn-secondary">📂 게시판 관리</a>
        <a href="${usersUrl}" class="btn btn-secondary">👥 사용자 관리</a>
    </c:if>
</div>

<!-- 검색 결과 정보 -->
<div class="stats">
    <c:choose>
        <c:when test="${hasKeyword}">
            🔍 "${keyword}" 검색 결과: <strong>${totalPosts}개</strong>
            <c:if test="${totalPosts == 0}">- 검색 결과가 없습니다.</c:if>
        </c:when>
        <c:otherwise>
            전체 게시글: <strong>${totalPosts}개</strong>
        </c:otherwise>
    </c:choose>
</div>

<!-- 📑 게시글 테이블 -->
<c:choose>
    <c:when test="${not empty posts}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>제목</th>
                    <th>내용</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>좋아요</th>
                    <th>작성일</th>
                    <th>수정일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="post" items="${posts}">
                    <tr>
                        <td>${post.id}</td>
                        <td style="text-align: left;">
                            <c:url var="postUrl" value="/posts/${post.id}" />
                            <a href="${postUrl}">${post.title != null ? post.title : '제목 없음'}</a>
                        </td>
                        <td style="text-align: left; max-width: 200px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            ${post.content != null ? post.content : '내용 없음'}
                        </td>
                        <td>
                            <!-- ⭐ PostDTO를 사용하므로 바로 username 접근! -->
                            ${post.username}
                        </td>
                        <td>${post.viewCount}</td>
                        <td>❤️ ${post.likeCount}</td>
                        <td>${post.formattedCreatedAt}</td>
                        <td>
                            <c:if test="${post.createdAt ne post.updatedAt}">
                                ${post.formattedUpdatedAt}
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 50px; color: #999;">
            <c:choose>
                <c:when test="${hasKeyword}">
                    <h3>😔 검색 결과가 없습니다</h3>
                    <p>"${keyword}"에 해당하는 게시글을 찾을 수 없습니다.</p>
                    <c:url var="allPostsUrl" value="/posts">
                        <c:param name="sort" value="${sort}" />
                        <c:param name="order" value="${order}" />
                        <c:param name="pageSize" value="${pageSize}" />
                    </c:url>
                    <a href="${allPostsUrl}" style="color: #007bff;">전체 목록 보기</a>
                </c:when>
                <c:otherwise>
                    <h3>📝 첫 번째 게시글을 작성해보세요!</h3>
                    <c:if test="${sessionScope.currentUser != null}">
                        <c:url var="createPostUrl" value="/posts/new" />
                        <a href="${createPostUrl}" style="color: #007bff;">게시글 작성하기</a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>

<!-- 페이지네이션 -->
<c:if test="${pageInfo.totalPageCount > 1}">
    <div class="pagination">
        <!-- 첫 페이지 -->
        <c:if test="${pageInfo.firstPage != -1}">
            <c:url var="firstUrl" value="/posts">
                <c:param name="page" value="${pageInfo.firstPage}" />
                <c:param name="sort" value="${sort}" />
                <c:param name="order" value="${order}" />
                <c:param name="pageSize" value="${pageSize}" />
                <c:if test="${hasKeyword}">
                    <c:param name="keyword" value="${keyword}" />
                </c:if>
            </c:url>
            <a href="${firstUrl}">[처음]</a>
        </c:if>

        <!-- 이전 블록 -->
        <c:if test="${pageInfo.prevPage != -1}">
            <c:url var="prevUrl" value="/posts">
                <c:param name="page" value="${pageInfo.prevPage}" />
                <c:param name="sort" value="${sort}" />
                <c:param name="order" value="${order}" />
                <c:param name="pageSize" value="${pageSize}" />
                <c:if test="${hasKeyword}">
                    <c:param name="keyword" value="${keyword}" />
                </c:if>
            </c:url>
            <a href="${prevUrl}">[이전]</a>
        </c:if>

        <!-- 페이지 번호들 -->
        <c:set var="endPage" value="${pageInfo.endPage == -1 ? pageInfo.startPage : pageInfo.endPage}" />
        <c:forEach var="i" begin="${pageInfo.startPage}" end="${endPage}">
            <c:url var="pageUrl" value="/posts">
                <c:param name="page" value="${i}" />
                <c:param name="sort" value="${sort}" />
                <c:param name="order" value="${order}" />
                <c:param name="pageSize" value="${pageSize}" />
                <c:if test="${hasKeyword}">
                    <c:param name="keyword" value="${keyword}" />
                </c:if>
            </c:url>
            <a href="${pageUrl}" class="${i == pageInfo.currentPage ? 'active' : ''}">[${i}]</a>
        </c:forEach>

        <!-- 다음 블록 -->
        <c:if test="${pageInfo.nextPage != -1}">
            <c:url var="nextUrl" value="/posts">
                <c:param name="page" value="${pageInfo.nextPage}" />
                <c:param name="sort" value="${sort}" />
                <c:param name="order" value="${order}" />
                <c:param name="pageSize" value="${pageSize}" />
                <c:if test="${hasKeyword}">
                    <c:param name="keyword" value="${keyword}" />
                </c:if>
            </c:url>
            <a href="${nextUrl}">[다음]</a>
        </c:if>

        <!-- 마지막 페이지 -->
        <c:if test="${pageInfo.lastPage != -1}">
            <c:url var="lastUrl" value="/posts">
                <c:param name="page" value="${pageInfo.lastPage}" />
                <c:param name="sort" value="${sort}" />
                <c:param name="order" value="${order}" />
                <c:param name="pageSize" value="${pageSize}" />
                <c:if test="${hasKeyword}">
                    <c:param name="keyword" value="${keyword}" />
                </c:if>
            </c:url>
            <a href="${lastUrl}">[마지막]</a>
        </c:if>

        <!-- 페이지 정보 -->
        <div style="margin-top: 10px; font-size: 12px; color: #666;">
            ${pageInfo.currentPage} / ${pageInfo.totalPageCount} 페이지 (총 ${totalPosts}개)
        </div>
    </div>
</c:if>

</body>
</html>