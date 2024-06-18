Thymeleaf(타임리프)




<c:forEach  var="item" items="${items}" >

</c:forEach>



<c:forEach  var="item" items="${items}" varStatus="status">
	
</c:forEach>


			- 콤마(,) 문자열 
			- <c:forEach>액션의 items 애트리뷰트를 이용해서 처리할 수 있는 데이터

<c:forEach var="item" items="Apple,Orange,Melon">
	<div>${item}</div>
</c:forEach>



<c:choose>
	<c:when test="${param.age < 8}">
		유치원생
	</c:when>
	<c:when test="${param.age < 14}">
		초등학생
	</c:when>
	<c:when test="${param.age < 17}">
		청소년
	</c:when>
	<c:otherwise>
		성인
	</c:otherwise>
</c:choose>	



<c:forTokens var="item" items="Apple#Orange#Melon" delims="#+">
	<div>${item}</div>
</c:forTokens>



					<c:catch var="e">
					<%
						String str = null;
						str.toUpperCase();
					%>
					</c:catch>
					<c:if test="${e != null}">
						<%--에러메시지 : ${e.getMessage()} 아래줄과 동일-->
						에러메시지 : ${e.message}
					</c:if>
					

					<c:redirect url="ex03.jsp">
						<c:param name="key1" value="value1" />
						<c:param name="key2" value="value2" />
					</c:redirect>					
					
					<c:import url="inc.jsp" >
						<c:param name="num1" value="20" />
						<c:param name="num2" value="30" />
					</c:import>

					<jsp:include ... /> 
					  : 버퍼에 추가, 서버쪽 자원만 버퍼에 추가 가능
					  : <jsp:param name="이름" value="값" /> : 요청데이터에 추가
					  
					  
<c:url />
<a href="<c:url value='/member/login' />">로그인</a>	

<c:url var="loginUrl" value='/member/login' />
<a href="${loginUrl}">로그인</a>

<a href="<c:url value='/member/join' />">회원가입</a>


				<c:out value="${str}" default="값없음" />
				<c:out value="<h1>제목</h1>" />
				<c:out value="<h1>제목제목</h1>" escapeXml="false"/>


<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="date" value="<%=new Date()%>" />
${date}<br>
<fmt:formatDate value="${date}" type="date" />
<fmt:formatDate value="${date}" type="time" />
<fmt:formatDate value="${date}" type="both" />


<fmt:formatNumber value="${num}" groupingUsed="true" type="perent"/>
<fmt:formatNumber value="${num}" pattern="#,###.##" />


<fmt:setLocale value="ko_kr" />

<fmt:setBundle basename="messages.common" />   /resource/messages/common.properties

<fmt:message key="이메일" />

<fmt:message key="로그인메시지">
	<fmt:param>값1</fmt:param>
	<fmt:param>값2</fmt:param>
</fmt:message>



<%@ taglib prefix="fn" uri="jakarta.tags.function" %>
<c:set var="str" value="Apple,Mango,Orange" />
<c:set var="fruits" value="${fn:split(str,',')}" />
<c:forEach var="fruit" items="fruits">
	${fruit}
</c:forEach>

str2 : ${fn:join(fruits, "#")}


