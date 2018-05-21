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

/**
 *
 * MW-Status ::= BIT STRING { sc-AddressNotIncluded (0), mnrf-Set (1), mcef-Set (2) , mnrg-Set (3)} (SIZE (6..16)) -- exception
 * handling: -- bits 4 to 15 shall be ignored if received and not understood
 *
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface MWStatus extends Serializable {

    boolean getScAddressNotIncluded();

    boolean getMnrfSet();

    boolean getMcefSet();

    boolean getMnrgSet();

}