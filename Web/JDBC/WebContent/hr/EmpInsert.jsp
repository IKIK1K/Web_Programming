<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="lab.web.model.*" import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 정보 ${message}</title>
</head>
<body>
<h1>사원 정보 ${message}</h1>
<form action="/JDBC/Emp.do" method=post>
<input type=hidden name=action value="${action}">
<table border=1>
<tr>
<td>사원번호</td><td><input type=text name=empId value="${emp.employeeId}"></td>
</tr>
<tr>
<td>이름, 성</td><td><input type=text name=firstName value="${emp.firstName}">
<input type=text name=lastName value="${emp.lastName}"></td>
</tr>
<tr>
<td>이메일</td><td><input type=text name=email value="${emp.email}"></td>
</tr>
<tr>
<td>연락처</td><td><input type=text name=phoneNumber value="${emp.phoneNumber}"></td>
</tr>
<tr>
<td>입사일</td><td><input type=date name=hireDate value="${emp.hireDate}"></td>
</tr>
<tr>
<td>직무</td><td><Select name=jobId>
<c:forEach var="job" items="${jobList}">
<option value="${job.jobId}" ${emp.jobId eq job.jobId ? "selected" : ""}>${job.jobTitle}
</c:forEach>
</Select></td>
</tr>
<tr>
<td>급여</td><td><input type=text name=salary value="${emp.salary}"></td>
</tr>
<tr>
<td>보너스율</td><td><input type=number min=0 max=0.99 step=0.05
name=commissionPct value="${emp.commissionPct}"></td>
</tr>
<tr>
<td>매니저</td><td><select name=managerId>
<c:forEach var="man" items="${manList}">
<option value="${man.employeeId}" ${emp.managerId eq man.employeeId ? "selected" : ""}>${man.firstName}
</c:forEach>
</select></td>
</tr>
<tr>
<td>부서</td><td><select name=departmentId>
<c:forEach var="dept" items="${deptList}">
<option value="${dept.departmentId}" ${dept.departmentId eq emp.departmentId ? "selected" : ""}>${dept.departmentName}
</c:forEach>
</select></td></tr>
<tr><td colspan=2>
<input type=submit value="${message}"><input type=reset value=취소>
</td></tr>
</table>
</form>
</body>
</html>











