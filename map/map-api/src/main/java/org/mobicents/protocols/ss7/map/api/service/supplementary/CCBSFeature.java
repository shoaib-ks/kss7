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

package org.mobicents.protocols.ss7.map.api.service.supplementary;

import java.io.Serializable;

import org.mobicents.protocols.ss7.map.api.primitives.ISDNAddressString;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.BasicServiceCode;

/**
 *
 CCBS-Feature ::= SEQUENCE { ccbs-Index [0] CCBS-Index OPTIONAL, b-subscriberNumber [1] ISDN-AddressString OPTIONAL,
 * b-subscriberSubaddress [2] ISDN-SubaddressString OPTIONAL, basicServiceGroup [3] BasicServiceCode OPTIONAL, ...}
 *
 * CCBS-Index ::= INTEGER (1..5)
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface CCBSFeature extends Serializable {

    Integer getCcbsIndex();

    ISDNAddressString getBSubscriberNumber();

    ISDNAddressString getBSubscriberSubaddress();

    BasicServiceCode getBasicServiceCode();

}
