// Name: Elijah Melton
// TA:   James Hu
// HW:   Guitar Hero
//
// Guitar 37 implements the Guitar interface. This Guitar object has 37 strings and can play 
// notes from 110-880 Hz chromatically using the key mappings specified by the KEYBOARD 
// constant. 

public class Guitar37 implements Guitar {
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
   private GuitarString[] strings;              
   private int time;                            //keeps track of how many times tic() is called
   
   //constructs a Guitar37 which has 37 GuitarStrings with frequency 110 to 880 Hz. 
   public Guitar37() {
      int numStrings = 37;
      strings = new GuitarString[numStrings];
      for (int i = 0; i < numStrings; i++) {
         strings[i] = new GuitarString(chromaticFrequency(i));
      }
      this.time = 0;
   }
   
   //given an int parameter, this method plays the note that is pitch indexes away from concert
   //a in the chromatic scale. If a guitar cannot play a given pitch, it is ignored.
   //in this class, -24 <= pitch <= 12 corresponds to the range of this guitar.
   public void playNote(int pitch) {
      if(pitch >= -24 && pitch <= 12)strings[pitch+KEYBOARD.indexOf('v')].pluck();
   }
   
   //returns a boolean based on if the guitar has a string that corresponds to the input 
   //character. If our guitar's keyboard layout has it, returns true, if not, returns false.
   public boolean hasString(char string) {
      return KEYBOARD.contains(""+string);
   }
   
   //if the guitar has a string that corresponds to the parameter char string, then we
   //pluck that corresponding guitar string. If it doesnt have a corresponding string, this method
   //throws an IllegalArgumentException.
   public void pluck(char string){
      if(!hasString(string)) throw new IllegalArgumentException();
      strings[KEYBOARD.indexOf(string)].pluck();
   }
   
   //returns the sum of the samples for all 37 strings (as a double).
   public double sample() {
      double total = 0; 
      for (GuitarString s : strings) {
         total+=s.sample();
      }
      return total;
   }
   
   //tics all 37 strings once (performing the Karplus-Strong update to each ring buffer)
   //calling tic advances time.
   public void tic() {
      this.time++;
      for (GuitarString s :  strings) {
         s.tic();
      }
   }
   
   //returns the current time which is the number of times tic() has been called.
   public int time() {
      return this.time;
   }
   
/*--------------------------------------------------------------------------------------------*/
   
   //given int parameter scaleIndex, returns the frequency scaleIndex notes away (chromatically)
   //from the starting frequency (for this class it is 440 Hz). if index is less  
   //than 0, throws IllegalArgumentException
   private double chromaticFrequency(int scaleIndex) {
      if (scaleIndex < 0) throw new IllegalArgumentException();
      double startingFreq = 440.0;
      return startingFreq*Math.pow(2.0,(scaleIndex-24.0)/12.0);
   }
   
   
}
