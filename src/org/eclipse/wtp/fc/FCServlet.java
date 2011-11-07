package org.eclipse.wtp.fc;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FCServlet extends HttpServlet implements Servlet {
	private static final int ERROR = 0;
	private static final int SUCCESS = 1;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String todo = request.getParameter("todo");
		MsgList msgs = new MsgList();
		
		Collection myCollection = (Collection)request.getSession(true).getAttribute("collection");
		
		if (myCollection == null) {
			myCollection = new Collection();
			myCollection.loadData();
		}
		
		if (todo.equals("newCard")) {
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String category = request.getParameter("category");

			if (title.equals("") && description.equals("")) {
				msgs.addMsg("Please provide a title", Msg.ERROR);
				msgs.addMsg("Please provide a description", Msg.ERROR);
			} else if (title.equals("") && !description.equals("")) {
				msgs.addMsg("Please provide a title", Msg.ERROR);
			} else if (description.equals("") && !title.equals("")) {
				msgs.addMsg("Please provide a description", Msg.ERROR);
			} else {
				Card newCard = new Card(title, description);
				myCollection.newCard(category, newCard);
				myCollection.saveData();
				msgs.addMsg("Card created", Msg.SUCCESS);
			}
		} else if (todo.equals("newCategory")) {
			String title = request.getParameter("title");
			if (title.length() <= 25) {
				Category newCategory = new Category(title);
				
				if (myCollection.numCategories() == 0)
					request.getSession().setAttribute("category", newCategory);
				
				if (!myCollection.exists(newCategory)) {
					if ((!title.equals(""))) {					
						myCollection.addCategory(newCategory);
						msgs.addMsg("Category created", Msg.SUCCESS);
					} else {
						msgs.addMsg("Please provide a title", Msg.ERROR);
					}
				} else {
					msgs.addMsg("Category already exists", Msg.ERROR);
				}
			} else {
				msgs.addMsg("Category title cannot exceed 25 characters", Msg.ERROR);
			}
		} else if (todo.equals("removeCard")) {
			int catID = Integer.parseInt(request.getParameter("catID"));
			int cardID = Integer.parseInt(request.getParameter("cardID"));	
			
			Category category = myCollection.getCategory(catID);
			
			category.removeCard(cardID);
			msgs.addMsg("Card removed", Msg.SUCCESS);
			
			request.getSession().setAttribute("card", null);			
		} else if (todo.equals("removeCategory")) {
			int catID = Integer.parseInt(request.getParameter("catID"));
			Category category = (Category)request.getSession().getAttribute("category");
			
			myCollection.removeCategory(catID);
			
			if (catID == category.getID())
				if (myCollection.numCategories() == 0)
					request.getSession().setAttribute("category", null);
				else if (catID <= myCollection.numCategories())
					request.getSession().setAttribute("category", myCollection.getCategory(catID));
				else
					request.getSession().setAttribute("category", myCollection.getCategory(catID-1));
			
			msgs.addMsg("Category removed", Msg.SUCCESS);
			
			request.getSession().setAttribute("card", null);
		} else if (todo.equals("save")) {
			myCollection.saveData();
		} else if (todo.equals("load")) {
			myCollection.loadData();
			if (myCollection.numCategories() > 0) {
				request.getSession().setAttribute("category", myCollection.getCategory(1));
			}
			request.getSession().setAttribute("hasLoaded", true);
		} else if (todo.equals("reset")) {
			myCollection = new Collection();
			request.getSession().setAttribute("collection", myCollection);
			request.getSession().setAttribute("category", null);
			request.getSession().setAttribute("card", null);
			msgs.addMsg("Data reset", Msg.SUCCESS);
		} else if (todo.equals("editCard")) {
			String newTitle = request.getParameter("title");
			String newDesc = request.getParameter("description");
			int cardID = Integer.parseInt(request.getParameter("cardID"));
			int catID = Integer.parseInt(request.getParameter("catID"));
			
			Category cat = myCollection.getCategory(catID);
			Card card = cat.getCard(cardID);
			
			card.setTitle(newTitle);
			card.setDescription(newDesc);
			
			request.getSession().setAttribute("card", card);
			request.getSession().setAttribute("editCard", null);
			
			msgs.addMsg("Card updated", Msg.SUCCESS);
		}
		
		myCollection.saveData();
		request.getSession().setAttribute("msgs", msgs);
		request.getSession().setAttribute("collection", myCollection);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Collection myCollection = (Collection)request.getSession(true).getAttribute("collection");
		String cardString =  request.getParameter("card");
		MsgList msgs = new MsgList();
		String viewString = request.getParameter("view");
		String todo = request.getParameter("todo");
		int view = 0;
		if (viewString != null)
			view = Integer.parseInt(viewString);
		
		if (todo != null) {
			if (todo.equals("resetData")) {
				System.out.println("Reset Data");
				myCollection = new Collection();
				request.getSession().setAttribute("collection", myCollection);
				request.getSession().setAttribute("category", null);
				request.getSession().setAttribute("card", null);
				msgs.addMsg("Data reset", Msg.SUCCESS);
				myCollection.saveData();
			}
		}
		

		if (myCollection == null) {
			myCollection = new Collection();
			myCollection.loadData();
		}

		Category category = myCollection.getCategory(view);
		request.getSession().setAttribute("category", category);
		request.getSession().setAttribute("msgs", msgs);
		request.getSession().setAttribute("editCard", null);
		request.getSession().setAttribute("testCard", null);
		
		if (todo != null) {
			if (todo.equals("testCard")) {
				request.getSession().setAttribute("testCard", category.randomCard());
			}
		}
		
		if (cardString != null) {
			int cardID = Integer.parseInt(cardString);
			try {
				Card card = category.getCard(cardID);
				request.getSession().setAttribute("card", card);
				
				if (todo != null) {
					if (todo.equals("edit"))
						request.getSession().setAttribute("editCard", true);
				}
			} catch (NullPointerException e) {
				System.err.println(e);
			}
		} else {
			request.getSession().setAttribute("card", null);
		}
		
		getServletContext().getRequestDispatcher("/index.jsp?view=" + view).forward(request, response);	
	}
}
