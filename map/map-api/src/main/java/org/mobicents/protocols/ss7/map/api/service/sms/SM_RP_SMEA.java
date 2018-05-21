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

package org.mobicents.protocols.ss7.map.api.service.sms;

import java.io.Serializable;

import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.smstpdu.AddressField;

/**
 *
 * SM-RP-SMEA ::= OCTET STRING (SIZE (1..12)) -- this parameter contains an address field which is encoded -- as defined in 3GPP
 * TS 23.040. An address field contains 3 elements : -- address-length -- type-of-address -- address-value
 *
 * @author sergey vetyutnev
 *
 */
public interface SM_RP_SMEA extends Serializable {

    byte[] getData();

    AddressField getAddressField() throws MAPException;

}