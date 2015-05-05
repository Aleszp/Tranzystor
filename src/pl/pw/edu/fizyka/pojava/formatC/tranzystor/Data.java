package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import static org.junit.Assert.*;

import java.awt.HeadlessException;

import org.junit.Test;

/**
 *Contains all the model's data. With it's methods calculates currents for voltages.  
 *@author Aleksander Szpakiewicz-Szatan
 */
public class Data 
{
	int collectorEmitterVoltegeSteps;
	int baseEmitterVoltegeSteps;
	
	/**
	 * Index: <ul>
	 * <li> - start collector-emitter voltage value,</li>
	 * <li>1 - end collector-emitter voltage value;</li></ul>
	 */
	double collectorEmitterVoltageRange[];
	/**
	 * Index: <ul>
	 * <li>0 - start base-emitter voltage value,</li>
	 * <li>1 - end base-emitter voltage value;</li></ul>
	 */
	double baseEmitterVoltageRange[];
	
	double voltageCE[]; 
	
	double voltageBE[]; 
	/**
	 * Array of currents.  
	 * First index is collector-emitter voltage id;<br>
	 * Second index is base-emitter voltage id;<br>
	 * Third index is current voltage id where:<ul>
	 * 	<li>0 stands for base current,</li>
	 * 	<li>1 stands for collector current,</li>
	 * 	<li>2 stands for emitter current;</li></ul>	
	 * 
	 * @see #getBaseCurrent(int, int)
	 * @see #getCollectorCurrent(int, int)
	 * @see #getEmitterCurrent(int, int)
	 */
	double currents[][][];
	/**
	 * Array that represents transistor's h-matrix, indexes:<ul>
	 * <li>0 - h11 </li>
	 * <li>1 - h12 </li>
	 * <li>2 - h21 </li>
	 * <li>3 - h22 </li>
	 */
	double hMatrix[];
	
	double collectorEmitterVoltageMax;
	double baseEmitterVoltageMax;
	double baseCurrentMax;
	double collectorCurrentMax;
	double emitterCurrentMax;
	double saturationVoltage;
	double saturationCurrent;
	double fitParameter;
	
	
	/**
	 * Use {@link #Data(int, int, double[], double[])} as constructor.
	 * 
	 * @param collectorEmitterVoltegeSteps_ number of steps for collector-emitter voltage value
	 * @param baseEmitterVoltegeSteps_ number of steps for base-emitter voltage value
	 * @param collectorEmitterVoltageRange_   array:<ul><li> 0 - start collector-emitter voltage value,</li> <li>1 - end collector-emitter voltage value</li></ul>
	 * @param baseEmitterVoltageRange_   array:<ul><li> 0 - start base-emitter voltage value, </li> <li>1 - end base-emitter voltage value</li></ul>
	 * 
	 * @see #collectorEmitterVoltageRange
	 * @see #baseEmitterVoltageRange
	 */
	
	/*public Data(int collectorEmitterVoltegeSteps_,int baseEmitterVoltegeSteps_, double collectorEmitterVoltageRange_[], double baseEmitterVoltageRange_[]) throws HeadlessException 
	{
		//Wczytaj dane początkowe
		collectorEmitterVoltegeSteps=collectorEmitterVoltegeSteps_;
		baseEmitterVoltegeSteps=baseEmitterVoltegeSteps_;
		collectorEmitterVoltageRange=new double[2];
		baseEmitterVoltageRange=new double[2];
		collectorEmitterVoltageRange[0]=collectorEmitterVoltageRange_[0];
		collectorEmitterVoltageRange[1]=collectorEmitterVoltageRange_[1];
		baseEmitterVoltageRange[0]=baseEmitterVoltageRange_[0];
		baseEmitterVoltageRange[1]=baseEmitterVoltageRange_[1];
		
		createArrays();
	}*/
	
	/**
	 * Use {@link #Data()} as default (for test purposes) constructor. 
	 */
	public Data() throws HeadlessException 
	{
		
		collectorEmitterVoltegeSteps=40;
		baseEmitterVoltegeSteps=200;
		collectorEmitterVoltageRange=new double[2];
		baseEmitterVoltageRange=new double[2];
		collectorEmitterVoltageRange[0]=0;
		collectorEmitterVoltageRange[1]=5;
		baseEmitterVoltageRange[0]=0;
		baseEmitterVoltageRange[1]=5;
		
		createArrays();
	}
	
