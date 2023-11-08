package tools.redstone.core.shared.serialization.serializers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class IntSerializerTest {
	private final IntSerializer serializer = new IntSerializer(Integer.MIN_VALUE, Integer.MAX_VALUE);

	@Test
	void testDeserialize() {
		assertEquals(69, serializer.deserialize("69"));
		assertEquals(-420, serializer.deserialize("-420"));
		assertThrows(RuntimeException.class, () -> serializer.deserialize("abcd"));
	}

	@Test
	void testSerialize() {
		assertEquals("69", serializer.serialize(69));
		assertEquals("-420", serializer.serialize(-420));
	}
}
