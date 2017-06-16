public class Planet {
   public double xxPos;
   public double yyPos;
   public double xxVel;
   public double yyVel;
   public double mass;
   public String imgFileName;

   public static double G = 6.67E-11;

   public Planet( double xP, double yP, double xV,
		  double yV, double m, String img ) {
      xxPos = xP;
      yyPos = yP;
      xxVel = xV;
      yyVel = yV;
      mass = m;
      imgFileName = img;
   }

   public Planet( Planet p ) {
      xxPos = p.xxPos;
      yyPos = p.yyPos;
      xxVel = p.xxVel;
      yyVel = p.yyVel;
      mass = p.mass;
      imgFileName = p.imgFileName;      
   }

   public double calcDistance( Planet p ) {
      double dx = xxPos - p.xxPos;
      double dy = yyPos - p.yyPos;
      return Math.sqrt( dx*dx + dy*dy);
   }

   public double calcForceExertedBy( Planet p ) {
      double r = calcDistance( p );
      return G * mass * p.mass / (r*r);
   }

   public double calcForceExertedByX( Planet p ) {
      double r = calcDistance( p );
      double f = calcForceExertedBy( p );
      double dx = p.xxPos - xxPos;
      return f * dx / r;      
   }

   public double calcForceExertedByY( Planet p ) {
      double r = calcDistance( p );
      double f = calcForceExertedBy( p );
      double dy = p.yyPos - yyPos;
      return f * dy / r;      
   }

   public boolean Equal( Planet p ) {
      if( this == p )
	 return true;
      return false;
   }

   public double calcNetForceExertedByX( Planet[] allplanets ) {
      double netfx = 0;
      for( int i = 0; i < allplanets.length; i++ ) {
	 Planet p = allplanets[i];
	 if( !Equal( p ) ) {
	    netfx += calcForceExertedByX( p );
	 }
      }
      return netfx;
   }

   public double calcNetForceExertedByY( Planet[] allplanets ) {
      double netfy = 0;
      for( int i = 0; i < allplanets.length; i++ ) {
	 Planet p = allplanets[i];
	 if( !Equal( p ) ) {
	    netfy += calcForceExertedByY( p );
	 }
      }
      return netfy;
   }
   
   public void update( double dt, double fx, double fy ) {
      double ax = fx / mass;
      double ay = fy / mass;

      xxVel += ax * dt;
      yyVel += ay * dt;

      xxPos += xxVel * dt;
      yyPos += yyVel * dt;
   }

   public void draw() {
      StdDraw.picture( xxPos, yyPos, imgFileName );
   }
}
