# polybiusCypher
A simple terminal java app to encrypt/decrypt using a Polybius Cypher

## What is this?

This terminal app is a java terminal app that we had to create as assignment during my studies in GMIT.

## How the encryption works?

### Code Word

You will have to set up a code word before using the application.
No worries, when launching the app you will actually be prompted to set your code word.
You will always be able to change this code word via the Main Menu > Code Word.
If code word contains multiple words with spaces, spaces will be deleted and words will grouped as one word.

### Encrypting

The encryption follows the idea of a Polybius ADFGVX cypher.

|  | A | D | F | G | V | X |
| --- | --- | --- | --- | --- | --- | --- | 
| **A** | P | H | 0 | Q | G | 6 | 
| **D** | 4 | M | E | A | 1 | Y | 
| **F** | L | 2 | N | O | F | D | 
| **G** | X | K | R | 3 | C | V | 
| **V** | S | 5 | Z | W | 7 | B | 
| **X** | J | 9 | U | T | I | B | 


The app will look for each character on the above table and provide two letters representing this character.
The app will then use the code word provided earlier to create a second table (see below).
In this example the code word is JAVA to encrypt OBJECT.

> First table

| J | A | V | A |
| --- | --- | --- | --- |
| F | G | V | X |
| X | A | D | F |
| G | V | X | G |

> Second table

| A | V | A | J |
| --- | --- | --- | --- |
| G | V | X | F |
| A | D | F | X |
| V | X | G | G |

This table is then mixed by swapping the letters of the code word. By default, the letters will jump 3 rows further.
You can select a different amount of rows in Main Menu > Settings.

Note:
The encryption will replace any space or character not found in the matrix with a dot.
The decryption will replace any dots by spaces.

### Text

When selecting encrypting/decrypting you will be directly prompted to enter the text you would like to encrypt.
Your text will have to be a line of text. You won't be able to encrypt multiple lines. If you wish to do so, please use a file.
Please note that the code word you have set up this encryption won't be shown or provided within the encrypted text.
It is up to you to write down the code word used for this encryption.
An additional feature can be enable via Menu > Settings > Enable/Disable auto copy.
This feature allows you to automatically copy the encrypted text to your clipboard.

### File

When selecting to encrypt/decrypt a file you will be prompted to navigate through you file system.
You will be automatically directed to your home folder.
You will have to enter the name of the folder you would like to access. You can use the word "return" to go back one directory.


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
