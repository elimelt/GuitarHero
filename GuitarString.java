// Name: Elijah Melton
// TA:   James Hu
// HW:   Guitar Hero
//
// GuitarString uses a ring buffer and the Karplus-Strong update to imitate the physics
// of a real guitar string at a given frequency. Instances of Guitar Strings appear in 
// Guitar (interface) objects. Uses Java util class.
import java.util.*;

public class GuitarString {
   public static final double ENERGY_DECAY_FACTOR = .996;
   private Queue<Double> ringBuffer;   
   
   //contstructs GuitarString with ring buffer of length frequency/sample rate containing only 
   //zeros. Takes double frequency parameter. If frequency <= 0 or if ring bufffer has less 
   //than 2 elements, throws IllegalArgumentException.
   public GuitarString(double frequency) {
      if (frequency <= 0) throw new IllegalArgumentException();
      int N = (int)Math.round(StdAudio.SAMPLE_RATE/frequency);
      if (N < 2) throw new IllegalArgumentException();
      ringBuffer = new LinkedList<>();
      for (int i = 0; i < N; i++) ringBuffer.add(0.0);
   }
   
   //constructs GuitarString and initializes ring buffer to the values contained in input array
   //parameter. array must have 2 or more elements or constructor throws IllegalArgumentException.
   //only used for testing
   public GuitarString(double[] init) {
      if (init.length < 2) throw new IllegalArgumentException();
      ringBuffer = new LinkedList<>();
      for (double n : init) {
         ringBuffer.add(n);
      }
   }
   
   //replaces all values in ring buffer with random doubles from -.5 <= n < .5
   public void pluck() {
      Random r = new Random();
      for (int i = 0; i < ringBuffer.size(); i++){
         ringBuffer.add(r.nextDouble() - .5);
         ringBuffer.remove();
      }
   }
   
   //updates the ring buffer by applying the Karplus-Strong update once. averages the first two
   //values of the ring buffer and multiplies them by the DECAY_FACTOR constant, then adds this 
   //value to the end of the ring buffer.
   public void tic() {
      double front = ringBuffer.remove();
      double next = ringBuffer.peek();
      ringBuffer.add(ENERGY_DECAY_FACTOR*((front+next)/2));
   }
   
   //returns the double at the front of the ring buffer
   public double sample() {
      return ringBuffer.peek();
   }
}