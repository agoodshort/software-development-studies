package ie.gmit.dip;

import java.util.Scanner;

public class Menu {
	private boolean keepRunning = true; // Used to shutdown the app
	public boolean goCode = false; // Used to do not display option menu when accessing showCode()

	private Scanner s;
	public Cypher c; // Has to be public to be shared with parser and keep the same code word
	private Parser p;

	/* ----------------------- Main Menu ----------------------- */

// Start
	public void start() throws Exception { // First launch of the program
		c = new Cypher(); // Initialize cypher
		p = new Parser(); // Initialize parser
		s = new Scanner(System.in); // Initialize a scanner

		showBanner(); // Shows Welcome message
		Thread.sleep(2000); // Wait 2 seconds

		while (keepRunning) { // Used to shutdown the app
			showOptions();
			selectOption();
		}
	}

// Banner
	private void showBanner() throws InterruptedException {
		System.out.println("");
		System.out.println("############################");
		System.out.println("#### A polybius Square  ####");
		System.out.println("####      Cypher        ####");
		System.out.println("############################");
		System.out.println("");
		System.out.println("############################");
		System.out.println("# This application encrypt #");
		System.out.println("# or decrypt files and     #");
		System.out.println("# texts using an ADFGVX    #");
		System.out.println("# cypher.                  #");
		System.out.println("############################");
		Thread.sleep(2000);
		System.out.println("");
		System.out.println("############################");
		System.out.println("# Code word has to be set  #");
		System.out.println("# before using any of the  #");
		System.out.println("# functions.               #");
		System.out.println("# Once set, your code word #");
		System.out.println("# won't be asked again.    #");
		System.out.println("# If you forget it or wish #");
		System.out.println("# to change it, go to      #");
		System.out.println("# Code word menu.          #");
		System.out.println("############################");
		Thread.sleep(2000);
		System.out.println("");
		System.out.println("############################");
		System.out.println("# Please visit settings    #");
		System.out.println("# for additional features. #");
		System.out.println("############################");
	}

// Main Menu
	private void showOptions() {
		System.out.println("");
		System.out.println("############################");
		System.out.println("#####    Main  Menu    #####");
		System.out.println("############################");
		System.out.println("");
		System.out.println("1) Code word");
		System.out.println("2) Encrypt");
		System.out.println("3) Decrypt");
		System.out.println("4) Settings");
		System.out.println("5) Exit");
		System.out.println("");
		System.out.print("Enter your selection => ");
	}

// Select Option Main Menu
	private void selectOption() throws Exception {
		try {
			int selection = Integer.parseInt(s.next());

			// Option 1
			// Code word menu
			if (selection == 1) {
				showCode();

				// Option 2
				// Encrypt
			} else if (selection == 2) {
				if (c.keywordSet) {
					showEncrypt();

					// If code word hasn't been set previously
				} else {
					System.out.println("");
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					System.out.println("!!!! CODE WORD NOT SET  !!!!");
					System.out.println("!!! Please set code word !!!");
					System.out.println("!!!         below        !!!");
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

					goCode = true;
					showCode();
					showEncrypt();
				}

				// Option 3
				// Decrypt
			} else if (selection == 3) {

				// If code has been set
				if (c.keywordSet) {
					showDecrypt();

					// If code word hasn't been set previously
				} else {
					System.out.println("");
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					System.out.println("!!!! CODE WORD NOT SET  !!!!");
					System.out.println("!!! Please set code word !!!");
					System.out.println("!!!         below        !!!");
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

					goCode = true;
					showCode(); // Go to code menu
					showDecrypt(); // Then go to Decrypt
				}

				// Option 4
				// Settings
			} else if (selection == 4) {
				settings();

				// Option 5
				// Exit
			} else if (selection == 5) {
				keepRunning = false;
				System.out.println("");
				System.out.println("############################");
				System.out.println("####  Thanks for using  ####");
				System.out.println("####    our services    ####");
				System.out.println("############################");
				Thread.sleep(2000);

				// User didn't enter a number between 1 and 5
			} else {
				System.out.println("");
				System.out.println("!!!!!! INVALID  INPUT !!!!!!");
				System.out.println("!!!!   Please enter a   !!!!");
				System.out.println("!!!! number from 1 to 5 !!!!");
				Thread.sleep(2000);
			}

			// catch if value in scanner is not an int
		} catch (NumberFormatException e) {
			System.out.println("");
			System.out.println("!!!!!! INVALID  INPUT !!!!!!");
			System.out.println("!!!!   Please enter a   !!!!");
			System.out.println("!!!! number from 1 to 5 !!!!");
			Thread.sleep(2000);
		}
	}

