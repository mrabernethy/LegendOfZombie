package Source;

/**
 *
 * @author tpg0400
 */
public class Vector2D 
{
    private float x, y;
    
    public Vector2D(){}
    
    public Vector2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }    
    
    /**
     * Gets the length of the vector from (0,0).
     * @return the vector length
     */
    public float getLength()
    {
        return (float) Math.sqrt(x*x + y*y);
    }
    
    /**
     * Gets the distance between two vectors.
     * @param v the second vector
     * @return the vector length
     */
    public float getDistance(Vector2D v)
    {
        float newX = v.getX() - x;
        float newY = v.getY() - y;
        return (float) Math.sqrt(newX*newX + newY*newY);
    }
    
    /**
     * Gets the angle of this vector from the origin (0,0).
     * @return 
     */
    public float angle()
    {
        return (float) Math.atan2((double)y,(double)x);
    }
    
    /**
     * Gets the angle between this vector and a given vector.
     * @param v
     * @return 
     */
    public float getAngle(Vector2D v)
    {
        float newX = v.getX() - x;
        float newY = v.getY() - y;
        return (float) Math.atan2((double)newY,(double)newX);
    }
    
    /**
     * If the length of the new vector is greater than 1 divide by its length.
     * Returns a vector 1 unit long.
     * @param v 
     * @return the unit vector
     */
    public Vector2D getUnit(Vector2D v)
    {
        float newX = v.getX() - x;
        float newY = v.getY() - y;
        Vector2D unit = new Vector2D(newX, newY);
        if (unit.getLength() > 0)
        {
            unit.divideVector(unit.getLength());
        }
        return unit;
    }
    
    /**
     * Returns the dot product of the two vectors.
     * Value of zero shows angle is 90 degrees.
     * Positive value shows angle less than 90 degrees.
     * Negative value shows angle greater than 90 degrees.
     * @param vector the multiplier vector
     * @return the dot product
     */
    private float dotProduct(Vector2D vector)
    {
        return (x*vector.getX()) + (y*vector.getY());
    }
    
    /**
     * Rotates a vector. Positive angle anti-clockwise, negative clockwise.
     * Must be rotated around origin vector (this vector).
     * @param vector the vector to rotate
     * @param radians the angle of rotation
     * @return 
     */
    private Vector2D rotateVector(float radians)
    {
        float sine = (float) Math.sin(radians);
        float cosine  = (float) Math.cos(radians);
        Vector2D v = new Vector2D(x, y);

        // rotate the vector
        v.setX(v.getX() * cosine - v.getY() * sine);
        v.setY(v.getX() * sine + v.getY() * cosine);
        
        return v;
    }
    
    public float dotZombie(Vector2D v, float angle)
    {
        Vector2D zv = rotateVector(angle);
        return dotProduct(v);
    }
    
    public void multiplyVector(float scaler)
    {
        x = x * scaler;
        y= y * scaler;
    }
    
    public void divideVector(float scaler)
    {
        x = x/scaler;
        y = y/scaler;
    }
    
    public float getX()
    {
        return x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public void setX(float x)
    {
        this.x = x;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }
}
