/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.mcommands;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.StageMonitorCommand;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class GetVar implements StageMonitorCommand {
	/**
	 * The command implemented by this StageMonitorCommand.
	 */
	public static final String COMMAND = "getVar:";

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#getCommand()
	 */
	@Override
	public String getCommand() {
		return COMMAND;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, java.lang.String)
	 */
	@Override
	public String execute(ScratchRuntime runtime, ScriptContext context, String param) {
		Object value = context.getContextVariableValueByName(param);
		if(value instanceof Number) {
			Number number = (Number) value;
			if((number instanceof Double)&&(number.doubleValue() == Math.round(number.doubleValue())))
				return ""+number.longValue();
		}
		return value.toString();
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		String name = (String)valueListener.getArguments()[0];
		valueListener.getScriptContext().addVariableListener(name, valueListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		String name = (String)valueListener.getArguments()[0];
		valueListener.getScriptContext().removeVariableListener(name, valueListener);
	}
}
