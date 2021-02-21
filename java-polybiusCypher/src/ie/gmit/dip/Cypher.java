package ie.gmit.dip;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

public class Cypher {

	/*
	 * ---------------------------- Variable definition ----------------------------
	 */
	// Variable accessible from Menu
	public boolean keywordSet;
	public boolean copyEnabled = false;
	public boolean fileEncryption = false;
	public boolean codeWordSwapSet = false;

	// Variables needed to encrypt
	private char[] keyword;
	private int rowPos;
	private int colPos;
	private int kwCoef;
	private int encryptTextStep;
	private int keywordMatrixRow;
	public int rowSwap = 3;

	// Variables needed to decrypt
	private int keywordUsedMatrixRow;

	// Polybius square and cypher
	private char[] cypher = { 'A', 'D', 'F', 'G', 'V', 'X' };
	private char[][] matrix = { { 'P', 'H', '0', 'Q', 'G', '6' }, { '4', 'M', 'E', 'A', '1', 'Y' },
			{ 'L', '2', 'N', 'O', 'F', 'D' }, { 'X', 'K', 'R', '3', 'C', 'V' }, { 'S', '5', 'Z', 'W', '7', 'B' },
			{ 'J', '9', 'U', 'T', 'I', '8' } };

	/* ------------- Encrypting / Decrypting methods ---------------- */

//Encrypt text entered
	public String encryptText(String plainText) {
		char[][] step1 = createMatrix(plainText);
		char[][] step2 = transposeKeywordMatrix(step1);
		return readTransposedMatrix(step2);
	}

//Decrypt text entered	
	public String decryptText(String encryptedText) {
		char[][] step1 = readEncryptedText(encryptedText, keyword);
		char[][] step2 = transposeEncryptedMatrix(step1, keyword);
		return createText(step2);
	}

	/* ------------------- Encrypting sub-methods ---------------------- */

//Create Matrix
	// sub-method of encrpytText
	private char[][] createMatrix(String plainText) {
		char[] plainTextUp = plainText.toUpperCase().toCharArray();

		// Amount of row needed in the keyword matrix
		keywordMatrixRow = (plainTextUp.length) / ((keyword.length) / 2) + 1;

		// Create the keyword matrix
		char[][] keywordMatrix = new char[keywordMatrixRow][(keyword.length)];

		kwCoef = 1;

		// Might try to use for (encrpytTextStep : plainTextUp)
		for (encryptTextStep = 0; encryptTextStep < plainTextUp.length; encryptTextStep++) { 
			boolean keywordMatrixSet = false; //Allow to either add a letter or a .
			rowPos = encryptTextStep / ((keyword.length) / 2);
			colPos = ((encryptTextStep % (keyword.length)) + encryptTextStep);

			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 6; col++) {

					// If letter is found in the matrix
					if (plainTextUp[encryptTextStep] == matrix[row][col]) {
						getColPos();

						//Add encoded character into the table
						keywordMatrix[rowPos][colPos] = cypher[row];
						keywordMatrix[rowPos][colPos + 1] = cypher[col];
						keywordMatrixSet = true; // Set to true to skip following if statement
					}

					// If letter is not found add a space in the matrix
					if (keywordMatrixSet == false && row == 5 && col == 5) {
						getColPos();

						//Add . into the table
						keywordMatrix[rowPos][colPos] = '.';
						keywordMatrix[rowPos][colPos + 1] = '.';
					}
				}
			}
		}

		return keywordMatrix;
	}

// Get Column position
	// Sub-method of createMatrix
	// Finds the column position in the keywordMatrix based on the keyword length and the row position
	private void getColPos() { 
		rowPos = encryptTextStep / ((keyword.length) / 2);
		colPos = ((encryptTextStep % (keyword.length)) + encryptTextStep);

		// Go to the next line
		if (encryptTextStep == (kwCoef + 1) * keyword.length) {
			kwCoef += 1;
		}

		// Set to column 0
		if (encryptTextStep == keyword.length || encryptTextStep == rowPos * ((keyword.length) / 2)) {
			colPos = 0;

			// Set column position in the first line
		} else if (encryptTextStep > kwCoef * keyword.length) {
			colPos = colPos - (rowPos * (keyword.length)) + kwCoef * keyword.length;

			// Set column position in the second line
		} else if (encryptTextStep > 2 * keyword.length) {
			colPos = colPos - (rowPos * (keyword.length)) + 2 * keyword.length;

			// Set column position in all the lines
		} else if (encryptTextStep > keyword.length) {
			colPos = colPos - (rowPos * (keyword.length)) + keyword.length;

			// Used for column swap
		} else if (colPos > ((keyword.length) - 1)) {
			colPos = ((encryptTextStep % (keyword.length)) + encryptTextStep);
			colPos = colPos - (rowPos * (keyword.length));
		}
	}

// Transpose keywordMatrix
	// Sub-method of encryptText
	private char[][] transposeKeywordMatrix(char[][] matrixToTranspose) {
		char[][] transposedMatrix = new char[keywordMatrixRow][(keyword.length)];

		for (int transposeKwStep = 0; transposeKwStep < keyword.length; transposeKwStep++) {
			for (int i = 0; i < keywordMatrixRow; i++) {
				
				// If character isnt a letter set a .
				if (!Character.isLetter(matrixToTranspose[i][transposeKwStep])
						&& !Character.isDigit(matrixToTranspose[i][transposeKwStep])) {
					matrixToTranspose[i][transposeKwStep] = '.';
				}

				// Used to transpose with the column swap number set in settings
				if (transposeKwStep + rowSwap >= keyword.length) {
					transposedMatrix[i][(transposeKwStep + rowSwap)
							- (keyword.length)] = matrixToTranspose[i][transposeKwStep];
				} else {
					transposedMatrix[i][transposeKwStep + rowSwap] = matrixToTranspose[i][transposeKwStep];
				}
			}
		}

		return transposedMatrix;
	}

