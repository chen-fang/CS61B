public class NBody {
   public static double readRadius( String file ) {
      In infile = new In( file );
      infile.readString();
      return infile.readDouble();
   }

   public static Planet[] readPlanets( String file ) {
      In infile = new In( file );
      int num_planets = infile.readInt();
      
      /* skip the 2nd line in data file */
      infile.readString();
      StdAudio.play( "audio/2001.mid" );
      
      Planet[] planets = new Planet[ num_planets ];
      for( int i = 0; i < num_planets; i++ ) {

	 double xP = infile.readDouble();
	 double yP = infile.readDouble();
	 double xV = infile.readDouble();
	 double yV = infile.readDouble();
	 double m  = infile.readDouble();
	 String img = "images/" + infile.readString();

	 planets[i] = new Planet( xP, yP, xV, yV, m, img );

	 /* The code below is Very Wrong due to lack
	    understandings of arrays
	 Planet p = planets[i];
	 p.xxPos = infile.readDouble();
	 p.yyPos = infile.readDouble();
	 p.xxVel = infile.readDouble();
	 p.yyVel = infile.readDouble();
	 p.mass  = infile.readDouble();
	 p.imgFileName = infile.readString();
	 */
      }
      return planets;
   }

   public static void main( String[] args ) {
      double T = Double.parseDouble( args[0] );
      double dt = Double.parseDouble( args[1] );
      String filename = args[2];
      double univRadius = readRadius( filename );
      Planet[] planets = readPlanets( filename );
      int num_planets = planets.length;

      // Draw background
      StdDraw.clear();
      StdDraw.setScale( -univRadius, univRadius );
      StdDraw.picture( 0, 0, "./images/starfield.jpg" );
      // Draw stars
      for( int i = 0; i < num_planets; i++ ) {
	 Planet p = planets[i];
	 p.draw();
      }

      // Animation
      double time = 0;
      while( time < T ) {
	 double[] xForces = new double[ num_planets ];
	 double[] yForces = new double[ num_planets ];
	 for( int i = 0; i < num_planets; i++ ) {
	    Planet p = planets[i];
	    xForces[i] = p.calcNetForceExertedByX( planets );
	    yForces[i] = p.calcNetForceExertedByY( planets );
	    p.update( dt, xForces[i], yForces[i] );
	 }
	 StdDraw.picture( 0, 0, "./images/starfield.jpg" );
	 for( int i = 0; i < num_planets; i++ ) {
	    planets[i].draw();
	 }
	 /* Wait 10 milliseconds.
	    Otherwise animation flashes.
	 */
	 StdDraw.show( 10 );
	 time += dt;
      }
   }
}
