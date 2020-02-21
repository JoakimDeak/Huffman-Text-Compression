package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		showBanner();
		showStartMenu();

	}

	private static boolean fileExists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	private static void showCompressMenu() {
		Scanner sc = new Scanner(System.in);
		//showBanner();
		System.out.println("Enter name of the file you wish to compress");
		System.out.println("Type exit to return to the main menu");

		boolean exit = false;
		while (!exit) {
			System.out.print("File name: ");
			String input = sc.next();
			if (input.equals("exit")) {
				exit = true;
				showBanner();
				showStartMenu();
			} else {
				if (fileExists(input)) {
					System.out.println("File found");
					System.out.println("Compressing");
					compress(input);
					exit = true;
				} else {
					System.out.println("File does not exist");
					System.out.println("Remember to include file ending and proper case");
				}
			}

		}
		sc.close();
	}

	private static void compress(String fileName) {
		Encoder e;
		try {
			e = new Encoder(fileName);
			e.encode();
			System.out.println("Compression successful");

			showBanner();
			showStartMenu();
		} catch (FileNotFoundException e1) {

		}
	}

	private static void showUncompressMenu() {
		Scanner sc = new Scanner(System.in);
		//showBanner();
		System.out.println("Enter name of the file you wish to uncompress");
		System.out.println("Type exit to return to the main menu");
		boolean exit = false;
		while (!exit) {
			System.out.print("File name: ");
			String input = sc.next();
			if (input.equals("exit")) {
				exit = true;
				showBanner();
				showStartMenu();
			} else {
				if (fileExists(input)) {
					System.out.println("File found");
					System.out.println("Uncompressing");
					uncompress(input);
					exit = true;
				} else {
					System.out.println("File does not exist");
					System.out.println("Remember to include file ending and proper case");
				}
			}

		}
		sc.close();
	}

	private static void uncompress(String fileName) {
		Decoder d = new Decoder();
		try {
			d.decode(fileName);
			System.out.println("Uncompression successful");
			
			showBanner();
			showStartMenu();
		} catch (FileNotFoundException e) {

		}
	}

	private static void errorMessage() {
		System.out.println("Invalid input");
		System.out.println();
	}

	private static void showStartMenu() {

		Scanner sc = new Scanner(System.in);

		System.out.println("1: Compress");
		System.out.println("2: Uncompress");
		System.out.println("3: Exit");
		System.out.println();
		System.out.print("choose operation: ");
		boolean selected = false;

		while (!selected) {
			String input = "";
			input = sc.next();

			switch (input) {
			case "1":
				showCompressMenu();
				selected = true;
				break;
			case "2":
				showUncompressMenu();
				selected = true;
				break;
			case "3":
				System.out.println("Thank you for using the program");
				selected = true;
				break;
			default:
				errorMessage();
				showStartMenu();
				break;
			}
		}
		sc.close();
	}

	private static void showBanner() {
		System.out.println("******************************************************");
		System.out.println("**                Huffman Compressor                **");
		System.out.println("******************************************************");
		System.out.println();
	}
}