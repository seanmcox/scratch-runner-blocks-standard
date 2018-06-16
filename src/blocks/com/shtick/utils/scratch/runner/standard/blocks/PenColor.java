/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

/**
 * @author sean.cox
 *
 */
public class PenColor extends SetPenHueTo {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "penColor:";
	}
}
