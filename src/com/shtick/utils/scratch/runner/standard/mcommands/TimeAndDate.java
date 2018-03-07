/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.mcommands;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.StageListener;
import com.shtick.utils.scratch.runner.core.StageMonitorCommand;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class TimeAndDate implements StageMonitorCommand {
	private static final int INDEX_SECOND = 0;
	private static final int INDEX_MINUTE = 1;
	private static final int INDEX_HOUR = 2;
	private static final int INDEX_DAY = 3;
	private static final int INDEX_MONTH = 4;
	private static final int INDEX_YEAR = 5;
	private LinkedList<ValueListener>[]  valueListeners = new LinkedList[]{
		new LinkedList<ValueListener>(),
		new LinkedList<ValueListener>(),
		new LinkedList<ValueListener>(),
		new LinkedList<ValueListener>(),
		new LinkedList<ValueListener>(),
		new LinkedList<ValueListener>()
	};
	private int lastReported[] = null;
	private int infrequencyLevel = -1;
	private Thread timerThread = new Thread(()->{
		while(infrequencyLevel>=0) {
			synchronized(valueListeners) {
				LocalDateTime now = LocalDateTime.now();
				int thenTargeted[] = new int[] {
						now.getSecond(),
						now.getMinute(),
						now.getHour(),
						now.getDayOfMonth(),
						now.getMonth().getValue(),
						now.getYear()
				};
				thenTargeted[infrequencyLevel]++;
				for(int i=0;i<infrequencyLevel;i++)
					thenTargeted[i]=0;
				LocalDateTime then = LocalDateTime.of(
						thenTargeted[INDEX_YEAR],
						thenTargeted[INDEX_MONTH],
						thenTargeted[INDEX_DAY],
						thenTargeted[INDEX_HOUR],
						thenTargeted[INDEX_MINUTE],
						thenTargeted[INDEX_SECOND],
						0);
				long waitTime = Duration.between(now, then).toMillis();
				try {
					valueListeners.wait(waitTime);
				}
				catch(InterruptedException t) {}
				changeTick();
			}
		}
	});
	
	
	/**
	 * The command implemented by this StageMonitorCommand.
	 */
	public static final String COMMAND = "timeAndDate";

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
		LocalDateTime now = LocalDateTime.now();
		switch(param) {
		case "year":
			return ""+now.getYear();
		case "month":
			return ""+now.getMonth().getValue();
		case "date":
			return ""+now.getDayOfMonth();
		case "day of week":
			return ""+now.getDayOfWeek();
		case "hour":
			return ""+now.getHour();
		case "minute":
			return ""+now.getMinute();
		case "second":
			return ""+now.getSecond();
		default:
			System.err.println("WARNING: Unrecognized timeAndDate parameter: "+param);
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		String arg = (String)valueListener.getArguments()[0];
		int frequency;
		switch(arg) {
		case "year":
			frequency = INDEX_YEAR;
			break;
		case "month":
			frequency = INDEX_MONTH;
			break;
		case "date":
			frequency = INDEX_DAY;
			break;
		case "day of week":
			frequency = INDEX_DAY;
			break;
		case "hour":
			frequency = INDEX_HOUR;
			break;
		case "minute":
			frequency = INDEX_MINUTE;
			break;
		case "second":
			frequency = INDEX_SECOND;
			break;
		default:
			System.err.println("WARNING: Unrecognized timeAndDate command parameter: "+arg);
			return;
		}
		synchronized(valueListeners) {
			valueListeners[frequency].add(valueListener);
			if(frequency<infrequencyLevel) {
				updateTimerInfrequency();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		String arg = (String)valueListener.getArguments()[0];
		int frequency;
		switch(arg) {
		case "year":
			frequency = INDEX_YEAR;
			break;
		case "month":
			frequency = INDEX_MONTH;
			break;
		case "date":
			frequency = INDEX_DAY;
			break;
		case "day of week":
			frequency = INDEX_DAY;
			break;
		case "hour":
			frequency = INDEX_HOUR;
			break;
		case "minute":
			frequency = INDEX_MINUTE;
			break;
		case "second":
			frequency = INDEX_SECOND;
			break;
		default:
			System.err.println("WARNING: Unrecognized timeAndDate command parameter: "+arg);
			return;
		}
		synchronized(valueListeners) {
			valueListeners[frequency].remove(valueListener);
			if((valueListeners[frequency].size()==0)&&(frequency==infrequencyLevel)){
				updateTimerInfrequency();
			}
		}
	}
	
	public void updateTimerInfrequency() {
		synchronized(valueListeners) {
			// Determine a new infrequency level. 
			int i;
			for(i=0;i<valueListeners.length;i++) {
				if(valueListeners[i].size()>0) {
					break;
				}
			}
			if(i>=valueListeners.length)
				i=-1;
			if(i==infrequencyLevel)
				return; // No change

			// End timer if necessary. 
			if(i<infrequencyLevel) {
				i=infrequencyLevel;
				valueListeners.notifyAll(); // Artificially trip timer and force recalculation of wait time.
			}
			else if(infrequencyLevel < 0) {
				// start new timer
				if(!timerThread.isAlive()) {
					timerThread.start();
				}
			}
			else {
				// Let the timer trip naturally and recalculate then.
				i=infrequencyLevel;
			}
		}
	}
	
	public void changeTick() {
		synchronized(valueListeners) {
			LocalDateTime now = LocalDateTime.now();
			int nowReported[] = new int[] {
					now.getSecond(),
					now.getMinute(),
					now.getHour(),
					now.getDayOfMonth(),
					now.getMonth().getValue(),
					now.getYear()
			};
			if(lastReported==null) {
				lastReported = nowReported;
				return;
			}
			for(int i=0;i<nowReported.length;i++) {
				if(lastReported[i]==nowReported[i])
					break;
				for(ValueListener valueListener:valueListeners[i])
					valueListener.valueUpdated(lastReported[i], nowReported[i]);
			}
		}
	}
}