	/**
	 * Use {@link #createArrays()} to create arrays for data to be hold in.
	 */
	public void createArrays()
	{
		hMatrix=new double[4];
		voltageCE=new double[collectorEmitterVoltegeSteps];
		voltageBE=new double[baseEmitterVoltegeSteps];
		currents=new double[collectorEmitterVoltegeSteps][baseEmitterVoltegeSteps][3];
	}
	
	/**
	 * Use {@link #countCurrentsForSingleStep(int,int)} to calculate base, collector and emitter currents and put results in class'es array of currents.
	 * @param collectorEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmittervoltageStepId base-emitter voltage id - determines which value from array to use
	 */
	public void countCurrentsForSingleStep(int collectorEmittervoltageStepId, int baseEmittervoltageStepId)
	{		
		//If base-emitter voltage is lower than saturation base-emitter voltage treat it as non-linear 
		if(voltageBE[baseEmittervoltageStepId]<saturationVoltage)
		{
			currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]=saturationCurrent*(Math.exp(voltageBE[baseEmittervoltageStepId]/(fitParameter*0.026))-1);
		}
		else //If base-emitter voltage is greater than saturation base-emitter voltage treat it as linear
		{
			currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]=(voltageCE[collectorEmittervoltageStepId]-voltageBE[baseEmittervoltageStepId]*hMatrix[1])/hMatrix[0];
			currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]+=saturationCurrent*Math.exp(saturationVoltage/(fitParameter*0.026));
		}
		currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][1]=hMatrix[2]*currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]+hMatrix[3]*voltageCE[collectorEmittervoltageStepId];
		currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][2]=currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]+currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][1];	
	}
	
	@Test
	public void test()
	{
		Data dataContainer=new Data();
		
		int collectorEmittervoltageStepId=39;
		int baseEmittervoltageStepId=2;
		
		saturationVoltage=0.25;
		saturationCurrent=0.033;
		fitParameter=5;
		hMatrix[0]=5000;
		hMatrix[1]=0.00016;
		hMatrix[2]=380;	
		hMatrix[3]=50*Math.pow(10,-9);
		
		voltageCE[0]=0;
		voltageBE[0]=0;
		for(int ii=0;ii<40;ii++)
		{
			for(int jj=0;jj<200;jj++)
			{
				dataContainer.countCurrentsForSingleStep(ii,jj);
				if(ii<199)voltageBE[ii+1]=voltageBE[ii]+0.025;
			}
			if(ii<39)voltageCE[ii+1]=voltageCE[ii]+0.25;
		}

		assertEquals(saturationCurrent*(Math.exp(voltageBE[baseEmittervoltageStepId]/(fitParameter*0.026))-1),0.015,0.001); //jest ok
		assertEquals(getBaseCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId), 0.015, 0.001); //powinien być ten sam wynik co w poprzednim, ale jest inny
		assertEquals(getCollectorCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId), 5.882, 0.001); // ??
		assertEquals(getEmitterCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId), 5.898, 0.001);   // ??
	}
	
	/**
	 * Use {@link #getBaseCurrent(int, int)}
	 * @param collectorEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @return base current value
	 */
	public double getBaseCurrent(int collectorEmittervoltageStepId,int baseEmittervoltageStepId)
	{
		return currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0];
	}
	/**
	 * Use {@link #getCollectorCurrent(int, int)}
	 * @param collectorEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @return collector current value
	 */
	public double getCollectorCurrent(int collectorEmittervoltageStepId,int baseEmittervoltageStepId)
	{
		return currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][1];
	}
	/**
	 * Use {@link #getEmitterCurrent(int, int)}
	 * @param collectorEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @return emitter current value
	 */
	public double getEmitterCurrent(int collectorEmittervoltageStepId,int baseEmittervoltageStepId)
	{
		return currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][2];
	}
		
}