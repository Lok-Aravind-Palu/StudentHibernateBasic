package com.practice.hibernate.HibetnateExample;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		HibernateConfiguration hc = new HibernateConfiguration();
		HibernateConfiguration.connectDB();

		for (;;) {
			System.out.println(
					"Enter 1: to save \n2: to Read \n3:Read from the select table \n4:to update\n5:to delete \n6 :drop a table  ");
			switch (s.nextInt()) {
			case 1:
				hc.insertValues();
				break;
			case 2:
				hc.readFromTable();
				break;
			case 3:
				hc.readSelectedFromTable();
				break;
			case 4:
				hc.updateToTable();
				break;
			case 5:
				hc.deleteFromTable();
				break;
			case 6:
				hc.dropTable();
				break;
			default:
				hc.disconnect();
				System.exit(0);
			}
		}

	}

}
