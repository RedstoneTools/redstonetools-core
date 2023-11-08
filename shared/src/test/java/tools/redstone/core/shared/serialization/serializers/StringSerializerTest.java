package tools.redstone.core.shared.serialization.serializers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringSerializerTest {
	@Test
	void deserializeQuoted() {
		assertEquals("hello world", new StringSerializer().deserialize("\"hello world\" "));
		assertEquals("hello", new StringSerializer().deserialize("\"hello\" "));
	}

	@Test
	void deserializeUnQuoted() {
		assertEquals("hello", new StringSerializer().deserialize("hello"));
	}
}
