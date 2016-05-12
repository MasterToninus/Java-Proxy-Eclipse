/**
 * 
 */
package it.csttech.miti;

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
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see it.csttech.miti.TargetInterface#print()
	 */
	@Override
	public void method() {
		String thisMethodName = new Object(){}.getClass().getEnclosingMethod().toString();
		System.out.println("    *" + this.getClass().getName() + "*: is invoking *" + thisMethodName +"*");
	}

}
