package tools.redstone.core.shared.serialization.serializers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BooleanSerializerTest {
	private final BooleanSerializer serializer = new BooleanSerializer();

	@Test
	void testDeserialize() {
		assertEquals(true, serializer.deserialize("true"));
		assertEquals(false, serializer.deserialize("false"));
		assertThrows(RuntimeException.class, () -> serializer.deserialize("jhrfhbfv"));
	}

	@Test
	void testSerialize() {
		assertEquals("true", serializer.serialize(true));
		assertEquals("false", serializer.serialize(false));
	}
}
