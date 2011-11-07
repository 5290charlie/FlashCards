<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlashCards!</title>
<link href="jqueryUI.css" rel="stylesheet" type="text/css" />
<link href="stylesheet.less" rel="stylesheet/less" type="text/css" />
<script src="less.js" type="text/javascript"></script>
<script src="jquery.js" type="text/javascript"></script>
<script src="jqueryUI.js" type="text/javascript"></script>
<script src="functions.js" type="text/javascript"></script>
</head>
<body>
	<div onclick="clearMsgs()" id="msgs">
		<c:forEach items="${msgs.fetchAll()}" var="msg">
			<p class="<c:out value="${msg.findClass()}" />">
				<c:out value="${msg.getMsg()}" />
			</p>
		</c:forEach>
 	</div>
	<div id="header">
		<div class="title" onclick="window.location='/FlashCards/index.jsp'">
			FlashCards
		</div>
		 
		<div class="options">
		<c:choose>
			<c:when test="${hasLoaded != null}">
				<input type="hidden" id="hasLoaded" value="yes" />
				<form id="resetData" method="post" action="/FlashCards/MainWindow">
					<input type="hidden" name="todo" value="reset" />
					<span id="resetDataSubmit">
						<span onclick="confirmResetData()" class="button">Reset</span>
					</span>
				</form>
			</c:when>
			<c:otherwise>
				<input type="hidden" id="hasLoaded" value="no" />
				<form id="loadData" method="post" action="/FlashCards/MainWindow">
					<input type="hidden" name="todo" value="load" />
				</form>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
	<c:if test="${hasLoaded != null}">
		<div id="container">
			<div id="sidebar">
				<form title="Create Category" class="creation" id="createCategory" method="post" action="/FlashCards/MainWindow">
					<input type="hidden" name="todo" value="newCategory" />
					<input class="input" placeholder="Title" name="title" type="text" />
					<input class="submit" type="submit" value="Create Category" />
				</form>
				<h1>Categories</h1>
				<c:choose>
					<c:when test="${collection.numCategories() > 0}">
						<div id="accordion">
							<c:forEach items="${collection.getCategories()}" var="cat">
								<h3 onclick="window.location='/FlashCards/MainWindow?view=<c:out value="${cat.getID()}" />'"><a href="#"><c:out value="${cat.getTitle()}" /></a></h3>
								<div>
									<span class="info"><c:out value="${cat.numCards()}" /> card(s)</span>
									<form id="removeCategory-<c:out value="${cat.getID()}" />" method="post" action="/FlashCards/MainWindow">
										<input type="hidden" name="todo" value="removeCategory" />
										<input type="hidden" name="catID" value="<c:out value="${cat.getID()}" />" />
										<br />
										<span id="removeCategorySubmit-<c:out value="${cat.getID()}" />">
											<span onclick="confirmRemoveCat(<c:out value="${cat.getID()}" />);" class="button">Remove Category</span>
										</span>
									</form>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						No Categories
					</c:otherwise>
				</c:choose>
				<br /><br />
				<a onclick="createCat()" class="button">Create Category</a>
			</div>
			<div id="content">
				<c:if test="${category != null}">
					<ul>
						<li><a href="#editCards">
							<c:choose>
								<c:when test="${testCard != null}">
									<c:out value="${category.getTitle()}" /> &raquo; Flash Me
								</c:when>
								<c:otherwise>
									<c:out value="${category.getTitle()}" /> &raquo; Cards
								</c:otherwise>
							</c:choose>
						</a></li>
						<c:if test="${card == null}">
						</c:if>
					</ul>
					<div id="editCards">
						<input type="hidden" id="cid" name="cid" value="<c:out value="${category.getID()}" />" />
						<c:choose>
							<c:when test="${card != null}">
								<form id="removeCard-<c:out value="${card.getID()}" />" method="post" action="/FlashCards/MainWindow">
									<input type="hidden" name="todo" value="removeCard" />
									<input type="hidden" name="catID" value="<c:out value="${category.getID()}" />" />
									<input type="hidden" name="cardID" value="<c:out value="${card.getID()}" />" />
									<a class="button" href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />">&laquo; Back</a>
									<c:choose>
										<c:when test="${editCard != null}">	
											<span class="button" onclick="submitEditCard()">Save Card</span>
										</c:when>
										<c:otherwise>
											<a class="button" href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />&card=<c:out value="${card.getID()}" />&todo=edit">Edit Card</a>
										</c:otherwise>
									</c:choose>
									
									<span id="removeCardSubmit-<c:out value="${card.getID()}" />">
										<span onclick="confirmRemoveCard(<c:out value="${card.getID()}" />);" class="button">Remove Card</span>
									</span>		
								</form>
								<hr>
								<c:if test="${editCard != null}">
									<form id="editCard" method="post" action="/FlashCards/MainWindow">
										<input type="hidden" name="todo" value="editCard" />
										<input type="hidden" name="cardID" value="<c:out value="${card.getID()}" />" />
										<input type="hidden" name="catID" value="<c:out value="${category.getID()}" />" />
								</c:if>
								<div class="answercard" id="title">
									<h2>Title</h2>
									<c:choose>
										<c:when test="${editCard != null}">
											<input name="title" value="<c:out value="${card.getTitle()}" />" />
										</c:when>
										<c:otherwise>
											<c:out value="${card.getTitle()}" />
										</c:otherwise>
									</c:choose>
								</div>
								<hr>
								<div class="answercard" id="description">
									<h2>Description</h2>
									<c:choose>
										<c:when test="${editCard != null}">
											<textarea name="description"><c:out value="${card.getDescription()}" /></textarea>
										</c:when>
										<c:otherwise>
											<c:out value="${card.getDescription()}" />
										</c:otherwise>
									</c:choose>
								</div>
								<c:if test="${editCard != null}">
									</form>
								</c:if>
								<c:if test="${editCard == null}">
									<hr>
									<div class="cardNav">
										<c:if test="${card.getID() > 1}">
											<a class="prev" href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />&card=<c:out value="${card.getID()-1}" />">&laquo; last card</a>
										</c:if>
										<c:if test="${card.getID() < category.numCards()}">
											<a class="next" href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />&card=<c:out value="${card.getID()+1}" />">next card &raquo;</a>
										</c:if>
										<div>
										Card <c:out value="${card.getID()}" /> of <c:out value="${category.numCards()}" />
										</div>
									</div>
								</c:if>
							</c:when>
							<c:when test="${testCard != null}">
								<span class="testShow">
									<a class="button" href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />&todo=testCard">Next Card &raquo;</a>
								</span>
								<a class="button left" href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />">&laquo; Back</a>
								<div class="clear"></div>
								<hr>
								<div id="hint">click card to show description</div>
								<div title="Show Description" onclick="showDescription()" class="answercard clickable" id="testTitle">
									<h2>Title</h2>
									<c:out value="${testCard.getTitle()}" />
								</div>
								<div title="Show Title" onclick="showTitle()" class="answercard clickable" id="testDesc">
									<h2>Description</h2>
									<c:out value="${testCard.getDescription()}" />
								</div>
							</c:when>
							<c:otherwise>
								<a class="button" onclick="newCard()">New Card</a>
								<a class="button" href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />&todo=testCard">Flash Me!</a>
								<form title="New Card" class="creation" id="createCard" method="post" action="/FlashCards/MainWindow">
									<input type="hidden" name="todo" value="newCard" />
									<input type="hidden" name="category" value="${category.getTitle()}" />
									<input class="input" placeholder="Title" name="title" type="text" /><br />
									<textarea class="input" placeholder="Description" name="description"></textarea>
									<input class="submit" type="submit" value="Create Card" />
								</form>
								<hr>
								<c:choose>
									<c:when test="${category.numCards() > 0}">
										<c:forEach items="${category.getCards()}" var="card">
											<a href="/FlashCards/MainWindow?view=<c:out value="${category.getID()}" />&card=<c:out value="${card.getID()}" />">
												<div class="card">
													<c:out value="${card.getTitleShort()}" />
												</div>
											</a>
										</c:forEach>
										<div class="clear"></div>
									</c:when>
									<c:otherwise>
									<div style="text-align:center;">
										No Cards
									</div>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<hr>
						<div class="footer">
							A Charlie McClung Production<br />
							CSCI 3308: Software Engineering Methods & Tools<br />
							[Iteration 3]
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</c:if>
</body>
</html>