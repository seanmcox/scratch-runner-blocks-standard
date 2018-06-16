package com.shtick.utils.scratch.runner.standard.bundle;

import java.util.Hashtable;
import java.util.LinkedList;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.shtick.utils.scratch.runner.core.GraphicEffect;
import com.shtick.utils.scratch.runner.core.Opcode;
import com.shtick.utils.scratch.runner.core.StageMonitorCommand;
import com.shtick.utils.scratch.runner.standard.StandardBlocksExtensions;

/**
 **/
public class Activator implements BundleActivator {
	private LinkedList<ServiceRegistration<?>> runtimeRegistrations=new LinkedList<>();
	
    /**
     * Implements BundleActivator.start(). Prints
     * a message and adds itself to the bundle context as a service
     * listener.
     * @param context the framework context for the bundle.
     **/
    @Override
	public void start(BundleContext context){
		System.out.println(this.getClass().getCanonicalName()+": Starting.");
		synchronized(runtimeRegistrations) {
			for(Opcode opcode:StandardBlocksExtensions.STANDARD_OPCODES)
				runtimeRegistrations.add(context.registerService(Opcode.class.getName(), opcode,new Hashtable<String, String>()));
			
			for(GraphicEffect graphicEffect:StandardBlocksExtensions.STANDARD_GRAPHIC_EFFECTS)
				runtimeRegistrations.add(context.registerService(GraphicEffect.class.getName(), graphicEffect,new Hashtable<String, String>()));

			for(StageMonitorCommand stageMonitorCommand:StandardBlocksExtensions.STANDARD_STAGE_MONITOR_COMMANDS)
				runtimeRegistrations.add(context.registerService(StageMonitorCommand.class.getName(), stageMonitorCommand,new Hashtable<String, String>()));
		}
    }

    /**
     * Implements BundleActivator.stop(). Prints
     * a message and removes itself from the bundle context as a
     * service listener.
     * @param context the framework context for the bundle.
     **/
    @Override
	public void stop(BundleContext context){
		System.out.println(this.getClass().getCanonicalName()+": Stopping.");
		synchronized(runtimeRegistrations) {
			for(ServiceRegistration<?> runtimeRegistration:runtimeRegistrations)
				runtimeRegistration.unregister();
			runtimeRegistrations.clear();
		}
    }

}