	/* ----------------------- Code Menu ----------------------- */

// Option 1 of Main Menu
	private void showCode() throws InterruptedException {
		boolean codeMenu = false;

		while (!codeMenu) {
			int codeSelection;

			if (!goCode) {
				System.out.println("");
				System.out.println("     ############################");
				System.out.println("     ########     Code    #######");
				System.out.println("     ############################");
				System.out.println("");
				System.out.println("     1) Set code word");
				System.out.println("     2) Get code word");
				System.out.println("     3) Main menu");
				System.out.println("");
				System.out.print("     Enter your selection => ");

				try { // If selection is not a number between 1 to 3, skip to else
					codeSelection = Integer.parseInt(s.next());
				} catch (NumberFormatException e) {
					codeSelection = 5; // Hardcoded to skip to else
				}

			} else {
				codeSelection = 1; // Hardcoded to skip the menu if goCode = true
			}

			// Selection input
			if (codeSelection == 1) { // Set code word
				System.out.println("");
				System.out.print("     Enter new code word => ");

				s = new Scanner(System.in); // Setting scanner for nextLine
				String kw = s.nextLine();
				c.setCode(kw); // Go to cypher > setCode

				if (!c.keywordSet) { // If code didn't match the pattern Cypher requires
					goCode = true;
				} else {
					codeMenu = true; // Stop While loop
					goCode = false;
					Thread.sleep(2000);
				}

			} else if (codeSelection == 2) { // Get code word
				codeMenu = true;
				System.out.println("");
				System.out.println("     Your code word is: " + c.getCode());
				Thread.sleep(2000);

			} else if (codeSelection == 3) { // Go to Main menu
				codeMenu = true;
				System.out.println("");
				System.out.println("     Going back to Main menu");
				Thread.sleep(2000);

			} else { // User didn't enter a correct option
				System.out.println("");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("     !!!!!! INVALID  INPUT !!!!!!");
				System.out.println("     !!!!   Please enter a   !!!!");
				System.out.println("     !!!! number from 1 to 3 !!!!");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				Thread.sleep(2000);
			}
		}
	}

	/* --------------------- Encrypt Menu --------------------- */

// Option 2 of Main Menu
	private void showEncrypt() throws Exception {
		boolean encryptMenu = false;

		while (!encryptMenu) {
			int encryptSelection;
			System.out.println("");
			System.out.println("     ############################");
			System.out.println("     #######  Encrypting   ######");
			System.out.println("     ############################");
			System.out.println("");
			System.out.println("     1) Encrypting text");
			System.out.println("     2) Encrypting a file");
			System.out.println("     3) Main menu");
			System.out.println("");
			System.out.print("     Enter your selection => ");

			try { // If selection is not a number between 1 to 3, skip to else
				encryptSelection = Integer.parseInt(s.next());
			} catch (NumberFormatException e) {
				encryptSelection = 5; // Hardcoded to skip to else
			}

			// Get text to encrypt
			if (encryptSelection == 1) {
				encryptMenu = true;
				System.out.println("");
				System.out.print("     Enter the text to encrypt => ");

				s = new Scanner(System.in); // Setting scanner for nextLine
				String plainText = s.nextLine();

				System.out.println("");
				System.out.print("     Your encrypted text is: " + c.encryptText(plainText));
				System.out.println("");
				Thread.sleep(2000);

				// Get file to encrypt
			} else if (encryptSelection == 2) {
				encryptMenu = true;
				c.fileEncryption = true;

				System.out.println("");
				System.out.println("     Please navigate to the file you would like to encrypt?");
				System.out.println("     Make use of 'return' to go one directory up.");

				System.out.println("");

				Thread.sleep(2000);
				p.where();

				while (!p.fileLocationSet) {
					System.out.println("");
					System.out.print("     Navigate to your file by entering folder name => ");
					s = new Scanner(System.in);
					String fileLocation = s.nextLine();
					System.out.println("");
					p.visitAndCypher(fileLocation, c, 1);
				}
				c.fileEncryption = false;
				p.fileLocationSet = false; // Reset
				Thread.sleep(2000);

			} else if (encryptSelection == 3) { // Go to Main menu
				encryptMenu = true;
				System.out.println("");
				System.out.println("     Going back to Main menu");
				Thread.sleep(2000);

			} else { // User didn't enter a correct option
				System.out.println("");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("     !!!!!! INVALID  INPUT !!!!!!");
				System.out.println("     !!!!   Please enter a   !!!!");
				System.out.println("     !!!! number from 1 to 3 !!!!");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				Thread.sleep(2000);
			}
		}
	}

