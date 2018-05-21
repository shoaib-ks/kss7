/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2013, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.mobicents.protocols.ss7.sccp.impl.messageflow;

import static org.testng.Assert.assertEquals;

import org.mobicents.protocols.ss7.Util;
import org.mobicents.protocols.ss7.indicator.RoutingIndicator;
import org.mobicents.protocols.ss7.sccp.LoadSharingAlgorithm;
import org.mobicents.protocols.ss7.sccp.OriginationType;
import org.mobicents.protocols.ss7.sccp.RuleType;
import org.mobicents.protocols.ss7.sccp.impl.Mtp3UserPartImpl;
import org.mobicents.protocols.ss7.sccp.impl.SccpHarness;
import org.mobicents.protocols.ss7.sccp.impl.SccpStackImpl;
import org.mobicents.protocols.ss7.sccp.impl.SccpStackImplProxy;
import org.mobicents.protocols.ss7.sccp.impl.User;
import org.mobicents.protocols.ss7.sccp.message.SccpDataMessage;
import org.mobicents.protocols.ss7.sccp.parameter.GlobalTitle;
import org.mobicents.protocols.ss7.sccp.parameter.SccpAddress;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class LoadSharingTest extends SccpHarness {

    private SccpAddress a1, a2;
    protected Mtp3UserPartImpl mtp3UserPart11 = new Mtp3UserPartImpl();

    public LoadSharingTest() {
    }

    @BeforeClass
    public void setUpClass() throws Exception {
        this.sccpStack1Name = "LoadSharingTestStack1";
        this.sccpStack2Name = "LoadSharingTestStack2";
    }

    @AfterClass
    public void tearDownClass() throws Exception {
    }

    protected void createStack1() {
        sccpStack1 = createStack(sccpStack1Name);
        sccpStack1.setMtp3UserPart(2, mtp3UserPart11);
        sccpProvider1 = sccpStack1.getSccpProvider();
    }

    protected void createStack2() {
        sccpStack2 = createStack(sccpStack2Name);
        sccpProvider2 = sccpStack2.getSccpProvider();
    }

    @Override
    protected SccpStackImpl createStack(String name) {
        SccpStackImpl stack = new SccpStackImplProxy(name);
        final String dir = Util.getTmpTestDir();
        if (dir != null) {
            stack.setPersistDir(dir);
        }
        return stack;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sccpStack1.getRouter().addMtp3ServiceAccessPoint(2, 2, 11, 2);
        sccpStack1.getRouter().addMtp3Destination(2, 1, 12, 12, 0, 255, 255);

        resource1.addRemoteSpc(2, 12, 0, 0);
        resource1.addRemoteSsn(2, 12, getSSN(), 0, false);
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }

    public byte[] getDataSrc() {
        return new byte[] { 11, 12, 13, 14, 15 };
    }

    @Test(groups = { "SccpMessage", "functional.transfer" })
    public void testTransfer() throws Exception {

        a1 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_DPC_AND_SSN, getStack1PC(), null, 8);
        a2 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_DPC_AND_SSN, getStack2PC(), null, 8);

        User u1 = new User(sccpStack1.getSccpProvider(), a1, a2, getSSN());
        User u2 = new User(sccpStack2.getSccpProvider(), a2, a1, getSSN());

        u1.register();
        u2.register();

        Thread.sleep(100);

        SccpAddress primaryAddress = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_DPC_AND_SSN, getStack2PC(),
                GlobalTitle.getInstance(1, "111111"), 8);
        sccpStack1.getRouter().addRoutingAddress(1, primaryAddress);
        // primaryAddress2 - with ssn==0, so we will get ssn from the message CalledPartyAddress
        SccpAddress primaryAddress2 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_DPC_AND_SSN, getStack2PC(),
                GlobalTitle.getInstance(1, "111111"), 0);
        sccpStack1.getRouter().addRoutingAddress(2, primaryAddress2);
        SccpAddress backupAddress = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_DPC_AND_SSN, 12, GlobalTitle.getInstance(
                1, "111111"), 8);
        sccpStack1.getRouter().addRoutingAddress(3, backupAddress);
        // sccpStack1.getRouter().addBackupAddress(1, backupAddress);

        // ---- Solitary case
        SccpAddress pattern = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1,
                "111111"), 0);
        // pattern2 - with default ssn value
        SccpAddress pattern2 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1,
                "222222"), 0);
        sccpStack1.getRouter().addRule(1, RuleType.Solitary, LoadSharingAlgorithm.Undefined, OriginationType.All, pattern, "K",
                1, -1, null);

        // Primary and backup are available
        SccpAddress a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0,
                GlobalTitle.getInstance(1, "111111"), 0);
        SccpDataMessage message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8,
                true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 0);
        assertEquals(u2.getMessages().size(), 1);
        assertEquals(mtp3UserPart11.getMessages().size(), 0);

        // Primary is available backup is disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 0);
        assertEquals(u2.getMessages().size(), 2);
        assertEquals(mtp3UserPart11.getMessages().size(), 0);

        // Primary is disabled backup is available
        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendPauseMessageToLocalUser(getStack2PC());
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 1);
        assertEquals(u2.getMessages().size(), 2);
        assertEquals(mtp3UserPart11.getMessages().size(), 0);

        // Primary and backup are disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 2);
        assertEquals(u2.getMessages().size(), 2);
        assertEquals(mtp3UserPart11.getMessages().size(), 0);

        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendResumeMessageToLocalUser(getStack2PC());
        Thread.sleep(100);

        // ---- Dominant case
        sccpStack1.getRouter().removeRule(1);
        sccpStack1.getRouter().addRule(1, RuleType.Dominant, LoadSharingAlgorithm.Undefined, OriginationType.All, pattern, "K",
                1, 3, null);

        // Primary and backup are available
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 2);
        assertEquals(u2.getMessages().size(), 3);
        assertEquals(mtp3UserPart11.getMessages().size(), 0);

        // Primary is available backup is disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 2);
        assertEquals(u2.getMessages().size(), 4);
        assertEquals(mtp3UserPart11.getMessages().size(), 0);

        // Primary is disabled backup is available
        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendPauseMessageToLocalUser(getStack2PC());
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 2);
        assertEquals(u2.getMessages().size(), 4);
        assertEquals(mtp3UserPart11.getMessages().size(), 1);

        // Primary and backup are disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 4);
        assertEquals(mtp3UserPart11.getMessages().size(), 1);

        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendResumeMessageToLocalUser(getStack2PC());
        Thread.sleep(100);

        // ---- Loadshared case
        sccpStack1.getRouter().removeRule(1);
        sccpStack1.getRouter().addRule(1, RuleType.Loadshared, LoadSharingAlgorithm.Bit4, OriginationType.All, pattern, "K", 1,
                3, null);
        // rule which primaryAddress ssn==0 (getting ssn from origin CalledPartyAddress)
        sccpStack1.getRouter().addRule(2, RuleType.Loadshared, LoadSharingAlgorithm.Bit4, OriginationType.All, pattern2, "K",
                2, 3, null);

        // Primary and backup are available
        // - class 1 (route by sls): sls = 0xEF: primary route (sls & 0x10 rule)
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0xEF, 8, true, null,
                null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 5);
        assertEquals(mtp3UserPart11.getMessages().size(), 1);

        // - class 1 (route by sls): sls = 0xFF: backup route (sls & 0x10 rule)
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0xFF, 8, true, null,
                null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 5);
        assertEquals(mtp3UserPart11.getMessages().size(), 2);

        // - class 0: first message is for primary route
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass0(a3, a1, getDataSrc(), 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 6);
        assertEquals(mtp3UserPart11.getMessages().size(), 2);

        // - class 0: second message is for backup route
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass0(a3, a1, getDataSrc(), 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 6);
        assertEquals(mtp3UserPart11.getMessages().size(), 3);

        // Primary is available backup is disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 7);
        assertEquals(mtp3UserPart11.getMessages().size(), 3);

        // Primary is available backup is disabled + CalledPartyAddress has SSN + primaryAddress has not SSN (SSN is taken from
        // CalledPartyAddress)
        SccpAddress a3_2 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1,
                "222222"), 8);
        message = this.sccpProvider1.getMessageFactory()
                .createDataMessageClass1(a3_2, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 8);
        assertEquals(mtp3UserPart11.getMessages().size(), 3);

        // Primary is disabled backup is available
        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendPauseMessageToLocalUser(getStack2PC());
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 3);
        assertEquals(u2.getMessages().size(), 8);
        assertEquals(mtp3UserPart11.getMessages().size(), 4);

        // Primary and backup are disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 4);
        assertEquals(u2.getMessages().size(), 8);
        assertEquals(mtp3UserPart11.getMessages().size(), 4);

        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendResumeMessageToLocalUser(getStack2PC());
        Thread.sleep(100);

        // ---- Broadcast case
        sccpStack1.getRouter().removeRule(1);
        sccpStack1.getRouter().removeRule(2);
        sccpStack1.getRouter().addRule(1, RuleType.Broadcast, LoadSharingAlgorithm.Undefined, OriginationType.All, pattern,
                "K", 1, 3, null);

        // Primary and backup are available
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 4);
        assertEquals(u2.getMessages().size(), 9);
        assertEquals(mtp3UserPart11.getMessages().size(), 5);

        // Primary is available backup is disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 4);
        assertEquals(u2.getMessages().size(), 10);
        assertEquals(mtp3UserPart11.getMessages().size(), 5);

        // Primary is disabled backup is available
        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendPauseMessageToLocalUser(getStack2PC());
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 4);
        assertEquals(u2.getMessages().size(), 10);
        assertEquals(mtp3UserPart11.getMessages().size(), 6);

        // Primary and backup are disabled
        this.mtp3UserPart1.sendPauseMessageToLocalUser(12);
        Thread.sleep(100);
        a3 = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE, 0, GlobalTitle.getInstance(1, "111111"), 0);
        message = this.sccpProvider1.getMessageFactory().createDataMessageClass1(a3, a1, getDataSrc(), 0, 8, true, null, null);
        sccpProvider1.send(message);
        Thread.sleep(100);
        assertEquals(u1.getMessages().size(), 5);
        assertEquals(u2.getMessages().size(), 10);
        assertEquals(mtp3UserPart11.getMessages().size(), 6);

        this.mtp3UserPart1.sendResumeMessageToLocalUser(12);
        this.mtp3UserPart1.sendResumeMessageToLocalUser(getStack2PC());
        Thread.sleep(100);
    }
}