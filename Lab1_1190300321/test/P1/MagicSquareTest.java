package P1;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class MagicSquareTest {

	@Test
	public void isLegalMagicSquareTest() throws IOException  {
		MagicSquare magicSquare = new MagicSquare();
		String filename1 = "src/P1/txt/1.txt";
        String filename2 = "src/P1/txt/2.txt";
        String filename3 = "src/P1/txt/3.txt";
        String filename4 = "src/P1/txt/4.txt";
        String filename5 = "src/P1/txt/5.txt";
        assertEquals(true,magicSquare.isLegalMagicSquare(filename1));
        assertEquals(true,magicSquare.isLegalMagicSquare(filename2));
        assertEquals(false,magicSquare.isLegalMagicSquare(filename3));
        assertEquals(false,magicSquare.isLegalMagicSquare(filename4));
        assertEquals(false,magicSquare.isLegalMagicSquare(filename5));
	}
}
