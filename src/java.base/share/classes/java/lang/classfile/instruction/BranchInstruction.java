/*
 * Copyright (c) 2022, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package java.lang.classfile.instruction;

import java.lang.classfile.*;

import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.classfile.impl.Util;

/**
 * Models a branching instruction (conditional or unconditional) in the {@code
 * code} array of a {@code Code} attribute.  Corresponding opcodes have a
 * {@linkplain Opcode#kind() kind} of {@link Opcode.Kind#BRANCH}.  Delivered as
 * a {@link CodeElement} when traversing the elements of a {@link CodeModel}.
 * <p>
 * A branch instruction is composite:
 * {@snippet lang=text :
 * // @link substring="BranchInstruction" target="#of":
 * BranchInstruction(
 *     Opcode opcode, // @link substring="opcode" target="#opcode()"
 *     Label target // @link substring="target" target="#target()"
 * )
 * }
 * <p>
 * Due to physical restrictions, some types of instructions cannot encode labels
 * too far away in the list of code elements.  In such cases, the {@link
 * ClassFile.ShortJumpsOption} controls how an invalid branch instruction model
 * is written by a {@link CodeBuilder}.
 *
 * @see Opcode.Kind#BRANCH
 * @see CodeBuilder#branch CodeBuilder::branch
 * @see ClassFile.ShortJumpsOption
 * @since 24
 */
public sealed interface BranchInstruction extends Instruction
        permits AbstractInstruction.BoundBranchInstruction,
                AbstractInstruction.UnboundBranchInstruction {
    /**
     * {@return the branch target of this instruction}
     */
    Label target();

    /**
     * {@return a branch instruction}
     *
     * @param op the opcode for the specific type of branch instruction,
     *           which must be of kind {@link Opcode.Kind#BRANCH}
     * @param target the target of the branch
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#BRANCH}
     */
    static BranchInstruction of(Opcode op, Label target) {
        Util.checkKind(op, Opcode.Kind.BRANCH);
        return new AbstractInstruction.UnboundBranchInstruction(op, target);
    }
}
