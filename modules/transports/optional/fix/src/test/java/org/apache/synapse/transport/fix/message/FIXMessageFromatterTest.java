/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.synapse.transport.fix.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMOutputFormat;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;

public class FIXMessageFromatterTest extends TestCase {

	public void testWriteTo() {

		String input =
		               "8=FIX.4.0\u00019=105\u000135=D\u000134=2\u000149=BANZAI\u000152=20080711-06:42:26\u000156=SYNAPSE\u0001"
		                       + "11=1215758546278\u000121=1\u000138=90000000\u000140=1\u000154=1\u000155=DEL\u000159=0\u000110=121\u0001";

		MessageContext msgCtx = new MessageContext();
		FIXMessageBuilder builder = new FIXMessageBuilder();
		OMElement element = null;
		try {
			element = builder.processDocument(new ByteArrayInputStream(input.getBytes()), "fix/j", msgCtx);
			Assert.assertNotNull(element);
			// System.out.println(element);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FIXMessageFromatter fixMessageFromatter = new FIXMessageFromatter();
		// msgCtx = new MessageContext();
		// msgCtx.seten
		OutputStream output = new ByteArrayOutputStream();
		SOAPFactory factory = OMAbstractFactory.getSOAP12Factory();
		SOAPEnvelope env = factory.getDefaultEnvelope();
		env.getBody().addChild(element);
		try {
			msgCtx.setEnvelope(env);
		} catch (AxisFault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OMOutputFormat myOutputFormat = new OMOutputFormat();
		try {
			fixMessageFromatter.writeTo(msgCtx, myOutputFormat, output, false);
			assertTrue(output.toString().length()>0);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
