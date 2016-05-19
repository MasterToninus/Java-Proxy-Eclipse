/**
 * 
 */
package it.csttech.demoproxy;

/**
 * @author JugAncona
 *TargetImplementation.java
 *The class implements the interface
 */
public class TargetImplementation implements TargetInterface {

	public String attribute = "ciao!";
	
	/**
	 * 
	 */
	public TargetImplementation() {

	}

	/* (non-Javadoc)
	 * @see it.csttech.miti.TargetInterface#print()
	 */
	@Override
	public void method() {
		String thisMethodName = new Object(){}.getClass().getEnclosingMethod().toString();
		System.out.println("    *" + this.getClass().getName() + "*: is invoking *" + thisMethodName +"*");
	}
	
	/**
	 * A method not defined in the interface
	 * @literal i
	 * @literal j
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean dummyMethod(final int i, final int j) {
		String thisMethodName = new Object(){}.getClass().getEnclosingMethod().toString();
		System.out.println("    *" + this.getClass().getName() + "*: is invoking *" + thisMethodName +"* with args ( " + i + " , " + j + ")");		
		return true;
	}

}
