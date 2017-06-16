/* 
 *
 * Make sure to see the more detailed description of
 * StdDraw at: http://introcs.cs.princeton.edu/java/15inout/ 
 * 
 * The link above also provides additional examples like BouncingBall.java
 *
 * Or you can see the full documentation at:
 *   http://introcs.cs.princeton.edu/java/15inout/javadoc/StdDraw.html
 */

public class StdDrawDemo {

   public static void drawSquare() {
      /* Clears the drawing window. */
      StdDraw.clear();

      /* Set both x- & y-axis start from -1.5 to +1.5 */
      StdDraw.setScale( -1, 1 ); //
      /* Set color and draw squares
	 @args: filledSquare( center_x, center_x, radius )
       */
      StdDraw.setPenColor( StdDraw.BLUE );      
      StdDraw.filledSquare( 0.5, 0.5, 0.5 );
      StdDraw.setPenColor( StdDraw.BLACK );      
      StdDraw.filledSquare( -0.5, 0.5, 0.5 );
      StdDraw.setPenColor( StdDraw.GREEN );      
      StdDraw.filledSquare( 0.5, -0.5, 0.5 );
      StdDraw.setPenColor( StdDraw.CYAN );      
      StdDraw.filledSquare( -0.5, -0.5, 0.5 );
      StdDraw.setPenColor( StdDraw.RED );
      StdDraw.filledSquare( 0, 0, 0.5 );

      /* When a new canvas scale is chosen, the previous
	 drawings do NOT change with the scale.
	 Resetting scale will only work on later drawings.

	 In the example below, the scale is doubled from
	 (-1,1) to (-2,2), meaning, for example, on that
	 fix-sized canvas, a point previous at (-1,1) is
	 now marked as (-2,2) instead.
	 Therefore, a new square with (0,0,0.5)*2 = (0,0,1)
	 has to be drawn to cover the previous RED square
	 with (0,0,0.5). I selected 0.95 instead of 1.0 to
	 show that the new square barely covers the old one.
       */
      StdDraw.setScale( -2, 2 );
      StdDraw.setPenColor( StdDraw.MAGENTA );
      StdDraw.filledSquare( 0, 0, 0.95 );
   }

   public static void main(String[] args) {
      drawSquare();
   }
} 
