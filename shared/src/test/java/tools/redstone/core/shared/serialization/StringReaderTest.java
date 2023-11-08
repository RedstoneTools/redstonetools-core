package tools.redstone.core.shared.serialization;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringReaderTest {
	private StringReader stringReader;

	@BeforeEach
	void setup() {
		stringReader = new StringReader("hello world");
	}

	@Test
	void testRead() {
		assertEquals('h', stringReader.read().get());
	}

	@Test
	void testPeekEndOfInput() {
		stringReader.skip(11);
		assertEquals(Optional.empty(), stringReader.peek());
	}

	@Test
	void testPeekWhile() {
		stringReader.skip(2);
		assertEquals("ll", stringReader.peekWhile(c -> c == 'l'));
	}

	@Test
	void testReadWord() {
		assertEquals("hello", stringReader.readWord());
	}
}
