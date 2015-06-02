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
	
	double maxBaseCurrent;
	double maxCollectorCurrent;
	double maxEmitterCurrent;
	double maxVoltageBE;
	double maxVoltageCE;
	double maxVoltageCB;
	
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
	
	//Commented out in order to make tests not throw "Test class can only have one constructor" error, especially as they demand it to have no parameters
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
		collectorEmitterVoltageRange=new double[2];
		baseEmitterVoltageRange=new double[2];
		fitParameter=3;
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
	 * Use {@link #createArrays(double, double, double, double)} to fill arrays of voltages.
	 * @param collectorEmitterStartVoltege - collector-emitter voltage value for start of simulation
	 * @param collectorEmitterEndVoltege - collector-emitter voltage value for end of simulation
	 * @param baseEmitterStartVoltege - base-emitter voltage value for start of simulation
	 * @param baseEmitterEndVoltege - base-emitter voltage value for end of simulation
	 */
	public void fillVoltageArrays(double collectorEmitterStartVoltege,double collectorEmitterEndVoltege,double baseEmitterStartVoltege,double baseEmitterEndVoltege)
	{
		voltageCE=new double[collectorEmitterVoltegeSteps];
		voltageBE=new double[baseEmitterVoltegeSteps];
		currents=new double[collectorEmitterVoltegeSteps][baseEmitterVoltegeSteps][3];
		voltageCE[0]=collectorEmitterStartVoltege;
		voltageBE[0]=baseEmitterStartVoltege;
		double collectorEmitterStep=(collectorEmitterEndVoltege-collectorEmitterStartVoltege)/(collectorEmitterVoltegeSteps-1);
		double baseEmitterStep=(baseEmitterEndVoltege-baseEmitterStartVoltege)/(baseEmitterVoltegeSteps-1);
		for(int ii=1;ii<collectorEmitterVoltegeSteps;ii++)
		{
			voltageCE[ii]=voltageCE[ii-1]+collectorEmitterStep;
			System.out.println(voltageCE[ii]);
		}
		for(int jj=1;jj<baseEmitterVoltegeSteps;jj++)
		{
			voltageBE[jj]=voltageBE[jj-1]+baseEmitterStep;
			System.out.println(voltageBE[jj]);
		}
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
			setBaseCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,saturationCurrent*(Math.exp(voltageBE[baseEmittervoltageStepId]/(fitParameter*0.026))-1));
		}
		else //If base-emitter voltage is greater than saturation base-emitter voltage treat it as linear
		{
			double tmp=(voltageCE[collectorEmittervoltageStepId]-voltageBE[baseEmittervoltageStepId]*hMatrix[1])/hMatrix[0];
			tmp+=saturationCurrent*Math.exp(saturationVoltage/(fitParameter*0.026));
			setBaseCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,tmp);
		}
		setCollectorCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,hMatrix[2]*currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]+hMatrix[3]*voltageCE[collectorEmittervoltageStepId]);
		setEmitterCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]+currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][1]);
			
	}
	public void checkMaximumValues(int collectorEmittervoltageStepId, int baseEmittervoltageStepId)
	{
		if(voltageBE[baseEmittervoltageStepId]>maxVoltageBE||
				voltageCE[collectorEmittervoltageStepId]>maxVoltageCE||
				(voltageCE[collectorEmittervoltageStepId]-voltageBE[baseEmittervoltageStepId])>maxVoltageCB||
				getBaseCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId)>maxBaseCurrent||
				getCollectorCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId)>maxCollectorCurrent||
				getEmitterCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId)>maxEmitterCurrent)
		{
			setBaseCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,0);
			setCollectorCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,0);
			setEmitterCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,0);
		}
	}
	
	/**
	 * Test method countCurrentsForSingleStep
	 * @see #countCurrentsForSingleStep 
	 */
	@Test
	public void testCountCurrentsForSingleStep()
	{
		Data dataContainer=new Data();
		
		int collectorEmittervoltageStep=39;
		int baseEmittervoltageStep=2;
		
		dataContainer.saturationVoltage=0.25;
		dataContainer.saturationCurrent=0.033;
		dataContainer.fitParameter=5;
		dataContainer.hMatrix[0]=5000;
		dataContainer.hMatrix[1]=0.00016;
		dataContainer.hMatrix[2]=380;	
		dataContainer.hMatrix[3]=50*Math.pow(10,-9);
		
		dataContainer.voltageCE[0]=0;
		dataContainer.voltageBE[0]=0;
		
		for(int ii=0;ii<40;ii++)
		{
			for(int jj=0;jj<200;jj++)
			{
				dataContainer.countCurrentsForSingleStep(ii,jj);
				if(ii<199)dataContainer.voltageBE[ii+1]=dataContainer.voltageBE[ii]+0.025;
			}
			if(ii<39)dataContainer.voltageCE[ii+1]=dataContainer.voltageCE[ii]+0.25;
		}
			
		double current;
		if(dataContainer.voltageBE[baseEmittervoltageStep]<dataContainer.saturationVoltage)
		{
			current=dataContainer.saturationCurrent*(Math.exp(dataContainer.voltageBE[baseEmittervoltageStep]/(dataContainer.fitParameter*0.026))-1);
		}
		else //If base-emitter voltage is greater than saturation base-emitter voltage treat it as linear
		{
			current=(dataContainer.voltageCE[collectorEmittervoltageStep]-dataContainer.voltageBE[baseEmittervoltageStep]*dataContainer.hMatrix[1])/dataContainer.hMatrix[0];
			current+=dataContainer.saturationCurrent*(Math.exp(dataContainer.saturationVoltage/(dataContainer.fitParameter*0.026))-1);
		}
		assertEquals(current,0.015,0.001); //When calculates here, than it's ok
		assertEquals(dataContainer.getBaseCurrent(collectorEmittervoltageStep,baseEmittervoltageStep), 0.015, 0.001); //fixed 
		assertEquals(dataContainer.getCollectorCurrent(collectorEmittervoltageStep,baseEmittervoltageStep), 5.882, 0.001); //fixed
		assertEquals(dataContainer.getEmitterCurrent(collectorEmittervoltageStep,baseEmittervoltageStep), 5.898, 0.001);   //fixed
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
	 * Use {@link #setBaseCurrent(int, int, double)}
	 * @param collectorEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param base current new value
	 */
	public void setBaseCurrent(int collectorEmittervoltageStepId,int baseEmittervoltageStepId, double value)
	{
		currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][0]=value;
	}
	
	/**
	 * Test setter and getter for base current 
	 * @see getBaseCurrent
	 * @see setBaseCurrent
	 */
	@Test
	public void testSetAndGetBaseCurrent()
	{
		int collectorEmittervoltageStepId=5;
		int baseEmittervoltageStepId=10;
		double testValue=0.45;
		setBaseCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,testValue);
		assertEquals(getBaseCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId), testValue, 0.001);
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
	 * Use {@link #setCollectorCurrent(int, int, double)}
	 * @param collectorEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param collector current new value
	 */
	public void setCollectorCurrent(int collectorEmittervoltageStepId,int baseEmittervoltageStepId, double value)
	{
		currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][1]=value;
	}
	
	/**
	 * Test setter and getter for collector current 
	 * @see getCollectorCurrent
	 * @see setCollectorCurrent
	 */
	@Test
	public void testSetAndGetCollectorCurrent()
	{
		int collectorEmittervoltageStepId=5;
		int baseEmittervoltageStepId=10;
		double testValue=7.62;
		setCollectorCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,testValue);
		assertEquals(getCollectorCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId), testValue, 0.001);
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
	/**
	 * Use {@link #setEmitterCurrent(int, int, double)}
	 * @param collectorEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmittervoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param emitter current new value
	 */
	public void setEmitterCurrent(int collectorEmittervoltageStepId,int baseEmittervoltageStepId, double value)
	{
		currents[collectorEmittervoltageStepId][baseEmittervoltageStepId][2]=value;
	}
	
	/**
	 * Test setter and getter for emitter current
	 * @see getEmitterCurrent
	 * @see setEmitterCurrent 
	 */
	@Test
	public void testSetAndGetEmitterCurrent()
	{
		int collectorEmittervoltageStepId=5;
		int baseEmittervoltageStepId=10;
		double testValue=3.14;
		setEmitterCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId,testValue);
		assertEquals(getEmitterCurrent(collectorEmittervoltageStepId,baseEmittervoltageStepId), testValue, 0.001);
	}
	
	/**
	 * Use {@link #LoadArray(InterFace)}
	 * @param interFace - InterFace frame which holds hybrid matrix
	 */
	public void loadArray(InterFace interFace)
	{
		for(int ii=0;ii<4;ii++)
			hMatrix[ii]=interFace.hybridMatrix.getH(ii);
	}
	/**
	 * Use {@link #setMaximumValues(ValuePanel[])}
	 * @param panel - array of ValuePanels that contains maximum values data
	 */
	public void setMaximumValues(ValuePanel panel[])
	{
		maxVoltageCE=panel[0].getValue();
		maxVoltageBE=panel[1].getValue();
		maxVoltageCB=panel[2].getValue();
		maxCollectorCurrent=panel[3].getValue();
		maxBaseCurrent=panel[4].getValue();
		maxEmitterCurrent=panel[5].getValue();
	}
	/**
	 * Use {@link #setSaturationValues(ValuePanel[])}
	 * @param panel - Matrix Array (also holds saturation values)
	 */
	public void setSaturationValues(MatrixPanel panel)
	{
		saturationVoltage=panel.getSaturationVoltage();
		saturationCurrent=panel.getSaturationCurrent();
	}
	/**
	 * Use {@link #getBaseEmitterVoltage(int)}
	 * @param baseEmitterVoltageStep  base-emitter voltage id - determines which value from array to use
	 * @return base-emitter voltage value
	 */
	public double getBaseEmitterVoltage(int baseEmitterVoltageStep) 
	{
		return voltageBE[baseEmitterVoltageStep];
	}
	/**
	 * Use {@link #getCollectorEmitterVoltage(int)}
	 * @param baseCollectorVoltageStep  collector-emitter voltage id - determines which value from array to use
	 * @return collector-emitter voltage value
	 */
	public double getCollectorEmitterVoltage(int collectorEmitterVoltageStep) 
	{
		return voltageCE[collectorEmitterVoltageStep];
	}
}