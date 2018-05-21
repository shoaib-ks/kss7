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

package org.mobicents.protocols.ss7.tools.simulator.common;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.ss7.tools.simulator.level1.DialogicConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.level1.M3uaConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.level2.SccpConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.level3.CapConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.level3.MapConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.management.Instance_L1;
import org.mobicents.protocols.ss7.tools.simulator.management.Instance_L2;
import org.mobicents.protocols.ss7.tools.simulator.management.Instance_L3;
import org.mobicents.protocols.ss7.tools.simulator.management.Instance_TestTask;
import org.mobicents.protocols.ss7.tools.simulator.tests.cap.TestCapScfConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.tests.cap.TestCapSsfConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.tests.sms.TestSmsClientConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.tests.sms.TestSmsServerConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.tests.ussd.TestUssdClientConfigurationData;
import org.mobicents.protocols.ss7.tools.simulator.tests.ussd.TestUssdServerConfigurationData;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class ConfigurationData {

    public static final String INSTANCE_L1 = "instance_L1";
    public static final String INSTANCE_L2 = "instance_L2";
    public static final String INSTANCE_L3 = "instance_L3";
    public static final String INSTANCE_TESTTASK = "instance_TestTask";

    public static final String M3UA = "m3ua";
    public static final String DIALOGIC = "dialogic";
    public static final String SCCP = "sccp";
    public static final String MAP = "map";
    public static final String CAP = "cap";
    public static final String TEST_USSD_CLIENT = "testUssdClient";
    public static final String TEST_USSD_SERVER = "testUssdServer";
    public static final String TEST_SMS_CLIENT = "testSmsClient";
    public static final String TEST_SMS_SERVER = "testSmsServer";
    public static final String TEST_CAP_SCF = "testCapScf";
    public static final String TEST_CAP_SSF = "testCapSsf";

    private Instance_L1 instance_L1 = new Instance_L1(Instance_L1.VAL_NO);
    private Instance_L2 instance_L2 = new Instance_L2(Instance_L2.VAL_NO);
    private Instance_L3 instance_L3 = new Instance_L3(Instance_L3.VAL_NO);
    private Instance_TestTask instance_TestTask = new Instance_TestTask(Instance_TestTask.VAL_NO);

    private M3uaConfigurationData m3uaConfigurationData = new M3uaConfigurationData();
    private DialogicConfigurationData dialogicConfigurationData = new DialogicConfigurationData();
    private SccpConfigurationData sccpConfigurationData = new SccpConfigurationData();
    private MapConfigurationData mapConfigurationData = new MapConfigurationData();
    private CapConfigurationData capConfigurationData = new CapConfigurationData();

    private TestSmsClientConfigurationData testSmsClientConfigurationData = new TestSmsClientConfigurationData();
    private TestSmsServerConfigurationData testSmsServerConfigurationData = new TestSmsServerConfigurationData();
    private TestUssdClientConfigurationData testUssdClientConfigurationData = new TestUssdClientConfigurationData();
    private TestUssdServerConfigurationData testUssdServerConfigurationData = new TestUssdServerConfigurationData();
    private TestCapScfConfigurationData testCapScfConfigurationData = new TestCapScfConfigurationData();
    private TestCapSsfConfigurationData testCapSsfConfigurationData = new TestCapSsfConfigurationData();

    public Instance_L1 getInstance_L1() {
        return instance_L1;
    }

    public void setInstance_L1(Instance_L1 val) {
        instance_L1 = val;
    }

    public Instance_L2 getInstance_L2() {
        return instance_L2;
    }

    public void setInstance_L2(Instance_L2 val) {
        instance_L2 = val;
    }

    public Instance_L3 getInstance_L3() {
        return instance_L3;
    }

    public void setInstance_L3(Instance_L3 val) {
        instance_L3 = val;
    }

    public Instance_TestTask getInstance_TestTask() {
        return instance_TestTask;
    }

    public void setInstance_TestTask(Instance_TestTask val) {
        instance_TestTask = val;
    }

    public M3uaConfigurationData getM3uaConfigurationData() {
        return m3uaConfigurationData;
    }

    public void setM3uaConfigurationData(M3uaConfigurationData m3uaConfigurationData) {
        this.m3uaConfigurationData = m3uaConfigurationData;
    }

    public DialogicConfigurationData getDialogicConfigurationData() {
        return dialogicConfigurationData;
    }

    public void setDialogicConfigurationData(DialogicConfigurationData dialogicConfigurationData) {
        this.dialogicConfigurationData = dialogicConfigurationData;
    }

    public SccpConfigurationData getSccpConfigurationData() {
        return sccpConfigurationData;
    }

    public void setSccpConfigurationData(SccpConfigurationData sccpConfigurationData) {
        this.sccpConfigurationData = sccpConfigurationData;
    }

    public MapConfigurationData getMapConfigurationData() {
        return mapConfigurationData;
    }

    public void setMapConfigurationData(MapConfigurationData mapConfigurationData) {
        this.mapConfigurationData = mapConfigurationData;
    }

    public CapConfigurationData getCapConfigurationData() {
        return capConfigurationData;
    }

    public void setCapConfigurationData(CapConfigurationData capConfigurationData) {
        this.capConfigurationData = capConfigurationData;
    }

    public TestSmsClientConfigurationData getTestSmsClientConfigurationData() {
        return testSmsClientConfigurationData;
    }

    public void setTestSmsClientConfigurationData(TestSmsClientConfigurationData testSmsClientConfigurationData) {
        this.testSmsClientConfigurationData = testSmsClientConfigurationData;
    }

    public TestSmsServerConfigurationData getTestSmsServerConfigurationData() {
        return testSmsServerConfigurationData;
    }

    public void setTestSmsServerConfigurationData(TestSmsServerConfigurationData testSmsServerConfigurationData) {
        this.testSmsServerConfigurationData = testSmsServerConfigurationData;
    }

    public TestUssdClientConfigurationData getTestUssdClientConfigurationData() {
        return testUssdClientConfigurationData;
    }

    public void setTestUssdClientConfigurationData(TestUssdClientConfigurationData testUssdClientConfigurationData) {
        this.testUssdClientConfigurationData = testUssdClientConfigurationData;
    }

    public TestUssdServerConfigurationData getTestUssdServerConfigurationData() {
        return testUssdServerConfigurationData;
    }

    public void setTestUssdServerConfigurationData(TestUssdServerConfigurationData testUssdServerConfigurationData) {
        this.testUssdServerConfigurationData = testUssdServerConfigurationData;
    }

    public TestCapScfConfigurationData getTestCapScfConfigurationData() {
        return testCapScfConfigurationData;
    }

    public void setTestCapScfConfigurationData(TestCapScfConfigurationData testCapScfConfigurationData) {
        this.testCapScfConfigurationData = testCapScfConfigurationData;
    }

    public TestCapSsfConfigurationData getTestCapSsfConfigurationData() {
        return testCapSsfConfigurationData;
    }

    public void setTestCapSsfConfigurationData(TestCapSsfConfigurationData testCapSsfConfigurationData) {
        this.testCapSsfConfigurationData = testCapSsfConfigurationData;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<ConfigurationData> CONFIGURATION_DATA_XML = new XMLFormat<ConfigurationData>(
            ConfigurationData.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, ConfigurationData data) throws XMLStreamException {
            data.instance_L1 = Instance_L1.createInstance(xml.get(INSTANCE_L1, String.class));
            data.instance_L2 = Instance_L2.createInstance(xml.get(INSTANCE_L2, String.class));
            data.instance_L3 = Instance_L3.createInstance(xml.get(INSTANCE_L3, String.class));
            data.instance_TestTask = Instance_TestTask.createInstance(xml.get(INSTANCE_TESTTASK, String.class));

            M3uaConfigurationData m3ua = xml.get(M3UA, M3uaConfigurationData.class);
            if (m3ua != null)
                data.m3uaConfigurationData = m3ua;

            DialogicConfigurationData dial = xml.get(DIALOGIC, DialogicConfigurationData.class);
            if (dial != null)
                data.dialogicConfigurationData = dial;

            SccpConfigurationData sccp = xml.get(SCCP, SccpConfigurationData.class);
            if (sccp != null)
                data.setSccpConfigurationData(sccp);

            MapConfigurationData map = xml.get(MAP, MapConfigurationData.class);
            if (map != null)
                data.setMapConfigurationData(map);

            CapConfigurationData cap = xml.get(CAP, CapConfigurationData.class);
            if (cap != null)
                data.setCapConfigurationData(cap);

            TestUssdClientConfigurationData ussdClient = xml.get(TEST_USSD_CLIENT, TestUssdClientConfigurationData.class);
            if (ussdClient != null)
                data.setTestUssdClientConfigurationData(ussdClient);

            TestUssdServerConfigurationData ussdServer = xml.get(TEST_USSD_SERVER, TestUssdServerConfigurationData.class);
            if (ussdServer != null)
                data.setTestUssdServerConfigurationData(ussdServer);

            TestSmsClientConfigurationData smsClient = xml.get(TEST_SMS_CLIENT, TestSmsClientConfigurationData.class);
            if (smsClient != null)
                data.setTestSmsClientConfigurationData(smsClient);

            TestSmsServerConfigurationData smsServer = xml.get(TEST_SMS_SERVER, TestSmsServerConfigurationData.class);
            if (smsServer != null)
                data.setTestSmsServerConfigurationData(smsServer);

            TestCapScfConfigurationData capScf = xml.get(TEST_CAP_SCF, TestCapScfConfigurationData.class);
            if (capScf != null)
                data.setTestCapScfConfigurationData(capScf);

            TestCapSsfConfigurationData capSsf = xml.get(TEST_CAP_SSF, TestCapSsfConfigurationData.class);
            if (capSsf != null)
                data.setTestCapSsfConfigurationData(capSsf);

            // while (xml.hasNext()) {
            // Object o = xml.getNext();
            // }
        }

        @Override
        public void write(ConfigurationData data, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
            xml.add(data.instance_L1.toString(), INSTANCE_L1, String.class);
            xml.add(data.instance_L2.toString(), INSTANCE_L2, String.class);
            xml.add(data.instance_L3.toString(), INSTANCE_L3, String.class);
            xml.add(data.instance_TestTask.toString(), INSTANCE_TESTTASK, String.class);

            xml.add(data.m3uaConfigurationData, M3UA, M3uaConfigurationData.class);
            xml.add(data.dialogicConfigurationData, DIALOGIC, DialogicConfigurationData.class);
            xml.add(data.getSccpConfigurationData(), SCCP, SccpConfigurationData.class);
            xml.add(data.getMapConfigurationData(), MAP, MapConfigurationData.class);
            xml.add(data.getCapConfigurationData(), CAP, CapConfigurationData.class);

            xml.add(data.getTestUssdClientConfigurationData(), TEST_USSD_CLIENT, TestUssdClientConfigurationData.class);
            xml.add(data.getTestUssdServerConfigurationData(), TEST_USSD_SERVER, TestUssdServerConfigurationData.class);
            xml.add(data.getTestSmsClientConfigurationData(), TEST_SMS_CLIENT, TestSmsClientConfigurationData.class);
            xml.add(data.getTestSmsServerConfigurationData(), TEST_SMS_SERVER, TestSmsServerConfigurationData.class);
            xml.add(data.getTestCapScfConfigurationData(), TEST_CAP_SCF, TestCapScfConfigurationData.class);
            xml.add(data.getTestCapSsfConfigurationData(), TEST_CAP_SSF, TestCapSsfConfigurationData.class);
        }
    };

}
