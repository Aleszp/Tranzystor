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
	 * Use {@link #Data()} as constructor. 
	 */
	public Data() throws HeadlessException 
	{
		
		fitParameter=5;
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
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId base-emitter voltage id - determines which value from array to use
	 */
	public void countCurrentsForSingleStep(int collectorEmitterVoltageStepId, int baseEmitterVoltageStepId)
	{	
		//If base-emitter voltage is lower than saturation base-emitter voltage treat it as non-linear 
		if(voltageBE[baseEmitterVoltageStepId]<saturationVoltage)
		{
			setBaseCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,0.1*saturationCurrent*(Math.exp(voltageBE[baseEmitterVoltageStepId]/(fitParameter*0.026))-1));
		}
		else //If base-emitter voltage is greater than saturation base-emitter voltage treat it as linear
		{
			double tmp=((voltageBE[baseEmitterVoltageStepId]-saturationVoltage)-voltageCE[collectorEmitterVoltageStepId]*hMatrix[1])*1000/hMatrix[0];
			tmp+=+saturationCurrent*(Math.exp(saturationVoltage/(fitParameter*0.026))-1);
			setBaseCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,0.1*tmp);
		}
		setCollectorCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,hMatrix[2]*currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][0]+hMatrix[3]*voltageCE[collectorEmitterVoltageStepId]);
		setEmitterCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][0]+currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][1]);
			
	}
	/**
	 * Use {@link #checkMaximumValues(int,int)} to check if voltages or calculated currents don't exceed allowed values (for single pair of voltages).
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId base-emitter voltage id - determines which value from array to use
	 */
	public void checkMaximumValues(int collectorEmitterVoltageStepId, int baseEmitterVoltageStepId)
	{
		if(voltageBE[baseEmitterVoltageStepId]>maxVoltageBE||
				voltageCE[collectorEmitterVoltageStepId]>maxVoltageCE||
				(voltageCE[collectorEmitterVoltageStepId]-voltageBE[baseEmitterVoltageStepId])>maxVoltageCB||
				getBaseCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId)>maxBaseCurrent||
				getCollectorCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId)>maxCollectorCurrent||
				getEmitterCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId)>maxEmitterCurrent)
		{
			setBaseCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,0);
			setCollectorCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,0);
			setEmitterCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,0);
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
		
		dataContainer.collectorEmitterVoltegeSteps=100;
		dataContainer.baseEmitterVoltegeSteps=200;
		dataContainer.createArrays();
		
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
		assertEquals(dataContainer.getBaseCurrent(collectorEmittervoltageStep,baseEmittervoltageStep), 0.0015, 0.001); //fixed 
		assertEquals(dataContainer.getCollectorCurrent(collectorEmittervoltageStep,baseEmittervoltageStep), 0.5882, 0.001); //fixed
		assertEquals(dataContainer.getEmitterCurrent(collectorEmittervoltageStep,baseEmittervoltageStep), 0.5898, 0.001);   //fixed
	}
	
	/**
	 * Use {@link #getBaseCurrent(int, int)}
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @return base current value
	 */
	public double getBaseCurrent(int collectorEmitterVoltageStepId,int baseEmitterVoltageStepId)
	{
		return currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][0];
	}
	
	/**
	 * Use {@link #setBaseCurrent(int, int, double)}
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param base current new value
	 */
	public void setBaseCurrent(int collectorEmitterVoltageStepId,int baseEmitterVoltageStepId, double value)
	{
		currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][0]=value;
	}
	
	/**
	 * Test setter and getter for base current 
	 * @see getBaseCurrent
	 * @see setBaseCurrent
	 */
	@Test
	public void testSetAndGetBaseCurrent()
	{
		int collectorEmitterVoltageStepId=0;
		int baseEmitterVoltageStepId=0;
		double testValue=0.45;
		collectorEmitterVoltegeSteps=1;
		baseEmitterVoltegeSteps=1;
		createArrays();
		setBaseCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,testValue);
		assertEquals(getBaseCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId), testValue, 0.001);
	}
	
	/**
	 * Use {@link #getCollectorCurrent(int, int)}
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @return collector current value
	 */
	public double getCollectorCurrent(int collectorEmitterVoltageStepId,int baseEmitterVoltageStepId)
	{
		return currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][1];
	}
	/**
	 * Use {@link #setCollectorCurrent(int, int, double)}
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param collector current new value
	 */
	public void setCollectorCurrent(int collectorEmitterVoltageStepId,int baseEmitterVoltageStepId, double value)
	{
		currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][1]=value;
	}
	
	/**
	 * Test setter and getter for collector current 
	 * @see getCollectorCurrent
	 * @see setCollectorCurrent
	 */
	@Test
	public void testSetAndGetCollectorCurrent()
	{
		int collectorEmitterVoltageStepId=0;
		int baseEmitterVoltageStepId=0;
		double testValue=7.62;
		collectorEmitterVoltegeSteps=1;
		baseEmitterVoltegeSteps=1;
		createArrays();
		setCollectorCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,testValue);
		assertEquals(getCollectorCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId), testValue, 0.001);
	}
	/**
	 * Use {@link #getEmitterCurrent(int, int)}
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @return emitter current value
	 */
	public double getEmitterCurrent(int collectorEmitterVoltageStepId,int baseEmitterVoltageStepId)
	{
		return currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][2];
	}
	/**
	 * Use {@link #setEmitterCurrent(int, int, double)}
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param emitter current new value
	 */
	public void setEmitterCurrent(int collectorEmitterVoltageStepId,int baseEmitterVoltageStepId, double value)
	{
		currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][2]=value;
	}
	
	/**
	 * Test setter and getter for emitter current
	 * @see getEmitterCurrent
	 * @see setEmitterCurrent 
	 */
	@Test
	public void testSetAndGetEmitterCurrent()
	{
		int collectorEmitterVoltageStepId=0;
		int baseEmitterVoltageStepId=0;
		double testValue=3.14;
		collectorEmitterVoltegeSteps=1;
		baseEmitterVoltegeSteps=1;
		createArrays();
		setEmitterCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId,testValue);
		assertEquals(getEmitterCurrent(collectorEmitterVoltageStepId,baseEmitterVoltageStepId), testValue, 0.001);
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
		saturationCurrent=panel.getSaturationCurrent()/1000;
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
	/**
	 * Use {@link #getCurrent(int, int,int)}
	 * @param collectorEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param baseEmitterVoltageStepId collector-emitter voltage id - determines which value from array to use
	 * @param current id (0 - base current, 1 - collector current, 2 - emitter current, anything else returns 0)
	 * @return chosen current value
	 */
	public double getCurrent(int collectorEmitterVoltageStepId,int baseEmitterVoltageStepId, int id) 
	{
		if(id<0||id>2)
			return 0;
		return currents[collectorEmitterVoltageStepId][baseEmitterVoltageStepId][id];
	}
	
}