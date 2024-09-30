package de.schmiereck.vect;
/*
 *	Copyright (c) open4business GmbH, 2005
 */
/**
 * <p>
 *	TODO
 * </p>
 * 
 * http://64.233.183.104/search?q=cache:Bt_G2o24uf0J:www2002162.thinkquest.dk/content/java/source/VecMath.java+java+Vector2d+source+angle&hl=de
 * 
 * @author smk
 * @version <p>16.02.2005:	created, smk</p>
 */
public class Vector2d
{
	private double x;
	private double y;
	
	/**
	 * @param xx
	 * @param yy
	 */
	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public static double dotProduct(Vector2d v1, Vector2d v2)
	{
		double retVal = v1.getX() * v2.getX() + v1.getY() * v2.getY();

		return retVal;
	}

	public static double angle(Vector2d v1, Vector2d v2)
	{
		double retVal = 0;

		retVal = Math.acos((Vector2d.dotProduct(v1, v2)) / (v1.getLength() * v2.getLength()));

		return retVal;
	}


	public static double angle(Vector2d v1)
	{
		double retVal = 0;

		retVal = Math.acos((v1.getX() + v1.getY()) / (v1.getLength()));

		return retVal;
	}

	/**
	 * @return the attribute {@link #x}.
	 */
	public double getX()
	{
		return this.x;
	}
	/**
	 * @return the attribute {@link #y}.
	 */
	public double getY()
	{
		return this.y;
	}

	  //---------------------------------------------------------------------------
	  /**
	   * Copy constructor
	   * @param v source vector
	   */
	  public Vector2d(Vector2d v)
	  {
	    x = v.x;
	    y = v.y;
	  }

	  //---------------------------------------------------------------------------
	  /**
	   * Add vector
	   * @param pos vector to add
	   */
	  public void add(Vector2d pos)
	  {
	    x += pos.x;
	    y += pos.y;
	  }

	/**
	 * Multiply by scalar
	 * @param scalar scalar
	 */
	public void multiply(double scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
	}


	  /**
	   * Get length of this vector
	   * @return length of the vector
	   */
	  public double getLength()
	  {
	    return Math.sqrt(this.x * this.x + this.y * this.y);
	  }


	  //---------------------------------------------------------------------------
	  /**
	   * Get distance to second vector
	   * @return distance between vectors
	   */
	  public double getDistance(Vector2d second)
	  {
	    double a = second.x - this.x;
	    double b = second.y - this.y;
	    return Math.sqrt(a*a + b*b);
	  }


	  //---------------------------------------------------------------------------
	  /**
	   * Convert to string
	   * @return object converted to string
	   */
	  public String toString()
	  {
	    String s;

	    s = "(x=" + this.x + ", y=" + this.y + ")";

	    return s;
	  }

	/**
	 * @param d
	 */
	public void scale(double d)
	{
	}

	/**
	 * 
	 */
	public void normalize()
	{
		double l = this.getLength();
		
		this.x = this.x / l;
		this.y = this.y / l;
	}
}
