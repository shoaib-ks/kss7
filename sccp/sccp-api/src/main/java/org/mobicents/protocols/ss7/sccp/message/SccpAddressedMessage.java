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

package org.mobicents.protocols.ss7.sccp.message;

import org.mobicents.protocols.ss7.sccp.parameter.HopCounter;
import org.mobicents.protocols.ss7.sccp.parameter.SccpAddress;

/**
 *
 * This interface represents a SCCP message that addressed with Called/CallingPartyAddresses
 *
 * @author sergey vetyutnev
 *
 */
public interface SccpAddressedMessage extends SccpMessage {

    SccpAddress getCalledPartyAddress();

    SccpAddress getCallingPartyAddress();

    boolean getReturnMessageOnError();

    void clearReturnMessageOnError();

    boolean getSccpCreatesSls();

    HopCounter getHopCounter();

    void setCalledPartyAddress(SccpAddress v);

    void setCallingPartyAddress(SccpAddress v);

    void setHopCounter(HopCounter hopCounter);

    boolean reduceHopCounter();

}
