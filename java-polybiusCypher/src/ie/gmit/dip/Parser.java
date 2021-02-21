package ie.gmit.dip;

import java.io.*;

public class Parser {
	public boolean fileLocationSet = false;
	private File f;
	private File[] children;

	/* ----------------------- Find the file ----------------------- */

// Set the research in the home directory of the user
	public void where() {
		f = new File(System.getProperty("user.home")); // Location of the home directory of the user

		// Add a tab (“\t”) for each level of depth in the file system
		System.out.println("     " + f); // Output the name of the location
		System.out.println("");

		displayFolder();
	}

// Navigate through the file system
// Will decrypt or encrypt if a file is provided
	public void visitAndCypher(String fileLocationName, Cypher c, int whatToDo) throws Exception {
		if (fileLocationName.equals("return")) {
			String fileLocation = f.getParent();
			f = new File(fileLocation);

			System.out.println("     " + f);
			System.out.println("");

			displayFolder();

		} else {
			boolean locationFound = false; // Used for error message below

			// File location provided is correct
			for (int j = 0; j < children.length; j++) {
				if (fileLocationName.equals(children[j].getName())
						|| fileLocationName.equals(children[j].getName() + System.getProperty("file.separator"))) {

					locationFound = true;
					String fileLocation = f + System.getProperty("file.separator") + fileLocationName;
					f = new File(fileLocation);

					System.out.println("     " + f);
					System.out.println("");

					// Only look for children in directories.
					if (f.isDirectory()) {
						displayFolder(); // Loop over all the directories and files in the current directory

						// Encrypt
					} else if (f.isFile() && whatToDo == 1) {
						this.fileLocationSet = true;
						encryptFile(f, c);

						// Decrypt
					} else if (f.isFile() && whatToDo == 2) {
						this.fileLocationSet = true;
						decryptFile(f, c);
					}
				}
			}

			// No file matched the file location provided
			if (!locationFound) {
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("     !!!!!! INVALID  INPUT !!!!!!");
				System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("");
				System.out.println("     " + f);
				System.out.println("");

				displayFolder();
			}
		}
	}

	// Loop over all the directories and files in the current directory
	private void displayFolder() {
		children = f.listFiles();
		int choice = 1;

		// Displays folders and file with numbers in front of them
		for (int i = 0; i < children.length; i++) {
			if (!children[i].isHidden() && children[i].isDirectory()) {
				System.out.print("\t");
				System.out.println(
						"     " + choice + ") " + children[i].getName() + System.getProperty("file.separator"));
				choice += 1;

			} else if (!children[i].isHidden() && !children[i].isDirectory() && children[i].isFile()) {
				System.out.print("\t");
				System.out.println("     " + choice + ") " + children[i].getName());
				choice += 1;
			}
		}
	}

	/* ----------------------- Encrypt the file ----------------------- */

	private void encryptFile(File fileLocation, Cypher c) throws Exception {
		System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("     !!!!!!   ENCRYPTING   !!!!!!");
		System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation)));
		FileWriter out = new FileWriter(fileLocation + ".enc"); // Specify the output file
		String next; // Use buffering to read a line of text at a time from the file
		String nextEncrypted;
		while ((next = br.readLine()) != null) {
			nextEncrypted = c.encryptText(next);
			out.write(nextEncrypted); // Write out the next line of text to the character stream
			out.write("\n"); // Add a line break after each line of text!
		}

		System.out.println("");
		System.out.println("     Your encrypted file is: " + fileLocation + ".enc");

		// Clean up
		out.flush();
		out.close();
		br.close();
	}

	/* ----------------------- Decrypt the file ----------------------- */

	private void decryptFile(File fileLocation, Cypher c) throws Exception {
		System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("     !!!!!!   DECRYPTING   !!!!!!");
		System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation)));
		FileWriter out = new FileWriter(fileLocation + ".dec"); // Specify the output file
		String next; // Use buffering to read a line of text at a time from the file
		String nextDecrypted;
		while ((next = br.readLine()) != null) {
			nextDecrypted = c.decryptText(next);
			out.write(nextDecrypted); // Write out the next line of text to the character stream
			out.write("\n"); // Add a line break after each line of text!
		}
		System.out.println("");
		System.out.println("     Your decrypted file is: " + fileLocation);

		// Clean up
		out.flush();
		out.close();
		br.close();
	}
}