package com.baloise.confluence.digitalsignature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.junit.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class SignatureSerialisationTest {

	@Test
	public void deserialise() throws IOException, ClassNotFoundException {
		try(ObjectInputStream in = new ObjectInputStream(getClass().getResourceAsStream("/src/test/resources/signature.ser"))){
			Signature signature = (Signature) in.readObject();
			assertEquals("signature.a077cdcc5bfcf275fe447ae2c609c1c361331b4e90cb85909582e0d824cbc5b3", signature.getKey());
			assertEquals("[missing1, missing2]", signature.getMissingSignatures().toString());
			assertEquals(1, signature.getSignatures().size());
			assertTrue( signature.getSignatures().containsKey("signed1"));
			assertEquals(9999, signature.getSignatures().get("signed1").getTime());
			
		}
	}
	
	@Test
	@Ignore
	public void serialise() throws IOException {
		Signature signature = new Signature(123L, "body", "title");
		signature.getNotify().add("notify1");
		signature.getMissingSignatures().add("missing1");
		signature.getMissingSignatures().add("missing2");
		signature.getSignatures().put("signed1", new Date(9999));
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/test/resources/signature.ser"))){
			out.writeObject(signature);
		}
	}

}