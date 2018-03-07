/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.time.LocalDateTime;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class TimeAndDate implements OpcodeValue {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "timeAndDate";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		LocalDateTime now = LocalDateTime.now();
		switch((String)arguments[0]) {
		case "year":
			return now.getYear();
		case "month":
			return now.getMonth().getValue();
		case "date":
			return now.getDayOfMonth();
		case "day of week":
			return now.getDayOfWeek().getValue();
		case "hour":
			return now.getHour();
		case "minute":
			return now.getMinute();
		case "second":
			return now.getSecond();
		default:
			System.err.println("WARNING: Unrecognized timeAndDate parameter: "+arguments[0]);
			return "";
		}
	}
}