	/* --------------------- Decrypt Menu ---------------------- */

// Option 3 of the Main Menu
	private void showDecrypt() throws Exception {
		boolean decryptMenu = false;

		while (!decryptMenu) {
			int decryptSelection;
			System.out.println("");
			System.out.println("     ############################");
			System.out.println("     #######  Decrypting   ######");
			System.out.println("     ############################");
			System.out.println("");
			System.out.println("     1) Decrypting text");
			System.out.println("     2) Decrypting a file");
			System.out.println("     3) Main menu");
			System.out.println("");
			System.out.print("     Enter your selection => ");

			try { // If selection is not a number between 1 to 3, skip to else
				decryptSelection = Integer.parseInt(s.next());
			} catch (NumberFormatException e) {
				decryptSelection = 5;
			}

			// Get text to decrypt
			if (decryptSelection == 1) {
				decryptMenu = true;
				System.out.println("");
				System.out.print("     Enter the text to decrypt => ");

				s = new Scanner(System.in); // Reset scanner to allow the use of nextLine
				String encryptedText = s.nextLine();

				System.out.println("");
				System.out.print("     Your decrypted text is: " + c.decryptText(encryptedText));
				System.out.println("");
				Thread.sleep(2000);

				// Get file to decrypt
			} else if (decryptSelection == 2) {
				decryptMenu = true;
				c.fileEncryption = true;

				System.out.println("");
				System.out.println("     Please navigate to the file you would like to decrypt.");
				System.out.println("     Make use of return to go one directory up.");
				System.out.println("");

				Thread.sleep(2000);
				p.where();

				while (!p.fileLocationSet) {
					System.out.println("");
					System.out.print("     Navigate to your file by entering folder name => ");
					s = new Scanner(System.in); // Reset scanner to allow the use of nextLine
					String fileLocation = s.nextLine();
					System.out.println("");
					p.visitAndCypher(fileLocation, c, 2);
				}
				c.fileEncryption = false;
				p.fileLocationSet = false; // Reset
				Thread.sleep(2000);

				// Go to Main menu
			} else if (decryptSelection == 3) {
				decryptMenu = true;
				System.out.println("");
				System.out.println("     Going back to Main menu");
				Thread.sleep(2000);

				// User didn't enter a correct option
			} else {
				System.out.println("");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("     !!!!!! INVALID  INPUT !!!!!!");
				System.out.println("     !!!!   Please enter a   !!!!");
				System.out.println("     !!!! number from 1 to 3 !!!!");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				Thread.sleep(2000);
			}
		}
	}

	/* --------------------- Settings Menu ---------------------- */

	private void settings() throws Exception {
		int settingsSelection;
		System.out.println("");
		System.out.println("     ############################");
		System.out.println("     #######   Settings    ######");
		System.out.println("     ############################");
		System.out.println("");
		System.out.println("     1) Enable/Disable auto-copy to clipboard");
		System.out.println("     2) Amount of column swapped");
		System.out.println("     3) Main menu");
		System.out.println("");
		System.out.print("     Enter your selection => ");

		try { // If selection is not a number between 1 to 3, skip to else
			settingsSelection = Integer.parseInt(s.next());
		} catch (NumberFormatException e) {
			settingsSelection = 5;
		}

		// Auto-copy to clipboard
		if (settingsSelection == 1) {
			if (c.copyEnabled) { // Set variable and will be checked while encrypting
				c.copyEnabled = false;
				System.out.println("");
				System.out.println("     Auto-copy has been disabled.");
				Thread.sleep(2000);
				System.out.println("");
			} else if (!c.copyEnabled) {
				c.copyEnabled = true;
				System.out.println("");
				System.out.println("     Auto-copy has been enabled.");
				Thread.sleep(2000);
				System.out.println("");
			}

			// Set amount of column to swap
		} else if (settingsSelection == 2) {
			// Disclaimer if swap number has already been set
			if (c.codeWordSwapSet) {
				System.out.println("     Swap number is now set to " + c.rowSwap);
				System.out.println("     Please edit it carefully.");
				Thread.sleep(2000);
			}

			c.codeWordSwapSet = false; // reset to allow editing

			if (!c.keywordSet) { // If keyword is not set, redirect to set Code Word
				System.out.println("");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("     !!!!    Code Word has   !!!!");
				System.out.println("     !!!!  to be set before  !!!!");
				System.out.println("     !!!!  the swap number.  !!!!");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("");

				goCode = true;
				showCode(); // Redirect to set CodeWord
			}

			System.out.println("");
			System.out.println("     Please set the amount of column you would like the cypher to swap");
			System.out.println("     when transposing the last table.");
			System.out.println("     More info in README.txt");
			System.out.println("");
			Thread.sleep(2000);

			while (!c.codeWordSwapSet) { // Loop until correct number is set
				System.out.print("     Enter your desired swap number => ");
				int rowSwapTent = Integer.parseInt(s.next());
				c.setCodeWordSwap(rowSwapTent);
			}
			Thread.sleep(2000);
			System.out.println("");

		} else if (settingsSelection == 3) {
			System.out.println("");
			System.out.println("     Going back to Main menu");
			Thread.sleep(2000);
		} else {
			System.out.println("");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("     !!!!!! INVALID  INPUT !!!!!!");
			System.out.println("     !!!!   Please enter a   !!!!");
			System.out.println("     !!!! number from 1 to 3 !!!!");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			Thread.sleep(2000);
			
			settings(); //Restart settings menu
		}
	}
}