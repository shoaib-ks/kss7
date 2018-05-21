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

package org.mobicents.protocols.ss7.isup.message.parameter;

import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.isup.ParameterException;


/**
 * @author baranowb
 * @author sergey vetyutnev
 *
 */
public enum GeneralProblemType {

    /**
     * The component type is not recognized as being one of those defined in 3.1. (Invoke, ReturnResult, ReturnResultLast,
     * ReturnError, Reject) This code is generated by the TCAP layer.
     */
    UnrecognizedComponent(0),

    /**
     * The elemental structure of a component does not conform to the structure of that component as defined in 3.1/Q.773. This
     * code is generated by the TCAP layer.
     */
    MistypedComponent(1),

    /**
     * The contents of the component do not conform to the encoding rules defined in 4.1/Q.773. This code is generated by the
     * TCAP layer.
     */
    BadlyStructuredComponent(2);

    private long type = -1;

    public static final int _TAG_CLASS = Tag.CLASS_APPLICATION;
    public static final boolean _TAG_PC_PRITIMITIVE = true;

    GeneralProblemType(long l) {
        this.type = l;
    }

    /**
     * @return the type
     */
    public long getType() {
        return type;
    }

    public static GeneralProblemType getFromInt(long t) throws ParameterException {
        if (t == 0) {
            return UnrecognizedComponent;
        } else if (t == 1) {
            return MistypedComponent;
        } else if (t == 2) {
            return BadlyStructuredComponent;
        }

        throw new ParameterException("Wrong value of type: " + t);
    }
}
