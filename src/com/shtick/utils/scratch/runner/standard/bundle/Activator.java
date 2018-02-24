package com.shtick.utils.scratch.runner.standard.bundle;

import java.util.Hashtable;
import java.util.LinkedList;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.shtick.utils.scratch.runner.core.GraphicEffect;
import com.shtick.utils.scratch.runner.core.Opcode;
import com.shtick.utils.scratch.runner.core.StageMonitorCommand;
import com.shtick.utils.scratch.runner.standard.blocks.*;
import com.shtick.utils.scratch.runner.standard.effects.BrightnessEffect;
import com.shtick.utils.scratch.runner.standard.effects.GhostEffect;
import com.shtick.utils.scratch.runner.standard.effects.MosaicEffect;
import com.shtick.utils.scratch.runner.standard.mcommands.GetVar;

/**
 **/
public class Activator implements BundleActivator {
	/**
	 * WhenIReceive opcode to share.
	 */
	public static final WhenIReceive WHEN_I_RECEIVE = new WhenIReceive();
	/**
	 * 
	 */
	public static final ProcDef PROC_DEF = new ProcDef();
	
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
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _DividedBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _Equals(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _GreaterThan(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _LessThan(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _LogicalAnd(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _LogicalOr(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _Minus(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _Modulus(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _Plus(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new _Times(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Abs(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Answer(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new AppendToList(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new BackgroundIndex(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new BounceOffEdge(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Broadcast(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Call(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangeGraphicEffectBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangePenHueBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangePenShadeBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangePenSizeBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangeSizeBy(),new Hashtable<String, String>()));
			// TODO changeTempoBy:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangeVarBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangeVolumeBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangeXPosBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ChangeYPosBy(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ClearPenTrails(),new Hashtable<String, String>()));
			// TODO color:sees:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ComeToFront(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ComputeFunctionOf(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ConcatenateWith(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ContentsOfList(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new CostumeIndex(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new CreateCloneOf(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DeleteClone(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DeleteLineOfList(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DistanceTo(),new Hashtable<String, String>()));
			// TODO doAsk
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoBroadcastAndWait(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoForever(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoForeverIf(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoForLoop(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoIf(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoIfElse(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoPlaySoundAndWait(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoRepeat(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoReturn(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoUntil(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new DoWaitUntil(),new Hashtable<String, String>()));
			// Don't implement doWhile. It does not appear to be spec'd and in use.
			// TODO drum:duration:elapsed:from:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new FilterReset(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Forward(),new Hashtable<String, String>()));
			// Don't implement fxTest
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GetAttributeOf(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GetLineOfList(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GetParam(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GetUserId(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GetUserName(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GlideSecsToXYElapsedFrom(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GoBackByLayers(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GotoSpriteOrMouse(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new GotoXY(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Heading(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new HeadingSet(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Hide(),new Hashtable<String, String>()));
			// TODO hideAll
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new HideList(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new HideVariable(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new InsertAtOfList(),new Hashtable<String, String>()));
			// TODO instrument:
			// TODO isLoud
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new KeyPressed(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new LetterOf(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new LineCountOfList(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ListContains(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new LookLike(),new Hashtable<String, String>()));
			// TODO midiInstrument:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new MousePressed(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new MouseX(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new MouseY(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new NextCostume(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new NextScene(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Not(),new Hashtable<String, String>()));
			// TODO noteOn:duration:elapsed:from:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new PenColor(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new PenSize(),new Hashtable<String, String>()));
			// TODO playDrum
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new PlaySound(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new PointTowards(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), PROC_DEF,new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new PutPenDown(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new PutPenUp(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new RandomFromTo(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ReadVariable(),new Hashtable<String, String>()));
			// TODO rest:elapsed:from:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Rounded(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Say(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SayDurationElapsedFrom(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SayNothing(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Scale(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SceneName(),new Hashtable<String, String>()));
			// Don't implement scrollAlign. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
			// Don't implement scrollRight. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
			// Don't implement scrollUp. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
			// TODO senseVideoMotion
			// TODO sensor:
			// TODO sensorPressed:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SetGraphicEffectTo(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SetLineOfListTo(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SetPenHueTo(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SetPenShadeTo(),new Hashtable<String, String>()));
			// TODO setRotationStyle
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SetSizeTo(),new Hashtable<String, String>()));
			// TODO setTempoTo:
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SetVarTo(),new Hashtable<String, String>()));
			// TODO setVideoState
			// TODO setVideoTransparency
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new SetVolumeTo(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Show(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ShowList(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ShowVariable(),new Hashtable<String, String>()));
			// TODO soundLevel
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Sqrt(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new StampCostume(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new StartScene(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new StartSceneAndWait(),new Hashtable<String, String>()));
			// No longer current: stopAll See: https://wiki.scratch.mit.edu/wiki/Stop_All_(block)
			// TODO stopAllSounds
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new StopScripts(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new StringLength(),new Hashtable<String, String>()));
			// TODO tempo
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Think(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new ThinkDurationElapsedFrom(),new Hashtable<String, String>()));
			// TODO timeAndDate
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Timer(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new TimerReset(),new Hashtable<String, String>()));
			// TODO timestamp
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Touching(),new Hashtable<String, String>()));
			// TODO touchingColor:
			// No longer current: turnAwayFromEdge See: https://wiki.scratch.mit.edu/wiki/Point_Away_From_Edge_(block)
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new TurnLeft(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new TurnRight(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Volume(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new WaitElapsedFrom(),new Hashtable<String, String>()));
			// Don't implement warpSpeed. See: https://wiki.scratch.mit.edu/wiki/All_at_Once_(block)
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new WhenClicked(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new WhenCloned(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new WhenGreenFlag(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), WHEN_I_RECEIVE,new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new WhenKeyPressed(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new WhenSceneStarts(),new Hashtable<String, String>()));
			// TODO whenSensorGreaterThan
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Xpos(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new XposSet(),new Hashtable<String, String>()));
			// Don't implement xScroll. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new Ypos(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(Opcode.class.getName(), new YposSet(),new Hashtable<String, String>()));
			// Don't implement yScroll. See: https://wiki.scratch.mit.edu/wiki/Scrolling_(Stage)
			
			runtimeRegistrations.add(context.registerService(GraphicEffect.class.getName(), new BrightnessEffect(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(GraphicEffect.class.getName(), new GhostEffect(),new Hashtable<String, String>()));
			runtimeRegistrations.add(context.registerService(GraphicEffect.class.getName(), new MosaicEffect(),new Hashtable<String, String>()));

			runtimeRegistrations.add(context.registerService(StageMonitorCommand.class.getName(), new GetVar(),new Hashtable<String, String>()));
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