// Read Transposed Matrix
	// Sub-method of encryptText
	private String readTransposedMatrix(char[][] matrixToRead) {
		String encryptedText = "";
		for (int col = 0; col < matrixToRead[0].length; col++) {
			for (int row = 0; row < matrixToRead.length; row++) {
				encryptedText = encryptedText + matrixToRead[row][col]; // Not most efficient
			}
		}
		copyToClipboard(encryptedText);
		return encryptedText;
	}

	/*
	 * -------------------------- Decrypting sub-methods --------------------------
	 */

	private char[][] readEncryptedText(String textToDecrypt, char[] keywordUsed) {
		char[] encryptedTextUp = textToDecrypt.toUpperCase().toCharArray();
		keywordUsedMatrixRow = (encryptedTextUp.length / 2) / ((keywordUsed.length) / 2);
		char[][] matrixToDecrypt = new char[keywordUsedMatrixRow][keywordUsed.length];
		int readTextCount = 0;

		for (int readTextStep = 0; readTextStep < keywordUsed.length; readTextStep++) {
			for (int i = 0; i < keywordUsedMatrixRow; i++) {
				if (encryptedTextUp.length > readTextCount) {
					matrixToDecrypt[i][readTextStep] = encryptedTextUp[readTextCount];
					readTextCount += 1;
				}
			}
		}

		return matrixToDecrypt;
	}

	private char[][] transposeEncryptedMatrix(char[][] matrixToDecrypt, char[] keywordUsed) {
		char[][] matrixDecrypted = new char[keywordUsedMatrixRow][keywordUsed.length];
		for (int transposeEnStep = 0; transposeEnStep < keywordUsed.length; transposeEnStep++) {
			for (int i = 0; i < keywordUsedMatrixRow; i++) {
				if (transposeEnStep + rowSwap >= keyword.length) {
					matrixDecrypted[i][transposeEnStep] = matrixToDecrypt[i][(transposeEnStep + rowSwap) - (keyword.length)];
				} else {
					matrixDecrypted[i][transposeEnStep] = matrixToDecrypt[i][transposeEnStep + rowSwap];
				}
			}
		}

		return matrixDecrypted;
	}

	private String createText(char[][] matrixDecrypted) {
		String decryptedText = "";
		for (int row = 0; row < matrixDecrypted.length; row++) {
			for (int col = 0; col < matrixDecrypted[row].length; col += 2) {
				if (matrixDecrypted[row][col] == '.') {
					decryptedText = decryptedText + " ";
				} else {
					for (int i = 0; i < cypher.length; i++) {
						if (matrixDecrypted[row][col] == cypher[i]) {
							for (int j = 0; j < cypher.length; j++) {
								if (matrixDecrypted[row][col + 1] == cypher[j]) {
									decryptedText = decryptedText + matrix[i][j];
								}
							}
						}
					}
				}
			}
		}
		copyToClipboard(decryptedText); // if boolean copyEnabled true, then this will happen
		return decryptedText;
	}

	/* ---------------------------- Get & Set method ---------------------------- */

// Set Code word
	public void setCode(String kw) { // Option 1 of Code menu
		char[] kwChar = kw.replaceAll(" ", "").toUpperCase().toCharArray();

		// Code word is too short
		if (kwChar.length < 4) {
			this.keywordSet = false;
			System.out.println("");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("     !!!!!! INVALID  INPUT !!!!!!");
			System.out.println("     !!!!  Code word should  !!!!");
			System.out.println("     !!!!  contain a minimum !!!!");
			System.out.println("     !!!!   of 4 characters  !!!!");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			// Code word set properly
		} else if (kwChar.length % 2 == 0 && kwChar.length >= 4) {
			this.keyword = kwChar;
			this.keywordSet = true;
			System.out.println("");
			System.out.println("     Code word successfully set to " + getCode());
			System.out.println("");

			// Code word is not even
		} else {
			this.keywordSet = false;
			System.out.println("");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("     !!!!!! INVALID  INPUT !!!!!!");
			System.out.println("     !!!!  Code word should  !!!!");
			System.out.println("     !!!!  contain an even   !!!!");
			System.out.println("     !!!! amount of letters  !!!!");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

// Get Code word
	public String getCode() { // Option 2 of the Code menu

		String convertedKeyword = new String(keyword);
		return convertedKeyword;
	}

	/* --------------------------------- Extra -------------------------------- */

// Copy to Clipboard
	public void copyToClipboard(String stringToCopy) {
		// If settings is set to copy and not encrypting a file
		if (copyEnabled && !fileEncryption) {
			StringSelection stringSelection = new StringSelection(stringToCopy);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			
			// Copy successful
			System.out.println("     Auto-copy to clipboard enabled.");
			System.out.println("");
		}
	}
	
	public void setCodeWordSwap(int rowSwapTent) {
			// Swap number too big
		if(keyword.length <= rowSwapTent) {
			System.out.println("");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("     !!!!   Swap number has  !!!!");
			System.out.println("     !!!! to be shorter than !!!!");
			System.out.println("     !!!!   the code word.   !!!!");
			System.out.println("     !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("");
			
			// Set swap number
		}else if(keyword.length > rowSwapTent) {
			rowSwap = rowSwapTent;
			System.out.println("     Swap number set to " + rowSwap);
			codeWordSwapSet = true;
		}
	}
}