
public class GameOverAnimation {

	int NUM_FRAMES = 150;
	int NUM_BLOBS = 800;
	double PERSPECTIVE = 50.0;
	
	int _width,_height;
	
	private static long s=1;
	public static double prng() {
		s = s * 1488248101 + 981577151;
		return ((s % 65536) - 32768) / 32768.0;
	}
	
	private String frames[] = new String[NUM_FRAMES];
	private char p;
	private int i,j,x,y,z,v,ith,i0;
	private int maxx,minx,maxy,miny,delay=1000;
	private double bx,by,bz,br,r,th,t;
	private GameOverAnimationSpaceblob blobs[];
	
	public GameOverAnimation(int height,int width) {
		_width = Helper.constrain(width, 5, 100);
		_height = Helper.constrain(height, 7, 100);
		minx = -_width / 2;
		maxx = _width+minx-1;
		miny = -_height / 2;
		maxy = _height+miny-1;
		
		// generate "random" blob coordinates
		blobs = new GameOverAnimationSpaceblob[NUM_BLOBS];
		for (i=0;i<NUM_BLOBS;i++) {
			bx = prng();
			by = prng();
			bz = prng();
			br = Math.sqrt(bx*bx + by*by + bz*bz);
			blobs[i] = new GameOverAnimationSpaceblob();
			blobs[i].x = (bx / br) * (1.3 + 0.2 * prng());
			blobs[i].y = (0.5 * by / br) * (1.3 + 0.2 * prng());
			blobs[i].z = (bz / br) * (1.3 + 0.2 * prng());
		}
		
		for(i=0;i<NUM_FRAMES; i++) {
			t = (1. * i) / NUM_FRAMES;
			int n = _width * _height;
			frames[i] = ""; //String.format("%1$#"+n+"s", ""); // initialize frame with spaces
			for(y=miny;y<=maxy; y++) {
				for(x=minx;x<=maxx; x++) {
					// start with a single *
					if(i==0) {
						frames[i] += (x==0 && y==0) ? "*" : " ";
						continue;
					}
					// expanding point for 7 frames
					if(i<8) {
						r = Math.sqrt(x*x + 4*y*y);
						frames[i] += (r < i*2) ? "@" : " ";
						continue;
					}
					// show blast wave
					r = Math.sqrt(x*x + 4*y*y) * (0.5 + (prng()/3.0)*Math.cos(16.*Math.atan2(y*2.+0.01, x+0.01))*.3);
					ith = (int)(32+th*32. * (1/Math.PI));
					v = (int)(i - r - 7);
					if(v<0) {
						if(i<19) {
							switch(i-8) {
								case 0:	frames[i] += "%"; break;
								case 1: frames[i] += "@"; break;
								case 2: frames[i] += "W"; break;
								case 3: frames[i] += "#"; break;
								case 4: frames[i] += "H"; break;
								case 5: frames[i] += "="; break;
								case 6: frames[i] += "+"; break;
								case 7: frames[i] += "~"; break;
								case 8: frames[i] += "-"; break;
								case 9: frames[i] += ":"; break;
								case 10: frames[i] += "."; break;
							}
						} else {
							frames[i] += " ";
						}
					} else if (v<20) {
						switch(v) {
							case 0: frames[i] += " "; break;
							case 1: frames[i] += "."; break;
							case 2: frames[i] += ":"; break;
							case 3: frames[i] += "!"; break;
							case 4: frames[i] += "H"; break;
							case 5: frames[i] += "I"; break;
							case 6: frames[i] += "O"; break;
							case 7: frames[i] += "M"; break;
							case 8: frames[i] += "W"; break;
							case 9: frames[i] += "#"; break;
							case 10: frames[i] += "%"; break;
							case 11: frames[i] += "$"; break;
							case 12: frames[i] += "&"; break;
							case 13: frames[i] += "@"; break;
							case 14: frames[i] += "0"; break;
							case 15: frames[i] += "8"; break;
							case 16: frames[i] += "O"; break;
							case 17: frames[i] += "="; break;
							case 18: frames[i] += "+"; break;
							case 19: frames[i] += "-"; break;
						}
					} else {
						frames[i] += " ";
					}
				}
			}
			
			
			// add blobs with perspective effect
			if(i>6) {
				i0 = i-6;
				for(j=0;j<NUM_BLOBS; j++) {
					bx = blobs[j].x * i0;
			        by = blobs[j].y * i0;
			        bz = blobs[j].z * i0;
			        if (bz<5-PERSPECTIVE || bz>PERSPECTIVE) continue;
			        x = (int)(_width / 2 + bx * PERSPECTIVE / (bz+PERSPECTIVE));
			        y = (int)(_height / 2 + by * PERSPECTIVE / (bz+PERSPECTIVE));
			        if (x>=0 && x<_width && y>=0 && y<_height) {
			        	String _c = (bz>40) ? "." : (bz>-20) ? "o" : "@";
			        	frames[i] = frames[i].substring(0, getIdx(x,y)) + _c + frames[i].substring(getIdx(x,y)+1);
			        }
				}
			}
		}
	}
	
	public void play() {
		Console _c = Console.getInstance();
		for(i=0;i<NUM_FRAMES;i++) {
			for(j=0;j<_height;j++) {
				if(getIdx(_width,j) > frames[i].length()) {
					int _foo = frames[i].length();
				} else {
					//String _test = frames[i].substring(_start,_end);
					_c.writeAtPosition(0,j,frames[i].substring(getIdx(0,j), getIdx(_width,j)));
				}
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			delay=33;  // change to 30fps after first frame
		}
		
	}

	private int getIdx(int x, int y) {
		return (y*_width) + x;
	}
}
