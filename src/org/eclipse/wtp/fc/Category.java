package org.eclipse.wtp.fc;

import java.util.*;
import java.io.*;

public class Category implements Serializable {
	private static final long serialVersionUID = 2;
	
	private int id;
	private String title;
	private List<Card> cards = new ArrayList<Card>();

	public Category(String title) {
		this.title = title;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public void addCard(Card newCard) {
		newCard.setID(numCards()+1);
		cards.add(newCard);
	}
	
	public Card randomCard() {
		if (numCards() > 0) {
			Random r = new Random();
			int index = Math.abs(r.nextInt(numCards()));
			return cards.get(index);
		} else {
			return null;
		}
	}
	
	public Card getCard(String title) {
		int i;
		for (i = 0; i < numCards(); i++) {
			Card temp = cards.get(i);
			if (temp.getTitle().equals(title))
					return temp;
		}
		return null;
	}
	
	public Card getCard(int id) {
		int i;
		for (i = 0; i < numCards(); i++) {
			Card temp = cards.get(i);
			if (temp.getID() == id)
					return temp;
		}
		return null;
	}
	
	public void removeCard(int id) {
		cards.remove(id-1);
		for (int i = id-1; i < numCards(); i++) {
			cards.get(i).setID(i+1);
		}
	}
	
	public int numCards() {
		return cards.size();
	}
}
