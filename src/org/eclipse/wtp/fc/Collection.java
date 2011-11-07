package org.eclipse.wtp.fc;

import java.util.*;
import java.io.*;

public class Collection {	
	public List<Category> categories = new ArrayList<Category>();
	
	public void addCategory(Category newCategory) {
		newCategory.setID(numCategories()+1);
		categories.add(newCategory);
	}
	
	public void saveData() {
		writeToFile(categories, "categories");
	}
	
	public void loadData() {
		this.categories = (List<Category>) readFromFile("categories");
	}
	
	public int numCategories() {
		return categories.size();
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
	public boolean exists(Category cat) {
		int i;
		for (i = 0; i < numCategories(); i++) {
			Category temp = categories.get(i);
			if (temp.getTitle().equals(cat.getTitle()))
					return true;
		}
		return false;
	}
	
	public Category getCategory(String title) {
		int i;
		for (i = 0; i < numCategories(); i++) {
			Category temp = categories.get(i);
			if (temp.getTitle().equals(title))
				return temp;
		}
		return null;
	}
	
	public Category getCategory(int id) {
		int i;
		for (i = 0; i < numCategories(); i++) {
			Category temp = categories.get(i);
			if (temp.getID() == id)
				return temp;
		}
		return null;
	}
	
	public void removeCategory(int id) {
		categories.remove(id-1);
		for (int i = id-1; i < numCategories(); i++) {
			categories.get(i).setID(i+1);
		}
	}
	
	public void newCard(String category, Card card) {
		int i;
		for (i = 0; i < numCategories(); i++) {
			Category temp = categories.get(i);
			if (temp.getTitle().equals(category))
				temp.addCard(card);
		}
	}
	
	//given an object and a filename you can serialize java objects 
	//to an output file
	private static void writeToFile(Object o, String fileName) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try{
			fos = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(o);
			out.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
		

	//reads in a given file and then you can just cast it to whatever you know
	//exists in that file
	private static Object readFromFile(String fileName){
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try{
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			Object o = in.readObject();
			in.close();
			return o;
		}
		catch (IOException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
