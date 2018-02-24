/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Arrays;
import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.OpcodeHat;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;

/**
 * <p>This opcode isn't well documented. Somewhat irritatingly, the definition derived from inspection breaks the conventions of the ScriptTuple encoding.
 * In particular, it has has array parameters that are not BlockTuples or ScriptTuples.
 * The third parameter, in particular cannot even reliably be treated as if it were a BlockTuple, because its first element cannot be
 * relied on to be a String.</p>
 *
 * <p>See:
 * <ul>
 * <li>https://scratch.mit.edu/discuss/topic/77188/?page=1#post-826804</li>
 * <li>https://wiki.scratch.mit.edu/wiki/JSON_Tutorial</li>
 * <li>https://scratch.mit.edu/discuss/topic/1810/?page=7</li>
 * </ul>
 * </p>
 * 
 * @author sean.cox
 *
 */
public class ProcDef implements OpcodeHat {
	private HashMap<String,HashMap<String,ProcedureDefinition>> listenersByContextObject = new HashMap<>();
	private ScratchRuntime runtime;

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "procDef";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING,DataType.TUPLE,DataType.TUPLE,DataType.BOOLEAN};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#applicationStarted(com.shtick.utils.scratch.runner.core.ScratchRuntime)
	 */
	@Override
	public void applicationStarted(ScratchRuntime runtime) {
		this.runtime = runtime;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#registerListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, java.lang.Object[])
	 */
	@Override
	public void registerListeningScript(ScriptTuple script, Object[] params) {
		String s0 = (String)params[0];
		Object[] a1 = (Object[])params[1];
		Object[] a2 = (Object[])params[2];
		boolean b3 = (Boolean)params[3];
		if(a1.length!=a2.length)
			throw new IllegalArgumentException(getOpcode()+" opcode requires that the second and third arguments have matching length.");
		String[] s1 = new String[a1.length];
		for(int i=0;i<a1.length;i++) {
			if(!(a1[i] instanceof String))
				throw new IllegalArgumentException(getOpcode()+" opcode requires that the second argument be an array of only Strings.");
			s1[i] = (String)a1[i];
		}

		ScriptContext contextObject = script.getContext().getContextObject();
		synchronized(listenersByContextObject) {
			if(!listenersByContextObject.containsKey(contextObject.getObjName()))
				listenersByContextObject.put(contextObject.getObjName(), new HashMap<>());
			listenersByContextObject.get(contextObject.getObjName()).put(s0,new ProcedureDefinition(s0, s1, script, b3));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#unregisterListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple)
	 */
	@Override
	public void unregisterListeningScript(ScriptTuple script, Object[] params) {
		String s0 = (String)params[0];
		ScriptContext contextObject = script.getContext().getContextObject();
		synchronized(listenersByContextObject) {
			if(!listenersByContextObject.containsKey(contextObject.getObjName()))
				return;
			HashMap<String,ProcedureDefinition> listeners = listenersByContextObject.get(contextObject.getObjName());
			listeners.remove(s0);
			if(listeners.size()==0)
				listenersByContextObject.remove(contextObject.getObjName());
		}
	}
	
	/**
	 * @param context
	 * @param procName 
	 * @param params 
	 * @param runner 
	 * 
	 */
	public void call(ScriptContext context, String procName, Object[] params, ScriptTupleRunner runner) {
		if(procName.equals("Collateral Checks_pr %n %s %b")||procName.equals("Add to grow %n %n")) {
			if(context instanceof ProcDef.ProcedureContext)
				System.out.println(((ProcDef.ProcedureContext)context).getProcName());
			System.out.println("Called: "+procName+" - "+Arrays.toString(params));
			System.out.flush();
		}
		ScriptContext contextObject = context.getContextObject();
		ProcedureDefinition procDef;
		synchronized(listenersByContextObject) {
			if(!listenersByContextObject.containsKey(contextObject.getObjName())) {
				String objectString = ""+contextObject;
				if(contextObject != null)
					objectString = "\""+contextObject.getObjName()+"\" - "+contextObject;
				throw new IllegalArgumentException("The provided context object has no procedures defined: "+objectString);
			}
			procDef = listenersByContextObject.get(contextObject.getObjName()).get(procName);
		}
		if(procDef==null) {
			String objectString = "\""+contextObject.getObjName()+"\" - "+contextObject;
			throw new IllegalArgumentException("No script defined with the given name, "+procName+", within the context of "+objectString);
		}
		Object[] parameters = new Object[params.length];
		for(int i=0;i<parameters.length;i++)
			parameters[i] = params[i];
		
		ScriptTupleRunner childRunner = runtime.startScript(new ScriptTuple() {
			ProcedureContext procedureContext = new ProcedureContext(context, procDef.getProcName(), procDef.getParamNames(), parameters);
			
			@Override
			public Object[] toArray() {
				return procDef.script.toArray();
			}
			
			@Override
			public ScriptContext getContext() {
				return procedureContext;
			}
			
			@Override
			public java.util.List<BlockTuple> getBlockTuples() {
				return procDef.script.getBlockTuples();
			}
			
			@Override
			public int getBlockTupleCount() {
				return procDef.script.getBlockTupleCount();
			}
			
			@Override
			public BlockTuple getBlockTuple(int index) {
				return procDef.script.getBlockTuple(index);
			}
		}, procDef.isAtomic());
		try {
			childRunner.join();
		}
		catch(InterruptedException t) {
		}
	}

	private class ProcedureDefinition{
		private String procName;
		private String[] paramNames;
		private ScriptTuple script;
		private boolean isAtomic;
		
		/**
		 * @param procName 
		 * @param paramNames
		 * @param script
		 * @param isAtomic
		 */
		public ProcedureDefinition(String procName, String[] paramNames, ScriptTuple script, boolean isAtomic) {
			super();
			this.procName = procName;
			this.paramNames = paramNames;
			this.script = script;
			this.isAtomic = isAtomic;
		}

		/**
		 * @return the procName
		 */
		public String getProcName() {
			return procName;
		}

		/**
		 * @return the paramNames
		 */
		public String[] getParamNames() {
			return paramNames;
		}

		/**
		 * @return the script
		 */
		public ScriptTuple getScript() {
			return script;
		}

		/**
		 * @return the isAtomic
		 */
		public boolean isAtomic() {
			return isAtomic;
		}
	}


	/**
	 * @author sean.cox
	 *
	 */
	public static class ProcedureContext implements ScriptContext{
		private ScriptContext parentContext;
		private HashMap<String,Object> parameters;
		private String procName;

		/**
		 * @param parentContext
		 * @param procName 
		 * @param paramNames 
		 * @param paramValues Values. Not BlockTuples
		 */
		public ProcedureContext(ScriptContext parentContext, String procName, String[] paramNames, Object[] paramValues) {
			this.parentContext = parentContext;
			this.procName = procName;
			parameters = new HashMap<>(paramNames.length);
			for(int i=0;i<paramNames.length;i++)
				parameters.put(paramNames[i], paramValues[i]);
		}
		
		/**
		 * @return the procName
		 */
		public String getProcName() {
			return procName;
		}

		/**
		 * 
		 * @param name
		 * @return The value of the given parameter or null if it is not defined.
		 */
		public Object getParameterValueByName(String name) {
			return parameters.get(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return parentContext.getContextObject();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getObjName()
		 */
		@Override
		public String getObjName() {
			return getContextObject().getObjName();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextListByName(java.lang.String)
		 */
		@Override
		public List getContextListByName(String name) {
			return parentContext.getContextListByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextVariableValueByName(java.lang.String)
		 */
		@Override
		public Object getContextVariableValueByName(String name) throws IllegalArgumentException {
			return parentContext.getContextVariableValueByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#setContextVariableValueByName(java.lang.String, java.lang.Object)
		 */
		@Override
		public void setContextVariableValueByName(String name, Object value) throws IllegalArgumentException {
			parentContext.setContextVariableValueByName(name, value);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextPropertyValueByName(java.lang.String)
		 */
		@Override
		public Object getContextPropertyValueByName(String name) throws IllegalArgumentException {
			return parentContext.getContextPropertyValueByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#playSoundByName(java.lang.String, boolean)
		 */
		@Override
		public void playSoundByName(String soundName, boolean block) {
			parentContext.playSoundByName(soundName, block);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#setVolume(int)
		 */
		@Override
		public void setVolume(double volume) {
			parentContext.setVolume(volume);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getVolume()
		 */
		@Override
		public double getVolume() {
			return parentContext.getVolume();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#stopThreads()
		 */
		@Override
		public void stopThreads() {
			parentContext.stopThreads();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getThreadGroup()
		 */
		@Override
		public ThreadGroup getThreadGroup() {
			return parentContext.getThreadGroup();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#addVariableListener(java.lang.String, com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void addVariableListener(String var, ValueListener listener) {
			parentContext.addVariableListener(var, listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#removeVariableListener(java.lang.String, com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void removeVariableListener(String var, ValueListener listener) {
			parentContext.removeVariableListener(var, listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#addContextPropertyListener(java.lang.String, com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void addContextPropertyListener(String property, ValueListener listener) {
			parentContext.addContextPropertyListener(property, listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#removeContextPropertyListener(java.lang.String, com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void removeContextPropertyListener(String property, ValueListener listener) {
			parentContext.removeContextPropertyListener(property, listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#addVolumeListener(com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void addVolumeListener(ValueListener listener) {
			parentContext.addVolumeListener(listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#removeVolumeListener(com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void removeVolumeListener(ValueListener listener) {
			parentContext.removeVolumeListener(listener);
		}
		
	}
}
